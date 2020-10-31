package navin.laundry.myproject;

import android.content.Context;
import android.content.SharedPreferences;

public class User_Order_Modal {
    Context context;
    public SharedPreferences shareprefmyorder;

    public User_Order_Modal(Context context) {
        this.context = context;
        shareprefmyorder=context.getSharedPreferences("MyOrder",Context.MODE_PRIVATE);
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
        shareprefmyorder.edit().putString("order_date",orderdate).commit();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        shareprefmyorder.edit().putString("user_name",username).commit();

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        shareprefmyorder.edit().putString("email",email).commit();

    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
        shareprefmyorder.edit().putString("mobile",mobile).commit();

    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
        shareprefmyorder.edit().putString("items",items).commit();

    }

    public String getItemprice() {
        return itemprice;
    }

    public void setItemprice(String itemprice) {
        this.itemprice = itemprice;
        shareprefmyorder.edit().putString("item_price",itemprice).commit();

    }

    public String getItemqty() {
        return itemqty;
    }

    public void setItemqty(String itemqty) {
        this.itemqty = itemqty;
        shareprefmyorder.edit().putString("item_qty",itemqty).commit();

    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
        shareprefmyorder.edit().putString("total_price",totalprice).commit();

    }

    public String getPickupdate() {
        return pickupdate;
    }

    public void setPickupdate(String pickupdate) {
        this.pickupdate = pickupdate;
        shareprefmyorder.edit().putString("pickup_date",pickupdate).commit();

    }

    public String getPickuptime() {
        return pickuptime;
    }

    public void setPickuptime(String pickuptime) {
        this.pickuptime = pickuptime;
        shareprefmyorder.edit().putString("pickup_time",pickuptime).commit();

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        shareprefmyorder.edit().putString("status",status).commit();

    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        shareprefmyorder.edit().putString("address",address).commit();

    }

    String address;
    String items;
    String itemprice;
    String itemqty;
    String totalprice;
    String pickupdate;
    String pickuptime;
    String status;

    String orderdate;
    String username;
    String email;
    String mobile;

    public String getMobileoptional() {
        return mobileoptional;
    }

    public void setMobileoptional(String mobileoptional) {
        this.mobileoptional = mobileoptional;
        shareprefmyorder.edit().putString("mobile2",mobileoptional).commit();

    }

    String mobileoptional;

}
