package com.jyut.system.fragement;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jyut.system.C;
import com.jyut.system.EditorActivity;
import com.jyut.system.LoginActivity;
import com.jyut.system.R;
import com.jyut.system.SearchActivity;
import com.jyut.system.adapter.JsonArrayResponseAdapter;
import com.jyut.system.adapter.MemberAdapter;
import com.jyut.system.adapter.MemberAdapter.OnItemClickListener;
import com.jyut.system.bean.Member;
import com.jyut.system.database.DataLoader;
import com.jyut.system.http.HttpJsonRequest;
import com.jyut.system.util.JsonDealer;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import space.sye.z.library.listener.OnLoadMoreListener;
import space.sye.z.library.manager.RecyclerMode;
import space.sye.z.library.manager.RecyclerViewManager;
import space.sye.z.library.manager.RefreshRecyclerAdapterManager;
import space.sye.z.library.widget.RefreshRecyclerView;

/**
 * recyclerview的所在fragment的基类,实现了大部分的方法,
 * 子类只需要实现几个abstract方法即可
 * @author wztscau
 * @date Sep 21, 2016
 * @project 粤盟管理系统客户端
 */
public abstract class BaseListFragment extends Fragment implements OnItemClickListener, OnLoadMoreListener {

    private static final String TAG = "BaseListFragment";
    /**
     * 数据列表
     */
    List<Member> dataList = new ArrayList<>();
    MemberAdapter adapter;
    @Bind(R.id.ibtn_add)
    ImageButton ibtnAdd;
    @Bind(R.id.ibtn_search)
    ImageButton ibtnSearch;
    @Bind(R.id.toolbar)
    RelativeLayout toolbar;
    @Bind(R.id.fab_refresh)
    FloatingActionButton fabFresh;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.loader_progress)
    ContentLoadingProgressBar loaderProgress;
    @Bind(R.id.tv_empty_content)
    TextView tvEmptyContent;
    @Bind(R.id.recycleview)
    RefreshRecyclerView recycleview;
    /**
     * 旋转动画
     */
    private ObjectAnimator rotation;

    Bundle bundle;
    private RefreshRecyclerAdapterManager adapterManager;
    private ArrayList<Parcelable> listParcelable = new ArrayList<>();
    protected DataLoader dataLoader;

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public BaseListFragment() {
    }

    public void hideToolbar(boolean visible) {
        toolbar.setVisibility(visible ? View.GONE : View.VISIBLE);
    }

    public void hideFloatButton(boolean visible) {
        fabFresh.setVisibility(visible ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
        Bundle bundle = getBundle();
        if (bundle == null) {
//            loadData(setPageSize());
//            dataLoader = new DataLoader(LoginActivity.URL_SERVER+setPath(),bundle);
            dataLoader.setOnResponseListener(new BaseJsonResponse());
            dataLoader.setLoadListener(new DataLoader.LoadListener() {
                @Override
                public void onLoadStart(HttpJsonRequest request) {

                }

                @Override
                public void onLoading() {

                }

                @Override
                public void onLoadFinish(Bundle bundle) {
                    setTitle(setTitle());
                }
            });
            dataLoader.loadData(setPageSize());
            return;
        }
        /**
         * 如果bundle存在说明bundle数据来自搜索
         */
        notifyDataFromSearch(bundle);
    }

    private void notifyDataFromSearch(Bundle bundle) {
        tvEmptyContent.setVisibility(View.GONE);
        listParcelable = bundle.getParcelableArrayList(C.MEMBER);
        // 先清空已有的数据
        dataList.clear();
        for (int i = 0; i < listParcelable.size(); i++) {
            Member m = (Member) listParcelable.get(i);
            dataList.add(m);
        }
        Log.i(TAG, "list: " + dataList.toString());
        // 这里会更新数据
        getAdapter().notifyDataSetChanged();
        setTitle(setTitle());
        // 这里要记得更新数据bundle
        setBundle(bundle);
        if (listParcelable.isEmpty()) {
            tvEmptyContent.setVisibility(View.VISIBLE);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "oncreate");
        init();
        dataLoader = new DataLoader(LoginActivity.URL_SERVER + setPath(), bundle);
    }

    protected abstract void init();

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //!!!ע�⣬����inflate�ĵڶ�������������null������ᱨ�ظ������ӿؼ�error
        Log.i(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragement_listview, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.i(TAG, "onViewCreated: ");

//        recycleview.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new MemberAdapter(dataList);
        recycleview.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        // 使用space.sye.z.library.manager包里面的manager很方便
        // 在现有的recyclerview上用manager封装就行
        adapterManager = RecyclerViewManager.with(adapter, new LinearLayoutManager(getContext()))
                .setMode(RecyclerMode.BOTTOM)
//                .addFooterView(footer)
                .setOnLoadMoreListener(this);
        // 最后把recyclerview插入进manager
        adapterManager.into(recycleview, getContext());
    }

    @Override
    public void onLoadMore() {
        Log.i(TAG, "onLoadMore: ");
        // 加载更多的时候不清空列表
        dataLoader.setClearList(false);
        // 加载一定数量的数据
        dataLoader.loadData(setPageSize());

    }


    /**
     * 条目点击事件
     * {@inheritDoc}
     */
    @Override
    public void onItemClick(View v, int position) {
        Intent intent = new Intent(getActivity(), EditorActivity.class);
        Bundle bundle = new Bundle();
//        bundle.putSerializable(C.MEMBER, adapter.getItemObject(position));
        Member member = adapter.getItemObject(position);
//        Log.d(TAG, "onItemClick: member::" + JSON.toJSONString(member));
        bundle.putParcelable(C.MEMBER, member);
        intent.putExtras(bundle);
        intent.putExtra(C.TYPE, setType());
        startActivity(intent);
    }


    public MemberAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 设置url路径,不包括主机名加项目名
     * @return
     */
    protected abstract String setPath();

    @OnClick(R.id.ibtn_add)
    public void add() {
        // 将已得到的数据传至编辑界面
        Intent intent = new Intent(getActivity(), EditorActivity.class);
        intent.putExtra(C.TYPE, setType());
        intent.putExtra(C.TYPE_W_OR_R, C.TYPE_WRITE);
        startActivity(intent);
    }

    @OnClick(R.id.ibtn_search)
    public void search() {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        intent.putExtra(C.TYPE, setType());
        startActivity(intent);
    }

    /**
     * 设置数据bean类型,clubmember departmentmember之类的
     * @return
     */
    protected abstract String setType();

    @OnClick(R.id.fab_refresh)
    public void refresh() {
        // 先清空数据
        dataLoader.setClearList(true);
        // 重新将bundle置为空
        dataLoader.setBundle(null);
        // 开始读取数据
        dataLoader.loadData(setPageSize());
        // 这方法不管用，有没有提供setSelection（）方法
        recycleview.scrollTo(0, 0);

        // 创建旋转动画
        rotation = createRotation();
        // 执行旋转动画
        rotation.start();
    }

    /**
     * 创建旋转动画
     * @return
     */
    private ObjectAnimator createRotation(){
        // ofInt是不行的
        // 360度旋转
        ObjectAnimator rotation = ObjectAnimator.ofFloat(fabFresh, "rotation", 0, 360);
        // 旋转一圈时长1s
        rotation.setDuration(1000);
        // 不停地旋转
        rotation.setRepeatCount(ObjectAnimator.INFINITE);
        // 从初始位置执行下一次的旋转动作
        rotation.setRepeatMode(ObjectAnimator.RESTART);
        // 平滑的执行动画
        rotation.setInterpolator(new LinearInterpolator());
        return rotation;
    }


    protected class BaseJsonResponse extends JsonArrayResponseAdapter {

        @Override
        public void onStart(int what) {
            super.onStart(what);
            showPrgress(true);
        }

        @Override
        public void displayResult(JSONArray jsonArray) {
            // 将回传的JsonArray转成包含parcelable的arraylist
            ArrayList<? extends Parcelable> parcelables = JsonDealer.jsonArrayToBeanList(jsonArray, setEntityClass(), setPageSize());
//            Log.d(TAG, "displayResult: parcelables::"+parcelables);
            if (bundle != null) {
                bundle.putParcelableArrayList(C.MEMBER, listParcelable);
            }

            // 要清除数据时才clear
            if (dataLoader.isClearList()) {
                dataList.clear();
                listParcelable.clear();
            }
            // 在全局的list里面添加刚读取的数据
            listParcelable.addAll(parcelables);
            Log.d(TAG, "displayResult: clearlist--" + dataLoader.isClearList());
            for (int i = 0; i < parcelables.size(); i++) {
                Member m = (Member) parcelables.get(i);
                dataList.add(m);
            }
            // 必须要提醒adapter更新界面
            getAdapter().notifyDataSetChanged();
            // 动画要停止
            if (rotation != null) {
                rotation.cancel();
            }
            // 进度条也要停止
            loaderProgress.setVisibility(View.GONE);
            if (parcelables.isEmpty()) {
//                tvEmptyContent.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "没有更多数据", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailFinal() {
            loaderProgress.setVisibility(View.GONE);
            adapterManager.onRefreshCompleted();
        }

        @Override
        public void onFinish(int what) {
            super.onFinish(what);
            // 最后manager要结束加载更多的view
            adapterManager.onRefreshCompleted();
        }

        @Override
        protected void onInconformityError(String msg, Response<JSONObject> response) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }


    private void showPrgress(boolean b) {
        loaderProgress.setVisibility(b ? View.VISIBLE : View.GONE);
        tvEmptyContent.setVisibility(b ? View.GONE : View.VISIBLE);
    }

    /**
     * 子类必须实现,设置数据种类
     * @return 数据所对应的beanClass
     */
    protected abstract Class setEntityClass();

    /**
     * 设置标题
     * @param title 标题
     */
    private void setTitle(String title) {
        tvTitle.setText(title);
    }

    /**
     * 子类该如何设置标题
     * @return
     */
    protected abstract String setTitle();

    /**
     * 一次读取数据的分页大小
     * @return
     */
    protected abstract int setPageSize();
}
