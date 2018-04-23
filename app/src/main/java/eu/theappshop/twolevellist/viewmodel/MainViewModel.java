package eu.theappshop.twolevellist.viewmodel;


import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import eu.theappshop.twolevellist.activities.base.BaseViewModel;
import eu.theappshop.twolevellist.data.model.FirstLevelModel;
import eu.theappshop.twolevellist.data.model.SecondLevelModel;
import eu.theappshop.twolevellist.data.model.responce.GetListResponse;
import eu.theappshop.twolevellist.data.model.responce.ListResponce;
import eu.theappshop.twolevellist.navigator.MainNavigator;
import eu.theappshop.twolevellist.utils.AppConstants;
import eu.theappshop.twolevellist.utils.SchedulerProvider;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Fedchuk Maxim on 2018-04-23.
 */

public class MainViewModel extends BaseViewModel<MainNavigator> {

    private List<FirstLevelModel> mFirstLevelModelList = new ArrayList<>();

    public MainViewModel(Retrofit retrofit, SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable, Application application) {
        super(retrofit, schedulerProvider, compositeDisposable, application);
    }

    public void updateList() {
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
                        FirstLevelModel firstLevelModel = new FirstLevelModel();
                        ListResponce listResponce = value.getData().get(i);
                        if (listResponce != null) {
                            firstLevelModel.setId(listResponce.getId());
                            firstLevelModel.setTitle(listResponce.getName());
                            firstLevelModel.setOpen(false);
                            if (listResponce.getChildren() != null && !listResponce.getChildren().isEmpty()) {
                                List<SecondLevelModel> secondLevelModelList = new ArrayList<>();
                                for (int j = 0; j < listResponce.getChildren().size(); j++) {
                                    ListResponce.Child child = listResponce.getChildren().get(j);
                                    if (child != null) {
                                        SecondLevelModel secondLevelModel = new SecondLevelModel();
                                        secondLevelModel.setId(child.getId());
                                        secondLevelModel.setTitle(child.getName());
                                        secondLevelModel.setCheck(false);
                                        secondLevelModelList.add(secondLevelModel);
                                    }
                                }
                                firstLevelModel.setChild(secondLevelModelList);
                            }
                        }
                        mFirstLevelModelList.add(firstLevelModel);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TEST", "error=" + e.getMessage());
            }

            @Override
            public void onComplete() {
                getNavigator().setList(mFirstLevelModelList);
            }
        };
    }
}
