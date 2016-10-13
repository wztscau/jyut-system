package com.jyut.system;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jyut.system.fragement.ClubtableFragment;
import com.jyut.system.fragement.MeFragment;
import com.jyut.system.fragement.MessageQueueFragment;
import com.jyut.system.view.RadioButtonCompat;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.DKGRAY;
import static android.graphics.Color.WHITE;

/**
 * 主界面
 * @author wztscau
 * @date Sep 21, 2016
 * @project 粤盟管理系统客户端
 */
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.rg_tab)
    RadioGroup rgTab;
    public static final String TAG = "MainActivity";
    @Bind(R.id.rb_club)
    RadioButtonCompat rbClub;
    @Bind(R.id.rb_msg)
    RadioButtonCompat rbMsg;
    @Bind(R.id.rb_me)
    RadioButtonCompat rbMe;
    private FragmentManager fManager;
    private ClubtableFragment clubFrag;
    private MessageQueueFragment msgFrag;
    private MeFragment meFrag;
    private int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fManager = getSupportFragmentManager();
        onChangeFragment(R.id.rb_club);
        rgTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                onChangeFragment(checkedId);
            }
        });

    }


    /**
     * 监听底部按钮切换的动作
     *
     * @param checkId
     */
    private void onChangeFragment(int checkId) {
        FragmentTransaction ft = fManager.beginTransaction();
        clubFrag = (ClubtableFragment) fManager.findFragmentByTag(C.TAG_CLUB);
        msgFrag = (MessageQueueFragment) fManager.findFragmentByTag(C.TAG_MSG);
        meFrag = (MeFragment) fManager.findFragmentByTag(C.TAG_ME);

        switch (checkId) {
            case R.id.rb_club:
                if (clubFrag == null) {
                    clubFrag = new ClubtableFragment();
                    ft.add(R.id.frl_main, clubFrag, C.TAG_CLUB);
                } else {
                    ft.show(clubFrag);
                }


                if (meFrag != null) {
                    ft.hide(meFrag);
                }
                if (msgFrag != null) {
                    ft.hide(msgFrag);
                }
                break;
            case R.id.rb_me:
                if (meFrag == null) {
                    meFrag = new MeFragment();
                    ft.add(R.id.frl_main, meFrag, C.TAG_ME);

                } else {
                    ft.show(meFrag);
                }
                if (clubFrag != null) {
                    ft.hide(clubFrag);
                }
                if (msgFrag != null) {
                    ft.hide(msgFrag);
                }
                break;
            case R.id.rb_msg:
                if (msgFrag == null) {
                    msgFrag = new MessageQueueFragment();
                    ft.add(R.id.frl_main, msgFrag, C.TAG_MSG);
                } else {
                    ft.show(msgFrag);
                }
                if (clubFrag != null) {
                    ft.hide(clubFrag);
                }
                if (meFrag != null) {
                    ft.hide(meFrag);
                }


                break;
            default:
                break;
        }
        changeCheckStyle();
        flag = checkId;
        // 最后一定要commit
        ft.commit();
    }

    /**
     * 改变选中与未选中的button样式
     */
    public void changeCheckStyle(){
        rbClub.setCheckDrawableTopAndTextStyle(R.drawable.note_normal,R.drawable.note_discoloration,WHITE, DKGRAY);
        rbMsg.setCheckDrawableTopAndTextStyle(R.drawable.cards_normal,R.drawable.cards_discoloration,WHITE,DKGRAY);
        rbMe.setCheckDrawableTopAndTextStyle(R.drawable.computer_normal,R.drawable.computer_discoloration,WHITE,DKGRAY);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(TAG, "onNewIntent");
        // 对于singletask，onResume方法中得到的intent,但是得不到bundle
        // 因此必须重新为该Activity设置一个新的intent
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        Intent intent = getIntent();
        restoreDataFromSearch(intent);
    }

    private void restoreDataFromSearch(Intent intent) {
        // 对于singletask，onResume方法中得到的intent,但是得不到bundle
        ArrayList<Parcelable> parcelableArrayList = intent.getParcelableArrayListExtra(C.MEMBER);
        if (parcelableArrayList == null) {
            Log.i(TAG, "parcelableArrayList is null");
            return;
        }
        Log.i(TAG, parcelableArrayList.toString());
        switch (flag) {
            case R.id.rb_club:

                Bundle bundle = new Bundle();
                bundle.putString(C.L.LOCALE, intent.getStringExtra(C.L.LOCALE));
                bundle.putParcelableArrayList(C.MEMBER, parcelableArrayList);
                bundle.putSerializable(C.L.QUERY, intent.getSerializableExtra(C.L.QUERY));
                clubFrag.setBundle(bundle);
                /*
                 *  如果用Fragment.setArguments会报Fragment already active错误,所以只能折中用setter和getter方法
                 * clubFrag.setArguments(bundle);
                 * 或者使用静态的newInstance
                 * 如：public static PlusOneFragment newInstance(String param1, String param2) {
                 * PlusOneFragment fragment = new PlusOneFragment();
                 * Bundle args = new Bundle();
                 * args.putString(ARG_PARAM1, param1);
                 * args.putString(ARG_PARAM2, param2);
                 * fragment.setArguments(args);
                 * return fragment;
                 */
                break;
            case R.id.rb_msg:
                break;
            case R.id.rb_me:
                break;
        }
    }

}
