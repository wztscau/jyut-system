/**
 *
 */
package com.jyut.system.fragement;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jyut.system.C.L;
import com.jyut.system.C.S;
import com.jyut.system.LoginActivity;
import com.jyut.system.adapter.JsonResponseAdapter;
import com.jyut.system.bean.Member;
import com.jyut.system.http.HttpJsonRequest;
import com.jyut.system.util.TextUtil;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 编辑界面的基类,主要实现编辑发送请求功能,数据的具体实现由子类完成
 *
 * @author wztscau
 * @date Sep 28, 2016
 * @project
 */
public abstract class EditorFragment extends Fragment {

    /**
     * 读或写模式
     */
    String type;

    /**
     * 将数据绑定到bean上面
     */
    public abstract void bindMessages();

    public EditorFragment() {
    }

    /**
     * 检测一系列的edittext是否为空，
     * 因为inputtextlayout可以seterror，
     * 因此该方法暂时荒废
     *
     * @param set 包含所有edittext的集合
     * @return 为空的字段名
     */
    @Deprecated
    static List<String> textEmpty(Set<Entry<String, EditText>> set) {
        List<String> list = new ArrayList<>();
        for (Entry<String, EditText> entry : set) {
            String key = entry.getKey();
            String text = TextUtil.getText(entry.getValue());
            if (TextUtils.isEmpty(text)) {
                list.add(key);
            }
        }
        String text = "";
        for (int i = 0; i < list.size(); i++) {
            text += list.get(i) + ",";
        }
        list.add(text);
        if (TextUtils.isEmpty(text)) {
            list.clear();
        }
        return list;
    }

    /**
     * 懒人toast
     *
     * @param text 内容
     */
    void toast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    String getText(EditText e) {
        return TextUtil.getText(e);
    }

    @Override
    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public abstract void onViewCreated(View view, Bundle savedInstanceState);

    /**
     * 需要插入到数据库的路径,不包括host和project名
     *
     * @return
     */
    abstract String setInsertPath();

    /**
     * 确认提交数据到服务器上面
     *
     * @param member
     */
    private void commit(Member member) {
        String url = LoginActivity.URL_SERVER + setInsertPath();
        HttpJsonRequest request = new HttpJsonRequest(url);
        request.setOnResponseListener(new JsonResponse());
        request.sendRequest(member);

    }

    class JsonResponse extends JsonResponseAdapter<JSONObject> {
        @Override
        public void onStart(int what) {
            Log.i(getClass().getName(), "request start");
        }

        @Override
        public void onSucceed(int what, Response<JSONObject> response) {
            JSONObject jsonObject = response.get();
            if (jsonObject == null) {
                onFailed(what, response);
                return;
            }
            Log.i(getClass().getName(), jsonObject.toJSONString());
            String msg = jsonObject.getString(L.MESSAGE);
            // 如果是插入成功或者更新成功就跳转会主界面
            if (S.INSERT_SUCCESS.equals(msg) || S.UPDATE_SUCCESS.equals(msg)) {
                Intent intent = new Intent(getActivity(), setDispatchClass());
                getActivity().startActivity(intent);
                // 退出任务栈
                getActivity().finish();
                toast(msg);
            } else {
                onFailed(what, response);
            }
        }
    }

    /**
     * 发送请求到服务器上，不过还没有确定
     *
     * @param member
     */
    void pushRequest(final Member member) {
        // 创建对话框
        Builder builder = new Builder(getActivity());
        builder.setTitle(S.ARE_YOU_COMMIT);
        builder.setMessage(JSON.toJSONString(member));
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                commit(member);
            }

        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }

        });
        // show对话框
        builder.create().show();
    }

    /**
     * 需要跳转到的ActivityClass
     *
     * @return
     */
    protected abstract Class<?> setDispatchClass();
}
