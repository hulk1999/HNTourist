/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.controllers.show;

import hungnp.dtos.ArticleDTO;
import hungnp.dtos.TourArticleDTO;
import hungnp.models.ArticleDAO;
import hungnp.models.TourArticleDAO;
import hungnp.models.TourDAO;
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
public class IndexShowController extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try {
			
			TourDAO tourDao = new TourDAO();
			TourArticleDAO tourArticleDao = new TourArticleDAO();
			
			// get popular tours
			List<List<Object>> popularTourListPack = new ArrayList();
			List<TourArticleDTO> popularTourList = tourArticleDao.getPopularTourArticleShowList(1, 3).getItemList();
			for (TourArticleDTO dto : popularTourList){
				List<Object> tmpList = new ArrayList();
				tmpList.add(dto);
				tmpList.add(tourDao.getTourArticleMinPrice(dto.getID()));
				tmpList.add(tourDao.getTourArticleMaxDiscount(dto.getID()));
				popularTourListPack.add(tmpList);
			}
			
			// get new article
			ArticleDTO articleDto = new ArticleDAO().getNewArticleForHomePage();
			
			// get new tours
			List<List<Object>> newTourListPack = new ArrayList();
			List<TourArticleDTO> newTourList = tourArticleDao.getNewTourArticleShowList(1, 3).getItemList();
			for (TourArticleDTO dto : newTourList){
				List<Object> tmpList = new ArrayList();
				tmpList.add(dto);
				tmpList.add(tourDao.getTourArticleMinPrice(dto.getID()));
				tmpList.add(tourDao.getTourArticleMaxDiscount(dto.getID()));
				newTourListPack.add(tmpList);
			}
			
			request.setAttribute("popularTourListPack", popularTourListPack);
			request.setAttribute("articleDto", articleDto);
			request.setAttribute("newTourListPack", newTourListPack);
			
		} catch (Exception e) {
			log("Error at IndexShowController: " + e.getMessage());
		} finally{
			request.getRequestDispatcher("/index.jsp").forward(request, response);
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
