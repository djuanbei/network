package usecase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;

public class Test {
	// Thread.sleep(1000); System.out.print(" end:"+end+" ");
	public static void main(String[] args)  {
//		System.out.println(CaseUtil.combinateNum(6, 2));
		GenerateCase generateCase = new GenerateCase();
//		//1测试不联通
//		List<Integer> startPoint = new ArrayList<Integer>();
//		List<Integer> endPoint = new ArrayList<Integer>();
//		endPoint.add(1);endPoint.add(2);endPoint.add(0);
//		endPoint.add(4);endPoint.add(5);endPoint.add(3);
//		startPoint = generateCase.initStartPoint(6);
//		int i = generateCase.isConnecteGraph(endPoint);
//		System.out.println("是否连通" + i );
		
		//2测试产生的数据
		generateCase.thirdBlock_point_rent(20);
//		for (int i = 0; i < 10; i++) {
//			generateCase.thirdBlock_point_rent(50);
//			
//		}
		
		//3测试下标集
//		testallIndexOf();
		

	}
	
	public static void testallIndexOf() {
		List<Integer> point = new ArrayList<Integer>();
		List<Integer> index = new ArrayList<Integer>();
		point.add(1);point.add(2);point.add(2);
		point.add(3);point.add(3);point.add(3);
		point.add(4);point.add(5);point.add(6);
		int a =2;
		Integer b = new Integer(3);
		System.out.println("list集" + point );
		index = CaseUtil.allIndexOf(point, a);
		System.out.println("值2下标集" + index );
		index = CaseUtil.allIndexOf(point, b);
		System.out.println("值3下标集" + index );
		
	}
		

}
