package cn.korostudio.sweng.data;

public class Configuration {
    final static public Configuration DEFAULT=new Configuration();
    public boolean window=false;
    public int windowW=1024;
    public int windowH=720;
    public int unitW=1920;
    public int unitH=1080;
    public int fps=30;
    public boolean game=false;
    public String title="NULL";
    public boolean fullSceen=false;
    public Configuration (){

    }
    public Configuration(boolean window,boolean game,boolean fullSceen,int windowH,int windowW,int unitH,int unitW,int fps,String title){
        this.fps=fps;
        this.game=game;
        this.title=title;
        this.unitH=unitH;
        this.unitW=unitW;
        this.window=window;
        this.windowH=windowH;
        this.windowW=windowW;
        this.fullSceen=fullSceen;
    }
}
