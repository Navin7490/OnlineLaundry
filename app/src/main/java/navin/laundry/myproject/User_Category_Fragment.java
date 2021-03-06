package navin.laundry.myproject;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link User_Category_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class User_Category_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String CATEGORY_URL = "https://navindeveloperinfo.000webhostapp.com/laundry_service/api/category.php";
    String MARQUETEXT_URL = "https://navindeveloperinfo.000webhostapp.com/laundry_service/api/marque_title.php";
    String VIEWPAGER_URL = "https://navindeveloperinfo.000webhostapp.com/laundry_service/api/viewpager.php";
    ArrayList<Category_modal> product;
    RecyclerView recyclerView;
    View v;
    Toast toast;
    ImageSlider imageSlider;
    TextView tvmqree;
    Animation aniblanki;
    ShimmerFrameLayout shimmerFrameLayout;
    public User_Category_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment User_Category_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static User_Category_Fragment newInstance(String param1, String param2) {
        User_Category_Fragment fragment = new User_Category_Fragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =inflater.inflate(R.layout.fragment_user__category_, container, false);
        imageSlider = v.findViewById(R.id.imageSlider);

        tvmqree =v. findViewById(R.id.Tvmaruee);
        tvmqree.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvmqree.setSelected(true);
        aniblanki = AnimationUtils.loadAnimation(getActivity(), R.anim.anim);
        tvmqree.setVisibility(View.VISIBLE);


        //Initialize shimmerlayout
        shimmerFrameLayout=v.findViewById(R.id.shimmer_layoutcategory);

        tvmqree.startAnimation(aniblanki);
        recyclerView=v.findViewById(R.id.Rv_UsrCate);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        product=new ArrayList<>();
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
       // progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setContentView(R.layout.progrees_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        recyclerView.setVisibility(View.VISIBLE);

       // start simmer
        shimmerFrameLayout.startShimmer();

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
                        imageSlider.setVisibility(View.VISIBLE);


                    }
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);

                } catch (JSONException ex) {
                    ex.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

            }
        });
        RequestQueue viewpare = Volley.newRequestQueue(getActivity());
        viewpare.add(stringpager);

        // offer start//


        StringRequest stringmarque = new StringRequest(Request.Method.GET, MARQUETEXT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("category_product");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject category = jsonArray.getJSONObject(i);

                        String name = category.getString("m_text");
                        tvmqree.setText(name);
                        tvmqree.setVisibility(View.VISIBLE);


                    }

                } catch (JSONException ex) {
                    ex.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

            }
        });
        RequestQueue mrequset = Volley.newRequestQueue(getActivity());
        mrequset.add(stringmarque);
        //offer end //


        // category start  //
        StringRequest stringRequest = new StringRequest(Request.Method.GET, CATEGORY_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d("categoryresult", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("category_product");


                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject category = jsonArray.getJSONObject(i);
                        String image = category.getString("category_image");
                        String name = category.getString("category_name");

                        Category_modal productModal = new Category_modal();
                        productModal.setImage(image);
                        productModal.setName(name);


                        product.add(productModal);
                        Category_Adapter categoryAdapter = new Category_Adapter(getActivity(), product);
                        recyclerView.setAdapter(categoryAdapter);
                        recyclerView.setVisibility(View.VISIBLE);

                    }

                } catch (JSONException ex) {
                    ex.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                toast = Toast.makeText(getActivity(), "connection fail", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

        return v;
    }
}