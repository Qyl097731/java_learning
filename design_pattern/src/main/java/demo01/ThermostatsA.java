package demo01;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @description
 * @date:2022/11/23 17:21
 * @author: qyl
 */
public class ThermostatsA extends Thermostats{

    public ThermostatsA(ControlSystem controlSystem) {
        super (controlSystem);
    }

    //TODO 调整温度
    @Override
    protected void adjustTemperature(String time, BigDecimal temperature){
        //... 等待

    }
}
