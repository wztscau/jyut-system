package com.jyut.system.fragement;

import android.os.Bundle;
import android.view.View;

import com.jyut.system.C;
import com.jyut.system.bean.ClubMember;

/**
 * @author wztscau
 * @date Sep 21, 2016
 * @project 粤盟管理系统客户端
 */
public class ClubtableFragment extends BaseListFragment {


    private static final String TAG = "ClubtableFragment";


    public ClubtableFragment() {
        // 这里getArgument()会为空
    }

    @Override
    protected void init() {

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected String setPath() {
        return C.PATH_SERVER_CLUB_QUERY;
    }

    @Override
    protected String setType() {
        return C.TYPE_CLUB;
    }

    @Override
    protected Class setEntityClass() {
        return ClubMember.class;
    }

    @Override
    protected String setTitle() {
        if (bundle == null) {
            return "全国" + C.S.LIST_OF_COLLAGE_CLUB;
        }
        return bundle.getString(C.L.LOCALE) + C.S.LIST_OF_COLLAGE_CLUB;
    }

    @Override
    protected int setPageSize() {
        return C.PAGE_SIZE_DEFAULT;
    }


}
