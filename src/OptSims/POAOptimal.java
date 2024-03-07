package OptSims;

import BeanSets.BasicResBean;
import BeanSets.CalculateBean;
import DateManage.Save_xls_Date;
import ToolFunction.CommonFun;
import ToolFunction.MathFun;

public class POAOptimal {
    // simOptProcess simoptprc = new simOptProcess();
    simOptProcess_SanXia simoptprc_SanXia = new simOptProcess_SanXia();
    Save_xls_Date save = new Save_xls_Date();
    // static String savepath="E:/Eclipsework/NanPenRes/数据包/training/";

    long startTime = System.currentTimeMillis();

    public void optimizeDPSA(CalculateBean calbean, BasicResBean basicbeans[], String OutputName, int time) {
        // CalculateBean calbean=new CalculateBean();
        // BasicResBean basicbeans[]=new BasicResBean[2];

        String OptKind[] = calbean.getOptKind();
        int nodeNum = OptKind.length;
        for (int i = 0; i < nodeNum; i++) {
            String optkind = OptKind[i];
            if (optkind.equals("Multi"))// 水库群联合调度
            {
                System.out.println("Multi");
            } else if (optkind.equals("Single")) // 单水库调度
            {
                System.out.println("Single");
            } else {
                System.out.println("No");
            }

        }

        /*************** 调度控制线 **************/
        double maxQouts[][] = calbean.getMaxQouts();
        double initQout[][] = calbean.getOptQouts();
        double minQouts[][] = calbean.getMinQouts();
        // int A =initValue[1].length;
        // System.out.println(A);
        // System.out.println(" opeNum="+initValue.length+"
        // stageNum="+initValue[0].length);
        /*************** 调度控制线 **************/
        double res_ope_chart[][] = new double[initQout.length][initQout[0].length];
        for (int i = 0; i < initQout.length; i++) {
            for (int j = 0; j < initQout[i].length; j++) {
                res_ope_chart[i][j] = initQout[i][j];
            }
        }

        // double sim[][]=new double[time][];
        int a = 0;
        int b = 50;
        int c = 50;// --------------------------------------离散步长每循环b次进行重置,每循环c次输出水量平衡表
        for (int i = 0; i < time; i++) {
            if (i % b == 0) {
                a = 0;
            }
            double arf = Math.exp((-10) * Math.pow((((double) a + 1) * 1.0 / b), 3));
            double steplength = (0.2 * arf + 0.01);
            // System.out.println(steplength);
            double res_ope_chart2[][] = dPSA1(maxQouts, res_ope_chart, minQouts, steplength, calbean, basicbeans,
                    OutputName, time);// ----------dPSA为完整优化------dPSA1为快速优化
            for (int w = 0; w < res_ope_chart2.length; w++) {
                for (int j = 0; j < res_ope_chart2[w].length; j++) {
                    res_ope_chart[w][j] = res_ope_chart2[w][j];
                }
            }
            if (i % c == 0) {
                String OutputName1 = "_" + i + "次_" + OutputName;
                fitnessDPSA(res_ope_chart, calbean, basicbeans, -1024, 1, OutputName1);
            }
            a++;
        }

        // for (int i = 0; i < time; i++) {
        // if (i%b == 0){
        // a=0;
        // }
        // double arf=Math.exp((-10)*Math.pow((((double) a+1)*1.0/50),3));
        // double steplength=(0.2*arf+0.01);
        // System.out.println(steplength);
        //// double res_ope_chart2[][] = dPSA(maxQouts, res_ope_chart,
        // minQouts,steplength, calbean, basicbeans,OutputName,time);
        //// for(int w=0;w<res_ope_chart2.length;w++){
        //// for(int j=0;j<res_ope_chart2[w].length;j++){
        //// res_ope_chart[w][j]=res_ope_chart2[w][j];
        //// }
        //// }
        // a++;
        // }
        /**
         * 保存调度图
         */
        fitnessDPSA(res_ope_chart, calbean, basicbeans, -1024, 1, OutputName);
        /*
         * try {
         * boolean flag2 = save.saveColsheets(savepath+"调度图.xls", 7, initValue);
         * } catch (WriteException e) {
         * // TODO Auto-generated catch block
         * e.printStackTrace();
         * } catch (IOException e) {
         * // TODO Auto-generated catch block
         * e.printStackTrace();
         * }
         */
        /**
         * 依据调度图，模拟调度过程，并保存水量平衡表
         */
        // simOperation(initValue, xjkbean, fybean, npbean);
        /**
         * Vector simsupply = simRecWaterSupply(initValue);
         * boolean flag1 = save.saveCol("C:/Documents and
         * Settings/Administrator/桌面/新建文件夹"+j+"/sim" + i +".xls", sim[i]);
         * boolean flag2 = save.saveColsheets("C:/Documents and
         * Settings/Administrator/桌面/新建文件夹"+j+"/result" + i +".xls", 4, initValue);
         **/
    }

