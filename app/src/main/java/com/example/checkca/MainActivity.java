package com.example.checkca;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        int defaultValue = getResources().getInteger(R.integer.saved_high_score_default_key);
        int highScore = sharedPref.getInt(getString(R.string.saved_high_score_key), defaultValue);
        TextView tvInt = (TextView)findViewById(R.id.textView2);
        tvInt.setText(highScore+"");

        final Activity mContext = this;

        ImageButton Cbtn1 = (ImageButton)findViewById(R.id.imageButton2);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.sound);

        Cbtn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView tvInt = (TextView)findViewById(R.id.textView2);
                int nb = Integer.parseInt(tvInt.getText().toString()) + 1;
                tvInt.setText(nb+"");
                SharedPreferences sharedPref = mContext.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt(getString(R.string.saved_high_score_key), nb);
                editor.commit();
                ImageView coeur = (ImageView) findViewById(R.id.imageView2);
                ObjectAnimator animation = ObjectAnimator.ofFloat(coeur, "translationX", -400f);
                animation.setDuration(2000);
                animation.start();
                mp.start();
                animation.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animation.removeListener(this);
                        animation.setDuration(0);
                        animation.setInterpolator(new ReverseInterpolator());
                        animation.start();
                    }
                });
            }
        });
    }
}
