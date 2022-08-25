package util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * service工程文件工具类
 * 不与其他的包混用，宁愿冗余
 */
@Slf4j
public class ServiceFileUtil {

    /**
     * @param path:
     * @description: 创建多级目录
     * @return: void
     * @author: yesi
     * @date: 2022/4/25 14:48
     */
    public static void mkdirs(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                try {
                    log.debug("创建目录:" + dir.getCanonicalPath());
                } catch (IOException e) {
                    log.error("创建目录失败:" + dir);
                }
            }
        }
    }

    /**
     * 将内容写入文件，多次写入内容会覆盖
     *
     * @param filepath 文件全路径
     * @param content  内容
     * @throws IOException
     */
    public static void writeTofile(String filepath, String content) throws IOException {
        Files.write(Paths.get(filepath), content.getBytes());
    }

    /**
     * 将列表内容以追加的方式写入文件
     *
     * @param fileName
     * @param contentList
     */
    public static void writeAppendByList(String fileName, List<String> contentList) throws Exception {
        String pattrn = File.separator;
        if (!fileName.contains(pattrn)) {
            pattrn = "/";
        }
        String destFileDir = fileName.substring(0, fileName.lastIndexOf(pattrn));

        //储存下载文件的目录
        File dir = new File(destFileDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try (RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");) {
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            for (String content : contentList) {
                randomFile.write(content.concat("\r\n").getBytes("UTF-8"));
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 将列表内容以追加的方式写入文件
     *
     * @param fileName
     * @param contents
     */
    public static void writeAppend(String fileName, String contents) throws Exception {
        String pattrn = File.separator;
        if (!fileName.contains(pattrn)) {
            pattrn = "/";
        }
        String destFileDir = fileName.substring(0, fileName.lastIndexOf(pattrn));

        //储存下载文件的目录
        File dir = new File(destFileDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try (RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");) {
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.write(contents.concat("\r\n").getBytes("UTF-8"));
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param path 文件的全路径
     * @return
     */
    public static boolean judeFileExists(String path) {
        File file = new File(path);
        if (null != file && file.exists() && file.isFile()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断文件夹是否存在
     *
     * @param path 文件的全路径
     * @return
     */
    public static boolean judeFolderExists(String path) {
        File file = new File(path);
        if (null != file && file.exists() && file.isDirectory()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 读取txt文件，未做任何处理
     *
     * @param filePath
     * @return
     */
    public static String readTxt2String(String filePath) {
        File file = new File(filePath);
        StringBuilder result = new StringBuilder();
        InputStreamReader read = null;
        BufferedReader br = null;
        try {
            read = new InputStreamReader(new FileInputStream(file), "UTF-8");
            br = new BufferedReader(read);
            String s = null;
            boolean isNotfirst = false;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                if (isNotfirst) {
                    result.append(System.lineSeparator());
                }
                result.append(s);
                isNotfirst = true;
            }
        } catch (Exception e) {
            log.error(" 读取文件:" + filePath + " 失败！ ");
            log.error(e.getMessage());
        } finally {
            try {
                br.close();
                read.close();
            } catch (IOException e) {
                log.error(" 读取文件:" + filePath + " 失败-关闭文件流失败！");
            }
        }
        return result.toString();
    }

    /**
     * 读取txt文件，未做任何处理，按行输出
     *
     * @param filePath
     * @return
     * @date 2017-05-04
     */
    public static List<String> readTxt2Line(String filePath) {
        List<String> contentList = null;
        File file = new File(filePath);
        if (file.isFile() && file.exists()) { // 判断文件是否存在
            InputStreamReader read = null;
            BufferedReader bufferedReader = null;
            contentList = new ArrayList<String>();
            try {
                read = new InputStreamReader(new FileInputStream(file), "UTF-8");
                // 考虑到编码格式
                bufferedReader = new BufferedReader(read);
                String lineTxt = null;

                while ((lineTxt = bufferedReader.readLine()) != null) {
                    contentList.add(lineTxt);
                }

            } catch (IOException e) {
                log.error(" 读取文件:" + filePath + " 失败！ ");
                log.error(e.getMessage());
            } finally {
                try {
                    bufferedReader.close();
                    read.close();
                } catch (IOException e) {
                    log.error(" 读取文件:" + filePath + " 失败-关闭文件流失败！");
                }
            }
        } else {
            log.error(" 在目录:" + filePath + ",找不到文件！ ");
        }
        return contentList;
    }

    /**
     * 根据路径获取该路径下的所有数字yyyymm文件夹的最大值
     *
     * @param path
     * @return
     * @date 2018-08-02
     */
    public static String findMaxYMFolder(String path) {
        return findMaxFolder(path, "\\d{6}");
    }

    /**
     * 根据路径获取该路径下的所有数字dd文件夹的最大值
     *
     * @param path
     * @return
     * @date 2018-08-02
     */
    public static String findMaxDDFolder(String path) {
        return findMaxFolder(path, "\\d{2}");
    }

    /**
     * 根据路径获取该路径下的所有数字文件夹的最大值
     *
     * @param path
     * @param filterPattern
     * @return
     */
    public static String findMaxFolder(String path, String filterPattern) {
        List<String> folders = findFolders(path, filterPattern);
        String max = null;
        if (null != folders) {
            max = folders.get(0);
        }
        for (int i = 1; i < folders.size(); i++) {
            BigDecimal maxBigdecimal = new BigDecimal(max);
            BigDecimal comp = new BigDecimal(folders.get(i));
            //max < value
            if (maxBigdecimal.compareTo(comp) == -1) {
                max = folders.get(i);
            }
        }
        return max;
    }

    /**
     * 根据路径获取该路径下的所有文件加（不包括循环的子文件夹）
     *
     * @param path
     * @param filterPattern 文件过滤的正则表达式
     * @return
     * @date 2017-05-05
     */
    public static List<String> findFolders(String path, final String filterPattern) {
        List<String> result = null;
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            result = new ArrayList<String>();
            FileFilter ff = new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    Pattern p = Pattern.compile(filterPattern);
                    Matcher m = p.matcher(pathname.getName());
                    return m.matches();
                }

            };
            File[] subFiles = file.listFiles(ff);
            for (File subFile : subFiles) {
                if (subFile.isDirectory()) {
                    result.add(subFile.getName());
                }
            }
        } else {
            log.debug("路径：" + path + " ,不存在或不是文件夹");
        }
        return result;
    }

    /**
     * 根据路径获取该路径下的所有文件（不包括循环的子文件夹）
     *
     * @param path
     * @return
     * @date 2017-05-05
     */
    public static List<String> findFiles(String path) {
        List<String> result = null;
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            result = new ArrayList<String>();
            File[] subFiles = file.listFiles();
            for (File subFile : subFiles) {
                result.add(subFile.getName());
            }
        } else {
            log.debug("路径：" + path + " ,不存在或不是文件夹");
        }
        return result;
    }

    /**
     * 根据路径获取该路径下的所有文件（不包括循环）
     * * 2019-12-17因为文件夹下肯定是文件（规则由此设定），去除判断
     *
     * @param path
     * @param filterPattern 文件过滤的正则表达式
     * @return
     * @date 2017-06-30
     * @modify 2019-12-17
     */
    public static List<String> findFiles(String path, final String filterPattern) {
        List<String> result = null;
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            result = new ArrayList<String>();
            FileFilter ff = new FileFilter() {

                public boolean accept(File pathname) {
                    Pattern p = Pattern.compile(filterPattern);
                    Matcher m = p.matcher(pathname.getName());
                    return m.matches();
                }

            };
            File[] subFiles = file.listFiles(ff);
            for (File subFile : subFiles) {
                result.add(subFile.getName());
//				 if(subFile.exists()&& !subFile.isDirectory()){
//					 result.add(subFile.getName());
//				 }
            }
        } else {
            log.debug("路径：" + path + " ,不存在或不是文件夹");
        }
        return result;
    }

    /**
     * @param path
     * @param files
     * @return List<String>
     * @description 移动文件
     * @author qyl
     * @date 2022/7/18 12:43
     */
    public static List<String> saveFiles(StringBuilder path, List<MultipartFile> files) throws IOException {
        String fileName = "";
        List<String> pathList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(files)) {
            for (MultipartFile file : files) {
                fileName = file.getOriginalFilename();
                log.debug("上传文件名称：" + fileName);
                //上传的文件路径 为 temp目录+id目录+ 文件名
                File targetFile = new File(path + fileName);
                if (!targetFile.exists()) {    //判断文件的路径是否存在
                    targetFile.mkdirs();  //如果文件不存在  在目录中创建文件夹   这里要注意mkdir()和mkdirs()的区别
                }
                file.transferTo(targetFile); //传送  失败就抛异常
                pathList.add(fileName);
            }
        }
        return pathList;
    }

    /**
     * @description 文件夹中文件复制
     * @param src
     * @param tar
     * @return void
     * @author qyl
     * @date 2022/8/16 11:18
     */
    public static void copyFiles(StringBuilder src,StringBuilder tar) throws IOException {
        File file = new File(tar.toString()) , srcFile ,tarFile;
        if (!file.exists()){
            file.mkdirs();
        }
        List<String> files = ServiceFileUtil.findFiles(src.toString());
        if(!CollectionUtils.isEmpty(files)){
            for (String filename : files) {
                srcFile = new File(src.append(filename).toString());
                tarFile = new File(tar.append(filename).toString());
                Files.copy(srcFile.toPath(),tarFile.toPath());
            }
        }
    }

    /**
     * @param path
     * @return void
     * @description 删除文件
     * @author qyl
     * @date 2022/8/15 11:19
     */
    public static void deleteFiles(StringBuilder path){
        // 查看目标文件夹里的文件
        File old = new File(path.toString());
        if (old.exists()) {
            List<String> existsFiles = ServiceFileUtil.findFiles(path.toString());
            if (!CollectionUtils.isEmpty(existsFiles)) {
                for (String filename : existsFiles) {
                    File file = new File(path.append(filename).toString());
                    file.delete();
                }
            }

            try {
                old.delete();
            } catch (Exception e) {
                log.error(e.getMessage());
                log.error("删除目录失败：{}", path);
            }
        }
    }
}
