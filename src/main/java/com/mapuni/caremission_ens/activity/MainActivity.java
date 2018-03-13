package com.mapuni.caremission_ens.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mapuni.caremission_ens.R;
import com.mapuni.caremission_ens.fragment.BaseFragment;
import com.mapuni.caremission_ens.fragment.MainFragment;
import com.mapuni.caremission_ens.fragment.SupportFragment;

public class MainActivity extends AppCompatActivity {
    BaseFragment currentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            SupportFragment.newInstance().start(getSupportFragmentManager(),
                    R.id.fm_container, MainFragment.newInstance());
        }
    }
    public BaseFragment getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(BaseFragment currentFragment) {
        this.currentFragment = currentFragment;
    }
}
