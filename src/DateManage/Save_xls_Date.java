package DateManage;

import jxl.write.WritableWorkbook;
import jxl.write.WritableSheet;
import jxl.write.Label;
import java.io.IOException;
import javax.swing.JOptionPane;
import jxl.Workbook;
import java.io.OutputStream;
import jxl.write.WriteException;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.io.FileOutputStream;
import java.util.Hashtable;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class Save_xls_Date {
    public WritableWorkbook connect(String file) {
    WritableWorkbook book = null;
    try {
        //OutputStream is = new FileOutputStream(new File(file));//new File(file)
        //jxl.Workbook rwb = Workbook.getWorkbook(is);
        book = Workbook.createWorkbook(new File(file));
        //rs = book.getSheet(sheetnum);
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "连接excel失败，请检查！", "错误提示！",
                                      JOptionPane.WARNING_MESSAGE);
    }
    return book;
}

/**
 * 保存以数组存储形式存入excel表中
 * 不存储表头
 * @param file String
 * @param sheetnum int
 * @param sheetname String
 * @param coldate Object[][]
 * @return boolean
 * @throws WriteException
 * @throws IOException
 */
public boolean saveColsheets(String file, int sheetnum, String sheetname,double coldate[][]) throws WriteException,IOException {
    //jxl.write.WritableWorkbook book =Workbook
    //rwb = null;
    //sheet= null;
    try {
        OutputStream os = new FileOutputStream(file);
        //Workbook rwb = Workbook.getWorkbook(new File(file));rwb
        jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
        jxl.write.WritableSheet sheet[] = new WritableSheet[sheetnum];
        for (int i = 0; i < sheetnum; i++) {
            sheet[i] = book.createSheet(sheetname, sheetnum);
        }
        for (int i = 0; i < coldate.length; i++) {
            for (int j = 0; j < coldate[i].length; j++) {
                /*String tem = String.valueOf(coldate[i][j]);
                Label lab = new Label(0, j + 1, tem);*/
                jxl.write.Number doubleNum = new jxl.write.Number(j, i + 1,coldate[i][j]);
                sheet[i].addCell(doubleNum);
            }
        }
        book.write();
        book.close();

    } catch (IOException ex) {
    }
    boolean falge = false;

    return falge;
}
/**
 * 保存以数组存储形式存入excel表中
 * 不存储表头
 * @param file String
// * @param sheetnum int
// * @param sheetname String
// * @param coldate Object[][]
 * @return boolean
 * @throws WriteException
 * @throws IOException
 */
public boolean saveHashtableColsheets(String file,Hashtable hashtable) throws WriteException,IOException {
    try {
        int sheetnum=hashtable.size();
        OutputStream os = new FileOutputStream(file);
        //Workbook rwb = Workbook.getWorkbook(new File(file));rwb
        jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
        jxl.write.WritableSheet sheet[] = new WritableSheet[sheetnum];
        for (int i = 0; i < sheetnum; i++) {
            sheet[i] = book.createSheet("sheet"+(i+1), sheetnum);
        }
        for (int i = 0; i < sheetnum; i++) {
            Hashtable subhashtable = (Hashtable) hashtable.get(i + ""); //4
            for (int j = 0; j < subhashtable.size(); j++) {
                Hashtable subtable = (Hashtable) subhashtable.get(j + ""); //1248
                for (int k = 0; k < subtable.size(); k++) {
                    float date = (Float) subtable.get(k + "");
                    //jxl.write.Number doubleNum = new jxl.write.Number(j, k + 1,date);
                    jxl.write.Number doubleNum = new jxl.write.Number(k,
                            j + 1, date);
                    sheet[i].addCell(doubleNum);
                }
            }
        }
        book.write();
        book.close();
    } catch (IOException ex) {
    }
    boolean falge = false;
    return falge;
}
/**
 * 保存以数组存储形式存入excel表中
 * 不存储表头
 * @param file String
// * @param sheetnum int
// * @param sheetname String
// * @param coldate Object[][]
 * @return boolean
 * @throws WriteException
 * @throws IOException
 */
