package com.pc.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.sql.*;

/**
 * 
 * @author Switch
 * 操作数据库的工作类
 *
 */
public class SqlHelper {
	// 定义变量
	private static Connection ct = null;
	// 大多数情况下用preparedstatement替代statement
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;

	// 连接数据库的参数
	private static String url = "";
	private static String username = "";
	private static String driver = "";
	private static String passwd = "";

	private static CallableStatement cs = null;

	public static CallableStatement getCs() {
		return cs;
	}

	// 读配置文件变量
	private static Properties pp = null;
	private static InputStream fis = null;
	// 加载驱动，只需要一次，用静态代码块
	static {
		try {
			// 从dbinfo.properties读取配置
			pp = new Properties();
			// 在java项目可以直接使用流
			// FileInputStream fis = new FileInputStream("dbinfo.properties");
			
			// 在java web项目要使用类加载器(因为类加载器读取资源的时候，默认的主目录是src目录)
			fis = SqlHelper.class.getClassLoader().getResourceAsStream(
					"dbinfo.properties");
			// fis = new FileInputStream();
			pp.load(fis);
			url = pp.getProperty("url");
			username = pp.getProperty("username");
			driver = pp.getProperty("driver");
			passwd = pp.getProperty("password");

			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 垃圾回收机制回收
			fis = null;
		}

	}

	// 得到连接
	public static Connection getConnection() {
		try {
			ct = DriverManager.getConnection(url, username, passwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ct;
	}

	// *************callPro1存储过程函数1*************
	public static CallableStatement callPro1(String sql, String[] parameters) {
		try {
			ct = getConnection();
			cs = ct.prepareCall(sql);
			if (parameters != null) {
				for (int i = 0; i < parameters.length; i++) {
					cs.setObject(i + 1, parameters[i]);
				}
			}
			cs.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			close(rs, cs, ct);
		}
		return cs;
	}

	// *******************callpro2存储过程2************************
	public static CallableStatement callPro2(String sql, String[] inparameters,
			Integer[] outparameters) {
		try {
			ct = getConnection();
			cs = ct.prepareCall(sql);
			if (inparameters != null) {
				for (int i = 0; i < inparameters.length; i++) {
					cs.setObject(i + 1, inparameters[i]);
				}
			}
			// cs.registerOutparameter(2,oracle.jdbc.OracleTypes.CURSOR);
			if (outparameters != null) {
				for (int i = 0; i < outparameters.length; i++) {
					cs.registerOutParameter(inparameters.length + 1 + i,
							outparameters[i]);
				}
			}
			cs.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {

		}
		return cs;
	}

	// select语句
	public static ResultSet executeQuery(String sql, String[] parameters) {
		try {
			ct = getConnection();
			ps = ct.prepareStatement(sql);
			if (parameters != null) {
				for (int i = 0; i < parameters.length; i++) {
					ps.setString(i + 1, parameters[i]);
				}
			}
			rs = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {

		}
		return rs;
	}
	
	// select语句2(更安全)----规则：哪里使用资源，哪里关闭资源
	public static ArrayList executeQuery2(String sql, String[] parameters) {
		ArrayList result = null;
		try {
			ct = getConnection();
			ps = ct.prepareStatement(sql);
			if (parameters != null) {
				for (int i = 0; i < parameters.length; i++) {
					ps.setString(i + 1, parameters[i]);
				}
			}
			rs = ps.executeQuery();
			// 获取结果原始信息
			ResultSetMetaData resultSetMetaData = rs.getMetaData();
			int column = resultSetMetaData.getColumnCount();
			
			result = new ArrayList();
			// 一行数据
			while(rs.next()){
				// 对象数组，表示一行数据
				Object[] ob = new Object[column];
				for(int i = 1; i <= column; i++){
					// 一项数据
					ob[i - 1] = rs.getObject(i);
				}
				result.add(ob);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			SqlHelper.close(rs, ps, ct);
		}
		return result;
	}

	public static Connection getCt() {
		return ct;
	}

	public static PreparedStatement getPs() {
		return ps;
	}

	public static ResultSet getRs() {
		return rs;
	}

	// 多个 update/ delete/ insert语句 [考虑事务]
	public static void executeUpdate2(String[] sql, String[][] parameters) {
		try {
			ct = getConnection();
			ct.setAutoCommit(false);

			for (int i = 0; i < sql.length; i++) {

				if (null != parameters[i]) {
					ps = ct.prepareStatement(sql[i]);
					for (int j = 0; j < parameters[i].length; j++) {
						ps.setString(j + 1, parameters[i][j]);
					}
					ps.executeUpdate();
				}

			}

			ct.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				ct.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());
		} finally {
			close(rs, ps, ct);
		}

	}

	// 一个update、delete、insert [不考虑事务]
	// sql格式：update 表名 set 字段名 =？where 字段=？
	// parameter神应该是（”abc“,23）
	public static void executeUpdate(String sql, String[] parameters) {
		try {
			ct = getConnection();
			ps = ct.prepareStatement(sql);
			if (parameters != null) {
				for (int i = 0; i < parameters.length; i++) {
					ps.setString(i + 1, parameters[i]);
				}

			}
			ps.executeUpdate();
		} catch (Exception e) {
			// 开发阶段使用
			e.printStackTrace();
			// 抛出异常
			// 可以处理，也可以不处理
			throw new RuntimeException(e.getMessage());
		} finally {
			close(rs, ps, ct);
		}
	}

	public static void close(ResultSet rs, Statement ps, Connection ct) {
		// 关闭资源(先开后关)
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs = null;
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ps = null;
		}
		if (null != ct) {
			try {
				ct.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ct = null;
		}
	}
}
