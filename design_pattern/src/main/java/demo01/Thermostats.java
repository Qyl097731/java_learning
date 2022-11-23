package demo01;

import io.netty.handler.codec.DateFormatter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @description
 * @date:2022/11/23 17:21
 * @author: qyl
 */
public abstract class Thermostats {
    protected ControlSystem controlSystem;

    public static final DateTimeFormatter FORMAT_YMDHMS = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public Thermostats(ControlSystem controlSystem) {
        this.controlSystem = controlSystem;
    }

    /**
     * 手动调整
     * @param temperature
     */
    protected void adjustTemperature(BigDecimal temperature){
        adjustTemperature (FORMAT_YMDHMS.format (LocalDateTime.now (ZoneId.systemDefault ())),temperature);
    }

    //TODO 调整温度
    protected void adjustTemperature(String time, BigDecimal temperature){
        //... 等待
        controlSystem.setTemperature (temperature);
    }

    protected void display(){
        System.out.println (controlSystem.getTemperature ());
    }
}
