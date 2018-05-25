package com.sammy.test.quizdemo.ui.home;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sammy.test.quizdemo.R;
import com.sammy.test.quizdemo.model.Question;
import com.sammy.test.quizdemo.ui.home.adapter.AnswerAdapter;
import com.sammy.test.quizdemo.ui.home.util.HomeQuestionRandomizer;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends Fragment implements HomeContract.HomeView {

    private static final String QUESTIONS_KEY = "questions";
    private static final String QUESTION_NUM_KEY = "questionNum";
    private static final String COUNTER_KEY = "counter";
    private static final String LAYOUT_STATE_KEY = "layoutState";
    private static final String SAVE_TIME_KEY = "savedTime";
    private static final String QUESTION_ANSWERED_KEY = "questionAnswered";

    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.question_header)
    TextView mQuestionHeader;
    @BindView(R.id.question)
    TextView mQuestion;
    @BindView(R.id.choices)
    RecyclerView mRecyclerView;
    @BindView(R.id.timer)
    TextView mTimerText;

    @BindView(R.id.try_again)
    Button mTryAgain;
    @BindView(R.id.new_quiz)
    Button mNewQuiz;
    @BindView(R.id.blocker)
    View mChoiceBlocker;
    @BindView(R.id.result)
    TextView mResult;

    private RecyclerView.LayoutManager mLayoutManager;
    private HomeContract.HomePresenter mHomePresenter;
    private ArrayList<Question> mQuestions;

    private Question.Answer mQuestionAnswered;
    private int mQuestionNumber;
    private CountDownTimer mTimer;
    private int mCounter;

    public HomeFragment() {
        //needed by default
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(QUESTIONS_KEY, mQuestions);
        outState.putInt(QUESTION_NUM_KEY, mQuestionNumber);
        outState.putInt(COUNTER_KEY, mCounter);
        outState.putParcelable(QUESTION_ANSWERED_KEY, mQuestionAnswered);
        outState.putLong(SAVE_TIME_KEY, System.currentTimeMillis());
        outState.putParcelable(LAYOUT_STATE_KEY, mLayoutManager.onSaveInstanceState());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Create presenter
        new HomePresenter(this, new HomeInteractor());
        if(savedInstanceState != null) {
            mQuestionNumber = savedInstanceState.getInt(QUESTION_NUM_KEY);
            mQuestionAnswered = savedInstanceState.getParcelable(QUESTION_ANSWERED_KEY);

            //Calculate time difference while app was in background
            int timeDiffInSeconds = (int)(System.currentTimeMillis() - savedInstanceState.getLong(SAVE_TIME_KEY))/1000;
            mCounter = savedInstanceState.getInt(COUNTER_KEY) - timeDiffInSeconds;
            mQuestions = savedInstanceState.getParcelableArrayList(QUESTIONS_KEY);

            Parcelable layoutState = savedInstanceState.getParcelable(LAYOUT_STATE_KEY);
            if(layoutState != null) {
                mLayoutManager.onRestoreInstanceState(layoutState);
            }

            nextQuestion(mQuestions.get(mQuestionNumber), true);
        } else {
            //start the presenter
            mHomePresenter.start();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retain this fragment across configuration changes.
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void bindDataOnView(@NotNull Map<String, List<String>> response) {
        mQuestions = HomeQuestionRandomizer.randomizeQuestions(response);
        //only initialize if it's 1st time
        if(mQuestionNumber == 0)
            nextQuestion(null, false);
    }

    @Override
    public void showErrorMessage(@NotNull Throwable error) {
        Toast.makeText(getContext(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();;
    }

    @Override
    public void setPresenter(HomeContract.HomePresenter presenter) {
        mHomePresenter = presenter;
    }

    private void nextQuestion(@Nullable Question question, boolean startFromPreviousState) {
        //If there's an existing question to display the show that
        if(question == null) {
            mQuestionHeader.setText(getString(R.string.question_header, mQuestionNumber + 1));
            question = mQuestions.get(mQuestionNumber++);
        } else {
            mQuestionHeader.setText(getString(R.string.question_header, mQuestionNumber));
            question = mQuestions.get(mQuestionNumber - 1);
            if(startFromPreviousState) {
                if(mQuestionAnswered != null) {
                    showButtons(mQuestionAnswered);
                }
            }
        }
        mQuestion.setText(getString(R.string.question, question.getQuestion()));

        //Create the adapter
        AnswerAdapter adapter = new AnswerAdapter(getContext(), question.getAnswers(), (Question.Answer answer) -> {
            showButtons(answer);
        });

        mRecyclerView.setAdapter(adapter);

        if(mQuestionAnswered == null)
            resetTimer(startFromPreviousState);
    }

    private void init() {
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mTryAgain.setOnClickListener((View view) -> {
            resetQuiz();
        });
        mNewQuiz.setOnClickListener((View view) -> {
            resetQuiz();
            nextQuestion(null, false);
        });

        mChoiceBlocker.setOnClickListener((View view) -> {
            //do nothing
        });
    }

    private void resetQuiz() {
        mChoiceBlocker.setVisibility(View.GONE);
        mNewQuiz.setVisibility(View.GONE);
        mTryAgain.setVisibility(View.GONE);
        mResult.setText(null);
        mQuestionAnswered = null;
        resetTimer(false);
    }

    private void resetTimer(boolean startFromPreviousState) {
        if (mTimer != null) {
            mTimer.cancel();
        }

        if(startFromPreviousState) {
            if(mCounter < 0) {
                timesUp();
            } else {
                mTimerText.setText(String.valueOf(mCounter));
            }
        } else {
            mCounter = 30;
            mTimerText.setText(String.valueOf(mCounter));
        }

        //add 200 milliseconds because timer starts a few 100ms fast
        mTimer = new CountDownTimer(mCounter * 1000 + 500, 1000){
            public void onTick(long millisUntilFinished){
                mTimerText.setText(String.valueOf(mCounter--));
            }
            public  void onFinish(){
                timesUp();
            }
        }.start();
    }

    private void timesUp() {
        showButtons(null);
        mTimerText.setText("Times Up!!");
    }

    private void showButtons(Question.Answer answer) {
        mChoiceBlocker.setVisibility(View.VISIBLE);
        mQuestionAnswered = answer;
        if(mTimer != null)
            mTimer.cancel();

        if(answer == null || !answer.isCorrectAnswer()) {
            mTryAgain.setVisibility(View.VISIBLE);
        }

        if(answer != null) {
            @StringRes int result = answer.isCorrectAnswer() ? R.string.correct : R.string.sorry;
            mResult.setText(result);
        }

        if(mQuestionNumber < mQuestions.size()) {
            mNewQuiz.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Listener for the recycler view interaction
     */
    public interface HomeItemListener {
        void onRowClick(Question.Answer answer);
    }
}
