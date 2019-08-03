/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.controllers.show.account;

import hungnp.dtos.BookingDTO;
import hungnp.dtos.TourArticleDTO;
import hungnp.dtos.TourDTO;
import hungnp.dtos.TouristDTO;
import hungnp.models.BookingDAO;
import hungnp.models.TourArticleDAO;
import hungnp.models.TourDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DELL
 */
public class AccountBookingViewShowController extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try {
			
			int id = Integer.parseInt(request.getParameter("id"));
			BookingDAO dao = new BookingDAO();
			BookingDTO dto = dao.getBooking(id);
			List<TouristDTO> touristList = dao.getTouristList(id);
			TourDTO tourDto = new TourDAO().getTour(dto.getTourCode());
			TourArticleDTO tourArticleDTO = new TourArticleDAO().getTourArticleInfo(tourDto.getTourArticleID());
			
			request.setAttribute("dto", dto);
			request.setAttribute("touristList", touristList);
			request.setAttribute("tourDto", tourDto);
			request.setAttribute("tourArticleDto", tourArticleDTO);
			
		} catch (Exception e) {
			log("Error at AccountBookingViewShowController: " + e.getMessage());
		} finally{
			request.getRequestDispatcher("/account/account-booking-view.jsp").forward(request, response);
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
