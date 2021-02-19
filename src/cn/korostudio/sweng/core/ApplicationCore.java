package cn.korostudio.sweng.core;

import cn.korostudio.sweng.data.Configuration;
import cn.korostudio.sweng.listener.CallBack;
import cn.korostudio.sweng.window.WindowController;
import com.sun.jna.NativeLibrary;
import javafx.embed.swing.JFXPanel;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.RuntimeUtil;
import uk.co.caprica.vlcj.factory.discovery.NativeDiscovery;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;

public class ApplicationCore  {
    static protected boolean FXInit=false;
    static protected boolean VlcjInit=false;
    final static protected CountDownLatch latch = new CountDownLatch(1);
    protected Configuration conf=new Configuration();
    protected WindowController windowController;
    protected Application application;
    static boolean found=false;
    //初始化javafx功能
    private void fxInit(){
        if (!FXInit){
            FXInit=true;
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
    private void vlcjInit(){
        if (!VlcjInit){
            //NativeDiscovery().discover();函数返回的是一个布尔类型的值，所有可以定义一个布尔类型的值，用来接收，利用控制台打印，是否发现本地库
            found = new NativeDiscovery().discover();
            if (found) System.out.println("已自动发现VLC库。");
            else {
                NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "\\lib\\vlc");
                //打印版本，用来检验是否获得文件
                System.out.println(LibVlc.libvlc_get_version());
            }
            VlcjInit=true;
        }
    }

    public WindowController getWindowController() {
        return windowController;
    }

    public Application getApplication() {
        return application;
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
        if (Application.FXSupport)fxInit();
        if (Application.VLCSupport)vlcjInit();
    }
}
