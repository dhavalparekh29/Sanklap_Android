package com.universalapp.sankalp.learningapp.controller.reportList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.model.testReport.ChapterWiseReport;
import com.universalapp.sankalp.learningapp.model.testReport.ChapterWiseReport;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TestReportChapterListAdapter  extends RecyclerView.Adapter<TestReportChapterListAdapter.MyViewHolder> {

    List<ChapterWiseReport> arrayListChapterWiseReport = new ArrayList<>();
    Activity activity;

    public TestReportChapterListAdapter (Activity activity, List<ChapterWiseReport> arrayListChapterWiseReport){
        this.activity = activity;
        this.arrayListChapterWiseReport = arrayListChapterWiseReport;
    }

    @NonNull
    @Override
    public TestReportChapterListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new TestReportChapterListAdapter.MyViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_report_chapter_wise, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull TestReportChapterListAdapter.MyViewHolder viewHolder, int position) {

        //viewHolder.textViewName.setText(arrayListTimeSlot.get(i).getName());
        viewHolder.textViewSubjectName.setText(arrayListChapterWiseReport.get(position).getSubjectName());
        viewHolder.textViewChapterName.setText(arrayListChapterWiseReport.get(position).getChapterName());
        viewHolder.textViewTotalAttempt.setText(arrayListChapterWiseReport.get(position).getTotalAttempted());
        viewHolder.textViewTotalCorrect.setText(arrayListChapterWiseReport.get(position).getTotalCorrect());
        viewHolder.textViewTotalQuestion.setText(arrayListChapterWiseReport.get(position).getTotalQuestions());
        viewHolder.textViewTotalScore.setText(arrayListChapterWiseReport.get(position).getTotalCorrect()+"/"+ arrayListChapterWiseReport.get(position).getTotalQuestions());

    }

    @Override
    public int getItemCount() {
        //return arrayListTimeSlot.size();
        return arrayListChapterWiseReport.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{



        @BindView(R.id.text_subject_name)
        TextView textViewSubjectName;
        @BindView(R.id.text_chapter_name)
        TextView textViewChapterName;
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

    public  void updateList(List<ChapterWiseReport> arrayListChapterWiseReport){
        this.arrayListChapterWiseReport = arrayListChapterWiseReport;
        notifyDataSetChanged();
    }
}