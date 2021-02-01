package cn.korostudio.sweng.tools.image;

import com.alibaba.fastjson.annotation.JSONField;

public class JSONImageFile {
    @JSONField(name = "file")
    protected String file;
    @JSONField(name = "name")
    protected String name;

    public JSONImageFile(){}

    public void setFile(String file) {
        this.file = file;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile() {
        return file;
    }

    public String getName() {
        return name;
    }
}
