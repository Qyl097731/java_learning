package com.nju.observer;

import java.math.BigDecimal;

/**
 * @description
 * @date:2022/11/23 17:21
 * @author: qyl
 */
public class ThermostatsB extends Thermostats{

    public ThermostatsB(ControlSystem controlSystem) {
        super (controlSystem);
    }

    //TODO 调整温度
    @Override
    protected void adjustTemperature(String time, BigDecimal temperature){
        //... 等待
    }
}
