package com.example.smart_will;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RedirectError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Residue_of_estate extends AppCompatActivity {
    SessionManager session;
    ImageButton but;
    public void spouse(View view) {
        Intent intern = new Intent(this, Residue_of_estate2.class);
        startActivity(intern);
    }

    EditText roe1,roe2,roe3,roe4,roe5;
    Button roeNext,addbeneficary,addstep;
    String url_ros="http://128.199.50.69/residue";
    String url_step="http://128.199.50.69/step_child ";

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_residue_of_estate);
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        final String uid = user.get(SessionManager.KEY_NAME);
        roe1=(EditText)findViewById(R.id.rfs1);
        roe2=(EditText)findViewById(R.id.rfs2);
        roe3=(EditText)findViewById(R.id.rfs3);
        roe4=(EditText)findViewById(R.id.rfs4);
        roe5=(EditText)findViewById(R.id.rfs5);

            but = (ImageButton) findViewById(R.id.back);
            but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Residue_of_estate.this, Info_block.class));
                }
            });

        roeNext = (Button)findViewById(R.id.rfsNext);
        addbeneficary = (Button)findViewById(R.id.addspouse);
        addstep = (Button)findViewById(R.id.addspouse1);
        roeNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(roe1.length()==0)
                {
                    roe1.requestFocus();
                    roe1.setError("Please enter first name");
                }
                else if(roe3.length()==0)
                {
                    roe3.requestFocus();
                    roe3.setError("Please enter surname");
                }
                else if(roe4.length()==0)
                {
                    roe4.requestFocus();
                    roe4.setError("Please enter Relation");
                }
                else if(roe5.length()==0)
                {
                    roe5.requestFocus();
                    roe5.setError("Please enter percentage of estate");
                }

//                else if(rg.getCheckedRadioButtonId()==0)
//                {
//                    rg.requestFocus();
//                    Toast.makeText(testator_details.this, "please select male Female", Toast.LENGTH_SHORT).show();
//                }
                else
                {
//                    Toast.makeText(testator_details.this,"Validation Successful",Toast.LENGTH_LONG).show();
                    Insert_residue_of_estate(uid);

                    Rfsfunc(uid);
                }

            }
        });

        addstep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(roe1.length()==0)
                {
                    roe1.requestFocus();
                    roe1.setError("Please enter first name");
                }
                else if(roe3.length()==0)
                {
                    roe3.requestFocus();
                    roe3.setError("Please enter surname");
                }
                else if(roe4.length()==0)
                {
                    roe4.requestFocus();
                    roe4.setError("Please enter Relation");
                }
                else if(roe5.length()==0)
                {
                    roe5.requestFocus();
                    roe5.setError("Please enter percentage of estate");
                }
                else
                {
                    Insert_step_children(uid);
                    Intent in=new Intent(Residue_of_estate.this, Residue_of_estate.class);
                    startActivity(in);
                }
            }
        });
        addbeneficary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(roe1.length()==0)
                {
                    roe1.requestFocus();
                    roe1.setError("Please enter first name");
                }
                else if(roe3.length()==0)
                {
                    roe3.requestFocus();
                    roe3.setError("Please enter surname");
                }
                else if(roe4.length()==0)
                {
                    roe4.requestFocus();
                    roe4.setError("Please enter Relation");
                }
                else if(roe5.length()==0)
                {
                    roe5.requestFocus();
                    roe5.setError("Please enter percentage of estate");
                }
                else
                {
                    Insert_residue_of_estate(uid);
                    Intent in=new Intent(Residue_of_estate.this, Residue_of_estate.class);
                    startActivity(in);
                    //Rfsfunc(uid);
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.logout:
            session.logoutUser();
            Intent in=new Intent(Residue_of_estate.this,logina1.class);
            startActivity(in);
            return(true);


    }
        return(super.onOptionsItemSelected(item));
    }

    public void Insert_step_children(final String uid){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_step, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

               // Toast.makeText(getApplication(), "7777", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(Residue_of_estate.this, error + "", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String,String> params=new HashMap<String, String>();

//                String newString = newString.getText().toString();
//                String email = mail.getText().toString();
//                String passwrd = pass.getText().toString();
                Intent intent = new Intent();
                intent.putExtras(getIntent());
                // Toast.makeText(Submission.this, getIntent().getExtras().getString("gfts1") , Toast.LENGTH_SHORT).show();
                String rof1 = roe1.getText().toString();
                String rof2= roe2.getText().toString();
                String rof3 = roe3.getText().toString();
                String rof4 = roe4.getText().toString();
                String rof5 = roe5.getText().toString();


                //String uid5 = getIntent().getExtras().getString("u_id");
                intent.putExtra("u_id",uid);


                params.put("name", rof1);
                params.put("mdname", rof2);
                params.put("srname", rof3);
                params.put("relation", rof4);
                params.put("estate", rof5);
                params.put("uid",uid);


                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }
    public void Insert_residue_of_estate(final String uid){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_ros, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplication(), "7777", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(Residue_of_estate.this, error + "", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String,String> params=new HashMap<String, String>();

//                String newString = newString.getText().toString();
//                String email = mail.getText().toString();
//                String passwrd = pass.getText().toString();
                Intent intent = new Intent();
                intent.putExtras(getIntent());
                // Toast.makeText(Submission.this, getIntent().getExtras().getString("gfts1") , Toast.LENGTH_SHORT).show();
                String rof1 = roe1.getText().toString();
                String rof2= roe2.getText().toString();
                String rof3 = roe3.getText().toString();
                String rof4 = roe4.getText().toString();
                String rof5 = roe5.getText().toString();


                //String uid5 = getIntent().getExtras().getString("u_id");
                intent.putExtra("u_id",uid);


                params.put("name", rof1);
                params.put("mdname", rof2);
                params.put("srname", rof3);
                params.put("relation", rof4);
                params.put("estate", rof5);
                params.put("uid",uid);


                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }
    private void Rfsfunc(String uid){

        Intent intent = new Intent(this, Special_request.class);
        intent.putExtra("rfs1",roe1.getText().toString());
        intent.putExtra("rfs2",roe2.getText().toString());
        intent.putExtra("rfs3",roe3.getText().toString());
        intent.putExtra("rfs4",roe4.getText().toString());
        intent.putExtra("rfs5",roe5.getText().toString());

      //  String uid = getIntent().getExtras().getString("u_id");
        intent.putExtra("u_id",uid);

        intent.putExtras(getIntent());
        startActivity(intent);



    }



}
