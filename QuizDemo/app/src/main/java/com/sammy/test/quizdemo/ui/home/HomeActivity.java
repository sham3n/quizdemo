package com.sammy.test.quizdemo.ui.home;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.sammy.test.quizdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    private static final String FRAGMENT_TAG = "homeFragment";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setupFragment(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    //    getSupportFragmentManager().putFragment(outState, "homefragment", homeFragment);
    }

    private void setupFragment(Bundle savedInstanceState) {

        homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);

        if (null == homeFragment) {
            homeFragment = HomeFragment.newInstance();
            //Add fragment to activity
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.home_fragment_container, homeFragment, FRAGMENT_TAG);
            fragmentTransaction.commit();
        }
    }
}
