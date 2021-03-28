package cn.korostudio.jsme.tools.music;
import cn.korostudio.jsme.listener.CallBack;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.ArrayList;
import java.util.Arrays;
public class MusicPlayer {
    protected Media musicMedia;
    protected MediaPlayer musicMediaPlayer = null;
    protected String URI;
    protected boolean playing = false;
    protected ArrayList<CallBack> stopCallBacks = new ArrayList<>();
    protected ArrayList<CallBack> startCallBacks = new ArrayList<>();
    public void setURI(java.net.URI uri) {
        URI = uri.toString();
        musicMedia = new Media(URI);
        musicMediaPlayer = new MediaPlayer(musicMedia);
        musicMediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                playing = false;
                try {
                    for (CallBack callBack : stopCallBacks) {
                        callBack.run();
                    }
                } catch (Exception e) {
                }
            }
        });
        musicMediaPlayer.setOnPlaying(new Runnable() {
            @Override
            public void run() {
                playing = false;
                try {
                    for (CallBack callBack : startCallBacks) {
                        callBack.run();
                    }
                } catch (Exception e) {
                }
            }
        });
    }
    public void setStopCallBacks(CallBack... callBacks) {
        this.stopCallBacks.addAll(Arrays.asList(callBacks));
    }
    public void removeALLStopCallBacls() {
        this.stopCallBacks.removeAll(stopCallBacks);
    }
    public void removeStopCallBacls(CallBack... callBacks) {
        this.stopCallBacks.removeAll(Arrays.asList(callBacks));
    }
    public void setStartCallBacks(CallBack...callBacks) {
        this.startCallBacks.addAll(Arrays.asList(callBacks));
    }
    public void removeStartCallBacks(CallBack...callBacks){
        this.startCallBacks.removeAll(Arrays.asList(callBacks));
    }
    public void play() {
        playing = true;
        musicMediaPlayer.play();
    }
    public boolean isPlaying() {
        return playing;
    }
    public MediaPlayer getMusicMediaPlayer() {
        return musicMediaPlayer;
    }
    public void stop() {
        musicMediaPlayer.stop();
    }
    public void pause() {
        musicMediaPlayer.pause();
    }
}
