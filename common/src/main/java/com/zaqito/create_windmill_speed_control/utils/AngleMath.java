package com.zaqito.create_windmill_speed_control.utils;

public class AngleMath {

    private AngleMath() {}

    public static float angularDifference(float current, float previous) {

        float delta = current - previous;

        while (delta > 180f)
            delta -= 360f;

        while (delta < -180f)
            delta += 360f;

        return delta;
    }

}
