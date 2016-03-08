package com.pc.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.pc.domain.User;
import com.pc.utils.SqlHelper;
/**
 * 
 * @author Switch
 * ���ܣ��û������࣬�������û���һЩ����
 * 
 */
public class UsersService {

	// ����û�
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

	// �޸��û�
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

	// ɾ���û�
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

	// ��֤�û��Ƿ�Ϸ��ķ���
	public boolean checkUser(User user) {

		// ʹ��SqlHelper��ɲ�ѯ����
		String sql = "select * from users where id=? and passwd=?";
		String[] parameters = { user.getId() + "", user.getPassword() };
		ResultSet rs = SqlHelper.executeQuery(sql, parameters);
		// ����rs���жϸ��û��Ƿ����
		try {
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// �ر���Դ
			SqlHelper.close(rs, SqlHelper.getPs(), SqlHelper.getCt());
		}
		return false;
	}

	// ��ȡpageCount
	public int getPageCount(int pageSize) {
		int pageCount = 1;
		String sql = "select count(*) from users";
		ResultSet rs = SqlHelper.executeQuery(sql, null);
		try {
			// �α�����
			rs.next();
			int rowCount = rs.getInt(1);
			// ���ж���ҳ
			pageCount = (rowCount - 1) / pageSize + 1;

			// ���ַ�ҳ�㷨
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

	// ��ҳ����
	public ArrayList<User> getUsersByPage(int pageNow, int pageSize) {
		ArrayList<User> users = new ArrayList<User>();

		// ��ѯ���
		String sql = "select * from "
				+ "(select u.*,rownum rn from (select * from users order by id) u where rownum <=?) "
				+ "where rn >=?";

		// ����
		// int pageUp = pageSize * pageNow;
		// int pageDown = (pageNow - 1) * pageSize + 1;
		// System.out.println(pageDown + " " + pageUp + " " + pageNow + " " +
		// pageSize);
		String[] parameters = { (pageSize * pageNow) + "",
				((pageNow - 1) * pageSize + 1) + "" };
		ResultSet rs = SqlHelper.executeQuery(sql, parameters);

		// ���η�װResultSet -> User���� -> ArrayList
		try {
			while (rs.next()) {
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setName(rs.getString("username"));
				u.setEmail(rs.getString("email"));
				u.setGrade(rs.getInt("grade"));
				// ��u���뵽al��
				users.add(u);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// �ر���Դ
			SqlHelper.close(rs, SqlHelper.getPs(), SqlHelper.getCt());
		}
		return users;
	}

	// ͨ��id��ȡ�û�����
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
