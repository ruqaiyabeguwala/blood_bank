package com.example.win7.bloodbank;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Win 7 on 3/21/2018.
 */

public class MyAdapter extends BaseAdapter {

    List<Donation> list;
    Context context;

    public MyAdapter(List<Donation> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int i) {
        return this.list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewItem viewItem = null;
        if(convertView == null)
        {
            viewItem = new ViewItem();

            LayoutInflater layoutInfiater = (LayoutInflater)this.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInfiater.inflate(R.layout.card, null);

            viewItem.tvbd_id = (TextView)convertView.findViewById(R.id.listbd_id);
            viewItem.tvhos = (TextView)convertView.findViewById(R.id.listhos);
            viewItem.tvdate = (TextView)convertView.findViewById(R.id.listdate);

            viewItem.tvvcon = (TextView)convertView.findViewById(R.id.tvlistcon);
            viewItem.tvcon = (ImageView) convertView.findViewById(R.id.listcon);
            convertView.setTag(viewItem);
        }
        else
        {
            viewItem = (ViewItem) convertView.getTag();
        }

        viewItem.tvbd_id.setText("Donation Id: "+list.get(position).bd_id);

        viewItem.tvdate.setText(list.get(position).date);
        viewItem.tvhos.setText(list.get(position).hospital);
        if(list.get(position).confirm.contains("yes")){
            viewItem.tvcon.setImageResource(R.drawable.tick);
            viewItem.tvvcon.setText("Confirmed");
        }
        else if(list.get(position).confirm.contains("no")){
            viewItem.tvvcon.setText("Not confirmed!");
            viewItem.tvcon.setImageResource(R.drawable.user);
        }




        return convertView;
    }
}

class ViewItem
{
    TextView tvbd_id;
    TextView tvhos;
    TextView tvdate;

    TextView tvvcon;
    ImageView tvcon;

}


