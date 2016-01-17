package com.example.theone.virtualbank;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by theone on 17/01/16.
 */
public class GoalsData extends Fragment {
    SessionManager session;
    View rootView;
    ProgressDialog pDialog;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Session manager
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(Goals.goalid);
        pDialog=new ProgressDialog(getActivity());
        session = new SessionManager(getActivity());
        rootView = inflater.inflate(R.layout.goal_scrolling, container, false);

            myfunction();
            showDialog();
            pDialog.setMessage("Loading .....");
            return rootView;



        // return rootView;
    }


    public  void myfunction(){
        String url = "https://api.mongolab.com/api/1/databases/theone/collections/goals?q=%7B%22goal_id%22:%22"+Goals.goalid+"%22%7D&apiKey=jBfxiFnkZdbe04eUUUwsywUkhmSIjS0j";
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
                                abc1.setText(obj.getString("goal_name"));

                                TextView abc2 = (TextView) rootView.findViewById(R.id.tvNumber2);
                                abc2.setText(obj.getString("cost"));
                                TextView abc3 = (TextView) rootView.findViewById(R.id.tvNumber3);
                                abc3.setText(obj.getString("per_month"));

                                TextView abc4 = (TextView) rootView.findViewById(R.id.tvNumber4);
                                abc4.setText(obj.getString("saving"));

                                TextView abc5 = (TextView) rootView.findViewById(R.id.tvNumber5);
                                abc5.setText(obj.getString("created"));
                                TextView abc6 = (TextView) rootView.findViewById(R.id.tvNumber6);
                                abc6.setText(obj.getString("end"));



                            }



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
                Toast.makeText(getActivity(),
                        "Something went wrong ..try again!", Toast.LENGTH_LONG)
                        .show();

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
