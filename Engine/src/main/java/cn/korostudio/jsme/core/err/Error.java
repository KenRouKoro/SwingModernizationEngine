package cn.korostudio.jsme.core.err;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Error {
    public static final String error(Exception e) {
        StringBuffer error = new StringBuffer();
        File file = new File("error.log");
        try {
            StackTraceElement[] messages = e.getStackTrace();
            int length = messages.length;
            FileWriter fwr = new FileWriter(file);
            BufferedWriter bfr = new BufferedWriter(fwr);
            error.append("error:" + e.getCause().toString() + "\n");
            bfr.write("\n");
            bfr.write("error:" + e.getCause().toString() + "\n");
            for (int i = 0; i < length; i++) {
                error.append("ClassName:" + messages[i].getClassName() + "\n");
                bfr.write("ClassName:" + messages[i].getClassName() + "\n");
                error.append("getFileName:" + messages[i].getFileName() + "\n");
                bfr.write("getFileName:" + messages[i].getFileName() + "\n");
                error.append("getLineNumber:" + messages[i].getLineNumber() + "\n");
                bfr.write("getLineNumber:" + messages[i].getLineNumber() + "\n");
                error.append("getMethodName:" + messages[i].getMethodName() + "\n");
                bfr.write("getMethodName:" + messages[i].getMethodName() + "\n");
                error.append("toString:" + messages[i].toString() + "\n");
                bfr.write("toString:" + messages[i].toString() + "\n");
            }
            JOptionPane.showMessageDialog(null, error.toString(), "错误", 0);
                    bfr.close();
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(null, "错误文件写入错误", "错误", 0);
            System.exit(0);
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(null, "无法获取异常数据", "错误", 0);
            System.exit(0);
        }
        System.exit(0);
        return error.toString();
    }
}
