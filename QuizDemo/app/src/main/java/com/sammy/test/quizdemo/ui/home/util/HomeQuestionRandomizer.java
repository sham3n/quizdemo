package com.sammy.test.quizdemo.ui.home.util;

import android.support.annotation.NonNull;

import com.sammy.test.quizdemo.model.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by sng on 4/9/18.
 */

public class HomeQuestionRandomizer {
    public static ArrayList<Question> randomizeQuestions(@NonNull Map<String, List<String>> responseQuestions) {
        ArrayList<Question> shuffledQuestions = new ArrayList<>();

        List<String> keySet = new ArrayList<>(responseQuestions.keySet());

        //Randomize the questions
        Collections.shuffle(keySet);

        //Loop through the keys and get the answers
        for(String questionStr : keySet) {
            Question question = new Question(questionStr);

            //get the first answer
            boolean isFirstPosition = true;
            for(String answerStr : responseQuestions.get(questionStr)) {
                question.addAnswer(new Question.Answer(answerStr, isFirstPosition));
                isFirstPosition = false;
            }

            //Randomize answers
            Collections.shuffle(question.getAnswers());

            //Add question to shuffledList
            shuffledQuestions.add(question);
        }

        return shuffledQuestions;
    }
}
