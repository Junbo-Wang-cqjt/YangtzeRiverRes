package ToolFunction;

import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * <p>Title: </p>
 * <p>Description:数据库取值 </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author xw
 * @version 1.0
 */
public class ConnectDB {
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
//调用double型
    public  double[] getdoublezhi1(String qa, String query,String name)
    {
        double[] fq = null;
         Statement stmt =null;
       Connection connection=null ;
        try {
           connection =Connect(name);
           stmt = connection.createStatement();
            stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                                  ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            rs.last();
            int num = rs.getRow();
            fq = new double[num];
            rs.beforeFirst();
            int i = 0;
            while (rs.next()) {
                fq[i] = rs.getDouble(qa);
                i++;
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
    public  float[] getfloatzhi1(String qa, String query,String name) {
       float[] fq = null;
        Statement stmt=null ;
       Connection connection = null;
       try {
           connection = Connect(name);
           stmt = connection.createStatement();
            stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                  ResultSet.CONCUR_UPDATABLE);
           ResultSet rs = stmt.executeQuery(query);
           rs.last();
           int num = rs.getRow();
           fq = new float[num];
           rs.beforeFirst();
           int i = 0;
           while (rs.next()) {
               fq[i] =(float) rs.getDouble(qa);
               i++;
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
   public  double[][] getdoublezhi2(String qa,String qb, String query,String name) {
       double[][] fq = null;
       Statement stmt =null;
     Connection connection = null;
       try {
          connection = Connect(name);
          stmt = connection.createStatement();
           stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                 ResultSet.CONCUR_UPDATABLE);
             stmt = connection.createStatement();
           ResultSet rs = stmt.executeQuery(query);
           rs.last();
           int num = rs.getRow();
            fq= new double[2][num];
           rs.beforeFirst();
           int i = 0;
           while (rs.next()) {
               fq[0][i] = rs.getDouble(qa);
               fq[1][i]=rs.getDouble(qb);
               i++;
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
   public  float[][] getfloatzhi2(String qa,String qb, String query,String name) {
       float[][] fq=null;
      Statement stmt=null ;
      Connection connection = null;
      ResultSet  rs;
      try {
        connection = Connect(name);
          stmt = connection.createStatement();
           stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                 ResultSet.CONCUR_UPDATABLE);
          rs = stmt.executeQuery(query);
          rs.last();
          int num = rs.getRow();
          fq= new float[2][num];
          rs.beforeFirst();
          int i = 0;
          while (rs.next()) {
              fq[0][i] = (float)rs.getDouble(qa);
              fq[1][i] = (float)rs.getDouble(qb);
              i++;
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
  public  float[][] getfloatzhi3(String qa,String qb, String qc,String query,String name) {
       float[][] fq=null;
      Statement stmt=null ;
      Connection connection = null;
      ResultSet  rs;
      try {
        connection = Connect(name);
          stmt = connection.createStatement();
           stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                 ResultSet.CONCUR_UPDATABLE);
          rs = stmt.executeQuery(query);
          rs.last();
          int num = rs.getRow();
          fq= new float[3][num];
          rs.beforeFirst();
          int i = 0;
          while (rs.next()) {
              fq[0][i] = (float)rs.getDouble(qa);
              fq[1][i] = (float)rs.getDouble(qb);
              fq[2][i] = (float)rs.getDouble(qc);
              i++;
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
    public  float[][] getfloat5(String qa, String qb,String qc,String qd,String qe,String query,String name) {
        float[][] fq = null;
        Statement stmt=null ;
        Connection connection = null;
        try {
           connection = Connect(name);
          stmt = connection.createStatement();
           stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                 ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            rs.last();
            int num = rs.getRow();
             fq= new float[5][num];
            rs.beforeFirst();
            int i = 0;
            while (rs.next()) {
                fq[0][i] = (float) rs.getDouble(qa);
                fq[1][i] = (float)rs.getDouble(qb);
                fq[2][i] = (float)rs.getDouble(qc);
                fq[3][i] = (float)rs.getDouble(qd);
                fq[4][i] = (float)rs.getDouble(qe);
                i++;
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
    public  float[][] getfloat9(String qa, String qb,String qc,String qd,
                                String qe,String qf,String qg,String qh,String qi,
                                String query,String name) {
        float[][] fq = null;
        Statement stmt=null ;
        Connection connection = null;
        try {
           connection = Connect(name);
          stmt = connection.createStatement();
           stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                 ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            rs.last();
            int num = rs.getRow();
             fq= new float[9][num];
            rs.beforeFirst();
            int i = 0;
            while (rs.next()) {
                fq[0][i] = (float) rs.getDouble(qa);
                fq[1][i] = (float)rs.getDouble(qb);
                fq[2][i] = (float)rs.getDouble(qc);
                fq[3][i] = (float)rs.getDouble(qd);
                fq[4][i] = (float)rs.getDouble(qe);
                fq[5][i] = (float)rs.getDouble(qf);
                fq[6][i] = (float)rs.getDouble(qg);
                fq[7][i] = (float)rs.getDouble(qh);
                fq[8][i] = (float)rs.getDouble(qi);
                i++;
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
    public  float[][] getfloat8(String qa, String qb,String qc,String qd,
                                   String qe,String qf,String qg,String qh,
                                   String query,String name) {
           float[][] fq = null;
           Statement stmt=null ;
           Connection connection = null;
           try {
              connection = Connect(name);
             stmt = connection.createStatement();
              stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                    ResultSet.CONCUR_UPDATABLE);
               ResultSet rs = stmt.executeQuery(query);
               rs.last();
               int num = rs.getRow();
                fq= new float[8][num];
               rs.beforeFirst();
               int i = 0;
               while (rs.next()) {
                   fq[0][i] = (float) rs.getDouble(qa);
                   fq[1][i] = (float)rs.getDouble(qb);
                   fq[2][i] = (float)rs.getDouble(qc);
                   fq[3][i] = (float)rs.getDouble(qd);
                   fq[4][i] = (float)rs.getDouble(qe);
                   fq[5][i] = (float)rs.getDouble(qf);
                   fq[6][i] = (float)rs.getDouble(qg);
                   fq[7][i] = (float)rs.getDouble(qh);
                   i++;
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
    public  float getdanzhi(String q, String query,String name) {
       float fq = 0;
        Statement stmt=null ;
       Connection connection = null;
       try {
           connection = Connect(name);
           stmt = connection.createStatement();
            stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                  ResultSet.CONCUR_UPDATABLE);
           ResultSet rs = stmt.executeQuery(query);

           rs.first();
           fq = (float)rs.getDouble(q);
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
   public  String[] getString(String qa, String query,String name) {
       String[] fq = null;
        Statement stmt=null ;
       Connection connection = null;
       try {
           connection = Connect(name);
           stmt = connection.createStatement();
            stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                  ResultSet.CONCUR_UPDATABLE);
           ResultSet rs = stmt.executeQuery(query);
           rs.last();
           int num = rs.getRow();
           fq = new String[num];
           rs.beforeFirst();
           int i = 0;
           while (rs.next()) {
               fq[i] =rs.getString(qa);
               i++;
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
   public void CreateTable(String query,String name){
       String[] fq = null;
        Statement stmt=null ;
       Connection connection = null;
       try {
           connection = Connect(name);
           stmt = connection.createStatement();
            stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                  ResultSet.CONCUR_UPDATABLE);
           stmt.executeUpdate(query);
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
   }
   public void DropTable(String query,String name){
       String[] fq = null;
        Statement stmt=null ;
       Connection connection = null;
       try {
           connection = Connect(name);
           stmt = connection.createStatement();
            stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                  ResultSet.CONCUR_UPDATABLE);
           stmt.executeUpdate(query);
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
   }
   public void InsertValue(float value[],String title[],String tablename,String name){
       String[] fq = null;
        Statement stmt=null ;
       Connection connection = null;
       try {
           connection = Connect(name);
           stmt = connection.createStatement();
            stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                  ResultSet.CONCUR_UPDATABLE);
            String insertString = "INSERT INTO tempDB VALUES ('"+value[0]+
                                  "','"+value[1]+"','"+value[2]+"','"+value[3]+
                                  "','"+value[4]+"','"+value[5]+"','"+value[6]+
                                  "','"+value[7]+"','"+value[8] + "')";

           /*for(int i=0;i<title.length;i++){
               insertString=insertString+","+value[i];
           }
           insertString=insertString+")";*/
           System.out.print(insertString);

           stmt.executeQuery(insertString);
       } catch (SQLException e) {
           e.printStackTrace();
           System.out.print("没有插值成功");
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
   public  Date[] getDate(String qa,String query,String name) {
        Date[] fq = null;
        //Vector vec=new Vector();
        Statement stmt =null;
       Connection connection=null ;
        try {
           connection =Connect(name);
           stmt = connection.createStatement();
            stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                                  ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            rs.last();
            int num = rs.getRow();
            fq = new Date[num];
            rs.beforeFirst();
            int i = 0;
            //String tempt;
            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            while (rs.next()) {
                try {
                String tempt=rs.getString(qa);

                  fq[i] = simpledateformat.parse(tempt);
                   i++;
                } catch (ParseException ex) {}

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
    public  Vector getDateFlow(String qa,String qb,String query,
                               String name,Date start,int n,int timestep) {
        int length=(n*24)/timestep;
        Date[] fq =new Date[length];
        float []fb=new float[length];
        Vector vec=new Vector();
         Statement stmt =null;
       Connection connection=null;
        try {
           connection =Connect(name);
           stmt = connection.createStatement();
            stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                                  ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            rs.beforeFirst();
            int i = 0;int j=0;
            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd-HH");
            lab:
            while (rs.next()) {
                try {
                    String tempt1=rs.getString(qa);
                    float tempt2=rs.getFloat(qb);
                    Date tempt = simpledateformat.parse(tempt1);
                    //System.out.println("tempt2=="+tempt2+"  tempt=="+tempt+"   start=="+start);

                    if((tempt.after(start))||(tempt.equals(start))){
                        fq[j] =tempt;
                        fb[j]=tempt2;
                        //System.out.println("time=="+fq[j]+"  flow=="+fb[j]);
                        j++;
                    }
                    if(j==length){break lab;}
                } catch (ParseException ex) {}
                i++;
            }
            vec.addElement(fq);
            vec.addElement(fb);
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
        return vec;
    }

}
