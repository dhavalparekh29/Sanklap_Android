package com.universalapp.sankalp.learningapp.api;

public class Config {

    public static final String BASE_URL = "http://sankalp.diwalipurayouth.com/api/";
    //public static final String BASE_URL = "http://www.diwalipurayouth.com/sahjanand/api/";
    public static final String IMAGE_URL = "http://www.diwalipurayouth.com/sahjanand/";


    public static final String LOGIN_URL = "user/login";
    public static final String UPDATE_PROFILE_URL = "user/profile/edit";
    public static final String UPDATE_AVTAR_URL = "user/avatar/edit";
    public static final String SIGNUP_URL = "user/register";
    public static final String STANDARD_URL = "standard/get";
    public static final String SUBJECT_URL = "subject/get_by_user_id";
    public static final String GET_SUBJECT_BY_USER_PLAN_URL = "subject/get_by_user_plan";
    public static final String CHAPTER_URL = "chapter/get_by_standard_medium_subject_id";
    public static final String GET_CHAPTER_BY_USER_PLAN_URL = "chapter/get_by_user_plan";
    public static final String VIDEO_URL = "videos/get_by_standard_medium_subject_chapter_id";
    public static final String QUIZ_CHAPTER_QUESTIONS_URL = "questions/get_by_standard_medium_subject_chapter_id";
    public static final String QUIZ_SUBJECT_QUESTIONS_URL = "questions/get_by_standard_medium_subject_id";
    public static final String QUIZ_GENERAL_QUESTIONS_URL = "questions/get_by_standard_medium_id";
    public static final String MEDIUM_URL = "medium/get_by_course_id";
    public static final String GET_OTP = "otp/generate";
    public static final String VALIDATE_OTP = "otp/validate";
    public static final String SUBMIT_QUIZ = "quiz/submit";
    public static final String GET_MEMBERSHIP_PLAN = "plan/get";

    public static final String TEST_REPORT_GENERAL = "quiz/list_general";
    public static final String TEST_REPORT_SUBJECT = "quiz/list_subject_wise";
    public static final String TEST_REPORT_CHAPTER = "quiz/list_chapter_wise";

    public static final String SUBMIT_MEMBERSHIP_PLAN = "user_plan/process";
    public static final String VERIFY_PAYTM_TRANSACTION = "user_plan/verify";

    public static final String CONTACT_US = "contact/inquiry";
    public static final String HELP = "contact/help";


    public static final String LEADERS_BOARD_GENERAL = "quiz/list_general_leaderboard";
    public static final String LEADERS_BOARD_SUBJECT = "quiz/list_subject_wise_leaderboard";
    public static final String LEADERS_BOARD_CHAPTER = "quiz/list_chapter_wise_leaderboard";


    public static final String SUPPORT_CONTACT_EDUCATION = "contact/support/educational";
    public static final String SUPPORT_CONTACT_TECHNICAL = "contact/support/technical";
    public static final String SUPPORT_CONTACT_CALLBACK = "contact/support/callback";
    public static final String SUPPORT_CONTACT_EMAIL = "contact/support/email";






}
