package cn.korostudio.jsme.core.err;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Error {
    public static String error(Class<?> errClass, Exception e) {
        Logger logger = LoggerFactory.getLogger(errClass);
        String error = "";

        try {
            StackTraceElement[] messages = e.getStackTrace();
            int length = messages.length;
            if (e.getCause() != null) {
                error = error + ("Error:" + e.getCause().toString() + "\n");
                logger.error("Error:" + e.getCause().toString());
            } else {
                error = error + ("Error:" + e + "\n");
                logger.error("Error:" + e);
            }

            for (StackTraceElement message : messages) {
                error = error + ("ClassName:" + message.getClassName() + "\n");
                logger.error("ClassName:" + message.getClassName());
                error = error + ("getFileName:" + message.getFileName() + "\n");
                logger.error("getFileName:" + message.getFileName());
                error = error + ("getLineNumber:" + message.getLineNumber() + "\n");
                logger.error("getLineNumber:" + message.getLineNumber());
                error = error + ("getMethodName:" + message.getMethodName() + "\n");
                logger.error("getMethodName:" + message.getMethodName());
                error = error + ("toString:" + message.toString() + "\n");
                logger.error("toString:" + message.toString());
            }

            //JOptionPane.showMessageDialog(null, error.toString(), "错误", 0);
        } catch (Exception e1) {
            //JOptionPane.showMessageDialog(null, "无法获取异常数据", "错误", 0);
            logger.error(e1.toString());
            System.exit(0);
        }
        return error;
    }
}
