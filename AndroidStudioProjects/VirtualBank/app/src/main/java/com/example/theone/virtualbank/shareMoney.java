package com.example.theone.virtualbank;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by theone on 17/01/16.
 */
public class shareMoney extends Fragment {
    ProgressDialog pDialog;
    String user,pass,amount,email,puser,paccount;
    SessionManager session;
    View rootView;
    String ID;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Session manager
        SessionManager session = new SessionManager(getActivity());
        rootView = inflater.inflate(R.layout.account_trans, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Share Money");
        session=new SessionManager(getActivity().getApplicationContext());
        if(session.GetChildusername()!=null)
            ID=session.GetChildusername();
        else
          ID=session.Getusername();




        // Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        Button login=(Button)rootView.findViewById(R.id.btnRegister);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EditText abc2=(EditText)rootView.findViewById(R.id.namep);
                user=abc2.getText().toString().trim();
                EditText abc3=(EditText)rootView.findViewById(R.id.amount);
                pass=abc3.getText().toString().trim();

                if( !user.isEmpty() && !pass.isEmpty() ){
                    register();
                } else
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
            }
        });




        return rootView;
    }

    public void register() {
        pDialog.setMessage("Sharing Money.......");
        showDialog();
        String url = "http://aitplacements.com/code/sharemoney.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Result handling
                        Log.d("response", "Login Response: " + response.toString());
                        if(response.toString().equalsIgnoreCase("no"))
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Invalid Accountant Username", Toast.LENGTH_LONG)
                                .show();
                        else
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Money Share Successfully", Toast.LENGTH_LONG)
                                    .show();
                        hideDialog();




                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error handling
                hideDialog();
                System.out.println("Something went wrong!");
                error.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(),
                        "Something went wrong", Toast.LENGTH_LONG)
                        .show();

            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("user_name", user);
                params.put("cost", pass);
                params.put("user", ID);



                return params;
            }
        };

        Volley.newRequestQueue(getActivity()).add(stringRequest);
        // Volley.
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
