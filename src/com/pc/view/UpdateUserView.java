package com.pc.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pc.domain.User;
/**
 * 
 * @author Switch
 * 功能：更新用户信息界面
 * 
 */
public class UpdateUserView extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 字符编码
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		// 获取从控制器UserClServlet传递过来的User对象
		User user = (User) request.getAttribute("userinfo");

		out.println("<img src='images/logo.jpg' width='100px'/> 欢迎 ××登录");
		out.println("<a href='/UserManager/MainFrame'>返回主界面</a> <a href='/UserManager/LoginServlet'>安全退出</a>");
		out.println("<hr/>");

		// 显示
		out.println("<h1>修改用户 </h1>");
		out.println("<form action='/UserManager/UserClServlet?type=update' method='post'>");
		out.println("<table border='1' bordercolor='green' cellspacing='0px' width='300px'>");
		out.println("<tr><td>ID</td><td><input type='text' name='id' value='"
				+ user.getId() + "' readonly/></td></tr>");
		out.println("<tr><td>用户名</td><td><input type='text' name='username' value='"
				+ user.getName() + "' /></td></tr>");
		out.println("<tr><td>email</td><td><input type='text' name='email' value='"
				+ user.getEmail() + "' /></td></tr>");
		out.println("<tr><td>级别</td><td><input type='text' name='grade' value='"
				+ user.getGrade() + "' /></td></tr>");
		out.println("<tr><td>密码</td><td><input type='password' name='passwd' value='"
				+ user.getPassword() + "' /></td></tr>");
		out.println("<tr><td><input type='submit' value='修改用户' /></td><td><input type='reset' value='重新填写' /></td></tr>");
		out.println("</table>");
		out.println("</from>");

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
