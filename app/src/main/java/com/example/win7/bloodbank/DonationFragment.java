package com.example.win7.bloodbank;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class DonationFragment extends Fragment {

    Spinner bg,hos;

    public DonationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_donation, container, false);
       bg=(Spinner)view.findViewById(R.id.don_bg_spin);
        hos=(Spinner)view.findViewById(R.id.don_hos_spin);


      HashMap dataa= new HashMap();
        dataa.put("hos","abd");
        PostResponseAsyncTask getHos= new PostResponseAsyncTask(getActivity(),dataa, new AsyncResponse() {
            @Override
            public void processFinish(String sh) {

             //   Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                JSONArray arr = null;
                String dat="";
                try {
                    arr = new JSONArray(sh);


                    ArrayList<String> hlist= new ArrayList<>();
                    hlist.add("Select Hospital Preference");

                    for(int i=0;i< arr.length();i++){
                        JSONObject jObj = arr.getJSONObject(i);
                        hlist.add(jObj.getString("name"));
                        }
                    ArrayAdapter hadapter= new ArrayAdapter(getActivity().getApplicationContext(),android.R.layout.simple_spinner_item,hlist);
                    hadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    hos.setAdapter(hadapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        getHos.execute("http://192.168.43.139/my_documents/blood%20bank/mvc/control_android.php");





        ArrayList<String> list= new ArrayList<>();
        list.add("Select Blood group");
        list.add(SaveSharedPreferences.getbGroup(getActivity().getApplicationContext()));
        ArrayAdapter adapter= new ArrayAdapter(getActivity().getApplicationContext(),android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       bg.setAdapter(adapter);


        try {
    Button donate=(Button)view.findViewById(R.id.btn_donate);
    donate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {



    String bgroup = bg.getSelectedItem().toString();
    String hospital = hos.getSelectedItem().toString();
    String bid = SaveSharedPreferences.getUserId(getActivity().getApplicationContext());
    HashMap insertData = new HashMap();
    insertData.put("bg", bgroup);
    insertData.put("hosp", hospital);
    insertData.put("id", bid);
    PostResponseAsyncTask donat = new PostResponseAsyncTask(getActivity(), insertData, new AsyncResponse() {
        @Override
        public void processFinish(String s) {

            //Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_SHORT).show();
               if(!s.equals(" ")){
                   String[] sp= s.split(",");
                    AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
                    builder.setTitle("Registration Successfull!");
                    builder.setIcon(R.drawable.ic_launcher);
                    builder.setMessage("Congrats! You donation has been successfully  registered at "+sp[1]+". Kindly refer to the corresponding hospital to complete the donation with id:"+sp[0]);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                   builder.show();
                   RecentFragment frag =new RecentFragment();
                   FragmentManager manager =getActivity().getSupportFragmentManager();
                   manager.beginTransaction().replace(R.id.my_content, frag, frag.getTag()).commit();

               }
                    else{
                    AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
                    builder.setTitle("Registration UnSuccessfull!");
                    builder.setIcon(R.drawable.ic_launcher);
                    builder.setMessage("Seems like we are facing some issues! Try again later!");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                   builder.show();
                  }

        }
    });

    donat.execute("http://192.168.43.139/my_documents/blood%20bank/mvc/control_android.php");
        }
    });
        }
catch (Exception e){
    Toast.makeText(getActivity().getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
}

        return view;
    }


}