public boolean saveHashMapColsheet(String file,HashMap hashmap) throws WriteException,IOException {
	try {
	      //int sheetnum=hashmap.size();
	      OutputStream os = new FileOutputStream(file);
	      //Workbook rwb = Workbook.getWorkbook(new File(file));rwb
	      jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
	      jxl.write.WritableSheet sheet = book.createSheet("Sheet1", 0);
	      String titlename[]={"controlVolume","inflow","duringInflow","citysupplywater","argsupplywater",
	    		  "hydropower","hydropowerflow","spillwater","zoologysupplywater","EEE","zoologyquewater"};
	      for(int j=0;j<titlename.length;j++){
              String tem =titlename[j];
              Label lab = new Label(j, 0, tem);
              sheet.addCell(lab);
          }
	      for (int i = 0; i < titlename.length; i++) {
	          double subhashtable[] = (double[]) hashmap.get(titlename[i]); //
	          for (int k = 0; k < subhashtable.length; k++) {
	                  double date =subhashtable[k];
	                  //jxl.write.Number doubleNum = new jxl.write.Number(j, k + 1,date);
	                  jxl.write.Number doubleNum = new jxl.write.Number(i, k+1,date);
	                  sheet.addCell(doubleNum);
	              }

	      }
	      book.write();
	      book.close();
	  } catch (IOException ex) {
	  }
	  boolean falge = false;
	  return falge;
}
/**
* 保存以数组存储形式存入excel表中
* 不存储表头
* @param file String
//* @param sheetnum int
//* @param sheetname String
//* @param coldate Object[][]
* @return boolean
* @throws WriteException
* @throws IOException
*/
public boolean saveHashtableColsheet(String file,Hashtable hashtable) throws WriteException,IOException {
  try {
      int sheetnum=hashtable.size();
      OutputStream os = new FileOutputStream(file);
      //Workbook rwb = Workbook.getWorkbook(new File(file));rwb
      jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
      jxl.write.WritableSheet sheet = book.createSheet("Sheet1", 0);
      for (int i = 0; i < hashtable.size(); i++) {
          Hashtable subhashtable = (Hashtable) hashtable.get(i + ""); //
          for (int k = 0; k < subhashtable.size(); k++) {
                  double date = (Double) subhashtable.get(k + "");
                  //jxl.write.Number doubleNum = new jxl.write.Number(j, k + 1,date);
                  jxl.write.Number doubleNum = new jxl.write.Number(k,i + 1, date);
                  sheet.addCell(doubleNum);
              }

      }
      book.write();
      book.close();
  } catch (IOException ex) {
  }
  boolean falge = false;
  return falge;
}
/**
* 保存以数组存储形式存入excel表中
* 不存储表头
* @param file String
//* @param sheetnum int
//* @param sheetname String
//* @param coldate Object[][]
* @return boolean
* @throws WriteException
* @throws IOException
*/
public boolean saveHashtableDoubleColArray(String file,Hashtable hashtable) throws WriteException,IOException {
  try {
      int sheetnum=hashtable.size();
      OutputStream os = new FileOutputStream(file);
      //Workbook rwb = Workbook.getWorkbook(new File(file));rwb
      jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
      jxl.write.WritableSheet sheet = book.createSheet("Sheet1", 0);
      for (int i = 0; i < hashtable.size(); i++) {
          //Hashtable subhashtable = (Hashtable) hashtable.get(i + ""); //
          double subtalbe[]= (double [])hashtable.get(i + "");//
          for (int k = 0; k < subtalbe.length; k++) {
                  //double date = (Double) subhashtable.get(k + "");
                  //jxl.write.Number doubleNum = new jxl.write.Number(j, k + 1,date);
                  jxl.write.Number doubleNum = new jxl.write.Number(k,i + 1, subtalbe[k]);
                  sheet.addCell(doubleNum);
              }

      }
      book.write();
      book.close();
  } catch (IOException ex) {
  }
  boolean falge = false;
  return falge;
}
/**
* 保存以数组存储形式存入excel表中
* 不存储表头
* @param file String
//* @param sheetnum int
//* @param sheetname String
//* @param coldate Object[][]
* @return boolean
* @throws WriteException
* @throws IOException
*/
public boolean saveHashtableFloatColArray(String file,Hashtable hashtable) throws WriteException,IOException {
  try {
      int sheetnum=hashtable.size();
      OutputStream os = new FileOutputStream(file);
      //Workbook rwb = Workbook.getWorkbook(new File(file));rwb
      jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
      jxl.write.WritableSheet sheet = book.createSheet("Sheet1", 0);
      for (int i = 0; i < hashtable.size(); i++) {
          //Hashtable subhashtable = (Hashtable) hashtable.get(i + ""); //
          float subtalbe[]= (float [])hashtable.get(i + "");//
          for (int k = 0; k < subtalbe.length; k++) {
                  //double date = (Double) subhashtable.get(k + "");
                  //jxl.write.Number doubleNum = new jxl.write.Number(j, k + 1,date);
                  jxl.write.Number doubleNum = new jxl.write.Number(k,i + 1, subtalbe[k]);
                  sheet.addCell(doubleNum);
              }

      }
      book.write();
      book.close();
  } catch (IOException ex) {
  }
  boolean falge = false;
  return falge;
}
/**
* 保存以数组存储形式存入excel表中
* 不存储表头
* @param file String
//* @param sheetnum int
//* @param sheetname String
//* @param coldate Object[][]
* @return boolean
* @throws WriteException
* @throws IOException
*/
public boolean saveHashtableColTime(String file,Hashtable hashtable) throws WriteException,IOException {
  try {
      int sheetnum=hashtable.size();
      OutputStream os = new FileOutputStream(file);
      //Workbook rwb = Workbook.getWorkbook(new File(file));rwb
      jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
      jxl.write.WritableSheet sheet = book.createSheet("Sheet1", 0);
      for (int i = 0; i < hashtable.size(); i++) {
          Hashtable subhashtable = (Hashtable) hashtable.get(i + ""); //
          Date subtalbe= (Date)hashtable.get(i + "");//
          jxl.write.DateTime dateTime=new jxl.write.DateTime(0,i + 1, subtalbe);
          sheet.addCell(dateTime);
      }
      book.write();
      book.close();
  } catch (IOException ex) {
  }
  boolean falge = false;
  return falge;
}

/**
 * 保存以数组存储形式存入excel表中
 * 存储表头
 * @param file String
 //* @param sheetnum int
 //* @param sheetname String
 //* @param coldate Object[][]
 * @return boolean
 * @throws WriteException
 * @throws IOException
 */
