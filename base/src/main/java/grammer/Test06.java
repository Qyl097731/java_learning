package grammer; /**
 * projectName: fushi
 * fileName: grammer.Test06.java
 * packageName: fushi
 * date: 2022年3月24日下午5:36:50
 * copyright(c) 2020 南晓18卓工
 */

/**
 * @title: grammer.Test06.java
 * @package fushi
 * @description: TODO
 * @author: qiuyiliang
 * @date: 2022年3月24日 下午5:36:50
 * @version: V1.0
*/

public class Test06 {
	public static void main(String[] args) {
		Number number = new Number();
		Say say = new Say(number);
		System.out.println(number.getValue());
	}

}

class Number{
	 int value = 1;

	/**
	 * @title: getValue
	 * @description: TODO
	 * @return: int
	 */

	public int getValue() {
		return value;
	}

	/**
	 * @title: setValue
	 * @description: TODO
	 * @return: int
	 */

	public void setValue(int value) {
		this.value = value;
	}
}

class Say{
	/**
	 * @title: grammer.Test06.grammer.Say
	 * @description: TODO(这里用一句话描述这个方法的作用)
	 * @param:
	 * @throws:
	 */

	public Say(Number number) {
		// TODO Auto-generated constructor stub
		number.setValue(number.getValue() + 1);
	}
}
