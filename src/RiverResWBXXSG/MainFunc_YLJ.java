package RiverResWBXXSG;

import BeanSets.BasicResBean;
import BeanSets.CalculateBean;
import BeanSets.getResINFO;
import OptSims.POAOptimal;

public class MainFunc_YLJ {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/**
		 * 读取数据模块
		 * /Users/lovemevol/Documents/IdeaProjects/YangtzeRiverRes/ResStations/ResColLev.xls
		 */
		String OutputName = "GK3-7000.xls";// ------------------------------------------------------定义输出名
		int time = 301;// -----------------------------------------------------------------------优化次数
		// -----水库群计算顺序获取---------------
		getResINFO dddd = new getResINFO();
		String filepath1 = "RiverData/JSJ/YLJ/ResStr.xls";
		System.out.println("1");
		CalculateBean calbean = new CalculateBean();
		dddd.getResExcelInfo(filepath1, calbean);
		String nodenames[] = calbean.getName();
		// String nodeKinds[]=calbean.getNodeKinds();
		// -----水库群水位控制曲线----------
		String filepath2 = "RiverData/JSJ/YLJ/ResColLev.xls";
		dddd.getResControlLevs(filepath2, calbean);
		System.out.println("2");
		// -----水库群泄流控制过程----------
		String filepath21 = "RiverData/JSJ/YLJ/ResColflow.xls";
		System.out.println("3");
		dddd.getResControlQouts(filepath21, calbean);
		// ---------------------------
		// -----水库群基础信息获取----------
		String filepath3 = "RiverData/JSJ/YLJ/";
		System.out.println("4");
		int nodeNum = nodenames.length;
		BasicResBean basicbeans[] = new BasicResBean[nodeNum];
		for (int i = 0; i < nodeNum; i++) {
			String subfilepath = filepath3 + nodenames[i] + ".xls";
			BasicResBean subbasicbean = new BasicResBean();
			dddd.getBasicResInfo(subfilepath, subbasicbean, nodenames[i]);// filepath3,subfilepath
			basicbeans[i] = subbasicbean;
		}
		// System.out.println(basicbean.getGuaranteedCapacity());
		/**
		 * 开始计算
		 */
		POAOptimal POAopt = new POAOptimal();
		POAopt.optimizeDPSA(calbean, basicbeans, OutputName, time);

	}

}
