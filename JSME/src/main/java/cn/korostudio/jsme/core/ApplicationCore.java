package cn.korostudio.jsme.core;

import cn.korostudio.jsme.data.Configuration;
import cn.korostudio.jsme.listener.CallBack;
import cn.korostudio.jsme.window.WindowController;
import com.sun.jna.NativeLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import java.util.concurrent.CountDownLatch;

public class ApplicationCore {
    final static protected CountDownLatch latch = new CountDownLatch(1);
    private final static Logger logger = LoggerFactory.getLogger(ApplicationCore.class);
    static protected boolean FXInit = false;
    static protected boolean VlcjInit = false;
    static boolean found = false;
    protected Configuration conf = new Configuration();
    protected WindowController windowController;
    protected Application application;
    protected BasePanel basePanel;

    //构造方法
    public ApplicationCore(Configuration conf, Application application) {
        this.conf = conf;
        this.application = application;
        //if (JSMEApplication.FXSupport)fxInit();
        logger.debug("VLCJ Support:" + Application.VLCSupport);
        if (Application.VLCSupport) vlcjInit();
    }

    //初始化javafx功能
    /*
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
            System.out.println("FX功能已经加载。");
        }
    }
    */
    //初始化VLCJ功能
    private void vlcjInit() {
        if (!VlcjInit) {
            //NativeDiscovery().discover();函数返回的是一个布尔类型的值，所有可以定义一个布尔类型的值，用来接收，利用控制台打印，是否发现本地库
            found = new NativeDiscovery().discover();
            if (found) logger.info("The VLCLib has been discovered automatically.");
            else {
                NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "\\lib\\vlc");
                //打印版本，用来检验是否获得文件
                logger.info(LibVlc.INSTANCE.libvlc_get_version());
            }
            VlcjInit = true;
            logger.info("VLCJ Init Ending");
        }
    }

    public WindowController getWindowController() {
        return windowController;
    }

    public Application getApplication() {
        return application;
    }

    protected void stop() {
        if (conf.game) {
            windowController.stopLoop();
        }
        application.stop();
        logger.debug("JSMEApplication Core is stop.");
    }

    //初始化
    protected void init(BasePanel basePanel) {
        this.basePanel = basePanel;
        windowController = new WindowController(conf);
        windowController.setCloseCallBack(new CallBack() {
            @Override
            public void run() {
                stop();
            }
        });
        windowController.getWindow().add(basePanel);
        start();
        windowController.getWindow().repaint();
    }

    //开始方法
    public void start() {
        windowController.show(true);
        if (conf.game) {
            windowController.startLoop();
        }
        logger.debug("JSMEApplication Core is start");
    }
}
