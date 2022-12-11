package PrimaShalihJSleepJS.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import PrimaShalihJSleepJS.jsleep_android.model.Account;
import PrimaShalihJSleepJS.jsleep_android.request.BaseApiService;
import PrimaShalihJSleepJS.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Prima Shalih on 5/12/2020.
 *
 * @version 1.0
 * @since 1.0
 */

public class LoginActivity extends AppCompatActivity {

    BaseApiService mApiService;
    EditText email, password;
    Context mContext;
    public static Account loggedAccount;

    /**
     * Method to request login
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        email = findViewById(R.id.buatEmail);
        password = findViewById(R.id.buatPassword);
        TextView register = findViewById(R.id.RegisterButton);
        TextView main = findViewById(R.id.tombolLogin);

        /**
         * Method to request login
         * @param View
         */
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(move);
            }
        });

        /**
         * Method to request login
         * @param View
         */
        main.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    System.out.println("Halo");
                  requestLogin(email.getText().toString(),password.getText().toString());
                }
            });
}

    /**
     * Method to request login
     * @param email
     * @param password
     */
    protected Account requestLogin(String email, String password){
        System.out.println("Halo1");
        mApiService.login(email,password).enqueue(new Callback<Account>() {

            /**
             * Method to request login
             * @param call
             * @param response
             */
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    System.out.println("Halo2");
                    Account account;
                    account = response.body();
                    loggedAccount = account;
                    System.out.println(account.toString());
                    Intent move = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(move);
                }
            }

            /**
             * Method to request login
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                System.out.println("Halo3");
                Toast.makeText(mContext, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

    /**
     * Method to request login
     * @param email
     * @param password
     * @param name
     */
    protected Account requestRegister(String email, String password, String name){
        mApiService.register(email,password, name).enqueue(new Callback<Account>() {

            /**
             * Method to request login
             * @param call
             * @param response
             */
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    Account account;
                    account = response.body();
                    loggedAccount = account;
                    System.out.println(account.toString());
                    Intent move = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(move);
                }
            }

            /**
             * Method to request login
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "Register Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}