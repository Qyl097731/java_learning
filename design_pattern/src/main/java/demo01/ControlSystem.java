package demo01;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description
 * @date:2022/11/23 17:31
 * @author: qyl
 */
public class ControlSystem {
    private BigDecimal temperature;

    private List<Thermostats> thermostats;

    public ControlSystem(){}

    public ControlSystem(List<Thermostats> thermostats){
        this.thermostats = thermostats;
    }

    public void setTemperature(BigDecimal temperature){
        this.temperature = temperature;
        for (Thermostats thermostat : thermostats) {
            thermostat.display ();
        }
    }

    public BigDecimal getTemperature(){
        return temperature;
    }
}
