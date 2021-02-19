package cn.korostudio.sweng.layout;

public  class FormAttachment {
    protected float percentage=0; // 这个参数与SWT中的不同，不叫numerator，而是其等价的小数形式
    protected int offset=0;
    public FormAttachment(float percentage, int offset) {
        this.percentage = percentage;
        this.offset = offset;
    }
    public FormAttachment set(float percentage,int offset){
        this.percentage = percentage;
        this.offset = offset;
        return  this;
    }
}