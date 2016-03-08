package com.pc.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pc.domain.User;
/**
 * 
 * @author Switch
 * 功能：主界面
 * 
 */
public class MainFrame extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 字符编码
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		// 取出session
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginuser");

		// 如果未登录
		if (user == null) {
			// 设置错误信息
			request.setAttribute("err", "请输入用户名和密码登录");
			request.getRequestDispatcher("/LoginServlet").forward(request,
					response);
			// 必须要带return
			return;
		}

		out.println("<img src='images/logo.jpg' width='100px'/> 欢迎"
				+ user.getName() + "登录");
		// String lasttime = (String)request.getAttribute("lasttime");
		// if (lasttime != null) {
		// out.println(" 上次登录时间：" + lasttime);
		// } else {
		// out.println(" 这是您7天内第一次登录");
		// }
		out.println("<a href='/UserManager/LoginServlet'>返回重新登录</a>");
		out.println(" 浏览量为：" + this.getServletContext().getAttribute("nums"));
		out.println("<hr/>");

		out.println("<h3>请选择您要进行的操作</h3>");
		out.println("<a href='/UserManager/ManageUsers'>管理用户</a><br/>");
		out.println("<a href='/UserManager/UserClServlet?type=gotoAddView'>添加用户</a><br/>");
		out.println("<a href=''>查找用户</a><br/>");
		out.println("<a href=''>退出系统</a>");
		out.println("<hr/>");
		out.println("<img src='images/mylogo.jpg' />");

		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
