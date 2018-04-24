/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package eu.theappshop.twolevellist.di.module;

import android.app.Application;
import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import eu.theappshop.twolevellist.data.db.AppDatabase;
import eu.theappshop.twolevellist.di.ApplicationContext;
import eu.theappshop.twolevellist.di.components.AddCookiesInterceptor;
import eu.theappshop.twolevellist.di.components.ReceivedCookiesInterceptor;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Fedchuk Maxim on 2018-04-23.
 */
@Module
public class ApplicationModule {

    private final Application mApplication;
    private final String mBaseUrl;

    public ApplicationModule(Application application, String baseUrl) {
        mApplication = application;
        mBaseUrl = baseUrl;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase() {
        return AppDatabase.getInstance(provideContext());
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    Cache provideHttpCache(Application application) {
        //create cache storage
        File httpCacheDirectory = new File(application.getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        return new Cache(httpCacheDirectory, cacheSize);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient(Cache cache) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new AddCookiesInterceptor(mApplication))
                .addInterceptor(new ReceivedCookiesInterceptor(mApplication))
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .build();
    }
}
