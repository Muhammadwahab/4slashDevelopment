package com.example.smart_will;


        import android.app.Activity;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.Toolbar;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.android.volley.AuthFailureError;
        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;

        import java.util.HashMap;
        import java.util.Map;

public class logina1 extends AppCompatActivity {


    SessionManager session;

    public static final String LOGIN_URL = "http://128.199.50.69/login";

    public static final String key_mail="email";
    public static final String key_pass="password";

    EditText mail,pass;
    Button btn;


    private String mailing, passwrd;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public void signup(View view) {
        Intent intern = new Intent(this, sign_up.class);
        startActivity(intern);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logina1);
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        // Session Manager
        session = new SessionManager(getApplicationContext());
       // Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
        mail=(EditText)findViewById(R.id.mail);
        final String Name=mail.getText().toString();
        pass=(EditText)findViewById(R.id.pas);
        btn=(Button)findViewById(R.id.login);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mail.length()==0)
                {
                    mail.requestFocus();
                    mail.setError("Please Enter Email");
                }
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(mail.getText().toString()).matches())
                {
                    mail.setError("Invalid Email");
                }
                else if(pass.length()==0)
                {
                    pass.requestFocus();
                    pass.setError("Please Enter Password");
                }
                else
                {
                    login();
                }



            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();

    }

    private void login(){

        mailing = mail.getText().toString().trim();
        passwrd = pass.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(login.this, response, Toast.LENGTH_SHORT).show();
                        if(response.trim().equals("Wrong email password")){
                            Toast.makeText(logina1.this,response,Toast.LENGTH_LONG).show();


                        }else{

                            openProfile(response);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(logina1.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(key_mail,mailing);
                map.put(key_pass,passwrd);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void openProfile(String response ){
        session.createLoginSession(response,mailing);
        Intent intent = new Intent(getApplicationContext(), testator_details.class);
        intent.putExtra(key_mail, mailing);
        intent.putExtra("u_id",response.toString());
        startActivity(intent);
        finish();

    }

    public void onClick(View v) {
        login();
    }


}



