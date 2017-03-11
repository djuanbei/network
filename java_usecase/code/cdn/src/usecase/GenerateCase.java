package usecase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;



public class GenerateCase {
	List<String> lines = new LinkedList<String>();
	private String casefilePath;
	
	/**系统设定和系统要求 */
	private int pointNumScope=1000;//网络节点数量的范围－默认不超过1000个
	private int perEdgeNumScope=20;//每个节点的链路数量的范围－默认不超过20条
	private int userNumScope=500;//消费节点的数量的范围－默认不超过500个
	private int serverCostScope=5000;//视频内容服务器部署成本为[0,5000]的整数，默认值为5000
	
	private int bandWidthScope=100;//链路总宽带[0,100]的整数，默认值为100
	private int rentWeightScope=100;//链路租用范围[0,100]的整数，默认值为100
	private int needWidthScope;//消费节点的视频带宽消耗需求为[0,5000]的整数，默认值为5000
	private String rentWeightType = "random";//租用费用产生的方式：distance距离值、random随机值
	private String bandWidthType = "random";//租用费用产生的方式：random随机值
	
	/**用户设定和系统要求 */
	private int pointNum=10;//用户设定的网络节点数量
	private int userNum=8;//用户设定的消费节点的数量
	private int serverCost=100;//用户设定的服务器部署成本
	private int edgeNum=20;//用户设定的服务器部署成本
	
	private int bandWidth=50;//链路总宽带[0,100]的整数
	private int rentWeight=30;//链路总宽带[0,100]的整数
	private int needWidth=30;//消费节点的视频带宽消耗需求为[0,100]的整数
	
	
	/**两个常用的下限常量、随机生成标签*/
	 public final int zeroLow = 0;
	 public final int oneLow = 1;
	 public final int randomSign = -1;
	 public boolean rentWWeightDistanceType = false;
	 

	/** 构造函数，将配置文件里的值传入*/
	 public GenerateCase() {
		}
	public GenerateCase(String casefilePath, int edgeNum, int userNum) {
		this.edgeNum = edgeNum;
		this.userNum = userNum;
		this.casefilePath = casefilePath;
		this.generateOne();
	}
		   
	public GenerateCase(String casefilePath, int pointNum, int edgeNum, int userNum, int serverCost, int bandWidth, int rentWeight, int needWidth, int pointNumScope, int perEdgeNumScope, int userNumScope, int serverCostScope, int bandWidthScope, int rentWeightScope, int needWidthScope,String rentWeightType,String bandWidthType) {
		this.casefilePath = casefilePath;
		this.pointNumScope = pointNumScope;
		this.perEdgeNumScope = perEdgeNumScope;
		this.userNumScope = userNumScope;
		this.serverCostScope = serverCostScope;
		
		this.bandWidthScope = bandWidthScope;
		this.rentWeightScope = rentWeightScope;
		this.needWidthScope = needWidthScope;
		
		this.bandWidthType=bandWidthType;
		this.rentWeightType=rentWeightType;
		//根据用例的要求设置属性值
		setPointNum(pointNum,pointNumScope);
		setEdgeNum(edgeNum);
		setUserNum(userNum);
		setServerCost(serverCost);
		setBandWidth(bandWidth);
		setRentWeight(rentWeight);
		setNeedWidth(needWidth);
		
		this.generateOne();
	}
	
