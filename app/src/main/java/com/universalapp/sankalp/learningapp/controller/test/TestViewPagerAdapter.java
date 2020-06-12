package com.universalapp.sankalp.learningapp.controller.test;


import com.universalapp.sankalp.learningapp.model.quiz.QuestionDetails;
import com.universalapp.sankalp.learningapp.view.activities.QuizActivity;
import com.universalapp.sankalp.learningapp.view.fragment.TestQuestionFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TestViewPagerAdapter extends FragmentStatePagerAdapter {


    List<QuestionDetails> arrayListQuestion = new ArrayList<>();
    QuizActivity activity;

    public TestViewPagerAdapter(FragmentManager fm, List<QuestionDetails> arrayListQuestion, QuizActivity activity) {
        super(fm);
        this.arrayListQuestion = arrayListQuestion;
        this.activity = activity;
    }

    @Override
    public Fragment getItem(int position) {
        TestQuestionFragment fragment = new TestQuestionFragment();
        fragment.bindData(arrayListQuestion.get(position), position, activity);

        return fragment;
    }

    @Override
    public int getCount() {
        return arrayListQuestion.size();
    }

    public void updateList(List<QuestionDetails> arrayListQuestion) {
        arrayListQuestion = arrayListQuestion;
        notifyDataSetChanged();
    }

    /*public void setPageColor(int position, int vibrantColor, int mutedColor) {
        Constant.vibrantColors[position] = vibrantColor;
        Constant.mutedColors[position] = mutedColor;
    }*/
}