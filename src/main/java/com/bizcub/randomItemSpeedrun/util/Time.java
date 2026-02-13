package com.bizcub.randomItemSpeedrun.util;

public class Time {
    private int seconds;
    private int minutes = -1;
    private int hours = -1;
    private int days = -1;

    public Time(int seconds) {
        this.seconds = seconds;

        if (this.seconds >= 60) {
            this.minutes = this.seconds / 60;
            this.seconds %= 60;

            if (this.minutes >= 60) {
                this.hours = this.minutes / 60;
                this.minutes %= 60;

                if (this.hours >= 24) {
                    this.days = this.hours / 24;
                    this.hours %= 24;
                }
            }
        }
    }

    public String getSeconds() {
        String secondsStr = "";
        if (this.seconds != -1) {
            if (this.seconds < 10 && minutes != 0) secondsStr = "0" + secondsStr;
            secondsStr += this.seconds;
        }
        return secondsStr;
    }

    public String getMinutes() {
        String minutesStr = "";
        if (this.minutes != -1) {
            if (this.minutes < 10 && hours != 0) minutesStr = "0" + minutesStr;
            minutesStr += this.minutes;
        }
        return minutesStr;
    }

    public String getHours() {
        String hoursStr = "";
        if (this.hours != -1) {
            if (this.hours < 10 && days != 0) hoursStr = "0" + hoursStr;
            hoursStr += this.hours;
        }
        return hoursStr;
    }

    public String getDays() {
        String daysStr = "";
        if (this.days != -1) daysStr += this.days;
        return daysStr;
    }

    public boolean isMinutesExist() {
        return this.minutes!= -1;
    }

    public boolean isHoursExist() {
        return this.hours != -1;
    }

    public boolean isDaysExist() {
        return this.days != -1;
    }
}
