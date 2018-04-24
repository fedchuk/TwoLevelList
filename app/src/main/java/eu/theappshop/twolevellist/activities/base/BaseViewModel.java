package eu.theappshop.twolevellist.activities.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.widget.Toast;

import eu.theappshop.twolevellist.data.db.AppDatabase;
import eu.theappshop.twolevellist.data.helper.ApiHelper;
import eu.theappshop.twolevellist.utils.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;

/**
 * Created by Fedchuk Maxim on 2018-04-21.
 */

public abstract class BaseViewModel<N> extends AndroidViewModel {

    private final Retrofit mRetrofit;
    private final ApiHelper mApiHelper;
    private final SchedulerProvider mSchedulerProvider;
    private final CompositeDisposable mCompositeDisposable;
    private final AppDatabase mAppDatabase;
    private N mNavigator;

    public BaseViewModel(Retrofit retrofit, SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable, Application application, AppDatabase appDatabase) {
        super(application);
        this.mRetrofit = retrofit;
        this.mSchedulerProvider = schedulerProvider;
        this.mCompositeDisposable = compositeDisposable;
        this.mApiHelper = retrofit.create(ApiHelper.class);
        this.mAppDatabase = appDatabase;
    }

    public void onDestroy() {
        mCompositeDisposable.dispose();
    }

    public N getNavigator() {
        return mNavigator;
    }

    public void setNavigator(N navigator) {
        this.mNavigator = navigator;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    public ApiHelper getApiHelper() {
        return mApiHelper;
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public AppDatabase getAppDatabase() {
        return mAppDatabase;
    }

    public void logMessage(String message) {
        if (getApplication().getApplicationContext() != null) {
            Toast.makeText(getApplication().getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }
}