    /**
     * 输入水库群调度图
     * 
     * @param res_ope_limt  float[][]限制水位【水库群水库个数】【调度线】
     * @param res_ope_chart float[][]【水库群水库个数】【各调度线长度】
     * @param rec_ope_dead  float[][]死水位【水库群水库个数】【调度线】
     */
    int ComNum = 1;

    public double[][] dPSA(double res_ope_limt[][], double res_ope_chart[][], double rec_ope_dead[][],
            double steplenpercent, CalculateBean calbean, BasicResBean basicbeans[], String OutputName, int time) {
        int resNum = res_ope_chart.length;// 第i个水库调度线条数（参与调度的水库个数）
        int stageNum = res_ope_chart[0].length;// 第i个水库调度线调度阶段数-时段（优化的时段数）
        System.out.println("第" + ComNum + "次优化  opeNum=" + resNum + "  stageNum=" + stageNum);
        for (int j = 0; j < (stageNum); j++) {// 对水库i的j时段进行调度线优化(stageNum-1)
            // int combinNum=(int)Math.pow(3,opeNum);
            double value[] = new double[resNum];
            double steplenght[] = new double[resNum];
            double res_ope_max[] = new double[resNum];
            double res_ope_min[] = new double[resNum];
            for (int k = 0; k < resNum; k++) {// 对水库k的第j时段进行调度线组合
                value[k] = res_ope_chart[k][j];
                steplenght[k] = (res_ope_limt[k][j] - rec_ope_dead[k][j]) * steplenpercent;
                res_ope_max[k] = res_ope_limt[k][j];
                res_ope_min[k] = rec_ope_dead[k][j];
            }
            double yuanshi = fitnessDPSA(res_ope_chart, calbean, basicbeans, j, 0, OutputName);
            // =======================
            double disstage[][] = disStage(res_ope_max, value, res_ope_min, steplenght, 3);// --------------n离散份数，方案数为n的水库数次方，disstage[方案组合数][水库数]
            // System.out.println(disstage.length+" "+disstage[1].length);
            double calResult[] = new double[disstage.length];
            for (int t = 0; t < disstage.length; t++) {
                for (int k = 0; k < resNum; k++) {// 对水库k的第j时段进行调度线组合
                    res_ope_chart[k][j] = disstage[t][k];
                    // System.out.print(disstage[t][k]+" ");
                }
                // System.out.println();
                calResult[t] = fitnessDPSA(res_ope_chart, calbean, basicbeans, j, 0, OutputName);// ------------------标记点
                // System.out.println(t+" "+calResult[t]);
            }
            double maxResult = MathFun.getMax(calResult);
            int maxIndex = MathFun.orderint_double(maxResult, calResult);

            // System.out.println("第"+ ComNum + "次优化 stage="+j+" maxIndex="+maxIndex+"
            // maxResult="+maxResult);
            // res_ope_chart=ope_chart[maxIndex];
            double errorr = maxResult - yuanshi;
            if (errorr <= 0) {
                for (int k = 0; k < resNum; k++) {// 对水库k的第j时段进行调度线组合
                    res_ope_chart[k][j] = value[k];
                }
            } else {
                // System.out.println(" stage="+j+" maxResult="+maxResult+" yuanshi="+yuanshi);
                for (int k = 0; k < resNum; k++) {// 对水库k的第j时段进行调度线组合
                    res_ope_chart[k][j] = disstage[maxIndex][k];
                }
            }

            if (j % 1000 == 0) {// ------------------------------------------每1000个时段打印
                long endTime = System.currentTimeMillis();
                long useTime = (endTime - startTime) / 1000 / 60;
                String Times = "";
                if ((int) Math.floor(useTime / 60) < 1) {
                    Times = (useTime % 60) + "min";
                } else {
                    Times = (int) Math.floor(useTime / 60) + "h " + (useTime % 60) + "min";
                }
                System.out.println("第" + ComNum + "/" + time + "次优化  stage=" + j + "   maxIndex=" + maxIndex
                        + "   maxResult=" + maxResult + "      useTime：" + Times);// startTime
            }
        }

        ComNum++;
        return res_ope_chart;
    }

