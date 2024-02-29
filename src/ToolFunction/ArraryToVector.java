package ToolFunction;
import java.util.Vector;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class ArraryToVector {
    public static Vector convert(String[] string)
 {
   if(string==null) return null;
   Vector vector=new Vector();
   for(int i=0;i<string.length;i++)
   {
     vector.addElement(string[i]);
   }
   return vector;
 }

 public static Vector convert(int[] integer)
 {
   if(integer==null) return null;
   Vector vector=new Vector();
   for(int i=0;i<integer.length;i++){
     vector.addElement(new Integer(integer[i]));
   }
   return vector;
 }

 public static Vector convert(long[] longer)
 {
   if(longer==null) return null;
   Vector vector=new Vector();
   for(int i=0;i<longer.length;i++){
     vector.addElement(new Long(longer[i]));
   }
   return vector;
 }

 public static Vector convert(float[] fl)
 {
   if(fl==null) return null;
   Vector vector=new Vector();
   for(int i=0;i<fl.length;i++){
     vector.addElement(new Float(fl[i]));
   }
   return vector;
 }

 public static Vector convert(double[] dl)
 {
   if(dl==null) return null;
   Vector vector=new Vector();
   for(int i=0;i<dl.length;i++){
     vector.addElement(new Double(dl[i]));
   }
   return vector;
 }

 public static Vector convert(Object[] dl)
 {
   if(dl==null) return null;
   Vector vector=new Vector();
   for(int i=0;i<dl.length;i++){
     vector.addElement(dl[i]);
   }
   return vector;
 }

 public static Object[] remove(Object[] dl,int index)
 {
     Vector vec=ArraryToVector.convert(dl);
     vec.remove(index);
     return VectorToArrary.convertObject(vec);
 }

 public static Object[] add(Object[] dl,int index,Object object)
 {
     Vector vec=ArraryToVector.convert(dl);
     vec.insertElementAt(object,index);
     return VectorToArrary.convertObject(vec);
 }
 public static Object[] add(Object[] dl,int index,Object[] object)
 {
     Vector vec=ArraryToVector.convert(dl);
     for(int i=0;i<object.length;i++){
         vec.insertElementAt(object[i], index+i);
     }
     return VectorToArrary.convertObject(vec);
 }

 public static Object[] add(int[] dl,int index,int[] object)
 {
     Vector vec=ArraryToVector.convert(dl);
     for(int i=0;i<object.length;i++){
         vec.insertElementAt(new Integer(object[i]), index+i);
     }
     return VectorToArrary.convertObject(vec);
 }

}
