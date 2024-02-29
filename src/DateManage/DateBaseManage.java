package DateManage;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.Statement;
import java.util.Vector;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;

/**
 * <p>
 * 数据库操作
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author xw
 * @version 1.0
 */
public class DateBaseManage {
  /**
   * 连接数据库
   * 
   * @param name String
   *             待连接数据库名
   * @return Connection
   *         返回 Connection对象
   */
  public Connection Connect(String name) {
    Connection connection = null;
    try {
      String driverName = "sun.jdbc.odbc.JdbcOdbcDriver";
      Class.forName(driverName); // 注册驱动
    } catch (ClassNotFoundException e) {
      System.out.println("没有找到文件或表格");
    }
    try {
      String dbURL = "jdbc:odbc:" + name;
      connection = DriverManager.getConnection(dbURL, "sa", "");
    } catch (SQLException e) {
      System.out.println("出现SQL异常");
    }
    return connection;
  }

  /**
   * 判断数据库中表是否存在
   * 
   * @param biao String
   *             连接数据库中的表名
   * @param name String
   *             连接的数据库名
   * @return Boolean
   *         返回 true表示biao存在，false为不存在
   */
  public Boolean judgeExist(String biao, String name) {
    Boolean flag = true;
    Connection connection = null;
    ResultSet rs = null;
    connection = Connect(name);
    try {
      rs = connection.getMetaData().getTables(null, null, biao, null);
      if (rs.next()) {
        flag = true;
      } else {
        flag = false;
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
      try {
        flag = false;
        JOptionPane.showMessageDialog(null, "数据库表存在性判断出错！",
            "信息提示", JOptionPane.PLAIN_MESSAGE);
      } catch (Exception e) {
      }
    } finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return flag;
  }

  /**
   * 判断数据库中表是否存在
   * 若存在则删除该表，然后创建表
   * 若不存在，直接创建表
   * biao 表名
   * // * @param Object colname
   * colname 表示创建表中的列名
   * 各列属性均为VARCHAR(50)
   *
   */
  public Boolean creatTable(Object colname[], String biao, String name) {
    Boolean flag = true;
    Statement stmt = null;
    Connection connection = null;
    ResultSet rs = null;
    connection = Connect(name);
    try {
      rs = connection.getMetaData().getTables(null, null, biao, null);
      stmt = connection.createStatement();
      stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
          ResultSet.CONCUR_UPDATABLE);
      connection.setAutoCommit(false);
      if (rs.next()) {
        rs.close();
        stmt.execute("DROP TABLE " + biao);
      }
      StringBuffer sqlString1 = new StringBuffer("CREATE TABLE " + biao + " (");
      for (int i = 0; i < colname.length; i++) {
        String COLNAME = String.valueOf(colname[i]);
        sqlString1.append(COLNAME);
        sqlString1.append("  VARCHAR(50),");
      }
      sqlString1.append(")");
      stmt.execute(sqlString1.toString());
      // stmt.executeBatch();
      connection.commit();
      connection.setAutoCommit(true);
      Boolean isautocommit = connection.getAutoCommit();
      if (isautocommit) {
        JOptionPane.showMessageDialog(null, "数据库表删除/创建成功！",
            "信息提示", JOptionPane.PLAIN_MESSAGE);
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
      try {
        connection.rollback();
        flag = false;
        JOptionPane.showMessageDialog(null, "数据库表存在性判断出错！",
            "信息提示", JOptionPane.PLAIN_MESSAGE);
      } catch (Exception e) {
      }
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
    return flag;
  }

  /**
   * 判断数据库中表是否存在
   * 若存在则删除该表
   * biao 表名
   */
  public Boolean dropTable(String biao, String name) {
    Boolean flag = true;
    Statement stmt = null;
    Connection connection = null;
    ResultSet rs = null;
    connection = Connect(name);
    try {
      rs = connection.getMetaData().getTables(null, null, biao, null);
      stmt = connection.createStatement();
      stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
          ResultSet.CONCUR_UPDATABLE);
      connection.setAutoCommit(false);
      if (rs.next()) {
        rs.close();
        stmt.execute("DROP TABLE " + biao);
      }
      connection.commit();
      connection.setAutoCommit(true);
      Boolean isautocommit = connection.getAutoCommit();
      if (isautocommit) {
        JOptionPane.showMessageDialog(null, "数据库表删除成功！",
            "信息提示", JOptionPane.PLAIN_MESSAGE);
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
      try {
        connection.rollback();
        flag = false;
        JOptionPane.showMessageDialog(null, "数据库表删除出错！",
            "信息提示", JOptionPane.PLAIN_MESSAGE);
      } catch (Exception e) {
      }
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
    return flag;
  }

  /**
   * 获得数据库中表的列名
   * // * @param query String
   * 查询语句
   * 
   * @param name String
   *             表存在的数据库名
   * @return Vector
   *         以Vector的形式返回数据库表列名
   */
  public Vector getHead(String biao, String name) {

    Statement stmt = null;
    Connection connection = null;
    Vector head = new Vector();
    try {
      connection = Connect(name);
      stmt = connection.createStatement();
      stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
          ResultSet.CONCUR_UPDATABLE);
      String query = "SELECT * FROM " + biao;
      ResultSet rs = stmt.executeQuery(query);
      ResultSetMetaData rsmd = rs.getMetaData();
      for (int i = 1; i <= rsmd.getColumnCount(); ++i) {
        head.addElement(rsmd.getColumnName(i));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, "数据库表列名获取失败！",
          "信息提示", JOptionPane.PLAIN_MESSAGE);
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
    return head;
  }

  /**
   * 获得query查询语句查出的行
   * 
   * @param query String
   *              查询语句
   * @param name  String
   *              表存在的数据库名
   * @return Vector
   *         以Vector形式返回查得得各行
   */
  public Vector getRow(String query, String name) {
    Statement stmt = null;
    Connection connection = null;
    Vector rows = new Vector();
    try {
      connection = Connect(name);
      stmt = connection.createStatement();
      stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
          ResultSet.CONCUR_UPDATABLE);
      ResultSet rs = stmt.executeQuery(query);
      ResultSetMetaData rsmd = rs.getMetaData();
      while (rs.next()) {
        Vector currentRow = new Vector();
        for (int i = 1; i <= rsmd.getColumnCount(); ++i) {
          String p = rsmd.getColumnClassName(i);
          if (p.equals("java.lang.Integer")) {
            currentRow.addElement(rs.getString(i));
          } else if (p.equals("java.sql.Timestamp")) {
            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
            currentRow.addElement(simpledateformat.format(rs.getDate(i)));
          } else if (p.equals("java.sqlFloat")) {
            currentRow.addElement(String.valueOf(new BigDecimal(rs.getFloat(i)).setScale(2, BigDecimal.ROUND_HALF_UP)));
          } else if (p.equals("java.lang.Double")) {
            currentRow
                .addElement(String.valueOf(new BigDecimal(rs.getDouble(i)).setScale(2, BigDecimal.ROUND_HALF_UP)));
          } else if (p.equals("java.lang.String")) {
            currentRow.addElement(rs.getString(i));
          }
        }
        rows.addElement(currentRow);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, "数据库表查询出错！",
          "信息提示", JOptionPane.PLAIN_MESSAGE);
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
    return rows;
  }

  /**
   * 保存数据
   * 
   * @param vec  Vector
   *             vec为一个2维数据集
   *             vec中包含有各行的值
   * @param biao String
   * @param name String
   * @return Boolean
   */
  public Boolean SaveVector(Vector vec, String biao, String name) {
    Boolean sucess = true;
    Statement stmt = null;
    Connection connection = null;
    connection = Connect(name);
    try {
      stmt = connection.createStatement();
      stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
          ResultSet.CONCUR_UPDATABLE);
      SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
      connection.setAutoCommit(false);
      Vector tempvec = new Vector();
      for (int i = 0; i < vec.size(); i++) {
        tempvec = (Vector) vec.elementAt(i);
        String insert = "INSERT INTO " + biao + " VALUES ('";
        StringBuffer sqlString2 = new StringBuffer(insert);
        for (int j = 0; j < tempvec.size() - 1; j++) {
          sqlString2.append(String.valueOf(tempvec.elementAt(j)));
          sqlString2.append("','");
        }
        sqlString2.append(String.valueOf(tempvec.elementAt(tempvec.size() - 1)));
        sqlString2.append("')");
        stmt.addBatch(sqlString2.toString());
        tempvec.removeAllElements();
      }
      stmt.executeBatch();
      connection.commit();
      connection.setAutoCommit(true);
      Boolean isautocommit = connection.getAutoCommit();
      if (isautocommit) {
        JOptionPane.showMessageDialog(null, "保存成功",
            "信息提示", JOptionPane.PLAIN_MESSAGE);
      }
    } catch (Exception e) {
      e.printStackTrace();
      try {
        connection.rollback();
        sucess = false;
        JOptionPane.showMessageDialog(null, "保存失败",
            "警告信息", JOptionPane.WARNING_MESSAGE);
      } catch (Exception ex) {
      }
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

}
