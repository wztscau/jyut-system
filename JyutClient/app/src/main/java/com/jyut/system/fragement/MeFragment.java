package com.jyut.system.fragement;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jyut.system.C;
import com.jyut.system.DepartmentActivity;
import com.jyut.system.FunctionsActivity;
import com.jyut.system.R;
import com.jyut.system.bean.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * “我”的界面
 * @author wztscau
 * @date Sep 21, 2016
 * @project 粤盟管理系统客户端
 */
public class MeFragment extends Fragment {


    private static final String TAG ="MeFragment" ;
    private ViewHolder holder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        holder = new ViewHolder(view);
        initView();
    }

    /**
     *  view的初始化处理
     */
    private void initView(){
        holder.ibtnAdd.setVisibility(View.INVISIBLE);
        holder.ibtnSearch.setVisibility(View.INVISIBLE);
        holder.tvMeName.setText(User.getInstance().getUname());
        String[] types = getResources().getStringArray(R.array.perm_depts);
        try{
            int type = Integer.parseInt(User.getInstance().getPermission());
            Log.d(TAG, "onViewCreated: type--"+type);
            holder.tvMeType.setText(types[type]);
        }catch(Exception e){
            Log.e(TAG, "onViewCreated: " + e.getMessage());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 每个按钮的处理方式<br>
     * {@inheritDoc}
     */
    @OnClick({R.id.grid_item_msg, R.id.grid_item_membercheck, R.id.grid_item_anno, R.id.grid_item_alloc, R.id.grid_item_worker, R.id.grid_item_perm, R.id.grid_item_addcount, R.id.grid_item_check, R.id.grid_item_school})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.grid_item_worker:
                intent.setClass(getActivity(), DepartmentActivity.class);
                break;
            case R.id.grid_item_msg:
                intent.setClass(getActivity(), FunctionsActivity.class);
                intent.putExtra(C.POSITION, 0);
                break;
            case R.id.grid_item_membercheck:
                intent.setClass(getActivity(), FunctionsActivity.class);
                intent.putExtra(C.POSITION, 1);
                break;
            case R.id.grid_item_anno:
                intent.setClass(getActivity(), FunctionsActivity.class);
                intent.putExtra(C.POSITION, 2);
                break;
            case R.id.grid_item_alloc:
                intent.setClass(getActivity(), FunctionsActivity.class);
                intent.putExtra(C.POSITION, 3);
                break;
            case R.id.grid_item_perm:
                intent.setClass(getActivity(), FunctionsActivity.class);
                intent.putExtra(C.POSITION, 4);
                break;
            case R.id.grid_item_addcount:
                intent.setClass(getActivity(), FunctionsActivity.class);
                intent.putExtra(C.POSITION, 5);
                break;
            case R.id.grid_item_check:
                intent.setClass(getActivity(), FunctionsActivity.class);
                intent.putExtra(C.POSITION, 6);
                break;
            case R.id.grid_item_school:
                intent.setClass(getActivity(), FunctionsActivity.class);
                intent.putExtra(C.POSITION, 7);
                break;
        }
        startActivity(intent);
    }

    static class ViewHolder {
        @Bind(R.id.iv_icon)
        ImageView ivIcon;
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
        @Bind(R.id.tv_me_name)
        TextView tvMeName;
        @Bind(R.id.tv_me_type)
        TextView tvMeType;
        @Bind(R.id.rel_me_head)
        RelativeLayout relMeHead;
        @Bind(R.id.grid_item_msg)
        TextView gridItemMsg;
        @Bind(R.id.grid_item_membercheck)
        TextView gridItemClubtable;
        @Bind(R.id.grid_item_anno)
        TextView gridItemAnno;
        @Bind(R.id.grid_item_alloc)
        TextView gridItemAlloc;
        @Bind(R.id.grid_item_worker)
        TextView gridItemWorker;
        @Bind(R.id.grid_item_perm)
        TextView gridItemPerm;
        @Bind(R.id.grid_item_addcount)
        TextView gridItemAddcount;
        @Bind(R.id.grid_item_check)
        TextView gridItemCheck;
        @Bind(R.id.grid_item_school)
        TextView gridItemSchool;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
