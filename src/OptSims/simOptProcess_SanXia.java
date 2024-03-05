package OptSims;

import BeanSets.BasicResBean;
import BeanSets.CalculateBean;
import DateManage.Save_xls_Date;
import ToolFunction.MathFun;
import jxl.write.WriteException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

public class simOptProcess_SanXia {

	/**
	 *
	 * //* @param optlines
	 * 
	 * @param calbean
	 * @param basicbeans
	 * @param saves
	 * @return
	 */
	double[] K = new double[15];// 电量平衡系数：每轮优化开始时赋值

	public double simResfitness_SanXia(double optQouts[][], CalculateBean calbean, BasicResBean basicbeans[],
			int startK, int saves, String OutputName) {// startK为调度时段
		String nodeNames[] = calbean.getName();
		String nodeKinds[] = calbean.getNodeKinds();
		String optKinds[] = calbean.getOptKind();
		String nodeFlowindexs[] = calbean.getInflow_index();
		String upStreamindexs[] = calbean.getUpstream_index();
		// ------------------------------
		double fitness = 0;
		// ------------------------------
		int nodeNum = nodeNames.length;
		HashMap outflow_map = new HashMap();
		int index = 0;
		// double initValue[][]=calbean.getOptmlevels();
		double maxlevel[][] = calbean.getLimtlevels();
		double minlevel[][] = calbean.getDeadlevels();
		// double timestep=maxlevel[0].length;//调度时段长
		for (int i = 0; i < nodeNum; i++) {
			String nodekind = nodeKinds[i];
			double upstream[] = getUpStream(upStreamindexs[i], outflow_map);
			if (nodekind.equals("R")) {// 水库类型节点
				// -----------------------------
				// *******优化发电流量过程************
				double optQout[] = optQouts[index];
				// -----------------------------
				// *******水库最高运行控制线************
				double maxLeve[] = maxlevel[index];
				double minLeve[] = minlevel[index];
				// -----------------------------
				// ****依据发电流量过程，模拟水库调度*******
				Vector rtresult = simResFunc(nodeNames[i], basicbeans[i], maxlevel[index], optKinds[i],
						upstream, optQout, minLeve, outflow_map, startK, saves, OutputName);
				// -----------------------------
				/**
				 * ********目标函数的计算***********
				 * rtresult包含2部分，
				 * 1 部分：发电量、深度破坏，生态和航运破坏量
				 * 2 部分：多目标保证率控制，返回的是与保证率的差值
				 */
				// -----------------------------

				double record[] = (double[]) rtresult.elementAt(0);
				double deepdestroy = 0;
				deepdestroy = record[0] * 100;
				// System.out.println(startK);
				if (startK == 0) {
					K[i] = 1000000000 / (record[1]);
				}
				double energys = (record[1]) * K[i] * 10;/// 10000
				double guarantEPunish = record[2] / 100;// (timestep)
				double que_ecoPunish = record[3] / 1000;// (Double)rtresult.elementAt(2);
				double que_navPunish = record[4] / 1000;// (Double)rtresult.elementAt(3);
				double minLeveLimit = record[5] * 100000000;// 小于低水位的惩罚
				double spills = record[6];
				/**
				 * 保证率的控制
				 * 不满足相应目标的保证率，则惩罚
				 * ****不满足保证率越多，惩罚越厉害****
				 * ****现在先注释掉，需要的时候自己添加****
				 */
				// double mulitobj_punish[]=(double[])rtresult.elementAt(1);
				// double sumbaozhenglv[]=(double[]) rtresult.elementAt(2);
				// double hydro_punish = mulitobj_punish[0] * 1000000000;
				// double eco_punish = mulitobj_punish[1] * 1000000000;
				// double nav_punish = mulitobj_punish[2] * 1000000000;
				// double eco_punish = sumbaozhenglv[1] * 1 * 1000000000;
				// double nav_punish = sumbaozhenglv[2] * 10 * 1000000000;

				// --------------------
				/**
				 * 目标函数计算
				 */
				fitness = fitness + (energys - minLeveLimit - que_ecoPunish - que_navPunish);// -deepdestroy
				// energys-deepdestroy-que_ecoPunish-que_navPunish-guarantEPunish//-guarantEPunish-deepdestroy-hydro_punish-minLeveLimit
				// System.out.println(nodeNames[i]+"系数"+K[i]+" spills="+spills+"
				// energys="+energys+" que_ecoPunish="+que_ecoPunish+"
				// que_navPunish="+que_navPunish+" minLeveLimit="+minLeveLimit);
				index++;
			} else {
				simStsFunc(nodeNames[i], basicbeans[i], optKinds[i], upstream, outflow_map);
			}
		}
		return fitness;
	}

