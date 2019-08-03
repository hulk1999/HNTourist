/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.controllers.show.admin;

import hungnp.models.TourDAO;
import hungnp.support.ListPack;
import hungnp.support.Parser;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DELL
 */
public class AdminTourShowController extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try {
			
			String search = request.getParameter("search");
			int pageShow = Parser.getPageShow(request.getParameter("page"));
			int itemsPerPage = 20;
			ListPack listPack;
			if (search == null) listPack = new TourDAO().getTourList(pageShow, itemsPerPage);
			else listPack = new TourDAO().getTourList(search, pageShow, itemsPerPage);
			String urlForPageNavigation = Parser.queryStrToUrlForPageNavigation(request.getContextPath(), "admin/tour", request.getQueryString());

			request.setAttribute("listPack", listPack);
			request.setAttribute("urlForPageNavigation", urlForPageNavigation);
			
		} catch (Exception e) {
			log("Error at AdminTourShowController: " + e.getMessage());
		} finally{
			request.getRequestDispatcher("/admin/admin-tour.jsp").forward(request, response);
		}
	}

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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
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

}
