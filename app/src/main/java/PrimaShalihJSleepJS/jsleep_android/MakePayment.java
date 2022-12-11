package PrimaShalihJSleepJS.jsleep_android;

import static PrimaShalihJSleepJS.jsleep_android.DetailRoomActivity.temporaryRoom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import PrimaShalihJSleepJS.jsleep_android.model.Payment;
import PrimaShalihJSleepJS.jsleep_android.model.Room;
import PrimaShalihJSleepJS.jsleep_android.request.BaseApiService;
import PrimaShalihJSleepJS.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakePayment extends AppCompatActivity {
    Button payButton;
    TextView hotelName, hotelAddress, name, from, to, totalPrice;
    EditText editStart, editEnd;
    DatePickerDialog datePickerDialogEnd,datePickerDialogStart;
    Context mContext;
    BaseApiService mApiService;
    Payment payment;
    double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        name = findViewById(R.id.namaBuyer);
        name.setText(LoginActivity.loggedAccount.name);
        totalPrice = findViewById(R.id.totalPrice);
        totalPrice.setText(String.valueOf(temporaryRoom.price.price) + " x Night");
        hotelName = findViewById(R.id.hotelNamediPayment);
        hotelAddress = findViewById(R.id.hotelAddressdiPayment);
        hotelName.setText(temporaryRoom.name);
        hotelAddress.setText(temporaryRoom.address);

        payButton = findViewById(R.id.payButtons);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Pay Button Clicked");
                createPayment(LoginActivity.loggedAccount.id,
                        temporaryRoom.accountId,
                        temporaryRoom.id,
                        editStart.getText().toString(),
                        editEnd.getText().toString());
            }

        });

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        datePickerDialogStart = new DatePickerDialog(MakePayment.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        editStart.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, year, month, day);

        datePickerDialogEnd = new DatePickerDialog(MakePayment.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        editEnd.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, year, month, day);

        editStart = findViewById(R.id.chooseStart);
        editEnd = findViewById(R.id.chooseEnd);

        editStart.setOnClickListener(v -> {
            datePickerDialogStart.show();
        });

        editEnd.setOnClickListener(v -> {
            datePickerDialogEnd.show();
        });
    }

    public boolean availability(Date from, Date to, Room room){
        if(from.after(to) || from.equals(to)){
            return false;
        }

        for (Date i : room.booked) {
            if (from.equals(i)) {
                return false;
            } else if(from.before(i)){
                if(from.before(i) && to.after(i)){
                    return false;
                }
            }
        }
        return true;
    }

    protected Payment createPayment(int buyerId, int renterId, int roomId, String from, String to){

        System.out.println("Callback");
        System.out.println(buyerId);
        System.out.println(renterId);
        System.out.println(roomId);
        System.out.println(from);
        System.out.println(to);

        mApiService.createPayment(buyerId, renterId, roomId, from, to).enqueue(new Callback<Payment>(){

            @Override
            public void onResponse(@NonNull Call<Payment> call, @NonNull Response<Payment> response){
                if(response.isSuccessful()){
                    System.out.println("Success");
                    payment = response.body();
                    System.out.println(payment);
                    LoginActivity.loggedAccount.balance -= price;
                    Intent move = new Intent(MakePayment.this,MainActivity.class);
                    startActivity(move);
                    Toast.makeText(mContext, "Payment Created", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Payment> call, @NonNull Throwable t) {
                Toast.makeText(mContext, "Create Payment Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}