	/** 生成一个用例  */
	public int generateOne() {
		//一、生成数据  二、添加数据
		this.firstLine_pointNum();
		this.secondLine_severCost(serverCost);
		this.thirdBlock_point_rent(edgeNum);
		this.fourthBlock_user_need(userNum);
		// 三、保存数据为文件
		this.saveFile(casefilePath, false);
		return 1;
	}
	// 一、生成数据
	/** 1.1生成第一行数据： 网络节点数量 网络链路数量 消费节点数量 */
	public void firstLine_pointNum() {
		String line = "";
		line = pointNum + " "+edgeNum + " " +userNum;// 只有前两个数字后才加空格
		saveData(line);
		System.out.println("firstLine" + line);
	}
	/** 1.2视频内容服务器部署成本 */
	public void secondLine_severCost(int serverCost) {
		String line = "";
		line = line + serverCost;
		saveData(line);
		System.out.println("secondLine" + line);
	}
	/** 1.3链路起始节点ID 链路终止节点ID 总带宽大小   网络租用费 
	 * @throws IOException 
	 * @throws InterruptedException */
	public void thirdBlock_point_rent(int edgeNum) {
		isRihtEdgeNum(edgeNum);
		List<String> Block = new LinkedList<String>();
		String line;
		List<Integer> startPoint = new ArrayList<Integer>();
		List<Integer> endPoint = new ArrayList<Integer>();
		//1.3.1、先生成point个点的连通图
		startPoint = initStartPoint(pointNum);
		endPoint = initConnectGraph(pointNum);
		//1.3.2、找到从0开始的最长的连通线路(a,b)-->(b,c)-->(c,d)-->....-->(z,a)
		//1.3.3、从1.3.2之外的点开始找最长线路
		int isConnect = isConnecteGraph(endPoint);
		while (isConnect==0) {
			System.out.println("－－－－－－不是连通图－重新生成－－－－－－－－－");
			endPoint = initConnectGraph(pointNum);
			isConnect = isConnecteGraph(endPoint);
		}
		//1.3.4、去除补点再补充
		startPoint.remove(pointNum-1);
		endPoint.remove(pointNum-1);
		addNORepeatEdge(startPoint,endPoint);
		//1.3.5、随机带宽
		List<Integer> bandList = CaseUtil.randomMinMaxList(1, bandWidth, edgeNum);
		//1.3.5、随机网络租用
		List<Integer> rentList = CaseUtil.randomMinMaxList(1, rentWeight, edgeNum);
		//1.3.6、连成一串
		for (int i = 0; i < edgeNum; i++) {
			line = startPoint.get(i).toString()+" "+endPoint.get(i)+" "+bandList.get(i)+" "+rentList.get(i);
			//System.out.println("thirdLine" + line);
			Block.add(line);
		}
		saveData(Block);
		saveBlankLine();
	}
	/**1.3.1、先生成point个点的连通图的起点集*/
	public List<Integer> initStartPoint(int pointNum) {
		List<Integer> startPoint = new ArrayList<Integer>();
		for (int i = 0; i <= pointNum-1; i++)
			startPoint.add(i);
		return startPoint;
	}
	/**1.3.1、先生成point个点的连通图终点集，它的index可以当作起点集*/
	public List<Integer> initConnectGraph(int pointNum) {
		List<Integer> startPoint = new ArrayList<Integer>();
		List<Integer> endPoint = new ArrayList<Integer>();
		// 1.3.1、先生成point个点的连通图
		for (int i = 0; i < pointNum-1 ; i++) {
			int start = i;// (1)起点为0、1、2、3...pointNum－1
			int end = start;// (2)终点为随机数：不与起点相同，如相同则继续产生随机数
			boolean oneEdge = false;// (3)是同一条边则继续产生随机数
			while ((end == start) || oneEdge) {
				end = CaseUtil.randomMinMaxInt(0, pointNum);
				// (startPoint,endPoint)同一条边(i,end)＝(end,endPoint.get(end)) : 起点值为i,终点值为end <－－〉第i个起点值为end，终点值为i
				if (startPoint.contains(end)) {
					if (i == endPoint.get(end).intValue()) {
						oneEdge = true;
					} else {
						oneEdge = false;
					}
				} else {
					oneEdge = false;
				}
				// Thread.sleep(1000); System.out.print(" end:"+end+" ");
			}
			startPoint.add(start);
			endPoint.add(end);
			System.out.println("编号" + i + "：    " + i + "－－" + end + "   边<-->点－－点");
		}
		//当生成的边不足结点数时，补齐最后一条边来凑数
		if (endPoint.size() < pointNum) {
			int lastPoint = pointNum-1;
			startPoint.add(lastPoint);
			
			if (endPoint.contains(lastPoint)) {
				int last_end_repeat = endPoint.indexOf(lastPoint);
				endPoint.add(last_end_repeat);
			}else {
				endPoint.add(lastPoint);
			}
			System.out.println("编号" + lastPoint + "：    " + lastPoint + "－－" + endPoint.get(lastPoint) + "   边<-->点－－点");
		}
		System.out.println("起点集 " + startPoint );
		System.out.println("终点集 " + endPoint);
		return endPoint;
	}
	
