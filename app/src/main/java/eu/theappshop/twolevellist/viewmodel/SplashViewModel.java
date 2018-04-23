package eu.theappshop.twolevellist.viewmodel;


import android.app.Application;

import eu.theappshop.twolevellist.activities.base.BaseViewModel;
import eu.theappshop.twolevellist.navigator.SplashNavigator;
import eu.theappshop.twolevellist.utils.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;

/**
 * Created by Fedchuk Maxim on 2018-04-23.
 */

public class SplashViewModel extends BaseViewModel<SplashNavigator> {

    public SplashViewModel(Retrofit retrofit, SchedulerProvider schedulerProvider,
                           CompositeDisposable compositeDisposable, Application application) {
        super(retrofit, schedulerProvider, compositeDisposable, application);
    }
}
