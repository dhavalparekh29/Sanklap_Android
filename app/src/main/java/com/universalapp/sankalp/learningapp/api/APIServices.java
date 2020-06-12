package com.universalapp.sankalp.learningapp.api;

import com.google.gson.JsonObject;
import com.universalapp.sankalp.learningapp.model.BasicResponse;
import com.universalapp.sankalp.learningapp.model.chapter.ChapterResponse;
import com.universalapp.sankalp.learningapp.model.leadersBoard.LeadersBoardResponse;
import com.universalapp.sankalp.learningapp.model.login.LoginResponse;
import com.universalapp.sankalp.learningapp.model.medium.MediumResponse;
import com.universalapp.sankalp.learningapp.model.membershipPack.CallbackChecksum;
import com.universalapp.sankalp.learningapp.model.membershipPack.MembershipRequest;
import com.universalapp.sankalp.learningapp.model.membershipPack.MembershipResponse;
import com.universalapp.sankalp.learningapp.model.membershipPack.MembershipSubmitResponse;
import com.universalapp.sankalp.learningapp.model.payment.PaytmVerifyResponse;
import com.universalapp.sankalp.learningapp.model.quiz.TestResponse;
import com.universalapp.sankalp.learningapp.model.quiz.submitQuiz.SubmitQuizRequest;
import com.universalapp.sankalp.learningapp.model.signup.SignupResponse;
import com.universalapp.sankalp.learningapp.model.standard.StandardResponse;
import com.universalapp.sankalp.learningapp.model.subject.SubjectResponse;
import com.universalapp.sankalp.learningapp.model.testReport.ChapterWiseReport;
import com.universalapp.sankalp.learningapp.model.testReport.GeneralWiseReport;
import com.universalapp.sankalp.learningapp.model.testReport.SubjectWiseReport;
import com.universalapp.sankalp.learningapp.model.video.VideoResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by user on 2/7/2018.
 */

public interface APIServices {

    // Login
    @FormUrlEncoded
    @POST(Config.LOGIN_URL)
    Call<LoginResponse> login(@FieldMap Map<String, String> params);

    // Update profile
    @FormUrlEncoded
    @POST(Config.UPDATE_PROFILE_URL)
    Call<BasicResponse> updateProfile(@FieldMap Map<String, String> params);

    // Update profile
    @FormUrlEncoded
    @POST(Config.UPDATE_AVTAR_URL)
    Call<BasicResponse> updateAvatar(@FieldMap Map<String, String> params);

    // Login
    @FormUrlEncoded
    @POST(Config.SIGNUP_URL)
    Call<LoginResponse> signup(@FieldMap Map<String, String> params);


