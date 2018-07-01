package com.unipg.tommaso.apartmentmanager.roommates;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.unipg.tommaso.apartmentmanager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tommaso on 06/06/2018.
 */

public class RoommatesAdapter extends BaseAdapter{
    ArrayList<Roommate> roommatesList;
    private Context context;
    private LayoutInflater inflater;

    public RoommatesAdapter(ArrayList<Roommate> roommatesList, Context context) {
        this.context = context;
        this.roommatesList = roommatesList;
        this.inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return roommatesList.size();
    }

    @Override
    public Roommate getItem(int position) {
        return roommatesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RoommatesViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.roommate_list_item, parent, false);
            holder = new RoommatesViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (RoommatesViewHolder) convertView.getTag();
        }
        String currentName = getItem(position).getDisplayName();
        holder.roommate_name.setText(currentName);
        return convertView;
    }

    public void setRoommates(List<Roommate> roommates){
        roommatesList.addAll(roommates);
        notifyDataSetChanged();
    }

    static class RoommatesViewHolder {
        TextView roommate_name;
        private RoommatesViewHolder(View convertView){
            this.roommate_name = convertView.findViewById(R.id.roommate_list_item);
        }
    }
}

