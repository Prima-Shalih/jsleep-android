package PrimaShalihJSleepJS.jsleep_android.request;


import java.util.ArrayList;
import java.util.List;

import PrimaShalihJSleepJS.jsleep_android.model.Account;
import PrimaShalihJSleepJS.jsleep_android.model.BedType;
import PrimaShalihJSleepJS.jsleep_android.model.Payment;
import PrimaShalihJSleepJS.jsleep_android.model.Renter;
import PrimaShalihJSleepJS.jsleep_android.model.Room;
import PrimaShalihJSleepJS.jsleep_android.model.City;
import PrimaShalihJSleepJS.jsleep_android.model.Facility;
import PrimaShalihJSleepJS.jsleep_android.model.City;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Prima Shalih on 5/12/2020.
 * @version 1.0
 * @since 1.0
 */
public interface BaseApiService {
    @GET("account/{id}")
    Call<Account> getAccount (@Path("id") int id);

    @POST("account/login")
    Call<Account> login (@Query("email") String email, @Query("password") String password);

    @POST("account/register")
    Call<Account> register(@Query("email") String email,@Query("password") String password,@Query("name") String name);

    @GET("room/{id}")
    Call<Room> getRoom (@Path("id") int id);

    @POST("account/{id}/topUp")
    Call<Boolean> topUp (@Path("id") int id, @Query("balance") double balance);

    @GET("room/getAllRoom")
    Call<List<Room>> getAllRoom(@Query("page") int page, @Query("pageSize") int pageSize);

    @POST("account/{id}/registerRenter")
    Call<Renter> registerRenter(@Path("id") int id,
                                @Query("username") String username,
                                @Query("address") String address,
                                @Query("phoneNumber") String phoneNumber);

    @POST("room/create")
    Call<Room> createRoom(
            @Query("accountId") int accountId,
            @Query("name") String name,
            @Query("size") int size,
            @Query("price") int price,
            @Query("facility") ArrayList<Facility> facility,
            @Query("city") City city,
            @Query("address") String address,
            @Query("bedType") BedType bedType
    );

    @POST("payment/create")
    Call<Payment> createPayment(@Query("buyerId") int buyerId,
                                @Query("renterId") int renterId,
                                @Query("roomId") int roomId,
                                @Query("from") String from,
                                @Query("to") String to);

    @POST("payment/{id}/accept")
    Call<Boolean> accept(@Path("id") int id);

    @POST("payment/{id}/cancel")
    Call<Boolean> cancel(@Path("id") int id);

}
