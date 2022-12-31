package com.nju.concurrent.ch10;

import net.jcip.annotations.GuardedBy;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @description 协作死锁 在getImage和setLocation隐含了死锁的可能性。
 * @date:2022/12/28 20:56
 * @author: qyl
 */
public class CooperationDeadlock {
    class Taxi{
        @GuardedBy ("this")
        private Point location,destination;
        private final Dispatcher dispatcher;

        Taxi(Dispatcher dispatcher) {
            this.dispatcher = dispatcher;
        }

        public synchronized Point getLocation(){
            return location;
        }

        public synchronized void setLocation(Point location){
            this.location = location;
            if (location.equals (destination)){
                dispatcher.notifyAvailable(this);
            }
        }
    }


    class Dispatcher{
        @GuardedBy ("this")
        private final Set<Taxi> taxis;
        private final Set<Taxi> availableTaxis;


        Dispatcher() {
            taxis = new HashSet<> ();
            availableTaxis = new HashSet<> ();
        }

        public synchronized void notifyAvailable(Taxi taxi){
            availableTaxis.add (taxi);
        }

        public synchronized Image getImage(){
            Image image = new Image ();
            for (Taxi t : taxis){
                image.drawMarker(t.getLocation());
            }
            return image;
        }
    }
    class Image{
        public void drawMarker(Point location){
            System.out.println (location);
        }
    }
}
