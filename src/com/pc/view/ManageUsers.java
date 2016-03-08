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
		// �ַ�����
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		// javascript
		out.println("<script type='text/javascript' language='javascript'>");
		// ---��ת��ĳһҳ
		out.println("function gotoPageNow(){");
		out.println("var pageNow = document.getElementById('pageNow');");
		out.println("var pageCount = document.getElementById('pageCount');");
		out.println("if(parseInt(pageNow.value) >= 1 && parseInt(pageNow.value) <= parseInt(pageCount.value)){");
		out.println("window.open('/UserManager/ManageUsers?pageNow='+ pageNow.value,'_self');");
		out.println("} else {");
		out.println("window.alert('����ҳ��������С')");
		out.println("}");
		out.println("}");
		// ---����ȷ��
		out.println("function confirmOper(){");
		out.println("return window.confirm('���Ҫִ�иò�����');");
		out.println("}");

		out.println("</script>");

		out.println("<img src='images/logo.jpg' width='100px'/> ��ӭ ������¼");
		out.println("<a href='/UserManager/MainFrame'>����������</a> <a href='/UserManager/LoginServlet'>��ȫ�˳�</a>");
		out.println("<hr/>");
		out.println("<h1>�����û�</h1>");

		// ��ҳ����
		int pageNow = 1; // ��ǰ�ǵڼ�ҳ
		int pageSize = 3; // ÿҳ��ʾ��¼��
		int pageCount = 1; // ���ж���ҳ
		int rowCount = 1; // �ܼ�¼

		// ��ȡpageNow
		String pageNowTemp = request.getParameter("pageNow");
		if (pageNowTemp != null && pageNowTemp != "") {
			pageNow = Integer.parseInt(pageNowTemp);
		}

		// ����
		// System.out.println(pageNow + " " + pageSize + " " + pageCount);

		UsersService usersService = new UsersService();
		// ��ȡ��ҳ��
		pageCount = usersService.getPageCount(pageSize);
		// ��ȡ��ҳ����
		ArrayList<User> users = usersService.getUsersByPage(pageNow, pageSize);

		out.println("<table border='1' bordercolor='green' cellspacing='0px' width='700px'>");
		out.println("<tr><th>id</th><th>�û���</th><th>email</th><th>����</th><th>ɾ���û�</th><th>�޸��û�</th></tr>");

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
					+ "'>ɾ���û�</a></td><td><a href='/UserManager/UserClServlet?type=gotoUpdateView&id="
					+ u.getId() + "'>�޸��û�</a></td></tr>");
		}
		out.println("</table>");

		// ��ҳ
		out.println("<a href='/UserManager/ManageUsers?pageNow=1'>[��ҳ]</a>");

		// ��һҳ
		if (pageNow != 1) {
			out.println("<a href='/UserManager/ManageUsers?pageNow="
					+ (pageNow - 1) + "'>[��һҳ]</a>");
		}

		// ҳ��
		for (int i = 1; i <= pageCount; i++) {
			out.println("<a href='/UserManager/ManageUsers?pageNow=" + i
					+ "'>[" + i + " ]</a>");
		}

		// ��һҳ
		if (pageNow != pageCount) {
			out.println("<a href='/UserManager/ManageUsers?pageNow="
					+ (pageNow + 1) + "'>[��һҳ]</a>");
		}

		// βҳ
		out.println("<a href='/UserManager/ManageUsers?pageNow=" + pageCount
				+ "'>[βҳ]</a>");

		// ��ǰҳ/��ҳ��
		out.println("��ǰҳ" + pageNow + "/��ҳ��" + pageCount);
		out.println("<br/>");
		// ��ת����?ҳ
		out.println("��ת��");
		out.println("<input type='text' value='' id='pageNow'/>");
		out.println("<input type='button' value='��' onclick='gotoPageNow()'/>");
		out.println("<input type='hidden' value='" + pageCount
				+ "' id='pageCount' />");

		// ҳβ
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
