
package com.jyut.system;

import com.jyut.system.bean.ClubMember;
import com.jyut.system.bean.DepartmentMember;
import com.jyut.system.fragement.ClubEditorFragment;
import com.jyut.system.fragement.ClubtableFragment;
import com.jyut.system.fragement.DepartmentEditorFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

/**
 * 编辑界面
 * @author wztscau
 * @date Sep 22, 2016
 * @project 粤盟管理系统客户端
 */
public class EditorActivity extends AppCompatActivity {

    private String type;
    public static final String TAG = "EditorActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        init();
    }

    /**
     * 初始化界面
     */
    private void init() {
        Intent intent = getIntent();
        type = intent.getStringExtra(C.TYPE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        bundle.clear();
        Parcelable parcelable = intent.getParcelableExtra(C.MEMBER);
//        Log.i(TAG, "init: ");
        if (C.TYPE_CLUB.equals(type)) {
            fragment = new ClubEditorFragment();
        }
        if (C.TYPE_DEPT.equals(type)) {
            fragment = new DepartmentEditorFragment();
        }

        if (parcelable != null) {
            bundle.putParcelable(C.MEMBER, parcelable);
        }
        bundle.putString(C.TYPE_W_OR_R, intent.getStringExtra(C.TYPE_W_OR_R));
        fragment.setArguments(bundle);
        transaction.add(R.id.container, fragment);
        // 最后要提交
        transaction.commit();
    }

}
