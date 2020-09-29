package navin.laundry.myproject;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import navin.laundry.myproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Admin_Home_Order_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Admin_Home_Order_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    View v;
    ArrayList<Admin_ViewOrderModal> product;
    ProgressDialog progressDialog;
    String LISTORDER_URL="https://navindeveloperinfo.000webhostapp.com/laundry_service/view_order.php";
   // String LISTORDER_URL="http://192.168.43.65/laundry_service/view_order.php";
    public Admin_Home_Order_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Admin_Home_Order_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Admin_Home_Order_Fragment newInstance(String param1, String param2) {
        Admin_Home_Order_Fragment fragment = new Admin_Home_Order_Fragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_admin__home__order_, container, false);
        recyclerView=v.findViewById(R.id.Rv_ListOrder);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        product=new ArrayList<>();


        StringRequest stringRequest=new StringRequest(Request.Method.POST, LISTORDER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("respons",response);
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("order_detail");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject orderdetail=jsonArray.getJSONObject(i);
                        String oderId=orderdetail.getString("order_id");
                        String oderDate=orderdetail.getString("order_date");
                        String cname=orderdetail.getString("username");
                        String cstatus=orderdetail.getString("status");

                        Admin_ViewOrderModal listOrderModal=new Admin_ViewOrderModal();
                        listOrderModal.setOrderId(oderId);
                        listOrderModal.setOrderdate(oderDate);
                        listOrderModal.setCname(cname);
                        listOrderModal.setCstatus(cstatus);
                        product.add(listOrderModal);
                        Admin_ViewOrderAdapter adapter=new Admin_ViewOrderAdapter(getActivity(),product);
                        recyclerView.setAdapter(adapter);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Connection fail", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
        return v;


    }
}