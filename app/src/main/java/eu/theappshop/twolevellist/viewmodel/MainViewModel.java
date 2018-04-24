package eu.theappshop.twolevellist.viewmodel;


import android.annotation.SuppressLint;
import android.app.Application;
import android.databinding.ObservableField;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import eu.theappshop.twolevellist.activities.base.BaseViewModel;
import eu.theappshop.twolevellist.data.db.AppDatabase;
import eu.theappshop.twolevellist.data.entity.LevelListEntity;
import eu.theappshop.twolevellist.data.model.responce.GetListResponse;
import eu.theappshop.twolevellist.data.model.responce.ListResponce;
import eu.theappshop.twolevellist.navigator.MainNavigator;
import eu.theappshop.twolevellist.utils.SchedulerProvider;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Fedchuk Maxim on 2018-04-23.
 */

public class MainViewModel extends BaseViewModel<MainNavigator> {

    public ObservableField<Boolean> statusProgress = new ObservableField<Boolean>();
    private List<LevelListEntity> mLevelModelList = new ArrayList<>();

    public MainViewModel(Retrofit retrofit, SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable, Application application, AppDatabase appDatabase) {
        super(retrofit, schedulerProvider, compositeDisposable, application, appDatabase);
        statusProgress.set(false);
    }

    public void getData() {
        getAppDatabase().getLevelListDao().deleteListAll();
        statusProgress.set(true);
        getCompositeDisposable().add(getApiHelper().getList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(getListObserver()));
    }

    public DisposableObserver<GetListResponse<ListResponce>> getListObserver() {
        return new DisposableObserver<GetListResponse<ListResponce>>() {
            @Override
            public void onNext(GetListResponse<ListResponce> value) {
                if (value.getData() != null && !value.getData().isEmpty()) {
                    for (int i = 0; i < value.getData().size(); i++) {
                        ListResponce listResponce = value.getData().get(i);
                        if (listResponce != null) {
                            LevelListEntity levelListEntity = new LevelListEntity();
                            levelListEntity.setId(listResponce.getId());
                            levelListEntity.setTitle(listResponce.getName());
                            levelListEntity.setParent(0);
                            levelListEntity.setOpen(false);
                            levelListEntity.setChecks(false);
                            mLevelModelList.add(levelListEntity);
                            if (listResponce.getChildren() != null && !listResponce.getChildren().isEmpty()) {
                                for (int j = 0; j < listResponce.getChildren().size(); j++) {
                                    ListResponce.Child children = listResponce.getChildren().get(j);
                                    if (children != null) {
                                        levelListEntity = new LevelListEntity();
                                        levelListEntity.setId(children.getId());
                                        levelListEntity.setTitle(children.getName());
                                        levelListEntity.setParent(listResponce.getId());
                                        levelListEntity.setOpen(false);
                                        levelListEntity.setChecks(false);
                                        mLevelModelList.add(levelListEntity);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TEST", "error=" + e.getMessage());
                statusProgress.set(false);
            }

            @Override
            public void onComplete() {
                updateBD();
                statusProgress.set(false);
            }
        };
    }

    private void updateBD() {
        getAppDatabase().getLevelListDao().addListAll(mLevelModelList);
        getDataFromBd();
    }

    @SuppressLint("StaticFieldLeak")
    public void getDataFromBd() {
        getAppDatabase().getLevelListDao().getAllList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<LevelListEntity>>() {
                    @Override
                    public void accept(List<LevelListEntity> levelListEntities) throws Exception {
                        if (!levelListEntities.isEmpty()) {
                            getNavigator().setList(levelListEntities);
                        }
                    }
                });
    }

    public void updateListOpen(LevelListEntity levelListEntity) {
        getAppDatabase().getLevelListDao().updateList(levelListEntity);
    }

    public void updateListCheck(LevelListEntity levelListEntity) {
        getAppDatabase().getLevelListDao().clearChecks();
        getAppDatabase().getLevelListDao().updateList(levelListEntity);
    }
}
