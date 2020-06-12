package com.universalapp.sankalp.learningapp.controller.selection;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.controller.membershipPlan.MembershipPlanListAdapter;
import com.universalapp.sankalp.learningapp.model.membershipPack.MembershipSubjectSelection;
import com.universalapp.sankalp.learningapp.model.subject.SubjectDetails;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SubjectListSelectorAdapter extends RecyclerView.Adapter<SubjectListSelectorAdapter.MyViewHolder> {

    List<SubjectDetails> arrayListSubject = new ArrayList<>();
    Activity activity;
    int numberOfSelection;
    int totalSelection = 0 ;

    public SubjectListSelectorAdapter (Activity activity, List<SubjectDetails> arrayListSubject, int numberOfSelection){
        this.activity = activity;
        this.arrayListSubject = arrayListSubject;
        this.numberOfSelection = numberOfSelection;
    }

    @NonNull
    @Override
    public SubjectListSelectorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new SubjectListSelectorAdapter.MyViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_check_box, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull SubjectListSelectorAdapter.MyViewHolder viewHolder, int position) {

        viewHolder.checkBoxName.setText(arrayListSubject.get(position).getSubjectName());

        viewHolder.checkBoxName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(viewHolder.checkBoxName.isChecked()){
                    if(totalSelection < numberOfSelection) {
                        totalSelection = totalSelection + 1;
                        MembershipPlanListAdapter.SUBJECT_SELECTION.add(new MembershipSubjectSelection(arrayListSubject.get(position).getSubjectId()));
                        //viewHolder.checkBoxName.setChecked(true);

                    }else {
                        viewHolder.checkBoxName.setChecked(false);
                        Toast.makeText(activity, "Maximum you can select "+numberOfSelection +" subject", Toast.LENGTH_SHORT).show();
                    }
                    //viewHolder.checkBoxName.setChecked(false);
                }else{
                    if(totalSelection>0) {
                        totalSelection = totalSelection - 1;
                        for(int i = 0; i < MembershipPlanListAdapter.SUBJECT_SELECTION.size() ; i ++ ){
                            if(MembershipPlanListAdapter.SUBJECT_SELECTION.get(i).getSubjectId().equalsIgnoreCase(arrayListSubject.get(position).getSubjectId())){
                                MembershipPlanListAdapter.SUBJECT_SELECTION.remove(i);
                            }
                        }
                    }
                }
                //Toast.makeText(activity, "Total selection "+totalSelection, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        //return arrayListTimeSlot.size();
        return arrayListSubject.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{



        @BindView(R.id.checkbox_name)
        CheckBox checkBoxName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);

        }
    }

    public  void updateList(List<SubjectDetails> arrayListSubject){
        this.arrayListSubject = arrayListSubject;
        notifyDataSetChanged();
    }
}