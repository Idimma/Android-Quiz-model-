package nexustech.com.ng.nexusquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Picker extends AppCompatActivity {

    Button stage1, stage2, stage3, draw1, draw2, draw3, draw4, draw5;
    ImageView hide_cha;
    LinearLayout main, slave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);
        final Context mContext = Picker.this;
        stage1 = (Button) findViewById(R.id.stage1);
        stage2 = (Button) findViewById(R.id.stage2);
        stage3 = (Button) findViewById(R.id.stage3);
        draw1 = (Button) findViewById(R.id.draw1);
        draw2 = (Button) findViewById(R.id.draw2);
        draw3 = (Button) findViewById(R.id.draw3);
        draw4 = (Button) findViewById(R.id.draw4);
        draw5 = (Button) findViewById(R.id.draw5);
        main = (LinearLayout) findViewById(R.id.mainstages);
        slave = (LinearLayout) findViewById(R.id.stagees);
        hide_cha = (ImageView) findViewById(R.id.hide_cha);

        TextView sch = (TextView) findViewById(R.id.sch);
        sch.setText(SharedPref.getInstance(this).getSch());

        stage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.getInstance(mContext).setStage("stage1");
                startActivity(new Intent(mContext, QuizActivity.class));
            }
        });
        stage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.getInstance(mContext).setStage("stage2");
                startActivity(new Intent(mContext, QuizActivity.class));
            }
        });
        stage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.getInstance(mContext).setStage("stage3");
                startActivity(new Intent(mContext, QuizActivity.class));
            }
        });
        draw1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.getInstance(mContext).setStage("draw1");
                startActivity(new Intent(mContext, QuizActivity.class));
            }
        });
        draw2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.getInstance(mContext).setStage("draw2");
                startActivity(new Intent(mContext, QuizActivity.class));
            }
        });
        draw3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.getInstance(mContext).setStage("draw3");
                startActivity(new Intent(mContext, QuizActivity.class));
            }
        });
        draw4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.getInstance(mContext).setStage("draw4");
                startActivity(new Intent(mContext, QuizActivity.class));
            }
        });
        draw5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.getInstance(mContext).setStage("draw5");
                startActivity(new Intent(mContext, QuizActivity.class));
            }
        });
        hide_cha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (main.getVisibility() == View.VISIBLE) {
                    main.setVisibility(View.GONE);
                    slave.setVisibility(View.VISIBLE);
                    hide_cha.setImageResource(R.drawable.ic_close_black_24dp);
                } else {
                    main.setVisibility(View.VISIBLE);
                    slave.setVisibility(View.GONE);
                    hide_cha.setImageResource(R.drawable.ic_remove_red_eye_black_24dp);
                }

            }
        });
    }
}
