package com.example.practicedraw6.practice;

import android.content.Context;

import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.practicedraw6.R;

public class Practice02Rotation extends RelativeLayout {
    Button animateBt;
    ImageView imageView;

    int count = 0;

    public Practice02Rotation(Context context) {
        super(context);
    }

    public Practice02Rotation(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice02Rotation(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        animateBt = (Button) findViewById(R.id.animateBt);
        imageView = (ImageView) findViewById(R.id.imageView);

        animateBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                // 在这里处理点击事件，通过 View.animate().rotation/X/Y() 来让 View 旋转
                int state = count++ % 6;
                switch (state) {
                    case 0:
                        //Z轴旋转
                        imageView.animate().rotationBy(180);
                        break;
                    case 1:
                        imageView.animate().rotation(0);
                        break;
                    case 2:
                        imageView.animate().rotationXBy(87);
                        break;
                    case 3:
                        imageView.animate().rotationX(0);
                        break;
                    case 4:
                        imageView.animate().rotationYBy(120);
                        break;
                    default:
                        imageView.animate().rotationY(0);
                }
            }
        });
    }
}