package PrimaShalihJSleepJS.jsleep_android.request;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Prima Shalih on 5/12/2020.
 * @version 1.0
 * @since 1.0
 */
public class RetrofitClient {
    private static Retrofit retrofit = null;

    /**
     * Method to get RetrofitClient
     * @param baseUrl
     */

    public  static Retrofit getClient(String baseUrl){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
