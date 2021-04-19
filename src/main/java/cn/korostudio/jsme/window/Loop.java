package cn.korostudio.jsme.window;

import cn.korostudio.jsme.data.Configuration;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Loop {
    protected Configuration conf;
    protected Thread loopThread;
    protected LoopThread loopRunnable;
    protected boolean empty=true;
    protected ArrayList<Component>components=new ArrayList<>();
    private boolean stop=false;
    public Loop(Configuration conf){
        this.conf=conf;
        loopRunnable=new LoopThread();
        loopThread=new Thread(loopRunnable);
    }
    public void add(Component...components){
        this.components.addAll(Arrays.asList(components));
    }
    public void remove(Component...components){
        this.components.removeAll(Arrays.asList(components));
    }
    public void stop(){
        stop=true;
    }
    public void start(){
        loopThread.start();
    }

    public boolean isEmpty() {
        return empty;
    }

    protected class LoopThread implements Runnable{
        @Override
        public void run() {
            while (!stop){
                for (Component component:components){
                    component.repaint();
                }
                try {
                    Thread.sleep(1000/conf.fps);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            stop=false;
        }
    }



}
