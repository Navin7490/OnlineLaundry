package com.example.myproject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Admin_Home_category_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Admin_Home_category_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageSlider imageSlider;
    View v;
    String VIEWPAGER_URL="http://192.168.43.65/laundry_service/viewpager.php";
    ProgressDialog progressDialog;
    Context context;
    FloatingActionButton floatingACategory;
    Dialog dialog;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Admin_CategoryModal> product;
    EditText etname;
    CircleImageView categoryImageView;
    Button btncancel,btnAdd;
    private  static final int PICKE_IMAGE=1;
    Uri imageUri;
    Bitmap bitmap;
    String ADD_CATEGORY_URL="http://192.168.43.65/laundry_service/Add_category.php";
    String imagedata;
    String VIEW_CATEGORY="http://192.168.43.65/laundry_service/view_category.php";
    public Admin_Home_category_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Admin_Home_category_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Admin_Home_category_Fragment newInstance(String param1, String param2) {
        Admin_Home_category_Fragment fragment = new Admin_Home_category_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        dialog=new Dialog(getContext());
        dialog.setContentView(R.layout.admin_add_category_dialog);
        dialog.setCanceledOnTouchOutside(false);

        StringRequest stringpager = new StringRequest(Request.Method.GET, VIEWPAGER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("view_pager");
                    List<SlideModel> slideModels = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject category = jsonArray.getJSONObject(i);


                        String image = category.getString("v_image");
                        String name = category.getString("v_title");
                        slideModels.add(new SlideModel(image, name));
                        imageSlider.setImageList(slideModels, true);

                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Connection fail", Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue viewpare = Volley.newRequestQueue(getContext());
        viewpare.add(stringpager);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_admin__home_category_, container, false);
        imageSlider=v.findViewById(R.id.imageSlider);
        floatingACategory=v.findViewById(R.id.Floati_Home_catego);
        recyclerView=v.findViewById(R.id.Rv_Category);
        layoutManager=new GridLayoutManager(getContext(),2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        product=new ArrayList<>();




        StringRequest categoryRequest=new StringRequest(Request.Method.POST, VIEW_CATEGORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("category_product");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject categoryView=jsonArray.getJSONObject(i);

                        String name=categoryView.getString("category_name");
                        String image=categoryView.getString("category_image");
                        Admin_CategoryModal categoryModal=new Admin_CategoryModal();
                        categoryModal.setCatename(name);
                        categoryModal.setCateimage(image);
                        product.add(categoryModal);

                        Admin_CategoryAdapter adapter1=new Admin_CategoryAdapter(getContext(),product);
                        recyclerView.setAdapter(adapter1);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue categoryQue=Volley.newRequestQueue(getContext());
        categoryQue.add(categoryRequest);
        floatingACategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btncancel=dialog.findViewById(R.id.Btn_Cancel);
                btnAdd=dialog.findViewById(R.id.Btn_Add);
                etname=dialog.findViewById(R.id.Et_Category);
                categoryImageView=dialog.findViewById(R.id.Img_category);
                dialog.show();

                categoryImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent gallary=new Intent();
                        gallary.setType("image/*");
                        gallary.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(gallary,"select image"),PICKE_IMAGE);
                    }
                });


                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String etcategory=etname.getText().toString();
                        if (etcategory.isEmpty()){
                            Toast.makeText(getContext(), "Enter Category Name", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            progressDialog.show();
                            StringRequest stringRequest=new StringRequest(Request.Method.POST, ADD_CATEGORY_URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressDialog.dismiss();
                                    try {
                                        JSONObject jsonObject=new JSONObject(response);
                                        String status=jsonObject.getString("status");
                                        if (status.equals("Success")){
                                            Toast.makeText(getContext(), "Category Add Successfully", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                        else if (status.equals("falure")){
                                            Toast.makeText(getContext(), "Category Add Fail ", Toast.LENGTH_SHORT).show();

                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "Connection Fail", Toast.LENGTH_SHORT).show();
                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String,String> parms=new HashMap<>();
                                    imagedata=imageToString(bitmap);
                                    parms.put("category_name",etcategory);
                                    parms.put("category_image",imagedata);

                                    return parms;
                                }
                            };
                            RequestQueue requestQueue=Volley.newRequestQueue(getContext());
                            requestQueue.add(stringRequest);

                        }

                    }
                });
                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });



            }
        });

        return v;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICKE_IMAGE && resultCode==RESULT_OK){
            imageUri=data.getData();
        }
        try {
            bitmap= MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),imageUri);
            categoryImageView.setImageBitmap(bitmap);
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
    private  String imageToString(Bitmap bitmap){

        ByteArrayOutputStream OutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,OutputStream);
        byte[]imagebyte=OutputStream.toByteArray();
        String encodedImage= Base64.encodeToString(imagebyte,Base64.DEFAULT);
        return encodedImage;
    }

}