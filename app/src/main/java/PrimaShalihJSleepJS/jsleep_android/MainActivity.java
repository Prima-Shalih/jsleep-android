package PrimaShalihJSleepJS.jsleep_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import PrimaShalihJSleepJS.jsleep_android.model.Room;
import PrimaShalihJSleepJS.jsleep_android.request.BaseApiService;
import PrimaShalihJSleepJS.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Prima Shalih on 5/12/2020.
 * @version 1.0
 * @since 1.0
 */
public class MainActivity extends AppCompatActivity {
    Context mContext;
    BaseApiService mApiService;
    ListView listView;
    Button next, prev;
    List<Room> activitylist;
    public static ArrayList<Room> listRoom;
    public static int roomIndex;
    int current;

    /**
     * Method to create menu
     * @param savedInstanceState
     * @return
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        activitylist = getRoomList(current);
        setContentView(R.layout.activity_main);
        next = findViewById(R.id.nextButton);
        prev = findViewById(R.id.prevButton);
        listView = (ListView) findViewById(R.id.iniListViewMainActivity);
        listView.setOnItemClickListener(this::onItemClick);
        getRoomList(0);

        /**
         * Method to go to next page
         */
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current += 1;
                activitylist = getRoomList(current);
            }
        });

        /**
         * Method to go to previous page
         */
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current == 0) {
                    Toast.makeText(mContext, "You are at the first page", Toast.LENGTH_SHORT).show();
                } else {
                    current -= 1;
                    activitylist = getRoomList(current);
                }
            }
        });
    }


    /**
     * Method to create menu
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        /**
         * The constant TAG.
         */

        if (item.getItemId() == R.id.profile_button) {
            Intent move = new Intent(MainActivity.this, AboutMeActivity.class);
            startActivity(move);
            return true;
        } else if(item.getItemId() == R.id.add_button){
            Intent move = new Intent(MainActivity.this, CreateRoomActivity.class);
            startActivity(move);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method to create menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /**
         * The constant TAG.
         */
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.topmenu, menu);
        return true;
    }

    /**
     * Method to get room list
     * @param page
     * @return
     */
    protected List<Room> getRoomList(int page) {
        mApiService.getAllRoom(page, 5).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    activitylist =(response.body());
                    assert activitylist != null;
                    listRoom = new ArrayList<>(activitylist);
                    ArrayList<String> getlist = new ArrayList<>();
                    assert activitylist != null;
                    for (Room room : activitylist){
                        getlist.add(room.name);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, getlist);
                    listView.setAdapter(adapter);
                    System.out.println("Get Room Success!");


                }
            }
            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(mContext, "Get Room Failed!", Toast.LENGTH_SHORT).show();
            }

        });
        return null;
    }

    /**
     * Method to get room list
     * @param l
     * @param v
     * @param position
     * @param id
     * @return
     */

    public void onItemClick (AdapterView<?> l, View v, int position, long id){
        System.out.println("onItemClick Success");
        Log.i("ListView", "You clicked Item np : " + id + " at position:" + position);
        // Then you start a new Activity via Intent
        Intent intent = new Intent();
        roomIndex = position;
        System.out.println("Clicked");

        intent.setClass(mContext, DetailRoomActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}