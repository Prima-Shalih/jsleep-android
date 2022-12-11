package PrimaShalihJSleepJS.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

import PrimaShalihJSleepJS.jsleep_android.model.Facility;
import PrimaShalihJSleepJS.jsleep_android.model.Room;

/**
 * This class is used to show the detail of the room
 * @author Prima Shalih
 * @version 1.0
 * @since 2020-06-01
 */

public class DetailRoomActivity extends AppCompatActivity {
    public static Room temporaryRoom;
    TextView nameDetail, bedTypeDetail, sizeDetail, priceDetail, addressDetail, cityDetail;
    CheckBox ac, fridge, wifi, bathub, balcony, restaurant, pool, fitness;

    /**
     * This method is used to show the detail of the room
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);
        temporaryRoom = MainActivity.listRoom.get(MainActivity.roomIndex);
        nameDetail = findViewById(R.id.nameOfCustomerDiDetailRoom);
        bedTypeDetail = findViewById(R.id.bedTypePilihanCustomerDiDetailRoom);
        sizeDetail = findViewById(R.id.sizePilihanCustomerDiDetailRoom);
        priceDetail = findViewById(R.id.PricePilihanCustomerDiDetailRoom);
        addressDetail = findViewById(R.id.AddressPilihanCustomerDiDetailRoom);
        cityDetail = findViewById(R.id.CityPilihanCustomerDiDetailRoom);

        nameDetail.setText(temporaryRoom.name);
        priceDetail.setText(String.valueOf(temporaryRoom.price.price));
        sizeDetail.setText(String.valueOf(temporaryRoom.size));
        addressDetail.setText(temporaryRoom.address);
        //  bedTypeDetail.setText(temporaryRoom.bedType.toString());
        cityDetail.setText(temporaryRoom.city.toString());

        ac = findViewById(R.id.checkBoxAC);
        fridge = findViewById(R.id.checkBoxRefrigerator);
        wifi = findViewById(R.id.checkBoxWiFi);
        bathub = findViewById(R.id.checkBoxBathub);
        balcony = findViewById(R.id.checkBoxBalcony);
        restaurant = findViewById(R.id.checkBoxRestaurant);
        pool = findViewById(R.id.checkBoxSwimmingPool);
        fitness = findViewById(R.id.checkBoxFitnessCenter);

        for (int i = 0; i < temporaryRoom.facility.size(); i++) {
            if (temporaryRoom.facility.get(i).equals(Facility.AC)) {
                ac.setChecked(true);
            } else if (temporaryRoom.facility.get(i).equals(Facility.Refrigerator)) {
                fridge.setChecked(true);
            } else if (temporaryRoom.facility.get(i).equals(Facility.WiFi)) {
                wifi.setChecked(true);
            } else if (temporaryRoom.facility.get(i).equals(Facility.Bathtub)) {
                bathub.setChecked(true);
            } else if (temporaryRoom.facility.get(i).equals(Facility.Balcony)) {
                balcony.setChecked(true);
            } else if (temporaryRoom.facility.get(i).equals(Facility.Restaurant)) {
                restaurant.setChecked(true);
            } else if (temporaryRoom.facility.get(i).equals(Facility.SwimmingPool)) {
                pool.setChecked(true);
            } else if (temporaryRoom.facility.get(i).equals(Facility.FitnessCenter)) {
                fitness.setChecked(true);
            }
        }
    }
}