package cn.korostudio.sweng.core;

import cn.korostudio.sweng.data.Configuration;
import cn.korostudio.sweng.listener.CallBack;
import cn.korostudio.sweng.window.WindowController;
import javafx.embed.swing.JFXPanel;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;

public class ApplicationCore  {
    static protected boolean FXIndi=false;
    final static protected CountDownLatch latch = new CountDownLatch(1);
    protected Configuration conf=new Configuration();
    protected WindowController windowController;
    protected Application application;
    //初始化javafx功能
    private void fxIndi(){
        if (!FXIndi){
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new JFXPanel(); // initializes JavaFX environment
                    latch.countDown();
                }
            });
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected void stop(){
        application.stop();
    }

    //初始化
    protected void init(BasePanel basePanel){
        windowController=new WindowController(conf);
        windowController.setCloseCallBack(new CallBack() {
            @Override
            public void run() {
                stop();
            }
        });
        windowController.getWindow().add(basePanel);
        start();
    }
    public void start(){
        windowController.show(true);
    }
    //构造方法
    public ApplicationCore(Configuration conf,Application application){
        this.conf=conf;
        this.application=application;
        fxIndi();
    }
}
