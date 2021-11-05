package com.kabha.foodrecipes;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


public abstract class BaseActivity extends AppCompatActivity {

    public ProgressBar mProgressBar;


    @Override
    public void setContentView(int layoutResID) {
        //since the constraintLayout is the parent of this activityLayout
        @SuppressLint("InflateParams") ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout frameLayout = constraintLayout.findViewById(R.id.activity_content);
        mProgressBar = constraintLayout.findViewById(R.id.progress_bar);
        //it is going to associate the frameLayout with the BaseActivity
        //so the frameLayout acts like a container for any activities that extend these Class
        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        super.setContentView(layoutResID);
    }

    //control the visibility of the ProgressBar
    public void showProgressBar(boolean visibility){
        mProgressBar.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }

}
