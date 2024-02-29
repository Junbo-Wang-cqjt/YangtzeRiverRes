package DateManage;

import jxl.*;
import jxl.Workbook;
import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import ToolFunction.CommonFun;
import ToolFunction.MathFun;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class Import_xls_Date {
    InputStream input;
    Workbook rwb;
    public void connect(File file) {
        try {
            //构建Workbook对象, 只读Workbook对象
            //直接从本地文件创建Workbook
            //从输入流创建Workbook
            //File f=new File(file);
            input = new FileInputStream(file);
            rwb = Workbook.getWorkbook(input);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "无法获得excel资料,请检查!",
                                          "错误提示",
                                          JOptionPane.WARNING_MESSAGE);
        }
    }

    public void connect(String file) {
        try {
            //构建Workbook对象, 只读Workbook对象
            //直接从本地文件创建Workbook
            //从输入流创建Workbook
            File f = new File(file);
            input = new FileInputStream(f);
            rwb = Workbook.getWorkbook(input);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "无法获得excel资料,请检查!",
                                          "错误提示",
                                          JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * 获取excel中表名
     * @param file File
     * 连接路径为File类型
//     * @param sheetnum int
     * @return String[]
     */
    public String[] getSheetsName(File file) {
        connect(file);
        Sheet sheets[] = rwb.getSheets();
        int numsheet = sheets.length;
        String sheetname[] = new String[numsheet];
        for (int i = 0; i < numsheet; i++) {
            sheetname[i] = sheets[i].getName();
        }
        return sheetname;
    }

    /**
     * 获取excel中表名
     * @param file File
     * 连接路径为String类型
//     * @param sheetnum int
     * @return String[]
     */
    public String[] getSheetsName(String file) {
        connect(file);
        Sheet sheets[] = rwb.getSheets();
        int numsheet = sheets.length;
        String sheetname[] = new String[numsheet];
        for (int i = 0; i < numsheet; i++) {
            sheetname[i] = sheets[i].getName();
        }
        return sheetname;
    }

    /**
     * 获取excel表中的表头
     * @param file File
     * 连接路径为File类型
     * @param sheetnum int
     * @return String[]
     */
    public String[] getRowHead(File file, int sheetnum) {
        connect(file);
        Sheet sheet = rwb.getSheet(sheetnum);
        int rows = sheet.getColumns();
        String rowhead[] = new String[rows];
        for (int i = 0; i < rows; i++) {
            Cell ctemp = sheet.getCell(i, 0);
            rowhead[i] = ctemp.getContents();
        }
        return rowhead;
    }

    /**
     * 获取excel表中的表头
//     * @param String File
     * 连接路径为String类型
     * @param sheetnum int
     * @return String[]
     */
    public String[] getRowHead(String file, int sheetnum) {
        connect(file);
        Sheet sheet = rwb.getSheet(sheetnum);
        int rows = sheet.getColumns();
        String rowhead[] = new String[rows];
        for (int i = 0; i < rows; i++) {
            Cell ctemp = sheet.getCell(i, 0);
            rowhead[i] = ctemp.getContents();
        }
        return rowhead;
    }

    /**
     * 获取excel表中的表头
//     * @param String File
     * 连接路径为String类型
     * @param sheetnum int
     * @return String[]
     */
    public Object[] getRowHeadObj(String file, int sheetnum) {
        connect(file);
        Sheet sheet = rwb.getSheet(sheetnum);
        int rows = sheet.getColumns();
        Object rowhead[] = new Object[rows];
        for (int i = 0; i < rows; i++) {
            Cell ctemp = sheet.getCell(i, 0);
            rowhead[i] = (Object) ctemp.getContents();
        }
        return rowhead;
    }

    /**
     * 获取excel表中的表头
//     * @param String File
     * 连接路径为String类型
     * @param sheetnum int
     * @return String[]
     */
    public Object[] getRowHeadObj(File file, int sheetnum) {
        connect(file);
        Sheet sheet = rwb.getSheet(sheetnum);
        int rows = sheet.getColumns();
        Object rowhead[] = new Object[rows];
        for (int i = 0; i < rows; i++) {
            Cell ctemp = sheet.getCell(i, 0);
            rowhead[i] = (Object) ctemp.getContents();
        }
        return rowhead;
    }

    /**
     * 获取excel表中指定列名的列值
     * @param Colname String
     * @param file File
     * @param sheetnum int
     * @return float[]
     */
    public float[] getColumns(String Colname, File file, int sheetnum) {
        connect(file);
        Sheet sheet = rwb.getSheet(sheetnum);
        int rows = sheet.getColumns();
        int cols = sheet.getRows();
        int num = -1;
        for (int i = 0; i < rows; i++) {
            Cell ctemp = sheet.getCell(i, 0);
            String tempt = ctemp.getContents();
            if (Colname.equals(tempt)) {
                num = i;
            }
        }
        float col[] = new float[rows - 1];
        for (int i = 1; i < cols; i++) {
            Cell ctemp = sheet.getCell(num, i);
            String tempt = ctemp.getContents();
            if (tempt.equals("")) {
                col[i-1] = 0;
            } else {
                col[i-1] = Float.parseFloat(tempt);
            }
        }
        return col;
    }

    /**
     * 获取excel表中指定列名的列值
     * @param Colname String
     * @param file File
     * @param sheetnum int
     * @return float[]
     */
    public String[] getStringColumns(String Colname, File file, int sheetnum) {
        connect(file);
        Sheet sheet = rwb.getSheet(sheetnum);
        int rows = sheet.getColumns();
        int cols = sheet.getRows();
        int num = -1;
        for (int i = 0; i < rows; i++) {
            Cell ctemp = sheet.getCell(i, 0);
            String tempt = ctemp.getContents();
            if (Colname.equals(tempt)) {
                num = i;
            }
        }
        String col[] = new String[rows - 1];
        for (int i = 1; i < cols; i++) {
            Cell ctemp = sheet.getCell(num, i);
            String tempt = ctemp.getContents();
            col[i-1] = tempt;
            /*if (tempt.equals("")) {
                col[i] = 0;
                         } else {
                col[i] = Float.parseFloat(tempt);
                         }*/
        }
        return col;
    }

    /**
     * 获取excel表中指定列名的列值
     * @param Colname String
     * @param file File
     * @param sheetnum int
     * @return float[]
     */
    public String[] getStringColumns(String Colname, String file, int sheetnum) {
       // String col[] ;
        try {
            connect(file);
            Sheet sheet = rwb.getSheet(sheetnum);
            int rows = sheet.getColumns();
            int cols = sheet.getRows();
            int num = -1;
            for (int i = 0; i < rows; i++) {
                Cell ctemp = sheet.getCell(i, 0);
                String tempt = ctemp.getContents();
                if (Colname.equals(tempt)) {
                    num = i;
                }
            }
            String col[] = new String[cols - 1];
            for (int i = 1; i < cols; i++) {
                Cell ctemp = sheet.getCell(num, i);
                String tempt = ctemp.getContents();
                if (tempt.equals("")) {
                    col[i-1] = " ";
                } else {
                    col[i-1] =tempt;
                }
            }
            rwb.close();
            input.close();
            return col;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "无法获得excel资料,请检查数据是否正确",
                                          "错误提示",
                                          JOptionPane.WARNING_MESSAGE);
        }
        return null;
    }

    /**
     * 获取excel表中指定列名的列值
     * @param Colname String
     * @param file String
     * 路径为String
     * @param sheetnum int
     * @return float[]
     */
    public float[] getFloatColumns(String Colname, String file, int sheetnum) {
        float Row_col[] = null;
        try {
            connect(file);
            //构建Workbook对象, 只读Workbook对象
            //直接从本地文件创建Workbook
            //从输入流创建Workbook
            Sheet rs = rwb.getSheet(sheetnum);
            int cols = rs.getRows();
            int rows = rs.getColumns();
            /********表头******************/
            int row = 0;
            for (int i = 0; i < rows; i++) {
                Cell ctemp = rs.getCell(i, 0);
                String tempt = ctemp.getContents();
                if (tempt.equals(Colname)) {
                    row = i;
                }
            }
            /*********行-列********************/
            Row_col = new float[cols - 1];
            for (int j = 1; j < cols; j++) {
                Cell ctemp = rs.getCell(row, j);
                String tempt = ctemp.getContents();
                if (tempt.equals("")) {
                    Row_col[j - 1] = 0;
                } else {
                    Row_col[j - 1] = Float.parseFloat(tempt);
                }
            }
            rwb.close();
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "无法获得excel资料,请检查是否数据为整数",
                                          "错误提示",
                                          JOptionPane.WARNING_MESSAGE);
        }
        return Row_col;
    }

    /**
     * 获取excel表中指定列名的列值
     * @param Colname String
     * @param file File
     * @param sheetnum int
     * @return float[]
     */
    public double[] getDoubleColumns(String Colname, File file, int sheetnum) {
        double col[] = null;
        try {
            connect(file);
            Sheet sheet = rwb.getSheet(sheetnum);
            int rows = sheet.getColumns();
            int cols = sheet.getRows();
            int num = -1;
            for (int i = 0; i < rows; i++) {
                Cell ctemp = sheet.getCell(i, 0);
                String tempt = ctemp.getContents();
                if (Colname.equals(tempt)) {
                    num = i;
                }
            }
            col = new double[cols - 1];
            for (int i = 1; i < cols; i++) {
                Cell ctemp = sheet.getCell(num, i);
                String tempt = ctemp.getContents();
                if (tempt.equals("")) {
                    col[i-1] = 0;
                } else {
                    col[i-1] = Double.parseDouble(tempt);
                }
            }

            rwb.close();
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "无法获得excel资料,请检查是否数据为整数",
                                          "错误提示",
                                          JOptionPane.WARNING_MESSAGE);
        }

        return col;
    }

    /**
     * 获取excel表中指定列名的列值
     * @param Colname String
     * @param file String
     * 路径为String
     * @param sheetnum int
     * @return float[]
     */
    public double[] getDoubleColumns(String Colname, String file, int sheetnum) {
        double Row_col[] = null;
        try {
            connect(file);
            //构建Workbook对象, 只读Workbook对象
            //直接从本地文件创建Workbook
            //从输入流创建Workbook
            Sheet rs = rwb.getSheet(sheetnum);
            int cols = rs.getRows();
            int rows = rs.getColumns();
            /********表头******************/
            int row = 0;
            for (int i = 0; i < rows; i++) {
                Cell ctemp = rs.getCell(i, 0);
                String tempt = ctemp.getContents();
                if (tempt.equals(Colname)) {
                    row = i;
                }
            }
            /*********行-列********************/
            Row_col = new double[cols - 1];
            for (int j = 1; j < cols; j++) {
                Cell ctemp = rs.getCell(row, j);
                String tempt = ctemp.getContents();
                if (tempt.equals("")) {
                    Row_col[j - 1] = 0;
                } else {
                    Row_col[j - 1] = Double.parseDouble(tempt);
                }
            }
            rwb.close();
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "无法获得excel资料,请检查是否数据为整数",
                                          "错误提示",
                                          JOptionPane.WARNING_MESSAGE);
        }
        return Row_col;
    }

    /**
     * 获取excel表中指定列名的列值
     * @param Colname String
     * @param file String
     * 路径为String
     * @param sheetnum int
     * @return int[]
     */
    public int[] getIntColumns(String Colname, String file, int sheetnum) {
        int Row_col[] = null;
        try {
            connect(file);
            //构建Workbook对象, 只读Workbook对象
            //直接从本地文件创建Workbook
            //从输入流创建Workbook
            Sheet rs = rwb.getSheet(sheetnum);
            int cols = rs.getRows();
            int rows = rs.getColumns();
            /********表头******************/
            int row = 0;
            for (int i = 0; i < rows; i++) {
                Cell ctemp = rs.getCell(i, 0);
                String tempt = ctemp.getContents();
                if (tempt.equals(Colname)) {
                    row = i;
                }
            }//找到列名对应的列数
            /*********行-列********************/
            Row_col = new int[cols - 1];
            for (int j = 1; j < cols; j++) {
                Cell ctemp = rs.getCell(row, j);
                String tempt = ctemp.getContents();
                if (tempt.equals("")) {
                    Row_col[j - 1] = 0;
                } else {
                    Row_col[j - 1] = Integer.parseInt(tempt);
                }
            }
            rwb.close();
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "无法获得excel资料,请检查是否数据为整数",
                                          "错误提示",
                                          JOptionPane.WARNING_MESSAGE);
        }
        return Row_col;
    }

    /**
     * 获取excel表中指定列名的列值
     * @param Colname String
     * @param file String
     * 路径为String
     * @param sheetnum int
     * @return int[]
     */
    public int[] getIntColumns(String Colname, File file, int sheetnum) {
        int Row_col[] = null;
        try {
            connect(file);
            //构建Workbook对象, 只读Workbook对象
            //直接从本地文件创建Workbook
            //从输入流创建Workbook
            Sheet rs = rwb.getSheet(sheetnum);
            int cols = rs.getRows();
            int rows = rs.getColumns();
            /********表头******************/
            int row = 0;
            for (int i = 0; i < rows; i++) {
                Cell ctemp = rs.getCell(i, 0);
                String tempt = ctemp.getContents();
                if (tempt.equals(Colname)) {
                    row = i;
                }
            }
            /*********行-列********************/
            Row_col = new int[cols - 1];
            for (int j = 1; j < cols; j++) {
                Cell ctemp = rs.getCell(row, j);
                String tempt = ctemp.getContents();
                if (tempt.equals("")) {
                    Row_col[j - 1] = 0;
                } else {
                    Row_col[j - 1] = Integer.parseInt(tempt);
                }
            }
            rwb.close();
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "无法获得excel资料,请检查是否数据为整数",
                                          "错误提示",
                                          JOptionPane.WARNING_MESSAGE);
        }
        return Row_col;
    }

    /**
     * 返回行数组
     * @param file String
     * @return Object[]
     */
    public Object[][] getRowColumnsobject(String file, int sheetnum) {
        Object Row_col[][] = null;
        try {
            connect(file);
            Sheet rs = rwb.getSheet(sheetnum);
            int cols = rs.getRows();
            int rows = rs.getColumns();
            /*********行-列********************/
            Row_col = new Object[rows][cols - 1];
            for (int i = 0; i < rows; i++) {
                for (int j = 1; j < cols; j++) {
                    Cell ctemp = rs.getCell(i, j);
                    String tempt = ctemp.getContents();
                    Row_col[i][j - 1] = (Object) tempt;
                }
            }
            rwb.close();
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "无法获得excel资料,请检查是否数据为整数",
                                          "错误提示",
                                          JOptionPane.WARNING_MESSAGE);
        }
        return Row_col;
    }

    /**
     * 返回行数组
     * @param file String
     * @return Object[]
     */
    public float[][] getRowColumnsfloat(String file, int sheetnum) {
        float Row_col[][] = null;
        try {
            connect(file);
            Sheet rs = rwb.getSheet(sheetnum);
            int cols = rs.getRows();
            int rows = rs.getColumns();
            /*********行-列********************/
            Row_col = new float[rows][cols - 1];
            for (int i = 0; i < rows; i++) {
                for (int j = 1; j < cols; j++) {
                    Cell ctemp = rs.getCell(i, j);
                    String tempt = ctemp.getContents();
                    if(tempt.equals("")){
                        Row_col[i][j-1] = 0;
                    }else{
                        Row_col[i][j - 1] = Float.parseFloat(tempt);
                    }
                }
            }
            rwb.close();
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "无法获得excel资料,请检查是否数据为整数",
                                          "错误提示",
                                          JOptionPane.WARNING_MESSAGE);
        }
        return Row_col;
    }

    /**
     * 返回列数组
     * @param file String
     * @return Object[]
     */
    public Object[][] getColumnRowsobject(String file, int sheetnum) {
        Object Col_row[][] = null;
        try {
            connect(file);
            Sheet rs = rwb.getSheet(sheetnum);
            int cols = rs.getRows();
            int rows = rs.getColumns();
            /*********列-行********************/
            Col_row = new Object[cols - 1][rows];
            for (int i = 1; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
                    Cell ctemp = rs.getCell(j, i);
                    String tempt = ctemp.getContents();
                    Col_row[i - 1][j] = (Object) tempt;
                }
            }
            rwb.close();
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "无法获得excel资料,请检查是否数据为整数",
                                          "错误提示",
                                          JOptionPane.WARNING_MESSAGE);
        }
        return Col_row;
    }

    /**
     * 返回列数组
     * @param file String
     * @return Object[]
     */
    public float[][] getColumnRowsfloat(String file, int sheetnum) {
        float Col_row[][] = null;
        try {
            connect(file);
            Sheet rs = rwb.getSheet(sheetnum);
            int cols = rs.getRows();
            int rows = rs.getColumns();
            /*********列-行********************/
            Col_row = new float[cols - 1][rows];
            for (int i = 1; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
                    Cell ctemp = rs.getCell(j, i);
                    String tempt = ctemp.getContents();
                    if(tempt.equals("")){
                        Col_row[i - 1][j] =0;
                    }else{
                        Col_row[i - 1][j] = Float.parseFloat(tempt);
                    }
                }
            }
            rwb.close();
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "无法获得excel资料,请检查是否数据为整数",
                                          "错误提示",
                                          JOptionPane.WARNING_MESSAGE);
        }
        return Col_row;
    }

    /**
     * 返回行数组
     * @param file String
     * @return Object[]
     */
    public double[][] getRowColumnsDouble(String file, int sheetnum) {
        double Row_col[][] = null;
        try {
            connect(file);
            Sheet rs = rwb.getSheet(sheetnum);
            int cols = rs.getRows();
            int rows = rs.getColumns();
            /*********行-列********************/
            Row_col = new double[rows][cols - 1];
            for (int i = 0; i < rows; i++) {
                for (int j = 1; j < cols; j++) {
                    Cell ctemp = rs.getCell(i, j);
                    String tempt = ctemp.getContents();
                    Row_col[i][j - 1] = Double.valueOf(tempt);
                }
            }
            rwb.close();
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "无法获得excel资料,请检查是否数据为整数",
                                          "错误提示",
                                          JOptionPane.WARNING_MESSAGE);
        }
        return Row_col;
    }

    /**
     * 返回列数组
     * @param file String
     * @return Object[]
     */
    public double[][] getColumnRowsDouble(String file, int sheetnum) {
        double Col_row[][] = null;
        try {
            connect(file);
            Sheet rs = rwb.getSheet(sheetnum);
            int cols = rs.getRows();
            int rows = rs.getColumns();
            /*********列-行********************/
            Col_row = new double[cols - 1][rows];
            for (int i = 1; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
                    Cell ctemp = rs.getCell(j, i);
                    String tempt = ctemp.getContents();
                    Col_row[i - 1][j] = Double.valueOf(tempt);
                }
            }
            rwb.close();
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "无法获得excel资料,请检查是否数据为整数",
                                          "错误提示",
                                          JOptionPane.WARNING_MESSAGE);
        }
        return Col_row;
    }

    /**
     * 返回行数组
     * @param file String
     * @return Object[]
     */
    public int[][] getRowColumnsInt(String file, int sheetnum) {
        int Row_col[][] = null;
        try {
            connect(file);
            Sheet rs = rwb.getSheet(sheetnum);
            int cols = rs.getRows();
            int rows = rs.getColumns();
            /*********行-列********************/
            Row_col = new int[rows][cols - 1];
            for (int i = 0; i < rows; i++) {
                for (int j = 1; j < cols; j++) {
                    Cell ctemp = rs.getCell(i, j);
                    String tempt = ctemp.getContents();
                    Row_col[i][j - 1] = Integer.valueOf(tempt);
                }
            }
            rwb.close();
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "无法获得excel资料,请检查是否数据为整数",
                                          "错误提示",
                                          JOptionPane.WARNING_MESSAGE);
        }
        return Row_col;
    }

    /**
     * 返回列数组
     * @param file String
     * @return Object[]
     */
    public int[][] getColumnRowsInt(String file) {
        int Col_row[][] = null;
        try {
            connect(file);
            Sheet rs = rwb.getSheet(0);
            int cols = rs.getRows();
            int rows = rs.getColumns();
            /*********列-行********************/
            Col_row = new int[cols - 1][rows];
            for (int i = 1; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
                    Cell ctemp = rs.getCell(j, i);
                    String tempt = ctemp.getContents();
                    Col_row[i - 1][j] = Integer.valueOf(tempt);
                }
            }
            rwb.close();
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "无法获得excel资料,请检查是否数据为整数",
                                          "错误提示",
                                          JOptionPane.WARNING_MESSAGE);
        }
        return Col_row;
    }

    /**
     * 获取列为时间格式的的数据
     * @param file String
//     * @param title String
     * @return Date[]
     */
    public Date[] getColDate_Time(String Colname, String file, int sheetnum) {
        Date col[] = null;
        Vector tem = new Vector();
        try {
            connect(file);
            Sheet rs = rwb.getSheet(sheetnum);
            int cols = rs.getRows();
            int rows = rs.getColumns();
            int row = 0;
            for (int i = 0; i < rows; i++) {
                Cell ctemp = rs.getCell(i, 0);
                String tempt = ctemp.getContents();
                if (tempt.equals(Colname)) {
                    row = i;
                }
            }
            col = new Date[cols-1];
            for (int j = 1; j < cols; j++) {
                Cell ctemp = rs.getCell(row, j);
                if (ctemp.getType() == CellType.DATE) {
                    DateCell datecell = (DateCell) ctemp;
                    Date time = datecell.getDate();
                    if (time != null) {
                        col[j-1]=time;
                        //tem.addElement(time);
                    }
                }
            }
            //col = MathFun.convertDate(tem);
            rwb.close();
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "无法获得excel资料,请检查时间格式!",
                                          "错误提示",
                                          JOptionPane.WARNING_MESSAGE);
        }
        return col;
    }

    /**
     * 获得指定行数据
     * 没有过，不知道能不能用
     * @param Rowname String
     * @param file File
     * @param sheetnum int
     * @return float[]
     */
    public float[] getRows(String Rowname, File file, int sheetnum) {
        connect(file);
        Sheet sheet = rwb.getSheet(sheetnum);
        int rows = sheet.getColumns();
        int cols = sheet.getRows();
        int num = -1;
        lab:
                for (int i = 0; i < cols; i++) {
            Cell ctemp = sheet.getCell(0, i);
            String tempt = ctemp.getContents();
            if (Rowname.equals(tempt)) {
                num = i;
                break lab;
            }
        }
        float Row[] = new float[rows - 1];
        for (int i = 1; i < cols; i++) {
            Cell ctemp = sheet.getCell(i, num);
            String tempt = ctemp.getContents();
            Row[i] = Float.parseFloat(tempt);
        }
        return Row;
    }

    public Vector import_xls_Date(File file) {
        int Row_col[][] = null;
        int Col_row[][] = null;
        Object Row_col1[][] = null;
        Object Col_row1[][] = null;
        Object Row_name[] = null;
        try {
            //构建Workbook对象, 只读Workbook对象
            //直接从本地文件创建Workbook
            //从输入流创建Workbook
            InputStream is = new FileInputStream(file);
            jxl.Workbook rwb = Workbook.getWorkbook(is);
            Sheet rs = rwb.getSheet(0);
            int cols = rs.getRows();
            int rows = rs.getColumns();
            System.out.println("cols=" + cols + "  rows==" + rows);
            /********表头******************/
            Row_name = new Object[rows];
            for (int i = 0; i < rows; i++) {
                Cell ctemp = rs.getCell(i, 0);
                String tempt = ctemp.getContents();
                Row_name[i] = (Object) tempt;
                //Row_name[i]=ctemp.getContents();
            }
            /*********行-列********************/
            Row_col = new int[rows][cols - 1];
            Row_col1 = new Object[rows][cols - 1];
            for (int i = 0; i < rows; i++) {
                for (int j = 1; j < cols; j++) {
                    Cell ctemp = rs.getCell(i, j);
                    String tempt = ctemp.getContents();
                    Row_col1[i][j - 1] = (Object) tempt;
                    Row_col[i][j - 1] = Integer.parseInt(tempt);
                    //System.out.print(Row_col[i][j-1]+"  ,");
                }
            }
            /*********列-行********************/
            Col_row = new int[cols - 1][rows];
            Col_row1 = new Object[cols - 1][rows];
            for (int i = 1; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
                    Cell ctemp = rs.getCell(j, i);
                    String tempt = ctemp.getContents();
                    Col_row1[i - 1][j] = (Object) tempt;
                    Col_row[i - 1][j] = Integer.parseInt(tempt);
                }
            }
            rwb.close();
            is.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "无法获得excel资料,请检查是否数据为整数",
                                          "错误提示",
                                          JOptionPane.WARNING_MESSAGE);
        }
        Vector vec = new Vector();
        vec.addElement(Row_name);
        vec.addElement(Row_col);
        vec.addElement(Col_row);
        vec.addElement(Row_col1);
        vec.addElement(Col_row1);
        return vec;
    }
}
