package com.universalapp.sankalp.learningapp.controller.reportList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.model.testReport.GeneralWiseReport;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TestReportGeneralListAdapter extends RecyclerView.Adapter<TestReportGeneralListAdapter.MyViewHolder> {

    List<GeneralWiseReport> arrayListGeneralWiseReport = new ArrayList<>();
    Activity activity;

    public TestReportGeneralListAdapter (Activity activity, List<GeneralWiseReport> arrayListGeneralWiseReport){
        this.activity = activity;
        this.arrayListGeneralWiseReport = arrayListGeneralWiseReport;
    }

    @NonNull
    @Override
    public TestReportGeneralListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new TestReportGeneralListAdapter.MyViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_report_general_wise, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull TestReportGeneralListAdapter.MyViewHolder viewHolder, int position) {

        //viewHolder.textViewName.setText(arrayListTimeSlot.get(i).getName());
        viewHolder.textViewTotalAttempt.setText(arrayListGeneralWiseReport.get(position).getTotalAttempted());
        viewHolder.textViewTotalCorrect.setText(arrayListGeneralWiseReport.get(position).getTotalCorrect());
        viewHolder.textViewTotalQuestion.setText(arrayListGeneralWiseReport.get(position).getTotalQuestions());
        viewHolder.textViewTotalScore.setText(arrayListGeneralWiseReport.get(position).getTotalCorrect()+"/"+ arrayListGeneralWiseReport.get(position).getTotalQuestions());

    }

    @Override
    public int getItemCount() {
        //return arrayListTimeSlot.size();
        return arrayListGeneralWiseReport.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{



        @BindView(R.id.text_total_attempt)
        TextView textViewTotalAttempt;
        @BindView(R.id.text_total_correct)
        TextView textViewTotalCorrect;
        @BindView(R.id.text_total_question)
        TextView textViewTotalQuestion;
        @BindView(R.id.text_total_score)
        TextView textViewTotalScore;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);

        }
    }

    public  void updateList(List<GeneralWiseReport> arrayListGeneralWiseReport){
        this.arrayListGeneralWiseReport = arrayListGeneralWiseReport;
        notifyDataSetChanged();
    }
}