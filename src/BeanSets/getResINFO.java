package BeanSets;

import DateManage.Import_xls_Date;

public class getResINFO {
	Import_xls_Date excel = new Import_xls_Date();

	public void getResExcelInfo(String filepath, CalculateBean calbean) {/// *~~~~~~~~~~~~~~~~~~~~~CalculateBean calbean
																			/// 是什么构造方法,用来储存内容的容器吗 String
																			/// nodenames[]=calbean.getName();*//
		// String
		// filepath_basicExcel="E:/Eclipsework/YangtzeRiverRes/ResStations/wu/ResStWU.xls";
		int order[] = excel.getIntColumns("Order", filepath, 0);
		String name[] = excel.getStringColumns("Names", filepath, 0);
		String NodeKinds[] = excel.getStringColumns("NodeKinds", filepath, 0);
		String OptKind[] = excel.getStringColumns("OptKind", filepath, 0);
		String inflow_index[] = excel.getStringColumns("inflow_index", filepath, 0);
		String upstream_index[] = excel.getStringColumns("upstream_index", filepath, 0);
		calbean.setOrder(order);
		calbean.setName(name);
		calbean.setNodeKinds(NodeKinds);
		calbean.setOptKind(OptKind);
		calbean.setInflow_index(inflow_index);
		calbean.setUpstream_index(upstream_index);
		/*
		 * for(int i=0;i<name.length;i++){
		 * System.out.println(name[i]);
		 * }
		 */
	}

	public void getBasicResInfo(String filepath, BasicResBean basicbean, String resName) {
		double infos[] = excel.getDoubleColumns("Value", filepath, 0);
		/*
		 * for(int i=0;i<infos.length;i++){
		 * System.out.println(infos[i]);
		 * }
		 */
		basicbean.setResNameCN(resName); // 水库名称
		basicbean.setDeadLevel(infos[0]); // 死水位m
		basicbean.setNormalLevel(infos[1]); // 正常蓄水位m
		basicbean.setFloodLimtLevel(infos[2]); // 汛限水位m
		basicbean.setFloodHighLevel(infos[3]); // 防洪高水位m
		basicbean.setDeadvolumn(infos[4]); // 死库容m3
		basicbean.setNormalvolumn(infos[5]); // 正常库容m3
		basicbean.setDeltaT(infos[6]); // 调度时段间隔(hour)
		basicbean.setInstalledCapacity(infos[7]);// 装机容量
		basicbean.setGuaranteedCapacity(infos[8]);// 保证出力
		basicbean.setEfficiencyCOE(infos[9]); // 发电效率系数
		basicbean.setTail_level(infos[10]); // 坝下水位
		basicbean.setHypower_max_outflow(infos[11]);// 最大发电流量
		basicbean.setEco_flow(infos[12]); // 生态基流m3/s
		basicbean.setCity_supply_flow(infos[13]);// 生产生活供水流量m3/s
		basicbean.setAgr_supply_flow(infos[14]); // 农业用水流量m3/s
		basicbean.setNav_supply_flow(infos[15]); // 通航需求流量m3/s
		basicbean.setOther_mini_flow(infos[16]); // 其他最小下泄流量m3/s
		basicbean.setVunit(infos[17]); // 库容单位 万方
		basicbean.setHypower_guarant(infos[18]); // 发电保证率
		basicbean.setEco_guarant(infos[19]); // 生态保证率
		basicbean.setCity_guarant(infos[20]); // 城镇供水保证率
		basicbean.setAgr_guarant(infos[21]); // 农业供水保证率
		basicbean.setNav_guarant(infos[22]); // 航运保证率
		basicbean.setOther_guarant(infos[23]); // 其他目标保证率
		// -----------------
		// basicbean.setCurrentwaterlevel(infos[1]);
		// basicbean.setCurrentwatervolumn(infos[5]);
		// -----------------
		double Z_V[][] = excel.getRowColumnsDouble(filepath, 1);
		basicbean.setZ_V(Z_V);
		double inflowss[] = excel.getDoubleColumns("inflow", filepath, 2);
		/*
		 * for (int i=0;i<inflowss.length;i++){
		 * System.out.println(inflowss[i]);
		 * }
		 */
		basicbean.setInflow_pro(inflowss);
		if (infos[9] == -1024) {
			double haoshuili_level[][] = excel.getRowColumnsDouble(filepath, 3);
			basicbean.setHaoshuili_level(haoshuili_level);
		}
		if (infos[10] == -1024) {
			double tail_flow_level[][] = excel.getRowColumnsDouble(filepath, 4);
			basicbean.setTail_flow_level(tail_flow_level);
		}
		if (infos[12] == -1024) {
			double ecoinflowss[] = excel.getDoubleColumns("ecoinflowss", filepath, 5);
			basicbean.setEco_flowpro(ecoinflowss);
		}
		if (infos[13] == -1024) {
			double citySupplyFlowpro[] = excel.getDoubleColumns("citySupplyFlowpro", filepath, 6);
			basicbean.setCity_supply_flowpro(citySupplyFlowpro);
		}
		if (infos[14] == -1024) {
			double agrSupplyFlowpro[] = excel.getDoubleColumns("agrSupplyFlowpro", filepath, 7);
			basicbean.setAgr_supply_flowpro(agrSupplyFlowpro);
		}
		if (infos[15] == -1024) {
			double navSupplyFlowpro[] = excel.getDoubleColumns("navSupplyFlowpro", filepath, 8);
			basicbean.setNav_supply_flowpro(navSupplyFlowpro);
		}
		if (infos[16] == -1024) {
			double otherMiniFlowpro[] = excel.getDoubleColumns("otherMiniFlowpro", filepath, 9);
			basicbean.setOther_mini_flowpro(otherMiniFlowpro);
		}
	}

	public void getResControlLevs(String filepath, CalculateBean calbean) {
		double deadlevels[][] = excel.getRowColumnsDouble(filepath, 0);
		double optmlevels[][] = excel.getRowColumnsDouble(filepath, 1);
		double limtlevels[][] = excel.getRowColumnsDouble(filepath, 2);
		calbean.setDeadlevels(deadlevels);
		calbean.setOptmlevels(optmlevels);
		calbean.setLimtlevels(limtlevels);
	}

	public void getResControlQouts(String filepath, CalculateBean calbean) {
		double minQouts[][] = excel.getRowColumnsDouble(filepath, 0);
		double optQouts[][] = excel.getRowColumnsDouble(filepath, 1);
		double maxQouts[][] = excel.getRowColumnsDouble(filepath, 2);
		calbean.setMinQouts(minQouts);
		calbean.setOptQouts(optQouts);
		calbean.setMaxQouts(maxQouts);
	}

}
