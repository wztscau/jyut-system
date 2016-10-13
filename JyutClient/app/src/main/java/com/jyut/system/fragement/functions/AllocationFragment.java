package com.jyut.system.fragement.functions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.jyut.system.C;
import com.jyut.system.R;
import com.jyut.system.bean.DepartmentMember;
import com.jyut.system.fragement.BaseListFragment;
import com.jyut.system.util.TextUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author wztscau
 * @date 10/5/2016
 * @project 粤盟管理系统客户端
 */

public class AllocationFragment extends Fragment {


    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_school)
    EditText etSchool;
    @Bind(R.id.btn_to_admin)
    AppCompatButton toAdmin;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_func_allocation, container, false);

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new ChildFragment());
        transaction.commit();

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        etName.setKeyListener(null);
        etSchool.setKeyListener(null);
    }

    @OnClick(R.id.btn_to_admin)
    public void toAdmin(){
        Toast.makeText(getContext(),"success",Toast.LENGTH_SHORT).show();
    }

    private boolean attempt(){
        if(TextUtil.isEmpty(etName)){
            etName.setError(getResources().getString(R.string.error_name_empty));
            return false;
        }
        if(TextUtil.isEmpty(etSchool)){
            etSchool.setError(getResources().getString(R.string.error_school_empty));
            return false;
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    class ChildFragment extends BaseListFragment {

        @Override
        public void onItemClick(View v, int position) {
            etName.setText(getAdapter().getItemObject(position).getName());
            etSchool.setText(getAdapter().getItemObject(position).getSchool());
        }

        @Override
        protected void init() {

        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            hideToolbar(true);
            hideFloatButton(true);
        }

        @Override
        protected String setPath() {
            return C.PATH_SERVER_DEPT_QUERY;
        }

        @Override
        protected String setType() {
            return "";
        }

        @Override
        protected Class setEntityClass() {
            return DepartmentMember.class;
        }

        @Override
        protected String setTitle() {
            return "功能";
        }

        @Override
        protected int setPageSize() {
            return 10;
        }
    }
}
