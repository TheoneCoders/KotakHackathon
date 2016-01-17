package com.example.theone.virtualbank;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

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
public class Goals extends Fragment {
    private ProgressDialog pDialog;
    GridView gridView;
    ArrayList<Item> gridArray = new ArrayList<Item>();
    ArrayList<String> goal_id;
    GridViewAdapter gridAdapter;
    Bitmap homeIcon;
    View rootView;
    String userID;
    SessionManager session;
    public  static  String goalid;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.child_account, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Goals");
        // Progress dialog
        session=new SessionManager(getActivity());
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        Button add=(Button)rootView.findViewById(R.id.button123);
        add.setText("Add New Goal");
        if(session.GetChildusername()!=null)
            userID=session.GetChildusername();
        else {
            userID = Child_account.userid;
            add.setEnabled(false);
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fr=new AddGoal();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.flContent, fr);

                fragmentTransaction.commit();

            }
        });

        //set grid view item
        goal_id=new ArrayList<String>();
        homeIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.photo);
        showDialog();
        myfunction();
        pDialog.setMessage("Loading Goals....");
        return rootView;
    }

    public  void myfunction(){
        String url = "https://api.mongolab.com/api/1/databases/theone/collections/goals?q={%22account_name%22:%22"+userID+"%22}&apiKey=jBfxiFnkZdbe04eUUUwsywUkhmSIjS0j";
// Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        gridArray.clear();
                        try {
                            JSONArray jarray=new JSONArray(response);
                            for(int i=0;i<jarray.length();i++)
                            {
                                JSONObject obj= jarray.getJSONObject(i);
                                gridArray.add(new Item(homeIcon,obj.getString("goal_name")));
                                goal_id.add(obj.getString("goal_id"));
                            }

                            gridView = (GridView) rootView.findViewById(R.id.gridView1);
                            gridAdapter = new GridViewAdapter(getActivity(), R.layout.row_grid, gridArray);
                            gridView.setAdapter(gridAdapter);
                            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View v,
                                                        int position, long id) {

                                    // DO something
                                    goalid=goal_id.get(position);
                                    Fragment fr=new GoalsData();
                                    FragmentManager fm = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                    fragmentTransaction.replace(R.id.flContent, fr);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                    Log.d("boom","it is clicked congrats");

                                }
                            });
                        }catch (JSONException e){}

                        hideDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error handling
                hideDialog();
                Toast.makeText(getActivity(),
                        "Something went wrong ..try again!", Toast.LENGTH_LONG)
                        .show();
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
