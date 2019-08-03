/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.controllers.show;

import hungnp.models.ArticleDAO;
import hungnp.models.LocationDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DELL
 */
public class ArticleShowController extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try {
			
			ArticleDAO articleDao = new ArticleDAO();
			LocationDAO locationDao = new LocationDAO();
			
			int id = Integer.parseInt(request.getParameter("id"));
			articleDao.increaseView(id);
			List<Object> rightSidebarPack = new ArrayList();
			rightSidebarPack.add(articleDao.getPopularArticleList(4));
			rightSidebarPack.add(articleDao.getNewArticleList(4));
			rightSidebarPack.add(locationDao.getPopularLocationList(5));
			
			request.setAttribute("dto", articleDao.getArticle(id));
			request.setAttribute("locationList", locationDao.getArticleLocations(id));
			request.setAttribute("rightSidebarPack", rightSidebarPack);
			
		} catch (Exception e) {
			log("Error at ArticleShowController: " + e.getMessage());
		} finally{
			request.getRequestDispatcher("/article.jsp").forward(request, response);
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
