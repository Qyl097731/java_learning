package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: qyl
 * @create: 2022-03-04 09:53
 */
public class CsvUtils {

    protected static Logger logger = LoggerFactory.getLogger(CsvUtils.class);

    /**
     * CSV文件生成方法
     *
     * @param head       文件头
     * @param dataList   数据列表
     * @param outPutPath 文件输出路径
     * @param filename   文件名
     * @return
     */
    public static File createCSVFile(List<Object> head, List<List<Object>> dataList, String outPutPath, String filename) {
        File csvFile = null;
        BufferedWriter csvWtriter = null;
        try {
            csvFile = new File(outPutPath + File.separator + filename + ".csv");
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();

            // GB2312使正确读取分隔符","
            csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    csvFile), "GB2312"), 1024);
            // 写入文件头部
            writeRow(head, csvWtriter);

            // 写入文件内容
            for (List<Object> row : dataList) {
                writeRow(row, csvWtriter);
            }
            csvWtriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvWtriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }

    /**
     * CSV文件生成方法
     * 追加数据
     *
     * @param dataList   数据列表
     * @param outPutPath 文件输出路径
     * @param filename   文件名
     * @return
     */
    public static File createCSVFile2(List<List<Object>> dataList, String outPutPath, String filename) {
        File csvFile = null;
        BufferedWriter csvWtriter = null;
        try {
            csvFile = new File(outPutPath + File.separator + filename + ".csv");
            File parent = csvFile.getParentFile();
            /*if (!parent.exists()) {
                parent.mkdirs();
                csvFile.createNewFile();
            }*/

            // GB2312使正确读取分隔符","
            csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    csvFile), "GB2312"), 1024);

            // 写入文件内容
            for (List<Object> row : dataList) {
                writeRow(row, csvWtriter);
            }
            csvWtriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvWtriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }

    /**
     * 写一行数据方法
     *
     * @param row
     * @param csvWriter
     * @throws IOException
     */
    private static void writeRow(List<Object> row, BufferedWriter csvWriter) throws IOException {
        // 写入文件头部
        for (Object data : row) {
            StringBuffer sb = new StringBuffer();
            String rowStr = sb.append("\"").append(data).append("\",").toString();
            csvWriter.write(rowStr);
        }
        csvWriter.newLine();
    }

    public static void addCloumn(List<List<Object>> pList, String filePath) throws IOException {
        BufferedReader bufReader = new BufferedReader(new FileReader(filePath));
        String lineStr = "";
        int rowNumber = 0;
        StringBuffer nContent = new StringBuffer();
        while ((lineStr = bufReader.readLine()) != null) {
            List<Object> addValue = new ArrayList<>();
            if (rowNumber < pList.size()) {
                addValue = pList.get(rowNumber);
            }
            if (lineStr.endsWith(",")) {
                nContent.append(lineStr).append("\"" + addValue + "\"");
            } else {
                nContent.append(lineStr).append(",\"" + addValue + "\"");
            }
            rowNumber++;
            nContent.append("\r\n");
        }
        bufReader.close();
        FileOutputStream fileOs = new FileOutputStream(new File(filePath), false);
        fileOs.write(nContent.toString().getBytes());
        fileOs.close();
    }

    /**
     * @description 读取csv文件
     * @param path
     * @return List<String>
     * @author qyl
     * @date 2022/8/11 14:58
     */
    public static List<String[]> readCsvFile(String path) {
        File file = new File(path);
        List<String[]> csvList = new ArrayList<>();
        if (!file.exists()) {
            return null;
        }

        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String line = null;
            while ((line = in.readLine()) != null) {
                csvList.add(line.split(","));
                line = null;
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return csvList;
    }
}