public boolean saveHashtableColsheets(String file, Hashtable title,Hashtable hashtable) throws WriteException, IOException {
    try {
        int sheetnum = hashtable.size();
        OutputStream os = new FileOutputStream(file);
        jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
        jxl.write.WritableSheet sheet[] = new WritableSheet[sheetnum];
        for (int i = 0; i < sheetnum; i++) {
            sheet[i] = book.createSheet("sheet" + (i + 1), sheetnum);
        }
        for (int i = 0; i < sheetnum; i++) {
            Hashtable subtitle=(Hashtable)title.get(i+"");
            for(int j=0;j<subtitle.size();j++){
                String tem =(String)subtitle.get(j+"");
                Label lab = new Label(j, 0, tem);
                sheet[i].addCell(lab);
            }
        }
        for (int i = 0; i < sheetnum; i++) {
            Hashtable subhashtable = (Hashtable) hashtable.get(i + ""); //4
            for (int j = 0; j < subhashtable.size(); j++) {
                Hashtable subtable = (Hashtable) subhashtable.get(j + ""); //1248
                for (int k = 0; k < subtable.size(); k++) {
                    float date = (Float) subtable.get(k + "");
                    //jxl.write.Number doubleNum = new jxl.write.Number(j, k + 1,date);
                    jxl.write.Number doubleNum = new jxl.write.Number(k,j + 1, date);
                    sheet[i].addCell(doubleNum);
                }
            }
        }
        book.write();
        book.close();
    } catch (IOException ex) {
    }
    boolean falge = false;
    return falge;
}
public boolean saveColsheets(String file, int sheetnum, String sheetname,
                        float coldate[][]) throws WriteException,IOException {
//jxl.write.WritableWorkbook book =Workbook
//rwb = null;
//sheet= null;
try {
    OutputStream os = new FileOutputStream(file);
    //Workbook rwb = Workbook.getWorkbook(new File(file));rwb
    jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
    jxl.write.WritableSheet sheet[] = new WritableSheet[sheetnum];
    for (int i = 0; i < sheetnum; i++) {
        sheet[i] = book.createSheet(sheetname, sheetnum);
    }
    for (int i = 0; i < coldate.length; i++) {
        for (int j = 0; j < coldate[i].length; j++) {
            String tem = String.valueOf(coldate[i][j]);
            Label lab = new Label(0, j + 1, tem);
            sheet[i].addCell(lab);
        }
    }
    book.write();
    book.close();

} catch (IOException ex) {
}
boolean falge = false;

return falge;
}

public boolean saveColsheets(String file, int sheetnum,double coldate[][][]) throws WriteException,IOException {
    try {
        OutputStream os = new FileOutputStream(file);
        jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
        jxl.write.WritableSheet sheet[] = new WritableSheet[sheetnum];
        for (int i = 0; i < sheetnum; i++) {
            sheet[i] = book.createSheet("sheet"+i, sheetnum);
        }
        for (int i = 0; i < coldate.length; i++) {
            for (int j = 0; j < coldate[i].length; j++) {
                for(int k=0;k<coldate[i][j].length;k++){
                    /*String tem = String.valueOf(coldate[i][j][k]);
                    Label lab = new Label(k, j + 1, tem);
                    sheet[i].addCell(lab);*/
                    //double date = (Double) subtable.get(k + "");
                    jxl.write.Number doubleNum = new jxl.write.Number(k,j + 1, coldate[i][j][k]);
                    sheet[i].addCell(doubleNum);
                }
            }
        }
        book.write();
        book.close();
    } catch (IOException ex) {
    }
    boolean falge = false;
    return falge;
}

public boolean saveColsheets(String file, int sheetnum,float coldate[][][]) throws WriteException,IOException {
    try {
        OutputStream os = new FileOutputStream(file);
        jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
        jxl.write.WritableSheet sheet[] = new WritableSheet[sheetnum];
        for (int i = 0; i < sheetnum; i++) {
            sheet[i] = book.createSheet("sheet"+i, sheetnum);
        }
        for (int i = 0; i < coldate.length; i++) {
            for (int j = 0; j < coldate[i].length; j++) {
                for(int k=0;k<coldate[i][j].length;k++){
                    //String tem = String.valueOf(coldate[i][j][k]);
                    //Label lab = new Label(k, j + 1, tem);
                    jxl.write.Number doubleNum = new jxl.write.Number(k, j + 1, coldate[i][j][k]);
                    sheet[i].addCell(doubleNum);
                }
            }
        }
        book.write();
        book.close();
    } catch (IOException ex) {
    }
    boolean falge = false;
    return falge;
}

public boolean saveSheetsVecObjArray(String file, int sheetnum,Vector date) throws WriteException,IOException {
    try {
        OutputStream os = new FileOutputStream(file);
        jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
        jxl.write.WritableSheet sheet[] = new WritableSheet[sheetnum];
        for (int i = 0; i < sheetnum; i++) {
            sheet[i] = book.createSheet("sheet"+i, sheetnum);
        }
        for (int i = 0; i < date.size(); i++) {
            Vector vec=(Vector)date.elementAt(i);

            for (int j = 0; j < vec.size(); j++) {
                Object array[]=(Object[])vec.elementAt(j);
                for(int k=0;k<array.length;k++){
                    String tem = String.valueOf(array[k]);
                    Label lab = new Label(k, j + 1, tem);
                    sheet[i].addCell(lab);
                }
            }
        }
        book.write();
        book.close();
    } catch (IOException ex) {
    }
    boolean falge = false;
    return falge;
}
/**
 * 储存String 数组的Vector
 * @param file
 //* @param sheetnum
 * @param date
 * @return
 * @throws WriteException
 * @throws IOException
 */
