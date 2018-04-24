package eu.theappshop.twolevellist.di.module;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import eu.theappshop.twolevellist.data.db.AppDatabase;
import eu.theappshop.twolevellist.di.ActivityContext;
import eu.theappshop.twolevellist.di.PerActivity;
import eu.theappshop.twolevellist.utils.AppSchedulerProvider;
import eu.theappshop.twolevellist.utils.SchedulerProvider;
import eu.theappshop.twolevellist.viewmodel.MainViewModel;
import eu.theappshop.twolevellist.viewmodel.SplashViewModel;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;

/**
 * Created by Fedchuk Maxim on 2018-04-23.
 */

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @PerActivity
    MainViewModel provideMainViewModel(Retrofit retrofit, SchedulerProvider schedulerProvider,
                                       CompositeDisposable compositeDisposable, Application application, AppDatabase appDatabase) {
        return new MainViewModel(retrofit, schedulerProvider, compositeDisposable, application, appDatabase);
    }

    @Provides
    @PerActivity
    SplashViewModel provideSplashViewModel(Retrofit retrofit, SchedulerProvider schedulerProvider,
                                           CompositeDisposable compositeDisposable, Application application, AppDatabase appDatabase) {
        return new SplashViewModel(retrofit, schedulerProvider, compositeDisposable, application, appDatabase);
    }
}
