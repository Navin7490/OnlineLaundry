package navin.laundry.myproject;

import android.content.Context;
import android.content.SharedPreferences;

public class User_login_modal {
    Context context;
    public SharedPreferences sharedPreferencesLogin;
    public User_login_modal(Context context) {
        this.context=context;
        sharedPreferencesLogin=context.getSharedPreferences( "LoginDetail",Context.MODE_PRIVATE );
    }

    public String getLEmail() {
        return LEmail;
    }

    public void setLEmail(String LEmail) {
        this.LEmail = LEmail;
        sharedPreferencesLogin.edit().putString( "LEmail",LEmail ).commit();
    }

    public String getLPassword() {
        return LPassword;
    }

    public void setLPassword(String LPassword) {
        this.LPassword = LPassword;
        sharedPreferencesLogin.edit().putString( "LPassword",LPassword ).commit();
    }
    public void removeuser(){
        sharedPreferencesLogin.edit().clear().commit();
    }

    public String getLimage() {
        return Limage;
    }

    public void setLimage(String limage) {
        Limage = limage;
        sharedPreferencesLogin.edit().putString("LImage",limage).commit();
    }

    private  String Limage,LName, LEmail,LPassword;

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
        sharedPreferencesLogin.edit().putString( "LName",LName ).commit();
    }
}
