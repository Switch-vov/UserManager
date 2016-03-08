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
 * 功能：用户信息处理
 * 
 */
public class UserClServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 字符编码
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");

		// 接收type
		String type = request.getParameter("type");

		UsersService usersService = new UsersService();

		if ("del".equals(type)) {
			// 删除操作
			deleteUser(request, response, usersService);
		} else if ("gotoUpdateView".equals(type)) {
			// 去修改界面
			gotoUpdateView(request, response, usersService);
		} else if ("update".equals(type)) {
			// 修改用户
			updateUser(request, response, usersService);
		} else if ("gotoAddView".equals(type)) {
			// 去添加用户页面
			request.getRequestDispatcher("/AddUserView").forward(request,
					response);
		} else if ("add".equals(type)) {
			// 添加用户
			addUser(request, response, usersService);
		}
	}

	private void gotoUpdateView(HttpServletRequest request,
			HttpServletResponse response, UsersService usersService)
			throws ServletException, IOException {

		// 接收ID
		String id = request.getParameter("id");

		// 通过ID获取一个UserBean
		User user = usersService.getUserById(id);

		// 为了让下一个Servlet使用user对象，可以放入到request域中去
		request.setAttribute("userinfo", user);
		// 请求转发至修改界面
		request.getRequestDispatcher("/UpdateUserView").forward(request,
				response);
	}

	private void deleteUser(HttpServletRequest request,
			HttpServletResponse response, UsersService usersService)
			throws ServletException, IOException {
		// 接收ID
		String id = request.getParameter("id");
		// 调用UsersService完成删除
		if (usersService.deleteUser(id)) {
			// 跳转到成功页面
			request.setAttribute("info", "删除成功");
			request.getRequestDispatcher("/OK").forward(request, response);

		} else {
			// 跳转到失败页面
			request.setAttribute("info", "删除失败");
			request.getRequestDispatcher("/Error").forward(request, response);
		}
	}

	private void updateUser(HttpServletRequest request,
			HttpServletResponse response, UsersService usersService)
			throws ServletException, IOException {
		// 接收用户新的信息
		String id = request.getParameter("id");
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String grade = request.getParameter("grade");
		String passwd = request.getParameter("passwd");
		// 封装成User对象
		User user = new User(Integer.parseInt(id), username, passwd, email,
				Integer.parseInt(grade));

		// 修改用户
		if (usersService.updateUser(user)) {
			// 跳转到成功页面
			request.setAttribute("info", "修改成功");
			request.getRequestDispatcher("/OK").forward(request, response);

		} else {
			// 跳转到失败页面
			request.setAttribute("info", "修改失败");
			request.getRequestDispatcher("/Error").forward(request, response);
		}
	}

	private void addUser(HttpServletRequest request,
			HttpServletResponse response, UsersService usersService)
			throws ServletException, IOException {
		// 接收用户新的信息
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String grade = request.getParameter("grade");
		String passwd = request.getParameter("passwd");

		// 封装成User对象
		User user = new User(username, passwd, email, Integer.parseInt(grade));

		// 添加用户
		if (usersService.addUser(user)) {
			// 跳转到成功页面
			request.setAttribute("info", "添加成功");
			request.getRequestDispatcher("/OK").forward(request, response);

		} else {
			// 跳转到失败页面
			request.setAttribute("info", "添加失败");
			request.getRequestDispatcher("/Error").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
