package com.example.theone.virtualbank;





/**
 * Created by theone on 16/01/16.
 */
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class AccountAdapter extends ArrayAdapter<account_item> {
    Context context;
    int layoutResourceId;
    ArrayList<account_item> data = new ArrayList<account_item>();

    public AccountAdapter(Context context, int layoutResourceId,
                           ArrayList<account_item> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RecordHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.firstLine);
            holder.txtDate= (TextView) row.findViewById(R.id.date);
            holder.txtDesc= (TextView) row.findViewById(R.id.secondLine);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }

        account_item item = data.get(position);
        holder.txtTitle.setText(item.getTitle());
        holder.txtDate.setText(item.getDate());
        holder.txtDesc.setText(item.getDesc());

        return row;

    }

    static class RecordHolder {
        TextView txtTitle;
        TextView txtDate;
        TextView txtDesc;

    }
}
