package cn.korostudio.jsme.tween;

import aurelienribon.tweenengine.TweenEquation;

import java.awt.*;

public class TimeLineDo {
    protected float[] targe;
    protected int mode;
    protected Component component;
    protected TweenEquation tweenMode;
    protected float time;

    public TimeLineDo(Component component, int mode, float time, TweenEquation tweenMode, float... targe) {
        this.targe = targe;
        this.mode = mode;
        this.component = component;
        this.tweenMode = tweenMode;
        this.time = time;
    }

    public TimeLineDo(int mode, float time, Component component, float... targe) {
        this.targe = targe;
        this.mode = mode;
        this.component = component;
        this.tweenMode = tweenMode;
        this.time = time;
    }

    public float getTime() {
        return time;
    }

    public int getMode() {
        return mode;
    }

    public Component getComponent() {
        return component;
    }

    public float[] getTarge() {
        return targe;
    }

    public TweenEquation getTweenMode() {
        return tweenMode;
    }
}
