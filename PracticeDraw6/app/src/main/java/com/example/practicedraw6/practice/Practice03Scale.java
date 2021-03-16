package com.example.practicedraw6.practice;

import android.content.Context;

import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.practicedraw6.R;

public class Practice03Scale extends RelativeLayout {
    Button animateBt;
    ImageView imageView;
    int count = 0;

    public Practice03Scale(Context context) {
        super(context);
    }

    public Practice03Scale(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice03Scale(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
                // 在这里处理点击事件，通过 View.animate().scaleX/Y() 来让 View 放缩
                int state = count++ % 4;
                switch (state) {
                    case 0:
                        //参数是倍数
                        imageView.animate().scaleXBy(.5f);
                        break;
                    case 1:
                        imageView.animate().scaleX(1f);
                        break;
                    case 2:
                        imageView.animate().scaleYBy(-0.5f);
                        break;
                    default:
                        imageView.animate().scaleY(1f);

                }
            }
        });
    }
}