public boolean saveSheetsVecStringArray(String file,Vector date) throws WriteException,IOException {
    try {
        OutputStream os = new FileOutputStream(file);
        jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
        jxl.write.WritableSheet sheet = book.createSheet("Sheet1", 0);
		for (int j = 0; j < date.size(); j++) {
			String array[] = (String[]) date.elementAt(j);
			for (int k = 0; k < array.length; k++) {
				String tem = String.valueOf(array[k]);
				Label lab = new Label(k, j + 1, tem);
				sheet.addCell(lab);
			}
		}
        book.write();
        book.close();
    } catch (IOException ex) {
    }
    boolean falge = false;
    return falge;
}

public boolean saveCol(String file,String sheetname,int sheetnum,int coldate[]) throws WriteException, IOException {
    //jxl.write.WritableWorkbook book =Workbook.
    //rwb = null;
    //sheet= null;
    boolean falge = false;
    OutputStream os = new FileOutputStream(file);
    jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
    jxl.write.WritableSheet sheet = book.createSheet(sheetname, sheetnum);

    for (int i = 0; i < coldate.length; i++) {
        String tem = String.valueOf(coldate[i]);
        Label lab = new Label(0, i + 1, tem);
        sheet.addCell(lab);
    }
    book.write();
    book.close();
    return falge;
}
public boolean saveCol(String file,String sheetname,int sheetnum,double coldate[]) throws WriteException, IOException {
    //jxl.write.WritableWorkbook book =Workbook.
    //rwb = null;
    //sheet= null;
    boolean falge = false;
    OutputStream os = new FileOutputStream(file);
    jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
    jxl.write.WritableSheet sheet = book.createSheet(sheetname, sheetnum);

    for (int i = 0; i < coldate.length; i++) {
        String tem = String.valueOf(coldate[i]);
        Label lab = new Label(0, i + 1, tem);
        sheet.addCell(lab);
    }
    book.write();
    book.close();
    return falge;
}
public boolean saveCol(String file,String sheetname,int sheetnum,float coldate[]) throws WriteException, IOException {
    //jxl.write.WritableWorkbook book =Workbook.
    //rwb = null;
    //sheet= null;
    boolean falge = false;
    OutputStream os = new FileOutputStream(file);
    jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
    jxl.write.WritableSheet sheet = book.createSheet(sheetname, sheetnum);

    for (int i = 0; i < coldate.length; i++) {
        String tem = String.valueOf(coldate[i]);
        Label lab = new Label(0, i + 1, tem);
        sheet.addCell(lab);
    }
    book.write();
    book.close();
    return falge;
}
public boolean saveCol(String file,int coldate[]) throws WriteException, IOException {
    //jxl.write.WritableWorkbook book =Workbook.
    //rwb = null;
    //sheet= null;
    boolean falge = false;
    OutputStream os = new FileOutputStream(file);
    jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
    jxl.write.WritableSheet sheet = book.createSheet("Sheet1", 0);

    for (int i = 0; i < coldate.length; i++) {
        String tem = String.valueOf(coldate[i]);
        Label lab = new Label(0, i + 1, tem);
        sheet.addCell(lab);
    }
    book.write();
    book.close();
    return falge;
}
public boolean saveCol(String file,double coldate[]) throws WriteException, IOException {
    //jxl.write.WritableWorkbook book =Workbook.
    //rwb = null;
    //sheet= null;
    boolean falge = false;
    OutputStream os = new FileOutputStream(file);
    jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
    jxl.write.WritableSheet sheet = book.createSheet("Sheet1", 0);

    for (int i = 0; i < coldate.length; i++) {
        String tem = String.valueOf(coldate[i]);
        Label lab = new Label(0, i + 1, tem);
        sheet.addCell(lab);
    }
    book.write();
    book.close();
    return falge;
}
public boolean saveCol(String file,float coldate[]) throws WriteException, IOException {
    //jxl.write.WritableWorkbook book =Workbook.
    //rwb = null;
    //sheet= null;
    boolean falge = false;
    OutputStream os = new FileOutputStream(file);
    jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
    jxl.write.WritableSheet sheet = book.createSheet("Sheet1", 0);

    for (int i = 0; i < coldate.length; i++) {
        /*String tem = String.valueOf(coldate[i]);
        Label lab = new Label(0, i + 1, tem);
        sheet.addCell(lab);*/
        jxl.write.Number doubleNum = new jxl.write.Number(0, i + 1,coldate[i]);
        sheet.addCell(doubleNum);
    }
    book.write();
    book.close();
    return falge;
}

/**
 * 保存以数组存储形式存入excel表中
 * 不存储表头
 * @param file String
 //* @param sheetnum int
 //* @param sheetname String
 * @param coldate Object[][]
 * @return boolean
 * @throws WriteException
 * @throws IOException
 */
public boolean saveCols(String file,double coldate[][]) throws WriteException,
        IOException {
    //jxl.write.WritableWorkbook book =Workbook.
    //rwb = null;
    //sheet= null;
    boolean falge = false;
    OutputStream os = new FileOutputStream(file);
    jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
    jxl.write.WritableSheet sheet = book.createSheet("Sheet1", 0);
    for (int i = 0; i < coldate.length; i++) {
        for (int j = 0; j < coldate[i].length; j++) {
            //String tem = String.valueOf(coldate[i][j]);
            //Label lab = new Label(i, j + 1, tem);
            //sheet.addCell(lab);
            jxl.write.Number doubleNum = new jxl.write.Number(i, j + 1, coldate[i][j]);
            sheet.addCell(doubleNum);
        }
    }
    book.write();
    book.close();
    return falge;
}
/**
 * 保存以数组存储形式存入excel表中
 * 不存储表头
 * @param file String
 //* @param sheetnum int
 //* @param sheetname String
 * @param coldate Object[][]
 * @return boolean
 * @throws WriteException
 * @throws IOException
 */
