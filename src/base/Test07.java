/** 
 * projectName: fushi 
 * fileName: Test07.java 
 * packageName: fushi 
 * date: 2022年3月24日下午6:18:21 
 * copyright(c) 2020 南晓18卓工
 */

package fushi;

/**   
 * @title: Test07.java 
 * @package fushi 
 * @description: TODO
 * @author: qiuyiliang
 * @date: 2022年3月24日 下午6:18:21 
 * @version: V1.0   
*/

public class Test07 {
	public static void main(String[] args) {
		C c = new C();
	}
}
class A{
	/**   
	 * @title: A   
	 * @description: TODO(这里用一句话描述这个方法的作用)   
	 * @param:   
	 * @throws:   
	 */
	int x = 1;
	public A() {
		System.out.println(x);
		// TODO Auto-generated constructor stub
		x = 2;
		System.out.println(x);
	}
}

class B extends A{
	/**   
	 * @title: B   
	 * @description: TODO(这里用一句话描述这个方法的作用)   
	 * @param:   
	 * @throws:   
	 */

	public B() {
		// TODO Auto-generated constructor stub
		x = 3;
		System.out.println(x);
	}
}

class C extends B{
	/**   
	 * @title: C   
	 * @description: TODO(这里用一句话描述这个方法的作用)   
	 * @param:   
	 * @throws:   
	 */

	public C() {
		// TODO Auto-generated constructor stub
		x = 4;
		System.out.println(x);
	}
}