package com.guigu.bookstore.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.management.RuntimeErrorException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.guigu.bookstore.domain.Account;
import com.guigu.bookstore.domain.Book;
import com.guigu.bookstore.domain.ShoppingCart;
import com.guigu.bookstore.domain.ShoppingCartItem;
import com.guigu.bookstore.domain.User;
import com.guigu.bookstore.service.AccountService;
import com.guigu.bookstore.service.BookService;
import com.guigu.bookstore.service.UserService;
import com.guigu.bookstore.web.BookStoreWebUtils;
import com.guigu.bookstore.web.CriteriaBook;
import com.guigu.bookstore.web.Page;


public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BookService bookservice=new BookService();
	private UserService userService=new UserService();
	private AccountService accounService=new AccountService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	     String methodName = request.getParameter("method");
	     try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			method.invoke(this, request,response);
		} catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void cash(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. ����֤: ��֤�����ֵ�Ƿ���ϻ����Ĺ淶: �Ƿ�Ϊ��, �Ƿ����תΪ int ����, �Ƿ���һ�� email. ����Ҫ���в�ѯ
				//���ݿ������κε�ҵ�񷽷�.
		String username = request.getParameter("username");
		String accountId = request.getParameter("accountId");
		StringBuffer errors = validateFromField(username, accountId);
		
		//����֤ͨ����
		if(errors.toString().equals("")) {
			errors=validateUser(username, accountId);
			
			//�û������˺���֤ͨ��
			if(errors.toString().equals("")) {
				errors=validateBookStoreNumber(request);
				
				//�����֤ͨ��
				if(errors.toString().equals("")) {
					errors=validateBalance(request, accountId);
				}
			}
		}
		
		if(!errors.toString().equals("")) {
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/WEB-INF/pages/cash.jsp").forward(request, response);
			return;
		}
		//��֤ͨ��ִ�о�����߼�����
		bookservice.cash(BookStoreWebUtils.getShoppingCart(request),username,accountId);
		response.sendRedirect(request.getContextPath()+"/success.jsp");
	}
	
	//��֤����Ƿ����
	public StringBuffer validateBalance(HttpServletRequest request,String accountId){
		StringBuffer errors=new StringBuffer("");
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		
		Account account=accounService.getAccount(Integer.parseInt(accountId));
		
		if(account.getBalance()<sc.getTotalMoney()) {
			errors.append("����!");
		}
		return errors;

	}

	//��֤����Ƿ����
	public StringBuffer validateBookStoreNumber(HttpServletRequest request){
		StringBuffer errors=new StringBuffer("");
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		
		for (ShoppingCartItem sci : sc.getItems()) {
		   int quantity=sci.getQuantity();
		   int storeNumber=bookservice.getBook(sci.getBook().getId()).getStoreNumber();
		   if(quantity>storeNumber) {
			   errors.append(sci.getBook().getTitle()+"��治��<br>");
		   }
		}
		
		return errors;

	}
	
	//��֤�û������˺��Ƿ�ƥ��
	public StringBuffer validateUser(String username,String accountId){
		boolean flag=false;
		User user=userService.getUserByUserName(username);
		if(user!=null) {
			int accountId2=user.getAccountId();
			if(accountId.trim().equals(""+accountId)) {
				flag=true;
			}
		}
		StringBuffer errors=new StringBuffer("");
		if(!flag) {
			errors.append("�û������˺Ų�ƥ��");
		}
		return errors;

	}
	
	//��֤�����Ƿ���ϻ����Ĺ���: �Ƿ�Ϊ��.
	public StringBuffer validateFromField(String username,String accountId){
		StringBuffer errors=new StringBuffer("");
		if(username == null || username.trim().equals("")) {
			errors.append("�û�������Ϊ��<br>");
		}
		
		if(accountId==null || accountId.trim().equals("")) {
			errors.append("�˺Ų���Ϊ��");
		}
		return errors;
		
	}
	
	public void updateItemQuantity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//4. �� updateItemQuantity ������, ��ȡ quanity, id, �ٻ�ȡ���ﳵ����, ���� service �ķ������޸�
		String idStr = request.getParameter("id");
		String quantityStr = request.getParameter("quantity");
		
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		
		int id=-1;
		int quantity=-1;
		
		try {
			id=Integer.parseInt(idStr);
			quantity=Integer.parseInt(quantityStr);
		} catch (NumberFormatException e) {}
		
		if(id>0 && quantity>0) {
			bookservice.updateItemQuantity(sc,id,quantity);
			
			//5. ���� JSON ����: bookNumber:xx, totalMoney
			Map<String,Object> result=new HashMap<>();
			result.put("bookNumber", sc.getBookNumber());
			result.put("totalMoney", sc.getTotalMoney());
			
			Gson gson=new Gson();
			String jsonStr = gson.toJson(result);
			response.setContentType("text/javascript");
			response.getWriter().print(jsonStr);
			
		}
		
	}
	
	public void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		bookservice.clearShoppingCart(sc);
		
		request.getRequestDispatcher("/WEB-INF/pages/emptycart.jsp").forward(request, response);
	}
	
	public void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		
		int id=-1;
		
		try {
			id=Integer.parseInt(idStr);
		} catch (NumberFormatException e) {}
		
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		bookservice.removeItemFromShoppingCart(sc,id);
		
		if(sc.isEmpty()) {
			request.getRequestDispatcher("/WEB-INF/pages/emptycart.jsp").forward(request, response);
			return;
		}
		
		request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
	}

	
	public void forwardPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("page");
		request.getRequestDispatcher("/WEB-INF/pages/"+page+".jsp").forward(request, response);
	}

	
	public void addToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		
		int id=-1;
		boolean flag=false;
		
		try {
			id=Integer.parseInt(idStr);
		} catch (NumberFormatException e) {}
		
		if(id>0) {
			ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
			
			 flag = bookservice.addToCart(id, sc);
		}
		if(flag) {
			getBooks(request, response);
			return;
		}
		response.sendRedirect(request.getContextPath() + "/error-1.jsp");
	}
	
	
	/**
	 * ��ȡһ�������ϸ��Ϣ
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void getBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		
		int id=-1;
		Book book=null;
		try {
			id=Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
		}
		
		if(id>0) 
		book = bookservice.getBook(id);

		if(book==null) {
			response.sendRedirect(request.getContextPath()+"/error-1.jsp");
			return;
		}
		
		request.setAttribute("book", book);
		request.getRequestDispatcher("/WEB-INF/pages/book.jsp").forward(request, response);
	}
	
	/**
	 * ���ݲ�ѯ������ȡ���е���
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void getBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pageNoStr = request.getParameter("pageNo");
		String minPriceStr = request.getParameter("minPrice");
		String maxPriceStr = request.getParameter("maxPrice");
		
		int pageNo=1;
		int minPrice=0;
		int maxPrice=Integer.MAX_VALUE;
		
		try {
			pageNo=Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}
		
		try {
			minPrice=Integer.parseInt(minPriceStr);
		} catch (NumberFormatException e) {}
		
		try {
			maxPrice=Integer.parseInt(maxPriceStr);
		} catch (NumberFormatException e) {}
		
		CriteriaBook criteriabook=new CriteriaBook(minPrice, maxPrice, pageNo);
		
		Page<Book> page = bookservice.getPage(criteriabook);
		
		request.setAttribute("bookpage", page);
		
		request.getRequestDispatcher("WEB-INF/pages/books.jsp").forward(request, response);
	}
}
