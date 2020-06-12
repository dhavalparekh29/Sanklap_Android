package com.universalapp.sankalp.learningapp.controller.reportList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.model.testReport.SubjectWiseReport;
import com.universalapp.sankalp.learningapp.model.testReport.SubjectWiseReport;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TestReportSubjectListAdapter  extends RecyclerView.Adapter<TestReportSubjectListAdapter.MyViewHolder> {

    List<SubjectWiseReport> arrayListSubjectWiseReport = new ArrayList<>();
    Activity activity;

    public TestReportSubjectListAdapter (Activity activity, List<SubjectWiseReport> arrayListSubjectWiseReport){
        this.activity = activity;
        this.arrayListSubjectWiseReport = arrayListSubjectWiseReport;
    }

    @NonNull
    @Override
    public TestReportSubjectListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new TestReportSubjectListAdapter.MyViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_report_subject_wise, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull TestReportSubjectListAdapter.MyViewHolder viewHolder, int position) {

        //viewHolder.textViewName.setText(arrayListTimeSlot.get(i).getName());
        viewHolder.textViewTotalAttempt.setText(arrayListSubjectWiseReport.get(position).getTotalAttempted());
        viewHolder.textViewSubjectName.setText(arrayListSubjectWiseReport.get(position).getSubjectName());
        viewHolder.textViewTotalCorrect.setText(arrayListSubjectWiseReport.get(position).getTotalCorrect());
        viewHolder.textViewTotalQuestion.setText(arrayListSubjectWiseReport.get(position).getTotalQuestions());
        viewHolder.textViewTotalScore.setText(arrayListSubjectWiseReport.get(position).getTotalCorrect()+"/"+ arrayListSubjectWiseReport.get(position).getTotalQuestions());

    }

    @Override
    public int getItemCount() {
        //return arrayListTimeSlot.size();
        return arrayListSubjectWiseReport.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{



        @BindView(R.id.text_subject_name)
        TextView textViewSubjectName;
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

    public  void updateList(List<SubjectWiseReport> arrayListSubjectWiseReport){
        this.arrayListSubjectWiseReport = arrayListSubjectWiseReport;
        notifyDataSetChanged();
    }
}