package cn.lxa.book.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.lxa.PageBean.PageBean;
import cn.lxa.book.domain.Book;
import cn.lxa.book.service.BookService;
import cn.lxa.category.domain.category;
import cn.lxa.category.service.categoryService;
import cn.lxa.category.web.categoryServlet;

public class BookServlet extends HttpServlet {
	private BookService bs = new BookService();
	
	private int getPc(HttpServletRequest req){
		String param = req.getParameter("pc");
		if(param==null||param.trim().equals("")){
			int pc = 1 ;
			return pc ;
		}else{
			try{
				int pc = Integer.parseInt(param);
				return pc ;
			}catch(RuntimeException e){
				System.out.println("赋值出现错误");
				int pc = 1 ; 
				return pc ;
			}
		}
	}
	
	//写入BasicServlet
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Class c = this.getClass();
		Method m = null;
		String method = request.getParameter("method");
		System.out.println(method);
		try {
			m = c.getMethod(method, HttpServletRequest.class,HttpServletResponse.class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		try {
			m.invoke(this,request, response);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Class c = this.getClass();
		Method m = null;
		try {
			m = c.getMethod(request.getParameter("method"), HttpServletRequest.class,HttpServletResponse.class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		try {
			m.invoke(this,request, response);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 截取url 并删去pc
	 */
	public String getUrl(HttpServletRequest req){
		String url = req.getRequestURL()+"?"+req.getQueryString();
		int index = url.lastIndexOf("&pc=");
		if(index != -1 ){
			url = url.substring(0, index);
		}
		return url ;
	}
	
	public void delete(HttpServletRequest req,HttpServletResponse resp) throws SQLException, ServletException, IOException{
		String bid = req.getParameter("bid");
		bs.delete(bid);
		req.setAttribute("msg", "编辑书本成功！");
		req.getRequestDispatcher("/adminjsps/msg.jsp").forward(req, resp);
	}
	
	public void findByCategory(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException, SQLException{
		int pc = getPc(req);
		String url = getUrl(req);
		String cid = req.getParameter("cid");
		PageBean<Book> pb = bs.findByCategory(cid, pc);
		pb.setUrl(url);
		System.out.println(url);
		req.setAttribute("pb", pb);
		req.getRequestDispatcher("/jsps/book/list.jsp").forward(req, resp);
	}
	
	public void findByCategoryToEdit(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException, SQLException{
		int pc = getPc(req);
		String url = getUrl(req);
		String cid = req.getParameter("cid");
		PageBean<Book> pb = bs.findByCategory(cid, pc);
		pb.setUrl(url);
		System.out.println(url);
		req.setAttribute("pb", pb);
		req.getRequestDispatcher("/adminjsps/admin/book/list.jsp").forward(req, resp);
	}
	
	public void findByAuthor(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException, SQLException{
		int pc = getPc(req);
		String url = getUrl(req);
		String author = req.getParameter("author");
		PageBean<Book> pb = bs.findByAuthor(author, pc);
		pb.setUrl(url);
		System.out.println(url);
		req.setAttribute("pb", pb);
		req.getRequestDispatcher("/jsps/book/list.jsp").forward(req, resp);
	}
	
	public void findByAuthorToEdit(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException, SQLException{
		int pc = getPc(req);
		String url = getUrl(req);
		String author = req.getParameter("author");
		PageBean<Book> pb = bs.findByAuthor(author, pc);
		pb.setUrl(url);
		System.out.println(url);
		req.setAttribute("pb", pb);
		req.getRequestDispatcher("/adminjsps/admin/book/list.jsp").forward(req, resp);
	}
	
	public void findByPress(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException, SQLException{
		int pc = getPc(req);
		String url = getUrl(req);
		String press = req.getParameter("press");
		System.out.println(press);
		PageBean<Book> pb = bs.findByPress(press, pc);
		pb.setUrl(url);
		System.out.println(url);
		req.setAttribute("pb", pb);
		req.getRequestDispatcher("/jsps/book/list.jsp").forward(req, resp);
	}
	
	public void findByPressToEdit(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException, SQLException{
		int pc = getPc(req);
		String url = getUrl(req);
		String press = req.getParameter("press");
		System.out.println(press);
		PageBean<Book> pb = bs.findByPress(press, pc);
		pb.setUrl(url);
		System.out.println(url);
		req.setAttribute("pb", pb);
		req.getRequestDispatcher("/adminjsps/admin/book/list.jsp").forward(req, resp);
	}
	
	public void findByName(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException, SQLException{
		int pc = getPc(req);
		String url = getUrl(req);
		String bname = req.getParameter("bname");
		PageBean<Book> pb = bs.findByName(bname, pc);
		pb.setUrl(url);
		System.out.println(url);
		req.setAttribute("pb", pb);
		req.getRequestDispatcher("/jsps/book/list.jsp").forward(req, resp);
	}
	
	public void findByCombination(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException, SQLException{
		int pc = getPc(req);
		String url = getUrl(req);
		Book book = CommonUtils.toBean(req.getParameterMap(), Book.class);
		PageBean<Book> pb = bs.findByCombination(book, pc);
		pb.setUrl(url);
		System.out.println(url);
		req.setAttribute("pb", pb);
		req.getRequestDispatcher("/jsps/book/list.jsp").forward(req, resp);
	}
	
	public void findByBid(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException, SQLException{
		String bid = req.getParameter("bid");
		Book book = bs.findByBid(bid);
		req.setAttribute("book", book);
		req.getRequestDispatcher("/jsps/book/desc.jsp").forward(req, resp);
	}
	
	public void addBookInf(HttpServletRequest req,HttpServletResponse resp) throws SQLException{
		Map map = req.getParameterMap();
		System.out.println(map);
		String cid = req.getParameter("cid");
		Book b = CommonUtils.toBean(map, Book.class);
		System.out.println(cid);
		bs.addBookInf(b);
	}
	
	public void load(HttpServletRequest req,HttpServletResponse resp) throws SQLException, ServletException, IOException{
		String bid = req.getParameter("bid");
		Book b = bs.load(bid);
		categoryService cs = new categoryService();
		List<category> list = cs.findAll();
		req.setAttribute("parents", list);
		req.setAttribute("book", b);
		req.getRequestDispatcher("/adminjsps/admin/book/desc.jsp").forward(req, resp);
	}
	
	public void edit(HttpServletRequest req,HttpServletResponse resp) throws SQLException, ServletException, IOException{
		Book b = CommonUtils.toBean(req.getParameterMap(), Book.class);
		category c = CommonUtils.toBean(req.getParameterMap(), category.class);
		b.setParent(c);
		bs.edit(b);
		req.setAttribute("msg", "编辑书本成功！");
		req.getRequestDispatcher("/adminjsps/msg.jsp").forward(req, resp);
	}
}
