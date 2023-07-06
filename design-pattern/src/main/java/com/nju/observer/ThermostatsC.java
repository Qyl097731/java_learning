package com.nju.observer;

import java.math.BigDecimal;

/**
 * @date:2022/11/23 17:21
 * @author: qyl
 */
public class ThermostatsC extends Thermostats{

    public ThermostatsC(ControlSystem controlSystem) {
        super (controlSystem);
    }

    //TODO 调整温度
    @Override
    protected void adjustTemperature(String time, BigDecimal temperature){
        //... 等待
    }
}
