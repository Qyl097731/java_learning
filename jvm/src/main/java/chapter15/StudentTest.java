package chapter15;

import com.sun.webkit.WebPage;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @date:2022/8/18 23:04
 * @author: qyl
 */
public class StudentTest {
    static List<WebPage> webpages = new ArrayList<WebPage>();

    public static void createWebPages(){
        for (int i = 0 ; i < 100 ; i++){
            WebPage wp = new WebPage();
            wp.setUrl("http:///www."+Integer.toHexString(i) + ".com");
            wp.setContent(Integer.toHexString(i));
            webpages.add(wp);
        }
    }

    public static void main(String[] args) {
        createWebPages();
        Student st3 =  new Student(3,"Tom");
        Student st5 = new Student(5,"Jerry");
        Student st7 = new Student(7,"Lilt");

        for (int i = 0; i < webpages.size(); i++) {
            if (i % st3.getId() == 0){
                st3.visit(webpages.get(i));
            }else if (i % st5.getId() == 0){
                st5.visit(webpages.get(i));
            }else if (i % st7.getId() == 0) {
                st7.visit(webpages.get(i));
            }
        }
        webpages.clear();;
        System.gc();
    }
}



@Data
class Student{
    private int id;
    private String name;
    private List<WebPage> history = new ArrayList<>();

    public Student(int id,String name){
        super();
        this.id = id;
        this.name = name;
    }

    public void visit(WebPage webPage) {
        history.add(webPage);
    }
}
