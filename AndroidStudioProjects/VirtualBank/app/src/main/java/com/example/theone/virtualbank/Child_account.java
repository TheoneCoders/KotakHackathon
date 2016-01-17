package com.example.theone.virtualbank;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

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
public class Child_account extends Fragment {
    private ProgressDialog pDialog;
    GridView gridView;
    ArrayList<Item> gridArray = new ArrayList<Item>();
    GridViewAdapter gridAdapter;
    SessionManager session;
    Bitmap homeIcon;
    ArrayList<String> goal_id;
    View rootView;
    public static String userid;
    @Override
    public View onCreateView(LayoutInflater inflater,
               ViewGroup container, Bundle savedInstanceState) {

         rootView = inflater.inflate(R.layout.child_account, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Child Account");
        // Progress dialog
        session=new SessionManager(getActivity());
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        goal_id=new ArrayList<String>();

        Button add=(Button)rootView.findViewById(R.id.button123);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fr=new AddChild();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.flContent, fr);

                fragmentTransaction.commit();

            }
        });
        //set grid view item

        homeIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.myaccount);
        showDialog();
        myfunction();
        return rootView;
    }

    public  void myfunction(){
        String url = "https://api.mongolab.com/api/1/databases/theone/collections/Users?q=%7B%22parent_account%22:%22"+session.Getusername()+"%22%7D&apiKey=jBfxiFnkZdbe04eUUUwsywUkhmSIjS0j";
// Request a string response
        pDialog.setMessage("Fetching data ......");
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
                                gridArray.add(new Item(homeIcon,obj.getString("username")));
                                goal_id.add(obj.getString("username"));
                            }

                            gridView = (GridView) rootView.findViewById(R.id.gridView1);
                            gridAdapter = new GridViewAdapter(getActivity(), R.layout.row_grid, gridArray);
                            gridView.setAdapter(gridAdapter);
                            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View v,
                                                        final int position, long id) {

                                    // DO something
                                    CharSequence colors[] = new CharSequence[] {"View Profile", "View Goals", "Transaction Summary "};

                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setTitle("Select an action");
                                    builder.setItems(colors, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // the user clicked on colors[which]
                                            if(which==0)
                                            {
                                                userid=goal_id.get(position);
                                                Fragment fr=new ChildProfile();
                                                FragmentManager fm = getFragmentManager();
                                                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                                fragmentTransaction.replace(R.id.flContent, fr);
                                                fragmentTransaction.addToBackStack(null);
                                                fragmentTransaction.commit();

                                                Log.d("boom", "it is clicked congrats which");
                                            }
                                            else if(which==1)
                                            {
                                                userid=goal_id.get(position);
                                                Fragment fr=new Goals();
                                                FragmentManager fm = getFragmentManager();
                                                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                                fragmentTransaction.replace(R.id.flContent, fr);
                                                fragmentTransaction.addToBackStack(null);
                                                fragmentTransaction.commit();

                                                Log.d("boom", "it is clicked congrats which");
                                            }
                                        }
                                    });
                                    builder.show();
                                    Log.d("boom", "it is clicked congrats");

                                }
                            });
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
