package com.universalapp.sankalp.learningapp.controller.spinner;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.model.standard.StandardList;
import com.universalapp.sankalp.learningapp.model.subject.SubjectDetails;

import java.util.List;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SubjectSpinnerAdapter extends BaseAdapter {


    Inflater inflater;
    Activity activity;
    List<SubjectDetails> arraySpinnerList;

    public SubjectSpinnerAdapter(Activity context, List<SubjectDetails> arraySpinnerList) {
        this.activity = context;
        this.arraySpinnerList = arraySpinnerList;
    }
    @Override
    public int getCount() {

        return arraySpinnerList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(R.layout.spinner_layout,parent, false);
        }
        SubjectDetails rowItem = arraySpinnerList.get(position);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.text_spinner_title);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,5,10,5);
        //txtTitle.setLayoutParams(params);
        txtTitle.setText(rowItem.getSubjectName());

        convertView.setPadding(20,20,20,20);
        return convertView;
    }



}