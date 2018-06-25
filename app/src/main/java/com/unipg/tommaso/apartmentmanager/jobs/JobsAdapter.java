package com.unipg.tommaso.apartmentmanager.jobs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.unipg.tommaso.apartmentmanager.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by tommaso on 10/06/2018.
 */

class JobsAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Job> jobList;

    public JobsAdapter(ArrayList<Job> jobList, Context context){
        this.jobList = jobList;
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
    }
    @Override
    public int getCount() {
        return jobList.size();
    }

    @Override
    public Job getItem(int position) {
        return jobList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        JobsViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.jobs_list_item, parent, false);
            holder = new JobsViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (JobsViewHolder) convertView.getTag();
        }
        Job currentJob = getItem(position);
        holder.jobsListView.setText(currentJob.jobName);
        return convertView;
    }
    public void setJobs(ArrayList<Job> jobs){
        jobList.addAll(jobs);
        notifyDataSetChanged();
    }
    class JobsViewHolder{
        TextView jobsListView;
        private JobsViewHolder(View convertView){
            this.jobsListView = convertView.findViewById(R.id.jobs_list_item);
        }
    }
}

