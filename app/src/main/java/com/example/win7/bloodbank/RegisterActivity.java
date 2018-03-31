package com.example.win7.bloodbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText etname,etmail,etadd,etpass;
    String name,mail,add,pass,bgroup;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etname=(EditText)findViewById(R.id.et_rname);
        etmail=(EditText)findViewById(R.id.et_remail);
        etadd=(EditText)findViewById(R.id.et_radd);
        etpass=(EditText)findViewById(R.id.et_rpass);
       register=(Button)findViewById(R.id.btnregister);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        final Spinner bg= (Spinner)findViewById(R.id.spinbg);
        ArrayList<String> list= new ArrayList<>();
        list.add("Select blood group");
        list.add("A+");
        list.add("A-");
        list.add("B+");
        list.add("B-");
        list.add("AB+");
        list.add("AB-");
        list.add("O+");
        list.add("O-");
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bg.setAdapter(adapter);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    name = etname.getText().toString();
                    add = etadd.getText().toString();
                    mail = etmail.getText().toString();
                    pass = etpass.getText().toString();
                    bgroup =  bg.getSelectedItem().toString();
                  //  Toast.makeText(RegisterActivity.this, name+","+mail+","+add+","+pass+","+bgroup+".", Toast.LENGTH_SHORT).show();
                    HashMap data = new HashMap();
                    data.put("name", name);
                    data.put("email", mail);
                    data.put("bgroup", bgroup);
                    data.put("address", add);
                    data.put("pass", pass);
                    PostResponseAsyncTask reg = new PostResponseAsyncTask(RegisterActivity.this, data, "Please wait while we register..!", new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {

                           if(s.contains("success")){
                               Toast.makeText(RegisterActivity.this, "Registration Successfull! Now you may login to enjoy our services", Toast.LENGTH_LONG).show();
                           startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                           }
                        }
                    });
                    reg.execute("http://192.168.43.139/my_documents/blood%20bank/mvc/control_android.php");
                }
                catch (Exception e){
                    Toast.makeText(RegisterActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
