package com.example.theone.virtualbank;

import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



public class MainActivity extends Activity
{

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnLogin;
    private Button btnLinkToRegister;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);



        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn() && session.GetChildusername()==null) {
            // User is already logged in. Take him to main activity
            Intent i = new Intent(getApplicationContext(),
                    parent_main.class);
            startActivity(i);

            finish();
        }
        else if(session.isLoggedIn() )
        {
            // User is already logged in. Take him to main activity
            Intent i = new Intent(getApplicationContext(),
                    ChildMain.class);
            startActivity(i);

            finish();
        }

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {
                    // login user
                    checkLogin(email, password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }

            }

        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),
                        "Please contact admin rohitchahar4@outlook.com!", Toast.LENGTH_LONG)
                        .show();
            }
        });

    }

    /**
     * function to verify login details in mysql db
     * */
    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request

        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        String url = "https://api.mongolab.com/api/1/databases/theone/collections/Users?q=%7B%22username%22:%22"+email+"%22,%22password%22:%22"+password+"%22%7D&apiKey=jBfxiFnkZdbe04eUUUwsywUkhmSIjS0j";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
        new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                    // Result handling
                    Log.d(TAG, "Login Response: " + response.toString());
                    hideDialog();
                try {
                    JSONArray jarray=new JSONArray(response);

                        JSONObject obj= jarray.getJSONObject(0);

                        if(obj.getString("password")!=null){

                            session.setLogin(true, obj.getString("username"), obj.getString("type"));

                            if(obj.getString("type").equalsIgnoreCase("parent")) {

                                session.setDetails(obj.getString("username"), obj.getString("password"), obj.getString("mobile"), obj.getString("email"), obj.getString("account_no"));
                                Intent i = new Intent(getApplicationContext(),
                                        parent_main.class);
                                startActivity(i);
                            }
                            else if(obj.getString("type").equalsIgnoreCase("child"))
                            {
                                session.setChildDetails(obj.getString("username"), obj.getString("password"), obj.getString("email"), obj.getString("account_no"), obj.getString("parent_account"));
                                Intent i = new Intent(getApplicationContext(),
                                        ChildMain.class);
                                startActivity(i);
                            }

                            // Launch main activity

                            finish();
                        }




                }catch (JSONException e){
                    Toast.makeText(getApplicationContext(),
                            "Wrong credentials!", Toast.LENGTH_LONG)
                            .show();
                }




                    }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                    // Error handling
                hideDialog();
                    System.out.println("Something went wrong!");
                    error.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "Wrong credentials!", Toast.LENGTH_LONG)
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