	/**
	 *
	 * @param resName
	 * @param basicbean
	 * @param optkind
	 * @param upstream
	 *                    //* @param optlines
	 * @param outflow_map
	 * @param saves
	 * @return
	 */
	public Vector simResFunc(String resName, BasicResBean basicbean, double maxcontrolev[], String optkind,
			double upstream[], double optQout[], double minLeve[], HashMap outflow_map, int startK, int saves,
			String OutputName) {
		// -----------------------
		double ZV[][] = basicbean.getZ_V();
		double inflowss[] = basicbean.getInflow_pro();
		// -----------------------
		double deadLevel = basicbean.getDeadLevel(); // 死水位
		double normalLevel = basicbean.getNormalLevel(); // 正常蓄水位
		double floodLimtLevel = basicbean.getFloodLimtLevel();// 防洪限制水位 or 汛限水位
		double floodHighLevel = basicbean.getFloodHighLevel();// 防洪高水位
		double deadvolumn = basicbean.getDeadvolumn(); // 死库容
		double normalvolumn = basicbean.getNormalvolumn(); // 正常库容
		double deltaT = basicbean.getDeltaT(); // 调度时段间隔(hour)
		double Vunit = basicbean.getVunit(); // 库容单位
		// ----电站信息-------------------
		double InstalledCapacity = basicbean.getInstalledCapacity();// 装机容量
		double GuaranteedCapacity = basicbean.getGuaranteedCapacity();// 保证出力
		double efficiencyCOE = basicbean.getEfficiencyCOE(); // 发电效率系数——单值
		double tail_level = basicbean.getTail_level(); // 坝下水位——单值
		double hypower_max_outflow = basicbean.getHypower_max_outflow();// 最大发电流量
		double Qmax = hypower_max_outflow * deltaT * 3600;// 转为水量：立方米
		double haoshuili_level[][] = basicbean.getHaoshuili_level();// 耗水率曲线 HSL
		double tail_flow_level[][] = basicbean.getTail_flow_level();// 坝下流量-水位曲线 TailFL
		// ----其他用水信息----------------
		// -----------生态流量需求（输入单值或曲线数据）---------------
		double eco_flow = basicbean.getEco_flow();// -1024;//生态流量均值——单值
		double eco_flowpro[] = basicbean.getEco_flowpro();// null;//生态流量过程 EFP
		// -----------城镇供水流量需求（输入单值或曲线数据）---------------
		double city_supply_flow = basicbean.getCity_supply_flow();// 供水流量均值——单值
		double city_supply_flowpro[] = basicbean.getCity_supply_flowpro();// 供水流量过程CSFP
		// -----------农业供水流量需求（输入单值或曲线数据）---------------
		double agr_supply_flow = basicbean.getAgr_supply_flow();// 供水流量均值——单值
		double agr_supply_flowpro[] = basicbean.getAgr_supply_flowpro();// 供水流量过程ASFP
		// -----------航运流量需求（输入单值或曲线数据）---------------
		double nav_supply_flow = basicbean.getNav_supply_flow();// 供水流量均值——单值
		double nav_supply_flowpro[] = basicbean.getNav_supply_flowpro();// 供水流量过程NSFP
		// -----------其他最小下泄流量需求（输入单值或曲线数据）---------------
		double other_mini_flow = basicbean.getOther_mini_flow();// 其他最小下泄流量——单值
		double other_mini_flowpro[] = basicbean.getOther_mini_flowpro();// 其他最小下泄流量过程OMFP
		// -----水量平衡表-----------
		double balances[][] = new double[14][inflowss.length + 1];// 0水位，1库容，2弃水，3发电流量，4发电量，5其他
		// -----保证率--------------
		double hyp_guarant = basicbean.getHypower_guarant();
		double eco_guarant = basicbean.getAgr_guarant();
		double city_guarant = basicbean.getCity_guarant();
		double agr_guarant = basicbean.getAgr_guarant();
		double nav_guarant = basicbean.getNav_guarant();
		double other_guarant = basicbean.getOther_guarant();
		double baozhelv[] = new double[3];// 0发电，1生态，2航运
		// double que_navflowss=0;
		// double que_ecoflowss=0;
		// -----------------------
		// double punish[]=new double[inflowss.length];
		// double energy[]=new double[inflowss.length];
		// double spills[]=new double[inflowss.length];
		double outflow[] = new double[inflowss.length];
		// -----------------------
		double record[] = new double[7];// 记录
		// -----------------------

		int starts = 0;
		int ends = inflowss.length;

		// saves=1;
		// System.out.println(startK);
		if (saves == 0) {
			for (int i = 1; i <= (inflowss.length / 1461); i++) {
				if (startK < 1461 * i) {
					starts = 1461 * (i - 1) - 465;
					ends = 1461 * i + 180;
					if (starts < 0) {
						starts = 0;
					}
					if (ends > inflowss.length) {
						ends = inflowss.length;
					}
					break;
				}
			}
		}
		// System.out.println(starts+" "+ends);

		// int before=1000;
		// int after=1000;
		// if(startK>-300){
		// starts=startK-before;
		// if(starts<0){
		// starts=0;
		// }
		// ends= startK +after;
		// if(ends>inflowss.length){
		// ends=inflowss.length;
		// }
		// }
		// System.out.println(starts+" "+ends);
		// double
		// startvol=basicbean.getNormalvolumn()*Vunit;//------------------------初始库容floodHighLevel
		// double
		// startlev=basicbean.getNormalLevel();//-------------------------------初始水位
		double startlev = floodHighLevel;
		double startvol = MathFun.insert2(startlev, ZV[0], ZV[1], ZV[0].length) * Vunit;

		double deadvol = basicbean.getDeadvolumn() * Vunit;
		for (int i = starts; i < ends; i++) {
			// for(int i=0;i<20;i++){
			double inflowall = inflowss[i];
			if (upstream != null) {
				inflowall = (inflowss[i] + upstream[i]);// ----------------------------修改：流量转为水量
				// System.out.println(inflowall);
			}
			// double Vols=MathFun.insert2(optlines[i], ZV[0], ZV[1], ZV[0].length)*Vunit;
			// double Vole=MathFun.insert2(optlines[i+1], ZV[0], ZV[1], ZV[0].length)*Vunit;
			// ----可用水量单位：立方米-------------------------
			/**
			 * 可用水量单位：立方米
			 * availableQ=初始库容+来水量-死水位
			 * availableQ表示本时段全部的可以用水量
			 */
			double availableQ = startvol + (inflowall * deltaT * 3600) - deadvol;
			// -----水量不平衡-判断------------
			/*
			 * if(availableQ<0){//--深度破坏--
			 * record[0]=record[0]+availableQ;
			 * //System.out.println(i+"  "+availableQ);
			 * balances[11][i]=1;
			 * availableQ=0;
			 * //System.out.println("存在深度破坏！");
			 * }
			 */
			// -----城镇需水量-判断------------
			double citySupplyFlow = city_supply_flow * deltaT * 3600;// ----------------------------修改：流量转为水量
			if (city_supply_flow == -1024) {
				citySupplyFlow = city_supply_flowpro[i] * deltaT * 3600;// ----------------------------修改：流量转为水量
			}
			// -----农业需水量-判断------------
			double agrSupplyFlow = agr_supply_flow * deltaT * 3600;// ----------------------------修改：流量转为水量
			if (agr_supply_flow == -1024) {
				agrSupplyFlow = agr_supply_flowpro[i] * deltaT * 3600;// ----------------------------修改：流量转为水量
			}
			// -----生态需水量-判断------------
			double ecoFlow = eco_flow * deltaT * 3600;// ----------------------------修改：流量转为水量
			if (eco_flow == -1024) {
				ecoFlow = eco_flowpro[i] * deltaT * 3600;// ----------------------------修改：流量转为水量
			}
			// -----航运需水量-判断------------
			double navSupplyFlow = nav_supply_flow * deltaT * 3600;// ----------------------------修改：流量转为水量
			if (nav_supply_flow == -1024) {
				navSupplyFlow = nav_supply_flowpro[i] * deltaT * 3600;// ----------------------------修改：流量转为水量
			}
			double other_mini_flow1 = 0;
			if (other_mini_flow == -1024) {
				other_mini_flow1 = other_mini_flowpro[i];

				if ((other_mini_flow1 >= 801) && (other_mini_flow1 < 901)) {
					if (inflowall > 18000) {
						if (optQout[i] < 18000) {
							optQout[i] = 18000;
						}
					} else {
						optQout[i] = inflowall;
					}
				} else if ((other_mini_flow1 >= 901) && (other_mini_flow1 < 1001)) {
					if (inflowall > 10000) {
						if (optQout[i] < 10000) {
							optQout[i] = 10000;
						}
					} else if ((inflowall > 8000) && (inflowall < 10000)) {
						optQout[i] = inflowall;
					} else {
						optQout[i] = 8000;
					}
				} else if ((other_mini_flow1 >= 1001) && (other_mini_flow1 < 1101)) {
					if (inflowall > 8000) {
						if (optQout[i] < 8000) {
							optQout[i] = 8000;
						}
					} else {
						optQout[i] = inflowall;
					}
				}
			}
			// System.out.println(resName+"："+other_mini_flow1+"："+optQout[i]);
			/**
			 * 1 城镇和农业用水调度
			 * a 城镇和农业直接从河道和库区抽水，先直接扣除
			 */
			if (citySupplyFlow <= availableQ) {
				availableQ = availableQ - citySupplyFlow;
			} else {
				citySupplyFlow = availableQ;//// 可用水全部用完
				availableQ = 0;// 可用水全部用完
			}
			if (agrSupplyFlow <= availableQ) {
				availableQ = availableQ - agrSupplyFlow;
			} else {
				agrSupplyFlow = availableQ;// 可用水全部用完
				availableQ = 0;// 可用水全部用完
			}
			/**
			 * 2水库发电调度
			 * b 判断下泄流量是否大于满发流量，大于则发生弃水
			 */
			// System.out.println("------------hydroVol---------");
			// double minGuaranteedCapacityLow=GuaranteedCapacity/9.81/efficiencyCOE/;

			// System.out.println(optQout[i]);
			double hydroVol = optQout[i] * deltaT * 3600;// 转换为流量
			// System.out.println("hydroVol="+ hydroVol );
			/**
			 * 判断可用水量是否为负
			 * 为负,说明存在深度破坏
			 */
			if (availableQ < hydroVol) {
				record[0] = record[0] + (hydroVol - availableQ);// --------------------深度破坏，保持高水位
				// System.out.println(i+" "+availableQ);
				// balances[11][i]=1;
				hydroVol = availableQ;
				availableQ = 0;
				// System.out.println("存在深度破坏！");
			} else {
				availableQ = availableQ - hydroVol;
			}
			// System.out.println("availableQ="+ availableQ );
			// System.out.println("availableQ="+ availableQ + " hydroVol="+hydroVol);
			double endvolss = deadvol + availableQ;
			// System.out.println("endvolss="+ endvolss );
			double maxVols = MathFun.insert2(maxcontrolev[i], ZV[0], ZV[1], ZV[0].length) * Vunit;
			// System.out.println("maxVols="+ maxVols );
			double spills = 0;
			if (endvolss > maxVols) {
				spills = endvolss - maxVols;
				endvolss = maxVols;
			}
			if ((hydroVol + spills) >= hypower_max_outflow * (24 * 3600)) {
				spills = (hydroVol + spills) - hypower_max_outflow * (24 * 3600);
				hydroVol = hypower_max_outflow * (24 * 3600);
				// System.out.println(spills);
				// System.out.println(hypower_max_outflow);
			}
			record[6] = record[6] + spills;
			outflow[i] = hydroVol + spills;
			// System.out.println("endvolss="+ endvolss );
			double endlevel = MathFun.insert2((endvolss / Vunit), ZV[1], ZV[0], ZV[0].length);

			if (endlevel < minLeve[i]) {
				record[5] = record[5] + 100000;
				// System.out.println(minLeve[i]);
			}
			// System.out.println(resName+"："+other_mini_flow);
			if ((other_mini_flow1 >= 525) && (other_mini_flow1 < 611)) {// ------------------------------------------------仅针对三峡
				if ((startlev - endlevel) > 1) {
					record[5] = record[5] + 10000000;
					// System.out.println("startlev="+ startlev+" endlevel="+ endlevel+"
					// (startlev-endlevel)="+ (startlev-endlevel) );
				}
				// System.out.println(resName+other_mini_flow1);
				// System.out.println("startlev="+ startlev+" endlevel="+ endlevel+"
				// (startlev-endlevel)="+ (startlev-endlevel) );
			}
			// System.out.println("availableQ="+ availableQ + " endvolss="+endvolss+ "
			// maxVols="+maxVols);
			// System.out.println("startlev="+ startlev+" endlevel="+ endlevel+"
			// (startlev-endlevel)="+ (startlev-endlevel) );
			// basicbean.setCurrentwaterlevel(endlevel);
			// basicbean.setCurrentwatervolumn((endvolss/Vunit));

			// outflow[i]=availableQ/(deltaT*3600);//下泄流=总可用水量-城镇供水-农业
			double hydroQout = hydroVol / (deltaT * 3600);// 转换为流量

			/*
			 * if(availableQ>Qmax){//发生弃水，此处Qmax为水量
			 * spills=availableQ-Qmax;
			 * hydroQout=Qmax/(deltaT*3600);//转换为流量
			 * }
			 */
			// System.out.println("availableQ= "+hydroQout+" Qmax= "+Qmax/(deltaT*3600));
			double energy = 0;
			double energy1 = 0;
			if (efficiencyCOE == -1024) {// 采用耗水率曲线
				// 采用耗水率曲线
			} else {
				double tailleve = 0;
				if (availableQ < 0) {// 深度破环时水量处理
					availableQ = 0;
				}
				double transAvailableQ = outflow[i] / (deltaT * 3600);
				if (tail_level == -1024) {
					tailleve = MathFun.insert2(transAvailableQ, tail_flow_level[1],
							tail_flow_level[0], tail_flow_level[0].length);// 是否改用availableQ,-----修改：将availableQ转为流量
				} else {
					tailleve = tail_level;
				}
				// System.out.println("tailleve= "+tailleve+" tail_level= "+tail_level);
				double punish = 0;
				double Head = (startlev + endlevel) / 2.0 - tailleve;// deadLevel
				double minHead = 1;
				double HeadShu = (Head - (deadLevel - tailleve)) / (normalLevel - deadLevel);
				if (resName.equals("乌东德")) {
					minHead = 1 + HeadShu * 0.3;
				} else if (resName.equals("白鹤滩")) {
					minHead = 1 + HeadShu * 1.5;
				} else if (resName.equals("溪洛渡")) {
					minHead = 1 + HeadShu * 0.2;
				} else if (resName.equals("三峡")) {
					minHead = 1 + HeadShu * 0.1;
				}
				// System.out.println("Head= "+Head+"，startlev= "+startlev+"，endlevel=
				// "+endlevel+"，tailleve= "+tailleve);
				// System.out.println(resName+" "+minHead);
				energy1 = (9.81 * efficiencyCOE * hydroQout * Head * minHead * deltaT);// -----------------------------增加水头敏感度
				energy = (9.81 * efficiencyCOE * hydroQout * Head * deltaT);// 单位KW*H，//如果乘以deltaT即为KW*h
				if (energy < (1 * GuaranteedCapacity * deltaT)) {
					punish = (1 * GuaranteedCapacity * deltaT) - energy;
					baozhelv[0] = baozhelv[0] + 1;
					// System.out.println("不满足最小出力");
				}
				// System.out.println("InstalledCapacity "+InstalledCapacity/10000);
				if (energy > (InstalledCapacity * deltaT)) {
					energy = InstalledCapacity * deltaT;
				}
				// System.out.println((InstalledCapacity*deltaT)/100000+"
				// energy="+energy/100000+" "+(GuaranteedCapacity*deltaT)/100000);
				record[1] = record[1] + energy1;// 单位KW*H
				record[2] = record[2] + punish;
			}

			/**
			 * 3 生态和航运用水调度
			 * c 弃水+发电流量是否大于生态和航运需水，大于则满足条件
			 */
			double que_ecoflow = 0;
			if (ecoFlow <= outflow[i]) {
				// ecoFlow=0;
			} else {
				baozhelv[1] = baozhelv[1] + 1;
				que_ecoflow = ecoFlow - outflow[i];// 可用水量低，计算生态缺水量，单位：立方米
				// ecoFlow=availableQ;
				if (other_mini_flow1 > 0 & (other_mini_flow1 < 400 | other_mini_flow1 > 1000)) {
					record[3] = record[3] + que_ecoflow + 1000000000;
				}
				record[3] = record[3] + que_ecoflow;
			}
			double que_navflow = 0;
			if (navSupplyFlow <= outflow[i]) {
				// navSupplyFlow=0;
			} else {
				baozhelv[2] = baozhelv[2] + 1;
				que_navflow = navSupplyFlow - outflow[i];// 可用水量低，计算航运缺水量
				// navSupplyFlow=availableQ;
				if (other_mini_flow1 > 0 & (other_mini_flow1 < 400 | other_mini_flow1 > 1000)) {
					record[4] = record[4] + (que_navflow + 1000000000) * 100;
					// System.out.println(resName+(que_navflow+1000000000)*100);
				} else if (resName.equals("向家坝")) {
					record[4] = record[4] + (que_navflow + 1000000000) * 100;
					// System.out.println(resName+(que_navflow+1000000000)*100);
				} else {
					record[4] = record[4] + que_navflow;
					// System.out.println(resName+que_navflow);
				}
			}

			// ----水量平衡表------------
			// if (saves == 1) {
			// 	balances[0][i + 1] = startlev;// optlines[i];
			// 	balances[1][i + 1] = startvol;
			// 	balances[2][i + 1] = inflowss[i];
			// 	balances[3][i + 1] = 0;
			// 	if (upstream != null) {
			// 		balances[3][i + 1] = upstream[i];
			// 	} // 上游水库来水
			// 	balances[4][i + 1] = spills / 24 / 3600;// 弃水
			// 	balances[5][i + 1] = citySupplyFlow;
			// 	balances[6][i + 1] = agrSupplyFlow;
			// 	balances[7][i + 1] = hydroQout;
			// 	balances[7][0] = 1 - baozhelv[0] / inflowss.length;
			// 	balances[8][i + 1] = que_ecoflow;
			// 	balances[8][0] = 1 - baozhelv[1] / inflowss.length;
			// 	balances[9][i + 1] = que_navflow;
			// 	balances[9][0] = 1 - baozhelv[2] / inflowss.length;
			// 	balances[10][i + 1] = energy;
			// 	balances[10][0] = (balances[10][0] + balances[10][i + 1]) / 100000000;
			// 	balances[11][i + 1] = hydroQout + spills / 24 / 3600;// 下泄
			// }
			if (saves == 1) {
				balances[0][i + 1] = minLeve[i];
				balances[1][i + 1] = startlev;// optlines[i];
				balances[2][i + 1] = maxcontrolev[i];
				balances[3][i + 1] = startvol;
				balances[4][i + 1] = inflowss[i];
				balances[5][i + 1] = 0;
				if (upstream != null) {
					balances[3][i + 1] = upstream[i];
				} // 上游水库来水
				balances[6][i + 1] = spills / 24 / 3600;// 弃水
				balances[7][i + 1] = citySupplyFlow;
				balances[8][i + 1] = agrSupplyFlow;
				balances[9][i + 1] = hydroQout;
				balances[9][0] = 1 - baozhelv[0] / inflowss.length;
				balances[10][i + 1] = que_ecoflow;
				balances[10][0] = 1 - baozhelv[1] / inflowss.length;
				balances[11][i + 1] = que_navflow;
				balances[11][0] = 1 - baozhelv[2] / inflowss.length;
				balances[12][i + 1] = energy;
				balances[12][0] = (balances[10][0] + balances[10][i + 1]) / 100000000;
				balances[13][i + 1] = hydroQout + spills / 24 / 3600;// 下泄
			}
			// -----------------------
			startvol = endvolss;
			startlev = endlevel;
			outflow[i] = outflow[i] / (deltaT * 3600);
		}
		outflow_map.put(resName + "out", outflow);
		double[] baozhelv1 = new double[3];

		int inflowleng = inflowss.length;
		if (saves == 0) {
			inflowleng = ends - starts;
		}
		// System.out.println(inflowleng);

		baozhelv1[0] = (1.0 - (baozhelv[0] / inflowleng)) * 100;
		baozhelv1[1] = (1.0 - (baozhelv[1] / inflowleng)) * 100;
		baozhelv1[2] = (1.0 - (baozhelv[2] / inflowleng)) * 100;
		// System.out.println(baozhelv1[0]);
		// System.out.println(baozhelv1[1]);
		// System.out.println(baozhelv1[2]);
		double muliobj_punish[] = new double[3];
		if (hyp_guarant > 50) {// 大于50%才判断考虑了保证率约束
			if (baozhelv1[0] < hyp_guarant) {
				muliobj_punish[0] = hyp_guarant - baozhelv1[0];
			}
		}
		// System.out.println(eco_guarant);
		if (eco_guarant > 50) {// 大于50%才判断考虑了保证率约束
			if (baozhelv1[1] < eco_guarant) {
				muliobj_punish[1] = eco_guarant - baozhelv1[1];
			}
		}
		if (nav_guarant > 50) {// 大于50%才判断考虑了保证率约束
			if (baozhelv1[2] < nav_guarant) {
				muliobj_punish[2] = nav_guarant - baozhelv1[2];
			}
		}

		// System.out.println(record[0]+" "+record[1]+" "+record[2]);

		// -----------------------
		Vector retnVec = new Vector();
		retnVec.addElement(record);
		retnVec.addElement(muliobj_punish);
		retnVec.addElement(baozhelv);
		// retnVec.add(que_ecoflowss);
		// retnVec.add(que_navflowss);
		/**
		 * 保存信息
		 * 1 水量平衡表
		 * 2 保证率
		 */
		if (saves == 1) {
			Save_xls_Date save = new Save_xls_Date();
			String savePath = basicbean.getSavebalance();
			try {
				// boolean flag1 = save.saveCol(savePath+resName+".xls", baozhelv);
				boolean flag2 = save.saveCols(savePath + resName + OutputName, balances);
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return retnVec;
	}

	/**
	 * //* @param resName
	 * 
	 * @param basicbean
	 * @param optkind
	 * @param upstream
	 * @param outflow_map
	 */
	public void simStsFunc(String stName, BasicResBean basicbean, String optkind,
			double upstream[], HashMap outflow_map) {
		double inflowss[] = basicbean.getInflow_pro();
		double outflowall[] = new double[inflowss.length];
		for (int i = 0; i < inflowss.length; i++) {
			if (upstream != null) {
				outflowall[i] = inflowss[i] + upstream[i];
			} else {
				outflowall[i] = inflowss[i];
			}
		}
		outflow_map.put(stName + "out", outflowall);
	}

	/**
	 *
	 * @param upStreamindexs
	 * @param outflow_map
	 */
	public double[] getUpStream(String upStreamindexs, HashMap outflow_map) {

		double upstream[] = null;
		if (upStreamindexs.equals("no")) {

		} else {
			String upStreamNames[] = upStreamindexs.split(",");
			for (int i = 0; i < upStreamNames.length; i++) {
				if (outflow_map.containsKey(upStreamNames[i])) {
					double outflows[] = (double[]) outflow_map.get(upStreamNames[i]);
					if (upstream == null) {
						upstream = new double[outflows.length];
					}
					for (int j = 0; j < outflows.length; j++) {
						upstream[j] = upstream[j] + outflows[j];
					}
				}
			}
		}
		return upstream;
	}
}