package cn.korostudio.jsme.tools.image;

import com.alibaba.fastjson.annotation.JSONField;

public class JSONImageFile {
    @JSONField(name = "file")
    protected String file;
    @JSONField(name = "name")
    protected String name;

    public JSONImageFile() {
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
