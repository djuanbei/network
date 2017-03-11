package graphcase;

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
		line = pointNum + " "+edgeNum + " " +userNum ;// 只有前两个数字后才加空格
		lines.add(line);
		lines.add("");
		
		System.out.println("firstLine" + line);
	}
	/** 1.2视频内容服务器部署成本 */
	public void secondLine_severCost(int serverCost) {
		String line = "";
		line = line + serverCost;
		lines.add(line);
		lines.add("");
		System.out.println("secondLine" + line);
	}
	/** 1.3链路起始节点ID 链路终止节点ID 总带宽大小   网络租用费  */
	public void thirdBlock_point_rent(int edgeNum) {
		List<String> Block = new LinkedList<String>();
		isRihtEdgeNum(edgeNum);
		GraphNet treeGraph = GraphGenerator.minConGraph(pointNum);
		treeGraph.addSomeEdge(edgeNum-pointNum+1);	
		Block = treeGraph.totxtString();
//		System.out.println("thirdLine" + line);
		lines.addAll(Block);
		lines.add("");
	}
	
	/** 1.4消费节点ID 相连网络节点ID 视频带宽消耗需求 */
	public void fourthBlock_user_need(int userNum) {
		List<String> Block = new LinkedList<String>();
		String line;
		Random random = new Random();
		//1.4、随机用户数、终端点、需求
//		List<Integer> userList = MathUtil.randomMinMaxList(1, userNum, userNum);
		List<Integer> terminalList = MathUtil.randomMinMaxList(0, pointNum, userNum);
		List<Integer> needList = MathUtil.randomMinMaxList(0, needWidth, userNum);

		for (int i = 0; i < userNum; i++) {
			line = i+" "+terminalList.get(i)+" "+needList.get(i);
			//System.out.println("foruthLine" + line);
			lines.add(line);
		}
	}
	/**  二、添加数据：往字条串列表里添加数据  */

	/** 三、保存数据为文件：将已经生成的字条串列表转换成字符串写入指定的文件地址里  */
	public int saveFile(String casefilePath, boolean append) {
		String[] caseContents = lines.toArray(new String[lines.size()]);
		int sate = MathUtil.write(casefilePath, caseContents, false);
		return sate;
	}
	//====================================================================================
	/**1 (1)网络结点的数量区间为[1,1000] (2)-1意思为需要程序随机产生 */
	public void setPointNum(int pointNum, int pointNumSope) {
		if(pointNum == -1 || pointNum > pointNumSope){
			this.pointNum = MathUtil.randomMinMaxInt(oneLow,pointNumSope);
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
		edgeMaxNum = MathUtil.combinateNum(pointNum, 2);
		edgeMinNum = pointNum - 1;
		edgeMustUnicomNum = (pointNum-1)*(pointNum-2)/2+1;
		if(edgeNum == -1 || edgeNum<edgeMinNum || edgeNum > edgeMaxNum ){
			this.edgeNum = MathUtil.randomMinMaxInt(edgeMinNum,edgeMaxNum);
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
			this.userNum = MathUtil.randomMinMaxInt(zeroLow,scope);
		}
		else{
			this.userNum = userNum;
		} 
		System.out.println("小区数量:"+ userNum +";  小区区间:["+ oneLow +","+scope+ "]  －－－－－－－－－－－－GenerateCase.setUserNum" );
	}
	/**4服务器成本*/
	public void setServerCost(int serverCost) {
		if(serverCost == -1 || userNum > serverCostScope){
			this.userNum = MathUtil.randomMinMaxInt(oneLow,serverCostScope);
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
