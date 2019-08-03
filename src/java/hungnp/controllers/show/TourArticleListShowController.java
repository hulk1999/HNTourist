/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.controllers.show;

import hungnp.dtos.TourArticleDTO;
import hungnp.models.TourArticleDAO;
import hungnp.models.TourDAO;
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
public class TourArticleListShowController extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try {
			
			String search = request.getParameter("search");
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");
			String foreign = request.getParameter("foreign");
			String show = request.getParameter("show"); // only search or show at the same time
			int pageShow = Parser.getPageShow(request.getParameter("page"));
			int itemsPerPage = 5;
			ListPack listPack;
			if (search == null){
				if (show == null) listPack = new TourArticleDAO().getNewTourArticleShowList(pageShow, itemsPerPage);
				else if (show.equals("popular")) listPack = new TourArticleDAO().getPopularTourArticleShowList(pageShow, itemsPerPage);
				else if (show.equals("local") || show.equals("foreign")) listPack = new TourArticleDAO().getRegionalTourArticleShowList(show, pageShow, itemsPerPage);
				else listPack = new TourArticleDAO().getLocatedTourArticleShowList(show, pageShow, itemsPerPage);
			} else listPack = new TourArticleDAO().getTourArticleSearchShowList(search, fromDate, toDate, foreign, pageShow, itemsPerPage);
			List<List<Object>> tourDateListPack = new ArrayList();
			TourDAO tourDao = new TourDAO();
			for (TourArticleDTO dto : (List<TourArticleDTO>) listPack.getItemList()){
				List<Object> tmpList = new ArrayList();
				tmpList.add(tourDao.getTourArticleMinPrice(dto.getID()));
				tmpList.add(tourDao.getTourArticleMaxDiscount(dto.getID()));
				tmpList.add(tourDao.getTourArticleTourDateList(dto.getID()));
				tourDateListPack.add(tmpList);
			}
			
			String pageTitle = "Tour mới cập nhật";
			if (show != null){
				if (show.equals("popular")) pageTitle = "Tour nổi bật";
				else if (show.equals("local")) pageTitle = "Tour trong nước";
				else if (show.equals("foreign")) pageTitle = "Tour nước ngoài";
				else pageTitle = "Tour " + show;
			} else if (search != null){
				pageTitle = "Tìm kiếm: " + search;
			}
			String urlForPageNavigation = Parser.queryStrToUrlForPageNavigation(request.getContextPath(), "tour-article-list", request.getQueryString());

			request.setAttribute("listPack", listPack);
			request.setAttribute("tourDateListPack", tourDateListPack);
			request.setAttribute("pageTitle", pageTitle);
			request.setAttribute("urlForPageNavigation", urlForPageNavigation);
			
		} catch (Exception e) {
			log("Error at TourArticleListShowController: " + e.getMessage());
		} finally{
			request.getRequestDispatcher("/tour-article-list.jsp").forward(request, response);
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
