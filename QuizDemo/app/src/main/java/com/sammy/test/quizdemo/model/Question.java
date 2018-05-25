package com.sammy.test.quizdemo.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sng on 4/9/18.
 */

public class Question implements Parcelable {
    @SerializedName("question")
    private String mQuestion;
    @SerializedName("answers")
    private List<Answer> mAnswers;

    public Question(@NonNull String question) {
        this(question, null);
    }

    public Question(@NonNull String question, @NonNull List<Answer> answers) {
        mQuestion = question;
        mAnswers = answers;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public List<Answer> getAnswers() {
        return mAnswers;
    }

    public void addAnswer(@NonNull Answer answer) {
        if(mAnswers == null) {
            mAnswers = new ArrayList<>();
        }

        mAnswers.add(answer);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mQuestion);
        out.writeTypedList(mAnswers);
    }

    private Question(Parcel in) {
        mQuestion = in.readString();
        mAnswers = new ArrayList<>();
        in.readTypedList(mAnswers, Answer.CREATOR);
    }


    public static final Parcelable.Creator<Question> CREATOR
            = new Parcelable.Creator<Question>() {
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
    public static class Answer implements Parcelable {
        @Nullable
        @SerializedName("image")
        private String mImageUrl;
        @SerializedName("correctAnswer")
        private boolean mIsCorrectAnswer;

        public Answer(@Nullable String imageUrl, boolean isCorrectAnswer) {
            mImageUrl = imageUrl;
            mIsCorrectAnswer = isCorrectAnswer;
        }

        @Nullable
        public String getImageUrl() {
            return mImageUrl;
        }

        public void setImageUrl(@Nullable String mImageUrl) {
            this.mImageUrl = mImageUrl;
        }

        public boolean isCorrectAnswer() {
            return mIsCorrectAnswer;
        }

        public void setIsCorrectAnswer(boolean mIsCorrectAnswer) {
            this.mIsCorrectAnswer = mIsCorrectAnswer;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel out, int flags) {
            out.writeString(mImageUrl);
            out.writeByte((byte) (mIsCorrectAnswer ? 1 : 0));
        }

        private Answer(Parcel in) {
            mImageUrl = in.readString();
            mIsCorrectAnswer = in.readByte() == 1;
        }

        public static final Parcelable.Creator<Answer> CREATOR
                = new Parcelable.Creator<Answer>() {
            public Answer createFromParcel(Parcel in) {
                return new Answer(in);
            }

            public Answer[] newArray(int size) {
                return new Answer[size];
            }
        };
    }
}
