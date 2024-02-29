package BeanSets;

public class CalculateBean {
	public int order[] = null;// ---------------水库顺序
	public String name[] = null;// -------------水库名称
	public String NodeKinds[] = null;// --------节点类型
	public String OptKind[] = null;// -------------调度方式
	public String inflow_index[] = null;
	public String upstream_index[] = null;
	// ===================
	public double deadlevels[][] = null;
	public double optmlevels[][] = null;
	public double limtlevels[][] = null;
	// ===================
	public double maxQouts[][] = null;
	public double optQouts[][] = null;
	public double minQouts[][] = null;

	// ===================
	public int[] getOrder() {
		return order;
	}

	public void setOrder(int[] order) {
		this.order = order;
	}

	public String[] getName() {
		return name;
	}

	public void setName(String[] name) {
		this.name = name;
	}

	public String[] getNodeKinds() {
		return NodeKinds;
	}

	public void setNodeKinds(String[] nodeKinds) {
		NodeKinds = nodeKinds;
	}

	public String[] getOptKind() {
		return OptKind;
	}

	public void setOptKind(String[] optKind) {
		OptKind = optKind;
	}

	public String[] getInflow_index() {
		return inflow_index;
	}

	public void setInflow_index(String[] inflowIndex) {
		inflow_index = inflowIndex;
	}

	public String[] getUpstream_index() {
		return upstream_index;
	}

	public void setUpstream_index(String[] upstreamIndex) {
		upstream_index = upstreamIndex;
	}

	public double[][] getDeadlevels() {
		return deadlevels;
	}

	public void setDeadlevels(double[][] deadlevels) {
		this.deadlevels = deadlevels;
	}

	public double[][] getOptmlevels() {
		return optmlevels;
	}

	public void setOptmlevels(double[][] optmlevels) {
		this.optmlevels = optmlevels;
	}

	public double[][] getLimtlevels() {
		return limtlevels;
	}

	public void setLimtlevels(double[][] limtlevels) {
		this.limtlevels = limtlevels;
	}

	public double[][] getMaxQouts() {
		return maxQouts;
	}

	public void setMaxQouts(double[][] maxQouts) {
		this.maxQouts = maxQouts;
	}

	public double[][] getOptQouts() {
		return optQouts;
	}

	public void setOptQouts(double[][] optQouts) {
		this.optQouts = optQouts;
	}

	public double[][] getMinQouts() {
		return minQouts;
	}

	public void setMinQouts(double[][] minQouts) {
		this.minQouts = minQouts;
	}

}
