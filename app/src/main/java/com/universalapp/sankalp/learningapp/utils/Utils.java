package com.universalapp.sankalp.learningapp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.paytm.pgsdk.PaytmPGService;
import com.universalapp.sankalp.learningapp.BuildConfig;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;


    public static String getAppVersionName() {

        return BuildConfig.VERSION_NAME;
    }

    public static String getAppVersionCode() {

        return BuildConfig.VERSION_CODE + "";
    }
    public static boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static String getTimeZone() {
        Calendar calendar = Calendar.getInstance();
        return String.valueOf(calendar.getTimeZone().getRawOffset() / 1000);
    }

   /* public static  boolean checkAndRequestPermissions(Activity activity) {
        int permissionSendMessage = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.SEND_SMS);

        int receiveSMS = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.RECEIVE_SMS);

        int readSMS = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.READ_SMS);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (receiveSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_MMS);
        }
        if (readSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

*/
    public static String getMinfromSec(long seconds){
        String result = "";
        try{
            result = String.valueOf(seconds/60);
            return result;
        }catch (Exception e){
            return result;
        }
    }

    public static void readStoragePermission(Activity thisActivity) {
        if (ContextCompat.checkSelfPermission(thisActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(thisActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(thisActivity,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        Constants.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);


            }


        }
    }


    public static void cameraPermission(Activity thisActivity) {
        if (ContextCompat.checkSelfPermission(thisActivity,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
                    Manifest.permission.CAMERA)) {

            } else {

                ActivityCompat.requestPermissions(thisActivity,
                        new String[]{Manifest.permission.CAMERA},
                        Constants.MY_CAMERA_PERMISSION);


            }


        }
    }

    public static void writeStoragePermission(Activity thisActivity) {
        if (ContextCompat.checkSelfPermission(thisActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(thisActivity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constants.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);


            }
        }
    }

    public static byte[] bitmapToByteArray(Bitmap bitmap) {

        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            return byteArray;
        } else {
            return null;
        }
    }

    public static String uriToBase64(Uri uri, Activity activity){
        try {
            final InputStream imageStream = activity.getContentResolver().openInputStream(uri);
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            String encodedImage = encodeImage(selectedImage);
            return encodedImage;
        }catch (Exception e){
            return "";
        }
    }
    private static String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    public static String convertSecondsToHMmSs(long seconds) {
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        long h = (seconds / (60 * 60)) % 24;
        return String.format("%d:%02d:%02d", h,m,s);
    }

    //get current date in yyyy-mmm-dd
    public static String getDateinYMD(Date date) {
        try {
            SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
            String finalString = newFormat.format(date);
            return finalString;
        } catch (Exception e) {
            return "";
        }
    }
    public static long convertYMDToTimeStamp(String value) {
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = (Date) formatter.parse(value);
            System.out.println("Today is " + date.getTime());
            return date.getTime();
        } catch (Exception e) {
            return 0;
        }
    }
    public static String formatDate(String date, String initDateFormat, String endDateFormat) {

        try {
            Date initDate = new SimpleDateFormat(initDateFormat).parse(date);
            SimpleDateFormat formatter = new SimpleDateFormat(endDateFormat);
            String parsedDate = formatter.format(initDate);

            return parsedDate;
        } catch (Exception e) {
            return "";
        }
    }
    public static long getCurrentTimeStamp() {
        return System.currentTimeMillis();
    }
    public static boolean isMembershipExpired(){
        boolean result = false;

        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date dateMembershipExpiry = sdf.parse(Constants.USER_LOGIN_RESPONSE.getUserPlan().getValidTo());
                Date dateCurrentDate = sdf.parse(getCurrentDateinYMD());

                if (dateCurrentDate.compareTo(dateMembershipExpiry) > 0) {
                    Log.i("app", "Date1 is after Date2");
                    result = true;
                }else{
                    result = false;
                }
            } catch (ParseException ex) {
                Log.v("Exception", ex.getLocalizedMessage());
            }
        }catch (Exception e){
            System.out.println("membership expiry ex: "+e.getMessage());
        }

        return result;
    }
    public static String getCurrentDateinYMD(){
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(c);
    }


    public static PaytmPGService paytmPGService(){
        /*if (BuildConfig.DEBUG) {
            return PaytmPGService.getStagingService();
        }else{
            return PaytmPGService.getProductionService();
        }*/

        return PaytmPGService.getProductionService();
    }

}
