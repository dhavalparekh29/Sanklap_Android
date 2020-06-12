package com.universalapp.sankalp.learningapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.universalapp.sankalp.learningapp.R;
import com.universalapp.sankalp.learningapp.controller.pagerAdapter.TestReportPagerAdapter;
import com.universalapp.sankalp.learningapp.view.fragment.TestReportChapterFragment;
import com.universalapp.sankalp.learningapp.view.fragment.TestReportGeneralFragment;
import com.universalapp.sankalp.learningapp.view.fragment.TestReportSubjectFragment;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TestReportActivity extends AppCompatActivity {

    @BindView(R.id.image_menu)
    ImageView imageViewBack;

    @BindView(R.id.htab_tabs)
    TabLayout tabLayout;
    @BindView(R.id.text_title)
    TextView textViewTitle;

    @BindView(R.id.viewPager_report)
    ViewPager viewPagerProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_report);

        ButterKnife.bind(this);
        textViewTitle.setText("Test reports");

        viewPagerProfile.setOffscreenPageLimit(4);
        setupViewPager(viewPagerProfile);
        tabLayout.setupWithViewPager(viewPagerProfile);
        changeTabsFont();
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        TestReportPagerAdapter adapter = new TestReportPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new TestReportGeneralFragment(), "General");
        adapter.addFrag(new TestReportSubjectFragment(), "Subject");
        adapter.addFrag(new TestReportChapterFragment(), "Chapter");
        viewPager.setAdapter(adapter);
    }

    private void changeTabsFont() {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(ResourcesCompat.getFont(this, R.font.montserrat_regular));
                }
            }
        }
    }

}