public boolean saveStringCols(String file,double coldate[][]) throws WriteException,
        IOException {
    //jxl.write.WritableWorkbook book =Workbook.
    //rwb = null;
    //sheet= null;
    boolean falge = false;
    OutputStream os = new FileOutputStream(file);
    jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
    jxl.write.WritableSheet sheet = book.createSheet("Sheet1", 0);
    for (int i = 0; i < coldate.length; i++) {
        for (int j = 0; j < coldate[i].length; j++) {
            String tem = String.valueOf(coldate[i][j]);
            Label lab = new Label(i, j + 1, tem);
            sheet.addCell(lab);
        }
    }
    book.write();
    book.close();
    return falge;
}
public boolean saveCols(String file,String sheetname,int sheetnum,double coldate[][]) throws WriteException,
        IOException {
    //jxl.write.WritableWorkbook book =Workbook.
    //rwb = null;
    //sheet= null;
    boolean falge = false;
    OutputStream os = new FileOutputStream(file);
    jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
    jxl.write.WritableSheet sheet = book.createSheet(sheetname, sheetnum);
    for (int i = 0; i < coldate.length; i++) {
        for (int j = 0; j < coldate[i].length; j++) {
            String tem = String.valueOf(coldate[i][j]);
            Label lab = new Label(i, j + 1, tem);
            sheet.addCell(lab);
        }
    }
    book.write();
    book.close();
    return falge;
}


/**
 * 保存以数组存储形式存入excel表中
 * 不存储表头
 * @param file String
 //* @param sheetnum int
 //* @param sheetname String
 * @param coldate Object[][]
 * @return boolean
 * @throws WriteException
 * @throws IOException
 */
public boolean saveCols(String file,float coldate[][]) throws WriteException,
        IOException {
    //jxl.write.WritableWorkbook book =Workbook.
    //rwb = null;
    //sheet= null;
    boolean falge = false;
    OutputStream os = new FileOutputStream(file);
    jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
    jxl.write.WritableSheet sheet = book.createSheet("Sheet1", 0);
    for (int i = 0; i < coldate.length; i++) {
        for (int j = 0; j < coldate[i].length; j++) {
            /*String tem = String.valueOf(coldate[i][j]);
            Label lab = new Label(i, j + 1, tem);*/
            jxl.write.Number doubleNum = new jxl.write.Number(i, j + 1, coldate[i][j]);
            sheet.addCell(doubleNum);
        }
    }
    book.write();
    book.close();
    return falge;
}
public boolean saveCols(String file,String sheetname,int sheetnum,float coldate[][]) throws WriteException,
        IOException {
    //jxl.write.WritableWorkbook book =Workbook.
    //rwb = null;
    //sheet= null;
    boolean falge = false;
    OutputStream os = new FileOutputStream(file);
    jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
    jxl.write.WritableSheet sheet = book.createSheet(sheetname, sheetnum);
    for (int i = 0; i < coldate.length; i++) {
        for (int j = 0; j < coldate[i].length; j++) {
            String tem = String.valueOf(coldate[i][j]);
            Label lab = new Label(i, j + 1, tem);
            sheet.addCell(lab);
        }
    }
    book.write();
    book.close();
    return falge;
}


/**
 * 保存以数组存储形式存入excel表中
 * 不存储表头
 * @param file String
 //* @param sheetnum int
 //* @param sheetname String
 * @param coldate Object[][]
 * @return boolean
 * @throws WriteException
 * @throws IOException
 */
public boolean saveCols(String file,int coldate[][]) throws WriteException, IOException {
    //jxl.write.WritableWorkbook book =Workbook.
    //rwb = null;
    //sheet= null;
    boolean falge = false;
    OutputStream os = new FileOutputStream(file);
    jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
    jxl.write.WritableSheet sheet = book.createSheet("Sheet1", 0);
    for (int i = 0; i < coldate.length; i++) {
        for (int j = 0; j < coldate[i].length; j++) {
            String tem = String.valueOf(coldate[i][j]);
            Label lab = new Label(i, j + 1, tem);
            sheet.addCell(lab);
        }
    }
    book.write();
    book.close();
    return falge;
}
public boolean saveCols(String file,String sheetname,int sheetnum,int coldate[][]) throws WriteException, IOException {
    //jxl.write.WritableWorkbook book =Workbook.
    //rwb = null;
    //sheet= null;
    boolean falge = false;
    OutputStream os = new FileOutputStream(file);
    jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
    jxl.write.WritableSheet sheet = book.createSheet(sheetname, sheetnum);
    for (int i = 0; i < coldate.length; i++) {
        for (int j = 0; j < coldate[i].length; j++) {
            String tem = String.valueOf(coldate[i][j]);
            Label lab = new Label(i, j + 1, tem);
            sheet.addCell(lab);
        }
    }
    book.write();
    book.close();
    return falge;
}



/**
 * 保存以数组存储形式存入excel表中
 * 不存储表头
 * @param file String
 //* @param sheetnum int
 //* @param sheetname String
 * @param coldate Object[][]
 * @return boolean
 * @throws WriteException
 * @throws IOException
 */
