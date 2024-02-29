package BeanSets;

public class BasicResBean {
	// ----------水库基本信息------------
	public int resOrder = -1024; // 水库编号，计算顺序的依据
	public int resNumber = -1024; // 水库编号
	public String resNameCN = null; // 水库--中文--名称
	public double deadLevel = -1024; // 死水位
	public double normalLevel = -1024; // 正常蓄水位
	public double floodLimtLevel = -1024;// 防洪限制水位 or 汛限水位
	public double floodHighLevel = -1024;// 防洪高水位
	public double deadvolumn = -1024; // 死库容
	public double normalvolumn = -1024; // 正常库容
	public double deltaT = -1024; // 调度时段间隔(hour)
	public double Vunit = 10000; // 库容单位（万方）
	// -----------保证率-------------------
	public double hypower_guarant = -1024;
	public double eco_guarant = -1024;
	public double city_guarant = -1024;
	public double agr_guarant = -1024;
	public double nav_guarant = -1024;
	public double other_guarant = -1024;
	// ----------计算中的信息------------
	public String NodeKinds = null; // 节点类型
	public int OptKind = -1024;// 运行方式
	public String inflow_index = null;// 入库径流索引名称
	public String upstream_index = null;// 上游入库径流索引名称

	// ----------各类基础曲线------------
	public double Z_V[][] = null; // 水位库容曲线ZV
	public double spillway[][] = null; // 溢洪道泄流能力曲线

	// -------实时水位和库容---------
	public double currentwaterlevel = deadLevel;// 当前时段，水库蓄水位
	public double currentwatervolumn = deadvolumn;// 当前时段，水库蓄水库容
	public double inflow_pro[] = null;// 当前时段，入库径流过程 Inflows

	// -----------电站信息---------------
	public double InstalledCapacity = -1024;// 装机容量
	public double GuaranteedCapacity = -1024;// 保证出力
	public double efficiencyCOE = -1024;// 发电效率系数——单值
	public double tail_level = -1024; // 坝下水位——单值
	public double hypower_max_outflow = -1024;// 最大发电流量
	public double haoshuili_level[][] = null;// 耗水率曲线 HSL
	public double tail_flow_level[][] = null;// 坝下流量-水位曲线 TailFL

	// -----------生态流量需求（输入单值或曲线数据）---------------
	public double eco_flow = -1024;// 生态流量均值——单值
	public double eco_flowpro[] = null;// 生态流量过程 EFP

	// -----------城镇供水流量需求（输入单值或曲线数据）---------------
	public double city_supply_flow = -1024;// 供水流量均值——单值
	public double city_supply_flowpro[] = null;// 供水流量过程CSFP

	// -----------农业供水流量需求（输入单值或曲线数据）---------------
	public double agr_supply_flow = -1024;// 供水流量均值——单值
	public double agr_supply_flowpro[] = null;// 供水流量过程ASFP

	// -----------航运流量需求（输入单值或曲线数据）---------------
	public double nav_supply_flow = -1024;// 供水流量均值——单值
	public double nav_supply_flowpro[] = null;// 供水流量过程NSFP

	// -----------其他最小下泄流量需求（输入单值或曲线数据）---------------
	public double other_mini_flow = -1024;// 其他最小下泄流量——单值
	public double other_mini_flowpro[] = null;// 其他最小下泄流量过程OMFP
	// -----------其他计算需要的曲线（输入单值或曲线数据）---------------
	public double other_curve_lines[][] = null;// 其他计算需要的曲线OCLs

	// -----------水量平衡保存地址-------------------
	public String savebalance = "ResStations\\";
	/// Users/lovemevol/Library/CloudStorage/OneDrive-m.ldu.edu.cn/文稿/IdeaProjects/YangtzeRiverRes-枯水/ResStations
	// D:\OneDrive - m.ldu.edu.cn\文稿\IdeaProjects\YangtzeRiverRes-枯水\ResStations\
	/// Users/lovemevol/Library/CloudStorage/OneDrive-m.ldu.edu.cn/文稿/IdeaProjects/YangtzeRiverRes/ResStations/
	// D:\OneDrive - m.ldu.edu.cn\文稿\IdeaProjects\YangtzeRiverRes\ResStations\
	// -------------水库运行方式和特点-------------------
	// public int OptKind=0;

