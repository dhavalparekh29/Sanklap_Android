package com.universalapp.sankalp.learningapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.paytm.pgsdk.view.EasypayTravelBrowserFragment;
import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.api.Config;
import com.universalapp.sankalp.learningapp.customLoader.LoaderDialog;
import com.universalapp.sankalp.learningapp.utils.Constants;
import com.universalapp.sankalp.learningapp.utils.CustomDialog;

public class MaterialActivity extends AppCompatActivity {

    @BindView(R.id.text_title)
    TextView textViewTitle;
    @BindView(R.id.image_menu)
    ImageView imageViewBack;
    @BindView(R.id.webView)
    WebView webview;
    LoaderDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);

        ButterKnife.bind(this);
        //webview = (WebView) findViewById(R.id.webView);
        webview.getSettings().setJavaScriptEnabled(true);
        //webview.getSettings().setPluginsEnabled(true);
        String pdf = Config.IMAGE_URL + getIntent().getStringExtra(Constants.KEY_MATERIAL_URL);
        textViewTitle.setText(getIntent().getStringExtra(Constants.KEY_CHAPTER_NAME));

        dialog = new LoaderDialog(MaterialActivity.this);
        dialog.showProgress();
        webview.loadUrl("https://docs.google.com/gview?embedded=true&url="+pdf+"&pid=explorer&efh=false&a=v&chrome=false");
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dialog.hideProgressBar();

            }
        });
        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {

            }


        });
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
