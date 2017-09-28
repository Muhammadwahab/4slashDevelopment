package com.example.smart_will;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Special_request extends AppCompatActivity {

    String url_req ="http://128.199.50.69/special_request";
    String url;
    Button SpNext, vr,save,retake;
    RadioGroup rg1,rg2,rg3;
    EditText ed,ed2;
    ImageButton but;
    final private int VIDEO_REQUEST_CODE=100;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    SessionManager session;
    private String mFilename=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_request);
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        final String uid = user.get(SessionManager.KEY_NAME);
        ed=(EditText)findViewById(R.id.music);
        ed2=(EditText)findViewById(R.id.sprq);
        rg1 =(RadioGroup)findViewById(R.id.Rbg1);
        rg2 =(RadioGroup)findViewById(R.id.Rbg2);
        rg3 =(RadioGroup)findViewById(R.id.Rbg3);
        Intent ifno=new Intent(getIntent());
////        String r1=ifno.getExtras().getString("R1");
////        String r2=ifno.getExtras().getString("R2");
////        String r3=ifno.getExtras().getString("R3");
//        if (r1.equals("No") && r2.equals("No") && r3.equals("No")) {
//            but = (ImageButton) findViewById(R.id.back);
//            but.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    startActivity(new Intent(Special_request.this, Info_block.class));
//                }
//            });
//        }
//        else {
//            but = (ImageButton) findViewById(R.id.back);
//            but.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    startActivity(new Intent(Special_request.this, Residue_of_estate.class));
//                }
//            });
//        }

        SpNext = (Button)findViewById(R.id.spNext);
        SpNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Insert_request(uid);
             //Srintent();


            }
        });

        vr= (Button)findViewById(R.id.capturevideo);
        vr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                captureVideo(view);

            }
        });
//
//        save= (Button)findViewById(R.id.save);
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                save();
//
//            }
//        });

//        retake= (Button)findViewById(R.id.retake);
//        retake.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent in=new Intent(Special_request.this,CameraActivity.class);
//                startActivity(in);
//                //captureVide(view);
//
//            }
//        });
    }

    private void Insert_request(final String uid) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_req, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(Special_request.this, Submission.class);
                startActivity(intent);
                //Toast.makeText(getApplication(), "44444", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                  // Toast.makeText(Special_request.this, Rg1+Rg2+Rg3+uid, Toast.LENGTH_SHORT).show();
               // Toast.makeText(Special_request.this, error + "", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String,String> params=new HashMap<String, String>();


                String a=get_url();
                String Rg1 = ((RadioButton)findViewById(rg1.getCheckedRadioButtonId())).getText().toString();
                String Rg2 = ((RadioButton)findViewById(rg2.getCheckedRadioButtonId())).getText().toString();
                String Rg3 = ((RadioButton)findViewById(rg3.getCheckedRadioButtonId())).getText().toString();
                params.put("buried_cremated", Rg1);
                params.put("prepaid_plan", Rg2);
                params.put("music",ed.getText().toString());
                params.put("video_storage",a);
                params.put("request", Rg3);
                params.put("u_id",uid);


                return params;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void save() {
        StorageReference storage= FirebaseStorage.getInstance().getReference();
        SharedPreferences sp1 = getSharedPreferences("Path", Activity.MODE_PRIVATE);
        String path = sp1.getString("your_path",null);
        Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
        File abc=new File(path);

        //Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
        Uri file = Uri.fromFile(abc);
        //Toast.makeText(this, file.toString(), Toast.LENGTH_SHORT).show();
        StorageReference riversRef = storage.child("videos/"+file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);
        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(Special_request.this, "failed", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                 url=downloadUrl.toString();
                set_url(url);
                Toast.makeText(Special_request.this, "Uploaded Sucessfull", Toast.LENGTH_SHORT).show();
            }
        });



    }
    public void set_url(String url) {
        this.url=url;
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
            Intent in=new Intent(Special_request.this,logina1.class);
            startActivity(in);
            return(true);



    }
        return(super.onOptionsItemSelected(item));
    }

    public void captureVideo(View view)
    {
        Intent camera=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        File video_path=getFilePath();
       Uri video_uri=Uri.fromFile(video_path);
        camera.putExtra(MediaStore.EXTRA_OUTPUT,video_uri);
        camera.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 180);
        camera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
        startActivityForResult(camera,VIDEO_REQUEST_CODE);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==VIDEO_REQUEST_CODE)
        {
            if(resultCode==RESULT_OK)
            {
                // Create a storage reference from our app
                StorageReference storage= FirebaseStorage.getInstance().getReference();

                Uri uri = data.getData();
                Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();
                Log.d("STATE",data.getData().toString());
                StorageReference riversRef = storage.child("Video/"+uri.getLastPathSegment());
                UploadTask uploadTask = riversRef.putFile(uri);

               // Register observers to listen for when the download is done or if it fails
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Special_request.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        url=downloadUrl.toString();
                        set_url(url);

                        Toast.makeText(Special_request.this, "Upload Success", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else
                Toast.makeText(getApplicationContext(), "Video Capture Failed", Toast.LENGTH_SHORT).show();
        }


    }

    public File getFilePath()
    {
        File folder=new File("sdcard/video_app");
        if(folder.exists())
        {
            folder.mkdir();

        }
        File video_folder = new File(folder,"sample_video.mp4");

        return video_folder;
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();

    }
    private void Srintent(){


        Intent intent = new Intent(this, Submission.class);
        String Rg1 = ((RadioButton)findViewById(rg1.getCheckedRadioButtonId())).getText().toString();
        intent.putExtra("Radiogroup1",Rg1);

        intent.putExtra("sp1",ed.getText().toString());
        intent.putExtra("sp2",ed2.getText().toString());

        String Rg2 = ((RadioButton)findViewById(rg2.getCheckedRadioButtonId())).getText().toString();
        intent.putExtra("Radiogroup2",Rg2);

        String Rg3 = ((RadioButton)findViewById(rg3.getCheckedRadioButtonId())).getText().toString();
        intent.putExtra("Radiogroup3",Rg3);

//        intent.putExtras(getIntent());
//        String uid = getIntent().getExtras().getString("u_id");
//        intent.putExtra("u_id",uid);

        intent.putExtras(getIntent());
        startActivity(intent);
    }


    public String  get_url() {
        return url;
    }
}