	public int getResNumber() {
		return resNumber;
	}

	public void setResNumber(int resNumber) {
		this.resNumber = resNumber;
	}

	public String getResNameCN() {
		return resNameCN;
	}

	public void setResNameCN(String resNameCN) {
		this.resNameCN = resNameCN;
	}

	public double getDeadLevel() {
		return deadLevel;
	}

	public void setDeadLevel(double deadLevel) {
		this.deadLevel = deadLevel;
	}

	public double getNormalLevel() {
		return normalLevel;
	}

	public void setNormalLevel(double normalLevel) {
		this.normalLevel = normalLevel;
	}

	public double getFloodLimtLevel() {
		return floodLimtLevel;
	}

	public void setFloodLimtLevel(double floodLimtLevel) {
		this.floodLimtLevel = floodLimtLevel;
	}

	public double getFloodHighLevel() {
		return floodHighLevel;
	}

	public void setFloodHighLevel(double floodHighLevel) {
		this.floodHighLevel = floodHighLevel;
	}

	public double getDeadvolumn() {
		return deadvolumn;
	}

	public void setDeadvolumn(double deadvolumn) {
		this.deadvolumn = deadvolumn;
	}

	public double getNormalvolumn() {
		return normalvolumn;
	}

	public void setNormalvolumn(double normalvolumn) {
		this.normalvolumn = normalvolumn;
	}

	public double getDeltaT() {
		return deltaT;
	}

	public void setDeltaT(double deltaT) {
		this.deltaT = deltaT;
	}

	public double[][] getZ_V() {
		return Z_V;
	}

	public void setZ_V(double[][] zV) {
		Z_V = zV;
	}

	public double[][] getSpillway() {
		return spillway;
	}

	public void setSpillway(double[][] spillway) {
		this.spillway = spillway;
	}

	public double getCurrentwaterlevel() {
		return currentwaterlevel;
	}

	public void setCurrentwaterlevel(double currentwaterlevel) {
		this.currentwaterlevel = currentwaterlevel;
	}

	public double getCurrentwatervolumn() {
		return currentwatervolumn;
	}

	public void setCurrentwatervolumn(double currentwatervolumn) {
		this.currentwatervolumn = currentwatervolumn;
	}

	public double[] getInflow_pro() {
		return inflow_pro;
	}

	public void setInflow_pro(double[] inflowPro) {
		inflow_pro = inflowPro;
	}

	public double getInstalledCapacity() {
		return InstalledCapacity;
	}

	public void setInstalledCapacity(double installedCapacity) {
		InstalledCapacity = installedCapacity;
	}

	public double getGuaranteedCapacity() {
		return GuaranteedCapacity;
	}

	public void setGuaranteedCapacity(double guaranteedCapacity) {
		GuaranteedCapacity = guaranteedCapacity;
	}

	public double getEfficiencyCOE() {
		return efficiencyCOE;
	}

	public void setEfficiencyCOE(double efficiencyCOE) {
		this.efficiencyCOE = efficiencyCOE;
	}

	public double[][] getHaoshuili_level() {
		return haoshuili_level;
	}

	public void setHaoshuili_level(double[][] haoshuiliLevel) {
		haoshuili_level = haoshuiliLevel;
	}

	public double getTail_level() {
		return tail_level;
	}

	public void setTail_level(double tailLevel) {
		tail_level = tailLevel;
	}

	public double[][] getTail_flow_level() {
		return tail_flow_level;
	}

	public void setTail_flow_level(double[][] tailFlowLevel) {
		tail_flow_level = tailFlowLevel;
	}

	public double getHypower_max_outflow() {
		return hypower_max_outflow;
	}

	public void setHypower_max_outflow(double hypowerMaxOutflow) {
		hypower_max_outflow = hypowerMaxOutflow;
	}

	public double getEco_flow() {
		return eco_flow;
	}

	public void setEco_flow(double ecoFlow) {
		eco_flow = ecoFlow;
	}

	public double getCity_supply_flow() {
		return city_supply_flow;
	}

