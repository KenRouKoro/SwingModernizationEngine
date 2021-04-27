package cn.korostudio.jsme.tools.music;

import cn.korostudio.jsme.core.err.Error;
import cn.korostudio.jsme.listener.MusicPlayerListener;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MusicPlayer {
    private String musicPath;
    private volatile boolean run = true;
    private Thread mainThread;
    private AudioInputStream audioStream;
    private AudioFormat audioFormat;
    private SourceDataLine sourceDataLine;
    private ArrayList<MusicPlayerListener> musicPlayerListeners = new ArrayList<>();


    public MusicPlayer() {

    }

    public MusicPlayer(String file) {
        this.musicPath = file;
        prefetch();
    }

    public void addMusicPlayerListener(MusicPlayerListener... musicPlayerListeners) {
        this.musicPlayerListeners.addAll(Arrays.asList(musicPlayerListeners));
    }

    public void removeMusicPlayerListener(MusicPlayerListener... musicPlayerListeners) {
        this.musicPlayerListeners.removeAll(Arrays.asList(musicPlayerListeners));
    }

    public void setFile(String file) {
        this.musicPath = file;
        prefetch();
    }

    public void start(final boolean loop) throws Exception {
        if (this.musicPath == null)
            throw new Exception("未输入文件地址");
        this.mainThread = new Thread(new Runnable() {
            public void run() {
                MusicPlayer.this.playMusic();
            }
        });
        this.mainThread.start();
    }

    public void stop() {
        (new Thread(new Runnable() {
            public void run() {
                MusicPlayer.this.paushMusic();
                ;
            }
        })).start();
    }

    protected void finalize() throws Throwable {
        super.finalize();
        this.sourceDataLine.drain();
        this.sourceDataLine.close();
        this.audioStream.close();
    }

    private void prefetch() {
        try {
            this.audioStream = AudioSystem.getAudioInputStream(new File(this.musicPath));
            this.audioFormat = this.audioStream.getFormat();
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, this.audioFormat, -1);
            this.sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            this.sourceDataLine.open(this.audioFormat);
            this.sourceDataLine.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
            Error.error(getClass(), ex);
        }
    }


    private void playMusic() {
        try {
            synchronized (this) {
                this.run = true;
                for (MusicPlayerListener listener : musicPlayerListeners) {
                    listener.play();
                }
            }
            this.audioStream = AudioSystem.getAudioInputStream(new File(this.musicPath));
            byte[] tempBuff = new byte[1024];
            int count;
            while ((count = this.audioStream.read(tempBuff, 0, tempBuff.length)) != -1) {
                synchronized (this) {
                    while (!this.run)
                        wait();
                }
                this.sourceDataLine.write(tempBuff, 0, count);
            }
            for (MusicPlayerListener listener : musicPlayerListeners) {
                listener.finish();
            }
        } catch (UnsupportedAudioFileException | IOException | InterruptedException ex) {
            //UnsupportedAudioFileException unsupportedAudioFileException1;
            Error.error(getClass(), ex);
        }
    }

    private void paushMusic() {
        synchronized (this) {
            this.run = false;
            notifyAll();
        }
    }

    private void continueMusic() {
        synchronized (this) {
            this.run = true;
            notifyAll();
        }
    }

    public void continues() {
        (new Thread(new Runnable() {
            public void run() {
                MusicPlayer.this.continueMusic();
            }
        })).start();
    }

    //public MusicPlayer() {}
}
/*
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
 */
