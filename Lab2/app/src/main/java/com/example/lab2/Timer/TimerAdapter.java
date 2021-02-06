package com.example.lab2.Timer;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lab2.Activity.TimerCreateActivity;
import com.example.lab2.Activity.TrainingActivity;
import com.example.lab2.DB.DBHelper;
import com.example.lab2.DB.DBModel;
import com.example.lab2.R;

import java.util.List;


public class TimerAdapter extends ArrayAdapter<DBModel> {
    private LayoutInflater inflater;
    private int layout;
    private List<DBModel> dbModelList;
    private DBHelper db;

    public TimerAdapter(Context context, int resource, List<DBModel> timerModels,
                        DBHelper db) {
        super(context, resource, timerModels);
        this.dbModelList = timerModels;
        this.layout = resource;
        this.db = db;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        DBModel dbModel = dbModelList.get(position);
        viewHolder.nameView.setText(dbModel.Name);
        viewHolder.idView.setText((Integer.toString(dbModel.Id)));
        viewHolder.layout.setBackgroundColor(dbModel.Color);

        viewHolder.startButton.setOnClickListener(i -> {
            Context context = getContext();
            Intent intent = new Intent(context, TrainingActivity.class);
            intent.putExtra("timerId", dbModel.Id);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

        viewHolder.removeButton.setOnClickListener(i -> {
            db.dbTimer().delete(dbModelList.get(position));
            dbModelList.remove(dbModel);
            notifyDataSetChanged();
        });

        viewHolder.editButton.setOnClickListener(i -> {
            Context context = getContext();
            Intent intent = new Intent(context, TimerCreateActivity.class);
            intent.putExtra("timerId", new int[]{dbModel.Id, 1});
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

        return convertView;
    }

    private class ViewHolder {
        Button removeButton, editButton, startButton;
        TextView nameView, idView;
        LinearLayout layout;
        ViewHolder(View view){
            nameView = view.findViewById(R.id.nameView);
            idView = view.findViewById(R.id.idView);
            startButton = view.findViewById(R.id.startButton);
            editButton = view.findViewById(R.id.changesButton);
            removeButton = view.findViewById(R.id.deleteButton);
            layout = view.findViewById(R.id.layout);
        }
    }
}