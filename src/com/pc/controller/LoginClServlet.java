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
 * 功能：登录信息处理
 * 
 */
public class LoginClServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 字符编码
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");

		// 把user对象保存到session中
		HttpSession session = request.getSession();

		// 接收用户提交的用户id和密码
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String checkCode = request.getParameter("checkcode");

		// 从CreateCode中获取验证码
		String checkCodeConfirm = (String) request.getSession().getAttribute(
				"checkcode");

		// 测试
		// System.out.println(checkCode + " " + checkCodeConfirm);

		// 验证码不正确
		if (!checkCode.equals(checkCodeConfirm)) {
			request.setAttribute("err", "验证码不正确");
			request.getRequestDispatcher("/LoginServlet").forward(request,
					response);
			return;
		}

		// 测试
		// System.out.println(id + " " + password);

		// 创建UserService对象，完成到数据库的验证
		UsersService usersService = new UsersService();
		User user = new User();
		try {
			user.setId(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			// TODO: handle exception
			// 非数字异常处理
			request.setAttribute("err", "id只能为数字");
			request.getRequestDispatcher("/LoginServlet").forward(request,
					response);
			return;
		}
		user.setPassword(password);

		// 验证
		if (usersService.checkUser(user)) {
			// 用户合法

			// 设置user
			user = usersService.getUserById(user.getId() + "");
			session.setAttribute("loginuser", user);

			// 登录人数+1
			String nums = (String) this.getServletContext().getAttribute("nums");
			this.getServletContext().setAttribute("nums", (Integer.parseInt(nums) + 1) + "");
			
			// // 添加lasttime属性
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
			// // 有效期7天
			// cookie.setMaxAge(3600 * 24 * 7);
			// response.addCookie(cookie);
			//
			// // 添加用户名属性
			// request.setAttribute("username", user.getName());
			// 请求转发主界面
			request.getRequestDispatcher("/MainFrame").forward(request,
					response);
		} else {
			// 用户不合法
			// 请求转发到登录界面
			request.setAttribute("err", "用户id或者密码有误!");
			request.getRequestDispatcher("/LoginServlet").forward(request,
					response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
