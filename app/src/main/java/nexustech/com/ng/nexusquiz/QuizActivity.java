package nexustech.com.ng.nexusquiz;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class QuizActivity extends AppCompatActivity {
    TextView stage, timer, count;
    WebView question;
    Button submit;
    long total_sec = 60;
    RadioButton opt1, opt2, opt3, opt4;
    RadioGroup options;
    Context mContext;
    CountDownTimer countDownTimer;
    String PICKED, STAGE, SEVERPICKED = "", QUESTION = "QUESTION", ANSWER = "Answer", A = "A", B = "B", C = "C", D = "D";
    int currentNumber = 0, scores = 0;
    JSONArray serverObject;
    SharedPref mSharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity);
        mContext = QuizActivity.this;
        mSharedPref = SharedPref.getInstance(mContext);
        serverConnection();

    }

    private void serverConnection() {
        STAGE = SharedPref.getInstance(mContext).getStage();

        if (mSharedPref.getIP().isEmpty()) {
            mSharedPref.setIP("192.168.43.24");
        }
        String URL = "http://" +
                mSharedPref.getIP() +
                "/quiz/server/tables.php?s=" + STAGE;
        StringRequest mRequest = new StringRequest(Request.Method.POST,
                URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                intialize(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
        };
        RequestHandler.getInstance(this).addToRequestQueue(mRequest);

    }

    private void intialize(String response) {
        try {
            serverObject = new JSONArray(response);
            SharedPref.getInstance(this).setQuest(serverObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        stage = (TextView) findViewById(R.id.stage);
        timer = (TextView) findViewById(R.id.timer);
        count = (TextView) findViewById(R.id.count);
        question = (WebView) findViewById(R.id.question);

        submit = (Button) findViewById(R.id.submit);
        submit.setVisibility(View.GONE);

        opt1 = (RadioButton) findViewById(R.id.opt1);
        opt2 = (RadioButton) findViewById(R.id.opt2);
        opt3 = (RadioButton) findViewById(R.id.opt3);
        opt4 = (RadioButton) findViewById(R.id.opt4);

        options = (RadioGroup) findViewById(R.id.options);

        options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = checkedId;
                if (id == opt1.getId()) {
                    PICKED = A;
                }
                if (id == opt2.getId()) {
                    PICKED = B;
                }
                if (id == opt3.getId()) {
                    PICKED = C;
                }
                if (id == opt4.getId()) {
                    PICKED = D;
                }
            }
        });

        if (mSharedPref.getStage().equalsIgnoreCase("stage3")) {
            total_sec = 90;
        }

        countDownTimer = new CountDownTimer(total_sec * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String newSec = getSec(millisUntilFinished);
                timer.setText(newSec);
            }

            @Override
            public void onFinish() {
                PICKED = " ";
                int id = options.getCheckedRadioButtonId();
                if (id == opt1.getId()) {
                    PICKED = A;
                }
                if (id == opt2.getId()) {
                    PICKED = B;
                }
                if (id == opt3.getId()) {
                    PICKED = C;
                }
                if (id == opt4.getId()) {
                    PICKED = D;
                }

//                if (PICKED.isEmpty()) {
//                    PICKED = " ";
//                } else {
                if (PICKED.equalsIgnoreCase(getText(ANSWER))) {
                    scores++;
                }
//                }
                SEVERPICKED = SEVERPICKED + PICKED + ",";
                nextQuestion();
            }
        };
        showQuestion();

    }

    private String getSec(long millisUntilFinished) {
        long sec = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));
        long mins = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
        String secs;
        if (sec < 10) {
            secs = "0" + sec;
        } else {
            secs = "" + sec;
        }
        String newSec = "" + String.format("0%d : %s", mins, secs);
        return newSec;
    }

    private String getText(String KeyWord) {

        try {
            return serverObject.getJSONObject(currentNumber).getString(KeyWord);
        } catch (JSONException e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    private void showQuestion() {
        stage.setText(SharedPref.getInstance(QuizActivity.this).getSch() + " in " + STAGE);
        question.clearCache(true);
        count.setText(currentNumber + 1 + " / " + serverObject.length());
        question.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//        String que = " <h4 style=\"background-color:#fff; text-align: center; color: #000; font-size: 25px\">" + getText(QUESTION) + "</h4> ";
        String que = "<p style=\"background-color:#fff; text-align: center; color: black; font-size: 18px\">" + getText(QUESTION) + "</p>";
        String html = "<div class=\"outer\">\n" +
                "  <div class=\"middle\">\n" +
                "    <div class=\"inner\">\n" +
                que +
                "\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</div>" +
                "<style>.outer {\n" +
                "    display: table;\n" +
                "    position: absolute;\n" +
                "    height: 100%;\n" +
                "    width: 100%;\n" +
                "}\n" +
                "\n" +
                ".middle {\n" +
                "    display: table-cell;\n" +
                "    vertical-align: middle;\n" +
                "}\n" +
                "\n" +
                ".inner {\n" +
                "    width: auto;\n" +
                "}</style>" +
                "";

        question.loadData(html, "text/html", "UTF-8");
        question.reload();
        opt1.setText(getText(A));
        opt2.setText(getText(B));
        opt3.setText(getText(C));
        opt4.setText(getText(D));
        countDownTimer.start();
    }

    private void nextQuestion() {
        countDownTimer.cancel();
        options.clearCheck();
        currentNumber++;
        if (currentNumber < serverObject.length()) {
            showQuestion();
        } else {
            endQuiz();
        }

    }

    private void endQuiz() {
        String URL = "http://" + mSharedPref.getIP() +
                "/quiz/server/insert.php";
        StringRequest mRequest = new StringRequest(Request.Method.POST,
                URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                final Dialog alertDialog = new Dialog(mContext, R.style.AppTheme_Dialog);
                alertDialog.setContentView(R.layout.alert);
                final WebView res = (WebView) alertDialog.findViewById(R.id.web_alert);
                final Button ok = (Button) alertDialog.findViewById(R.id.alert_btn);
                String message = "";
                try {
                    message = new JSONObject(response).getString("response");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                res.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                String que = "<p style=\"background-color:#fff; margin: 10px; text-align: center; color: black; font-size: 20px\">" + message + "</p>";

                res.loadData(que, "text/html", "UTF-8");
                res.reload();
                alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        startActivity(new Intent(QuizActivity.this, Entry.class));
                    }
                });
                alertDialog.show();

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        startActivity(new Intent(QuizActivity.this, Entry.class));

                    }
                });

                alertDialog.setCancelable(true);
                alertDialog.show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                final Dialog alertDialog = new Dialog(mContext, R.style.AppTheme_Dialog);
                alertDialog.setContentView(R.layout.alert);
                final WebView res = (WebView) alertDialog.findViewById(R.id.web_alert);
                final Button ok = (Button) alertDialog.findViewById(R.id.alert_btn);
                String message = error.getMessage();
                res.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                String que = "<p style=\"background-color:#fff; margin: 10px; text-align: center;  font-size: 20px\">" + message + "</p> ";

                res.loadData(que, "text/html", "UTF-8");
                res.reload();

                alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        startActivity(new Intent(QuizActivity.this, Entry.class));
                    }
                });
                alertDialog.show();

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        startActivity(new Intent(QuizActivity.this, Entry.class));
                    }
                });

                alertDialog.setCancelable(true);
                alertDialog.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("sch", SharedPref.getInstance(QuizActivity.this).getSch());
                params.put("pass", mSharedPref.getStage());
                params.put("score", scores + " / " + serverObject.length());
                params.put("picked", SEVERPICKED);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(mRequest);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
