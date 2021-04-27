package cn.korostudio.jsme.tools.classscanner;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @Author
 * @Description 包扫描器
 * @CopyRight
 */
public class ClassScanner {
    private final String CLASS_FILE_SUFFIX = ".class";                       // Java字节码文件后缀
    private Map<String, Class<?>> classes = new HashMap<>();
    private FilenameFilter javaClassFilter;                                    // 类文件过滤器,只扫描一级类
    private String packPrefix;                                         // 包路径根路劲

    public ClassScanner() {
        javaClassFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                // 排除内部内
                return !name.contains("$");
            }
        };
    }

    /**
     * @param basePath    包所在的根路径
     * @param packagePath 目标包路径
     * @return Integer 被扫描到的类的数量
     * @throws ClassNotFoundException
     * @Title: scanning
     * @Description 扫描指定包(本地)
     */
    public Integer scanning(String basePath, String packagePath) throws ClassNotFoundException {
        packPrefix = basePath;

        String packTmp = packagePath.replace('.', '/');
        File dir = new File(basePath, packTmp);

        // 不是文件夹
        if (dir.isDirectory()) {
            scan0(dir);
        }

        return classes.size();
    }

    /**
     * @param packagePath 包路径
     * @param recursive   是否扫描子包
     * @return Integer 类数量
     * @Title: scanning
     * @Description 扫描指定包, Jar或本地
     */
    public Integer scanning(String packagePath, boolean recursive) {
        Enumeration<URL> dir;
        String filePackPath = packagePath.replace('.', '/');
        try {
            // 得到指定路径中所有的资源文件
            dir = Thread.currentThread().getContextClassLoader().getResources(filePackPath);
            packPrefix = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            if (System.getProperty("file.separator").equals("\\")) {
                packPrefix = packPrefix.substring(1);
            }

            // 遍历资源文件
            while (dir.hasMoreElements()) {
                URL url = dir.nextElement();
                String protocol = url.getProtocol();

                if ("file".equals(protocol)) {
                    File file = new File(url.getPath().substring(1));
                    scan0(file);
                } else if ("jar".equals(protocol)) {
                    scanJ(url, recursive);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return classes.size();
    }

    /**
     * @param url       jar-url路径
     * @param recursive 是否递归遍历子包
     * @throws IOException
     * @throws ClassNotFoundException
     * @Title: scanJ
     * @Description 扫描Jar包下所有class
     */
    private void scanJ(URL url, boolean recursive) throws IOException, ClassNotFoundException {
        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
        JarFile jarFile = jarURLConnection.getJarFile();

        // 遍历Jar包
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = (JarEntry) entries.nextElement();
            String fileName = jarEntry.getName();

            if (jarEntry.isDirectory()) {
                if (recursive) {
                }
                continue;
            }

            // .class
            if (fileName.endsWith(CLASS_FILE_SUFFIX)) {
                String className = fileName.substring(0, fileName.indexOf('.')).replace('/', '.');
                classes.put(className, Class.forName(className));
            }

        }
    }

    /**
     * @param dir Java包文件夹
     * @throws ClassNotFoundException
     * @Title: scan0
     * @Description 执行扫描
     */
    private void scan0(File dir) throws ClassNotFoundException {
        File[] fs = dir.listFiles(javaClassFilter);
        for (int i = 0; fs != null && i < fs.length; i++) {
            File f = fs[i];
            String path = f.getAbsolutePath();

            // 跳过其他文件
            if (path.endsWith(CLASS_FILE_SUFFIX)) {
                String className = StringUtil.getPackageByPath(f, packPrefix); // 获取包名
                classes.put(className, Class.forName(className));
            }
        }
    }

    /**
     * @return Map&lt;String,Class&lt;?&gt;&gt; K:类全名, V:Class字节码
     * @Title: getClasses
     * @Description 获取包中所有类
     */
    public Map<String, Class<?>> getClasses() {
        return classes;
    }

}
