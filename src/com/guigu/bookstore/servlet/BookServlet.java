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
		//1. 简单验证: 验证表单域的值是否符合基本的规范: 是否为空, 是否可以转为 int 类型, 是否是一个 email. 不需要进行查询
				//数据库或调用任何的业务方法.
		String username = request.getParameter("username");
		String accountId = request.getParameter("accountId");
		StringBuffer errors = validateFromField(username, accountId);
		
		//表单验证通过。
		if(errors.toString().equals("")) {
			errors=validateUser(username, accountId);
			
			//用户名和账号验证通过
			if(errors.toString().equals("")) {
				errors=validateBookStoreNumber(request);
				
				//库存验证通过
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
		//验证通过执行具体的逻辑操作
		bookservice.cash(BookStoreWebUtils.getShoppingCart(request),username,accountId);
		response.sendRedirect(request.getContextPath()+"/success.jsp");
	}
	
	//验证余额是否充足
	public StringBuffer validateBalance(HttpServletRequest request,String accountId){
		StringBuffer errors=new StringBuffer("");
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		
		Account account=accounService.getAccount(Integer.parseInt(accountId));
		
		if(account.getBalance()<sc.getTotalMoney()) {
			errors.append("余额不足!");
		}
		return errors;

	}

	//验证库存是否充足
	public StringBuffer validateBookStoreNumber(HttpServletRequest request){
		StringBuffer errors=new StringBuffer("");
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		
		for (ShoppingCartItem sci : sc.getItems()) {
		   int quantity=sci.getQuantity();
		   int storeNumber=bookservice.getBook(sci.getBook().getId()).getStoreNumber();
		   if(quantity>storeNumber) {
			   errors.append(sci.getBook().getTitle()+"库存不足<br>");
		   }
		}
		
		return errors;

	}
	
	//验证用户名和账号是否匹配
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
			errors.append("用户名和账号不匹配");
		}
		return errors;

	}
	
	//验证表单域是否符合基本的规则: 是否为空.
	public StringBuffer validateFromField(String username,String accountId){
		StringBuffer errors=new StringBuffer("");
		if(username == null || username.trim().equals("")) {
			errors.append("用户名不能为空<br>");
		}
		
		if(accountId==null || accountId.trim().equals("")) {
			errors.append("账号不能为空");
		}
		return errors;
		
	}
	
	public void updateItemQuantity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//4. 在 updateItemQuantity 方法中, 获取 quanity, id, 再获取购物车对象, 调用 service 的方法做修改
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
			
			//5. 传回 JSON 数据: bookNumber:xx, totalMoney
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
	 * 获取一本书的详细信息
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
	 * 根据查询条件获取所有的书
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
