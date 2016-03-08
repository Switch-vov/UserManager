package com.pc.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.net.aso.b;

import org.omg.CORBA.Request;

/**
 * 
 * @author Switch 
 * 功能：进行初始化操作和销毁时进行的操作 已设置<load-on-startup>1</load-on-startup>
 */
public class InitServlet extends HttpServlet {

	// 初始化
	public void init() throws ServletException {
		// 从record.text文件中，读取浏览量
		// 1、得到该文件的全路径
		String path = this.getServletContext().getRealPath("record.text");
		// 测试
		// System.out.println("全路径为：" + path);

		// 2、打开该文件
		BufferedReader bufferedReader = null;
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(new File(path));
			// 转为BufferedReader
			bufferedReader = new BufferedReader(fileReader);
			String nums = bufferedReader.readLine();
			// 测试
			// System.out.println(nums);
			// 把nums添加到ServletContext中
			this.getServletContext().setAttribute("nums", nums);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭流
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					bufferedReader = null;
				}
			}
		}
	}

	// 销毁
	public void destroy() {
		// 把ServletContext的值重新保存到record.text文件中
		// 1、得到该文件的全路径
		String path = this.getServletContext().getRealPath("record.text");
		// 测试
		// System.out.println("全路径为：" +　path);

		// 2、打开该文件
		BufferedWriter bufferedWriter = null;
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(new File(path));
			// 转为BufferedReader
			bufferedWriter = new BufferedWriter(fileWriter);
			// 从ServletContext中，获取nums
			String nums = (String) this.getServletContext().getAttribute("nums");
			// 写入文件中
			bufferedWriter.write(nums);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭流
			if (bufferedWriter != null) {
				try {
					bufferedWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					bufferedWriter = null;
				}
			}
		}

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		this.getServletConfig().getServletContext();
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
