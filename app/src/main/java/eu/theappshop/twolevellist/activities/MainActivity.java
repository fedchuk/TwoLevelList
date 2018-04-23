package eu.theappshop.twolevellist.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.android.databinding.library.baseAdapters.BR;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import eu.theappshop.twolevellist.R;
import eu.theappshop.twolevellist.activities.base.BaseActivity;
import eu.theappshop.twolevellist.adapters.LevelAdapter;
import eu.theappshop.twolevellist.data.model.FirstLevelModel;
import eu.theappshop.twolevellist.databinding.ActivityMainBinding;
import eu.theappshop.twolevellist.navigator.MainNavigator;
import eu.theappshop.twolevellist.viewmodel.MainViewModel;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator {

    @Inject
    MainViewModel mMainViewModel;
    ActivityMainBinding mActivityMainBinding;

    RecyclerView rvLevel;
    Toolbar toolbar;
    private LevelAdapter adapter;
    private List<FirstLevelModel> mFirstLevelModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityMainBinding = getViewDataBinding();
        mMainViewModel.setNavigator(this);
        initToolbar();
        init();
        getViewModel().updateList();
    }

    @Override
    public MainViewModel getViewModel() {
        return mMainViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void performDependencyInjection() {
        getActivityComponent().inject(this);
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    private void initToolbar() {
        toolbar = mActivityMainBinding.toolbarMain.toolbar;
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
    }

    private void init() {
        mFirstLevelModelList = new ArrayList<>();
        adapter = new LevelAdapter(this, mFirstLevelModelList);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        rvLevel = mActivityMainBinding.recyclerviewLevel;
        rvLevel.setLayoutManager(manager);
        rvLevel.setAdapter(adapter);
    }

    @Override
    public void setList(List<FirstLevelModel> mFirstLevelModelList) {
        adapter.updateList(mFirstLevelModelList);
    }
}
