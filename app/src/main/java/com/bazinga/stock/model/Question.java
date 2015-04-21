package com.bazinga.stock.model;

/**
 * Created by abhijitnukalapati on 4/11/15.
 */
public class Question {
    private int stringResourceId;
    private boolean correct;

    public Question(int stringResourceId, boolean correct) {
        this.stringResourceId = stringResourceId;
        this.correct = correct;
    }

    public int getStringResourceId() {
        return stringResourceId;
    }

    public void setStringResourceId(int stringResourceId) {
        this.stringResourceId = stringResourceId;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

}
