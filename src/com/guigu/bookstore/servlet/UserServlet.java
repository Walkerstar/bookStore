package com.guigu.bookstore.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.guigu.bookstore.domain.User;
import com.guigu.bookstore.service.UserService;


public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserService userService=new UserService();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��ȡ username ���������ֵ
		String username=request.getParameter("username");
		String minprice = request.getParameter("minPrice");
		String maxprice = request.getParameter("maxPrice");
		
		
		int minPrice=0;
		int maxPrice=Integer.MAX_VALUE;
		
		
		try {
			minPrice=Integer.parseInt(minprice);
		} catch (NumberFormatException e) {}
		
		try {
			maxPrice=Integer.parseInt(maxprice);
		} catch (NumberFormatException e) {}
		
		//���� UserService �� getUser(username) ��ȡUser ����Ҫ�� trades �Ǳ�װ��õģ�����ÿһ�� Trrade ����� items Ҳ��װ���
		User user=userService.getUserWithTrades(username);
		
		//�� User ������뵽 request ��
		if(user==null) {
			response.sendRedirect(request.getContextPath()+"/error-1.jsp");
			return;
		}
		
		request.setAttribute("user", user);
		
		//ת��ҳ�浽 /WEB-INF/pages/trades.jsp
		request.getRequestDispatcher("/WEB-INF/pages/trades.jsp").forward(request, response);
		
	}

}
