package DateManage;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import java.util.Vector;

/**
 * <p>
 * 保存决策表
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
 * @author not attributable
 * @version 1.0
 */
public class SaveDecisionList {
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
   * // * @param connection Connection
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
   * // * @param connection Connection
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
   * 保存决策规则集
   */
  public Boolean SaveRuleList(Vector vec, String biao, String name) {
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
