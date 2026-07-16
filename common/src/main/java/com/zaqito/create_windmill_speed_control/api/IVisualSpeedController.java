package com.zaqito.create_windmill_speed_control.api;

public interface IVisualSpeedController {

    int getVisualSpeedPercentage();

    void setVisualSpeedPercentage(int percentage);

    float getVisualSpeedModifier();

    float getVisualAngle();

    float getVisualPrevAngle();
}
