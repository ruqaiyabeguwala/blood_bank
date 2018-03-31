package com.example.win7.bloodbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class LoginActivity extends AppCompatActivity {
    EditText etemail, etpass;
    Button btn;
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        v = findViewById(android.R.id.content);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
        etemail = (EditText) findViewById(R.id.editTextemail);
        etpass = (EditText) findViewById(R.id.editTextPass);
        btn = (Button) findViewById(R.id.buttonLogin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap data = new HashMap();
                data.put("elogin", etemail.getText().toString());
                data.put("plogin", etpass.getText().toString());

                PostResponseAsyncTask task = new PostResponseAsyncTask(LoginActivity.this, data, "Please wait...!!", new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                              Log.d("email",s);
                          if(etemail.getText().toString().equals(" ") && etpass.getText().toString().equals(" ")){
                            Snackbar.make(v,"Field can't be empty!",Snackbar.LENGTH_LONG);
                        }
                        else if (s.equals("yes")) {
                            try {

                                HashMap data= new HashMap();
                                data.put("mail",etemail.getText().toString());
                                PostResponseAsyncTask getId= new PostResponseAsyncTask(LoginActivity.this,data, new AsyncResponse() {
                                    @Override
                                    public void processFinish(String s) {
                                        JSONArray arr = null;
                                        String dat="";
                                        try {
                                            arr = new JSONArray(s);
                                            JSONObject jObj = arr.getJSONObject(0);
                                            String b_id=jObj.getString("b_id");
                                            String nm=jObj.getString("name");
                                            String bg=jObj.getString("bgroup");
                                            String ad=jObj.getString("address");
                                           // Toast.makeText(LoginActivity.this, b_id+","+nm+","+bg+","+ad, Toast.LENGTH_SHORT).show();
                                            SaveSharedPreferences.setUser(LoginActivity.this,b_id,nm,etemail.getText().toString(),bg,ad);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }





                                    }
                                });
                                getId.execute("http://192.168.43.139/my_documents/blood%20bank/mvc/control_android.php");


                                    Intent i = new Intent(LoginActivity.this, MyActivity.class);
                                    startActivity(i);
                                    finish();



                            } catch (Exception e) {

                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }

                         else if (s.equals("no")) {
                            Snackbar.make(v, "Sorry..!! Wrong email or password.", Snackbar.LENGTH_SHORT).show();
                        }

                        else {
                            Snackbar.make(v, "Error connecting to server..", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
                task.execute("http://192.168.43.139/my_documents/blood%20bank/mvc/control_android.php");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}