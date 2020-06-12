package com.universalapp.sankalp.learningapp.controller.chapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.model.chapter.ChapterDetails;
import com.universalapp.sankalp.learningapp.model.login.UserPlanDetail;
import com.universalapp.sankalp.learningapp.utils.Constants;
import com.universalapp.sankalp.learningapp.utils.Utils;
import com.universalapp.sankalp.learningapp.view.activities.ChapterActivity;
import com.universalapp.sankalp.learningapp.view.activities.SubChapterActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ChapterListAdapter  extends RecyclerView.Adapter<ChapterListAdapter.MyViewHolder> {

    List<ChapterDetails> arrayListChapter = new ArrayList<>();
    Activity activity;

    public ChapterListAdapter (Activity activity, List<ChapterDetails> arrayListChapter){
        this.activity = activity;
        this.arrayListChapter = arrayListChapter;
    }

    @NonNull
    @Override
    public ChapterListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new ChapterListAdapter.MyViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_standard_layout, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ChapterListAdapter.MyViewHolder viewHolder, int position) {

        //viewHolder.textViewName.setText(arrayListTimeSlot.get(i).getName());
        viewHolder.textViewSubjectName.setText(arrayListChapter.get(position).getChapterName());

        viewHolder.imageViewBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Utils.isMembershipExpired() == false) {

                    Intent intent = new Intent(activity, SubChapterActivity.class);
                    intent.putExtra(Constants.KEY_CHAPTER_ID, arrayListChapter.get(position).getChapterId());
                    intent.putExtra(Constants.KEY_SUBJECT_ID, arrayListChapter.get(position).getSubjectId());
                    intent.putExtra(Constants.KEY_MATERIAL_URL, arrayListChapter.get(position).getMaterial());
                    intent.putExtra(Constants.KEY_CHAPTER_NAME, arrayListChapter.get(position).getChapterName());
                    activity.startActivity(intent);
                    /*boolean isAccessAvailable = false;
                    List<UserPlanDetail> userPlanDetailArrayList = Constants.USER_LOGIN_RESPONSE.getUserPlanDetail();
                    if (Constants.USER_LOGIN_RESPONSE.getUserPlan().getAccessLevel().equals("c")) {
                        for (int i = 0; i < userPlanDetailArrayList.size(); i++) {
                            if (arrayListChapter.get(position).getChapterId().equals(userPlanDetailArrayList.get(i).getChapterId())) {
                                isAccessAvailable = true;
                            }
                        }
                    } else {
                        isAccessAvailable = true;
                    }

                    if (isAccessAvailable) {

                    } else {
                        Toast.makeText(activity, "Upgrade plan to open this chapter", Toast.LENGTH_SHORT).show();

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
        return arrayListChapter.size();
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

    public  void updateList(List<ChapterDetails> arrayListChapter){
        this.arrayListChapter = arrayListChapter;
        notifyDataSetChanged();
    }
}