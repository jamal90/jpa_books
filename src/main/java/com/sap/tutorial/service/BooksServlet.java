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
		// get all the books 
		
		TypedQuery<Book> allBooksQuery = em.createNamedQuery("AllBooks", Book.class);
		List<Book> allBooks = allBooksQuery.getResultList();
		PrintWriter out = response.getWriter();
		
		Gson gson = new Gson();
		List<String> jsonRes = new ArrayList<String>();
		
		for (Book book: allBooks){
			jsonRes.add(gson.toJson(book));
		}
		
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
