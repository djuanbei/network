package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
	/**系统设定和系统要求 */
	private static String configFilePath="configuration.properties";//配置文件路径：放在src下，但是要从生成的bin下找
	private static String caseFilePath;//保存用列的文件路径
	private static int pointNumScope;//网络节点数量的范围－默认不超过1000个
	private static int perEdgeNumScope;//每个节点的链路数量的范围－默认不超过20条
	private static int userNumScope;//消费节点的数量的范围－默认不超过500个
	private static int serverCostScope;//视频内容服务器部署成本为[0,5000]的整数，默认值为5000
	private static int bandWidthScope;//链路总带宽－默认不超过100个
	private static int rentWeightScope;//网络租用费－默认不超过100个
	private static int needWidthScope;//消费节点的视频带宽消耗需求为[0,5000]的整数，默认值为5000
	
	/**用户设定和系统要求 */
	private static int pointNum;//用户设定的网络节点数量
	private static int edgeNum;//用户设定的服务器部署成本
	private static int userNum;//用户设定的消费节点的数量
	private static int serverCost;//用户设定的服务器部署成本
	
	/** */
	private static int bandWidth;//总带宽
	private static int rentWeight;//单位租用费
	private static int needWidth;//消费节点的视频带宽消耗需求
	
	private static String rentWWeightType;
	private static String bandWidthType;
	
	
	

	static {
		// 加载configuration.properties配置文件
		Properties props = new Properties();
		FileInputStream in = null;
		try {
//			in = new FileInputStream("bin/usecase/configuration.properties");
			in = new FileInputStream(configFilePath);
			props.load(in); // 加载
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}

		// 初始化所有配置项
		
		configFilePath = props.getProperty("configFilePath");
		caseFilePath = props.getProperty("casefilePath");
		pointNumScope = Integer.parseInt(props.getProperty("pointNumScope"));
		perEdgeNumScope = Integer.parseInt(props.getProperty("perEdgeNumScope"));
		userNumScope = Integer.parseInt(props.getProperty("userNumScope"));
		serverCostScope = Integer.parseInt(props.getProperty("serverCostScope"));
		bandWidthScope = Integer.parseInt(props.getProperty("bandWidthScope"));
		rentWeightScope = Integer.parseInt(props.getProperty("rentWeightScope"));
		needWidthScope = Integer.parseInt(props.getProperty("needWidthScope"));
		
		pointNum = Integer.parseInt(props.getProperty("pointNum"));
		edgeNum = Integer.parseInt(props.getProperty("edgeNum"));
		userNum = Integer.parseInt(props.getProperty("userNum"));
		serverCost = Integer.parseInt(props.getProperty("serverCost"));
		bandWidth = Integer.parseInt(props.getProperty("bandWidth"));
		rentWeight = Integer.parseInt(props.getProperty("rentWeight"));
		needWidth = Integer.parseInt(props.getProperty("needWidth"));
		
		rentWWeightType = props.getProperty("rentWWeightType");
		bandWidthType = props.getProperty("bandWidthType");
		System.out.println("===  Configuration已经加载configuration.properties配置文件并初始化所有配置 ===");
		System.out.println("configFilePath: " + configFilePath);
		System.out.println("caseFilePath: " + caseFilePath);
		System.out.println("===================================================");
		System.out.println("pointNumScope: " + pointNumScope);
		System.out.println("perEdgeNumScope: " + perEdgeNumScope);
		System.out.println("userNumScope: " + userNumScope );
		System.out.println("serverCostScope: " + serverCostScope);
		System.out.println("bandWidthScope: " + bandWidthScope);
		System.out.println("rentWeightScope: " + rentWeightScope);
		System.out.println("needWidthScope: " + needWidthScope );
		System.out.println("===================================================");
		System.out.println("pointNum: " + pointNum);
		System.out.println("edgeNum: " + edgeNum);
		System.out.println("userNum: " + userNum);
		System.out.println("serverCost: " + serverCost);
		System.out.println("bandWidth: " + bandWidth);
		System.out.println("rentWeight: " + rentWeight);
		System.out.println("needWidth: " + needWidth);
		System.out.println("===================================================");
		System.out.println("rentWWeightType: " + rentWWeightType);
		System.out.println("bandWidthType: " + bandWidthType);
	}


	public static String getConfigFilePath() {
		return configFilePath;
	}




	public static String getCaseFilePath() {
		return caseFilePath;
	}




	public static int getPointNumScope() {
		return pointNumScope;
	}




	public static int getPerEdgeNumScope() {
		return perEdgeNumScope;
	}




	public static int getUserNumScope() {
		return userNumScope;
	}




	public static int getServerCostScope() {
		return serverCostScope;
	}




	public static int getBandWidthScope() {
		return bandWidthScope;
	}




	public static int getRentWeightScope() {
		return rentWeightScope;
	}




	public static int getNeedWidthScope() {
		return needWidthScope;
	}




	public static int getPointNum() {
		return pointNum;
	}




	public static int getEdgeNum() {
		return edgeNum;
	}




	public static int getUserNum() {
		return userNum;
	}




	public static int getServerCost() {
		return serverCost;
	}




	public static int getBandWidth() {
		return bandWidth;
	}




	public static int getRentWeight() {
		return rentWeight;
	}




	public static int getNeedWidth() {
		return needWidth;
	}




	public static String getRentWWeightType() {
		return rentWWeightType;
	}




	public static String getBandWidthType() {
		return bandWidthType;
	}




	
	


}
