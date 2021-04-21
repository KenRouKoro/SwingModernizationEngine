package cn.korostudio.jsme.tools.video;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayer;

public class VideoPlayer {
    protected String file = null;

    protected int w = 1024;

    protected int h = 720;

    EmbeddedMediaPlayerComponent playerComponent;

    public VideoPlayer() {
        this.playerComponent = new EmbeddedMediaPlayerComponent();
        //playerComponent.setVisible(false);
    }

    public MediaPlayer getMediaPlayer() {
        return (MediaPlayer)this.playerComponent.getMediaPlayer();
    }

    public void start() {
        /*
        this.playerComponent.addComponentListener(new ComponentListener() {
            public void componentShown(ComponentEvent arg0) {}

            public void componentResized(ComponentEvent arg0) {
                playerComponent.setBounds(0, 0, playerComponent.getWidth(), playerComponent.getHeight());
            }

            public void componentMoved(ComponentEvent arg0) {}

            public void componentHidden(ComponentEvent arg0) {}
        });
        */
        this.playerComponent.getMediaPlayer().play();
        playerComponent.repaint();
    }

    public void setFile(String file) {
        this.file = file;
        String[] opt = { "--subsdec-encoding=GB18030" };
        this.playerComponent.getMediaPlayer().prepareMedia(file, opt);
        //this.playerComponent.setBounds(0, 0, w,h);
    }

    public Component backPart() {
        return (Component)this.playerComponent;
    }
}
/*

public class VideoPlayer extends JPanel {
    protected EmbeddedMediaPlayerComponent playerComponent=null;
    protected File videoFile=null;
    private void init(){
        setLayout(null);
        playerComponent=new EmbeddedMediaPlayerComponent();

        add(playerComponent);

    }
    public VideoPlayer(){
        init();
    }
    public VideoPlayer(File videoFile){
        setVideoFile(videoFile);
        init();

    }
    public void setVideoFile(File videoFile){
        this.videoFile=videoFile;
        playerComponent.mediaPlayer().media().play(videoFile.getAbsolutePath());
    }

    @Override
    public void setSize(Dimension d) {
        super.setSize(d);
        playerComponent.setSize(d);
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        playerComponent.setSize(width, height);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        playerComponent.setSize(width, height);
    }

    @Override
    public void setBounds(Rectangle r) {
        super.setBounds(r);
        playerComponent.setSize((int)r.getWidth(),(int)r.getHeight());
    }

}

 */
