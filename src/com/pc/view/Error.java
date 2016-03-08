package com.pc.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 
 * @author Switch
 * 功能：错误提示界面
 * 
 */
public class Error extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 字符编码
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		out.println("<img src='images/logo.jpg' width='100px'/> 欢迎 ××登录");
		out.println("<a href='/UserManager/LoginServlet'>返回重新登录</a>");
		out.println("<hr/>");
		
		// 显示失败消息
		out.println("<h1>" + request.getAttribute("info") + "</h1>");
		out.println("<a href='/UserManager/MainFrame'>返回主界面</a>");
		
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
