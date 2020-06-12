package com.universalapp.sankalp.learningapp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.api.RestClient;
import com.universalapp.sankalp.learningapp.controller.membershipPlan.MembershipPlanListAdapter;
import com.universalapp.sankalp.learningapp.customLoader.LoaderDialog;
import com.universalapp.sankalp.learningapp.model.membershipPack.MembershipResponse;
import com.universalapp.sankalp.learningapp.utils.Constants;
import com.universalapp.sankalp.learningapp.utils.CustomDialog;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MembershipPackActivity extends AppCompatActivity {


    @BindView(R.id.text_title)
    TextView textViewTitle;
    @BindView(R.id.text_active_plan)
    TextView textViewActivePlan;
    @BindView(R.id.image_menu)
    ImageView imageViewBack;
    @BindView(R.id.recycler_membership_pack)
    RecyclerView recyclerViewMembershipPack;
    @BindView(R.id.text_no_result_found)
    TextView textViewNoResultFound;

    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_pack);

        ButterKnife.bind(this);
        MembershipPlanListAdapter.SUBJECT_SELECTION.clear();
        MembershipPlanListAdapter.CHAPTER_SELECTION.clear();

        layoutManager = new LinearLayoutManager(this);
        recyclerViewMembershipPack.setLayoutManager(layoutManager);

        getMembershipPlan();
        textViewTitle.setText("Membership Plans");
        if(Constants.USER_LOGIN_RESPONSE.getUserPlan()!=null) {
            textViewActivePlan.setText("Active plan " + Constants.USER_LOGIN_RESPONSE.getUserPlan().getPlanName());
        }else{
            textViewActivePlan.setVisibility(View.GONE);
        }
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getMembershipPlan(){

        LoaderDialog dialog = new LoaderDialog(MembershipPackActivity.this);
        dialog.showProgress();

        Map<String, String> params = new HashMap<>();
        params.put("user_id", Constants.USER_LOGIN_RESPONSE.getUser().getUserId());
        params.put("standard_id", Constants.USER_LOGIN_RESPONSE.getUser().getStandardId());
        params.put("medium_id", Constants.USER_LOGIN_RESPONSE.getUser().getMediumId());

        Call<MembershipResponse> request = RestClient.getInstance(this).getMembershipPlan(params);


        request.enqueue(new Callback<MembershipResponse>() {
            @Override
            public void onResponse(Call<MembershipResponse> call, Response<MembershipResponse> response) {
                System.out.println("Login response " + response.body().toString());
                //Toast.makeText(LoginActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();
                Intent intent;
                if(response.body().getSuccess() == 1){

                    MembershipPlanListAdapter adapter = new MembershipPlanListAdapter(MembershipPackActivity.this, response.body().getPlan());
                    recyclerViewMembershipPack.setAdapter(adapter);

                }else{
                    //CustomDialog.commonDialog(MembershipPackActivity.this, "Login fail", "Credential mismatch.", "Retry");
                }
                dialog.hideProgressBar();
                if(response.body().getPlan() == null){
                    recyclerViewMembershipPack.setVisibility(View.GONE);
                    textViewNoResultFound.setVisibility(View.VISIBLE);
                }else{
                    recyclerViewMembershipPack.setVisibility(View.VISIBLE);
                    textViewNoResultFound.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<MembershipResponse> call, Throwable t) {


                dialog.hideProgressBar();
            }
        });

    }
}
