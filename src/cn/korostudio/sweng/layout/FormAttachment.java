package cn.korostudio.sweng.layout;

public final class FormAttachment {
    protected float percentage; // 这个参数与SWT中的不同，不叫numerator，而是其等价的小数形式
    protected int offset;
    public FormAttachment(float percentage, int offset) {
        this.percentage = percentage;
        this.offset = offset;
    }
}