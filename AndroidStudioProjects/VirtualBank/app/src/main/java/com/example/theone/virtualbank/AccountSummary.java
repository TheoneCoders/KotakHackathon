package com.example.theone.virtualbank;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by theone on 16/01/16.
 */
public class AccountSummary extends Fragment{

    private ProgressDialog pDialog;
    ListView gridView;
    SessionManager session;
    ArrayList<account_item> gridArray = new ArrayList<account_item>();
    AccountAdapter gridAdapter;
    Bitmap homeIcon;
    String id;
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.account_summary, container, false);
        // Progress dialog
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Account Summary");
        session=new SessionManager(getActivity());
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        //set grid view item
        pDialog.setMessage("Data Loading..... ");
        showDialog();

        if(session.GetChildusername()!=null)
            id=session.GetChildusername();
        else
            id=session.Getusername();

        myfunction();
        return rootView;
    }

    public  void myfunction(){
        String url = "https://api.mongolab.com/api/1/databases/theone/collections/account?q={%22account_name%22:%22"+id+"%22}&apiKey=jBfxiFnkZdbe04eUUUwsywUkhmSIjS0j";
// Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jarray=new JSONArray(response);
                            for(int i=0;i<jarray.length();i++)
                            {
                                JSONObject obj= jarray.getJSONObject(i);
                                gridArray.add(new account_item(obj.getString("desc"),"$"+obj.getString("amount"),obj.getString("date")));

                            }


                            gridView = (ListView) rootView.findViewById(R.id.listView);
                            gridAdapter = new AccountAdapter(getActivity(), R.layout.account_item, gridArray);
                            gridView.setAdapter(gridAdapter);
                        }catch (JSONException e){}




                        // Result handling
                        hideDialog();



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error handling
                hideDialog();
                System.out.println("Something went wrong!");
                error.printStackTrace();

            }
        });

// Add the request to the queue
        // Volley.newRequestQueue(this).add(stringRequest);
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);


    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