	public void setCity_supply_flow(double citySupplyFlow) {
		city_supply_flow = citySupplyFlow;
	}

	public double[] getCity_supply_flowpro() {
		return city_supply_flowpro;
	}

	public void setCity_supply_flowpro(double[] citySupplyFlowpro) {
		city_supply_flowpro = citySupplyFlowpro;
	}

	public double getAgr_supply_flow() {
		return agr_supply_flow;
	}

	public void setAgr_supply_flow(double agrSupplyFlow) {
		agr_supply_flow = agrSupplyFlow;
	}

	public double[] getAgr_supply_flowpro() {
		return agr_supply_flowpro;
	}

	public void setAgr_supply_flowpro(double[] agrSupplyFlowpro) {
		agr_supply_flowpro = agrSupplyFlowpro;
	}

	public double getNav_supply_flow() {
		return nav_supply_flow;
	}

	public void setNav_supply_flow(double navSupplyFlow) {
		nav_supply_flow = navSupplyFlow;
	}

	public double[] getNav_supply_flowpro() {
		return nav_supply_flowpro;
	}

	public void setNav_supply_flowpro(double[] navSupplyFlowpro) {
		nav_supply_flowpro = navSupplyFlowpro;
	}

	public int getOptKind() {
		return OptKind;
	}

	public void setOptKind(int optKind) {
		OptKind = optKind;
	}

	public double getOther_mini_flow() {
		return other_mini_flow;
	}

	public void setOther_mini_flow(double otherMiniFlow) {
		other_mini_flow = otherMiniFlow;
	}

	public double[] getOther_mini_flowpro() {
		return other_mini_flowpro;
	}

	public void setOther_mini_flowpro(double[] otherMiniFlowpro) {
		other_mini_flowpro = otherMiniFlowpro;
	}

	public double[] getEco_flowpro() {
		return eco_flowpro;
	}

	public void setEco_flowpro(double[] ecoFlowpro) {
		eco_flowpro = ecoFlowpro;
	}

	public double[][] getOther_curve_lines() {
		return other_curve_lines;
	}

	public void setOther_curve_lines(double[][] otherCurveLines) {
		other_curve_lines = otherCurveLines;
	}

	public String getNodeKinds() {
		return NodeKinds;
	}

	public void setNodeKinds(String nodeKinds) {
		NodeKinds = nodeKinds;
	}

	public String getInflow_index() {
		return inflow_index;
	}

	public void setInflow_index(String inflowIndex) {
		inflow_index = inflowIndex;
	}

	public String getUpstream_index() {
		return upstream_index;
	}

	public void setUpstream_index(String upstreamIndex) {
		upstream_index = upstreamIndex;
	}

	public int getResOrder() {
		return resOrder;
	}

	public void setResOrder(int resOrder) {
		this.resOrder = resOrder;
	}

	public double getVunit() {
		return Vunit;
	}

	public void setVunit(double vunit) {
		Vunit = vunit;
	}

	public String getSavebalance() {
		return savebalance;
	}

	public void setSavebalance(String savebalance) {
		this.savebalance = savebalance;
	}

	public double getEco_guarant() {
		return eco_guarant;
	}

	public void setEco_guarant(double ecoGuarant) {
		eco_guarant = ecoGuarant;
	}

	public double getCity_guarant() {
		return city_guarant;
	}

	public void setCity_guarant(double cityGuarant) {
		city_guarant = cityGuarant;
	}

	public double getAgr_guarant() {
		return agr_guarant;
	}

	public void setAgr_guarant(double agrGuarant) {
		agr_guarant = agrGuarant;
	}

	public double getNav_guarant() {
		return nav_guarant;
	}

	public void setNav_guarant(double navGuarant) {
		nav_guarant = navGuarant;
	}

	public double getOther_guarant() {
		return other_guarant;
	}

	public void setOther_guarant(double otherGuarant) {
		other_guarant = otherGuarant;
	}

	public double getHypower_guarant() {
		return hypower_guarant;
	}

	public void setHypower_guarant(double hypowerGuarant) {
		hypower_guarant = hypowerGuarant;
	}

}