    public double[][] dPSA1(double res_ope_limt[][], double res_ope_chart[][], double rec_ope_dead[][],
            double steplenpercent, CalculateBean calbean, BasicResBean basicbeans[], String OutputName, int time) {

        int resNum = res_ope_chart.length;// 第i个水库调度线条数（参与调度的水库个数）
        int stageNum = res_ope_chart[0].length;// 第i个水库调度线调度阶段数-时段（优化的时段数）
        System.out.print("第" + ComNum + "次优化  opeNum=" + resNum + "  stageNum=" + stageNum);
        for (int j = 0; j < (stageNum); j++) {// 对水库i的j时段进行调度线优化(stageNum-1)
            double value[] = new double[resNum];
            double steplenght[] = new double[resNum];
            double res_ope_max[] = new double[resNum];
            double res_ope_min[] = new double[resNum];
            for (int k = 0; k < resNum; k++) {// 对水库k的第j时段进行调度线组合
                value[k] = res_ope_chart[k][j];
                steplenght[k] = (res_ope_limt[k][j] - rec_ope_dead[k][j]) * steplenpercent;
                res_ope_max[k] = res_ope_limt[k][j];
                res_ope_min[k] = rec_ope_dead[k][j];
            }
            double yuanshi = fitnessDPSA(res_ope_chart, calbean, basicbeans, j, 0, OutputName);
            // =======================

            int maxIndex = 0;
            double maxResult = 0;
            int maxnum[] = new int[resNum];// 记录每个水库的最优点（0、1、2）
            for (int q = 0; q < resNum; q++) {// 对水库q的第j时段进行调度线组合
                double disstage[][] = disStage1(res_ope_max, value, res_ope_min, steplenght, 3, q, maxnum);// --------------n离散份数
                double calResult[] = new double[disstage.length];
                // System.out.println(disstage.length);
                for (int t = 0; t < disstage.length; t++) {
                    for (int k = 0; k < resNum; k++) {// 对水库k的第j时段进行调度线组合
                        res_ope_chart[k][j] = disstage[t][k];
                        // System.out.print(disstage[t][k]+" ");
                    }
                    // System.out.println();
                    calResult[t] = fitnessDPSA(res_ope_chart, calbean, basicbeans, j, 0, OutputName);// ------------------标记点
                    // System.out.println(t+" "+calResult[t]);
                }
                maxResult = MathFun.getMax(calResult);
                maxIndex = MathFun.orderint_double(maxResult, calResult);
                maxnum[q] = maxIndex;

                // System.out.println(q+" "+maxnum[q]);

                double errorr = maxResult - yuanshi;
                if (errorr <= 0) {
                    for (int k = 0; k < resNum; k++) {// 对水库k的第j时段进行调度线组合
                        res_ope_chart[k][j] = value[k];
                    }
                } else {
                    // System.out.println(" stage="+j+" maxResult="+maxResult+" yuanshi="+yuanshi);
                    for (int k = 0; k < resNum; k++) {// 对水库k的第j时段进行调度线组合
                        res_ope_chart[k][j] = disstage[maxIndex][k];
                    }
                }
            }
            if (j % stageNum == 0) {
                long endTime = System.currentTimeMillis();
                long useTime = (endTime - startTime) / 1000 / 60;
                String Times = "";
                if ((int) Math.floor(useTime / 60) < 1) {
                    Times = (useTime % 60) + "min";
                } else {
                    Times = (int) Math.floor(useTime / 60) + "h " + (useTime % 60) + "min";
                }
                System.out.println("    第" + ComNum + "/" + time + "次优化  stage=" + j + "   maxIndex=" + maxIndex
                        + "   maxResult=" + maxResult + "      useTime：" + Times);// startTime
            }

            // System.out.println("第"+ ComNum + "次优化 stage="+j+" maxIndex="+maxIndex+"
            // maxResult="+maxResult);
            // res_ope_chart=ope_chart[maxIndex];

        }
        ComNum++;
        return res_ope_chart;
    }

