package usecase;


public class StartGenerate {
	public static void main(String[] args) {
		String casefilePath = Configuration.getCaseFilePath();
		int pointNum = Configuration.getPointNum();
		int edgeNum = Configuration.getEdgeNum();
		int userNum = Configuration.getUserNum();
		int serverCost = Configuration.getServerCost();
		
		int bandWidth = Configuration.getBandWidth();
		int rentWeight = Configuration.getRentWeight();
		int needWidth = Configuration.getNeedWidth();
		
		int pointNumScope = Configuration.getPointNumScope();
		int perEdgeNumScope = Configuration.getPerEdgeNumScope();
		int userNumScope = Configuration.getUserNumScope();
		int serverCostScope = Configuration.getServerCostScope();
		
		int bandWidthScope = Configuration.getBandWidthScope();
		int rentWeightSope = Configuration.getRentWeightScope();
		int needWidthScope = Configuration.getNeedWidthScope();
		
		String rentWWeightType = Configuration.getRentWWeightType();
		String bandWidthType = Configuration.getBandWidthType();

//		GenerateCase generateCase = new GenerateCase(filePath, edgeNum, userNum);
		GenerateCase generateCase = new GenerateCase(casefilePath, pointNum, edgeNum, userNum, serverCost, bandWidth, rentWeight, needWidth,pointNumScope, perEdgeNumScope, userNumScope, serverCostScope, bandWidthScope,rentWeightSope,needWidthScope,rentWWeightType,bandWidthType);
		
		// 读取输入文件
//		String[] graphContent = FileUtil.read(casefilePath, null);
//		showCase(graphContent);
		
	}
	
	/**
	 * 把生成的用例显示在命令行上，看格式
	 * @param graphContent
	 */
	public static void showCase(String[] graphContent) {
		System.out.println("====================================");
		for (int i = 0; i < graphContent.length; i++) {
			System.out.println(graphContent[i]);
			System.out.println("====================================");
		}
	}
}
