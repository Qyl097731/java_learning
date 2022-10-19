package chapter02;

import java.io.*;

/**
 * @description 发送72字符文本行 一次发送一行
 * @date:2022/10/19 21:00
 * @author: qyl
 */
public class Generator {
    public static void generateCharacters(OutputStream out) throws IOException {
        int firstPrintableCharacter = 33;
        int numberOfPrintableCharacters = 94;
        int numberOfCharacterPerLine = 72;
        int start = firstPrintableCharacter;

        // 保存回车和换行
        byte[] line = new byte[numberOfCharacterPerLine + 2];

        while (true) {
            for (int i = start; i < start + numberOfCharacterPerLine; i++) {
                line[i - start] =
                        (byte) ((i - firstPrintableCharacter) % numberOfPrintableCharacters + firstPrintableCharacter);
            }
            line[72] = (byte) '\r';
            line[73] = (byte) '\n';
            out.write(line);
            start = ((start + 1) - firstPrintableCharacter) % numberOfPrintableCharacters + firstPrintableCharacter;
        }
    }
}
