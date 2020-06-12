package com.universalapp.sankalp.learningapp.view.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.api.Config;
import com.universalapp.sankalp.learningapp.api.RestClient;
import com.universalapp.sankalp.learningapp.customLoader.LoaderDialog;
import com.universalapp.sankalp.learningapp.model.BasicResponse;
import com.universalapp.sankalp.learningapp.model.login.LoginResponse;
import com.universalapp.sankalp.learningapp.model.login.LoginUser;
import com.universalapp.sankalp.learningapp.utils.AppPrefs;
import com.universalapp.sankalp.learningapp.utils.Constants;
import com.universalapp.sankalp.learningapp.utils.CustomDialog;
import com.universalapp.sankalp.learningapp.utils.ImageUtils;
import com.universalapp.sankalp.learningapp.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;

public class EditProfileActivity extends AppCompatActivity {


    @BindView(R.id.text_plan_name)
    TextView textViewPlanName;
    @BindView(R.id.text_access_level)
    TextView textViewAccessLevel;
    @BindView(R.id.image_quiz_status)
    ImageView imageViewQuizStatus;
    @BindView(R.id.image_video_status)
    ImageView imageViewVideoStatus;
    @BindView(R.id.image_material_status)
    ImageView imageViewMaterialStatus;

    @BindView(R.id.text_user_name)
    TextView textViewUserName;
    @BindView(R.id.text_user_number)
    TextView textViewUserNumber;
    @BindView(R.id.text_user_email)
    TextView textViewUserEmail;
    @BindView(R.id.text_active_plan)
    TextView textViewActivePlan;
    @BindView(R.id.text_subject_name)
    TextView textViewSubjectName;
    @BindView(R.id.text_validity)
    TextView textViewValidity;
    @BindView(R.id.image_edit_details)
    ImageView imageViewEditDetails;
    @BindView(R.id.image_update_profile_picture)
    ImageView imageViewUpdateprofilePicture;
    @BindView(R.id.image_profile)
    ImageView imageViewProfilePicture;

    LoginUser loginUser;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1, CROP_PHOTO = 3;
    private String userChoosenTask;
    File captureMediaFile;
    Uri cameraPhotoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ButterKnife.bind(this);

        loginUser = Constants.USER_LOGIN_RESPONSE.getUser();