public boolean saveCols(String file,Object coldate[][]) throws WriteException,
        IOException {
    //jxl.write.WritableWorkbook book =Workbook.
    //rwb = null;
    //sheet= null;
    boolean falge = false;
    OutputStream os = new FileOutputStream(file);
    jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
    jxl.write.WritableSheet sheet = book.createSheet("Sheet1", 0);
    for (int i = 0; i < coldate.length; i++) {
        for (int j = 0; j < coldate[i].length; j++) {
            String tem = String.valueOf(coldate[i][j]);
            Label lab = new Label(i, j + 1, tem);
            sheet.addCell(lab);
        }
    }
    book.write();
    book.close();
    return falge;
}


/**
 *
 * @param file String
 * 保存文件路径
 * @param sheetnum int
 * 保存excel表的sheet位置
 * @param sheetname String
 * 保存sheet表的名字
 * @param rowhead Object[]
 * 表头名称
 * @return boolean
 * 保存成功返回TRUE
 */
public boolean saveRowHead(String file, int sheetnum, String sheetname,
                           Object rowhead[]) throws WriteException,
        IOException {
    boolean falge = false;
    OutputStream os = new FileOutputStream(file);
    jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
    jxl.write.WritableSheet sheet = book.createSheet(sheetname, sheetnum);
    for (int i = 0; i < rowhead.length; i++) {
        String tem = String.valueOf(rowhead[i]);
        Label lab = new Label(i, 0, tem);
        sheet.addCell(lab);
    }
    book.write();
    book.close();
    return falge;
}
/**
 *
 * @param file String
 * 保存文件路径
 * @param sheetnum int
 * 保存excel表的sheet位置
 * @param sheetname String
 * 保存sheet表的名字
 * @param rowhead Object[]
 * 表头名称
 * @return boolean
 * 保存成功返回TRUE
 */
public boolean saveRowHead(String file, int sheetnum, String sheetname,
                           String rowhead[]) throws WriteException,
        IOException {
    boolean falge = false;
    OutputStream os = new FileOutputStream(file);
    jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
    jxl.write.WritableSheet sheet = book.createSheet(sheetname, sheetnum);
    for (int i = 0; i < rowhead.length; i++) {
        String tem =rowhead[i];
        Label lab = new Label(i, 0, tem);
        sheet.addCell(lab);
    }
    book.write();
    book.close();
    return falge;
}
/**
 * 保存以Vector存储形式存入excel表中
 * 代存储表头
 * @param file String
 //* @param sheetnum int
 //* @param sheetname String
 //* @param rowhead Object[]
 * 存储数据表头的名称
 * @param Rowdate Vector
 * 需要存储的数据
 * @throws WriteException
 * @throws IOException
 */
public void saveArrayVEC_vec(String file,Object headname[],Vector Rowdate) throws WriteException, IOException {
    OutputStream os = new FileOutputStream(file);
    jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
    jxl.write.WritableSheet sheet = book.createSheet("Sheet1", 0);
    for (int i = 0; i < headname.length; i++) {
        String tem = String.valueOf(headname[i]);
        Label lab = new Label(i, 0, tem);
        sheet.addCell(lab);
    }
    for (int i = 0; i < Rowdate.size(); i++) {
        Vector tempt = (Vector) Rowdate.elementAt(i);
        for (int j = 0; j < tempt.size(); j++) {
            String tem = String.valueOf(tempt.elementAt(j));
            Label lab = new Label(j, i + 1, tem);
            sheet.addCell(lab);
        }
    }
    book.write();
    book.close();
    os.flush();
    os.close();
}
/**
 * 保存以Vector存储形式存入excel表中
 * 代存储表头
 * @param file String
 //* @param sheetnum int
 //* @param sheetname String
 //* @param rowhead Object[]
 * 存储数据表头的名称
 * @param Rowdate Vector
 * 需要存储的数据
 * @throws WriteException
 * @throws IOException
 */
public void saveRows(String file,String headname[],double Rowdate[][]) throws WriteException, IOException {
    OutputStream os = new FileOutputStream(file);
    jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
    jxl.write.WritableSheet sheet = book.createSheet("Sheet1", 0);
    for (int i = 0; i < headname.length; i++) {
        String tem = headname[i];
        Label lab = new Label(i, 0, tem);
        sheet.addCell(lab);
    }
    for (int i = 0; i < Rowdate.length; i++) {
        for (int j = 0; j < Rowdate[i].length; j++) {
            String tem = String.valueOf(Rowdate[i][j]);
            Label lab = new Label(j, i + 1, tem);
            sheet.addCell(lab);
        }
    }
    book.write();
    book.close();
    os.flush();
    os.close();
}
public void saveCols(String file,String headname[],double Colsdate[][]) throws WriteException, IOException {
    OutputStream os = new FileOutputStream(file);
    jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
    jxl.write.WritableSheet sheet = book.createSheet("Sheet1", 0);
    for (int i = 0; i < headname.length; i++) {
        String tem = headname[i];
        Label lab = new Label(i, 0, tem);
        sheet.addCell(lab);
    }
    for (int i = 0; i < Colsdate.length; i++) {
        for (int j = 0; j < Colsdate[i].length; j++) {
            //String tem = String.valueOf(Colsdate[i][j]);
            //Label lab = new Label(j, i + 1, tem);
            jxl.write.Number doubleNum = new jxl.write.Number(i,j + 1, Colsdate[i][j]);
            sheet.addCell(doubleNum);
        }
    }
    book.write();
    book.close();
    os.flush();
    os.close();
}

