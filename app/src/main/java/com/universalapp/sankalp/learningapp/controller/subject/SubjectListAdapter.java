package com.universalapp.sankalp.learningapp.controller.subject;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.api.Config;
import com.universalapp.sankalp.learningapp.controller.chapter.ChapterListAdapter;
import com.universalapp.sankalp.learningapp.model.login.UserPlanDetail;
import com.universalapp.sankalp.learningapp.model.subject.SubjectDetails;
import com.universalapp.sankalp.learningapp.utils.Constants;
import com.universalapp.sankalp.learningapp.utils.Utils;
import com.universalapp.sankalp.learningapp.view.activities.ChapterActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SubjectListAdapter extends RecyclerView.Adapter<SubjectListAdapter.MyViewHolder> {

    List<SubjectDetails> arrayListSubject = new ArrayList<>();
    Activity activity;

    public SubjectListAdapter (Activity activity, List<SubjectDetails> arrayListSubject){
        this.activity = activity;
        this.arrayListSubject = arrayListSubject;
    }

    @NonNull
    @Override
    public SubjectListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new SubjectListAdapter.MyViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_standard_layout, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull SubjectListAdapter.MyViewHolder viewHolder, int position) {

        //viewHolder.textViewName.setText(arrayListTimeSlot.get(i).getName());
        //viewHolder.textViewSubjectName.setText(arrayListSubject.get(position).getSubjectName());

        Picasso.get().load(Config.IMAGE_URL+arrayListSubject.get(position).getImage()).into(viewHolder.imageViewBackground);
        viewHolder.imageViewBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Utils.isMembershipExpired() == false) {
                    Constants.SELECTED_TEST_SUBJECT = arrayListSubject.get(position).getSubjectName();

                    Intent intent = new Intent(activity, ChapterActivity.class);
                    intent.putExtra(Constants.KEY_SUBJECT_ID, arrayListSubject.get(position).getSubjectId());
                    intent.putExtra(Constants.KEY_SUBJECT_NAME, arrayListSubject.get(position).getSubjectName());
                    activity.startActivity(intent);
                    /*boolean isAccessAvailable = false;
                    List<UserPlanDetail> userPlanDetailArrayList = Constants.USER_LOGIN_RESPONSE.getUserPlanDetail();
                    for (int i = 0; i < userPlanDetailArrayList.size(); i++) {
                        if (arrayListSubject.get(position).getSubjectId().equals(userPlanDetailArrayList.get(i).getSubjectId())) {
                            isAccessAvailable = true;
                        }
                    }
                    if (isAccessAvailable) {

                    } else {
                        Toast.makeText(activity, "Upgrade plan to open this subject", Toast.LENGTH_SHORT).show();
                    }*/
                }else{
                    Toast.makeText(activity, "Your membership plan has been expired, please upgrade your membership plan", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        //return arrayListTimeSlot.size();
        return arrayListSubject.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{



        @BindView(R.id.text_subject_name)
        TextView textViewSubjectName;
        @BindView(R.id.image_background)
        ImageView imageViewBackground;


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