	/** */
	public int isConnecteGraph(List<Integer> endPoint) {
		int connetGraph = 0;
		// 1.3.2、找到从0开始的最长的连通线路(a,b)-->(b,c)-->(c,d)-->....-->(z,a)
		Set<Integer> connectPointSet = new LinkedHashSet<Integer>();
		int orderPoint = 0;
		connectPointSet.add(orderPoint);
		// (orderPoint,endPoint.get(orderPoint)) -->(orderPoint=endPoint.get(orderPoint),endPoint.get(orderPoint))
		while (!connectPointSet.contains(endPoint.get(orderPoint))) {// (a,b)-->...-->(z,a、b……y)如果连通点集已经有a、b...则形成了一个回路
			connectPointSet.add(endPoint.get(orderPoint));
			orderPoint = endPoint.get(orderPoint);
		}
		System.out.println("  初始连通点:" + connectPointSet);
		// 1.3.3、从1.3.2之外的点开始找最长线路
		for (int i = 0; i < endPoint.size(); i++) {
			if (connectPointSet.contains(i)) {
				continue;
			} else {
				Set<Integer> excludePointSet = new LinkedHashSet<Integer>();
				int excludePoint = i;
				excludePointSet.add(excludePoint);
				while (!excludePointSet.contains(endPoint.get(excludePoint))) {// (a,b)-->...-->(z,a、b……y)如果连通点集已经有a、b...则形成了一个回路
					Integer e = endPoint.get(excludePoint);
					if (connectPointSet.contains(e)) {
						excludePointSet.add(e);
						connectPointSet.addAll(excludePointSet);
//						System.out.println("  打到新的连通点集:" + excludePointSet);
//						System.out.println("  添加后:" + connectPointSet);
						break;
					} else {
						excludePointSet.add(e);
						excludePoint = e;
					}
				}
				//System.out.println(" 连通子集:" + excludePointSet);
			}
		}
		if (connectPointSet.size()==pointNum) {
			System.out.println(" 是连通无向图" + connectPointSet);
			connetGraph=1;
		}
		return connetGraph;
	}
	//1.3.4、去除补点再补充
	public int addNORepeatEdge(List<Integer> startPoint,List<Integer> endPoint) {
		List<Integer> pointDegree = new ArrayList<Integer>();
		//(1.1)起点全部只出现一次，每个点的出度为1.	(1.2)计算尾结点的入度。无重复边总度＝出度+入度
		//pointDegree = CaseUtil.oldDegree(endPoint);
		pointDegree = CaseUtil.degree(startPoint, endPoint, pointNum);
		System.out.println("度： "+pointDegree);  
		//(2.2)每一个点随机加入inNum=random个边，范围[0,n],n=min(20-度，remainEdgeNum)
		int insertPostion=0;
		int remainEdgeNum =edgeNum - endPoint.size();//还需要加多少条边
		for (int j = 0; j < pointNum; j++) {
			if(remainEdgeNum == 0){
				break;
			}
			int pointDegreeNum = Math.min(pointNum, 20) - pointDegree.get(j);//一个点的度最多为pointNum,题目范围为20
			int pointReaminEdgeNum = CaseUtil.randomMinMaxInt(0, pointDegreeNum);
			int inserEdgeNum = Math.min(pointReaminEdgeNum, remainEdgeNum);//插入的边数由随机数，和总边数剩余边数决定
			// (2.3)每一个j点插入K条边
			for (int k = 1; k <=inserEdgeNum ; k++) {
				int start = j;// (1)起点为j
				int end = start;// (2)终点为随机数：不与起点相同，如相同则继续产生随机数
				boolean sameEdge = false;// (3)是同一条边则继续产生随机数
				boolean oneEdge = false;
				while ((end == start) || oneEdge ||sameEdge) {
					end = CaseUtil.randomMinMaxInt(0, pointNum);
					//System.out.println("随机生成(start,end):   (" + start + ", " + end + ")" );
					//(2.4.2)(startPoint,endPoint)同一条边情况2: (start1,end1)＝(start0,end0)
					oneEdge = CaseUtil.isOneEdge(start, end, startPoint, endPoint);
					List<Integer> allIndexOf = CaseUtil.allIndexOf(startPoint, start);
					insertPostion = allIndexOf.get(allIndexOf.size()-1);//插入位置为与start相同的最后一个index
					// (2.4.1)(startPoint,endPoint)同一条边情况1：(start1,end1)＝(start0==end1,end0==start1) : 起点值为start,终点值为end <－－〉起点值为end，终点值为start
					sameEdge = CaseUtil.isSameEdge(start, end, startPoint, endPoint);
				}
				startPoint.add(insertPostion,start);
				endPoint.add(insertPostion,end);
				insertPostion = insertPostion + 1 ;//插入点的位置
				//System.out.println(j+"点插入边号 " + k + "：    " + j + "－－" + end + "   边<-->点－－点");
			}
			remainEdgeNum =edgeNum - endPoint.size();
			pointDegree = CaseUtil.degree(startPoint, endPoint, pointNum);//更新下一点的度用来 计算该点还能添加多少条边
		}
		System.out.println("起点集 " +startPoint );
		System.out.println("终点集 " + endPoint);
		return 1;
	}