public void saveTwoCols(String file,String Colsdate1[],double Colsdate2[]) throws WriteException, IOException {
    OutputStream os = new FileOutputStream(file);
    jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
    jxl.write.WritableSheet sheet = book.createSheet("Sheet1", 0);
    for (int i = 0; i < Colsdate1.length; i++) {
        String tem = Colsdate1[i];
        Label lab = new Label(0, i, tem);
        sheet.addCell(lab);
    }
    for (int i = 0; i < Colsdate2.length; i++) {
    	jxl.write.Number doubleNum = new jxl.write.Number(1,i, Colsdate2[i]);
    	sheet.addCell(doubleNum);
    }
    book.write();
    book.close();
    os.flush();
    os.close();
}

public void saveRows(String file,String rowhead[],float Rowdate[][]) throws WriteException, IOException {
OutputStream os = new FileOutputStream(file);
jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
jxl.write.WritableSheet sheet = book.createSheet("Sheet1", 0);
for (int i = 0; i < rowhead.length; i++) {
    String tem = rowhead[i];
    Label lab = new Label(i, 0, tem);
    sheet.addCell(lab);
}
for (int i = 0; i < Rowdate.length; i++) {
    for (int j = 0; j < Rowdate[i].length; j++) {
        String tem = String.valueOf(Rowdate[i][j]);
        Label lab = new Label(j, i + 1, tem);
        sheet.addCell(lab);
    }
}
book.write();
book.close();
os.flush();
os.close();
}
public void saveRows(String file,String rowhead[],int Rowdate[][]) throws WriteException, IOException {
OutputStream os = new FileOutputStream(file);
jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
jxl.write.WritableSheet sheet = book.createSheet("Sheet1", 0);
for (int i = 0; i < rowhead.length; i++) {
    String tem = rowhead[i];
    Label lab = new Label(i, 0, tem);
    sheet.addCell(lab);
}
for (int i = 0; i < Rowdate.length; i++) {
    for (int j = 0; j < Rowdate[i].length; j++) {
        String tem = String.valueOf(Rowdate[i][j]);
        Label lab = new Label(j, i + 1, tem);
        sheet.addCell(lab);
    }
}
book.write();
book.close();
os.flush();
os.close();
}
/**
 * 保存以Vector存储形式存入excel表中
 * 代存储表头
 * @param file String
// * @param sheetnum int
// * @param sheetname String
// * @param rowhead Object[]
 * 存储数据表头的名称
 * @param Rowdate Vector
 * 需要存储的数据
 * @throws WriteException
 * @throws IOException
 */
public void saveArrayVEC_vec(String file,Vector headname,Vector Rowdate) throws WriteException, IOException {
    OutputStream os = new FileOutputStream(file);
    jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
    jxl.write.WritableSheet sheet = book.createSheet("Sheet1", 0);
    for (int i = 0; i < headname.size(); i++) {
        String tem = String.valueOf(headname.elementAt(i));
        Label lab = new Label(i, 0, tem);
        sheet.addCell(lab);
    }
    for (int i = 0; i < Rowdate.size(); i++) {
        Vector tempt = (Vector) Rowdate.elementAt(i);
        for (int j = 0; j < tempt.size(); j++) {
            String tem = String.valueOf(tempt.elementAt(j));
            Label lab = new Label(j, i + 1, tem);
            sheet.addCell(lab);
        }
    }
    book.write();
    book.close();
    os.flush();
    os.close();
}

/**
 * 保存以Vector存储形式存入excel表中
 * 代存储表头
 * @param file String
 //* @param sheetnum int
 //* @param sheetname String
 * @param rowhead Object[]
 * 存储数据表头的名称
 * @param Rowdate Vector
 * 需要存储的数据
 * @throws WriteException
 * @throws IOException
 */
public void saveArrayVec_Int(String file,Vector rowhead,Vector Rowdate) throws WriteException, IOException {
    OutputStream os = new FileOutputStream(file);
    jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
    jxl.write.WritableSheet sheet = book.createSheet("Sheet1", 0);
    for (int i = 0; i < rowhead.size(); i++) {
        String tem = String.valueOf(rowhead.elementAt(i));
        Label lab = new Label(i, 0, tem);
        sheet.addCell(lab);
    }
    for (int i = 0; i < Rowdate.size(); i++) {
        int tempt[] = (int[]) Rowdate.elementAt(i);
        for (int j = 0; j < tempt.length; j++) {
            String tem = String.valueOf(tempt[j]);
            Label lab = new Label(i, j + 1, tem);
            sheet.addCell(lab);
        }
    }
    book.write();
    book.close();
    os.flush();
    os.close();
}

/**
 * 保存以Vector存储形式存入excel表中
 * 代存储表头
 * @param file String
 //* @param sheetnum int
 //* @param sheetname String
 * @param rowhead Object[]
 * 存储数据表头的名称
 * @param Rowdate Vector
 * 需要存储的数据
 * @throws WriteException
 * @throws IOException
 */
public void saveArrayVEC_Float(String file,Vector rowhead,Vector Rowdate) throws WriteException,
        IOException {
    OutputStream os = new FileOutputStream(file);
    jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
    jxl.write.WritableSheet sheet = book.createSheet("Sheet1", 0);
    for (int i = 0; i < rowhead.size(); i++) {
        String tem = String.valueOf(rowhead.elementAt(i));
        Label lab = new Label(i, 0, tem);
        sheet.addCell(lab);
    }
    for (int i = 0; i < Rowdate.size(); i++) {
        float tempt[] = (float[]) Rowdate.elementAt(i);
        for (int j = 0; j < tempt.length; j++) {
            String tem = String.valueOf(tempt[j]);
            Label lab = new Label(i, j + 1, tem);
            sheet.addCell(lab);
        }
    }
    book.write();
    book.close();
    os.flush();
    os.close();
}

