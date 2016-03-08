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
 * 功能：登录界面
 * 
 */
public class LoginServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		// 用户登录界面
		
		out.println("<img src='images/logo.jpg' width='100px'/>");
		out.println("<hr/>");
		out.println("<h1>用户登录</h1>");
		// action 格式 /web应用名/Servlet下的url
		out.println("<form action='/UserManager/LoginClServlet' method='post'>");
		out.println("用户id：<input type='text' name='id' /><br/>");
		out.println("密&nbsp;&nbsp;&nbsp;码：<input type='password' name='password' /><br/>");
		out.println("验证码：<input type='text' name='checkcode' /><img src='/UserManager/CreateCode' style='position: relative; top: 6px;' /></br/>");
		out.println("<input type='submit' value='登录' />");
		out.println("</form>");
		String errInfo = (String) request.getAttribute("err");
		if(errInfo != null){
			out.println("<font color='red'>" + errInfo + "</font>");
		}
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
