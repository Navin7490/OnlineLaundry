package com.example.myproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Admin_Home_Users_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Admin_Home_Users_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    ArrayList<Admin_UserModal>users;
    String VIEW_USERS="http://192.168.43.65/laundry_service/admin_View_Users.php";
    View v;
    String name,email;
    Toast toast;
    public Admin_Home_Users_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Admin_Home_Users_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Admin_Home_Users_Fragment newInstance(String param1, String param2) {
        Admin_Home_Users_Fragment fragment = new Admin_Home_Users_Fragment();
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
        v= inflater.inflate(R.layout.fragment_admin__home__users_, container, false);
        recyclerView=v.findViewById(R.id.Rv_A_User);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        users=new ArrayList<>();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, VIEW_USERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("user_details");

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject user=jsonArray.getJSONObject(i);
                        name=user.getString("u_name");
                        email=user.getString("u_email");

                        Admin_UserModal userModal=new Admin_UserModal();
                        userModal.setName(name);
                        userModal.setEmail(email);
                        users.add(userModal);
                        Admin_UserAdapter adapter=new Admin_UserAdapter(users,getContext());
                        recyclerView.setAdapter(adapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                toast=Toast.makeText(getContext(),"No Connection",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();

            }
        });
        RequestQueue queue= Volley.newRequestQueue(getContext());
        queue.add(stringRequest);
        return v;
    }
}