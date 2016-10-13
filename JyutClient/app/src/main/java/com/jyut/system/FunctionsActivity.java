package com.jyut.system;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageButton;

import com.jyut.pagerslidingtabstrip_master.PagerSlidingTabStrip;
import com.jyut.system.adapter.PageChangeAdapter;
import com.jyut.system.fragement.functions.AllocationFragment;
import com.jyut.system.fragement.functions.AnnouncementFragment;
import com.jyut.system.fragement.functions.ClubCheckFragment;
import com.jyut.system.fragement.functions.MemberCheckFragment;
import com.jyut.system.fragement.functions.MessageSenderFragment;
import com.jyut.system.fragement.functions.PermissionFragment;
import com.jyut.system.fragement.functions.SchoolFragment;
import com.jyut.system.fragement.functions.TimesFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能页面
 */
public class FunctionsActivity extends FragmentActivity {

    private static final String TAG = "FunctionsActivity";
    @Bind(R.id.ibtn_back)
    ImageButton ibtnBack;
    @Bind(R.id.ibtn_commit)
    ImageButton ibtnCommit;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.tabSliding)
    PagerSlidingTabStrip tabSliding;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
        setContentView(R.layout.activity_functions);
        ButterKnife.bind(this);
        // 为viewpager设置适配器
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        // 为viewpager设置页面滑动监听,并传入各种view
        viewPager.addOnPageChangeListener(new PageChangeAdapter(tabSliding, this,toolbar));
        // 设置viewpager加载的页面最大值
        viewPager.setOffscreenPageLimit(C.VIEWPAGE_LIMIT);
        // 设置viewpager当前的页面
        viewPager.setCurrentItem(getIntent().getIntExtra(C.POSITION,0));
        // 将viewpager放入滑动带中
        tabSliding.setViewPager(viewPager);

    }

    class PagerAdapter extends FragmentPagerAdapter {

        String[] title;
        MessageSenderFragment messageSenderFragment;
        MemberCheckFragment memberCheckFragment;
        AnnouncementFragment announcementFragment;
        AllocationFragment allocationFragment;
        PermissionFragment permissionFragment;
        TimesFragment timesFragment;
        ClubCheckFragment clubCheckFragment;
        SchoolFragment schoolFragment;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
            Log.i(TAG, "PagerAdapter: constructor");
            title = getResources().getStringArray(R.array.functions);
        }

        @Override
        public Fragment getItem(int position) {
            Log.i(TAG, "getItem: fragment" + position);
            switch (position) {
                case 0:
                    return new MessageSenderFragment();
                case 1:
                    return new MemberCheckFragment();
                case 2:
                    return new AnnouncementFragment();
                case 3:
                    return new AllocationFragment();
                case 4:
                    return new PermissionFragment();
                case 5:
                    return new TimesFragment();
                case 6:
                    return new ClubCheckFragment();
                case 7:
                    return new SchoolFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            Log.i(TAG, "getCount: " + title.length);
            return title.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Log.i(TAG, "getPageTitle: " + position);
            return title[position];
        }
    }


    @OnClick(R.id.ibtn_back)
    public void back() {
        onBackPressed();
    }
}
