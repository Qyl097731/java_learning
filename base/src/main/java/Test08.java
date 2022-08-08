/**
 * projectName: fushi
 * fileName: Test08.java
 * packageName: fushi
 * date: 2022年3月24日下午9:21:34
 * copyright(c) 2020 南晓18卓工
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @title: Test08.java
 * @package fushi
 * @description: TODO
 * @author: qiuyiliang
 * @date: 2022年3月24日 下午9:21:34
 * @version: V1.0
 */

public class Test08 {
	public static void main(String[] args) {
		Pattern pattern1, pattern2, pattern3, pattern4;
		Matcher matcher1, matcher2, matcher3, matcher4;

		pattern1 = Pattern.compile("^Java.*");
		matcher1 = pattern1.matcher("Java是一种程序语言");
		System.out.println(matcher1.matches());

		pattern2 = Pattern.compile("[| ,]");
		String[] strings = pattern2.split("Java Hello World  Java,Hello,,World|Sun");
		for (String string : strings) {
			System.out.println(string);
		}
		System.out.println(strings.length);

		pattern3 = Pattern.compile("正则");
		matcher3 = pattern3.matcher("正则 Hello Wrold,正则 Hello World");
		System.out.println(matcher3.replaceAll("java"));

		pattern4 = Pattern.compile("表达式");
		matcher4 = pattern4.matcher("表达式Hello World，表达式Hello World");
		StringBuffer sbr = new StringBuffer();
		while(matcher4.find()) {
			matcher4.appendReplacement(sbr, "Java");

		}
		matcher4.appendTail(sbr);
		System.out.println(sbr.toString());

	}
}
