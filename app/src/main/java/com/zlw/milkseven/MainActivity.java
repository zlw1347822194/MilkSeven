package com.zlw.milkseven;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.zlw.milkseven.ui.fragment.JokeFragment;
import com.zlw.milkseven.ui.fragment.JournalismFragment;
import com.zlw.milkseven.ui.fragment.MeFragment;
import com.zlw.milkseven.ui.fragment.PicFragment;
import com.zlw.milkseven.ui.widget.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.navigation)
    BottomNavigationViewEx navigation;
    @BindView(R.id.container)
    LinearLayout container;


    //生命fragment对象
    private JokeFragment jokeFragment;
    private JournalismFragment journalismFragment;
    private MeFragment meFragment;
    private PicFragment picFragment;


    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();

    }

    private void initView() {
        //禁用导航栏选中动画
        navigation.enableAnimation(false);
        navigation.enableShiftingMode(false);
        navigation.enableItemShiftingMode(false);
        //添加导航栏的监听器
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //设置默认fragment界面
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        picFragment  = new PicFragment();
        fragmentTransaction.add(R.id.content,picFragment);
        fragmentTransaction.commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    picFragment = new PicFragment();
                    replace(picFragment);
                    return true;
                case R.id.navigation_joke:
                    jokeFragment  = new JokeFragment();
                    replace(jokeFragment);
                    return true;
                case R.id.navigation_journalism:
                    journalismFragment  = new JournalismFragment();
                    replace(journalismFragment);
                    return true;
                case R.id.navigation_me:
                    meFragment  = new MeFragment();
                    replace(meFragment);
                    return true;
            }
            return false;
        }
    };

private void replace(Fragment fragment){
    fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.content,fragment);
    fragmentTransaction.commit();
}
}
