package com.universalapp.sankalp.learningapp.controller.spinner;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.model.chapter.ChapterDetails;
import com.universalapp.sankalp.learningapp.model.subject.SubjectDetails;

import java.util.List;
import java.util.zip.Inflater;

public class ChapterSpinnerAdapter extends BaseAdapter {



    Inflater inflater;
    Activity activity;
    List<ChapterDetails> arraySpinnerList;

    public ChapterSpinnerAdapter(Activity context, List<ChapterDetails> arraySpinnerList) {
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
        ChapterDetails rowItem = arraySpinnerList.get(position);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.text_spinner_title);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,5,10,5);
        //txtTitle.setLayoutParams(params);
        txtTitle.setText(rowItem.getChapterName());

        convertView.setPadding(20,20,20,20);
        return convertView;
    }
}
