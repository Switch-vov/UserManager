package com.pc.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pc.domain.User;
import com.pc.service.UsersService;

public class ManageUsers extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 字符编码
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		// javascript
		out.println("<script type='text/javascript' language='javascript'>");
		// ---跳转到某一页
		out.println("function gotoPageNow(){");
		out.println("var pageNow = document.getElementById('pageNow');");
		out.println("var pageCount = document.getElementById('pageCount');");
		out.println("if(parseInt(pageNow.value) >= 1 && parseInt(pageNow.value) <= parseInt(pageCount.value)){");
		out.println("window.open('/UserManager/ManageUsers?pageNow='+ pageNow.value,'_self');");
		out.println("} else {");
		out.println("window.alert('输入页数过大或过小')");
		out.println("}");
		out.println("}");
		// ---操作确认
		out.println("function confirmOper(){");
		out.println("return window.confirm('真的要执行该操作吗？');");
		out.println("}");

		out.println("</script>");

		out.println("<img src='images/logo.jpg' width='100px'/> 欢迎 ××登录");
		out.println("<a href='/UserManager/MainFrame'>返回主界面</a> <a href='/UserManager/LoginServlet'>安全退出</a>");
		out.println("<hr/>");
		out.println("<h1>管理用户</h1>");

		// 分页变量
		int pageNow = 1; // 当前是第几页
		int pageSize = 3; // 每页显示记录数
		int pageCount = 1; // 共有多少页
		int rowCount = 1; // 总记录

		// 获取pageNow
		String pageNowTemp = request.getParameter("pageNow");
		if (pageNowTemp != null && pageNowTemp != "") {
			pageNow = Integer.parseInt(pageNowTemp);
		}

		// 测试
		// System.out.println(pageNow + " " + pageSize + " " + pageCount);

		UsersService usersService = new UsersService();
		// 获取总页数
		pageCount = usersService.getPageCount(pageSize);
		// 获取分页数据
		ArrayList<User> users = usersService.getUsersByPage(pageNow, pageSize);

		out.println("<table border='1' bordercolor='green' cellspacing='0px' width='700px'>");
		out.println("<tr><th>id</th><th>用户名</th><th>email</th><th>级别</th><th>删除用户</th><th>修改用户</th></tr>");

		for (User u : users) {
			out.println("<tr><td>"
					+ u.getId()
					+ "</td><td>"
					+ u.getName()
					+ "</td><td>"
					+ u.getEmail()
					+ "</td><td>"
					+ u.getGrade()
					+ "</td><td><a onclick='return confirmOper()' href='/UserManager/UserClServlet?type=del&id="
					+ u.getId()
					+ "'>删除用户</a></td><td><a href='/UserManager/UserClServlet?type=gotoUpdateView&id="
					+ u.getId() + "'>修改用户</a></td></tr>");
		}
		out.println("</table>");

		// 首页
		out.println("<a href='/UserManager/ManageUsers?pageNow=1'>[首页]</a>");

		// 上一页
		if (pageNow != 1) {
			out.println("<a href='/UserManager/ManageUsers?pageNow="
					+ (pageNow - 1) + "'>[上一页]</a>");
		}

		// 页数
		for (int i = 1; i <= pageCount; i++) {
			out.println("<a href='/UserManager/ManageUsers?pageNow=" + i
					+ "'>[" + i + " ]</a>");
		}

		// 下一页
		if (pageNow != pageCount) {
			out.println("<a href='/UserManager/ManageUsers?pageNow="
					+ (pageNow + 1) + "'>[下一页]</a>");
		}

		// 尾页
		out.println("<a href='/UserManager/ManageUsers?pageNow=" + pageCount
				+ "'>[尾页]</a>");

		// 当前页/总页数
		out.println("当前页" + pageNow + "/总页数" + pageCount);
		out.println("<br/>");
		// 跳转到第?页
		out.println("跳转到");
		out.println("<input type='text' value='' id='pageNow'/>");
		out.println("<input type='button' value='跳' onclick='gotoPageNow()'/>");
		out.println("<input type='hidden' value='" + pageCount
				+ "' id='pageCount' />");

		// 页尾
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
