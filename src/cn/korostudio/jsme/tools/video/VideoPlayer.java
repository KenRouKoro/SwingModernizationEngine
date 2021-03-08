package cn.korostudio.jsme.tools.video;

import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;

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
