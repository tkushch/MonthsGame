package com.example.monthsgame;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ConstraintLayout constraintLayout;
    private TextView tv;
    private int height, width;
    private ConstraintSet set;
    private Map<String, Integer> months;
    private String[] names;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        constraintLayout = findViewById(R.id.cl);

        height = getResources().getDisplayMetrics().heightPixels;
        width = getResources().getDisplayMetrics().widthPixels;
        tv = findViewById(R.id.textView);

        set = new ConstraintSet();
        set.clone(constraintLayout);

        int prevId = ConstraintSet.PARENT_ID;
        int id;
        for (int i = 1; i <= 12; i++) {

            Button b = new Button(this);
            id = View.generateViewId();
            b.setId(id);
            //add
            constraintLayout.addView(b);
            b.setOnClickListener(this);
            b.setTextSize(height / 90);

            set.connect(id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
            set.connect(id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
            if (i <= 3) set.connect(id, ConstraintSet.TOP, prevId, ConstraintSet.TOP);
            else set.connect(id, ConstraintSet.TOP, prevId, ConstraintSet.BOTTOM);
            set.connect(id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);

            set.constrainHeight(id, height / 8);
            set.constrainWidth(id, width / 3);

            //Bias
            if (i <= 3) set.setVerticalBias(id, 0.50f);
            else set.setVerticalBias(id, 0.00f);

            if (i % 3 == 1) {
                set.setHorizontalBias(id, 0.00f);
                b.setText(i + "");
            } else if (i % 3 == 2) {
                set.setHorizontalBias(id, 1.00f);
                b.setText((i + 1) + "");
            } else {
                set.setHorizontalBias(id, 0.50f);
                b.setText((i - 1) + "");
                prevId = id;
            }
        }
        set.applyTo(constraintLayout);

        months = new HashMap<>();
        names = new String[]{"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август",
                "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
        for (int i = 0; i < 12; i++) {
            months.put(names[i], i + 1);
        }
        addMonthToTV();

    }

    protected void addMonthToTV() {
        int a = 1;
        int b = 12;
        int index = (int) (Math.random() * (b - a + 1) + a) - 1;
        tv.setText(names[index]);
    }

    @Override
    public void onClick(View v) {
        if ((Integer.parseInt(((Button) v).getText().toString())) == months.get(tv.getText().toString())) {
            tv.setBackgroundColor(Color.GREEN);
            addMonthToTV();
        } else {
            tv.setBackgroundColor(Color.RED);
        }
    }
}