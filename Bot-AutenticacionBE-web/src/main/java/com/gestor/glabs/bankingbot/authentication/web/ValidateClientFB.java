/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gestor.glabs.bankingbot.authentication.web;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gestor.glabs.bankingbot.authentication.service.AuthenticationService;

/**
 *
 * @author Hendrix
 */
@WebServlet(name = "ValidateClientFB", urlPatterns = {"/validateClientFB"})
public class ValidateClientFB extends HttpServlet {

	/** Id por JVM. */
	private static final long serialVersionUID = -2691228234383614437L;
	@EJB
	private AuthenticationService service;

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cedula = request.getParameter("cedula");
		HttpSession session = request.getSession();
		String accountLinkingToken = (String) session.getAttribute("accountLinkingToken");
		String result = this.service.validateClientInBank(cedula, accountLinkingToken);
		if ("OK".equals(result)) {
			RequestDispatcher view = request.getRequestDispatcher("validateFB.html");
			view.forward(request, response);
		} else if ("NOSMS".equals(result)) {
			RequestDispatcher view = request.getRequestDispatcher("noservice.html");
			view.forward(request, response);
		} else if ("LOGGED".equals(result)) {
			RequestDispatcher view = request.getRequestDispatcher("logged.html");
			view.forward(request, response);
		} else {
			RequestDispatcher view = request.getRequestDispatcher("authorizeFB1.html");
			view.forward(request, response);
		}
	}

}
