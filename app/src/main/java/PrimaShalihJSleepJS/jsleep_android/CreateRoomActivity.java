package PrimaShalihJSleepJS.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.Spinner;
        import android.widget.Toast;

import PrimaShalihJSleepJS.jsleep_android.model.BedType;
        import PrimaShalihJSleepJS.jsleep_android.model.City;
        import PrimaShalihJSleepJS.jsleep_android.model.Facility;
        import PrimaShalihJSleepJS.jsleep_android.model.Room;
        import PrimaShalihJSleepJS.jsleep_android.request.BaseApiService;
        import PrimaShalihJSleepJS.jsleep_android.request.UtilsApi;

        import java.util.ArrayList;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

        /**
         * Created by Prima Shalih on 5/12/2020.
         * @version 1.0
         * @since 1.0
         */

public class CreateRoomActivity extends AppCompatActivity {

    Context mContext;
    BaseApiService mApiService;
    Button buttonCreateRoom, buttonCancelCreateRoom;
    EditText insertNameCreateRoom, insertSizeCreateRoom, insertPriceCreateRoom, insertAddressCreateRoom;
    ArrayList<Facility> facility = new ArrayList<Facility>();
    CheckBox ac, fridge, wifi, bathub, balcony, restaurant, pool, fitness;
    Spinner Spinnercity, Spinnerbedtype;


    /**
     * Method to create menu
     * @param savedInstanceState
     * @return
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApiService = UtilsApi.getApiService();
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        Spinnercity = findViewById(R.id.CitySpinner);
        Spinnerbedtype = findViewById(R.id.BedTypeSpinner);
        buttonCreateRoom = findViewById(R.id.CreateRoomButton);
        buttonCancelCreateRoom = findViewById(R.id.CancelCreateRoomButton);
        ac = findViewById(R.id.checkBoxAC);
        fridge = findViewById(R.id.checkBoxRefrigerator);
        wifi = findViewById(R.id.checkBoxWiFi);
        bathub = findViewById(R.id.checkBoxBathub);
        balcony = findViewById(R.id.checkBoxBalcony);
        restaurant = findViewById(R.id.checkBoxRestaurant);
        pool = findViewById(R.id.checkBoxSwimmingPool);
        fitness = findViewById(R.id.checkBoxFitnessCenter);

        insertNameCreateRoom = findViewById(R.id.InsertNameCreateRoom);
        insertSizeCreateRoom = findViewById(R.id.InsertSizeCreateRoom);
        insertPriceCreateRoom = findViewById(R.id.insertPriceCreateRoom);
        insertAddressCreateRoom = findViewById(R.id.InsertAddressCreateRoom);

        Spinnerbedtype.setAdapter(new ArrayAdapter<BedType>(this, android.R.layout.simple_spinner_item, BedType.values()));
        Spinnercity.setAdapter(new ArrayAdapter<City>(this, android.R.layout.simple_spinner_item, City.values()));

        buttonCreateRoom.setOnClickListener(v -> {

            if (ac.isChecked()) {
                facility.add(Facility.AC);
            }
            if (fridge.isChecked()) {
                facility.add(Facility.Refrigerator);
            }
            if (wifi.isChecked()) {
                facility.add(Facility.WiFi);
            }
            if (bathub.isChecked()) {
                facility.add(Facility.Bathtub);
            }
            if (balcony.isChecked()) {
                facility.add(Facility.Balcony);
            }
            if (restaurant.isChecked()) {
                facility.add(Facility.Restaurant);
            }
            if (pool.isChecked()) {
                facility.add(Facility.SwimmingPool);
            }
            if (fitness.isChecked()) {
                facility.add(Facility.FitnessCenter);
            }
            String bed = Spinnerbedtype.getSelectedItem().toString();
            String cityStr = Spinnercity.getSelectedItem().toString();

            BedType bedType = BedType.valueOf(bed);
            City city = City.valueOf(cityStr);

            String name = insertNameCreateRoom.getText().toString();
            String address = insertAddressCreateRoom.getText().toString();
            int size = Integer.parseInt(insertSizeCreateRoom.getText().toString());
            int price = Integer.parseInt(insertPriceCreateRoom.getText().toString());

            requestRoom(LoginActivity.loggedAccount.id, insertNameCreateRoom.getText().toString(), size,price, facility, city, insertAddressCreateRoom.getText().toString(), bedType);
        });

        /**
         * Method to cancel create room
         * @param v
         * @return
         */
        buttonCancelCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(CreateRoomActivity.this, MainActivity.class);
                startActivity(move);
            }
        });

    }

    /**
     * Method to request room
     * @param id
     * @param name
     * @param size
     * @param price
     * @param facility
     * @param city
     * @param address
     * @param bedType
     * @return
     */
    protected Room requestRoom(int id, String name, int size, int price, ArrayList<Facility> facility, City city, String address, BedType bedType) {
        System.out.println(facility);

        /**
         * Method to create room
         * @param id
         * @param name
         * @param size
         * @param price
         * @param facility
         * @param city
         * @param address
         * @param bedType
         * @return
         */
        mApiService.createRoom(id,name,size,price,facility,city,address, bedType).enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if (response.isSuccessful()) {

                    Room responseq = response.body();
                    System.out.println(responseq.toString());
                    System.out.println("Request Sukses");
                    Toast.makeText(mContext, "Create Room Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateRoomActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            /**
             * Method to show error message
             * @param call
             * @param t
             * @return
             */

            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                System.out.println(name);
                System.out.println(size);
                System.out.println(address);
                System.out.println(price);
                System.out.println(facility);
                System.out.println(city);
                System.out.println(bedType);
                System.out.println(t.toString());
                Toast.makeText(mContext, "Fail To Create Room", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }


}