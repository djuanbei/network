package usecase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class CaseUtil {

	/** 生成生成[0,max)或者[min,max]范围内的正整数。 nextInt(max)生成[0,max)之间的数 */
	public static int randomMinMaxInt(int min, int max) {
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		return s;
	}
	/** 生成生成List随机数*/
	public static List<Integer> randomMinMaxList(int min, int max,int n) {
		List<Integer> randomList = new ArrayList<Integer>();
		for (int i = 0; i < n; i++) {
			int random = randomMinMaxInt(min, max);
			randomList.add(random);
		}
		return randomList;
	}

	/** 求一个数的组合：Cnk */
	public static int combinateNum(int n, int k) {
		int molecular_take = 1;// 分子乘积
		int denominator_take = 1;// 分母乘积
		for (int i = 0; i < k; i++) {
			molecular_take = molecular_take * (n - i);
			denominator_take = denominator_take * (k - i);

		}
		int result = molecular_take / denominator_take;
		return result;
	}

	/** 取出一个List里某一个值的所有下标 */
	public static List<Integer> allIndexOf(List<Integer> list, Integer n) {
		List<Integer> allIndex = new ArrayList<Integer>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals(n)) {
				allIndex.add(i);
			}
		}
		return allIndex;
	}

	/** 计算一个List里所有点的度 */
	public static List<Integer> degree(List<Integer> pointList, int pointNum) {
		List<Integer> pointDegree = new ArrayList<Integer>();
		// (1.1)初始化点集的度为0
		for (int i = 0; i < pointNum; i++) {
			pointDegree.add(0);
		}
		// (1.2)计算结点的度
		Set<Integer> uniqueSet = new HashSet<Integer>(pointList);
		for (Integer temp : uniqueSet) {
			int degree = Collections.frequency(pointList, temp);
			pointDegree.set(temp, degree);
		}
		return pointDegree;
	}

	/** 计算一个List里所有点的度 */
	public static List<Integer> degree(List<Integer> pointList1, List<Integer> pointList2, int pointNum) {
		List<Integer> pointDegree1 = new ArrayList<Integer>();
		List<Integer> pointDegree2 = new ArrayList<Integer>();
		List<Integer> pointDegree = new ArrayList<Integer>();
		// (1.1)初始化点集的度为0
		for (int i = 0; i < pointNum; i++) {
			pointDegree.add(0);
		}
		pointDegree1 = degree(pointList1, pointNum);
		pointDegree2 = degree(pointList2, pointNum);
		for (int i = 0; i < pointNum; i++) {
			int add = pointDegree1.get(i) + pointDegree2.get(i);
			pointDegree.set(i, add);
		}
		return pointDegree;

	}

	/** 计算一个List里所有点的度 */
	public static List<Integer> oldDegree(List<Integer> endPoint) {
		List<Integer> pointDegree = new ArrayList<Integer>();
		// (1.1)起点全部只出现一次，每个点的出度为1
		for (int i = 0; i < endPoint.size(); i++) {
			pointDegree.add(1);// 初始化除最后一个点所有点的度为1
		}
		pointDegree.add(0);// 初始化最后一个点的度为0
		System.out.println("起点度" + pointDegree);
		// (1.2)计算尾结点的入度。无重复边总度＝出度+入度
		Set<Integer> uniqueSet = new HashSet<Integer>(endPoint);
		for (Integer temp : uniqueSet) {
			int endPointDegree = Collections.frequency(endPoint, temp);
			System.out.println("尾点 " + temp + "度: " + Collections.frequency(endPoint, temp));
			int startPointDegree = pointDegree.get(temp);
			pointDegree.set(temp, endPointDegree + startPointDegree);
		}
		System.out.println("度： " + pointDegree);
		return pointDegree;
	}
	/** 看是不是起点终点相同的一条边 (s,e)=(sm,em)中  s==sm,em==e*/
	public static boolean isOneEdge(int start,int end,List<Integer> startPoint,List<Integer> endPoint) {
		boolean oneEdge = false;
		if(startPoint.contains(start)) {
			List<Integer> allIndexOf = CaseUtil.allIndexOf(startPoint, start);//1找到与start1相等的start0
			int i;
			for (i = 0; i < allIndexOf.size(); i++) {
				int index = allIndexOf.get(i);
				if (endPoint.get(index).intValue() == end ) {//2对比end0看与end1是否相等：end0＝endPoint.get(index)
					oneEdge = true;
					//System.out.println("yes查到与第 "+index+" 条边是一条边： "+"(" + startPoint.get(index) + ", " + endPoint.get(index) + ")");
				} 
			}
			
		}
		return oneEdge;
	}
	/** 看是不是起点终点相反的一条边(s,e)=(sm,em)中中  e==sm,em==s */
	public static boolean isSameEdge(int start,int end,List<Integer> startPoint,List<Integer> endPoint) {
		boolean sameEdge = false;
		if (startPoint.contains(end)) {
			List<Integer> allIndexOf = CaseUtil.allIndexOf(startPoint, end);//1找到与end1相等的start0
			for (int i = 0; i < allIndexOf.size(); i++) {
				int index = allIndexOf.get(i);
				if (start == endPoint.get(index).intValue()) {//2对比end0看与start1是否相等：end0＝endPoint.get(index)
					sameEdge = true;
					//System.out.println("same查到与第 "+index+" 条边共边： "+"(" + startPoint.get(index) + ", " + endPoint.get(index) + ")");
				}
			}
		}
		return sameEdge;
	}
	
}
