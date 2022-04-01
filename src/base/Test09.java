/** 
 * projectName: fushi 
 * fileName: Test09.java 
 * packageName: fushi 
 * date: 2022年3月24日下午9:53:31 
 * copyright(c) 2020 南晓18卓工
 */

package fushi;

/**   
 * @title: Test09.java 
 * @package fushi 
 * @description: TODO
 * @author: qiuyiliang
 * @date: 2022年3月24日 下午9:53:31 
 * @version: V1.0   
*/

public class Test09 {

	/**
	 *@title: main 
	 *@description: TODO
	 *@author: qiuyiliang
	 *@date: 2022年3月24日 下午9:53:31
	 *@param args
	 *@throws: 
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String a[] = {"boy","girl"};
		for (int i = 0; i < a.length-1; i++) {
			for(int j = i+1;j<a.length;j++) {
				if(a[i].compareTo(a[j]) < 0) {
					String temp = a[i];
					a[i] = a[j];
					a[j] = temp;
				}
			}
		}
		for (String string : a) {
			System.out.println(string);
		}
	}

}
