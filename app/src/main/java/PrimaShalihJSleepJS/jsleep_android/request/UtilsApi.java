package PrimaShalihJSleepJS.jsleep_android.request;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

/**
 * Created by Prima Shalih on 5/12/2020.
 *
 * @version 1.0
 * @since 1.0
 */
public class UtilsApi {
    public static final String BASE_URL_API = "http://10.0.2.2:8080/";

    /**
     * Method to get BaseApiService
     * @return BaseApiService
     */
    public static BaseApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}