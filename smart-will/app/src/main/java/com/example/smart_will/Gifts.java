package com.example.smart_will;

import android.app.Activity;
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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Gifts extends AppCompatActivity {
    String url_gifts ="http://128.199.50.69/gift";
SessionManager session;

    //    public void reg(View view) {
//        Intent intern = new Intent(this, Info_block.class);
//        startActivity(intern);
//    }
    public void add_benif(View view) {
        Intent intern = new Intent(this, Gifts2.class);
        startActivity(intern);
    }


    EditText gfts1,gfts2,gfts3,gfts4,gfts5;
    Button gftNext,gftben;
    static int count2;
    ImageButton but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gifts);
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("your_int_key", count2);
        editor.commit();


        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        final String uid = user.get(SessionManager.KEY_NAME);
        gfts1=(EditText)findViewById(R.id.gf1);
        gfts2=(EditText)findViewById(R.id.gf2);
        gfts3=(EditText)findViewById(R.id.gf3);
        gfts4=(EditText)findViewById(R.id.gf4);
        gfts5=(EditText)findViewById(R.id.gf5);

        gftNext=(Button)findViewById(R.id.gftNext);
        but=(ImageButton)findViewById(R.id.back);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Gifts.this,Gifts_and_legacy.class));
            }
        });
        gftben=(Button)findViewById(R.id.gftBeni);
        gftben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp1 = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
                int myIntValue = sp1.getInt("your_int_key", -1);
                count2++;
                String a=Integer.toString(myIntValue);
                if(gfts1.length()==0)
                {
                    gfts1.requestFocus();
                    gfts1.setError("Please enter first name");
                }

                else if(gfts3.length()==0)
                {
                    gfts3.requestFocus();
                    gfts3.setError("Please enter surname");
                }
                else if(gfts4.length()==0)
                {
                    gfts4.requestFocus();
                    gfts4.setError("Please enter relation");
                }
                else if(gfts5.length()==0)
                {
                    gfts5.requestFocus();
                    gfts5.setError("Please enter Gifts");
                }
                else if (a.equals("9"))
                {
                    Toast.makeText(Gifts.this, "You can't add more than 10 Beneficiary", Toast.LENGTH_SHORT).show();
                    Insert_gifts(uid);
                }
                else
                {
                    //Toast.makeText(Gifts.this, a, Toast.LENGTH_SHORT).show();
                    Insert_gifts_add(uid);
                    Intent in=new Intent(Gifts.this,Gifts.class);
                    startActivity(in);
                    //gftsintent();
                }



            }
        });


        gftNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gfts1.length()==0)
                {
                    gfts1.requestFocus();
                    gfts1.setError("Please enter first name");
                }

                else if(gfts3.length()==0)
                {
                    gfts3.requestFocus();
                    gfts3.setError("Please enter surname");
                }
                else if(gfts4.length()==0)
                {
                    gfts4.requestFocus();
                    gfts4.setError("Please enter relation");
                }
                else if(gfts5.length()==0)
                {
                    gfts5.requestFocus();
                    gfts5.setError("Please enter Gifts");
                }

//                else if(rg.getCheckedRadioButtonId()==0)
//                {
//                    rg.requestFocus();
//                    Toast.makeText(testator_details.this, "please select male Female", Toast.LENGTH_SHORT).show();
//                }
                else
                {
//                    Toast.makeText(testator_details.this,"Validation Successful",Toast.LENGTH_LONG).show();
                    Insert_gifts(uid);
                   // gftsintent(uid);
                }



            }
        });

    }

    private void Insert_gifts_add(final String uid) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_gifts, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                //Toast.makeText(getApplication(), "6666", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(Gifts.this, error + "", Toast.LENGTH_SHORT).show();

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
                String gft1 =gfts1.getText().toString();
                String gft2= gfts2.getText().toString();
                String gft3 = gfts3.getText().toString();
                String gft4 =gfts4.getText().toString();
                String gft5 = gfts5.getText().toString();


//                String uid5 = getIntent().getExtras().getString("u_id");
//                intent.putExtra("u_id",uid5);


                params.put("name", gft1);
                params.put("mdname", gft2);
                params.put("srname", gft3);
                params.put("relation", gft4);
                params.put("gift", gft5);
                params.put("uid",uid);


                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
            Intent in=new Intent(Gifts.this,logina1.class);
            startActivity(in);
            return(true);


    }
        return(super.onOptionsItemSelected(item));
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();

    }
    public void Insert_gifts(final String uid){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_gifts, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(Gifts.this, Info_block.class);
                startActivity(intent);
                //Toast.makeText(getApplication(), "6666", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(Gifts.this, error + "", Toast.LENGTH_SHORT).show();

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
                String gft1 =gfts1.getText().toString();
                String gft2= gfts2.getText().toString();
                String gft3 = gfts3.getText().toString();
                String gft4 =gfts4.getText().toString();
                String gft5 = gfts5.getText().toString();


//                String uid5 = getIntent().getExtras().getString("u_id");
//                intent.putExtra("u_id",uid5);


                params.put("name", gft1);
                params.put("mdname", gft2);
                params.put("srname", gft3);
                params.put("relation", gft4);
                params.put("gift", gft5);
                params.put("uid",uid);


                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    private void gftsintent(String uid){

        Intent intent = new Intent(this, Info_block.class);
        intent.putExtra("gfts1",gfts1.getText().toString());
        intent.putExtra("gfts2",gfts2.getText().toString());
        intent.putExtra("gfts3",gfts3.getText().toString());
        intent.putExtra("gfts4",gfts4.getText().toString());
        intent.putExtra("gfts5",gfts5.getText().toString());

//        String uid = getIntent().getExtras().getString("u_id");
        intent.putExtra("u_id",uid);

        intent.putExtras(getIntent());
        startActivity(intent);



    }

}
