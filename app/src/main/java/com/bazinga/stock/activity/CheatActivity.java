package com.bazinga.stock.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bazinga.stock.R;

/**
 * Created by abhijitnukalapati on 4/12/15.
 */
public class CheatActivity extends ActionBarActivity {

    public static final String EXTRA_ANSWER = "com.bazinga.stock.ANSWER";
    public static final String EXTRA_WAS_ANSWER_SHOWN = "com.bazinga.stock.WAS_ANSWER_SHOWN";
    private static final String ANSWER_SHOWN = "ANSWER_SHOWN";
    private static final String ANSWER = "ANSWER";

    private Button mShowButton;
    private TextView mAnswerView;

    private boolean didUserSeeAnswer;
    private String mAnswer;
    private final Intent mResultIntent = new Intent();

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ANSWER_SHOWN, didUserSeeAnswer);
        outState.putString(ANSWER, mAnswer);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        if(savedInstanceState != null) {
            didUserSeeAnswer = savedInstanceState.getBoolean(ANSWER_SHOWN, false);
            mAnswer = savedInstanceState.getString(ANSWER);
        }

        // the user hasn't seen the answer as yet
        // so set our extra data, that we are going
        // to pass on to the previous activity to false
        mResultIntent.putExtra(EXTRA_WAS_ANSWER_SHOWN, didUserSeeAnswer);

        mShowButton = (Button) findViewById(R.id.show_button);
        mAnswerView = (TextView) findViewById(R.id.answer);
        if(!TextUtils.isEmpty(mAnswer)) mAnswerView.setText(mAnswer);

        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnswer = String.valueOf(getIntent().getBooleanExtra(EXTRA_ANSWER, false));
                mAnswerView.setText(mAnswer);
                didUserSeeAnswer = true;
                mResultIntent.putExtra(EXTRA_WAS_ANSWER_SHOWN, didUserSeeAnswer);
            }
        });

        setResult(RESULT_OK, mResultIntent);
    }

}
