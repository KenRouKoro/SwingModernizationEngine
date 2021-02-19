package cn.korostudio.sweng.layout;

public class FormDatas extends FormData{
    public FormDatas(float levelPercentage,int levelOffset,float verticalPercentage,int ){

    }
    //设置水平位置
    public void setLevel(FormAttachment formAttachment){
        bottom=formAttachment;
        top=new ReFormAttachment(formAttachment);
    }
    //设置垂直位置
    public void setVertical(FormAttachment formAttachment){

    }
    protected class ReFormAttachment extends FormAttachment{
        public ReFormAttachment(float percentage, int offset) {
            super(percentage, 0);
        }

        @Override
        public FormAttachment set(float percentage, int offset) {
            return super.set(percentage, 0);
        }

        public ReFormAttachment(FormAttachment formAttachment){
            super(formAttachment.percentage, 0);
        }

        public void setFormAttachment(FormAttachment formAttachment) {
            this.percentage = formAttachment.percentage;
        }
    }
}
