package com.neusoft.sample.View.xel_mine.Xel_mine_learntrack;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by AstroBoy on 2016/12/10.
 */

public class Xel_learnTrack_bean implements Comparable<Xel_learnTrack_bean>,Serializable{

    private int xtcsjg_id;
    private int score;
    private int number_error;
    private int number_success;
    private int duration;
    private Date jg_datetime;
    private String test_group_number;
    private int user_id;

    public Xel_learnTrack_bean() {
        super();
    }

    public Xel_learnTrack_bean(int user_id, int xtcsjg_id, int score, int number_error, int number_success, int duration, Date jg_datetime, String test_group_number) {
        this.user_id = user_id;
        this.xtcsjg_id = xtcsjg_id;
        this.score = score;
        this.number_error = number_error;
        this.number_success = number_success;
        this.duration = duration;
        this.jg_datetime = jg_datetime;
        this.test_group_number = test_group_number;
    }

    public int getXtcsjg_id() {
        return xtcsjg_id;
    }

    public void setXtcsjg_id(int xtcsjg_id) {
        this.xtcsjg_id = xtcsjg_id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNumber_error() {
        return number_error;
    }

    public void setNumber_error(int number_error) {
        this.number_error = number_error;
    }

    public int getNumber_success() {
        return number_success;
    }

    public void setNumber_success(int number_success) {
        this.number_success = number_success;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getJg_datetime() {
        return jg_datetime;
    }

    public void setJg_datetime(Date jg_datetime) {
        this.jg_datetime = jg_datetime;
    }

    public String getTest_group_number() {
        return test_group_number;
    }

    public void setTest_group_number(String test_group_number) {
        this.test_group_number = test_group_number;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Xel_learnTrack_bean{" +
                "xtcsjg_id=" + xtcsjg_id +
                ", score=" + score +
                ", number_error=" + number_error +
                ", number_success=" + number_success +
                ", duration=" + duration +
                ", jg_datetime=" + jg_datetime +
                ", test_group_number='" + test_group_number + '\'' +
                ", user_id=" + user_id +
                '}';
    }

    /**
     * Compares this object to the specified object to determine their relative
     * order.
     *
     * @param another the object to compare to this instance.
     * @return a negative integer if this instance is less than {@code another};
     * a positive integer if this instance is greater than
     * {@code another}; 0 if this instance has the same order as
     * {@code another}.
     * @throws ClassCastException if {@code another} cannot be converted into something
     *                            comparable to {@code this} instance.
     */


    @Override
    public int compareTo(Xel_learnTrack_bean another) {
        //notify_time.
        if (jg_datetime.getDate() < another.jg_datetime.getDate()) return -1;
        else if (jg_datetime.getDate() == another.jg_datetime.getDate()) return 0;
        else return 1;
    }

}
