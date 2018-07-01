package com.unipg.tommaso.apartmentmanager.missing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.unipg.tommaso.apartmentmanager.R;

import java.util.ArrayList;

/**
 * Created by tommaso on 30/06/2018.
 */

class MissingAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Missing> missingList;

    public MissingAdapter(ArrayList<Missing> missingList, Context context){
        this.missingList = missingList;
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
    }
    @Override
    public int getCount() {
        return missingList.size();
    }

    @Override
    public Missing getItem(int position) {
        return missingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MissingViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.jobs_list_item, parent, false);
            holder = new MissingViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (MissingViewHolder) convertView.getTag();
        }
        Missing currentMissing = getItem(position);
        holder.missingListView.setText(currentMissing.getWho().getDisplayName());
        return convertView;
    }
    public void setJobs(ArrayList<Missing> missing){
        missingList.addAll(missing);
        notifyDataSetChanged();
    }
    class MissingViewHolder{
        TextView missingListView;
        private MissingViewHolder(View convertView){
            this.missingListView = convertView.findViewById(R.id.missing_list_item);
        }
    }
}
