package com.jyut.system;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.jyut.system.C.L;
import com.jyut.system.C.S;
import com.jyut.system.adapter.JsonResponseAdapter;
import com.jyut.system.bean.User;
import com.jyut.system.http.CookieManager;
import com.jyut.system.http.HttpJsonRequest;
import com.jyut.system.util.Encryption;
import com.yolanda.nohttp.rest.CacheMode;
import com.yolanda.nohttp.rest.Response;

import java.net.HttpCookie;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author wztscau
 * @date Sep 21, 2016
 * @project 粤盟管理系统客户端
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
//    @Bind(R.id.login_progress)
//    ProgressBar mProgressView;
    @Bind(R.id.et_account)
    AutoCompleteTextView mAccountView;
    @Bind(R.id.et_pwd)
    EditText mPasswordView;
    @Bind(R.id.btn_login)
    Button mLoginFormView;
    @Bind(R.id.spn_network)
    Spinner spnNetwork;

    private static final String TAG = "LoginActivity";
    private static String network = C.URL_SERVER_LAN;
    public static String URL_SERVER = network + C.PATH_SERVER_DEFAULT;
    public static String URL_LOGIN = URL_SERVER + C.PATH_SERVER_LOGIN;

    private String userName;
    private String pwd;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loadCookies();
        // Set up the login form.
        spnNetwork.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        network = C.URL_SERVER_LAN;
                        break;
                    case 1:
                        network = C.URL_SERVER_DOMAIN;
                        break;
                    case 2:
                        network = C.URL_SERVER_WAN;
                        break;
                    case 3:
                        network = C.OFFLINE;
                        break;
                }
                URL_SERVER = network + C.PATH_SERVER_DEFAULT;
                URL_LOGIN = URL_SERVER + C.PATH_SERVER_LOGIN;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mLoginFormView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mAccountView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        userName = mAccountView.getText().toString().trim();
        pwd = mPasswordView.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(pwd) && !isPasswordValid(pwd)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid account address.
        if (TextUtils.isEmpty(userName)) {
            mAccountView.setError(getString(R.string.error_field_required));
            focusView = mAccountView;
            cancel = true;
        } else if (!isAccountValid(userName)) {
            mAccountView.setError(getString(R.string.error_invalid_email));
            focusView = mAccountView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
//            showProgress(true);

            login(userName, pwd);
        }
    }

    /**
     * @param userName
     * @param pwd
     */
    public void login(String userName, String pwd) {

        HttpJsonRequest request = new HttpJsonRequest(URL_LOGIN, CacheMode.NONE_CACHE_REQUEST_NETWORK);
        // setListener要在发送请求之前，因为有可能请求回应的太快
        request.setOnResponseListener(new JsonResponse(this));
        if (C.offLine(network)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        // 这里要进行MD5加密
        // User里面自动会加密
        try{
            request.sendRequest(
                    User.newInstance(userName, pwd)
            );
        }catch (User.AlreadyException e){
            request.sendRequest(User.getInstance());
        }
    }


    /**
     * 从本地文件中提取用户名和密码
     */
    private void loadCookies() {
        List<HttpCookie> cookies = CookieManager.loadCookies();
        Log.d(TAG, "loadCookies: "+cookies);
        for(HttpCookie cookie : cookies){
            String name = cookie.getName();
            if(L.USERNAME.equals(name)){
                mAccountView.setText(cookie.getValue());
            }
            if(L.PASSWORD.equals(name)){
                mPasswordView.setText(cookie.getValue());
            }
        }
    }

    class JsonResponse extends JsonResponseAdapter<JSONObject> {


        public JsonResponse(Context context) {
            super(context);
        }

        @Override
        public void onSucceed(int what, Response<JSONObject> response) {
            super.onSucceed(what, response);
            JSONObject jsonObject = response.get();
            String message = jsonObject.getString(L.MESSAGE);
            if (S.ACCESS_MYSQL_FAILED.equals(message)) {
                onFailed(what, response);
                return;
            }
            Log.i(TAG, jsonObject.toJSONString());

            String data = jsonObject.getString(L.DATA);
            Log.i(TAG, "data : " + data);
            if (S.ACCESS_MYSQL_SUCCEED.equals(message)) {
                String permission = JSONObject.parseObject(data).getString(L.PERMISSION);
                Log.i(TAG, "permission=" + permission);
                // 1.保存用户名和密码，以备下次用户登录使用
                try {
                    User.commit();
                    saveCookies(response);
                    User.getInstance().setPermission(permission);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 2.跳转到主界面
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else if(S.USERNAME_OR_PWD_WRONG.equals(message)){
                toastShort(message);
            }
        }

        @Override
        public void onFinish(int what) {
            super.onFinish(what);
//            showProgress(false);
        }

        @Override
        public void onNetworkError() {
            super.onNetworkError();
            toastShort("连接不到服务器");
        }

        @Override
        public void onParseError() {
            super.onParseError();
            toastShort("用户名或密码错误");
        }
    }

    private void toastShort(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 保存用户名和密码
     *
     * @throws Exception
     */
    private void saveCookies(Response<JSONObject> response) throws Exception {
        String url = response.request().url();
        URI uri = new URI(url);
        List<HttpCookie> cookies = new ArrayList<>();
        cookies.add(new HttpCookie(L.USERNAME,userName));
        cookies.add(new HttpCookie(L.PASSWORD,pwd));
        CookieManager.saveCookies(cookies,uri);
    }

    private boolean isAccountValid(String account) {
        return true;
    }

    private boolean isPasswordValid(String password) {
        return true;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//    private void showProgress(final boolean show) {
//        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
//        // for very easy animations. If available, use these APIs to fade-in
//        // the progress spinner.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
//
//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//                }
//            });
//
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mProgressView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });
//        } else {
//            // The ViewPropertyAnimator APIs are not available, so simply show
//            // and hide the relevant UI components.
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//        }
//    }

}

