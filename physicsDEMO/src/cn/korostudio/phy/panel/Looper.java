package cn.korostudio.phy.panel;

import cn.korostudio.phy.data.Data;
import cn.korostudio.phy.data.Time;

public class Looper implements Runnable{
    protected Thread timeThread;
    protected boolean stop=false;
    protected PaintPanel paintPanel;
    public Looper(PaintPanel paintPanel){
        timeThread=new Thread(this);
        this.paintPanel=paintPanel;
    }
    @Override
    public void run(){
        while (!stop) {
            try {
                count();
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        stop=false;
    }
    public void start(){
        timeThread.start();
    }
    public void stop(){
        stop=true;
    }
    public void count(){

        paintPanel.x=(int)((Math.cos(Data.w1*(((double)Time.time)/1000.0)+Data.f1))*100);
        paintPanel.y=(int)((Math.cos(Data.w2*(((double)Time.time)/1000.0)+Data.f2))*100);
        Time.time=Time.time+3;
    }

}