	/** 1.4消费节点ID 相连网络节点ID 视频带宽消耗需求 */
	public void fourthBlock_user_need(int userNum) {
		List<String> Block = new LinkedList<String>();
		String line;
		Random random = new Random();
		//1.4、随机用户数、终端点、需求
		List<Integer> userList = CaseUtil.randomMinMaxList(1, userNum, userNum);
		List<Integer> terminalList = CaseUtil.randomMinMaxList(0, pointNum, userNum);
		List<Integer> needList = CaseUtil.randomMinMaxList(0, needWidth, userNum);

		for (int i = 0; i < userNum; i++) {
			line = userList.get(i).toString()+" "+terminalList.get(i)+" "+needList.get(i);
			//System.out.println("foruthLine" + line);
			Block.add(line);
		}
		saveData(Block);
	}
	/**  二、添加数据：往字条串列表里添加数据  */
	public int saveData(String str) {
		lines.add(str);
		saveBlankLine();
		return 1;
	}
	public int saveData(List<String> str) {
		lines.addAll(str);
		return 1;
	}
	public int saveBlankLine() {
		lines.add("");
		return 1;
	}
	/** 三、保存数据为文件：将已经生成的字条串列表转换成字符串写入指定的文件地址里  */
	public int saveFile(String casefilePath, boolean append) {
		String[] caseContents = lines.toArray(new String[lines.size()]);
		int sate = FileUtil.write(casefilePath, caseContents, false);
		return sate;
	}
	//====================================================================================
	/**1 (1)网络结点的数量区间为[1,1000] (2)-1意思为需要程序随机产生 */
	public void setPointNum(int pointNum, int pointNumSope) {
		if(pointNum == -1 || pointNum > pointNumSope){
			this.pointNum = CaseUtil.randomMinMaxInt(oneLow,pointNumSope);
		}
		else{
			this.pointNum = pointNum;
		} 
		System.out.println("结点数量:"+ pointNum +";  结点区间:["+ oneLow +","+pointNumSope+ "]  －－－－－－－－－－－－GenerateCase.setPointNum" );
	}
	/**2  (1.1)网络链路的数量区间为[pointNum－1, (n-1)*(n-2)/2]  (1.2)一定连通的区间 [(n-1)*(n-2)/2+1,n(n-1)/2]  (2)-1意思为需要程序随机产生*/  
	int edgeMaxNum;
	int edgeMinNum=0;
	int edgeMustUnicomNum;
	public void setEdgeNum(int edgeNum) {
		edgeMaxNum = CaseUtil.combinateNum(pointNum, 2);
		edgeMinNum = pointNum - 1;
		edgeMustUnicomNum = (pointNum-1)*(pointNum-2)/2+1;
		if(edgeNum == -1 || edgeNum<edgeMinNum || edgeNum > edgeMaxNum ){
			this.edgeNum = CaseUtil.randomMinMaxInt(edgeMinNum,edgeMaxNum);
		}
		else{
			this.edgeNum = edgeNum;
		} 
		System.out.println("链路数量:"+ edgeNum +";  链路区间:[最小"+ edgeMinNum +",必联通"+edgeMustUnicomNum +",最大"+edgeMaxNum+ "]  －－－－－－－－－－－－GenerateCase.setEdgeNum" );
	}
	public void isRihtEdgeNum(int edgeNum) {
		if (edgeNum<edgeMinNum||edgeNum>edgeMaxNum) {
			System.out.println("请输入正确的边区间,确保连通。区间为： ["+edgeMinNum+", "+edgeMaxNum+"]");
		}		
	}
	/**3 用户的数量: (1)设置用户数量小于网络结点。(2)要在指定的区间[0,500] (3)-1意思为需要程序随机产生   */
	public void setUserNum(int userNum) {
		int scope = Math.min(pointNum, userNumScope);//main(条件1，条件2)
		if(userNum == -1 || userNum > scope){
			this.userNum = CaseUtil.randomMinMaxInt(zeroLow,scope);
		}
		else{
			this.userNum = userNum;
		} 
		System.out.println("小区数量:"+ userNum +";  小区区间:["+ oneLow +","+scope+ "]  －－－－－－－－－－－－GenerateCase.setUserNum" );
	}
	/**4服务器成本*/
	public void setServerCost(int serverCost) {
		if(serverCost == -1 || userNum > serverCostScope){
			this.userNum = CaseUtil.randomMinMaxInt(oneLow,serverCostScope);
		}
		else{
			this.serverCost = serverCost;
		} 
	}
	/**5租用费用生成类型*/
	//rentWWeightType = "distance";//租用费用产生的方式：distance距离值、random随机值
	public void setRentWeightDistanceType(String rentWeightType) {
		if(rentWeightType.equals("distance")){
			this.rentWWeightDistanceType = true;
		}
		else if(rentWeightType.equals("random")){
			this.rentWWeightDistanceType = false;
		} 
	}
	public void setBandWidth(int bandWidth) {
		this.bandWidth = bandWidth;
	}
	public void setRentWeight(int rentWeight) {
		this.rentWeight = rentWeight;
	}
	public void setNeedWidth(int needWidth) {
		this.needWidth = needWidth;
	}
	
	//====================================================================================
	
	
	
	
	
	
	
}
