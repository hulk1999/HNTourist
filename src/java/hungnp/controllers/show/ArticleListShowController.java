/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.controllers.show;

import hungnp.models.ArticleDAO;
import hungnp.models.LocationDAO;
import hungnp.support.ListPack;
import hungnp.support.Parser;
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
public class ArticleListShowController extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try {
			
			ArticleDAO articleDao = new ArticleDAO();
			
			String show = request.getParameter("show");
			int pageShow = Parser.getPageShow(request.getParameter("page"));
			int itemsPerPage = 5;
			ListPack listPack;
			if (show == null) listPack = articleDao.getNewArticleShowList(pageShow, itemsPerPage);
			else if (show.equals("popular")) listPack = articleDao.getPopularArticleShowList(pageShow, itemsPerPage);
			else listPack = articleDao.getLocatedArticleShowList(show, pageShow, itemsPerPage);
			List<Object> rightSidebarPack = new ArrayList();
			rightSidebarPack.add(articleDao.getPopularArticleList(4));
			rightSidebarPack.add(articleDao.getNewArticleList(4));
			rightSidebarPack.add(new LocationDAO().getPopularLocationList(5));
			String pageTitle = "Bài viết mới";
			if (show != null){
				if (show.equals("popular")) pageTitle = "Bài viết nổi bật";
				else pageTitle = show;
			}
			String urlForPageNavigation = Parser.queryStrToUrlForPageNavigation(request.getContextPath(), "article-list", request.getQueryString());

			request.setAttribute("listPack", listPack);
			request.setAttribute("rightSidebarPack", rightSidebarPack);
			request.setAttribute("pageTitle", pageTitle);
			request.setAttribute("urlForPageNavigation", urlForPageNavigation);
			
		} catch (Exception e) {
			log("Error at ArticleListShowController: " + e.getMessage());
		} finally{
			request.getRequestDispatcher("/article-list.jsp").forward(request, response);
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
