package com.pc.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pc.domain.User;
import com.pc.service.UsersService;
/**
 * 
 * @author Switch
 * ���ܣ��û���Ϣ����
 * 
 */
public class UserClServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// �ַ�����
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");

		// ����type
		String type = request.getParameter("type");

		UsersService usersService = new UsersService();

		if ("del".equals(type)) {
			// ɾ������
			deleteUser(request, response, usersService);
		} else if ("gotoUpdateView".equals(type)) {
			// ȥ�޸Ľ���
			gotoUpdateView(request, response, usersService);
		} else if ("update".equals(type)) {
			// �޸��û�
			updateUser(request, response, usersService);
		} else if ("gotoAddView".equals(type)) {
			// ȥ����û�ҳ��
			request.getRequestDispatcher("/AddUserView").forward(request,
					response);
		} else if ("add".equals(type)) {
			// ����û�
			addUser(request, response, usersService);
		}
	}

	private void gotoUpdateView(HttpServletRequest request,
			HttpServletResponse response, UsersService usersService)
			throws ServletException, IOException {

		// ����ID
		String id = request.getParameter("id");

		// ͨ��ID��ȡһ��UserBean
		User user = usersService.getUserById(id);

		// Ϊ������һ��Servletʹ��user���󣬿��Է��뵽request����ȥ
		request.setAttribute("userinfo", user);
		// ����ת�����޸Ľ���
		request.getRequestDispatcher("/UpdateUserView").forward(request,
				response);
	}

	private void deleteUser(HttpServletRequest request,
			HttpServletResponse response, UsersService usersService)
			throws ServletException, IOException {
		// ����ID
		String id = request.getParameter("id");
		// ����UsersService���ɾ��
		if (usersService.deleteUser(id)) {
			// ��ת���ɹ�ҳ��
			request.setAttribute("info", "ɾ���ɹ�");
			request.getRequestDispatcher("/OK").forward(request, response);

		} else {
			// ��ת��ʧ��ҳ��
			request.setAttribute("info", "ɾ��ʧ��");
			request.getRequestDispatcher("/Error").forward(request, response);
		}
	}

	private void updateUser(HttpServletRequest request,
			HttpServletResponse response, UsersService usersService)
			throws ServletException, IOException {
		// �����û��µ���Ϣ
		String id = request.getParameter("id");
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String grade = request.getParameter("grade");
		String passwd = request.getParameter("passwd");
		// ��װ��User����
		User user = new User(Integer.parseInt(id), username, passwd, email,
				Integer.parseInt(grade));

		// �޸��û�
		if (usersService.updateUser(user)) {
			// ��ת���ɹ�ҳ��
			request.setAttribute("info", "�޸ĳɹ�");
			request.getRequestDispatcher("/OK").forward(request, response);

		} else {
			// ��ת��ʧ��ҳ��
			request.setAttribute("info", "�޸�ʧ��");
			request.getRequestDispatcher("/Error").forward(request, response);
		}
	}

	private void addUser(HttpServletRequest request,
			HttpServletResponse response, UsersService usersService)
			throws ServletException, IOException {
		// �����û��µ���Ϣ
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String grade = request.getParameter("grade");
		String passwd = request.getParameter("passwd");

		// ��װ��User����
		User user = new User(username, passwd, email, Integer.parseInt(grade));

		// ����û�
		if (usersService.addUser(user)) {
			// ��ת���ɹ�ҳ��
			request.setAttribute("info", "��ӳɹ�");
			request.getRequestDispatcher("/OK").forward(request, response);

		} else {
			// ��ת��ʧ��ҳ��
			request.setAttribute("info", "���ʧ��");
			request.getRequestDispatcher("/Error").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
