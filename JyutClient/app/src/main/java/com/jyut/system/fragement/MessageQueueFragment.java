package com.jyut.system.fragement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jyut.system.C;
import com.jyut.system.R;
import com.jyut.system.adapter.MessageAdapter;
import com.jyut.system.bean.Message;
import com.jyut.system.database.DBHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author wztscau
 * @date Sep 21, 2016
 * @project 粤盟管理系统客户端
 */
public class MessageQueueFragment extends Fragment {

    @Bind(R.id.recycleview)
    RecyclerView recycleview;
    @Bind(R.id.ibtn_add)
    ImageButton ibtnAdd;
    @Bind(R.id.ibtn_commit)
    ImageButton ibtnCommit;
    @Bind(R.id.ibtn_search)
    ImageButton ibtnSearch;
    @Bind(R.id.tv_msg_empty)
    TextView tvMsgEmpty;
    /**
     * 消息列表
     */
    private List<Message> msgList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_message, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // view的初始化工作
        initView();
        // 初始化消息数据
        initData();
        // 创建适配器
        MessageAdapter adapter = new MessageAdapter(msgList);
        recycleview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        // 将数据展现在recyclerview上
        recycleview.setAdapter(adapter);
    }

    /**
     * view的初始化工作
     */
    private void initView() {
        ibtnAdd.setVisibility(View.INVISIBLE);
        ibtnCommit.setVisibility(View.INVISIBLE);
        ibtnSearch.setVisibility(View.INVISIBLE);
        tvMsgEmpty.setVisibility(View.GONE);
    }

    private void initData() {
        tvMsgEmpty.setVisibility(View.GONE);

        DBHelper helper = new DBHelper(getActivity(), C.LOCAL_DATABASE_DEFAULT);
        // 在sqlite上查询所有消息表的数据
        List<Map<String, String>> list = helper.queryAll(C.TABLE_MSG, C.L.DATE);
        // 如果没有消息告知用户
        if (list.isEmpty()) {
            tvMsgEmpty.setVisibility(View.VISIBLE);
            return;
        }
        for (Map<String, String> map : list) {
            Message message = new Message();
            message.setTitle(map.get(C.L.TITLE));
            message.setContent(map.get(C.L.CONTENT));
            message.setReaded(Boolean.valueOf(map.get(C.L.READED)));
            message.setDate(map.get(C.L.DATE));
            msgList.add(message);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
