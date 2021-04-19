package cn.korostudio.jsme.data;

import java.awt.image.BufferedImage;

public class Configuration {
    final static public Configuration DEFAULT=new Configuration();
    public boolean window=false;
    public int windowW=1024;
    public int windowH=720;
    public double dpi=1;
    public int fps=30;
    public boolean game=false;
    public String title="NULL";
    public boolean fullSeen =false;
    public boolean resize=false;
    public boolean closingStop=false;
    public BufferedImage icon=null;
    public boolean dark=false;
    public Configuration (){

    }
    public Configuration(boolean resize, boolean window, boolean game, boolean fullSeen, int windowH, int windowW, int unitH, int unitW, int fps, String title, BufferedImage icon){
        this.fps=fps;
        this.game=game;
        this.resize=resize;
        this.title=title;
        this.window=window;
        this.windowH=windowH;
        this.windowW=windowW;
        this.fullSeen = fullSeen;
        this.icon=icon;
    }
}
