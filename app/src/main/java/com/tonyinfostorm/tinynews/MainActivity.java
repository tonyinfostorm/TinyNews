package com.tonyinfostorm.tinynews;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.tonyinfostorm.tinynews.adapter.HeaderListAdapter;
import com.tonyinfostorm.tinynews.adapter.NewsPageAdapter;
import com.tonyinfostorm.tinynews.fragment.NewsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OperatePagerIntf {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigationView)
    NavigationView navigationView;
    @BindView(R.id.header_list)
    RecyclerView header_list;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(android.R.id.tabs)
    TabLayout tabLayout;


    private ActionBarDrawerToggle drawerToggle;
    private NewsPageAdapter newsPageAdapter;
    private HeaderListAdapter headerListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupToolbar();
        setupToggle();
        setupViewPager();
        setupHeaderList();
    }

    private void setupToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setupToggle() {
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);
//
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(MenuItem item) {
//                drawerLayout.closeDrawers();
//                return true;
//            }
//        });

        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                headerListAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setupViewPager() {
        newsPageAdapter = new NewsPageAdapter(getFragmentManager());
        viewPager.setAdapter(newsPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupHeaderList() {
        headerListAdapter = new HeaderListAdapter(this);
        header_list.setLayoutManager(new GridLayoutManager(this, 1));
        header_list.setAdapter(headerListAdapter);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_refresh:
                newsPageAdapter.notifyDataSetChanged();
                NewsFragment fragment = (NewsFragment) newsPageAdapter.getItem(viewPager.getCurrentItem());
                if (fragment != null) {
                    fragment.loadNews();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public ViewPager getViewPager() {
        return viewPager;
    }

    @Override
    public void closeDrawer() {
        drawerLayout.closeDrawers();
    }
}
