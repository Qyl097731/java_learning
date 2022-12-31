package com.nju.concurrent.ch04;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @description 委托到多个底层的状态变量
 * @date:2022/12/17 19:57
 * @author: qyl
 */
public class VisualComponent {
    private final List<KeyListener> keyListeners = new CopyOnWriteArrayList<> ();
    private final List<MouseListener> mouseListeners = new CopyOnWriteArrayList<> ();

    public void addKeyListener(KeyListener listener){
        keyListeners.add (listener);
    }

    public void addMouseListener(MouseListener listener){
        mouseListeners.add (listener);
    }

    public void removeKeyListener(KeyListener listener){
        keyListeners.remove (listener);
    }

    public void removeMouseListener(MouseListener listener){
        mouseListeners.remove (listener);
    }
}
