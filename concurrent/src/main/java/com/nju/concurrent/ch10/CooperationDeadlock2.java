package com.nju.concurrent.ch10;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @description 通过开放调用 修改 synchronized
 * @date:2022/12/28 21:14
 * @author: qyl
 */
public class CooperationDeadlock2 {
    @ThreadSafe
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
            boolean reachedDestination;
            synchronized (this){
                this.location = location;
                reachedDestination = location.equals (destination);
            }
            if (reachedDestination){
                dispatcher.notifyAvailable (this);
            }
        }
    }

    class Dispatcher{
        @GuardedBy ("this")
        private final Set<Taxi> taxis;
        @GuardedBy ("this")
        private final Set<Taxi> availableTaxis;


        Dispatcher() {
            taxis = new HashSet<> ();
            availableTaxis = new HashSet<> ();
        }

        public synchronized void notifyAvailable(Taxi taxi){
            availableTaxis.add (taxi);
        }

        public synchronized Image getImage(){
            Set<Taxi> copy;
            synchronized (this){
                copy = new HashSet<> (taxis);
            }
            Image image = new Image ();
            for (Taxi t : copy){
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
