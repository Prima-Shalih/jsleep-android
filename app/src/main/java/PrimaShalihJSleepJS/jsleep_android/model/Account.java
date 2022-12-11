package PrimaShalihJSleepJS.jsleep_android.model;

import retrofit2.Callback;

/**
 * Created by Prima Shalih on 5/12/2020.
 * @version 1.0
 * @since 1.0
 */

public class Account extends Serializable{
    public double balance;
    public String email;
    public String name;
    public String password;
    public Renter renter;

    /**
     * Constructor
     */
    @Override
    public String toString(){
        return "Account{" +
                "Balance=" + balance +
                ", Email="+email+'\''+
                ", name='"+name+'\''+
                ", Password='"+password+'\''+
                ", renter=" + renter +
                '}';
    }

    /**
     * Constructor
     * @param id
     */
    public Account(int id) {
        super(id);
    }
}