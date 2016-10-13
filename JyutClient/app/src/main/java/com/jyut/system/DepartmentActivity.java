/**
 * @date Sep 22, 2016
 * @author wztscau
 * @project ���˹���ϵͳ
 */
package com.jyut.system;

import com.jyut.system.fragement.DepartmentFragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

/**
 * @author wztscau
 * @date Sep 22, 2016
 * @project 粤盟管理系统客户端
 */
public class DepartmentActivity extends AppCompatActivity {

    private static final String TAG = "DepartmentActivity";
    private DepartmentFragment deptFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_blank);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        deptFrag = new DepartmentFragment();
        transaction.add(R.id.container, deptFrag);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        restoreDataFromSearch(intent);

    }

    private void restoreDataFromSearch(Intent intent) {
        ArrayList<Parcelable> parcelableArrayList = intent.getParcelableArrayListExtra(C.MEMBER);
        if (parcelableArrayList == null) {
            Log.i(TAG, "parcelableArrayList is null");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(C.L.LOCALE, intent.getStringExtra(C.L.LOCALE));
        bundle.putParcelableArrayList(C.MEMBER, parcelableArrayList);
        bundle.putSerializable(C.L.QUERY,intent.getSerializableExtra(C.L.QUERY));
        deptFrag.setBundle(bundle);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }


}
