package com.guigu.bookstore.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.guigu.bookstore.db.JDBCUtils;
import com.guigu.bookstore.web.ConnectionContext;


public class TranactionFilter implements Filter {

   
    public TranactionFilter() {
       
    }

	
	public void destroy() {
		
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		Connection conn=null;
		try {
			//1. ��ȡ����
			conn=JDBCUtils.getConnection();
			
			//2. ��������
			conn.setAutoCommit(false);
			
			//3. ���� ThreadLocal �����Ӻ͵�ǰ�̰߳�
			ConnectionContext.getInstance().bind(conn);
			
			//4. ������ת��Ŀ�� Servlet
			chain.doFilter(request, response);
			
			//5. �ύ����
			conn.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			
			//6. �ع�����
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			HttpServletResponse resp=(HttpServletResponse) response;
			HttpServletRequest req=(HttpServletRequest) request;
			resp.sendRedirect(req.getContextPath()+"/error-1.jsp");
		}finally{
			
			//7. �����
		    ConnectionContext.getInstance().remove();
		    
		  //8. �ر�����
		    JDBCUtils.release(conn);
		}
		
	}
	


	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}
		

}
