package com.example.win7.bloodbank;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amigold.fundapter.BindDictionary;

import java.util.List;

/**
 * Created by Win 7 on 1/23/2018.
 */

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.DonationViewHolder> {

    private Context ctx;
    List<Donation> lists;
    BindDictionary dictionary;

    public DonationAdapter(Context ctx, List<Donation> lists) {
        this.ctx = ctx;
        this.lists = lists;
    }


    @Override
    public DonationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(ctx);
        View view=inflater.inflate(R.layout.card, null);
        DonationViewHolder holder= new DonationViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DonationViewHolder holder, int position) {

        Donation donation= lists.get(position);
     /*   holder.id.setText(donation.getBd_id());
        holder.hos.setText(donation.getHospital());
        holder.date.setText(donation.getDate());
        holder.sold.setText(donation.getSold());
        holder.confirm.setText(donation.getConfirm());
    */
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class DonationViewHolder extends  RecyclerView.ViewHolder{
TextView id, hos,date,confirm,sold;

        public DonationViewHolder(View itemView) {
            super(itemView);
            id=(TextView)itemView.findViewById(R.id.listbd_id);
            hos=(TextView)itemView.findViewById(R.id.listhos);
            date=(TextView)itemView.findViewById(R.id.listdate);
            confirm=(TextView)itemView.findViewById(R.id.listcon);

        }
    }
}