    /**
     *
     * @param x
     * @return
     */
    public int[][] combin(int x[][]) {
        int n = x.length;
        int v[][] = new int[(int) Math.pow(n, x[0].length)][x[0].length];
        // System.out.print(x[0].length);
        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < x[0].length; j++) {
                v[i][j] = x[(i / (int) Math.pow(n, x[0].length - (j + 1))) % n][j];
                // System.out.print((i / (int) Math.pow(n, x[0].length - (j+1))) % n);
            }
            // System.out.println();
        }
        /*
         * for(int i=0;i<v.length;i++){
         * System.out.print(i+"  ");
         * for(int j=0;j<v[0].length;j++){
         * System.out.print(v[i][j]+"  ");
         * }
         * System.out.println("");
         * }
         */
        return v;
    }

    // public int[][] combin1(int x[][]){
    // int n=x.length;//离散次数
    // int v[][]=new int[(int) ((n-1)* x[0].length+1)][x[0].length];//new int[(int)
    // Math.pow(n, x[0].length)][x[0].length]
    //// System.out.print(x[0].length);
    // for(int i=0;i<v.length;i++){//组合数
    // for(int j=0;j<x[0].length;j++){//水库数
    // v[i][j] = 1;//x[(i / (int) Math.pow(n, x[0].length - (j+1))) % n][j]
    // if (i==j){
    // v[i][j] = 0;
    // }else if ((i-x[0].length)==j){
    // v[i][j] = 2;
    // }else {
    // v[i][j] = 1;
    // }
    //
    //// System.out.print(v[i][j]+" ");
    //
    // }
    //// System.out.println();
    // }
    // /*for(int i=0;i<v.length;i++){
    // System.out.print(i+" ");
    // for(int j=0;j<v[0].length;j++){
    // System.out.print(v[i][j]+" ");
    // }
    // System.out.println("");
    // }*/
    // return v;
    // }
    public int[][] combin2(int x[][], int num, int MaxNum[]) {
        int n = x.length;// 离散次数
        int v[][] = new int[n][x[0].length];
        // System.out.print(x[0].length);
        for (int i = 0; i < v.length; i++) {// 组合数
            for (int j = 0; j < x[0].length; j++) {// 水库数
                if (j < num) {
                    v[i][j] = MaxNum[j];
                } else if (j == num) {
                    v[i][num] = i;
                } else {
                    v[i][j] = 1;
                }
                // System.out.print(v[i][j]+" ");
            }
            // System.out.println();
        }
        /*
         * for(int i=0;i<v.length;i++){
         * System.out.print(i+"  ");
         * for(int j=0;j<v[0].length;j++){
         * System.out.print(v[i][j]+"  ");
         * }
         * System.out.println("");
         * }
         */
        return v;
    }

    /**
     *
     * @param max
     * @param value
     * @param min
     * @param steplength
     * @param n
     * @return
     */
    public double[][] disStage(double max[], double value[], double min[], double steplength[], int n) {
        int len = value.length;
        double disStage[][] = new double[len][n];
        for (int i = 0; i < len; i++) {
            disStage[i] = CommonFun.disLev(max[i], min[i], value[i], steplength[i], n);
            // float dis[]=CommonFun.disLev(max,min,value[i],steplength,3);
            // disStage[i]=MathFun.getIndependent(dis);
        }
        int R[][] = new int[n][len];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < len; j++) {
                R[i][j] = i;
            }
        }
        int pos[][] = combin(R);
        // Vector vec=new Vector();
        double result[][] = new double[pos.length][];
        for (int i = 0; i < pos.length; i++) {
            // System.out.println(i);
            double record[] = new double[pos[i].length];
            for (int j = 0; j < pos[i].length; j++) {
                record[j] = disStage[j][pos[i][j]];
                // System.out.print( record[j]+" ");
            }
            // System.out.println();
            /*
             * for(int j=0;j<record.length-1;j++){
             * if(record[j]<record[j+1]){
             * record[j+1]=record[j];
             * }
             * }
             */
            result[i] = record;
            /*
             * for(int j=0;j<pos[i].length;j++){
             * System.out.print("   "+result[i][j]);
             * }
             * System.out.println();
             */
            /* vec.addElement(record); */
        }
        return result;
    }

    public double[][] disStage1(double max[], double value[], double min[], double steplength[], int n, int q,
            int maxnum[]) {// 线性组合
        int len = value.length;
        double disStage[][] = new double[len][n];
        for (int i = 0; i < len; i++) {
            disStage[i] = CommonFun.disLev(max[i], min[i], value[i], steplength[i], n);// ------------步长转为具体流量
            // float dis[]=CommonFun.disLev(max,min,value[i],steplength,3);
            // disStage[i]=MathFun.getIndependent(dis);
        }
        int R[][] = new int[n][len];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < len; j++) {
                R[i][j] = i;
            }
        }
        int pos[][] = combin2(R, q, maxnum);// pos[组合数][水库数]
        // Vector vec=new Vector();
        double result[][] = new double[pos.length][];
        for (int i = 0; i < pos.length; i++) {
            // System.out.println(i);
            double record[] = new double[pos[i].length];
            for (int j = 0; j < pos[i].length; j++) {
                record[j] = disStage[j][pos[i][j]];
                // System.out.print( record[j]+" ");
            }
            // System.out.println();
            /*
             * for(int j=0;j<record.length-1;j++){
             * if(record[j]<record[j+1]){
             * record[j+1]=record[j];
             * }
             * }
             */
            result[i] = record;
            /*
             * for(int j=0;j<pos[i].length;j++){
             * System.out.print("   "+result[i][j]);
             * }
             * System.out.println();
             */
            /* vec.addElement(record); */
        }
        return result;
    }

    // CalculateBean calbean=new CalculateBean();
    // BasicResBean basicbeans[]=new BasicResBean[2];
    public double fitnessDPSA(double schedul_lines[][], CalculateBean calbean, BasicResBean basicbeans[], int startK,
            int saves, String OutputName) {

        // double fitness=simoptprc.simResfitness(schedul_lines, calbean,
        // basicbeans,startK,saves,OutputName);
        double fitness = simoptprc_SanXia.simResfitness_SanXia(schedul_lines, calbean, basicbeans, startK, saves,
                OutputName);
        double result = fitness;

        return result;
    }
}