package com.pc.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.pc.domain.User;
import com.pc.utils.SqlHelper;
/**
 * 
 * @author Switch
 * 功能：用户服务类，包含对用户的一些方法
 * 
 */
public class UsersService {

	// 添加用户
	public boolean addUser(User user) {
		boolean b = true;
		String sql = "insert into users values(users_seq.nextval,?,?,?,?)";
		String[] parameters = { user.getName(), user.getEmail(),
				user.getGrade() + "", user.getPassword() };
		try {
			SqlHelper.executeUpdate(sql, parameters);
		} catch (Exception e) {
			// TODO: handle exception
			b = false;
		}

		return b;
	}

	// 修改用户
	public boolean updateUser(User user) {
		boolean b = true;
		String sql = "update users set username=?,email=?,grade=?,passwd=? where id=?";
		String[] parameters = { user.getName(), user.getEmail(),
				user.getGrade() + "", user.getPassword(), user.getId() + "" };
		try {
			SqlHelper.executeUpdate(sql, parameters);
		} catch (Exception e) {
			// TODO: handle exception
			b = false;
		}

		return b;
	}

	// 删除用户
	public boolean deleteUser(String id) {
		boolean b = true;
		String sql = "delete from users where id=?";
		String[] parameters = { id };

		try {
			SqlHelper.executeUpdate(sql, parameters);
		} catch (Exception e) {
			// TODO: handle exception
			b = false;
		}
		return b;
	}

	// 验证用户是否合法的方法
	public boolean checkUser(User user) {

		// 使用SqlHelper完成查询任务
		String sql = "select * from users where id=? and passwd=?";
		String[] parameters = { user.getId() + "", user.getPassword() };
		ResultSet rs = SqlHelper.executeQuery(sql, parameters);
		// 根据rs来判断该用户是否存在
		try {
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭资源
			SqlHelper.close(rs, SqlHelper.getPs(), SqlHelper.getCt());
		}
		return false;
	}

	// 获取pageCount
	public int getPageCount(int pageSize) {
		int pageCount = 1;
		String sql = "select count(*) from users";
		ResultSet rs = SqlHelper.executeQuery(sql, null);
		try {
			// 游标下移
			rs.next();
			int rowCount = rs.getInt(1);
			// 共有多少页
			pageCount = (rowCount - 1) / pageSize + 1;

			// 三种分页算法
			// if (rowCount % pageSize == 0) {
			// pageCount = rowCount / pageSize;
			// } else {
			// pageCount = rowCount / pageSize + 1;
			// }
			//
			// pageCount = (rowCount % pageSize == 0) ? (rowCount / pageSize)
			// : (rowCount / pageSize + 1);
			//
			// pageCount = (rowCount - 1) / pageSize + 1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			SqlHelper.close(rs, SqlHelper.getPs(), SqlHelper.getCt());
		}
		return pageCount;
	}

	// 分页操作
	public ArrayList<User> getUsersByPage(int pageNow, int pageSize) {
		ArrayList<User> users = new ArrayList<User>();

		// 查询语句
		String sql = "select * from "
				+ "(select u.*,rownum rn from (select * from users order by id) u where rownum <=?) "
				+ "where rn >=?";

		// 测试
		// int pageUp = pageSize * pageNow;
		// int pageDown = (pageNow - 1) * pageSize + 1;
		// System.out.println(pageDown + " " + pageUp + " " + pageNow + " " +
		// pageSize);
		String[] parameters = { (pageSize * pageNow) + "",
				((pageNow - 1) * pageSize + 1) + "" };
		ResultSet rs = SqlHelper.executeQuery(sql, parameters);

		// 二次封装ResultSet -> User对象 -> ArrayList
		try {
			while (rs.next()) {
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setName(rs.getString("username"));
				u.setEmail(rs.getString("email"));
				u.setGrade(rs.getInt("grade"));
				// 将u加入到al中
				users.add(u);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭资源
			SqlHelper.close(rs, SqlHelper.getPs(), SqlHelper.getCt());
		}
		return users;
	}

	// 通过id获取用户数据
	public User getUserById(String id) {
		User u = new User();
		String sql = "select * from users where id=?";
		String[] parameters = { id };
		ResultSet rs = SqlHelper.executeQuery(sql, parameters);
		try {
			if (rs.next()) {
				u.setId(rs.getInt("id"));
				u.setName(rs.getString("username"));
				u.setEmail(rs.getString("email"));
				u.setGrade(rs.getInt("grade"));
				u.setPassword(rs.getString("passwd"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			SqlHelper.close(rs, SqlHelper.getPs(), SqlHelper.getCt());
		}
		return u;
	}
}
