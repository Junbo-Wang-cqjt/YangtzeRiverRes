package ToolFunction;

import java.util.*;

/**
 * <p>基本函数集</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class MathFun {
  public static double insert2(double x0, double x[], double y[], int n) { //两点插值(double)
    if (n <= 1) {
      return 0;
    }
    int i;
    double y0, x1, x2;
    if (x[0] < x[1]) { //*x[] is ascending
      for (i = 0; i < n; i++) {
        if (x0 <= x[i]) {
          break;
        }
      }
    }
    else { //*x[] is descending
      for (i = 0; i < n; i++) {
        if (x0 >= x[i]) {
          break;
        }
      }
    }
    if (i >= n - 1) {
      i = n - 2;
    }
    else if (i <= 1) {
      i = 0;
    }
    else {
      i--;
    }
    x1 = x[i];
    x2 = x[i + 1];
    if (x1 == x2) {
      return y[i];
    }
    y0 = y[i] + (x0 - x1) / (x2 - x1) * (y[i + 1] - y[i]);

    return y0;
  }

  public static double insert3(double x0, double x[], double y[], int n) { //三点插值(double)
    int i;
    double y0, x1, x2, x3;
    if (x[0] < x[1]) { //x[] is ascending
      for (i = 0; i < n; i++) {
        if (x0 <= x[i]) {
          break;
        }
      }
    }
    else { //x[] is descending
      for (i = 0; i < n; i++) {
        if (x0 >= x[i]) {
          break;
        }
      }
    }
    if (i >= n - 2) {
      i = n - 3;
    }
    else if (i <= 1) {
      i = 0;
    }
    else if ( (x0 - x[i - 1]) < (x[i] - x0)) {
      i -= 2;
    }
    else {
      i -= 1;
    }
    x1 = x[i];
    x2 = x[i + 1];
    x3 = x[i + 2];
    if (x1 == x2 && x2 != x3) {
      return (y[i + 2] - y[i + 1]) / (x3 - x2) * (x0 - x2) + y[i + 1];
    }
    if (x1 != x2 && x2 == x3) {
      return (y[i + 1] - y[i]) / (x2 - x1) * (x0 - x1) + y[i];
    }
    if (x1 != x2 && x2 == x3) {
      return y[i];
    }
    y0 = (x0 - x2) * (x0 - x3) / ( (x1 - x2) * (x1 - x3)) * y[i];
    y0 += (x0 - x1) * (x0 - x3) / ( (x2 - x1) * (x2 - x3)) * y[i + 1];
    y0 += (x0 - x1) * (x0 - x2) / ( (x3 - x1) * (x3 - x2)) * y[i + 2];
    return y0;
  }

  public static float insert2(float x0, float x[], float y[], int n) { //两点插值(float)
    if (n <= 1) {
      return 0;
    }
    int i;
    float y0, x1, x2;
    if (x[0] < x[1]) { //*x[] is ascending
      for (i = 0; i < n; i++) {
        if (x0 <= x[i]) {
          break;
        }
      }
    }
    else { //*x[] is descending
      for (i = 0; i < n; i++) {
        if (x0 >= x[i]) {
          break;
        }
      }
    }
    if (i >= n - 1) {
      i = n - 2;
    }
    else if (i <= 1) {
      i = 0;
    }
    else {
      i--;
    }
    x1 = x[i];
    x2 = x[i + 1];
    if (x1 == x2) {
      return y[i];
    }
    y0 = y[i] + (x0 - x1) / (x2 - x1) * (y[i + 1] - y[i]);

    return y0;
  }

  public static float insert3(float x0, float x[], float y[], int n) { //三点插值(float)
    int i;
    float y0, x1, x2, x3;
    if (x[0] < x[1]) { //x[] is ascending
      for (i = 0; i < n; i++) {
        if (x0 <= x[i]) {
          break;
        }
      }
    }
    else { //x[] is descending
      for (i = 0; i < n; i++) {
        if (x0 >= x[i]) {
          break;
        }
      }
    }
    if (i >= n - 2) {
      i = n - 3;
    }
    else if (i <= 1) {
      i = 0;
    }
    else if ( (x0 - x[i - 1]) < (x[i] - x0)) {
      i -= 2;
    }
    else {
      i -= 1;
    }
    x1 = x[i];
    x2 = x[i + 1];
    x3 = x[i + 2];
    if (x1 == x2 && x2 != x3) {
      return (y[i + 2] - y[i + 1]) / (x3 - x2) * (x0 - x2) + y[i + 1];
    }
    if (x1 != x2 && x2 == x3) {
      return (y[i + 1] - y[i]) / (x2 - x1) * (x0 - x1) + y[i];
    }
    if (x1 != x2 && x2 == x3) {
      return y[i];
    }
    y0 = (x0 - x2) * (x0 - x3) / ( (x1 - x2) * (x1 - x3)) * y[i];
    y0 += (x0 - x1) * (x0 - x3) / ( (x2 - x1) * (x2 - x3)) * y[i + 1];
    y0 += (x0 - x1) * (x0 - x2) / ( (x3 - x1) * (x3 - x2)) * y[i + 2];
    return y0;
  }

  public static float insert_zj2(float x, float c[], float d[]) {
    float t = 0;
    for (int i = 0; i < c.length - 1; i++) {
      if ( (x > c[i]) & (x < c[i + 1])) {
        t = ( ( (x - c[i]) / (c[i + 1] - c[i])) * (d[i + 1] - d[i])) +
            d[i];
      }
      else if ( (x < c[i]) & (x > c[i + 1])) {
        t = ( ( (x - c[i]) / (c[i + 1] - c[i])) * (d[i + 1] - d[i])) +
            d[i];
      }
      else if (x == c[i]) {
        t = d[i];
      }
    }
    return t;
  }

  public static double insert_zj2(double x, double c[], double d[]) {
    double t = 0;
    for (int i = 0; i < c.length - 1; i++) {
      if ( (x > c[i]) & (x < c[i + 1])) {
        t = ( ( (x - c[i]) / (c[i + 1] - c[i])) * (d[i + 1] - d[i])) +
            d[i];
      }
      else if ( (x < c[i]) & (x > c[i + 1])) {
        t = ( ( (x - c[i]) / (c[i + 1] - c[i])) * (d[i + 1] - d[i])) +
            d[i];
      }
      else if (x == c[i]) {
        t = d[i];
      }
    }
    return t;
  }

  public static int mod(int a, int b) { //取模
    if (b == 0) {
      return -1;
    }
    int c = a / b;
    int left = a - c * b;
    return left;
  }

  public static float maxValue(float[] q, int n) {
    if (q == null) {
      return 0;
    }
    float max = 0;
    for (int i = 0; i < n; i++) {
      if (q[i] > max) {
        max = q[i];
      }
    }
    return max;
  }

  public static int maxValue(int[] q, int n) {
    if (q == null) {
      return 0;
    }
    int max = 0;
    for (int i = 0; i < n; i++) {
      if (q[i] > max) {
        max = q[i];
      }
    }
    return max;
  }

  public static float minValue(float[] q, int n) {
    if (q == null) {
      return 0;
    }
    float min = 900000000;
    for (int i = 0; i < n; i++) {
      if (q[i] < min) {
        min = q[i];
      }
    }
    return min;
  }

  public static float sumValue(float[] q, int n) {
    if (q == null) {
      return 0;
    }
    float sum = 0f;
    for (int i = 0; i < n; i++) {
      sum += q[i];
    }
    return sum;
  }
  public static double sumValue(double[] q, int n) {
      if (q == null) {
        return 0;
      }
      double sum = 0f;
      for (int i = 0; i < n; i++) {
        sum += q[i];
      }
      return sum;
  }
  public static int sumValue(int[] q, int n) {
      if (q == null) {
        return 0;
      }
      int sum = 0;
      for (int i = 0; i < n; i++) {
        sum += q[i];
      }
      return sum;
  }
  public static int sumValue(int[] q,int begin, int n) {
      if (q == null) {
        return 0;
      }
      int sum = 0;
      if((0<=begin)&(begin<q.length)){
          for (int i = begin; i <begin+n; i++) {
              sum += q[i];
          }
      }else{
          return 0;
      }
      return sum;
  }
  public static float sumValue(float[] q,int begin, int n) {
      if (q == null) {
        return 0;
      }
      float sum = 0;
      if((0<=begin)&(begin<q.length)){
          for (int i = begin; i <begin+n; i++) {
              sum += q[i];
          }
      }else{
          return 0;
      }
      return sum;
  }
  public static double sumValue(double[] q,int begin, int n) {
      if (q == null) {
        return 0;
      }
      double sum = 0;
      if((0<=begin)&(begin<q.length)){
          for (int i = begin; i <begin+n; i++) {
              sum += q[i];
          }
      }else{
          return 0;
      }
      return sum;
  }


  public static float average(float[] q, int n) {
    if (q == null) {
      return 0;
    }
    float sum = 0f;
    for (int i = 0; i < n; i++) {
      sum += q[i];
    }
    float average = sum / n;
    return average;
  }
  public static double average(double[] q, int n) {
      if (q == null) {
        return 0;
      }
      double sum = 0f;
      for (int i = 0; i < n; i++) {
        sum += q[i];
      }
      double average = sum / n;
      return average;
  }
  public static float Variance(float[] q, int n) {
    if (q == null) {
      return 0;
    }
    float sum = 0f, sum1 = 0f;
    for (int i = 0; i < n; i++) {
      sum += q[i];
    }
    float average = sum / n;
    for (int j = 0; j < n; j++) {
      sum1 = sum1 + (q[j] - average) * (q[j] - average);
    }
    float variance = (float) Math.sqrt( (double) sum1);
    return variance;
  }

  public static double insertTwo(double x0, double[] x, double[] y) {
    int n = x.length;
    if (n <= 1) {
      return -10000;
    }
    int i;
    double y0, x1, x2;
    if (x[0] < x[1]) { //*x[] is ascending
      for (i = 0; i < n; i++) {
        if (x0 <= x[i]) {
          break;
        }
      }
    }
    else { //*x[] is descending
      for (i = 0; i < n; i++) {
        if (x0 >= x[i]) {
          break;
        }
      }
    }

    if (i >= n - 1) {
      i = n - 2;
    }
    else if (i <= 1) {
      i = 0;
    }
    else {
      i--;
    }
    x1 = x[i];
    x2 = x[i + 1];
    if (x1 == x2) {
      return y[i];
    }
    else {
      return y0 = y[i] + (x0 - x1) / (x2 - x1) * (y[i + 1] - y[i]);
    }
  }

  public static double getMin(double[] dou) {
    double tem = dou[0];
    for (int i = 0; i < dou.length; i++) {
      if (tem > dou[i]) {
        tem = dou[i];
      }
    }
    return tem;
  }

  public static float getMin(float[] dou) {
    float tem = dou[0];
    for (int i = 0; i < dou.length; i++) {
      if (tem > dou[i]) {
        tem = dou[i];
      }
    }
    return tem;
  }

  public static double getMax(double[] dou) {
    double tem = 0.0;
    int length = dou.length;
    tem = dou[0];
    for (int i = 0; i < length; i++) {
      if (tem < dou[i]) {
        tem = dou[i];
      }
    }
    return tem;
  }

  public static float getMax(float[] dou) {
    float tem = 0;
    int length = dou.length;
    tem = dou[0];
    for (int i = 0; i < length; i++) {
      if (tem < dou[i]) {
        tem = dou[i];
      }
    }
    return tem;
  }

  public static double getSum(double[] dou) {
    double tem = 0.0;
    for (int i = 0; i < dou.length; i++) {
      tem = tem + dou[i];
    }
    return tem;
  }

  public static double getMin(double[][] dou) {
    double tem = dou[0][0];
    for (int k = 0; k < dou.length; k++) {
      for (int i = 0; i < dou[k].length; i++) {
        if (tem > dou[k][i]) {
          tem = dou[k][i];
        }
      }
    }
    return tem;
  }

  public static float getMin(float[][] dou) {
    float tem = dou[0][0];
    for (int k = 0; k < dou.length; k++) {
      for (int i = 0; i < dou[k].length; i++) {
        if (tem > dou[k][i]) {
          tem = dou[k][i];
        }
      }
    }
    return tem;
  }

  public static int compare3(int a, int b, int c) {
    int result = 0;
    if ( (a >= b) && (a >= c)) {
      result = a;
    }
    else if ( (b >= a) && (b >= c)) {
      result = b;
    }
    else if ( (c >= a) && (c >= b)) {
      result = c;
    }
    return result;
  }

  public static float compare3(float a, float b, float c) {
    float result = 0;
    if ( (a >= b) && (a >= c)) {
      result = a;
    }
    else if ( (b >= a) && (b >= c)) {
      result = b;
    }
    else if ( (c >= a) && (c >= b)) {
      result = c;
    }
    return result;
  }

  public static float check(float a[]) {
    float result = 0;
    for (int i = 0; i < a.length; i++) {
      if (a[i] != 0) {
        result = 1;
      }
    }
    return result;
  }

  public static float ordering(float a, float b[]) {
    float result = -1;
    lab:
        for (int i = 0; i < b.length; i++) {
      if (a == b[i]) {
        result = i;
        break lab;
      }
    }
    return result;
  }

  public static int orderint(float a, float b[]) {
    int result = -1;
    lab:
        for (int i = 0; i < b.length; i++) {
      if (a == b[i]) {
        result = i;
        break lab;
      }
    }
    return result;
  }
  public static int order_Int(int a, int b[]) {
      int result = -1;
      lab:
          for (int i = 0; i < b.length; i++) {
        if (a == b[i]) {
          result = i;
          break lab;
        }
      }
      return result;
  }
  public static int orderint_double(double a, double b[]) {
    int result = -1;
    lab:
        for (int i = 0; i < b.length; i++) {
      if (a == b[i]) {
        result = i;
        break lab;
      }
    }
    return result;
  }

  public static int order_Double(double a, double b[]) {
      int result = -1;
      lab:
          for (int i = 0; i < b.length; i++) {
        if (a == b[i]) {
          result = i;
          break lab;
        }
      }
      return result;
  }

  public static float orderValue(float a, float b[]) {
    float result = -1;
    lab:
        for (int i = 0; i < b.length; i++) {
      if (a == b[i]) {
        result = b[i];
        break lab;
      }
    }
    return result;
  }
  /*剔除数组中的相同元素*/
    public static int[] getIndependent(int[] no_Fore) {
      Vector vector = new Vector();
      int[] end_NoFore = null;
      for(int i=0;i<no_Fore.length;i++){
        if(!vector.contains(no_Fore[i])){
          vector.addElement(no_Fore[i]);
        }
      }
      end_NoFore = convertInt(vector);
      return end_NoFore;
  }
  /*剔除数组中的相同元素并按从大到小次序输出*/
  public static int[] getOrder_Int_fall(int[] no_Fore) {
    Vector vector = new Vector();
    int[] end_NoFore = null;
    if (no_Fore != null) {
      for (int i = 0; i < no_Fore.length; i++) {
        vector.addElement(no_Fore[i]);
      }
    }
    if (vector.size() == 0) {
      return null;
    }
    for (int i = 0; i < vector.size() - 1; i++) {
      for (int j = i + 1; j < vector.size(); j++) {
        if ( ( (Integer) vector.elementAt(j)).intValue() ==
            ( (Integer) vector.elementAt(i)).intValue()) {
          vector.remove(j);
          j=j-1;
        }
      }
    }
    end_NoFore = convertInt(vector);
    //排序
    for (int i = 0; i < end_NoFore.length - 1; i++) {
      for (int j = i + 1; j < end_NoFore.length; j++) {
        if (end_NoFore[j] > end_NoFore[i]) {
          int temp = 0;
          temp = end_NoFore[j];
          end_NoFore[j] = end_NoFore[i];
          end_NoFore[i] = temp;
        }
      }
    }
    return end_NoFore;
  }
  /*剔除数组中的相同元素并按从大到小次序输出*/
  public static double[] getOrder_double_fall(double[] no_Fore) {
    Vector vector = new Vector();
    Vector record=new Vector();
    double[] end_NoFore = null;
    if (no_Fore != null) {
      for (int i = 0; i < no_Fore.length; i++) {
        vector.addElement(no_Fore[i]);
      }
    }
    if (vector.size() == 0) {
      return null;
    }
    for (int i = 0; i < vector.size() - 1; i++) {
      for (int j = i + 1; j < vector.size(); j++) {
        if ( ( (Double) vector.elementAt(j)).doubleValue()==
            ( (Double) vector.elementAt(i)).doubleValue()) {
          //record.addElement(j);
          vector.remove(j);
          j=j-1;
        }
      }
      /*for(int j=0;j<record.size();j++){
        int k=(Integer)record.elementAt(j);
        vector.remove(k);
      }*/

    }
    end_NoFore =convertDouble(vector);
    //排序
    for (int i = 0; i < end_NoFore.length - 1; i++) {
      for (int j = i + 1; j < end_NoFore.length; j++) {
        if (end_NoFore[j] > end_NoFore[i]) {
          double temp = 0;
          temp = end_NoFore[j];
          end_NoFore[j] = end_NoFore[i];
          end_NoFore[i] = temp;
        }
      }
    }
    return end_NoFore;
  }

  /*剔除数组中的相同元素并按从小到大次序输出*/
  public static float[] getOrder(float[] no_Fore) {
    Vector vector = new Vector();
    float[] end_NoFore = null;
    if (no_Fore != null) {
      for (int i = 0; i < no_Fore.length; i++) {
        vector.addElement(new Float(no_Fore[i]));
      }
    }
    if (vector.size() == 0) {
      return null;
    }
    for (int i = 0; i < vector.size() - 1; i++) {
      for (int j = i + 1; j < vector.size(); j++) {
        if ( ( (Float) vector.elementAt(j)).floatValue() ==
            ( (Float) vector.elementAt(i)).floatValue()) {
          vector.remove(j);
          j=j-1;
        }
      }
    }
    end_NoFore = convertFloat(vector);
    //排序
    for (int i = 0; i < end_NoFore.length - 1; i++) {
      for (int j = i + 1; j < end_NoFore.length; j++) {
        if (end_NoFore[j] < end_NoFore[i]) {
          float temp = 0;
          temp = end_NoFore[j];
          end_NoFore[j] = end_NoFore[i];
          end_NoFore[i] = temp;
        }
      }
    }
    return end_NoFore;
  }

  /*剔除两个数组中的相同元素并按从小到大次序输出*/
  public static float[] getOrder(float[] no_Fore, float[] temp_No_Fore) { //取两个数组的唯一值并排序
    Vector vector = new Vector();
    float[] end_NoFore = null;
    if (no_Fore != null) {
      for (int i = 0; i < no_Fore.length; i++) {
        vector.addElement(new Float(no_Fore[i]));
      }
    }
    if (temp_No_Fore != null) {
      for (int i = 0; i < temp_No_Fore.length; i++) {
        vector.addElement(new Float(temp_No_Fore[i]));
      }
    }
    if (vector.size() == 0) {
      return null;
    }
    //剔除相同元素
    for (int i = 0; i < vector.size() - 1; i++) {
      for (int j = i + 1; j < vector.size(); j++) {
        if ( ( (Float) vector.elementAt(j)).floatValue() ==
            ( (Float) vector.elementAt(i)).floatValue()) {
          vector.remove(j);
          j=j-1;
        }
      }
    }
    end_NoFore = convertFloat(vector);
    //排序
    for (int i = 0; i < end_NoFore.length - 1; i++) {
      for (int j = i + 1; j < end_NoFore.length; j++) {
        if (end_NoFore[j] < end_NoFore[i]) {
          float temp = 0;
          temp = end_NoFore[j];
          end_NoFore[j] = end_NoFore[i];
          end_NoFore[i] = temp;
        }
      }
    }
    return end_NoFore;
  }

  /*对数组内数据从小到大次序输出*/
  public static float[] getSort(float[] no_NoFore) {
    //排序
    for (int i = 0; i < no_NoFore.length - 1; i++) {
      for (int j = i + 1; j < no_NoFore.length; j++) {
        if (no_NoFore[j] < no_NoFore[i]) {
          float temp = 0;
          temp = no_NoFore[j];
          no_NoFore[j] = no_NoFore[i];
          no_NoFore[i] = temp;
        }
      }
    }
    return no_NoFore;
  }
  public static int[] convertInt(Vector vector) {
    if (vector.size() == 0) {
      return null;
    }
    int num = vector.size();
    int[] fl = new int[num];
    for (int i = 0; i < num; i++) {
      fl[i] = ( (Integer) (vector.elementAt(i))).intValue();
    }
    return fl;
  }


  public static float[] convertFloat(Vector vector) {
      if (vector.size() == 0) {
        return null;
      }
      int num = vector.size();
      float[] fl = new float[num];
      for (int i = 0; i < num; i++) {
        fl[i] = ( (Float) (vector.elementAt(i))).floatValue();
      }
      return fl;
  }
  public static Date[] convertDate(Vector vector) {
      if (vector.size() == 0) {
        return null;
      }
      int num = vector.size();
      Date[] fl = new Date[num];
      for (int i = 0; i < num; i++) {
        fl[i] =(Date) (vector.elementAt(i));
      }
      return fl;
  }

  public static double[] convertDouble(Vector vector) {
      if (vector.size() == 0) {
        return null;
      }
      int num = vector.size();
      double[] fl = new double[num];
      for (int i = 0; i < num; i++) {
        fl[i] = ( (Double)(vector.elementAt(i))).doubleValue();
      }
      return fl;
  }

  public static float[] combArray_signal(float[] a, float[] b, float[] c) { //合并3个数组
    Vector vector = new Vector();
    float[] end_NoFore = null;
    if (a != null) {
      for (int i = 0; i < a.length; i++) {
        vector.addElement(new Float(a[i]));
      }
    }
    if (b != null) {
      for (int i = 0; i < b.length; i++) {
        vector.addElement(new Float(b[i]));
      }
    }
    if (c != null) {
      for (int i = 0; i < c.length; i++) {
        vector.addElement(new Float(c[i]));
      }
    }
    if (vector.size() == 0) {
      return null;
    }
    end_NoFore = convertFloat(vector);
    return end_NoFore;
  }

  public static float[] combArray2(float[] a, float[] b) { //合并两个数组
    Vector vector = new Vector();
    float[] end_NoFore = null;
    if (a != null) {
      for (int i = 0; i < a.length; i++) {
        vector.addElement(new Float(a[i]));
      }
    }
    if (b != null) {
      for (int i = 0; i < b.length; i++) {
        vector.addElement(new Float(b[i]));
      }
    }
    if (vector.size() == 0) {
      return null;
    }
    end_NoFore = convertFloat(vector);
    return end_NoFore;
  }

  public static float[] removeArray(float a[], float k) {
    float result[] = null;
    Vector vector = new Vector();
    if (a != null) {
      for (int i = 0; i < a.length; i++) {
        vector.addElement(new Float(a[i]));
      }
    }
    for (int i = 0; i < vector.size(); i++) {
      if ( ( (Float) vector.elementAt(i)).floatValue() == k) {
        vector.remove(i);
      }
    }
    if (vector.size() == 0) {
      return null;
    }
    result = convertFloat(vector);
    return result;
  }

  public static float[] removeZero(float[] no_Fore) {
    Vector vector = new Vector();
    float[] end_NoFore = null;
    if (no_Fore != null) {
      for (int i = 0; i < no_Fore.length; i++) {
        vector.addElement(new Float(no_Fore[i]));
      }
    }
    if (vector.size() == 0) {
      return null;
    }
    for (int i = 0; i < vector.size() - 1; i++) {
      for (int j = i + 1; j < vector.size(); j++) {
        if ( ( (Float) vector.elementAt(j)).floatValue() ==
            ( (Float) vector.elementAt(i)).floatValue()) {
          vector.remove(j);
        }
      }
    }
    end_NoFore = convertFloat(vector);
    return end_NoFore;
  }
  public static Vector VectorCopy(Vector sample){
    Vector copy=new Vector();
    for(int i=0;i<sample.size();i++){
      copy.addElement(sample.elementAt(i));
    }
    return copy;
  }
  public static int[][] COLROWTranspose(int col[][]){
    int row[][]=new int[col[0].length][col.length];
    for(int i=0;i<col.length;i++){
      for(int j=0;j<col[0].length;j++){
        row[j][i]=col[i][j];
      }
    }
    return row;
  }
  public static float[][] COLROWTranspose(float col[][]){
      float row[][]=new float[col[0].length][col.length];
      for(int i=0;i<col.length;i++){
        for(int j=0;j<col[0].length;j++){
          row[j][i]=col[i][j];
        }
      }
      return row;
  }

}
