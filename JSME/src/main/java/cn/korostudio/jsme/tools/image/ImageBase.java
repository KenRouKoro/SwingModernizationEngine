package cn.korostudio.jsme.tools.image;


import cn.korostudio.jsme.tools.Tool;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.concurrent.ConcurrentHashMap;

public class ImageBase {
    protected static ImageBase imageBase = new ImageBase();
    protected ConcurrentHashMap<String, BufferedImage> imageMap = new ConcurrentHashMap<>();

    private ImageBase() {
    }

    public static void removeALL() {
        imageBase.removeImageMap();
    }

    public static ImageBase getImageBase() {
        return imageBase;
    }

    public static BufferedImage put(URI uri, String name) {
        System.out.println("BASEPUT:" + name);
        BufferedImage bufferedImage = Tool.loadImage(uri);
        imageBase.putImage(bufferedImage, name);
        return bufferedImage;
    }

    public static BufferedImage put(String file, String name, Class resClass) {
        System.out.println("BASEPUT:" + name);
        BufferedImage bufferedImage = Tool.loadImage(file, resClass);
        imageBase.putImage(bufferedImage, name);
        return bufferedImage;
    }

    public static BufferedImage put(BufferedImage bufferedImage, String name) {
        System.out.println("BASEPUT:" + name);
        imageBase.putImage(bufferedImage, name);
        return bufferedImage;
    }

    public static BufferedImage get(String name) {
        return imageBase.getImage(name);
    }

    public static void remove(String name) {
        imageBase.removeImage(name);
    }

    protected void removeImageMap() {
        imageMap = new ConcurrentHashMap<>();
    }

    public ConcurrentHashMap<String, BufferedImage> getImageMap() {
        return imageMap;
    }

    public void putImage(BufferedImage image, String name) {
        imageMap.put(name, image);
    }

    public BufferedImage getImage(String name) {
        return imageMap.get(name);
    }

    public void removeImage(String name) {
        imageMap.remove(name);
    }
}
