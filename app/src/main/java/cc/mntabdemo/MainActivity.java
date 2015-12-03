package cc.mntabdemo;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import cc.mn.badge.BadgeView;
import cc.mn.tab.TabGroupView;
import cc.mn.tab.TabView;

public class MainActivity extends AppCompatActivity implements TabGroupView.OnItemClickListener{

    private TabGroupView group_tab_layout;
    private TabView tab_main, tab_friedns;
    private BadgeView mBadgeView, badgeView;

    private ViewPager view_pager;

    private List<ContentFragment> list = new ArrayList<>();

    private int mPosition = 0;

    private String tag = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list.add(new ContentFragment("微信"));
        list.add(new ContentFragment("通讯录"));
        list.add(new ContentFragment("发现"));
        list.add(new ContentFragment("我"));

        mBadgeView = new BadgeView(this);
        mBadgeView.setBadgeCount(11);
        mBadgeView.setBadgeGravity(Gravity.RIGHT);
        mBadgeView.setBackground(10, Color.RED);
        mBadgeView.setBadgePadding(2, 0, 2, 0);

        view_pager = (ViewPager)findViewById(R.id.view_pager);
        group_tab_layout = (TabGroupView)findViewById(R.id.group_tab_layout);
        group_tab_layout.setOnItemClickListener(this);

        tab_main = (TabView)findViewById(R.id.tab_main);
        mBadgeView.setTargetView(tab_main.getIconImageView());

        badgeView = new BadgeView(this);
        badgeView.setBadgeWidthAndHeight(30, 30);
        badgeView.setBadgeGravity(Gravity.RIGHT);
        badgeView.setBackground(10, Color.RED);

        tab_friedns = (TabView)findViewById(R.id.tab_friedns);
        badgeView.setTargetView(tab_friedns.getIconImageView());
        badgeView.setMustShow(true); //是不是0或者为空，都必须显示

        view_pager.setAdapter(pagerAdapter);

        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                group_tab_layout.onPageScrolling(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;
                group_tab_layout.setCurrentItem(mPosition);
            }

            @Override
            public void onPageScrollStateChanged(int state) { //状态

            }
        });

    }


    //创建适配器
    private FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    };

    @Override
    public void onClick(int position, TabView tabLayout) {
        Log.i(tag, "选中的tablayout=" + position);
        switch (position){
            case 0:
                mBadgeView.mustHide();
                break;
            case 2:
                badgeView.mustHide();
                break;
        }
        view_pager.setCurrentItem(position, false); //取消动画
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
