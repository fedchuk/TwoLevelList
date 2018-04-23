package eu.theappshop.twolevellist.activities;

import android.os.Bundle;
import android.os.Handler;

import com.android.databinding.library.baseAdapters.BR;

import javax.inject.Inject;

import eu.theappshop.twolevellist.R;
import eu.theappshop.twolevellist.activities.base.BaseActivity;
import eu.theappshop.twolevellist.databinding.ActivitySplashBinding;
import eu.theappshop.twolevellist.navigator.SplashNavigator;
import eu.theappshop.twolevellist.viewmodel.SplashViewModel;

/**
 * Created by Fedchuk Maxim on 2018-04-21.
 */

public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> implements SplashNavigator {

    @Inject
    SplashViewModel mSplashViewModel;
    ActivitySplashBinding mActivitySplashBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivitySplashBinding = getViewDataBinding();
        mSplashViewModel.setNavigator(this);
        init();
    }

    @Override
    public SplashViewModel getViewModel() {
        return mSplashViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void performDependencyInjection() {
        getActivityComponent().inject(this);
    }

    private void init() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(MainActivity.getIntent(SplashActivity.this));
                finish();
            }
        }, 2000);
    }
}
