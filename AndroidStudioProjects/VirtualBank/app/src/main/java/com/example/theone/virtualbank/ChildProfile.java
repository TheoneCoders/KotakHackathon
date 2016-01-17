
package com.example.theone.virtualbank;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by theone on 16/01/16.
 */
public class ChildProfile extends Fragment {
    SessionManager session;
    View rootView;
    ProgressDialog pDialog;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Session manager
        pDialog=new ProgressDialog(getActivity());
        session = new SessionManager(getActivity());
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Child Profile");
         rootView = inflater.inflate(R.layout.child_profile, container, false);
        if(session.GetChildusername()!=null) {
            TextView abc1 = (TextView) rootView.findViewById(R.id.tvNumber1);
            abc1.setText(session.GetChildusername());

            TextView abc2 = (TextView) rootView.findViewById(R.id.tvNumber2);
            abc2.setText(session.GetChildpass());
            TextView abc3 = (TextView) rootView.findViewById(R.id.tvNumber3);
            abc3.setText(session.GetChildemail());

            TextView abc4 = (TextView) rootView.findViewById(R.id.tvNumber4);
            abc4.setText(session.GetChildparent());

            TextView abc5 = (TextView) rootView.findViewById(R.id.tvNumber5);
            abc5.setText(session.GetChildAccount());
            return rootView;
        }
        else {
            myfunction();
            showDialog();
            pDialog.setMessage("Loading .....");
            return rootView;
        }


       // return rootView;
    }


    public  void myfunction(){
        String url = "https://api.mongolab.com/api/1/databases/theone/collections/Users?q=%7B%22username%22:%22"+Child_account.userid+"%22%7D&apiKey=jBfxiFnkZdbe04eUUUwsywUkhmSIjS0j";
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
                                TextView abc1 = (TextView) rootView.findViewById(R.id.tvNumber1);
                                abc1.setText(obj.getString("username"));

                                TextView abc2 = (TextView) rootView.findViewById(R.id.tvNumber2);
                                abc2.setText(obj.getString("password"));
                                TextView abc3 = (TextView) rootView.findViewById(R.id.tvNumber3);
                                abc3.setText(obj.getString("email"));

                                TextView abc4 = (TextView) rootView.findViewById(R.id.tvNumber4);
                                abc4.setText(obj.getString("parent_account"));

                                TextView abc5 = (TextView) rootView.findViewById(R.id.tvNumber5);
                                abc5.setText(obj.getString("account_no"));

                            }



                        }catch (JSONException e){}



                        // Result handling
                        hideDialog();



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error handling
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
