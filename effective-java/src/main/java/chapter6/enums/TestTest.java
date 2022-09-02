package chapter6.enums;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
/**
 * @author qyl
 * @program TestTest.java
 * @Description EnumSet代替位域
 * @createTime 2022-09-02 11:11
 */
public class TestTest {
    public static void main(String[] args) {
        Text text = new Text();
        text.applyStyles(EnumSet.of(Text.Style.BLOG, Text.Style.ITALIC));
        Set<Text.Style> styles = text.styles();
        System.out.println(styles.toString());
    }
}

class Text {
    public enum Style {BLOG, ITALIC, UNDERLINE}

    private Set<Style> styles;

    public void applyStyles(Set<Style> styles) {
        this.styles = styles;
    }
    public Set<Style> styles(){
        return new HashSet<>(styles);
    }
}
