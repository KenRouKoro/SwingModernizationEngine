package cn.korostudio.jsme.tween;

import aurelienribon.tweenengine.*;
import aurelienribon.tweenengine.equations.Cubic;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TweenSystem {
    protected TweenManager tweenManager;
    protected CopyOnWriteArrayList<TweenListener>tweenListeners=new CopyOnWriteArrayList<>();
    protected TweenEquation tweenMode = Cubic.INOUT;
    protected Component component;
    protected Thread runThread;
    protected boolean again = false;
    protected float time = 2f;
    protected int mode = TweenImplements.XY;
    protected boolean running = false;
    protected float[] arg;
    protected boolean stop = false;
    protected Runnable runnable;
    protected boolean timeLine = false;
    protected CopyOnWriteArrayList<TimeLineDo> timeLineDos = new CopyOnWriteArrayList<>();
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

    public void loop(boolean loop) {
        this.loop = loop;
    }

    public void loop(boolean loop, float looptime) {
        this.loop = loop;
        this.looptime = looptime;
    }

    public boolean isLoop() {
        return loop;
    }

    public void addTimeLineDo(TimeLineDo... timeLineDos) {
        timeLine = true;
        this.timeLineDos.addAll(Arrays.asList(timeLineDos));
    }

    public void removeTimeLineDo(TimeLineDo... timeLineDos) {
        this.timeLineDos.removeAll(tweenListeners);
    }

    public void removeALLTimeLineDo() {
        timeLineDos.removeAll(timeLineDos);
    }

    public TweenSystem setArg(float... arg) {

        this.arg = arg;
        return this;
    }

    public TweenSystem setTime(float time) {
        this.time = time;
        return this;
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
    public TweenSystem setTweenMode(TweenEquation mode){
        this.tweenMode=mode;
        return this;
    }

    public TweenSystem setComponent(Component component){
        this.component=component;
        return this;
    }

    public TweenSystem setMode(int mode){
        this.mode=mode;
        return this;
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

    public void setTimeline(boolean line) {
        timeLine = line;
    }

    protected void load() {
        try {
            Tween.registerAccessor(Component.class, new TweenImplements());
            tweenManager = new TweenManager();
            if (!timeLine) {
                Tween tween = Tween.to(component, mode, time).ease(tweenMode).target(arg);
                if (loop) tween.repeat(Tween.INFINITY, looptime);
                tween.start(tweenManager);
            } else {
                Timeline timeLine = Timeline.createSequence()
                        .beginSequence();
                for (TimeLineDo timeLineDo : timeLineDos) {
                    timeLine.push(Tween.to(timeLineDo.getComponent(), timeLineDo.getMode(), timeLineDo.getTime()).ease(timeLineDo.getTweenMode()).target(timeLineDo.getTarge()));
                }
                if (loop) timeLine.repeat(Tween.INFINITY, looptime);
                timeLine.start(tweenManager);
            }
            runThread = new Thread(new RunThread());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
