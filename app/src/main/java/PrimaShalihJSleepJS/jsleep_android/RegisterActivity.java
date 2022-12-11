package PrimaShalihJSleepJS.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class RegisterActivity extends AppCompatActivity {

    BaseApiService mApiService;
    Context mContext;
    EditText email, password, name, backButton;
    Button registernow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        name = findViewById(R.id.editTextTextPersonName);
        registernow = findViewById(R.id.registerNowButton);


        registernow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                System.out.println("Halo");
                requestRegister(email.getText().toString(),password.getText().toString(), name.getText().toString());
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.loggedAccount = null;
                Intent move = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(move);
            }
        });

    }


    /**
     * Method to request register
     * @param email
     * @param password
     * @param name
     */
    protected Account requestRegister(String email, String password, String name ){

        mApiService.register(email, password, name).enqueue(new Callback<Account>() {
            /**
             * Method to handle response
             * @param call
             * @param response
             */
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    Account account;
                    account = response.body();
                    System.out.println(account.toString());
                    Intent move = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(move);
                    System.out.println("Ngetes2");
                }
            }
            /**
             * Method to handle failure
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "Account Already Registered", Toast.LENGTH_SHORT).show();
                System.out.println("Ngetes3");
            }
        });
        return null;
    }
}