        textViewUserName.setText(loginUser.getName());
        textViewUserEmail.setText(loginUser.getEmail());
        textViewUserNumber.setText(loginUser.getMobile());
        if(Constants.USER_LOGIN_RESPONSE.getStandard() != null && Constants.USER_LOGIN_RESPONSE.getMedium() != null) {
            textViewSubjectName.setText(Constants.USER_LOGIN_RESPONSE.getStandard().getStandardName() + "(" + Constants.USER_LOGIN_RESPONSE.getMedium().getMediumName() + ")");
        }
        textViewActivePlan.setText("Active Plan: " + Constants.USER_LOGIN_RESPONSE.getUserPlan().getPlanName());
        textViewValidity.setText("Membership valid till: " + Utils.formatDate(Constants.USER_LOGIN_RESPONSE.getUserPlan().getValidTo(), "yyyy-MM-dd", "dd-MM-yyyy"));
        if(Constants.USER_LOGIN_RESPONSE.getUser().getAvatar()!=null) {
            Picasso.get().load(Config.IMAGE_URL + Constants.USER_LOGIN_RESPONSE.getUser().getAvatar()).into(imageViewProfilePicture);
        }
        imageViewEditDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDetailsPopup();
            }
        });

        imageViewUpdateprofilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        if(Constants.USER_LOGIN_RESPONSE.getUserPlan().getMaterial().equals("1")){
            imageViewMaterialStatus.setImageDrawable(this.getDrawable(R.drawable.ic_payment_success));
        }else{
            imageViewMaterialStatus.setImageDrawable(this.getDrawable(R.drawable.ic_payment_fail));
        }
        if(Constants.USER_LOGIN_RESPONSE.getUserPlan().getQuiz().equals("1")){
            imageViewQuizStatus.setImageDrawable(this.getDrawable(R.drawable.ic_payment_success));
        }else{
            imageViewQuizStatus.setImageDrawable(this.getDrawable(R.drawable.ic_payment_fail));
        }
        if(Constants.USER_LOGIN_RESPONSE.getUserPlan().getVideo().equals("1")){
            imageViewVideoStatus.setImageDrawable(this.getDrawable(R.drawable.ic_payment_success));
        }else{
            imageViewVideoStatus.setImageDrawable(this.getDrawable(R.drawable.ic_payment_fail));
        }

        if(Constants.USER_LOGIN_RESPONSE.getUserPlan().getTotal().equals("0")) {
            if (Constants.USER_LOGIN_RESPONSE.getUserPlan().getAccessLevel().equals("s")) {
                textViewAccessLevel.setText("Subject allowed: All");
            } else if (Constants.USER_LOGIN_RESPONSE.getUserPlan().getAccessLevel().equals("c")) {
                textViewAccessLevel.setText("Chapter allowed: All ");
            }
        }else{
            if (Constants.USER_LOGIN_RESPONSE.getUserPlan().getAccessLevel().equals("s")) {
                textViewAccessLevel.setText("Subject allowed " + Constants.USER_LOGIN_RESPONSE.getUserPlan().getTotal());
            } else if (Constants.USER_LOGIN_RESPONSE.getUserPlan().getAccessLevel().equals("c")) {
                textViewAccessLevel.setText("Chapter allowed " + Constants.USER_LOGIN_RESPONSE.getUserPlan().getTotal());
            }
        }
    }

    private void editDetailsPopup(){
        final Dialog dialog = new Dialog(EditProfileActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.popup_edit_profile);

        EditText editTextEmail = dialog.findViewById(R.id.edit_email);
        Button buttonCancel = dialog.findViewById(R.id.button_cancel);
        Button buttonUpdate = dialog.findViewById(R.id.button_update);

        editTextEmail.setText(textViewUserEmail.getText().toString());

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile(editTextEmail.getText().toString(), dialog);
            }
        });

        dialog.show();
    }

    private void updateProfile(String email, Dialog popupDialog){
        LoaderDialog dialog = new LoaderDialog(EditProfileActivity.this);
        dialog.showProgress();

        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("user_id", Constants.USER_LOGIN_RESPONSE.getUser().getUserId());
        params.put("location", "");
        Call<BasicResponse> request = RestClient.getInstance(this).updateProfile(params);

        request.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                System.out.println("Login response " + response.body().toString());
                //Toast.makeText(EditProfileActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();
                Intent intent;
                dialog.hideProgressBar();
                if(response.body().getSuccess() == 1){

                    loginUser.setEmail(email);
                    Constants.USER_LOGIN_RESPONSE.setUser(loginUser);

                    AppPrefs.getInstance(EditProfileActivity.this).setLoginUserDetails(new Gson().toJson(Constants.USER_LOGIN_RESPONSE));
                    textViewUserEmail.setText(Constants.USER_LOGIN_RESPONSE.getUser().getEmail());

                    Toast.makeText(EditProfileActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    popupDialog.dismiss();
                }else{
                    CustomDialog.commonDialog(EditProfileActivity.this, "Login fail", "Credential mismatch.", "Retry");
                }

            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {


                dialog.hideProgressBar();
            }
        });
    }
    private void updateProfilePicture(String base64){
        LoaderDialog dialog = new LoaderDialog(EditProfileActivity.this);
        dialog.showProgress();

        Map<String, String> params = new HashMap<>();
        params.put("avatar", base64);
        params.put("user_id", Constants.USER_LOGIN_RESPONSE.getUser().getUserId());
        Call<BasicResponse> request = RestClient.getInstance(this).updateAvatar(params);

        request.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {

                //Toast.makeText(EditProfileActivity.this, ""+response.body().getData().getUser().getUserName(), Toast.LENGTH_SHORT).show();
                Intent intent;
                dialog.hideProgressBar();
                if(response != null) {
                    if (response.body().getSuccess() == 1) {

                        Toast.makeText(EditProfileActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        login();
                    } else {
                        CustomDialog.commonDialog(EditProfileActivity.this, "Login fail", "Credential mismatch.", "Retry");
                    }
                }else{
                    CustomDialog.commonDialog(EditProfileActivity.this, "Update fail", "Not able to upload image properly, please try again.", "Retry");
                }

            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {


                dialog.hideProgressBar();
            }
        });
    }

    private void login(){
        LoaderDialog dialog = new LoaderDialog(EditProfileActivity.this);
        dialog.showProgress();

        Map<String, String> params = new HashMap<>();
        params.put("mobile", Constants.USER_LOGIN_RESPONSE.getUser().getMobile());
        params.put("standard_id", Constants.USER_LOGIN_RESPONSE.getUser().getStandardId());
        Call<LoginResponse> request = RestClient.getInstance(this).login(params);

        request.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                Intent intent;
                dialog.hideProgressBar();
                if(response != null) {
                    if (response.body().getSuccess() == 1) {

                        AppPrefs.getInstance(EditProfileActivity.this).setIsLogin(true);
                        AppPrefs.getInstance(EditProfileActivity.this).setLoginUserDetails(new Gson().toJson(response.body()));
                        Constants.USER_LOGIN_RESPONSE = response.body();

                        if (Constants.USER_LOGIN_RESPONSE.getUser().getAvatar() != null) {
                            Picasso.get().load(Config.IMAGE_URL + Constants.USER_LOGIN_RESPONSE.getUser().getAvatar()).into(imageViewProfilePicture);
                        }
                    } else {
                        CustomDialog.commonDialog(EditProfileActivity.this, "Login fail", "Credential mismatch.", "Retry");
                    }
                }else{

                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {


                dialog.hideProgressBar();
            }
        });
    }
    private void selectImage() {
        final CharSequence[] items = {  "Choose from Library", "Cancel" };
        //"Take Photo",
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (ContextCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    Utils.readStoragePermission(EditProfileActivity.this);

                }else{
                    if (items[item].equals("Take Photo")) {
                        if (ContextCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                            Utils.cameraPermission(EditProfileActivity.this);

                        }else {
                            userChoosenTask = "Take Photo";
                            cameraIntent();
                        }
                    } else if (items[item].equals("Choose from Library")) {
                        userChoosenTask ="Choose from Library";
                        galleryIntent();
                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }



            }
        });
        builder.show();
    }
    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureMediaFile = ImageUtils.getOutputMediaFile(getApplicationContext());


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION | FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, captureMediaFile);
        } else {
            cameraPhotoUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", captureMediaFile);
            intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraPhotoUri);
        }

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CAMERA);
        } else {
            //new AlertDialogMessage(getApplicationContext()).showAToast(R.string.camera_unavailable);
            System.out.println("camera exception - unavailable");

        }

    }

    private void galleryIntent()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECT_FILE);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            if(data!=null) {
                onCaptureImageResult(data);
            }else{
                updateProfilePicture(Utils.uriToBase64(cameraPhotoUri, EditProfileActivity.this));
            }
        } else if (requestCode == SELECT_FILE && resultCode == RESULT_OK) {
            if(data != null) {
                onSelectFromGalleryResult(data);
            }
        }
    }

    private void onCaptureImageResult(Intent data) {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

        File file;
        file = new File(Environment.getExternalStorageDirectory() + File.separator + "img.jpg");
        FileOutputStream fo;
        try {
            file.createNewFile();
            fo = new FileOutputStream(file);
            fo.write(Utils.bitmapToByteArray(thumbnail));
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateProfilePicture(Utils.uriToBase64(Uri.fromFile(file), EditProfileActivity.this));
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        if (data != null) {
            try {
                updateProfilePicture(Utils.uriToBase64(data.getData(), EditProfileActivity.this));

                /*CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);*/

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //imageViewUserProfile.setImageBitmap(bm);
    }
}
