package cn.korostudio.jsme.core.loader;

import cn.korostudio.jsme.core.err.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class Starter {
    static final private Logger logger = LoggerFactory.getLogger(Starter.class);

    private static ArrayList<Class<?>> findClass() {
        Field field = null;
        Vector<Class<?>> classes = null;
        ArrayList<Class<?>> reClasses = new ArrayList<>();
        try {
            field = ClassLoader.class.getDeclaredField("classes");
            field.setAccessible(true);
            //classes=(Vector<Class<?>>)field.get(ClassLoader.getSystemClassLoader());
            classes = (Vector<Class<?>>) field.get(ClassLoader.getSystemClassLoader());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.error("获取类加载器错误。");
            Error.error(Starter.class, e);
        }
        if (classes != null) reClasses.addAll(classes);
        for (String className : scanAllClasses()) {
            try {
                reClasses.add(Class.forName(className));
            } catch (Exception ignored) {
                logger.error("有非Class文件被反射。");
            }
        }
        logger.debug("已取得运行时所含所有类。");
        return reClasses;
    }

    private static void runApp(ArrayList<Class<?>> runClass, String[] args) {
        for (Class cla : runClass) {
            try {
                cn.korostudio.jsme.core.Application.start(cla, args);
            } catch (Exception e) {
                logger.error(cla.getName() + "启动失败！");
                Error.error(cla, e);
            }
        }
    }

    public static void main(String[] args) {
        logger.info("正在通过注解方式启动。");

        ArrayList<Class<?>> runClass = new ArrayList<>();
        ArrayList<Class<?>> classes = findClass();
        for (Class cla : classes) {
            logger.debug("正在扫描:" + cla.getName() + "-" + Arrays.toString(cla.getAnnotations()));
            if (cla.getAnnotation(JSMEApplication.class) != null) {
                runClass.add(cla);
                logger.debug("扫描到:" + cla.getName() + "应用启动类。");
            }
        }
        logger.debug("扫描结束,已获得" + runClass.size() + "个启动类。");
        runApp(runClass, args);
    }

    /*
    以下是遍历项目的方法
    */
    public static List<String> scanAllClasses() {
        String url = getClassPath();
        // 遍历classes，如果发现@Component就注入到容器中
        return getClassesList(url);
    }

    private static String getClassPath() {
        String url = URLDecoder.decode(Starter.class.getResource("/").getPath(), Charset.defaultCharset());
        if (url.startsWith("/")) {
            url = url.replaceFirst("/", "");
        }
        url = url.replaceAll("/", "\\\\");
        return url;
    }

    private static List<String> getClassesList(String url) {
        File file = new File(url);
        List<String> classes = getAllClass(file);
        for (int i = 0; i < classes.size(); i++) {
            classes.set(i, classes.get(i).replace(url, "").replace(".class", "").replace("\\", "."));
        }
        return classes;
    }

    private static List<String> getAllClass(File file) {
        List<String> ret = new ArrayList<>();
        if (file.isDirectory()) {
            File[] list = file.listFiles();
            for (var i : list) {
                var j = getAllClass(i);
                ret.addAll(j);
            }
        } else {

            ret.add(file.getAbsolutePath());
        }
        return ret;
    }
}
