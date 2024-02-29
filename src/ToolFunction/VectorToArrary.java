package ToolFunction;

import java.util.Vector;
import java.util.Date;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class VectorToArrary {
    public static String[] convertString(Vector vector)
  {
    if(vector.size()==0) return null;
    int num=vector.size();
    String[] str=new String[num];
    for(int i=0;i<num;i++){
      str[i]=(String)(vector.elementAt(i));
    }
    return str;
  }
  public static Date[] convertDate(Vector vector)
    {
      if(vector.size()==0) return null;
      int num=vector.size();
      Date[] str=new Date[num];
      for(int i=0;i<num;i++){
        str[i]=(Date)(vector.elementAt(i));
      }
      return str;
  }
  public static int[] convertInteger(Vector vector)
  {
    if(vector.size()==0) return null;
    int num=vector.size();
    int[] integer=new int[num];
    for(int i=0;i<num;i++){
      integer[i]=((Integer)(vector.elementAt(i))).intValue();
    }
    return integer;
  }

  public static long[] convertLong(Vector vector)
  {
    if(vector.size()==0) return null;
    int num=vector.size();
    long[] longer=new long[num];
    for(int i=0;i<num;i++){
      longer[i]=((Long)(vector.elementAt(i))).longValue();
    }
    return longer;
  }

  public  static float[] convertFloat(Vector vector)
  {
    if(vector.size()==0) return null;
    int num=vector.size();
    float[] fl=new float[num];
    for(int i=0;i<num;i++){
      fl[i]=((Float)(vector.elementAt(i))).floatValue();
    }
    return fl;
  }

  public static double[] convertDouble(Vector vector)
  {
    if(vector.size()==0) return null;
    int num=vector.size();
    double[] dl=new double[num];
    for(int i=0;i<num;i++){
      dl[i]=((Double)(vector.elementAt(i))).doubleValue();
    }
    return dl;
  }

  public static Object[] convertObject(Vector vector)
  {
      if(vector.size()==0) return null;
      return vector.toArray();
  }

}
