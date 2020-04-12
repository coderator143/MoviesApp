package com.example.movie_mvvm.Utilities;

public class UtilityMethods {

    private int minutes=0, hours=0;

    public String getHoursFromRuntime(String min) {
        convertRuntime(min);
        return String.valueOf(hours);
    }

    public String getMinutesFromRuntime(String min) {
        convertRuntime(min);
        return String.valueOf(minutes);
    }

    private void convertRuntime(String min) {
        minutes=Integer.parseInt(min);
        while (minutes>59) {
            minutes-=60;
            hours++;
        }
    }
}
