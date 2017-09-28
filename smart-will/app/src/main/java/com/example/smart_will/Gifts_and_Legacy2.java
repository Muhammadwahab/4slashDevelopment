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

public class Gifts_and_Legacy2 extends AppCompatActivity {
    int count= 0;
    ImageButton but;
    EditText edtxt1,edtxt2,edtxt3;
    TextView txtv;
    String url_gifts_legacy ="http://128.199.50.69/gifts_legacy";
SessionManager session;
    Button glNext;
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
        setContentView(R.layout.activity_gifts_and__legacy2);
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        final String uid = user.get(SessionManager.KEY_NAME);
        txtv = (TextView)findViewById(R.id.addfield);
        edtxt1=(EditText)findViewById(R.id.ed1);
        edtxt2=(EditText)findViewById(R.id.ed2);
        edtxt2.setVisibility(View.GONE);
        edtxt3=(EditText)findViewById(R.id.ed3);
        edtxt3.setVisibility(View.GONE);

        but=(ImageButton)findViewById(R.id.back);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Gifts_and_Legacy2.this,Gifts_and_legacy.class));
            }
        });

        txtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                count++;
                if (count==1){
                    edtxt2.setVisibility(View.VISIBLE);
                }
                else if(count==2){
                    edtxt3.setVisibility(View.VISIBLE);
                }}
        });



        glNext=(Button)findViewById(R.id.gftlegcynext);
        glNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edtxt1.length()==0)
                {
                    edtxt1.requestFocus();
                    edtxt1.setError("Please enter");
                }
                else if(edtxt2.length()==0)
                {
                    edtxt2.requestFocus();
                    edtxt2.setError("Please enter");
                }
                else if(edtxt3.length()==0)
                {
                    edtxt3.requestFocus();
                    edtxt3.setError("Please enter");
                }
                else
                {
//                    Toast.makeText(testator_details.this,"Validation Successful",Toast.LENGTH_LONG).show();
                    Insert_giftslegacy(uid);
                    glIntent();
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
            Intent in=new Intent(Gifts_and_Legacy2.this,logina1.class);
            startActivity(in);
            return(true);
     }
        return(super.onOptionsItemSelected(item));
    }


    public void Insert_giftslegacy(final String uid){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_gifts_legacy, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplication(), "5555", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(Gifts_and_Legacy2.this, error + "", Toast.LENGTH_SHORT).show();

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

                String gl1 = edtxt1.getText().toString();
                String gl2= edtxt2.getText().toString();
                String gl3 = edtxt3.getText().toString();


                String uid4 = getIntent().getExtras().getString("u_id");
                intent.putExtra("u_id",uid4);


                params.put("foundation1", gl1);
                params.put("foundation2", gl2);
                params.put("foundation3", gl3);

                params.put("uid",uid);


                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }
    private void glIntent(){



        Intent intent = new Intent(this, Info_block.class);
        intent.putExtra("ed1",edtxt1.getText().toString());
        intent.putExtra("ed2",edtxt2.getText().toString());
        intent.putExtra("ed3",edtxt3.getText().toString());

        String uid = getIntent().getExtras().getString("u_id");
        intent.putExtra("u_id",uid);

        intent.putExtras(getIntent());
        startActivity(intent);



    }


}
