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
 * ���ܣ�������ʾ����
 * 
 */
public class Error extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// �ַ�����
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		out.println("<img src='images/logo.jpg' width='100px'/> ��ӭ ������¼");
		out.println("<a href='/UserManager/LoginServlet'>�������µ�¼</a>");
		out.println("<hr/>");
		
		// ��ʾʧ����Ϣ
		out.println("<h1>" + request.getAttribute("info") + "</h1>");
		out.println("<a href='/UserManager/MainFrame'>����������</a>");
		
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
