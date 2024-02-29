package ToolFunction;

import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.sql.Timestamp;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author xw
 * @version 1.0
 */
public class DataBase {
    public  Connection Connect(String name)
     {
       Connection connection=null ;
         try {
             String driverName = "sun.jdbc.odbc.JdbcOdbcDriver";
             Class.forName(driverName);//注册驱动
         } catch (ClassNotFoundException e) {
             System.out.println("没有找到文件或表格");
         }
         try {
             String dbURL = "jdbc:odbc:"+name;
             connection = DriverManager.getConnection(dbURL, "sa", "");
         } catch (SQLException e) {
             System.out.println("出现SQL异常");
         }
         return connection;
     }

     public Boolean SaveFlood(Vector vec,String biao,String name){
       Boolean sucess=true;
        Statement stmt=null;
       Connection connection = null;
       connection = Connect(name);
       try {
           stmt = connection.createStatement();
           stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                  ResultSet.CONCUR_UPDATABLE);

           SimpleDateFormat simpledateformat=new SimpleDateFormat("yyyy-MM-dd-HH");
           connection.setAutoCommit(false);
           stmt.executeUpdate("DELETE FROM "+biao);
           Date date[]=(Date[])vec.elementAt(0);
           float length[]=(float[])vec.elementAt(1);
            for(int i=0;i<date.length;i++){
                //tempvec=(Vector)vec.elementAt(i);
                StringBuffer sqlString2 = new StringBuffer(
                        "INSERT INTO "+biao+" VALUES ('");
                //Date date= simpledateformat.parse((String)tempvec.elementAt(2));
                //sqlString2.append(simpledateformat.format(new Timestamp(date[i].getTime())));
                String d=simpledateformat.format(date[i]);
                sqlString2.append(d);
                sqlString2.append("',");
                sqlString2.append(length[i]);
                sqlString2.append(")");
                stmt.addBatch(sqlString2.toString());
            }
            stmt.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
            Boolean isautocommit=connection.getAutoCommit();
            System.out.println(biao+"保存成功");
            if(isautocommit){
                JOptionPane.showMessageDialog(null, "保存成功",
                             "信息提示", JOptionPane.PLAIN_MESSAGE);
            }
       } catch (Exception e) {
           e.printStackTrace();
           try{
            connection.rollback();
            sucess=false;
            System.out.println(biao+"保存失败");
            JOptionPane.showMessageDialog(null, "保存失败",
                             "警告信息", JOptionPane.WARNING_MESSAGE);
         }catch(Exception ex){}
       } finally {
           if (connection != null | stmt != null) {
               try {
                   stmt.close();
                   connection.close();
               } catch (SQLException e) {
                   e.printStackTrace();
               }
           }
       }
       return sucess;
   }
   public Boolean SaveFlood(Vector vec,String title,String table,String name){
       Boolean sucess=true;
        Statement stmt=null ;
       Connection connection = null;
       connection = Connect(name);
       try {
           stmt = connection.createStatement();
           stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                  ResultSet.CONCUR_UPDATABLE);

           SimpleDateFormat simpledateformat=new SimpleDateFormat("yyyy-MM-dd");
           connection.setAutoCommit(false);
            Vector tempvec=new Vector();
           stmt.executeUpdate("DELETE FROM "+table);
           /*for(int j=0;j<vec.size();j++){
               Date date=(Date)tempvec.elementAt(0);
               String select1="DELETE FROM  WHERE "+title+"='"+
                             simpledateformat.format(date)+"'AND value="+stcd;
           }*/
            for(int i=0;i<vec.size();i++){
                tempvec=(Vector)vec.elementAt(i);
                StringBuffer sqlString2 = new StringBuffer(
                        "UPDATE biao SET ");
                sqlString2.append("STCD=");
                sqlString2.append(Integer.parseInt((String)tempvec.elementAt(0)));
                sqlString2.append(",Fcstep=");
                sqlString2.append(Integer.parseInt((String)tempvec.elementAt(1)));
                sqlString2.append(",Date='");
                Date date= simpledateformat.parse((String)tempvec.elementAt(2));
                sqlString2.append(simpledateformat.format(new Timestamp(date.getTime())));
                sqlString2.append("',Rain=");
                sqlString2.append(Float.parseFloat((String)tempvec.elementAt(3)));
                sqlString2.append(",Magnitude=");
                sqlString2.append(Integer.parseInt((String)tempvec.elementAt(4)));
                sqlString2.append(" WHERE ");
                sqlString2.append(Integer.parseInt((String)tempvec.elementAt(0)));
                sqlString2.append(",");
                sqlString2.append(Integer.parseInt((String)tempvec.elementAt(1)));
                sqlString2.append(",'");
               // Date date= simpledateformat.parse((String)tempvec.elementAt(2));
                sqlString2.append(simpledateformat.format(new Timestamp(date.getTime())));
                sqlString2.append("',");
                sqlString2.append(Float.parseFloat((String)tempvec.elementAt(3)));
                sqlString2.append(",");
                sqlString2.append(Integer.parseInt((String)tempvec.elementAt(4)));
                sqlString2.append(")");
                stmt.addBatch(sqlString2.toString());
                tempvec.removeAllElements();
            }
            stmt.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
            Boolean isautocommit=connection.getAutoCommit();
            if(isautocommit){
                JOptionPane.showMessageDialog(null, "保存成功",
                             "信息提示", JOptionPane.PLAIN_MESSAGE);
            }
       } catch (Exception e) {
           e.printStackTrace();
           try{
            connection.rollback();
            sucess=false;
            JOptionPane.showMessageDialog(null, "保存失败",
                             "警告信息", JOptionPane.WARNING_MESSAGE);
         }catch(Exception ex){}
       } finally {
           if (connection != null | stmt != null) {
               try {
                   stmt.close();
                   connection.close();
               } catch (SQLException e) {
                   e.printStackTrace();
               }
           }
       }
       return sucess;
   }

     public  double[] getParameter(int model_ID,String comp_ID,String name)
     {

         double[] fq =new double[12];
          Statement stmt =null;
        Connection connection=null;
         try {
            connection =Connect(name);
            stmt = connection.createStatement();
             stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                                   ResultSet.CONCUR_UPDATABLE);
             String select1 ="SELECT COUNT(model_ID) FROM tempp3fit HERE model_ID='"+model_ID+"' AND comp_ID="+comp_ID;
             int k=CheckRecord(select1,name);
             if(k!=0){
                 String query = "SELECT * FROM tempp3fit WHERE model_ID='"+model_ID+"' AND comp_ID="+comp_ID;
                 ResultSet rs = stmt.executeQuery(query);
                 rs.beforeFirst();
                 while (rs.next()) {
                     fq[0] =(double)rs.getFloat("so");
                     fq[1] =(double)rs.getFloat("uo");
                     fq[2] =(double)rs.getFloat("do");
                     fq[3] =(double)rs.getFloat("soo");
                     fq[4] =(double)rs.getFloat("uoo");
                     fq[5] =(double)rs.getFloat("sd");
                     fq[6] =(double)rs.getFloat("ud");
                     fq[7] =(double)rs.getFloat("g");
                     fq[8] =(double)rs.getFloat("a");
                     fq[9] =(double)rs.getFloat("B");
                     fq[10] =(double)rs.getFloat("k2");
                     fq[11] =(double)rs.getFloat("ka");
                 }
             }else{
                 JOptionPane.showMessageDialog(null, "对不起！没有找到参数保存记录\n       请选择计算参数", "提示",
                                                   JOptionPane.WARNING_MESSAGE);
             }
         } catch (SQLException e) {
             e.printStackTrace();
         } finally {
             if (connection != null | stmt != null) {
                 try {
                     stmt.close();
                     connection.close();
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }
             }
         }
         return fq;
     }

     public void InsertP3Result(int stcd,String str,float p[],float value[],
                                float EX,float CV,float CS,String name){
        Statement stmt=null ;
        Connection connection = null;
        int t=0;
        try {
            connection = Connect(name);
            stmt = connection.createStatement();
             stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                   ResultSet.CONCUR_UPDATABLE);
             String select1 ="SELECT COUNT(title) FROM tempp3fit WHERE title='"+str+"' AND STCD="+stcd;
             int k=CheckRecord(select1,name);
             System.out.println("检验成功k=="+k);
             if(k==0){
            for(int i=0;i<value.length;i++){
                String insert = "INSERT INTO tempp3fit VALUES ("+stcd+",'"+str+
                                      "',"+p[i]+","+value[i]+","+EX+","+CV+","+CS+")";
                int rs = stmt.executeUpdate(insert);t=rs;
            }
             System.out.println("检验后插入成功k==");
             }else{
                 String fq = "DELETE FROM tempp3fit WHERE title='" +str+"'AND STCD="+stcd;
                 int rs1 = stmt.executeUpdate(fq);
                 System.out.println("检验后删除1成功k==");
                  for(int i=0;i<value.length;i++){
                String insert = "INSERT INTO tempp3fit VALUES ("+stcd+",'"+str+
                                      "',"+p[i]+","+value[i]+","+EX+","+CV+","+CS+")";
                int rs = stmt.executeUpdate(insert);t=rs;
            }
           }
             System.out.println("检验删除后插入成功k=="+t);
             if(t!=0){
                 JOptionPane.showMessageDialog(null, "保存成功", "提示！",
                              JOptionPane.PLAIN_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print("InsertRainMTCL插入失败");
        } finally {
            if (connection != null | stmt != null) {
                try {
                    stmt.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public int CheckRecord(String str,String name){
        int count=0;
        Statement stmt=null;
        Connection connection=null;
         try {
            connection =Connect(name);
            stmt = connection.createStatement();
            stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                                   ResultSet.CONCUR_UPDATABLE);
             ResultSet rs = stmt.executeQuery(str);
             rs.last();
             count = rs.getInt(1);
             System.out.print("count=="+count);
        } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (connection != null | stmt != null) {
                    try {
                        stmt.close();
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        return count;
    }
}
