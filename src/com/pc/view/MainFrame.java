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
 * ���ܣ�������
 * 
 */
public class MainFrame extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// �ַ�����
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		// ȡ��session
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginuser");

		// ���δ��¼
		if (user == null) {
			// ���ô�����Ϣ
			request.setAttribute("err", "�������û����������¼");
			request.getRequestDispatcher("/LoginServlet").forward(request,
					response);
			// ����Ҫ��return
			return;
		}

		out.println("<img src='images/logo.jpg' width='100px'/> ��ӭ"
				+ user.getName() + "��¼");
		// String lasttime = (String)request.getAttribute("lasttime");
		// if (lasttime != null) {
		// out.println(" �ϴε�¼ʱ�䣺" + lasttime);
		// } else {
		// out.println(" ������7���ڵ�һ�ε�¼");
		// }
		out.println("<a href='/UserManager/LoginServlet'>�������µ�¼</a>");
		out.println(" �����Ϊ��" + this.getServletContext().getAttribute("nums"));
		out.println("<hr/>");

		out.println("<h3>��ѡ����Ҫ���еĲ���</h3>");
		out.println("<a href='/UserManager/ManageUsers'>�����û�</a><br/>");
		out.println("<a href='/UserManager/UserClServlet?type=gotoAddView'>����û�</a><br/>");
		out.println("<a href=''>�����û�</a><br/>");
		out.println("<a href=''>�˳�ϵͳ</a>");
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
