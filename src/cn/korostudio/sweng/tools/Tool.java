package cn.korostudio.sweng.tools;

import cn.korostudio.sweng.tools.image.JSONImageFile;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Tool {
    public static ArrayList <JSONImageFile> getJSONImageFile(String json){
        ArrayList <JSONImageFile> arrayList=new ArrayList<>();
        JSONArray jsonArray=JSONArray.parseArray(json);
        for(Object obj:jsonArray.toArray()){
            arrayList.add(JSON.parseObject(((JSONObject)obj).toJSONString(),JSONImageFile.class));
        }
        return arrayList;
    }
    //获取res文件
    final static public URI getRes(String file,Class resClass) {
        try {
            return resClass.getResource(file).toURI();
        } catch (Exception e) {
            return new File(file.substring(1, file.length())).toURI();
        }
    }
    static public BufferedImage rotateImage(BufferedImage image, int rotate) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
        g2d.rotate(rotate * Math.PI / 180, image.getWidth() / 2, image.getHeight() / 2);
        g2d.drawImage(image, 0, 0, null);
        return bufferedImage;
    }

    static public ArrayList<BufferedImage> get360Image(BufferedImage image, int afterW, int afterH) {
        ArrayList<BufferedImage> images = new ArrayList<>();
        for (int i = 0; i <= 360; i++) {
            images.add(reImageSize(rotateImage(image, i), afterW, afterH));
        }
        return images;
    }

    static public String readFile(File file) {
        try {
            return FileUtils.readFileToString(file, "utf-8");
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 获取某包下（包括该包的所有子包）所有类
     *
     * @param packageName 包名
     * @return 类的完整名称
     */
    public static ArrayList<String> getClassName(String packageName) {
        return getClassName(packageName, true);
    }

    /**
     * 获取某包下所有类
     *
     * @param packageName  包名
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    public static ArrayList<String> getClassName(String packageName, boolean childPackage) {
        ArrayList<String> fileNames = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String packagePath = packageName.replace(".", "/");
        URL url = loader.getResource(packagePath);
        if (url != null) {
            String type = url.getProtocol();
            if (type.equals("file")) {
                fileNames = getClassNameByFile(url.getPath(), null, childPackage);
            } else if (type.equals("jar")) {
                fileNames = getClassNameByJar(url.getPath(), childPackage);
            }
        } else {
            fileNames = getClassNameByJars(((URLClassLoader) loader).getURLs(), packagePath, childPackage);
        }
        return fileNames;
    }

    /**
     * 从项目文件获取某包下所有类
     *
     * @param filePath     文件路径
     * @param className    类名集合
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    private static ArrayList<String> getClassNameByFile(String filePath, ArrayList<String> className, boolean childPackage) {
        ArrayList<String> myClassName = new ArrayList<>();
        File file = new File(filePath);
        File[] childFiles = file.listFiles();
        for (File childFile : childFiles) {
            if (childFile.isDirectory()) {
                if (childPackage) {
                    myClassName.addAll(getClassNameByFile(childFile.getPath(), myClassName, childPackage));
                }
            } else {
                String childFilePath = childFile.getPath();
                if (childFilePath.endsWith(".class")) {
                    childFilePath = childFilePath.substring(childFilePath.indexOf("\\classes") + 9,
                            childFilePath.lastIndexOf("."));
                    childFilePath = childFilePath.replace("\\", ".");
                    myClassName.add(childFilePath);
                }
            }
        }

        return myClassName;
    }

    /**
     * 从jar获取某包下所有类
     *
     * @param jarPath      jar文件路径
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    private static ArrayList<String> getClassNameByJar(String jarPath, boolean childPackage) {
        ArrayList<String> myClassName = new ArrayList<>();
        String[] jarInfo = jarPath.split("!");
        String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"));
        String packagePath = jarInfo[1].substring(1);
        try {
            JarFile jarFile = new JarFile(jarFilePath);
            Enumeration<JarEntry> entrys = jarFile.entries();
            while (entrys.hasMoreElements()) {
                JarEntry jarEntry = entrys.nextElement();
                String entryName = jarEntry.getName();
                if (entryName.endsWith(".class")) {
                    if (childPackage) {
                        if (entryName.startsWith(packagePath)) {
                            entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
                            myClassName.add(entryName);
                        }
                    } else {
                        int index = entryName.lastIndexOf("/");
                        String myPackagePath;
                        if (index != -1) {
                            myPackagePath = entryName.substring(0, index);
                        } else {
                            myPackagePath = entryName;
                        }
                        if (myPackagePath.equals(packagePath)) {
                            entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
                            myClassName.add(entryName);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myClassName;
    }

    /**
     * 从所有jar中搜索该包，并获取该包下所有类
     *
     * @param urls         URL集合
     * @param packagePath  包路径
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    private static ArrayList<String> getClassNameByJars(URL[] urls, String packagePath, boolean childPackage) {
        ArrayList<String> myClassName = new ArrayList<>();
        if (urls != null) {
            for (int i = 0; i < urls.length; i++) {
                URL url = urls[i];
                String urlPath = url.getPath();
                // 不必搜索classes文件夹
                if (urlPath.endsWith("classes/")) {
                    continue;
                }
                String jarPath = urlPath + "!/" + packagePath;
                myClassName.addAll(getClassNameByJar(jarPath, childPackage));
            }
        }
        return myClassName;
    }

    static public BufferedImage loadImage(File file) {
        System.out.println("LOAD:" + file);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }

    public static void setAntiAliasing(Graphics2D g2, boolean antiAliasing) {
        if (antiAliasing)
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING
                    , RenderingHints.VALUE_ANTIALIAS_ON);
        else
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING
                    , RenderingHints.VALUE_ANTIALIAS_OFF);
    }

    static public BufferedImage reImageSize(BufferedImage image, int w, int h) {
        BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
        setAntiAliasing(g2d, true);
        g2d.drawImage(image.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, null);
        return bufferedImage;
    }

    static public BufferedImage loadImage(String file,Class resClass) {
        try {
            System.out.println("LOAD:" + Paths.get(resClass.getResource(file).toURI()));
        } catch (URISyntaxException e) {
            System.err.println("WARRING:RES System Error!");
        }
        URI uri = null;
        try {
            uri = resClass.getResource(file).toURI();
        } catch (URISyntaxException e) {
            System.err.println("WARRING:TOURI Error! ");
        }
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(uri.toURL());
        } catch (IOException e) {
            System.err.println("WARRING:IMAGEIO Load Error!");
        }
        return bufferedImage;
    }

    static public BufferedImage loadImage(URI uri) {
        System.out.println("LOAD:" + uri);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(uri.toURL());
        } catch (IOException e) {
            System.err.println("WARRING:IMAGEIO Load Error!");
        }
        return bufferedImage;
    }

    static final public BufferedImage fillRect(int width, int height, Color color) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
        g2d.setColor(color);
        g2d.fillRect(0,0,width,height);
        return bufferedImage;
    }
    static public BufferedImage stringImage(Color color,Font font,String string){
        int i[] = count(string);
        int width = i[0] * font.getSize() + (i[1] + i[2] + i[3] + i[4] + 1) * font.getSize() / 2;
        BufferedImage test = new BufferedImage(200, 200, BufferedImage.TYPE_4BYTE_ABGR);
        test.getGraphics().setFont(font);
        FontRenderContext context= ((Graphics2D)test.getGraphics()).getFontRenderContext();
        LineMetrics lineMetrics = font.getLineMetrics(string, context);


        BufferedImage bufferedImage=new BufferedImage(width, (int) lineMetrics.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d =(Graphics2D) bufferedImage.getGraphics();
        g2d.setFont(font);
        float offset = 0;
        float y = (font.getSize() + lineMetrics.getAscent() - lineMetrics.getDescent() - lineMetrics.getLeading()) / 2;
        g2d.setColor(color);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.drawString(string,0,y);
        return bufferedImage;
    }
    static public int[] count(String str) {
        /*中文字符 */
        int chCharacter = 0;
        /*英文字符 */
        int enCharacter = 0;
        /*空格 */
        int spaceCharacter = 0;
        /*数字 */
        int numberCharacter = 0;
        /*其他字符 */
        int otherCharacter = 0;
        if (null == str || str.equals("")) {
            return null;
        }

        for (int i = 0; i < str.length(); i++) {
            char tmp = str.charAt(i);
            if ((tmp >= 'A' && tmp <= 'Z') || (tmp >= 'a' && tmp <= 'z')) {
                enCharacter ++;
            } else if ((tmp >= '0') && (tmp <= '9')) {
                numberCharacter ++;
            } else if (tmp ==' ') {
                spaceCharacter ++;
            } else if (isChinese(tmp)) {
                chCharacter ++;
            } else {
                otherCharacter ++;
            }
        }
        return new int[]{chCharacter, enCharacter, numberCharacter, spaceCharacter, otherCharacter};
    }
    static public boolean isChinese(char ch) {
        //获取此字符的UniCodeBlock
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(ch);
        //  GENERAL_PUNCTUATION 判断中文的“号
        //  CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号
        //  HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }
}
