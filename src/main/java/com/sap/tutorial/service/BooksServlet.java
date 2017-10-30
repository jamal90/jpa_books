package com.sap.tutorial.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
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
		} catch (Exception e){
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

		// get all the books 
		
		// using named query
		TypedQuery<Book> allBooksQuery = em.createNamedQuery("AllBooks", Book.class);
		List<Book> allBooks = allBooksQuery.getResultList();
		
		// using native query
		/*Query allBooksNatvieQuery = em.createNativeQuery("select \"ISBN\", \"Title\", \"Language\", \"PublisherID\", \"PublishedDate\", \"NumOfPages\" from \"JPA_TEST\".\"jpa_test.model::books.Book\"", Book.class);
		List<Book> allBooks = allBooksNatvieQuery.getResultList();*/

		// using parameterized query
		/*TypedQuery<Book> findBookByIdQuery = em.createNamedQuery("FindBookByISBN", Book.class);
		findBookByIdQuery.setParameter("in_isbn", request.getParameter("isbn")); 
		Book matchedBook = findBookByIdQuery.getSingleResult();*/
		
		Gson gson = new Gson();
		List<String> jsonRes = new ArrayList<String>();
		
		for (Book book: allBooks){
			jsonRes.add(gson.toJson(book));
		}
		
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

}
