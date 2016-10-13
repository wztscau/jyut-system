package com.jyut.system.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jyut.system.R;
import com.jyut.system.adapter.MessageAdapter.MyViewHolder;
import com.jyut.system.bean.Message;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 消息列表模块的适配器,功能尚未完善,只实现从数据库里面得到消息数据
 * @author wztscau
 * @date Sep 22, 2016
 * @project 粤盟管理系统客户端
 */
public class MessageAdapter extends Adapter<MyViewHolder> {


    private List<Message> list;
    private OnItemClickListener mListener;

    /**
     * constructor
     * @param list 数据列表
     */
    public MessageAdapter(List<Message> list) {
        this.list = list;
    }

    /**
     * 返回数据数量
     * @return 数据数量
     */
    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    /**
     * 返回特定位置的数据内容
     * @param position 条目位置
     * @return 特定位置的数据内容
     */
    public Message getItemObject(int position) {
        return list.get(position);
    }

    /**
     * 返回adapter里面的数据列表
     * @return 数据列表
     */
    public List<Message> getList() {
        return list;
    }

    /**
     * 将容器里面的view与adapter里面的数据绑定
     * @param holder view的容器
     * @param position index
     */
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // 防止空指针异常
        if (list.isEmpty()) {
            return;
        }
        // 绑定数据
        holder.tvMsgContent.setText(list.get(position).getContent());
        holder.tvMsgDate.setText(list.get(position).getDate());
        holder.tvMsgTitle.setText(list.get(position).getTitle());
        holder.ivMsgRead.setImageResource(list.get(position).isReaded() ? R.drawable.ic_checkmark_holo_light : R.drawable.ic_menu_help_holo_light);

        // RecyclerView里面竟然不提供OnItem'Click方法,只能手动实现
        if (mListener != null) {
            holder.cvItemMsg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    /**
     * 创建view的容器holder<br>
     * {@inheritDoc}
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_complex, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        ButterKnife.bind(this, view);
        return holder;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    /**
     * @author wztscau
     * @date Sep 22, 2016
     * @project 粤盟管理系统客户端
     */
    class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_msg_read)
        ImageView ivMsgRead;
        @Bind(R.id.tv_msg_date)
        TextView tvMsgDate;
        @Bind(R.id.tv_msg_title)
        TextView tvMsgTitle;
        @Bind(R.id.tv_msg_content)
        TextView tvMsgContent;
        @Bind(R.id.cv_item_msg)
        CardView cvItemMsg;

        /**
         * constructor
         * @param itemView 被绑定的父view
         */
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    /**
     * @author wztscau
     * @date Sep 22, 2016
     * @project 粤盟管理系统客户端
     */
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

}
