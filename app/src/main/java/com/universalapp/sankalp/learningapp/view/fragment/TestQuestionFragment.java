package com.universalapp.sankalp.learningapp.view.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.model.quiz.QuestionDetails;
import com.universalapp.sankalp.learningapp.utils.Constants;
import com.universalapp.sankalp.learningapp.view.activities.QuizActivity;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TestQuestionFragment extends Fragment {

    @BindView(R.id.text_question)
    TextView textViewQuestion;
    @BindView(R.id.text_answer_1)
    TextView textViewAnswer1;
    @BindView(R.id.text_answer_2)
    TextView textViewAnswer2;
    @BindView(R.id.text_answer_3)
    TextView textViewAnswer3;
    @BindView(R.id.text_answer_4)
    TextView textViewAnswer4;
    @BindView(R.id.text_answer_5)
    TextView textViewAnswer5;
    @BindView(R.id.text_answer_6)
    TextView textViewAnswer6;

    QuestionDetails questionDetails;
    int position;
    QuizActivity activity;
//    SubmitQuizQuestionDetails Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position) = new SubmitQuizQuestionDetails();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.layout_test_pager, container, false);

        ButterKnife.bind(this, rootView);


        textViewQuestion.setText(questionDetails.getQuestion());

        setOptions(questionDetails);
        textViewAnswer1.setOnClickListener(clickListener);
        textViewAnswer2.setOnClickListener(clickListener);
        textViewAnswer3.setOnClickListener(clickListener);
        textViewAnswer4.setOnClickListener(clickListener);
        textViewAnswer5.setOnClickListener(clickListener);
        textViewAnswer6.setOnClickListener(clickListener);

        return rootView;
    }


    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){

                case R.id.text_answer_1:

                        if (questionDetails.getAnswer().equalsIgnoreCase("1")) {
                            Constants.SUBMIT_QUIZ_REQUEST.setTotalCorrect((Constants.SUBMIT_QUIZ_REQUEST.getTotalCorrect() + 1));
                            Constants.SUBMIT_QUIZ_REQUEST.setTotalAttempted((Constants.SUBMIT_QUIZ_REQUEST.getTotalAttempted() + 1));
                            Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setAttempted(1);
                            Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setAnswer(1);
                            Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setCorrect(1);
                            textViewAnswer1.setBackground(getActivity().getDrawable(R.drawable.shape_answer_border_green));
                            playCorrectSound();

                        } else {
                            Constants.SUBMIT_QUIZ_REQUEST.setTotalAttempted((Constants.SUBMIT_QUIZ_REQUEST.getTotalAttempted() + 1));
                            Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setAttempted(1);
                            Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setAnswer(1);
                            Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setCorrect(0);
                            textViewAnswer1.setBackground(getActivity().getDrawable(R.drawable.shape_answer_border_red));
                            playIncorrectSound();
                        }

                        textViewAnswer2.setEnabled(false);
                        textViewAnswer3.setEnabled(false);
                        textViewAnswer4.setEnabled(false);
                        textViewAnswer5.setEnabled(false);
                        textViewAnswer6.setEnabled(false);


                    //activity.nextQuestion(position);
                    break;

                case R.id.text_answer_2:
                    if(questionDetails.getAnswer().equalsIgnoreCase("2")){
                        Constants.SUBMIT_QUIZ_REQUEST.setTotalCorrect((Constants.SUBMIT_QUIZ_REQUEST.getTotalCorrect() + 1));
                        Constants.SUBMIT_QUIZ_REQUEST.setTotalAttempted((Constants.SUBMIT_QUIZ_REQUEST.getTotalAttempted() + 1));
                        Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setAttempted(1);
                        Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setAnswer(2);
                        Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setCorrect(1);
                        textViewAnswer2.setBackground(getActivity().getDrawable(R.drawable.shape_answer_border_green));
                        playCorrectSound();
                    }else{
                        Constants.SUBMIT_QUIZ_REQUEST.setTotalAttempted((Constants.SUBMIT_QUIZ_REQUEST.getTotalAttempted() + 1));
                        Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setAttempted(1);
                        Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setAnswer(2);
                        Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setCorrect(0);
                        textViewAnswer2.setBackground(getActivity().getDrawable(R.drawable.shape_answer_border_red));
                        playIncorrectSound();
                    }
                    //activity.nextQuestion(position);
                    textViewAnswer1.setEnabled(false);
                    textViewAnswer3.setEnabled(false);
                    textViewAnswer4.setEnabled(false);
                    textViewAnswer5.setEnabled(false);
                    textViewAnswer6.setEnabled(false);
                    break;

                case R.id.text_answer_3:
                    if(questionDetails.getAnswer().equalsIgnoreCase("3")){
                        Constants.SUBMIT_QUIZ_REQUEST.setTotalCorrect((Constants.SUBMIT_QUIZ_REQUEST.getTotalCorrect() + 1));
                        Constants.SUBMIT_QUIZ_REQUEST.setTotalAttempted((Constants.SUBMIT_QUIZ_REQUEST.getTotalAttempted() + 1));
                        Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setAttempted(1);
                        Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setAnswer(3);
                        Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setCorrect(1);
                        textViewAnswer3.setBackground(getActivity().getDrawable(R.drawable.shape_answer_border_green));
                        playCorrectSound();
                    }else{
                        Constants.SUBMIT_QUIZ_REQUEST.setTotalAttempted((Constants.SUBMIT_QUIZ_REQUEST.getTotalAttempted() + 1));
                        Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setAttempted(1);
                        Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setAnswer(3);
                        Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setCorrect(0);
                        textViewAnswer3.setBackground(getActivity().getDrawable(R.drawable.shape_answer_border_red));
                        playIncorrectSound();
                    }
                    textViewAnswer1.setEnabled(false);
                    textViewAnswer2.setEnabled(false);
                    textViewAnswer4.setEnabled(false);
                    textViewAnswer5.setEnabled(false);
                    textViewAnswer6.setEnabled(false);
                    //activity.nextQuestion(position);
                    break;

                case R.id.text_answer_4:
                    if(questionDetails.getAnswer().equalsIgnoreCase("4")){
                        Constants.SUBMIT_QUIZ_REQUEST.setTotalCorrect((Constants.SUBMIT_QUIZ_REQUEST.getTotalCorrect() + 1));
                        Constants.SUBMIT_QUIZ_REQUEST.setTotalAttempted((Constants.SUBMIT_QUIZ_REQUEST.getTotalAttempted() + 1));
                        Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setAttempted(1);
                        Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setAnswer(4);
                        Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setCorrect(1);
                        textViewAnswer4.setBackground(getActivity().getDrawable(R.drawable.shape_answer_border_green));
                        playCorrectSound();
                    }else{
                        Constants.SUBMIT_QUIZ_REQUEST.setTotalAttempted((Constants.SUBMIT_QUIZ_REQUEST.getTotalAttempted() + 1));
                        Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setAttempted(1);
                        Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setAnswer(4);
                        Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setCorrect(0);
                        textViewAnswer4.setBackground(getActivity().getDrawable(R.drawable.shape_answer_border_red));
                        playIncorrectSound();
                    }
                    textViewAnswer2.setEnabled(false);
                    textViewAnswer3.setEnabled(false);
                    textViewAnswer1.setEnabled(false);
                    textViewAnswer5.setEnabled(false);
                    textViewAnswer6.setEnabled(false);
                    //activity.nextQuestion(position);
                    break;

                case R.id.text_answer_5:
                    if(questionDetails.getAnswer().equalsIgnoreCase("5")){
                        Constants.SUBMIT_QUIZ_REQUEST.setTotalCorrect((Constants.SUBMIT_QUIZ_REQUEST.getTotalCorrect() + 1));
                        Constants.SUBMIT_QUIZ_REQUEST.setTotalAttempted((Constants.SUBMIT_QUIZ_REQUEST.getTotalAttempted() + 1));
                        Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setAttempted(1);
                        Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setAnswer(5);
                        Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setCorrect(1);
                        textViewAnswer5.setBackground(getActivity().getDrawable(R.drawable.shape_answer_border_green));
                        playCorrectSound();
                    }else{
                        Constants.SUBMIT_QUIZ_REQUEST.setTotalAttempted((Constants.SUBMIT_QUIZ_REQUEST.getTotalAttempted() + 1));
                        Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setAttempted(1);
                        Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setAnswer(5);
                        Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setCorrect(0);
                        textViewAnswer5.setBackground(getActivity().getDrawable(R.drawable.shape_answer_border_red));
                        playIncorrectSound();
                    }
                    //activity.nextQuestion(position);
                    textViewAnswer2.setEnabled(false);
                    textViewAnswer3.setEnabled(false);
                    textViewAnswer4.setEnabled(false);
                    textViewAnswer1.setEnabled(false);
                    textViewAnswer6.setEnabled(false);
                    break;

                case R.id.text_answer_6:
                    if(questionDetails.getAnswer().equalsIgnoreCase("6")){
                        Constants.SUBMIT_QUIZ_REQUEST.setTotalCorrect((Constants.SUBMIT_QUIZ_REQUEST.getTotalCorrect() + 1));
                        Constants.SUBMIT_QUIZ_REQUEST.setTotalAttempted((Constants.SUBMIT_QUIZ_REQUEST.getTotalAttempted() + 1));
                        Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setAttempted(1);
                        Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setAnswer(6);
                        Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setCorrect(1);
                        textViewAnswer6.setBackground(getActivity().getDrawable(R.drawable.shape_answer_border_green));
                        playCorrectSound();
                    }else{
                        Constants.SUBMIT_QUIZ_REQUEST.setTotalAttempted((Constants.SUBMIT_QUIZ_REQUEST.getTotalAttempted() + 1));
                        Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setAttempted(1);
                        Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setAnswer(6);
                        Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position).setCorrect(0);
                        textViewAnswer6.setBackground(getActivity().getDrawable(R.drawable.shape_answer_border_red));
                        playIncorrectSound();
                    }
                    //activity.nextQuestion(position);
                    textViewAnswer2.setEnabled(false);
                    textViewAnswer3.setEnabled(false);
                    textViewAnswer4.setEnabled(false);
                    textViewAnswer5.setEnabled(false);
                    textViewAnswer1.setEnabled(false);
                    break;
            }
        }
    };
    private void setOptions(QuestionDetails questionDetails){
        textViewAnswer1.setVisibility(View.GONE);
        textViewAnswer2.setVisibility(View.GONE);
        textViewAnswer3.setVisibility(View.GONE);
        textViewAnswer4.setVisibility(View.GONE);
        textViewAnswer5.setVisibility(View.GONE);
        textViewAnswer6.setVisibility(View.GONE);
        int noOfptions = Integer.parseInt(questionDetails.getNoOfOptions());
        for(int i = 0 ; i < noOfptions ; i ++){
            switch ((i+1)){
                case 1:

                    textViewAnswer1.setVisibility(View.VISIBLE);
                    textViewAnswer1.setText(questionDetails.getOption1());

                    break;
                case 2:

                    textViewAnswer2.setVisibility(View.VISIBLE);
                    textViewAnswer2.setText(questionDetails.getOption2());

                    break;
                case 3:

                    textViewAnswer3.setVisibility(View.VISIBLE);
                    textViewAnswer3.setText(questionDetails.getOption3());

                    break;
                case 4:

                    textViewAnswer4.setVisibility(View.VISIBLE);
                    textViewAnswer4.setText(questionDetails.getOption4());

                    break;
                case 5:

                    textViewAnswer5.setVisibility(View.VISIBLE);
                    textViewAnswer5.setText(questionDetails.getOption5());

                    break;
                case 6:

                    textViewAnswer6.setVisibility(View.VISIBLE);
                    textViewAnswer6.setText(questionDetails.getOption6());

                    break;
            }
        }

    }

    public void bindData(QuestionDetails questionDetails, int position, QuizActivity activity){
        this.questionDetails = questionDetails;
        this.position = position;
        this.activity = activity;
        //Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position) = Constants.SUBMIT_QUIZ_REQUEST.getQuestions().get(position);
    }

    private void playCorrectSound(){
        final MediaPlayer mp = MediaPlayer.create(activity, R.raw.audio_correct);
        mp.start();
    }

    private void playIncorrectSound(){
        final MediaPlayer mp = MediaPlayer.create(activity, R.raw.audio_incorrect);
        mp.start();
    }
}
