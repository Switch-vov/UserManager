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
 * ���ܣ����г�ʼ������������ʱ���еĲ��� ������<load-on-startup>1</load-on-startup>
 */
public class InitServlet extends HttpServlet {

	// ��ʼ��
	public void init() throws ServletException {
		// ��record.text�ļ��У���ȡ�����
		// 1���õ����ļ���ȫ·��
		String path = this.getServletContext().getRealPath("record.text");
		// ����
		// System.out.println("ȫ·��Ϊ��" + path);

		// 2���򿪸��ļ�
		BufferedReader bufferedReader = null;
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(new File(path));
			// תΪBufferedReader
			bufferedReader = new BufferedReader(fileReader);
			String nums = bufferedReader.readLine();
			// ����
			// System.out.println(nums);
			// ��nums��ӵ�ServletContext��
			this.getServletContext().setAttribute("nums", nums);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// �ر���
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

	// ����
	public void destroy() {
		// ��ServletContext��ֵ���±��浽record.text�ļ���
		// 1���õ����ļ���ȫ·��
		String path = this.getServletContext().getRealPath("record.text");
		// ����
		// System.out.println("ȫ·��Ϊ��" +��path);

		// 2���򿪸��ļ�
		BufferedWriter bufferedWriter = null;
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(new File(path));
			// תΪBufferedReader
			bufferedWriter = new BufferedWriter(fileWriter);
			// ��ServletContext�У���ȡnums
			String nums = (String) this.getServletContext().getAttribute("nums");
			// д���ļ���
			bufferedWriter.write(nums);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// �ر���
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
