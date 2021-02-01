package cn.korostudio.sweng.tools.image;

import cn.korostudio.sweng.tools.Tool;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public class ImageLoader {

    static public void loadJSONImage(URI uri) {
        try {
            File file = Paths.get(uri).toFile();
            if (file.isDirectory()) {
                for (File file1 : Objects.requireNonNull(file.listFiles())) {
                    if (!(file1.getName().substring(file1.getName().lastIndexOf(".") + 1)).equalsIgnoreCase("json"))
                        continue;
                    loadJSONImage(file1.toURI());
                }
            } else {
                String json = "{}";
                try {
                    json = FileUtils.readFileToString(file, "utf-8");
                } catch (IOException e) {
                    System.err.println("WARRING:Read JSON String ERROR!");
                }
                ArrayList<JSONImageFile> imageFiles = Tool.getJSONImageFile(json);
                for (JSONImageFile jsonImageFile : imageFiles) {
                    ImageBase.put(Tool.loadImage(new File(file.getParent() + "/" + jsonImageFile.getFile())), jsonImageFile.getName());
                }
            }
        } catch (Exception e) {
            System.err.println("WARRING:JSON File Not Found!");
        }
    }
    static public void removeJSONImage(URI uri){
        File file=Paths.get(uri).toFile();
        String json = "{}";
        try {
            json= FileUtils.readFileToString(file,"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray=JSONArray.parseArray(json);
        for (Object obj:jsonArray.toArray()){
            JSONObject jsonObject=(JSONObject)obj;
            ImageBase.remove(jsonObject.getString("name"));
        }
    }
}
