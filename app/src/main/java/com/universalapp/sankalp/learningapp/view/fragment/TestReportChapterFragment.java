package com.universalapp.sankalp.learningapp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.api.RestClient;
import com.universalapp.sankalp.learningapp.controller.reportList.TestReportChapterListAdapter;
import com.universalapp.sankalp.learningapp.customLoader.LoaderDialog;
import com.universalapp.sankalp.learningapp.model.testReport.ChapterWiseReport;
import com.universalapp.sankalp.learningapp.utils.Constants;
import com.universalapp.sankalp.learningapp.utils.CustomDialog;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestReportChapterFragment  extends Fragment {

    @BindView(R.id.recycler_general_test)
    RecyclerView recyclerViewGeneralTest;
    @BindView(R.id.text_no_result_found)
    TextView textViewNoResultFound;

    RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_report_list, container, false);

        ButterKnife.bind(this, view);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewGeneralTest.setLayoutManager(layoutManager);

        getGeneralReport();
        return view;
    }

    private void getGeneralReport(){
        LoaderDialog dialog = new LoaderDialog(getActivity());
        //dialog.showProgress();


        Call<List<ChapterWiseReport>> request = RestClient.getInstance(getActivity()).getChapterTestReport(Constants.USER_LOGIN_RESPONSE.getUser().getUserId()
                , Constants.USER_LOGIN_RESPONSE.getUser().getMediumId()
                , Constants.USER_LOGIN_RESPONSE.getUser().getStandardId());


        request.enqueue(new Callback<List<ChapterWiseReport>>() {
            @Override
            public void onResponse(Call<List<ChapterWiseReport>> call, Response<List<ChapterWiseReport>> response) {
                System.out.println("Login response " + response.body().toString());
                //Toast.makeText(LoginActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();
                Intent intent;
                if(response.body().size() > 0){


                    TestReportChapterListAdapter adapter = new TestReportChapterListAdapter(getActivity(), response.body());
                    recyclerViewGeneralTest.setAdapter(adapter);

                }else{
                    //CustomDialog.commonDialog(getActivity(), getActivity().getResources().getString(R.string.app_name), "Something went wrong", "Retry");
                }

                if(response.body().size()>0){
                    recyclerViewGeneralTest.setVisibility(View.VISIBLE);
                    textViewNoResultFound.setVisibility(View.GONE);
                }else{
                    recyclerViewGeneralTest.setVisibility(View.GONE);
                    textViewNoResultFound.setVisibility(View.VISIBLE);
                }


                dialog.hideProgressBar();
            }

            @Override
            public void onFailure(Call<List<ChapterWiseReport>> call, Throwable t) {


                dialog.hideProgressBar();
            }
        });
    }
}
