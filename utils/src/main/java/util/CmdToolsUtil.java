package util;

/**
 * @author qyl
 * @program CmdTool.java
 * @Description cmd 工具类
 * @createTime 2022-08-25 17:23
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CmdToolsUtil {
    protected static Logger logger = LoggerFactory.getLogger(CmdToolsUtil.class);

    public CmdToolsUtil() {
    }

    public static void main(String[] args) {
        executeLinuxCmd("dir");
    }

    public static List<String> executeLinuxCmd(String cmd) {
        logger.debug("got cmd job : " + cmd);
        Runtime run = Runtime.getRuntime();

        try {
            Process process = run.exec(new String[]{"/bin/sh", "-c", cmd});
            InputStream in = process.getInputStream();
            BufferedReader bs = new BufferedReader(new InputStreamReader(in));
            List<String> list = new ArrayList();
            String result = null;

            while((result = bs.readLine()) != null) {
                logger.debug("job result [" + result + "]");
                list.add(result);
            }

            in.close();
            process.destroy();
            return list;
        } catch (IOException var7) {
            var7.printStackTrace();
            logger.error(var7.getMessage());
            return null;
        }
    }
}
