/**
 *
 */
package com.jyut.system.fragement;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.jyut.system.C;
import com.jyut.system.DepartmentActivity;
import com.jyut.system.R;
import com.jyut.system.bean.DepartmentMember;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author wztscau
 * @date Sep 28, 2016
 * @project 粤盟管理系统客户端
 */
public class DepartmentEditorFragment extends EditorFragment {

    private static final String TAG = "DepartmentEditor";

    private DepartmentMember member;
    private Map<String, EditText> nameOfEditText = new HashMap<>();
    private ViewHolder holder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_department_member, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        holder = new ViewHolder(view);
        initView();
        type = getArguments().getString(C.TYPE_W_OR_R);
        if (!C.TYPE_WRITE.equals(type)) {
            afterViewCreated();
        }
    }

    private void initView() {
        holder.ibtnSearch.setVisibility(View.GONE);
        holder.ibtnAdd.setVisibility(View.GONE);
        holder.ibtnCommit.setVisibility(View.VISIBLE);
    }

    private void afterViewCreated() {
        try {
            member = getArguments().getParcelable(C.MEMBER);

            holder.etAccount.setText(member.getAccount());
            holder.etSchool.setText(member.getSchool());
            holder.etName.setText(member.getName());
            holder.etTel.setText(member.getTel());
            holder.etWcNickName.setText(member.getNickname());
            holder.spnDeptStatus.setSelection(1 - member.getStatus());
            holder.spnDeptType.setSelection(member.getPermission() - 1);
            holder.spnDeptSex.setSelection(member.getSex().equals("男") ? 0 : 1);

        } catch (Exception e) {
            Log.e(TAG, "afterViewCreated: " + e.getMessage());
        }
    }

    @Override
    public void bindMessages() {
        member = new DepartmentMember();
        member.setAccount(getText(holder.etAccount));
        member.setSchool(getText(holder.etSchool));
        member.setName(getText(holder.etName));
        member.setNickname(getText(holder.etWcNickName));
        member.setTel(getText(holder.etTel));
        member.setSex(holder.spnDeptSex.getSelectedItem().toString());
        // getTransitionName()是没法得到所选中item的名字的
        // member.setSex(spnSex.getTransitionName());
        member.setPermission(1+1 + holder.spnDeptType.getFirstVisiblePosition());
        member.setStatus(1 - holder.spnDeptStatus.getFirstVisiblePosition());
    }

    @OnClick(R.id.ibtn_commit)
    public void commit() {
        // 先绑定数据到bean上面
        bindMessages();
        // 再检测是否数据无误
        boolean b = attemptCommit();
        // 如果无误就发送请求到服务器上面
        if(b){
            pushRequest(member);
        }

    }

    private boolean attemptCommit() {
        Resources resources = getResources();
        if(TextUtils.isEmpty(getText(holder.etName))){
            holder.etName.setError(resources.getString(R.string.error_name_empty));
            return false;
        }
        if (TextUtils.isEmpty(getText(holder.etTel))) {
            holder.etTel.setError(resources.getString(R.string.error_tel_empty));
            return false;
        }
        if (getText(holder.etTel).length() != 11) {
            holder.etTel.setError(resources.getString(R.string.error_tel));
            return false;
        }
        if (TextUtils.isEmpty(getText(holder.etSchool))) {
            holder.etSchool.setError(resources.getString(R.string.error_school_empty));
            return false;
        }
        if(TextUtils.isEmpty(getText(holder.etAccount))){
            holder.etAccount.setError(resources.getString(R.string.error_account_empty));
            return false;
        }
        return true;
    }

    @Override
    protected Class<?> setDispatchClass() {
        return DepartmentActivity.class;
    }

    @Override
    String setInsertPath() {
        return C.PATH_SERVER_DEPT_INSERT;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    static class ViewHolder {
        @Bind(R.id.iv_icon)
        ImageView ivIcon;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.ibtn_add)
        ImageButton ibtnAdd;
        @Bind(R.id.ibtn_commit)
        ImageButton ibtnCommit;
        @Bind(R.id.ibtn_search)
        ImageButton ibtnSearch;
        @Bind(R.id.toolbar)
        RelativeLayout toolbar;
        @Bind(R.id.imv_medium_head)
        ImageView imvMediumHead;
        @Bind(R.id.et_name)
        EditText etName;
        @Bind(R.id.spn_dept_sex)
        AppCompatSpinner spnDeptSex;
        @Bind(R.id.et_tel)
        EditText etTel;
        @Bind(R.id.et_school)
        EditText etSchool;
        @Bind(R.id.et_wc_nickName)
        EditText etWcNickName;
        @Bind(R.id.et_account)
        EditText etAccount;
        @Bind(R.id.spn_dept_type)
        Spinner spnDeptType;
        @Bind(R.id.spn_dept_status)
        Spinner spnDeptStatus;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
