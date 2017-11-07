package com.sap.tutorial.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sap.tutorial.model.Book;

/**
 * Servlet implementation class BooksServlet
 */
public class BooksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PUNIT = "jpa_books";
	private EntityManager em;
	private Gson gson = new Gson();
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BooksServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() throws ServletException {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory(PUNIT);
			em = emf.createEntityManager();
		} catch (RuntimeException e){
			e.printStackTrace();
			throw new ServletException("Error in creating entity manager for JPA", e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		Gson gson = new Gson();
		List<String> jsonRes = new ArrayList<String>();
		
		// get all the books 

		// using named query
		// TypedQuery<Book> allBooksQuery = em.createNamedQuery("AllBooks", Book.class);
		// List<Book> allBooks = allBooksQuery.getResultList();
		
		// using native query
		/*Query allBooksNatvieQuery = em.createNativeQuery("select \"ISBN\", \"Title\", \"Language\", \"PublisherID\", \"PublishedDate\", \"NumOfPages\" from \"JPA_TEST\".\"jpa_test.model::books.Book\"", Book.class);
		List<Book> allBooks = allBooksNatvieQuery.getResultList();*/

		// using parameterized query
		/*TypedQuery<Book> findBookByIdQuery = em.createNamedQuery("FindBookByISBN", Book.class);
		findBookByIdQuery.setParameter("in_isbn", request.getParameter("isbn")); 
		Book matchedBook = findBookByIdQuery.getSingleResult();*/
		
		// findById **
		 Book book = em.find(Book.class, request.getParameter("isbn"));
		
//		for (Book book: allBooks){
//			jsonRes.add(gson.toJson(book));
//		}
		
		jsonRes.add(gson.toJson(book));
		/*jsonRes.add(gson.toJson(matchedBook));*/
		
		out.write(jsonRes.toString());
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// first create an entity manually, and then read from client
		Gson gson = new Gson();
		
		// to insert into DB
		String jsonInput = getRequestBodyAsString(request);
		Book book = gson.fromJson(jsonInput, Book.class);
		em.getTransaction().begin();
		em.persist(book);
		em.getTransaction().commit();
	}
	
	private String getRequestBodyAsString(HttpServletRequest request) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()))){
			return br.lines().collect(Collectors.joining("\n"));
		}
	}
	
	private String getBookIsbn(HttpServletRequest req){
		int parts = req.getRequestURI().split("/").length;
		String bookId = req.getRequestURI().split("/")[parts - 1];
		return bookId;
	}

	
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// update in entire - only update the fields that are sent
		String jsonInput = this.getRequestBodyAsString(req);
		Book book = gson.fromJson(jsonInput, Book.class);
		String bookId = this.getBookIsbn(req);
		Book bookFromDB = em.find(Book.class, bookId);
		if (bookFromDB != null){
			em.getTransaction().begin();
				bookFromDB.setNumOfPages(book.getNumOfPages());
				bookFromDB.setTitle(book.getTitle());
			em.getTransaction().commit();
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 Book book = em.find(Book.class, this.getBookIsbn(req));
		 if (book != null){
			 em.getTransaction().begin();
			 em.remove(book);
			 em.getTransaction().commit();
		 }
		 
	}
	
}
