package project.jerry.snapask.model.request;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import project.jerry.snapask.model.data.ClassData;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Migme_Jerry on 2017/5/7.
 */

public class HttpRequest {

    private static final String BASE_URL = "https://api.myjson.com/bins/";
    private static final int TIMEOUT = 5;

    private static HttpRequest sInstance;
    private Retrofit retrofit;

    public static HttpRequest getInstance() {
        if (sInstance == null) {
            sInstance = new HttpRequest();
        }
        return sInstance;
    }

    private HttpRequest() {
        init();
    }

    private void init() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    // This runs in the back ground and the listener for UI updates will transit work to UI thread
    public void getClassData(Subscriber<HttpResult<List<ClassData>>> subscriber){
        ClassRequestService service;
        service = retrofit.create(ClassRequestService.class);
        service.getClassData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.trampoline())
                .subscribe(subscriber);
    }

}
