package com.androidwithshiv.spinthewheel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button spinBtn;
    private ImageView spinWheel;
    private LottieAnimationView win1Lt, win2Lt, lossLt;
    private String[] resultArr = {"WIN","LOSS","WIN","LOSS","WIN","LOSS","WIN","LOSS"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        spinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinTheWheel();
            }
        });
    }
    private void spinTheWheel(){
        int random = new Random().nextInt(360);
        RotateAnimation rotateAnimation = new RotateAnimation(0, (4*360)-random,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(3000);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());

        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                spinBtn.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                getValue(random);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        spinWheel.startAnimation(rotateAnimation);

    }

    private void getValue(int degree){
        int i=0, startDegree=0, endDegree=45;
        String result = null;
        do{
            if(startDegree<=degree && endDegree>=degree){
                result = resultArr[i];
            }
            i++;
            startDegree+=45;
            endDegree+=45;
        }while (result==null);
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        showLottie(result);
    }
    private void showLottie(String result){
        if(result.equals("WIN")){
            win1Lt.setVisibility(View.VISIBLE);
            win2Lt.setVisibility(View.VISIBLE);
            win1Lt.playAnimation();
            win2Lt.playAnimation();
            win1Lt.addAnimatorListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(@NonNull Animator animation, boolean isReverse) {

                }

                @Override
                public void onAnimationEnd(@NonNull Animator animation, boolean isReverse) {
                    win1Lt.setVisibility(View.GONE);
                    spinBtn.setEnabled(true);
                }
            });
            win2Lt.addAnimatorListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(@NonNull Animator animation, boolean isReverse) {

                }

                @Override
                public void onAnimationEnd(@NonNull Animator animation, boolean isReverse) {
                    win2Lt.setVisibility(View.GONE);
                    spinBtn.setEnabled(true);
                }
            });
        }
        else{
            lossLt.setVisibility(View.VISIBLE);
            lossLt.playAnimation();
            lossLt.addAnimatorListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(@NonNull Animator animation, boolean isReverse) {

                }

                @Override
                public void onAnimationEnd(@NonNull Animator animation, boolean isReverse) {
                    lossLt.setVisibility(View.GONE);
                    spinBtn.setEnabled(true);
                }
            });
        }
    }
    private void init(){
        spinBtn = findViewById(R.id.button);
        spinWheel = findViewById(R.id.wheel);
        win1Lt = findViewById(R.id.win1);
        win2Lt = findViewById(R.id.win2);
        lossLt = findViewById(R.id.loss);

    }
}