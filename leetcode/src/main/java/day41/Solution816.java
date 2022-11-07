package day41;

import org.junit.jupiter.api.Test;

import javax.sound.midi.VoiceStatus;
import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @date:2022/11/7 23:07
 * @author: qyl
 */
public class Solution816 {
    public List<String> ambiguousCoordinates(String s) {
        List<String> res = new ArrayList<>();
        int n = s.length() - 2;
        s = s.substring(1, n + 1);
        for (int i = 1; i < n; i++) {
            List<String> lt = getPos(s.substring(0, i));
            if (lt.isEmpty()) continue;
            List<String> rt = getPos(s.substring(i, n));
            if (rt.isEmpty()) continue;
            for (String l : lt) {
                for (String r : rt) {
                    res.add("(" + l + ", " + r + ")");
                }
            }

        }
        return res;
    }

    private List<String> getPos(String s) {
        List<String> pos = new ArrayList<>();
        if ("0".equals(s)) {
            pos.add(s);
            return pos;
        }
        if (s.charAt(0) != '0') {
            pos.add(s);
        }

        for (int i = 1; i < s.length(); i++) {
            if (i != 1 && s.charAt(0) == '0' || s.charAt(s.length() - 1) == '0') {
                continue;
            }
            pos.add(s.substring(0, i) + "." + s.substring(i));
        }
        return pos;
    }
    @Test
    public void test(){
        System.out.println(ambiguousCoordinates("(00001)"));
    }
}
