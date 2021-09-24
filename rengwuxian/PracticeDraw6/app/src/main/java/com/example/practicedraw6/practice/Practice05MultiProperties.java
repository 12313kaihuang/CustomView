package com.example.practicedraw6.practice;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.practicedraw6.R;
import com.example.practicedraw6.Utils;

public class Practice05MultiProperties extends ConstraintLayout {
    Button animateBt;
    ImageView imageView;

    int state = 0;

    public Practice05MultiProperties(Context context) {
        super(context);
    }

    public Practice05MultiProperties(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice05MultiProperties(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        animateBt = (Button) findViewById(R.id.animateBt);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setScaleX(0);
        imageView.setScaleY(0);
        imageView.setAlpha(0f);
        animateBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //  在这里处理点击事件，同时对多个属性做动画

                if ((state++ & 1) == 0) {
                    imageView.animate()
                            .alpha(1)
                            .scaleX(1)
                            .scaleY(1)
                            .rotation(360)
                            .translationXBy(Utils.dpToPixel(220));
                } else {
                    imageView.animate()
                            .alpha(0)
                            .scaleX(0)
                            .scaleY(0)
                            .rotation(0)
                            .translationX(0);
                }
            }
        });
    }
}
