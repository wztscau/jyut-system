/**
 *
 */
package com.jyut.system.fragement;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jyut.system.C;
import com.jyut.system.MainActivity;
import com.jyut.system.R;
import com.jyut.system.bean.ClubMember;
import com.jyut.system.bean.User;

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
public class ClubEditorFragment extends EditorFragment {

    private static final String TAG = "ClubEditorFragment";


    private ClubMember member;
    private Map<String, EditText> nameOfEditText = new HashMap<>();
    ViewHolder holder;
    private Bitmap head;

    public ClubEditorFragment() {
        Bundle bundle = getArguments();
        Log.d(TAG, "constructor getArguments: " + bundle);
    }

    @Override
    public void bindMessages() {
        member = new ClubMember();
        member.setSchool(getText(holder.etSchool));
        member.setName(getText(holder.etName));
        member.setTel(getText(holder.etTel));
        member.setLocale(getText(holder.etLocale));
        member.setClub(getText(holder.etClub));
        member.setWechat(getText(holder.etWechat));
        member.setQq(getText(holder.etQq));

        member.setModifier(User.getInstance().getUname());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_club_member, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // 等view创建出来再绑定到holder上面
        holder = new ViewHolder(view);
        // view的一些初始化工作
        initView();
        // 得到传回的是否读写
        type = getArguments().getString(C.TYPE_W_OR_R);
        // 如果是读模式要将数据展示出来
        if (!C.TYPE_WRITE.equals(type)) {
            afterViewCreated();
        }
    }

    private void initView() {
        holder.ibtnAdd.setVisibility(View.GONE);
        holder.ibtnCommit.setVisibility(View.VISIBLE);
        holder.ibtnSearch.setVisibility(View.GONE);
    }

    private void afterViewCreated() {
        try {
            member = getArguments().getParcelable(C.MEMBER);

            holder.etLocale.setText(member.getLocale());
            holder.etSchool.setText(member.getSchool());
            holder.etClub.setText(member.getClub());
            holder.etName.setText(member.getName());
            holder.etTel.setText(member.getTel());
            holder.etQq.setText(member.getQq());
            holder.etWechat.setText(member.getWechat());

            holder.tvModifier.setText(member.getModifier());
        } catch (Exception e) {
            Log.e(TAG, "afterViewCreated: " + e.getMessage());
        }
    }


    @OnClick(R.id.ibtn_commit)
    public void commit() {
        // 先绑定数据
        bindMessages();
        // 再检测是否数据无误
        boolean b = attemptCommit();
        // 如果无误就发送请求到服务器上面
        if (b) {
            pushRequest(member);
        }

    }

    @OnClick(R.id.imv_medium_head)
    public void openImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, C.WHAT_SUCCESS);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult: ");
        if(data==null){
            Log.d(TAG, "onActivityResult: intent is null");
            return;
        }
        Bundle extras = data.getExtras();
        if (extras != null) {
            Log.d(TAG, "onActivityResult: bundle exists");
//            head = (Bitmap) extras.get("data");
            head = extras.getParcelable("data");
            holder.imvMediumHead.setImageBitmap(head);
        }
    }

    private boolean attemptCommit() {
        Resources resources = getResources();
        if (TextUtils.isEmpty(getText(holder.etLocale))) {
            holder.etLocale.setError(resources.getString(R.string.error_locale_empty));
            return false;
        }
        String[] array = resources.getStringArray(R.array.provinces);
        boolean b = true;
        for (int i = 1; i < array.length; i++) {
            b = false;
            if (array[i].substring(3).equals(getText(holder.etLocale))) {
                b = true;
                break;
            }
        }
        if (b == false) {
            holder.etLocale.setError(resources.getString(R.string.error_locale));
            return false;
        }


        if (TextUtils.isEmpty(getText(holder.etSchool))) {
            holder.etSchool.setError(resources.getString(R.string.error_school_empty));
            return false;
        }
        if (TextUtils.isEmpty(getText(holder.etClub))) {
            holder.etClub.setError(resources.getString(R.string.error_club_empty));
            return false;
        }
        if (TextUtils.isEmpty(getText(holder.etName))) {
            holder.etName.setError(resources.getString(R.string.error_majorname_empty));
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
        if (getText(holder.etQq).length() > 12) {
            holder.etQq.setError(resources.getString(R.string.error_qq));
            return false;
        }
        String s = getText(holder.etWechat);
        char[] c = s.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (!(c[0] >= 65 && c[0] <= 90 || c[0] >= 97 && c[0] <= 122 || c[0] == 95)) {
                holder.etWechat.setError(resources.getString(R.string.error_wechat));
                return false;
            }
            if (!(c[i] >= 33 && c[i] < 126)) {
                return false;
            }
        }
        return true;
    }

    @Override
    String setInsertPath() {
        return C.PATH_SERVER_CLUB_INSERT;
    }

    @Override
    protected Class<?> setDispatchClass() {
        return MainActivity.class;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    static class ViewHolder {

        @Bind(R.id.imv_medium_head)
        ImageView imvMediumHead;
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
        @Bind(R.id.et_locale)
        EditText etLocale;
        @Bind(R.id.tid_locale)
        TextInputLayout tidLocale;
        @Bind(R.id.et_school)
        EditText etSchool;
        @Bind(R.id.til_school)
        TextInputLayout tilSchool;
        @Bind(R.id.et_club)
        EditText etClub;
        @Bind(R.id.til_club)
        TextInputLayout tilClub;
        @Bind(R.id.et_name)
        EditText etName;
        @Bind(R.id.til_name)
        TextInputLayout tilName;
        @Bind(R.id.et_tel)
        EditText etTel;
        @Bind(R.id.til_tel)
        TextInputLayout tilTel;
        @Bind(R.id.et_qq)
        EditText etQq;
        @Bind(R.id.til_qq)
        TextInputLayout tilQq;
        @Bind(R.id.et_wechat)
        EditText etWechat;
        @Bind(R.id.til_wechat)
        TextInputLayout tilWechat;
        @Bind(R.id.tv_modifier)
        TextView tvModifier;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
