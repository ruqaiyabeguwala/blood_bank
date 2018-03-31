package com.example.win7.bloodbank;


import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecentDonFragment extends Fragment {

    ListView listView;
    List<Donation> list;
    MyAdapter adap;
    public RecentDonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_recent_don, container, false);

        listView=(ListView)view.findViewById(R.id.don_listview);
        HashMap listdata=new HashMap();
        listdata.put("b_id",SaveSharedPreferences.getUserId(getActivity().getApplicationContext()));
        PostResponseAsyncTask getList= new PostResponseAsyncTask(getActivity(), listdata, "Please wait while we fetch data for you!", new AsyncResponse() {
            @Override
            public void processFinish(String s) {

           //     Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_SHORT).show();

                list= new ArrayList<>();
                JSONArray ar=null;
                JSONObject obj;
                try{

                    ar= new JSONArray(s);
                    for(int i=0;i<ar.length();i++){
                        Donation donation= new Donation();
                        obj= ar.getJSONObject(i);
                        donation.bd_id=obj.getString("bd_id");
                        donation.bgroup=obj.getString("bgroup");
                        donation.hospital=obj.getString("hospital");
                        donation.sold=obj.getString("sold");
                        donation.confirm=obj.getString("confirm");
                        donation.date=obj.getString("date");
                        list.add(donation);
                    }
                    adap= new MyAdapter(list,getActivity());
                    listView.setAdapter(adap);
                    registerForContextMenu(listView);
                }
                catch (JSONException e){
                    Toast.makeText(getActivity().getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }

            }

        });
        getList.execute("http://192.168.43.139/my_documents/blood%20bank/mvc/control_android.php");


        return  view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.don_listview) {
            MenuInflater inflater= getActivity().getMenuInflater();
            inflater.inflate(R.menu.list_context_menu,menu);

             }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        final Donation selected= (Donation) adap.getItem(info.position);

        if(item.getItemId()==R.id.menu_detail){

            AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
            builder.setTitle("Details");
            builder.setIcon(android.R.drawable.ic_menu_info_details);
            builder.setMessage("Id of donation:"+selected.bd_id+"\n" +
                    "Hospital Name:"+selected.hospital+"\n"
                    +"Blood group:"+selected.bgroup+"\n" +
                    "Date of registration:"+selected.date+"\n" +
                    "Confirmation:"+selected.confirm+"\n" +
                    "Blood sold:"+selected.sold);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();

        }
        if(item.getItemId()==R.id.menu_cancel){
            if(selected.confirm.contains("no")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Cancel Donation?");
                builder.setIcon(android.R.drawable.ic_menu_info_details);
                builder.setMessage("Are you sure you want to cancel the donation request with the following data" +
                        "\nId of donation:" + selected.bd_id + "\n" +
                        "Hospital Name:" + selected.hospital + "\n"
                        + "Blood group:" + selected.bgroup + "\n" +
                        "Date of registration:" + selected.date + "\n" +
                        "Confirmation:" + selected.confirm + "\n" +
                        "Blood sold:" + selected.sold);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        list.remove(selected);
                        adap.notifyDataSetChanged();
                        HashMap data= new HashMap();
                        data.put("bd_id",selected.bd_id);
                        PostResponseAsyncTask del= new PostResponseAsyncTask(getActivity(), data, new AsyncResponse() {
                            @Override
                            public void processFinish(String s) {

                                if(s.contains("success")){
                                    Toast.makeText(getActivity().getApplicationContext(), "Request cancelled successfully!", Toast.LENGTH_LONG).show();
                                }

                                if(s.contains("fail")){
                                    Toast.makeText(getActivity().getApplicationContext(), "Sorry, some unknown erroro occured!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();
            }
            else{
                Toast.makeText(getActivity().getApplicationContext(), "Sorry, this donation request has been confirmed already! Cancellation access denied", Toast.LENGTH_LONG).show();
            }
        }
       return super.onContextItemSelected(item);
    }


}
