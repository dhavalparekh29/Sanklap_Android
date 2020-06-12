package com.universalapp.sankalp.learningapp.view.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.universalapp.sankalp.learningapp.BuildConfig;
import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.api.Config;
import com.universalapp.sankalp.learningapp.api.RestClient;
import com.universalapp.sankalp.learningapp.controller.subject.SubjectListAdapter;
import com.universalapp.sankalp.learningapp.customLoader.LoaderDialog;
import com.universalapp.sankalp.learningapp.model.login.LoginResponse;
import com.universalapp.sankalp.learningapp.model.subject.SubjectResponse;
import com.universalapp.sankalp.learningapp.utils.AppPrefs;
import com.universalapp.sankalp.learningapp.utils.Constants;
import com.universalapp.sankalp.learningapp.utils.CustomDialog;
import com.universalapp.sankalp.learningapp.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {


    @BindView(R.id.image_menu)
    ImageView imageViewMenu;
    @BindView(R.id.image_profile)
    ImageView imageViewProfile;

    @BindView(R.id.text_user_name)
    TextView textViewUserName;
    @BindView(R.id.text_nav_user_name)
    TextView textViewNavigationUserName;
    @BindView(R.id.text_user_standard)
    TextView textViewNavigationUserStandard;
    @BindView(R.id.text_app_version)
    TextView textViewAppVersion;

    @BindView(R.id.recycler_subject_list)
    RecyclerView recyclerViewSubjectList;

    @BindView(R.id.layout_logout)
    LinearLayout linearLayoutLogout;
    @BindView(R.id.layout_profile)
    LinearLayout linearLayoutProfile;
    @BindView(R.id.layout_quiz)
    LinearLayout linearLayoutQuiz;
    @BindView(R.id.layout_test_report)
    LinearLayout linearLayoutTestReport;
    @BindView(R.id.layout_membership)
    LinearLayout linearLayoutMembership;
    @BindView(R.id.layout_contact_us)
    LinearLayout linearLayoutContactUs;
    @BindView(R.id.layout_help)
    LinearLayout linearLayoutHelp;
    @BindView(R.id.layout_general_leaders_board)
    LinearLayout linearLayoutGeneralLeadersBoard;
    @BindView(R.id.layout_share_app)
    LinearLayout linearLayoutShareApp;
    @BindView(R.id.layout_rate_app)
    LinearLayout linearLayoutRateApp;

    @BindView(R.id.layout_support_education)
    LinearLayout linearLayoutEducation;
    @BindView(R.id.layout_support_technical)
    LinearLayout linearLayoutTechnical;
    @BindView(R.id.layout_support_callback)
    LinearLayout linearLayoutCallback;
    @BindView(R.id.layout_support_email)
    LinearLayout linearLayoutEmail;

    DrawerLayout drawer;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        layoutManager = new LinearLayoutManager(this);
        recyclerViewSubjectList.setLayoutManager(layoutManager);

        textViewUserName.setText("Welcome "+Constants.USER_LOGIN_RESPONSE.getUser().getName());
        textViewNavigationUserName.setText(Constants.USER_LOGIN_RESPONSE.getUser().getName());
        if (Constants.USER_LOGIN_RESPONSE.getStandard() != null) {
            textViewNavigationUserStandard.setText(Constants.USER_LOGIN_RESPONSE.getStandard().getStandardName());
        }
        textViewAppVersion.setText("v"+Utils.getAppVersionName()+"("+Utils.getAppVersionCode()+")");

        imageViewMenu.setOnClickListener(clickListener);
        linearLayoutLogout.setOnClickListener(clickListener);
        linearLayoutProfile.setOnClickListener(clickListener);
        linearLayoutQuiz.setOnClickListener(clickListener);
        linearLayoutTestReport.setOnClickListener(clickListener);
        linearLayoutMembership.setOnClickListener(clickListener);
        linearLayoutContactUs.setOnClickListener(clickListener);
        linearLayoutHelp.setOnClickListener(clickListener);
        linearLayoutGeneralLeadersBoard.setOnClickListener(clickListener);
        linearLayoutShareApp.setOnClickListener(clickListener);
        linearLayoutRateApp.setOnClickListener(clickListener);
        linearLayoutEducation.setOnClickListener(clickListener);
        linearLayoutTechnical.setOnClickListener(clickListener);
        linearLayoutCallback.setOnClickListener(clickListener);
        linearLayoutEmail.setOnClickListener(clickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSubjectList();

        if(Constants.USER_LOGIN_RESPONSE.getUser().getAvatar()!=null) {
            Picasso.get().load(Config.IMAGE_URL + Constants.USER_LOGIN_RESPONSE.getUser().getAvatar()).into(imageViewProfile);
        }

        if(Utils.isMembershipExpired() == true) {
            Toast.makeText(this, "Your membership plan has been expired, please upgrade your membership plan", Toast.LENGTH_LONG).show();
        }
        updateUserDetails();
    }
    private void updateUserDetails(){


        Map<String, String> params = new HashMap<>();
        params.put("mobile", Constants.USER_LOGIN_RESPONSE.getUser().getMobile());
        params.put("standard_id", Constants.USER_LOGIN_RESPONSE.getUser().getStandardId());
        Call<LoginResponse> request = RestClient.getInstance(this).login(params);

        request.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                System.out.println("Login response " + response.body().toString());
                //Toast.makeText(LoginActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();
                Intent intent;
                if(response.body().getSuccess() == 1){

                    AppPrefs.getInstance(HomeActivity.this).setIsLogin(true);
                    AppPrefs.getInstance(HomeActivity.this).setLoginUserDetails(new Gson().toJson(response.body()));
                    Constants.USER_LOGIN_RESPONSE = response.body();

                }else{
                    //CustomDialog.commonDialog(HomeActivity.this, "Login fail", "Credential mismatch.", "Retry");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }
    private void getSubjectList(){
        LoaderDialog dialog = new LoaderDialog(HomeActivity.this);
        dialog.showProgress();

        String params = Constants.USER_LOGIN_RESPONSE.getUser().getUserId();
        Call<SubjectResponse> request = RestClient.getInstance(this).getSubjectByuserPlan(params
                , Constants.USER_LOGIN_RESPONSE.getUserPlan().getOrderId()
                , Constants.USER_LOGIN_RESPONSE.getUser().getStandardId()
                , Constants.USER_LOGIN_RESPONSE.getUser().getMediumId());

        request.enqueue(new Callback<SubjectResponse>() {
            @Override
            public void onResponse(Call<SubjectResponse> call, Response<SubjectResponse> response) {
                System.out.println("Login response " + response.body().toString());
                //Toast.makeText(LoginActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();
                Intent intent;
                if(response.body().getSuccess() == 1){

                    SubjectListAdapter adapter = new SubjectListAdapter(HomeActivity.this, response.body().getSubject());
                    recyclerViewSubjectList.setAdapter(adapter);

                }else{
                    CustomDialog.commonDialog(HomeActivity.this, "Login fail", "Credential mismatch.", "Retry");
                }
                dialog.hideProgressBar();
            }

            @Override
            public void onFailure(Call<SubjectResponse> call, Throwable t) {


                dialog.hideProgressBar();
            }
        });
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent;
            switch (view.getId()){
                case R.id.image_menu:

                    if(drawer.isDrawerOpen(GravityCompat.START)){
                        drawer.closeDrawer(Gravity.RIGHT);
                    }else{
                        drawer.openDrawer(Gravity.LEFT);
                    }
                    break;

                case R.id.layout_logout:

                    AppPrefs.getInstance(HomeActivity.this).setIsLogin(false);
                    AppPrefs.getInstance(HomeActivity.this).setIsOtpVerified(false);
                    AppPrefs.getInstance(HomeActivity.this).setLoginUserDetails("");

                    intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                    break;

                case R.id.layout_membership:

                    intent = new Intent(HomeActivity.this, MembershipPackActivity.class);
                    startActivity(intent);

                    break;

                case R.id.layout_profile:

                    intent = new Intent(HomeActivity.this, EditProfileActivity.class);
                    startActivity(intent);

                    break;
                case R.id.layout_general_leaders_board:

                    intent = new Intent(HomeActivity.this, LeadersBoardActivity.class);
                    intent.putExtra(Constants.KEY_LEADERS_BOARD_TYPE, Constants.TEST_TYPE_GENERAL_WISE);
                    startActivity(intent);

                    break;

                case R.id.layout_quiz:

                    if(Utils.isMembershipExpired() == false) {
                        if (Constants.USER_LOGIN_RESPONSE.getUserPlan().getQuiz().equals("1")) {
                            Constants.SELECTED_TEST_SUBJECT = "All subject test";
                            Constants.SELECTED_TEST_NAME = "General test";

                            intent = new Intent(HomeActivity.this, TestInstructionActivity.class);
                            intent.putExtra(Constants.KEY_TEST_TYPE, Constants.TEST_TYPE_GENERAL_WISE);
                            startActivity(intent);
                        } else {
                            Toast.makeText(HomeActivity.this, "Upgrade plan to participate in quiz", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(HomeActivity.this, "Your membership plan has been expired, please upgrade your membership plan", Toast.LENGTH_SHORT).show();
                    }

                    break;


                case R.id.layout_test_report:

                    intent = new Intent(HomeActivity.this, TestReportActivity.class);
                    startActivity(intent);

                    break;

                case R.id.layout_contact_us:

                    intent = new Intent(HomeActivity.this, ContactUsActivity.class);
                    intent.putExtra(Constants.KEY_CONTACT_US, "c");
                    startActivity(intent);

                    break;

                case R.id.layout_help:

                    intent = new Intent(HomeActivity.this, ContactUsActivity.class);
                    intent.putExtra(Constants.KEY_CONTACT_US, "h");
                    startActivity(intent);

                    break;

                case R.id.layout_share_app:
                    try {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Sankalp Learning App");
                        String shareMessage= "\nLet me recommend you this application\n\n";
                        shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                        startActivity(Intent.createChooser(shareIntent, "choose one"));
                    } catch(Exception e) {
                        //e.toString();
                    }
                    break;

                case R.id.layout_rate_app:
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + HomeActivity.this.getPackageName())));

                    } catch(Exception e) {
                        //e.toString();
                    }

                case  R.id.layout_support_education:
                    intent = new Intent(HomeActivity.this, EducationalSupportActivity.class);
                    intent.putExtra(Constants.KEY_SUPPORT_TYPE, Constants.SUPPORT_CONTACT_EDUCATION);
                    startActivity(intent);
                    break;
                case  R.id.layout_support_technical:
                    intent = new Intent(HomeActivity.this, TechnicalSupportActivity.class);
                    intent.putExtra(Constants.KEY_SUPPORT_TYPE, Constants.SUPPORT_CONTACT_TECHNICAL);
                    startActivity(intent);
                    break;
                case  R.id.layout_support_callback:
                    intent = new Intent(HomeActivity.this, SupportActivity.class);
                    intent.putExtra(Constants.KEY_SUPPORT_TYPE, Constants.SUPPORT_CONTACT_CALLBACK);
                    startActivity(intent);
                    break;
                case  R.id.layout_support_email:
                    intent = new Intent(HomeActivity.this, SupportActivity.class);
                    intent.putExtra(Constants.KEY_SUPPORT_TYPE, Constants.SUPPORT_CONTACT_EMAIL);
                    startActivity(intent);
                    break;
            }
        }
    };
}
