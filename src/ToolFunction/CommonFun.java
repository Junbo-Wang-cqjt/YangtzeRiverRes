package ToolFunction;

import java.io.ObjectOutputStream;
import javax.swing.JTable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.text.DecimalFormat;
import java.io.ObjectInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Vector;
import java.math.BigDecimal;
import Jama.Matrix;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class CommonFun {
    /*public float[] getAverageP(AllCompRainCode allCompRainCode) {
        int numP = allCompRainCode.rainDay[0].dt.length;
        float[] averageP = new float[numP];
        for(int i= 0;i<numP;i++){
            int number = 0;
            for(int j=0;j<allCompRainCode.idRainCode.length;j++){
                if (allCompRainCode.working[j] == 1 &&
                    allCompRainCode.rainDay[j].vacancy[i]) {
                    number++;
                }
            }
            float[] rain = new float[number];
            float[] weight = new float[number];
            int k=0;
            for(int j=0;j<allCompRainCode.idRainCode.length;j++){
                if (allCompRainCode.working[j] == 1 &&
                    allCompRainCode.rainDay[j].vacancy[i]) {
                    rain[k] = allCompRainCode.rainDay[j].p[i];
                    weight[k] = allCompRainCode.gageWeight[j];
                    k++;
                }
            }
            averageP[i] = CommonFun.getRainFall(rain,weight);
        }
        return averageP;
         }*/
    /*public static double DisFlow(double initVol,double endVol,double outflow){
         }*/
    public static float insertFrequce(float x0, float[] x, float[] y) {
        float q = 0;
        float max = MathFun.getMax(x);
        float min = MathFun.getMin(x);
        if (x0 < min) {
            q = MathFun.insert3(min, x, y, y.length);
        } else if (x0 > max) {
            q = MathFun.insert3(max, x, y, y.length);
        } else {
            q = MathFun.insert3(x0, x, y, y.length);
        }
        return q;
    }

    public static float Disflow(float[][] z_v, float Qin, float Qout,
                                float time, float unit, float StartWaterLevel) {
        float RegulateResult = 0;
        float discharge = Qin - Qout;
        float outVolume = discharge * time * 3600;
        float starReservoirVolume = MathFun.insert3(StartWaterLevel, z_v[0],
                z_v[1], z_v[0].length) + outVolume / unit;
        RegulateResult = MathFun.insert3(starReservoirVolume, z_v[1], z_v[0],
                                         z_v[0].length);
        return RegulateResult;
    }

    public static float MinDischarge(float[][] zhamen, float[][] Qdikong,
                                     int[] dischargemanner,
                                     float Level, float Qmax, float Qmin) {
        //������С��й����
        //Level����ʱ��ˮλ
        //QinForecast����ʱ��Ԥ������
        //ReferencePoint�����Ƶ�����й��
        //Qujian����������
        //Error�����
        float mindischarge = 0.0f, charge = 0;
        int zhamenNumber = dischargemanner[0];
        int DKFallNumber = dischargemanner[1];
        float dischargeone = MathFun.insert3(Level, Qdikong[0], Qdikong[1],
                                             Qdikong[0].length) *
                             DKFallNumber +
                             MathFun.insert3(Level, zhamen[0], zhamen[1],
                                             zhamen[0].length) *
                             zhamenNumber; //c��й�����ܳ����������й��������
        if (dischargeone <= Qmax) {
            charge = dischargeone;
        } else {
            charge = Qmax;
        }
        if (charge >= Qmin) {
            mindischarge = charge;
        } else {
            mindischarge = Qmin;
        }
        return mindischarge;
    }

    public static float rgkt(float[][] z_v, float[][] zhamen, float[][] dikong,
                             int[] dischargemanner, float qin, float Qmax,
                             float Qmin, float zstart, float timestep,
                             float unit) {
        //�������(float)
        //z_v��ˮ��ˮλ�����ݹ�ϵ����
        //z_q��ˮ��ˮλ��й����ϵ����
        //dischargemanne,��й��ʽ��dischargemanne[0],��ʾ�������豸��dischargemanne[1]����ʾ�豸��������
        //qinaverage��ʱ��n�ڵ�Ԥ�����ƽ����������λm3/s
        //zstart��ʱ��n����ˮλ����λm
        //timestep��ʱ��n��ʱ�γ�����λh
        //unit�����ݵ�λ
        float k1, k2, k3, k4;
        float s1, s2, s3, s4;
        float v1, v2, v3, v4;
        float z1, z2, z3, z4;
        float vstart, vend;
        float result = 0;
        vstart = MathFun.insert3(zstart, z_v[0], z_v[1], z_v[0].length) * unit;
        v1 = vstart;
        z1 = MathFun.insert3(v1 / unit, z_v[1], z_v[0], z_v[1].length);
        s1 = MinDischarge(zhamen, dikong, dischargemanner, z1, Qmax, Qmin);
        k1 = timestep * (qin - s1) * 3600;
        v2 = vstart + k1 / 2.0f;
        z2 = MathFun.insert3(v2 / unit, z_v[1], z_v[0], z_v[1].length);
        s2 = MinDischarge(zhamen, dikong, dischargemanner, z2, Qmax, Qmin);
        k2 = timestep * (qin - s2) * 3600;
        v3 = vstart + k2 / 2.0f;
        z3 = MathFun.insert3(v3 / unit, z_v[1], z_v[0], z_v[1].length);
        s3 = MinDischarge(zhamen, dikong, dischargemanner, z3, Qmax, Qmin);
        k3 = timestep * (qin - s3) * 3600;
        v4 = vstart + k3;
        z4 = MathFun.insert3(v4 / unit, z_v[1], z_v[0], z_v[1].length);
        s4 = MinDischarge(zhamen, dikong, dischargemanner, z4, Qmax, Qmin);
        k4 = timestep * (qin - s4) * 3600;
        vend = vstart + 1.0f / 6.0f * (k1 + 2.0f * (k2 + k3) + k4);
        result = MathFun.insert3(vend / unit, z_v[1], z_v[0], z_v[1].length);
        return result;
    }

    /**
     * //�������(float)
     * @param z_v float[][]ˮ��ˮλ�����ݹ�ϵ����
     * @param qin float �������
     * @param Qout float ��������
     * @param Qmax float ��������
     * @param Qmin float ��С������
     * @param zstart float ʱ��n����ˮλ����λm
     * @param timestep float ʱ��n��ʱ�γ�����λh
     * @param unit float ���ߵ�λ
     * @return floatdouble Qoutavg, double Qmax, double Qmin,
     */
    public static double rgkt_GenPower(double[][] z_v, double waterrate[][],
                                       double power, double Qinavg,
                                       double zstart, double timestep,
                                       double unit) {
        double k1, k2, k3, k4;
        double s1, s2, s3, s4;
        double v1, v2, v3, v4;
        double z1, z2, z3, z4;
        double vstart, vend;
        double rate = 0;
        double result = 0;
        vstart = MathFun.insert3(zstart, z_v[0], z_v[1], z_v[0].length) * unit;
        v1 = vstart;
        z1 = MathFun.insert3(v1 / unit, z_v[1], z_v[0], z_v[1].length);
        rate = MathFun.insert3(z1, waterrate[0], waterrate[1],
                               waterrate[1].length);
        k1 = timestep * 3600 * Qinavg - power * rate;
        v2 = vstart + k1 / 2.0f;
        z2 = MathFun.insert3(v2 / unit, z_v[1], z_v[0], z_v[1].length);
        rate = MathFun.insert3(z2, waterrate[0], waterrate[1],
                               waterrate[1].length);
        k2 = timestep * 3600 * Qinavg - power * rate;
        v3 = vstart + k2 / 2.0f;
        z3 = MathFun.insert3(v3 / unit, z_v[1], z_v[0], z_v[1].length);
        rate = MathFun.insert3(z3, waterrate[0], waterrate[1],
                               waterrate[1].length);
        k3 = timestep * 3600 * Qinavg - power * rate;
        v4 = vstart + k3;
        z4 = MathFun.insert3(v4 / unit, z_v[1], z_v[0], z_v[1].length);
        rate = MathFun.insert3(z4, waterrate[0], waterrate[1],
                               waterrate[1].length);
        k4 = timestep * 3600 * Qinavg - power * rate;
        vend = vstart + 1.0f / 6.0f * (k1 + 2.0f * (k2 + k3) + k4);
        result = MathFun.insert3(vend / unit, z_v[1], z_v[0], z_v[1].length);
        return result;
    }

    public static double TrialMethod(double[][] z_v, double waterrate[][],
                                     double power, double Qinavg,
                                     double zstart, double timestep,
                                     double unit) {
        double result = 0;

        return result;
    }

    public static double AvgDisCharge(double waterrate[][], double power,
                                      double startLev, double powernuit) {
        double rate = MathFun.insert3(startLev, waterrate[0], waterrate[1],
                                      waterrate[1].length);
        double Vol = power * powernuit * rate;
        return Vol;
    }

    public static float XLNL(float Z, float DiK[][], float YHD[][],
                             float dismanner[]) {
        float result = 0;
        float tempt1 = MathFun.insert3(Z, DiK[0], DiK[1], DiK[0].length);
        float tempt2 = MathFun.insert3(Z, YHD[0], YHD[1], YHD[0].length);
        result = tempt1 * dismanner[0] + tempt2 * dismanner[1];
        return result;
    }

    //��������ʱ���������
    public static float[] getQin_Comb(int lag_time_step, float Q_sub[],
                                      float Q_main[]) {
        float result[] = new float[Q_main.length];
        System.arraycopy(Q_main, 0, result, 0, Q_main.length);
        for (int i = lag_time_step; i < Q_main.length; i++) {
            result[i] = Q_sub[i - lag_time_step] + result[i];
        }
        return result;
    }

    //��ƽ������
    public static float getRainFall(float[] rain, float[] weight) {
        float[] aveWeight = CommonFun.getWeight(weight);
        float averageRain = 0;
        for (int i = 0; i < rain.length; i++) {
            averageRain += aveWeight[i] * rain[i];
        }
        return averageRain;
    }

    //����վȨ�ع�һ��
    private static float[] getWeight(float[] weight) {
        float[] aveWeight = new float[weight.length];
        float sum = 0;
        for (int i = 0; i < weight.length; i++) {
            sum += weight[i];
        }
        for (int i = 0; i < weight.length; i++) {
            aveWeight[i] = weight[i] / sum;
        }
        return aveWeight;
    }

    public static Date datePlusHour(Date date, int hour) { //��ָ����ʱ�����ָ����Сʱ�����õ�ʱ��
        Date result;
        Calendar time = Calendar.getInstance();
        time.setTime(date);
        time.add(Calendar.HOUR_OF_DAY, hour);
        result = time.getTime();
        return result;
    }

    public static Date datePlusDays(Date date, int days) { //��ָ����ʱ�����ָ�����������õ�ʱ��
        Date result;
        Calendar time = Calendar.getInstance();
        time.setTime(date);
        time.add(Calendar.DAY_OF_MONTH, days);
        //time.add(Calendar.DAY_OF_YEAR, days);
        result = time.getTime();
        return result;
    }

    //������ʼʱ��
    public static int getPeriods(Date dt_beg, Date dt_end, int time_step) {
        int periods = 0;
        if ((dt_end.getTime() - dt_beg.getTime()) >= 0) {
            periods = new Long((dt_end.getTime() - dt_beg.getTime()) /
                               (time_step * 3600 * 1000l)).intValue();
        }
        return periods;
    }

    public static String getFloodNoFromDt(Date dt) {
        String floodNo;
        String pattern = "00";
        DecimalFormat df = new DecimalFormat(pattern);

        Calendar time = Calendar.getInstance();
        time.setTime(dt);
        int year = time.get(Calendar.YEAR);
        int month = time.get(Calendar.MONTH) + 1;
        String mm = df.format(month);
        int day = time.get(Calendar.DAY_OF_MONTH);
        String dd = df.format(day);
        floodNo = year + mm + dd;
        return floodNo;
    }

    /**
     * ������ɢ
     * @param maxlev double  ��ɢ�������ֵ
     * @param minlev double  ��ɢ������Сֵ
     * @param n int  ��������ɢΪ���ٶ�
     * @return double[]������ɢ�����ֵ
     */
    public static double[] DisLev(double maxlev, double minlev, int n) {
        double result[] = new double[n + 1];
        double K = (maxlev - minlev) / n;
        for (int i = 0; i <= n; i++) {
            result[i] = minlev + K * i;
        }
        return result;
    }
    /**
     * ������ɢ
     * @param maxlev double  ��ɢ�������ֵ
     * @param minlev double  ��ɢ������Сֵ
     * @param n int  ��������ɢΪ���ٶ�
     * @return double[]������ɢ�����ֵ
     */
    public static double[] disLev(double maxlev, double minlev,double basevale, double steplength,int n) {
    	double result[] = new double[n];
        int N=(int)n/2;
        double K = steplength*N;
        for (int i = 0; i <n; i++) {
            result[i] = basevale+K-K * i;//����Ĭ��nȡ3
            if(result[i]>=maxlev){
                result[i]=maxlev;
            }
            if(result[i]<=minlev){
                result[i]=minlev;
            }
        }
        return result;
    }
    /**
     * ���ַ����ĳ�����չ��length
     * @param str
     * @param length
     * @return ��չ��ĳ���
     */
    public static String getString(String str, int length) {
        String result = "";
        if (str == null) {
            for (int i = 0; i < length; i++) {
                result += " ";
            }
        } else {
            result = str;
            for (int i = str.length(); i < length; i++) {
                result += " ";
            }
        }
        return result;
    }

    /**
     * ��ȡһ�����ж�����
     * @param year int ��
     * @param month int ��
     * @return int
     */
    public static int getMonthDays(int year, int month) {
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.YEAR, year);
        cal1.set(Calendar.MONTH, month - 1); //7��
        int days = cal1.getActualMaximum(Calendar.DATE);
        return days;
    }

    /**
     * ��ȡһ�����ж�����
     * @param year int ��
     * @param month int ��
     * @return int
     */
    public static int getMonthDays(Date date) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        int days = cal1.getActualMaximum(Calendar.DATE);
        return days;
    }

    /**
     * ��ȡһ����ݶ�����
     * @param year int ��
     * @param month int ��
     * @return int
     */
    public static int getYearDays(int year) {
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.YEAR, year);
        //cal1.set(Calendar.MONTH, month - 1); //7��
        int days = cal1.getActualMaximum(Calendar.DAY_OF_YEAR);
        return days;
    }

    /**
     * ��8:00ʱ��
     * @param dt
     * @return ĳһ����8:00ʱ��,С�ڵ���8��00��ȡǰһ��8��00
     */
    public static Date getStandardTime(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        if (cal.get(Calendar.HOUR_OF_DAY) <= 8) {
            cal.add(Calendar.DATE, -1);
        }
        cal.set(Calendar.HOUR_OF_DAY, 8);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * ��8:00ʱ��
     * @param dt
     * @return ĳһ����8:00ʱ��
     */
    public static Date getEightTime(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.set(Calendar.HOUR_OF_DAY, 8);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static int getXunNum(int day) {
        int XUN = -1;
        if (day < 11) {
            XUN = 1;
        } else if ((day >= 11) & (day < 21)) {
            XUN = 2;
        } else {
            XUN = 3;
        }
        return XUN;
    }

    public static int getXunNum(Date day) {
        Calendar time2 = Calendar.getInstance();
        time2.setTime(day);
        int i_day2 = time2.get(Calendar.DAY_OF_MONTH); //�õ���
        int XUN = -1;
        if (i_day2 < 11) {
            XUN = 1;
        } else if ((i_day2 >= 11) & (i_day2 < 21)) {
            XUN = 2;
        } else {
            XUN = 3;
        }
        return XUN;
    }
    public static int getXunDays(Date day) {
        Calendar time2 = Calendar.getInstance();
        time2.setTime(day);
        int i_day2 = time2.get(Calendar.DAY_OF_MONTH); //�õ���
        int month=time2.getActualMaximum(Calendar.DATE);
        int XUN = -1;
        if (i_day2 < 11) {
            XUN = 10;
        } else if ((i_day2 >= 11) & (i_day2 < 21)) {
            XUN = 10;
        } else {
            XUN = month-20;
        }
        return XUN;
    }

    public static Date getDT(Date dt, int MM, int dd, int HH) { //����6��1��8ʱ��ʱ��
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.set(Calendar.MONTH, MM);
        cal.set(Calendar.DAY_OF_MONTH, dd);
        cal.set(Calendar.HOUR_OF_DAY, HH);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static int daysAfter(Date dt_beg, Date dt_end) { //����ʱ����������
        int days = -1;
        if ((dt_end.getTime() - dt_beg.getTime()) >= 0) {
            days = new Long((dt_end.getTime() - dt_beg.getTime()) /
                            (24 * 3600 * 1000l)).intValue();
        }
        return days;
    }

    public static int hoursAfter(Date dt_beg, Date dt_end) { //����ʱ������Сʱ��
        int hours = -1;
        if ((dt_end.getTime() - dt_beg.getTime()) >= 0) {
            hours = new Long((dt_end.getTime() - dt_beg.getTime()) /
                             (3600 * 1000l)).intValue();
        }
        return hours;
    }

    public static ArrayList sort(ArrayList list) {
        ArrayList result = new ArrayList();
        ArrayList listBak = (ArrayList) list.clone();
        return result;
    }

    public static Object copyOneObject(Object object) { //���л�һ���������и���
        Object copyObject = null;
        try {
            ByteArrayOutputStream buf1 = new ByteArrayOutputStream();
            ObjectOutputStream out1 = new ObjectOutputStream(buf1);
            out1.writeObject(object);
            ObjectInputStream in1 = new ObjectInputStream(new
                    ByteArrayInputStream(buf1.toByteArray()));
            copyObject = in1.readObject();
        } catch (Exception e) {}
        return copyObject;
    }

    public static String[] convertDateToString(SimpleDateFormat formatter,
                                               Date[] dt) {
        if (dt == null) {
            return null;
        }
        int num = dt.length;
        String[] dtString = new String[num];
        for (int i = 0; i < num; i++) {
            dtString[i] = formatter.format(dt[i]);
        }
        return dtString;
    }


    public static Date[] convertStringToDate(SimpleDateFormat formatter,
                                             String[] dtString) throws
            Exception {
        if (dtString == null) {
            return null;
        }
        int num = dtString.length;
        Date[] dt = new Date[num];
        for (int i = 0; i < num; i++) {
            dt[i] = formatter.parse(dtString[i]);
        }
        return dt;
    }

    public static Date convertStringToDate(SimpleDateFormat formatter,
                                           String dtString) {
        if (dtString == null) {
            return null;
        }
        Date dt = null;
        try {
            dt = formatter.parse(dtString);
        } catch (Exception e) {}
        return dt;
    }

    public static boolean stopEditing(JTable table) {
        if (table.isEditing()) {
            int row = table.getEditingRow();
            int column = table.getEditingColumn();
            boolean open = table.getCellEditor(row, column).stopCellEditing(); //ֹͣ�༭
            if (!open) {
                return false;
            }
        }
        return true;
    }

    public static int[] getDistinct(Vector delete_No_Fore) { //��ȡһ�������е�Ψһֵ������
        int[] end_NoFore = null;
        if (delete_No_Fore.size() == 0) { //��ʾ�����㴦�ķ���û��
            end_NoFore = null;
        } else if (delete_No_Fore.size() == 1) {
            end_NoFore = (int[]) delete_No_Fore.elementAt(0); //�Ѿ�����
        } else { //ȡ�������ظ��ĵ�Ԫ�Ų�����
            Vector vector = new Vector();
            for (int j = 0; j < delete_No_Fore.size(); j++) {
                int[] end_Fore = (int[]) delete_No_Fore.elementAt(j);
                for (int i = 0; i < end_Fore.length; i++) {
                    vector.addElement(new Integer(end_Fore[i]));
                }
            }

            for (int i = 0; i < vector.size() - 1; i++) {
                for (int j = i + 1; j < vector.size(); j++) {
                    if (((Integer) vector.elementAt(j)).intValue() ==
                        ((Integer) vector.elementAt(i)).intValue()) {
                        vector.remove(j);
                    }
                }
            }
            end_NoFore = VectorToArrary.convertInteger(vector);
            //����
            for (int i = 0; i < end_NoFore.length - 1; i++) {
                for (int j = i + 1; j < end_NoFore.length; j++) {
                    if (end_NoFore[j] < end_NoFore[i]) {
                        int temp = 0;
                        end_NoFore[i] = end_NoFore[j];
                        end_NoFore[j] = temp;
                    }
                }
            }
        }
        return end_NoFore;
    }

    public static int[] getForeByNew(int[] no_Fore_Old, int[] no_Fore_New,
                                     boolean isOld) { //��ȡ��ʱ���ԭʼ����ķ��������������ﶼ�еķ�������ʱ��Ϊ׼
        int[] no_Fore_Old_Last = null;
        int[] no_Fore_New_Last = null;
        if (no_Fore_New == null && no_Fore_Old == null) {
            return null;
        } else if (no_Fore_New != null && no_Fore_Old == null) {
            no_Fore_New_Last = no_Fore_New;
            no_Fore_Old_Last = null;
        } else if (no_Fore_New == null && no_Fore_Old != null) { //���ñ���ı�����һ���з���
            no_Fore_Old_Last = no_Fore_Old;
            no_Fore_New_Last = null;
        } else { //���߶���Ϊ��
            Vector vec = new Vector();
            for (int j = 0; j < no_Fore_Old.length; j++) {
                int k = 0;
                for (k = 0; k < no_Fore_New.length; k++) {
                    if (no_Fore_New[k] == no_Fore_Old[j]) {
                        break;
                    }
                }
                if (k == no_Fore_New.length) { //���˵���÷���û����ͬ��
                    vec.add(new Integer(no_Fore_Old[j]));
                }
            }
            if (vec.size() == 0) { //˵��ԭ���ķ����������޸�״̬
                no_Fore_Old_Last = null;
                no_Fore_New_Last = no_Fore_New;
            } else {
                no_Fore_Old_Last = VectorToArrary.convertInteger(vec);
                no_Fore_New_Last = no_Fore_New;
            }
        } //end if
        if (isOld) {
            return no_Fore_Old_Last;
        } else {
            return no_Fore_New_Last;
        }
    }

    public static int[] getDistinct(int[] no_Fore, int[] temp_No_Fore) { //ȡ���������Ψһֵ������
        Vector vector = new Vector();
        int[] end_NoFore = null;
        if (no_Fore != null) {
            for (int i = 0; i < no_Fore.length; i++) {
                vector.addElement(new Integer(no_Fore[i]));
            }
        }
        if (temp_No_Fore != null) {
            for (int i = 0; i < temp_No_Fore.length; i++) {
                vector.addElement(new Integer(temp_No_Fore[i]));
            }
        }
        if (vector.size() == 0) {
            return null;
        }
        for (int i = 0; i < vector.size() - 1; i++) {
            for (int j = i + 1; j < vector.size(); j++) {
                if (((Integer) vector.elementAt(j)).intValue() ==
                    ((Integer) vector.elementAt(i)).intValue()) {
                    vector.remove(j);
                }
            }
        }
        end_NoFore = VectorToArrary.convertInteger(vector);
        //����
        for (int i = 0; i < end_NoFore.length - 1; i++) {
            for (int j = i + 1; j < end_NoFore.length; j++) {
                if (end_NoFore[j] < end_NoFore[i]) {
                    int temp = 0;
                    temp = end_NoFore[j];
                    end_NoFore[j] = end_NoFore[i];
                    end_NoFore[i] = temp;
                }
            }
        }
        return end_NoFore;
    }

    public static String[] getDistinct(String[] user, String[] temp_User) { //ȡ���������Ψһֵ������
        Vector vector = new Vector();
        if (user != null) {
            for (int i = 0; i < user.length; i++) {
                vector.addElement(user[i]);
            }
        }
        if (temp_User != null) {
            for (int i = 0; i < temp_User.length; i++) {
                vector.addElement(temp_User[i]);
            }
        }
        if (vector.size() == 0) {
            return null;
        }
        for (int i = 0; i < vector.size() - 1; i++) {
            for (int j = i + 1; j < vector.size(); j++) {
                if (((String) vector.elementAt(j)).equals((String) vector.
                        elementAt(i))) {
                    vector.remove(j);
                }
            }
        }
        return VectorToArrary.convertString(vector);
    }

    public static int createPlanFore(int noFore, int noPlan) { //����Ԥ���͵��ȵĺϲ�������
        String temp = "-1";
        if (noFore > 9) {
            temp = "" + noPlan + "" + noFore;
        } else {
            temp = "" + noPlan + "0" + noFore;
        }
        return Integer.parseInt(temp);
    }

    public static int getNOPlanOrFore(int noPlanFore, boolean isFore) { //108��ʾ���ȷ�����1��Ԥ��������8��118��ʾ���ȷ�����1��Ԥ��������18��1118��ʾ���ȷ�����11��Ԥ��������18, ��ȡԤ�������Ż���ȷ�����
        if (noPlanFore < 100) {
            if (isFore) {
                return noPlanFore;
            } else {
                return 0;
            }
        }
        String temp = "" + noPlanFore;
        int noPlan = -1;
        int noFore = -1;
        if (temp.length() == 3) {
            noPlan = Integer.parseInt(temp.substring(0, 1));
            if (temp.substring(1, 2).equals("0")) {
                noFore = Integer.parseInt(temp.substring(2, 3));
            } else {
                noFore = Integer.parseInt(temp.substring(1, 3));
            }
        } else if (temp.length() == 4) {
            noPlan = Integer.parseInt(temp.substring(0, 2));
            if (temp.substring(2, 3).equals("0")) {
                noFore = Integer.parseInt(temp.substring(3, 4));
            } else {
                noFore = Integer.parseInt(temp.substring(2, 4));
            }
        }
        if (isFore) {
            return noFore;
        } else {
            return noPlan;
        }
    }

    public static boolean isExist(int x, int y[]) {
        if (y == null) {
            return false;
        }
        for (int i = 0; i < y.length; i++) {
            if (x == y[i]) {
                return true;
            }
        }
        return false;
    }

    public static float getTotalV(float[] q, int timeStep) { //��ȡ����
        if (q == null) {
            return 0;
        }
        float total = 0;
        for (int i = 0; i < q.length - 1; i++) {
            float temp = new Float(0.5 * (q[i] + q[i + 1]) * timeStep * 3600).
                         floatValue();
            total += temp;
        }
        return (float) (total / Math.pow(10, 8));
    }

    public static float[] getProduct(float[] p, float multiplier) { //һ�������ÿ������������ͬ����
        for (int i = 0; i < p.length; i++) {
            p[i] = p[i] * multiplier;
        }
        return p;
    }

    public static float getSum(float[] p) { //��ȡ����
        if (p == null) {
            return 0;
        }
        float all = 0;
        for (int i = 0; i < p.length; i++) {
            all = all + p[i];
        }
        return all;
    }

    public static float[] getExtendArrary(float[] p, java.util.Date beg_DT,
                                          java.util.Date end_DT
                                          , java.util.Date dt0,
                                          java.util.Date dt1, int time_step,
                                          float insert) { //��������,insertΪ�ӽ�ȥ����
        if (p == null) {
            return null;
        }
        int i_floods = getPeriods(beg_DT, end_DT, time_step);
        float[] temp = new float[i_floods];
        int num_beg = getPeriods(beg_DT, dt0, time_step) - 1;
        if (num_beg < 0) {
            num_beg = 0;
        }
        for (int i = 0; i < num_beg; i++) {
            temp[i] = insert;
        }
        for (int i = num_beg; i < num_beg + p.length; i++) {
            temp[i] = p[i - num_beg];
        }
        for (int i = num_beg + p.length; i < i_floods; i++) {
            temp[i] = insert;
        }
        return temp;
    }

    public static Date[] getDateArrary(Date firstDate, int timeStep, int length) { //��ȡ��һʱ�俪ʼ�����ʱ��
        Date[] dateArray = new Date[length];
        for (int i = 0; i < length; i++) {
            dateArray[i] = datePlusHour(firstDate, i * timeStep);
        }
        return dateArray;
    }

    private static Vector getStandard(Date[] dt, int timeStep, Vector temp) { //ΪgetStandardArray(Date[] dt,int timeStep,Object src)Ԥ������,����ʱ������Щ�㲻������
        Vector vec = new Vector();
        long init = dt[0].getTime();
        for (int i = 0; i < dt.length; i++) {
            if (dt[i] != null) { //û�ж����
                long num = dt[i].getTime() - init;
                float step = num * 1.0f / (timeStep * 3600 * 1000l);
                if (step == (long) step) {
                    vec.add(temp.elementAt(i));
                }
            }
        }
        return vec;
    }

    public static Object getStandardArray(Date[] dt, int timeStep, Object src) { //��ȡ��ʱ�ε�����
        Object dest = null;
        if (src == null) {
            return null;
        }
        if (src.getClass().isArray()) {
            if (src.getClass().equals(String[].class)) {
                String[] temp = (String[]) src;
                Vector vec = getStandard(dt, timeStep,
                                         ArraryToVector.convert(temp));
                dest = VectorToArrary.convertString(vec);
            } else if (src.getClass().equals(int[].class)) {
                int[] temp = (int[]) src;
                Vector vec = getStandard(dt, timeStep,
                                         ArraryToVector.convert(temp));
                dest = VectorToArrary.convertInteger(vec);
            } else if (src.getClass().equals(float[].class)) {
                float[] temp = (float[]) src;
                Vector vec = getStandard(dt, timeStep,
                                         ArraryToVector.convert(temp));
                dest = VectorToArrary.convertFloat(vec);
            } else if (src.getClass().equals(double[].class)) {
                double[] temp = (double[]) src;
                Vector vec = getStandard(dt, timeStep,
                                         ArraryToVector.convert(temp));
                dest = VectorToArrary.convertDouble(vec);
            } else if (src.getClass().equals(long[].class)) {
                long[] temp = (long[]) src;
                Vector vec = getStandard(dt, timeStep,
                                         ArraryToVector.convert(temp));
                dest = VectorToArrary.convertLong(vec);
            } else {
                Object[] temp = (Object[]) src;
                Vector vec = getStandard(dt, timeStep,
                                         ArraryToVector.convert(temp));
                dest = VectorToArrary.convertObject(vec);
                
            }
        } else {
            return null;
        }
        return dest;
    }

    public static String tranBD(double k,int jingdu) {
        String result;
        result = String.valueOf(new BigDecimal(k).setScale(jingdu,BigDecimal.ROUND_HALF_UP));
        return result;
    }

    public static double [] multiLinearEquat(double x[][],double y[]) { //,
        int n = 0; //������
        int m = 0; //
        //double yinzi[][]=new double[x.length][x[0].length+1];
        //for(int i=0;i<
        /*double x[][] = { {1, 0, 7}, {1, 1, 4}, {1, 2, 44}, {1, 3, 6}, {1, 4, 4},
                       {1, 5, 2}, {1, 6, 1}, {1, 7, 1}, {1, 8, 1}, {1, 9, 0}};
        double y[] = {12.1, 11.9, 10.2, 8, 7.7, 5.3, 7.9, 7.8, 5.5, 2.6};*/
        double yy[][] = new double[1][];
        yy[0] = y;
        Matrix test = new Matrix(x.length,x[0].length+1,1); //Matrix(yy);
        Matrix Y = new Matrix(yy);

        Matrix X = new Matrix(x);
        /*Matrix tt =test.plus(X);
        prin(tt);*/
        //prin(X);
        Matrix tempt1 = X.transpose();
        Matrix tempt2 = tempt1.times(X);

        Matrix te1 = tempt2.inverse();
        //prin(te1);
        Matrix tempt3 = tempt1.times(Y.transpose());
        //prin(tempt3);
        Matrix p = te1.times(tempt3);
        //prin(p);
        double xishu[][] = p.getArray();
        double coef[]=new double[x[0].length];
        for(int i=0;i<xishu.length;i++){
            coef[i]=xishu[i][0];
            //System.out.println(xishu[i][0]);
        }
        return coef;
    }

    /**
     * �������
     * @param d Matrix
     */
    public static void prin(Matrix d) {
        double x[][] = d.getArray();
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[i].length; j++) {
                System.out.print(x[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("................................");
    }

}
