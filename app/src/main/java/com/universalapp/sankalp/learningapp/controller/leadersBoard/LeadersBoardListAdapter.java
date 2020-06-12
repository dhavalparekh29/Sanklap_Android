package com.universalapp.sankalp.learningapp.controller.leadersBoard;

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
import com.universalapp.sankalp.learningapp.model.leadersBoard.LeadersBoardResponse;
import com.universalapp.sankalp.learningapp.model.leadersBoard.LeadersBoardScore;
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

public class LeadersBoardListAdapter extends RecyclerView.Adapter<LeadersBoardListAdapter.MyViewHolder> {

    List<LeadersBoardScore> arrayListLeadersBoard = new ArrayList<>();
    LeadersBoardResponse leadersBoardResponse = new LeadersBoardResponse();
    Activity activity;

    public LeadersBoardListAdapter (Activity activity, LeadersBoardResponse leadersBoardResponse){
        this.activity = activity;
        this.leadersBoardResponse = leadersBoardResponse;
        arrayListLeadersBoard = leadersBoardResponse.getScore();

    }

    @NonNull
    @Override
    public LeadersBoardListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new LeadersBoardListAdapter.MyViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_leader_board_score, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull LeadersBoardListAdapter.MyViewHolder viewHolder, int position) {


        viewHolder.textViewUserName.setText(arrayListLeadersBoard.get(position).getName());
        viewHolder.textViewUserRank.setText(arrayListLeadersBoard.get(position).getRank());
        viewHolder.textViewScore.setText(arrayListLeadersBoard.get(position).getScore());


    }

    @Override
    public int getItemCount() {
        //return arrayListTimeSlot.size();
        return arrayListLeadersBoard.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{



        @BindView(R.id.text_user_name)
        TextView textViewUserName;
        @BindView(R.id.text_score)
        TextView textViewScore;
        @BindView(R.id.text_user_rank)
        TextView textViewUserRank;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);

        }
    }

    public  void updateList(List<LeadersBoardScore> arrayListLeadersBoard){
        this.arrayListLeadersBoard = arrayListLeadersBoard;
        notifyDataSetChanged();
    }
}