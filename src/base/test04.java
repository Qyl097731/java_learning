/** 
 * projectName: fushi 
 * fileName: test04.java 
 * packageName: fushi 
 * date: 2022年3月24日下午5:02:44 
 * copyright(c) 2020 南晓18卓工
 */

package fushi;

/**   
 * @title: test04.java 
 * @package fushi 
 * @description: TODO
 * @author: qiuyiliang
 * @date: 2022年3月24日 下午5:02:44 
 * @version: V1.0   
*/

public class test04 {
	public static void main(String[] args) {
		test04 t =new test04();
		test04.Animal cat = t.new Cat();
		cat.run();
	}
	
	
	abstract class Animal{
		abstract void run();
		void eat() {}
	}
	
	
	class Cat extends Animal{

		/**   
		 * @title: run
		 * @description: TODO   
		 * @see fushi.test04.animal#run()     
		 */ 
		
		@Override
		void run() {
			// TODO Auto-generated method stub
			System.out.println("run");
		}
		
	}
}
