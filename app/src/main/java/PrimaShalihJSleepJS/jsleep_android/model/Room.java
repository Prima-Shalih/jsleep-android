package PrimaShalihJSleepJS.jsleep_android.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Prima Shalih on 5/12/2020.
 *
 * @version 1.0
 * @since 1.0
 */

public class Room extends Serializable {
    public int accountId;
    public ArrayList<Date> booked;
    public String name;
    public String address;
    public Price price;
    public City city;
    public int size;
    public BedType bedType;
    public ArrayList<Facility> facility;

    public Room(int id){
        super(id);
    }
}
