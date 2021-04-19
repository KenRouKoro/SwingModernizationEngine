package cn.korostudio.jsme.tools.video;

import uk.co.caprica.vlcj.factory.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import uk.co.caprica.vlcj.binding.LibVlc;

public class VideoPlayer {
    protected String file = null;
    protected int w = 1024;
    protected int h = 720;
    protected EmbeddedMediaPlayerComponent playerComponent;
    protected String[] opt = { "--subsdec-encoding=GB18030" };

    public VideoPlayer() {
        boolean found = (new NativeDiscovery()).discover();
        //System.out.println(found);
        //System.out.println(LibVlc.INSTANCE.libvlc_get_version());
        this.playerComponent = new EmbeddedMediaPlayerComponent();
    }

    public MediaPlayer getMediaPlayer() {
        return (MediaPlayer)this.playerComponent.mediaPlayer();
    }

    public void start() {
        this.playerComponent.addComponentListener(new ComponentListener() {
            public void componentShown(ComponentEvent arg0) {}

            public void componentResized(ComponentEvent arg0) {
                VideoPlayer.this.playerComponent.setBounds(0, 0, VideoPlayer.this.playerComponent.getWidth(), VideoPlayer.this.playerComponent.getHeight());
            }

            public void componentMoved(ComponentEvent arg0) {}

            public void componentHidden(ComponentEvent arg0) {}
        });
        this.playerComponent.mediaPlayer().media().play(file, opt);//getMediaPlayer().play();
    }

    public void setfile(String file) {
        this.file = file;
        //this.playerComponent.mediaPlayer().media().prepare(file, opt);//getMediaPlayer().prepareMedia(file, opt);
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