    // get standard list
    @FormUrlEncoded
    @POST(Config.STANDARD_URL)
    Call<StandardResponse> getStandardList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Config.VALIDATE_OTP)
    Call<BasicResponse> verifyOTP(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Config.GET_OTP)
    Call<BasicResponse> genrateOTP(@FieldMap Map<String, String> params);


    // get medium list
    @GET(Config.MEDIUM_URL)
    Call<MediumResponse> getMediumList(@Query("standard_id") String param);

    // get subject list
    @GET(Config.SUBJECT_URL)
    Call<SubjectResponse> getSubject(@Query("user_id") String param);// get subject list

    @GET(Config.GET_SUBJECT_BY_USER_PLAN_URL)
    Call<SubjectResponse> getSubjectByuserPlan(@Query("user_id") String param
            , @Query("order_id") String param_order_id
            , @Query("standard_id") String param_standard_id
            , @Query("medium_id") String param_medium_id);

    @GET(Config.LEADERS_BOARD_CHAPTER)
    Call<LeadersBoardResponse> getLeadersBoardChapter(@Query("user_id") String userId
            , @Query("subject_id") String param_subject_id
            , @Query("chapter_id") String param_chapter_id
            , @Query("standard_id") String param_standard_id
            , @Query("medium_id") String param_medium_id);

    @GET(Config.LEADERS_BOARD_SUBJECT)
    Call<LeadersBoardResponse> getLeadersBoardSubject(@Query("user_id") String userId
            , @Query("subject_id") String param_subject_id
            , @Query("standard_id") String param_standard_id
            , @Query("medium_id") String param_medium_id);

    @GET(Config.LEADERS_BOARD_GENERAL)
    Call<LeadersBoardResponse> getLeadersBoardGeneral(@Query("user_id") String userId
            , @Query("standard_id") String param_standard_id
            , @Query("medium_id") String param_medium_id);


    @FormUrlEncoded
    @POST(Config.GET_MEMBERSHIP_PLAN)
    Call<MembershipResponse> getMembershipPlan(@FieldMap Map<String, String> params);

    // get chapter list
    @GET(Config.CHAPTER_URL)
    Call<ChapterResponse> getChapter(@Query("subject_id") String param_subject_id
            , @Query("medium_id") String param_medium_id
            , @Query("standard_id") String param_standard_id);

    // get chapter list
    @GET(Config.GET_CHAPTER_BY_USER_PLAN_URL)
    Call<ChapterResponse> getChapterByUserPlan(@Query("subject_id") String param_subject_id
            , @Query("medium_id") String param_medium_id
            , @Query("standard_id") String param_standard_id
            , @Query("order_id") String param_order_id
            , @Query("access_level") String param_access_level);

    // get video list
    @GET(Config.VIDEO_URL)
    Call<VideoResponse> getVideo(@Query("subject_id") String param_subject_id
            , @Query("medium_id") String param_medium_id
            , @Query("standard_id") String param_standard_id
            , @Query("chapter_id") String param_chapter_id);

    // get quiz questions list
    @GET(Config.QUIZ_CHAPTER_QUESTIONS_URL)
    Call<TestResponse> getChapterQuizQuestions(@Query("subject_id") String param_subject_id
            , @Query("medium_id") String param_medium_id
            , @Query("standard_id") String param_standard_id
            , @Query("chapter_id") String param_chapter_id);

    // get quiz questions list subject
    @GET(Config.QUIZ_SUBJECT_QUESTIONS_URL)
    Call<TestResponse> getSubjectQuizQuestions(@Query("subject_id") String param_subject_id
            , @Query("medium_id") String param_medium_id
            , @Query("standard_id") String param_standard_id);

    // get quiz questions list overall
    @GET(Config.QUIZ_GENERAL_QUESTIONS_URL)
    Call<TestResponse> getGenralQuizQuestions(@Query("medium_id") String param_medium_id
            , @Query("standard_id") String param_standard_id);


    // Contact us
    @FormUrlEncoded
    @POST(Config.CONTACT_US)
    Call<BasicResponse> contactUs(@FieldMap Map<String, String> params);

    // Education support
    @FormUrlEncoded
    @POST(Config.SUPPORT_CONTACT_EDUCATION)
    Call<BasicResponse> educationSupport(@FieldMap Map<String, String> params);

    // Technical support
    @FormUrlEncoded
    @POST(Config.SUPPORT_CONTACT_TECHNICAL)
    Call<BasicResponse> technicalSupport(@FieldMap Map<String, String> params);

    // Education support
    @FormUrlEncoded
    @POST(Config.SUPPORT_CONTACT_CALLBACK)
    Call<BasicResponse> callbackSupport(@FieldMap Map<String, String> params);

    // Education support
    @FormUrlEncoded
    @POST(Config.SUPPORT_CONTACT_EMAIL)
    Call<BasicResponse> emailSupport(@FieldMap Map<String, String> params);


    // Contact us
    @FormUrlEncoded
    @POST(Config.HELP)
    Call<BasicResponse> helpUs(@FieldMap Map<String, String> params);



    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST(Config.SUBMIT_QUIZ)
    Call<BasicResponse> submitQuiz(@Body SubmitQuizRequest submitQuizRequest);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST(Config.SUBMIT_MEMBERSHIP_PLAN)
    Call<MembershipSubmitResponse> submitMemebership(@Body MembershipRequest membershipRequest);

    @FormUrlEncoded()
    //@POST("generateChecksum.php")
    @POST("user_plan/generate")
    Call<CallbackChecksum> generateChecksum(@Field("MID") String merchantId,
                                              @Field("ORDER_ID") String orderId,
                                              @Field("CUST_ID") String customerId,
                                              @Field("CHANNEL_ID") String channelId,
                                              @Field("TXN_AMOUNT") String taxAmount,
                                              @Field("WEBSITE") String website,
                                              @Field("CALLBACK_URL") String callbackUrl,
                                              @Field("INDUSTRY_TYPE_ID") String industryType);


    // verify checksum
    @FormUrlEncoded
    @POST(Config.VERIFY_PAYTM_TRANSACTION)
    Call<PaytmVerifyResponse> verifyTransaction(@FieldMap Map<String, String> params);

    // get general test report
    @GET(Config.TEST_REPORT_GENERAL)
    Call<List<GeneralWiseReport>> getGeneralTestReport(@Query("user_id") String param_user_id
            , @Query("medium_id") String param_medium_id
            , @Query("standard_id") String param_standard_id);

    // get subject test report
    @GET(Config.TEST_REPORT_SUBJECT)
    Call<List<SubjectWiseReport>> getSubjectTestReport(@Query("user_id") String param_user_id
            , @Query("medium_id") String param_medium_id
            , @Query("standard_id") String param_standard_id);

    // get chapter test report
    @GET(Config.TEST_REPORT_CHAPTER)
    Call<List<ChapterWiseReport>> getChapterTestReport(@Query("user_id") String param_user_id
            , @Query("medium_id") String param_medium_id
            , @Query("standard_id") String param_standard_id);
}