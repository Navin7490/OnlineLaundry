package navin.laundry.myproject;

import android.content.Context;
import android.content.SharedPreferences;

public class Category_Sharpre_Modal {

    Context context;
    public SharedPreferences sharecategory;

    public Category_Sharpre_Modal(Context context) {
        this.context=context;
        sharecategory=context.getSharedPreferences("Category",Context.MODE_PRIVATE);
    }
    String men;
    String ladies;
    String kids;
    String others;

    public String getMen() {
        return men;
    }

    public void setMen(String men) {
        this.men = men;
        sharecategory.edit().putString("Men_Category",men).commit();
    }

    public String getLadies() {
        return ladies;
    }

    public void setLadies(String ladies) {
        this.ladies = ladies;
        sharecategory.edit().putString("Ladies_Category",ladies).commit();

    }

    public String getKids() {
        return kids;
    }

    public void setKids(String kids) {
        this.kids = kids;
        sharecategory.edit().putString("Kids_Category",kids).commit();

    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
        sharecategory.edit().putString("Others_Category",others).commit();

    }



}
