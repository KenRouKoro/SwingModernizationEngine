package cn.korostudio.jsme.tools.tween;

import aurelienribon.tweenengine.*;
import aurelienribon.tweenengine.equations.Cubic;
import cn.korostudio.jsme.core.err.Error;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TweenSystem {
    protected TweenManager tweenManager;
    protected CopyOnWriteArrayList<TweenListener>tweenListeners=new CopyOnWriteArrayList<>();
    protected CopyOnWriteArrayList<Tween> tweens=new CopyOnWriteArrayList<>();
    final protected TweenEquation tweenMode = Cubic.INOUT;
    protected Thread runThread;
    protected float time = 2f;
    protected boolean running = false;
    protected boolean stop = false;
    protected Runnable runnable;
    protected boolean pause = false;
    protected boolean loop = false;
    protected boolean looping = false;
    protected TweenSystem tweenSystem = this;
    protected float looptime = 0;

    class RunThread implements Runnable {

        public void run() {
            long lastMillis = System.currentTimeMillis();
            long deltaLastMillis = System.currentTimeMillis();
            running = true;
            while (running) {
                if (stop | !running) return;
                long newMillis = System.currentTimeMillis();
                long sleep = 1000 / 60 - (newMillis - lastMillis);
                lastMillis = newMillis;
                if (sleep > 1){
                    try {
                        Thread.sleep(sleep);
                    }
                    catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                long deltaNewMillis = System.currentTimeMillis();
                final float delta = (deltaNewMillis - deltaLastMillis) / 1000f;
                try {
                    SwingUtilities.invokeAndWait(new Runnable() {
                        public void run() {
                            tweenManager.update(delta);
                            List<BaseTween<?>> baseTweens=tweenManager.getObjects();
                            boolean canStop=true;
                            for (BaseTween baseTween:baseTweens){
                                if (!baseTween.isFinished())canStop=false;
                            }
                            if (canStop)running=false;
                        }});
                } catch (InterruptedException ex) {
                } catch (InvocationTargetException ex) {
                }
                deltaLastMillis = newMillis;
            }
            running = false;
            if (!stop) for (TweenListener tweenListener : tweenListeners) {
                tweenListener.finish();
            }
        }
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLooptime(float looptime) {
        this.looptime = looptime;
    }

    public boolean isRunning() {
        return running;
    }

    public TweenSystem start() {
        if (!pause) {
            load();
            for (TweenListener tweenListener : tweenListeners) {
                tweenListener.start();
            }
            runThread.start();
            running = true;
            stop = false;
            return this;
        } else {
            pause = false;
            for (TweenListener tweenListener : tweenListeners) {
                tweenListener.start();
            }
            List<BaseTween<?>> baseTweens = tweenManager.getObjects();
            for (BaseTween baseTween : baseTweens) {
                baseTween.resume();
            }
            return this;
        }
    }

    public void stop() {
        stop = true;
        running = false;
        List<BaseTween<?>> baseTweens = tweenManager.getObjects();
        for (BaseTween baseTween : baseTweens) {
            baseTween.free();
        }
        for (TweenListener tweenListener : tweenListeners) {
            tweenListener.stop();
        }
    }

    public void pause() {
        pause = true;
        List<BaseTween<?>> baseTweens = tweenManager.getObjects();
        for (BaseTween baseTween : baseTweens) {
            baseTween.pause();
        }
        for (TweenListener tweenListener : tweenListeners) {
            tweenListener.pause();
        }
    }

    public TweenSystem addTweenListener(TweenListener... tweenListeners) {
        this.tweenListeners.addAll(Arrays.asList(tweenListeners));
        return this;
    }

    public TweenSystem removeTweenListener(TweenListener... tweenListeners) {
        this.tweenListeners.removeAll(Arrays.asList(tweenListeners));
        return this;
    }

    public void add(Tween ...tween){
        tweens.addAll(Arrays.asList(tween));
    }

    public void removeALL(){
        tweens.removeAll(tweens);
    }

    public void remove(Tween ...tween){
        tweens.removeAll(Arrays.asList(tween));
    }

    public Tween add(Component component,int mode,TweenEquation tweenMode,float time,boolean loop,float ... args){
        Tween tween=Tween.to(component,mode,time).ease(tweenMode).target(args);

        return tween;
    }



    /*
    关键加载——初始化方法。
    */

    protected void load() {
        try {
            Tween.registerAccessor(Component.class, new TweenImplements());
            tweenManager = new TweenManager();
            /*
            if (!timeLine) {
                Tween tween = Tween.to(component, mode, time).ease(tweenMode).target(arg);
                if (loop) tween.repeat(Tween.INFINITY, looptime);
                tween.start(tweenManager);
            } else {
             */
            Timeline timeLine = Timeline.createSequence()
                    .beginSequence();
            for (Tween tween:tweens){
                timeLine.push(tween);
            }
            if (loop) timeLine.repeat(Tween.INFINITY, looptime);
            timeLine.start(tweenManager);
            runThread = new Thread(new RunThread());
        }catch (Exception e){
            Error.error(e);
        }
    }
}
