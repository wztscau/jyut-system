package com.jyut.system.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.jyut.system.R;
import com.jyut.system.adapter.MemberAdapter.MyViewHolder;
import com.jyut.system.bean.Member;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 成员RecylerView适配器,暂时系统规定两种成员,一种是社团成员,一种是工作部成员
 * @author wztscau
 * @date Sep 22, 2016
 * @project 粤盟管理系统客户端
 */
public class MemberAdapter extends Adapter<MyViewHolder> {

    @Bind(R.id.rel_cardview)
    RelativeLayout relCardview;
    private List<Member> list;
    private OnItemClickListener mListener;

    /**
     * constructor
     * @param list 数据列表,为适配器提供数据
     */
    public MemberAdapter(List<Member> list) {
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
     * @param postion 条目位置
     * @return 特定位置的数据内容
     */
    public Member getItemObject(int postion) {
        return list.get(postion);
    }

    /**
     * 返回adapter里面的数据列表
     * @return 数据列表
     */
    public List<Member> getList(){
        return list;
    }

    /**
     * 将容器里面的view与adapter里面的数据绑定
     * @param holder view的容器
     * @param position index
     */
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // 防止报空指针异常
		if(list.isEmpty()){
            return;
        }
        // 绑定数据
        holder.ivClubHead.setImageResource(R.drawable.image_placeholder);
		holder.tvClubmemberName.setText(list.get(position).getName());
		holder.tvClubSchoolname.setText(list.get(position).getSchool());

        // RecyclerView里面竟然不提供OnItem'Click方法,只能手动实现
        if (mListener != null) {
			holder.cvItemClub.setOnClickListener(new OnClickListener() {
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
        // 1、从布局中获取view
        View view = inflater.inflate(R.layout.item_simple, parent, false);
        // 2、将view绑定到holder上
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
    class MyViewHolder extends ViewHolder {

        @Bind(R.id.iv_club_head)
        ImageView ivClubHead;
        @Bind(R.id.tv_clubmember_name)
        TextView tvClubmemberName;
        @Bind(R.id.tv_club_schoolname)
        TextView tvClubSchoolname;
        @Bind(R.id.cv_item_msg)
        CardView cvItemClub;

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
     * 条目监听器
     * @author wztscau
     * @date Sep 22, 2016
     * @project 粤盟管理系统客户端
     */
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

}
