package eu.theappshop.twolevellist.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.android.databinding.library.baseAdapters.BR;

import java.util.List;

import javax.inject.Inject;

import eu.theappshop.twolevellist.R;
import eu.theappshop.twolevellist.activities.base.BaseActivity;
import eu.theappshop.twolevellist.adapters.LevelAdapter;
import eu.theappshop.twolevellist.data.entity.LevelListEntity;
import eu.theappshop.twolevellist.databinding.ActivityMainBinding;
import eu.theappshop.twolevellist.navigator.MainNavigator;
import eu.theappshop.twolevellist.utils.DividerItemDecoration;
import eu.theappshop.twolevellist.utils.NetworkUtils;
import eu.theappshop.twolevellist.viewmodel.MainViewModel;

/**
 * Created by Fedchuk Maxim on 2018-04-21.
 */

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator, LevelAdapter.OnClickLevelListListner {

    @Inject
    MainViewModel mMainViewModel;
    ActivityMainBinding mActivityMainBinding;

    RecyclerView rvLevel;
    Toolbar toolbar;
    private LevelAdapter adapter;

    public static Intent getIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityMainBinding = getViewDataBinding();
        mMainViewModel.setNavigator(this);
        initToolbar();
        init();
        if (NetworkUtils.isNetworkConnected(this)) {
            getViewModel().getData();
        } else {
            getViewModel().getDataFromBd();
        }
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

    private void initToolbar() {
        toolbar = mActivityMainBinding.toolbarMain.toolbar;
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
    }

    private void init() {
        adapter = new LevelAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        DividerItemDecoration decoration = new DividerItemDecoration();
        rvLevel = mActivityMainBinding.recyclerviewLevel;
        rvLevel.setLayoutManager(manager);
        rvLevel.setAdapter(adapter);
        rvLevel.addItemDecoration(decoration);
        adapter.setOnClickCategoryListener(this);
    }

    @Override
    public void setList(List<LevelListEntity> levelListEntities) {
        adapter.updateList(levelListEntities);
    }

    @Override
    public void adapterAddNewCheck(LevelListEntity levelListEntity) {
        getViewModel().updateListCheck(levelListEntity);
    }

    @Override
    public void adapterUpdateOpen(LevelListEntity levelListEntity) {
        getViewModel().updateListOpen(levelListEntity);
    }
}
