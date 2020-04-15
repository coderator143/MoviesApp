package com.example.movie_mvvm.Utilities;

import com.example.movie_mvvm.R;

import java.util.Random;

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
        hours=0;
        minutes=0;
        minutes=Integer.parseInt(min);
        while (minutes>59) {
            minutes-=60;
            hours++;
        }
    }

    public int getRandomColor() {
        final int random = new Random().nextInt(4) + 1; // [0, 4] + 1 => [1, 5]
        if(random==1) return R.color.orange;
        else if(random==2) return R.color.green;
        else if(random==3) return R.color.pink;
        else return R.color.blue;
    }
}
