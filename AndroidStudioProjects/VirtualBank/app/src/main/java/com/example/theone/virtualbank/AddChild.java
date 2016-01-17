package com.example.theone.virtualbank;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by theone on 17/01/16.
 */
public class AddChild extends Fragment {

    ProgressDialog pDialog;
    String user,pass,amount,email,puser,paccount;
    SessionManager session;
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Session manager
        SessionManager session = new SessionManager(getActivity());
       rootView = inflater.inflate(R.layout.register_child, container, false);

        session=new SessionManager(getActivity().getApplicationContext());
        puser=session.Getusername();
        paccount=session.GetAccount();

        Log.d("parent _deatisl","rohit:  ...."+amount+user);

        // Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        Button login=(Button)rootView.findViewById(R.id.btnRegister);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    EditText abc1=(EditText)rootView.findViewById(R.id.amountp);
                    amount=abc1.getText().toString().trim();
                    EditText abc2=(EditText)rootView.findViewById(R.id.namep);
                    user=abc2.getText().toString().trim();
                    EditText abc3=(EditText)rootView.findViewById(R.id.passwordp);
                    pass=abc3.getText().toString().trim();
                    EditText abc4=(EditText)rootView.findViewById(R.id.emailp);
                    email=abc4.getText().toString().trim();
                   if( !user.isEmpty() && !pass.isEmpty() && !amount.isEmpty() && !email.isEmpty()){
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
        pDialog.setMessage("Registering........");
        showDialog();
        String url = "http://aitplacements.com/code/register.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Result handling
                        Log.d("response", "Login Response: " + response.toString());
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Child Registered successfully", Toast.LENGTH_LONG)
                                .show();
                        hideDialog();
                        if(!response.isEmpty()){
                            getFragmentManager().popBackStack();

                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error handling
                hideDialog();
                System.out.println("Something went wrong!");
                error.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(),
                        "Wrong credentials!", Toast.LENGTH_LONG)
                        .show();

            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("username", user);
                params.put("password", pass);
                params.put("amount", amount);
                params.put("email", email);
                params.put("parent_id", puser);
                params.put("account_no", paccount);

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
