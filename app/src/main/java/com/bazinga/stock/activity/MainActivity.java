package com.bazinga.stock.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.bazinga.stock.R;
import com.bazinga.stock.model.Question;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    private static final String INDEX = "INDEX";
    private static final String CHEATED_QUESTIONS = "CHEATED_QUESTIONS";
    private static final int CHEAT_REQUEST_CODE = 100;

    private TextSwitcher mQuestionSwitcher;
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private Button mCheatButton;

    private Question[] mQuestions = new Question[]{
        new Question(R.string.question_one, false),
        new Question(R.string.question_two, true),
        new Question(R.string.question_three, false),
        new Question(R.string.question_four, true)
    };

    private int mCurrentIndex;
    private ArrayList<Integer> mCheatedQuestions = new ArrayList<>();

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save the current index, in case out activity is
        // destroyed by the android system
        outState.putInt(INDEX, mCurrentIndex);
        outState.putIntegerArrayList(CHEATED_QUESTIONS, mCheatedQuestions);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check if we can retrieve an index,
        // as the activity could have been recreated
        if(savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(INDEX, 0);

            // since we already set mCheatedQuestions to an empty list,
            // only assign a new value if we know it is not null
            if(savedInstanceState.containsKey(CHEATED_QUESTIONS)) {
                mCheatedQuestions = savedInstanceState.getIntegerArrayList(CHEATED_QUESTIONS);
            }
        }

        // get references to the views from the layout
        mQuestionSwitcher = (TextSwitcher) findViewById(R.id.question_switcher);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mNextButton = (Button) findViewById(R.id.next_button);
        mPrevButton = (Button) findViewById(R.id.prev_button);
        mCheatButton = (Button) findViewById(R.id.cheat_button);

        // our factory that creates views that are
        // going to be part of the switcher
        mQuestionSwitcher.setFactory(mFactory);

        // load animations
        Animation inAnimation = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation outAnimation = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);

        // set in and out animations for
        // out text switcher
        mQuestionSwitcher.setInAnimation(inAnimation);
        mQuestionSwitcher.setOutAnimation(outAnimation);

        // NOTE: setCurrentText uses no animation, it is
        // used to set the default text
        mQuestionSwitcher.setCurrentText(getString(mQuestions[mCurrentIndex % mQuestions.length].getStringResourceId()));

        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switchText(false);
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switchText(true);
            }
        });

        mTrueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showResponse(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showResponse(false);
            }
        });

        mCheatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent cheatActivityIntent = new Intent(MainActivity.this, CheatActivity.class);
                cheatActivityIntent.putExtra(CheatActivity.EXTRA_ANSWER, mQuestions[mCurrentIndex % mQuestions.length].isCorrect());
                startActivityForResult(cheatActivityIntent, CHEAT_REQUEST_CODE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private ViewSwitcher.ViewFactory mFactory = new ViewSwitcher.ViewFactory() {
        @Override
        public View makeView() {
            return getLayoutInflater().inflate(R.layout.question, null);
        }
    };

    private void showResponse(boolean choice){
        int responseStringId;

        int currIndex = mCurrentIndex % mQuestions.length;
        boolean isCorrect = mQuestions[currIndex].isCorrect();

        if(mCheatedQuestions.contains(currIndex)) {
            Toast.makeText(this, getString(R.string.response_cheat), Toast.LENGTH_SHORT)
                    .show();
        } else {
            if (isCorrect == choice) {
                responseStringId = R.string.response_correct;
            } else {
                responseStringId = R.string.response_wrong;
            }

            Toast.makeText(this, getString(responseStringId), Toast.LENGTH_SHORT).show();
        }

        switchText(false);
    }

    /**
     * Switches the text in {@link #mQuestionSwitcher} to next/prev question
     * depending on goBack
     * @param goBack a boolean to indicate if we need to switch backwards
     */
    private void switchText(boolean goBack) {
        int index;
        if(goBack) {
            // offset the index so that it always remains positive
            index = (--mCurrentIndex + mQuestions.length) % mQuestions.length;
            mQuestionSwitcher.setText(getString(mQuestions[index].getStringResourceId()));
        } else {
            index = ++mCurrentIndex % mQuestions.length;
            mQuestionSwitcher.setText(getString(mQuestions[index].getStringResourceId()));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHEAT_REQUEST_CODE && resultCode == RESULT_OK) {
            boolean isCheater = data.getBooleanExtra(CheatActivity.EXTRA_WAS_ANSWER_SHOWN, false);

            if(isCheater) {
                int currIndex = mCurrentIndex % mQuestions.length;
                if(!mCheatedQuestions.contains(currIndex)) mCheatedQuestions.add(currIndex);
            }
        }
    }
}
