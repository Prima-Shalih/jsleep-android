package PrimaShalihJSleepJS.jsleep_android;

import static PrimaShalihJSleepJS.jsleep_android.LoginActivity.loggedAccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import PrimaShalihJSleepJS.jsleep_android.model.Account;
import PrimaShalihJSleepJS.jsleep_android.model.Renter;
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

public class AboutMeActivity extends AppCompatActivity {

    TextView namaKostumer, emailKostumer, saldoKostumer, customerName, customerPhoneNumber, customerAddress, topup, logOutButton;
    CardView cardview1, cardview2;
    Button registerRenterButt, renterCancelButt, registerRenterRegisterButt, topUpButton;
    BaseApiService mApiService;
    EditText registPhoneNumber, registCustomerName, registAddressName, balance, inputAmount;
    Context mContext;
    Double inputAmountDouble;

    /**
     * Method to request login
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        mContext = (AboutMeActivity.this);
        mApiService = UtilsApi.getApiService();
        System.out.println(LoginActivity.loggedAccount);
        namaKostumer = findViewById(R.id.nameOfCustomer);
        emailKostumer = findViewById(R.id.emailOfCustomer);
        saldoKostumer = findViewById(R.id.balanceOfCustomer);
        namaKostumer.setText(LoginActivity.loggedAccount.name);
        emailKostumer.setText(LoginActivity.loggedAccount.email);
        String balance = Double.toString(LoginActivity.loggedAccount.balance);
        saldoKostumer.setText(balance);
        topUpButton = findViewById(R.id.topUpButton);
        topup = findViewById(R.id.balanceOfCustomer);
        inputAmount = findViewById(R.id.plain_text_input_amount);
        logOutButton = findViewById(R.id.LogOutButton);
        registCustomerName = findViewById(R.id.registerRenterInsertName);
        registPhoneNumber = findViewById(R.id.registerRenterInsertPhoneNumber);
        registAddressName = findViewById(R.id.registerRenterInsertAddress);
        customerName = findViewById(R.id.DataOfCustomerNameOfRegisterRenter);
        customerPhoneNumber = findViewById(R.id.DataOfPhoneNumberOfRegisterRenter);
        customerAddress = findViewById(R.id.DataOfAddressOfRegisterRenter);
        registerRenterButt = findViewById(R.id.registerRenterButton);
        renterCancelButt = findViewById(R.id.RegisterRenterCancelButton);
        cardview1 = findViewById(R.id.RegisterCardView);
        cardview2 = findViewById(R.id.DataOfRegisterRenter);

        registerRenterRegisterButt = findViewById(R.id.RegisterRenterRegisterButton);

        /**
         * Method to request register
         * @param View
         */
        registerRenterButt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                registerRenterButt.setVisibility(View.GONE);
                cardview1.setVisibility(View.VISIBLE);
                cardview2.setVisibility(View.GONE);
            }
        });

        /**
         * Method to request cancel
         * @param View
         */
        renterCancelButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerRenterButt.setVisibility(View.VISIBLE);
                cardview1.setVisibility(View.GONE);
                cardview2.setVisibility(View.GONE);
            }
        });

        topUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                inputAmountDouble = Double.parseDouble(inputAmount.getText().toString());
                TopUp(LoginActivity.loggedAccount.id, inputAmountDouble);
            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.loggedAccount = null;
                Intent move = new Intent(AboutMeActivity.this, LoginActivity.class);
                startActivity(move);
                finish();
            }
        });

        if(LoginActivity.loggedAccount.renter == null){
            registerRenterButt.setVisibility(View.VISIBLE);
            cardview1.setVisibility(View.GONE);
            cardview2.setVisibility(View.GONE);
            registerRenterRegisterButt.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Renter accountRenter = registerRenter();
                    registerRenterButt.setVisibility(View.GONE);
                    cardview1.setVisibility(View.GONE);
                    cardview2.setVisibility(View.VISIBLE);
                }
            });

            /**
             * Method to request register
             * @param View
             */
            renterCancelButt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    registerRenterButt.setVisibility(View.VISIBLE);
                    cardview1.setVisibility(View.GONE);
                    cardview2.setVisibility(View.GONE);
                }
            });

        } else {
            registerRenterButt.setVisibility(View.GONE);
            cardview1.setVisibility(View.GONE);
            cardview2.setVisibility(View.VISIBLE);
            customerName.setText(LoginActivity.loggedAccount.renter.username);
            customerPhoneNumber.setText(LoginActivity.loggedAccount.renter.address);
            customerAddress.setText(LoginActivity.loggedAccount.renter.phoneNumber);


        }
    }

    /**
     * Method to request register
     */
    protected Renter registerRenter(){
        System.out.println(LoginActivity.loggedAccount.id);
        System.out.println(registCustomerName.getText().toString());
        System.out.println(registPhoneNumber.getText().toString());
        System.out.println(registAddressName.getText().toString());
        mApiService.registerRenter(
                LoginActivity.loggedAccount.id,
                registCustomerName.getText().toString(),
                registPhoneNumber.getText().toString(),
                registAddressName.getText().toString()).enqueue(new Callback<Renter>() {

            /**
             * Method to request register
             * @param call
             * @param response
             */
            @Override
            public void onResponse(Call<Renter> call, Response<Renter> response) {
                if(response.isSuccessful()){
                    System.out.println("Register Renter Success!");
                    LoginActivity.loggedAccount.renter = response.body();
                    Toast.makeText(mContext, "Register Renter Success!", Toast.LENGTH_SHORT).show();
                    recreate();
                }
            }

            /**
             * Method to request register
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<Renter> call, Throwable t) {
                Toast.makeText(mContext, "Register Renter Failed!", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

    /**
     * Method to request register
     * @param menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(LoginActivity.loggedAccount.renter != null){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.topmenu, menu);
            return true;
        }
        return false;
    }

    /**
     * Method to request register
     * @param item
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (item.getItemId() == R.id.add_button){
            Intent move = new Intent(AboutMeActivity.this, CreateRoomActivity.class);
            startActivity(move);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method to request register
     * @param id
     * @param balance
     */
    protected Renter TopUp(int id, double balance){
        mApiService.topUp(id,balance).enqueue(new Callback<Boolean>(){
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    Toast.makeText(mContext, "Top Up Success", Toast.LENGTH_SHORT).show();
                    LoginActivity.loggedAccount.balance = LoginActivity.loggedAccount.balance + balance;
                    recreate();
                }
            }

            /**
             * Method to request register
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(mContext, "Top Up Fail", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}