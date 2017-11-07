package com.sap.tutorial.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Random;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class BooksJdbcServlet
 */
public class BooksJdbcServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource ds;
    
    @Override
	public void init() throws ServletException {
		try{
			InitialContext ctxt = new InitialContext();
			ds = (DataSource)ctxt.lookup("java:comp/env/jdbc/DefaultDB");
		}catch (NamingException e) {
			// TODO: handle exception
		}
	}

	/**
     * @see HttpServlet#HttpServlet()
     */
    public BooksJdbcServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Connection connection = ds.getConnection();
			String insertSql = "INSERT INTO \"JPA_TEST\".\"jpa_test.model::books.Book\" (\"ISBN\", \"Title\", \"Language\", \"PublisherID\", \"PublishedDate\", \"NumOfPages\")"
					+ " VALUES(?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = connection.prepareStatement(insertSql);
			Random rnd = new Random();
			pstmt.setString(1, "1-22-333-" + rnd.nextInt(Integer.MAX_VALUE));
			pstmt.setString(2,  "Programming in C");
			pstmt.setString(3,  "EN");
			pstmt.setInt(4, rnd.nextInt(Integer.MAX_VALUE));
			pstmt.setDate(5, java.sql.Date.valueOf(LocalDate.now()));
			pstmt.setInt(6, rnd.nextInt(Integer.MAX_VALUE));
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
