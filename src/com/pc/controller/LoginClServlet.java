package com.pc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pc.domain.User;
import com.pc.service.UsersService;
/**
 * 
 * @author Switch
 * ���ܣ���¼��Ϣ����
 * 
 */
public class LoginClServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// �ַ�����
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");

		// ��user���󱣴浽session��
		HttpSession session = request.getSession();

		// �����û��ύ���û�id������
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String checkCode = request.getParameter("checkcode");

		// ��CreateCode�л�ȡ��֤��
		String checkCodeConfirm = (String) request.getSession().getAttribute(
				"checkcode");

		// ����
		// System.out.println(checkCode + " " + checkCodeConfirm);

		// ��֤�벻��ȷ
		if (!checkCode.equals(checkCodeConfirm)) {
			request.setAttribute("err", "��֤�벻��ȷ");
			request.getRequestDispatcher("/LoginServlet").forward(request,
					response);
			return;
		}

		// ����
		// System.out.println(id + " " + password);

		// ����UserService������ɵ����ݿ����֤
		UsersService usersService = new UsersService();
		User user = new User();
		try {
			user.setId(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			// TODO: handle exception
			// �������쳣����
			request.setAttribute("err", "idֻ��Ϊ����");
			request.getRequestDispatcher("/LoginServlet").forward(request,
					response);
			return;
		}
		user.setPassword(password);

		// ��֤
		if (usersService.checkUser(user)) {
			// �û��Ϸ�

			// ����user
			user = usersService.getUserById(user.getId() + "");
			session.setAttribute("loginuser", user);

			// ��¼����+1
			String nums = (String) this.getServletContext().getAttribute("nums");
			this.getServletContext().setAttribute("nums", (Integer.parseInt(nums) + 1) + "");
			
			// // ���lasttime����
			// Cookie[] cookies = request.getCookies();
			// if (cookies != null) {
			// for (Cookie cookie : cookies) {
			// String name = cookie.getName();
			// if("lasttime".equals(name)){
			// request.setAttribute("lasttime", cookie.getValue());
			// break;
			// }
			// }
			// }
			//
			// SimpleDateFormat simpleDateFormat = new
			// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// String nowDate = simpleDateFormat.format(new Date());
			// Cookie cookie = new Cookie("lasttime", nowDate);
			// // ��Ч��7��
			// cookie.setMaxAge(3600 * 24 * 7);
			// response.addCookie(cookie);
			//
			// // ����û�������
			// request.setAttribute("username", user.getName());
			// ����ת��������
			request.getRequestDispatcher("/MainFrame").forward(request,
					response);
		} else {
			// �û����Ϸ�
			// ����ת������¼����
			request.setAttribute("err", "�û�id������������!");
			request.getRequestDispatcher("/LoginServlet").forward(request,
					response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
