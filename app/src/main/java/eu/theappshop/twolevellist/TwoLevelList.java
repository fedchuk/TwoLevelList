package eu.theappshop.twolevellist;

import android.app.Application;
import android.content.Context;

import eu.theappshop.twolevellist.di.components.ApplicationComponent;
import eu.theappshop.twolevellist.di.components.DaggerApplicationComponent;
import eu.theappshop.twolevellist.di.module.ApplicationModule;
import eu.theappshop.twolevellist.utils.AppConstants;


/**
 * Created by Fedchuk Maxim on 2018-04-23.
 */

public class TwoLevelList extends Application {

    private static ApplicationComponent mApplicationComponent;

    public static Context getContext() {
        return mApplicationComponent.context();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this, AppConstants.BASE_URL)).build();

        mApplicationComponent.inject(this);
    }
}