/**
 * 保存以Vector存储形式存入excel表中
 * 代存储表头
 * @param file String
 //* @param sheetnum int
 //* @param sheetname String
 * @param rowhead Object[]
 * 存储数据表头的名称
 * @param Rowdate Vector
 * 需要存储的数据
 * @throws WriteException
 * @throws IOException
 */
public void saveArryVEC_double(String file,Vector rowhead,Vector Rowdate) throws WriteException,
        IOException {
    OutputStream os = new FileOutputStream(file);
    jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
    jxl.write.WritableSheet sheet = book.createSheet("Sheet1", 0);
    for (int i = 0; i < rowhead.size(); i++) {
        String tem = String.valueOf(rowhead.elementAt(i));
        Label lab = new Label(i, 0, tem);
        sheet.addCell(lab);
    }
    for (int i = 0; i < Rowdate.size(); i++) {
        double tempt[] = (double[]) Rowdate.elementAt(i);
        for (int j = 0; j < tempt.length; j++) {
            String tem = String.valueOf(tempt[j]);
            Label lab = new Label(i, j + 1, tem);
            sheet.addCell(lab);
        }
    }
    book.write();
    book.close();
    os.flush();
    os.close();
}

/**
 * 保存以数组存储形式存入excel表中
 * 不存储表头
 * @param file String
// * @param sheetnum int
// * @param sheetname String
 * @param Rowdate Object[][]
 * 数据数组对象
 * @return boolean
 * @throws WriteException
 * @throws IOException
 */
public boolean saveRow(String file,Object Rowdate[][]) throws WriteException,IOException {
    boolean falge = false;
    OutputStream os = new FileOutputStream(file);
    jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
    jxl.write.WritableSheet sheet = book.createSheet("Sheet1", 0);
    for (int i = 0; i < Rowdate.length; i++) {
        for (int j = 0; j < Rowdate[i].length; j++) {
            String tem = String.valueOf(Rowdate[i][j]);
            Label lab = new Label(j, i + 1, tem);
            sheet.addCell(lab);
        }
    }
    book.write();
    book.close();
    return falge;
}

/**
 * 保存以数组存储形式存入excel表中
 * 存储表头一起
 * @param file String
// * @param sheetnum int
// * @param sheetname String
 * @param rowhead Object[]
 * @param Rowdate Object[][]
 * @return boolean
 * @throws WriteException
 * @throws IOException
 */
public boolean saveRow(String file,Object rowhead[],Object Rowdate[][]) throws WriteException,IOException {
    boolean falge = false;
    OutputStream os = new FileOutputStream(file);
    jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
    jxl.write.WritableSheet sheet = book.createSheet("Sheet1", 0);
    for (int i = 0; i < rowhead.length; i++) {
        String tem = String.valueOf(rowhead[i]);
        Label lab = new Label(i, 0, tem);
        sheet.addCell(lab);
    }
    for (int i = 0; i < Rowdate.length; i++) {
        for (int j = 0; j < Rowdate[i].length; j++) {
            String tem = String.valueOf(Rowdate[i][j]);
            Label lab = new Label(j, i + 1, tem);
            sheet.addCell(lab);
        }
    }
    book.write();
    book.close();
    return falge;
}

/**
 * 保存以数组存储形式存入excel表中
 * 不存储表头
 * @param file String
// * @param sheetnum int
// * @param sheetname String
 * @param coldate Object[][]
 * @return boolean
 * @throws WriteException
 * @throws IOException
 */
public boolean saveCol(String file,Object coldate[][]) throws WriteException,
        IOException {
    boolean falge = false;
    OutputStream os = new FileOutputStream(file);
    jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
    jxl.write.WritableSheet sheet = book.createSheet("Sheet1", 0);
    for (int i = 0; i < coldate.length; i++) {
        for (int j = 0; j < coldate[i].length; j++) {
            String tem = String.valueOf(coldate[i][j]);
            Label lab = new Label(i, j + 1, tem);
            sheet.addCell(lab);
        }
    }
    book.write();
    book.close();
    return falge;
}


/**
 * 存储行数据到excel表中
 * 并且不存储表头
 * @param file String
 * @param sheetnum int
 * @param sheetname String
 * @param coldate Vector
 * 该Vector中存储的是数据以行的形式存储
 * 也就是Vector中的子对象存储的是各行数据
 * @return boolean
 * @throws WriteException
 * @throws IOException
 */
public boolean saveCol_Vec(String file, int sheetnum, String sheetname,
                           Vector coldate) throws WriteException,
        IOException {
    boolean falge = false;
    OutputStream os = new FileOutputStream(file);
    jxl.write.WritableWorkbook book = Workbook.createWorkbook(os);
    jxl.write.WritableSheet sheet = book.createSheet(sheetname, sheetnum);
    for (int i = 0; i < coldate.size(); i++) {
        Object tempt[] = (Object[]) coldate.elementAt(i);
        for (int j = 0; j < tempt.length; j++) {
            String tem = String.valueOf(tempt[j]);
            Label lab = new Label(i, j + 1, tem);
            sheet.addCell(lab);
        }
    }
    book.write();
    book.close();
    return falge;
}

}
