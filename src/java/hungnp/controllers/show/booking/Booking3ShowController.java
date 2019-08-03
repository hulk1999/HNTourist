/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.controllers.show.booking;

import hungnp.dtos.BookingDTO;
import hungnp.dtos.TourArticleDTO;
import hungnp.dtos.TourDTO;
import hungnp.models.TourArticleDAO;
import hungnp.models.TourDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DELL
 */
public class Booking3ShowController extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try {
			
			String code = request.getParameter("code");
			BookingDTO bookingDto = null;
			HttpSession session = request.getSession();
			List<BookingDTO> bookingList = (List) session.getAttribute("bookingList");
			for (BookingDTO dto : bookingList)
				if (dto.getTourCode().equals(code)){
					bookingDto = dto;
					break;
				}
			
			TourDTO tourDto = new TourDAO().getTour(code);
			TourArticleDTO tourArticleDto = new TourArticleDAO().getTourArticleBookingRightSidebar(tourDto.getTourArticleID());
			
			request.setAttribute("bookingDto", bookingDto);
			request.setAttribute("tourDto", tourDto);
			request.setAttribute("tourArticleDto", tourArticleDto);
			
		} catch (Exception e) {
			log("Error at Booking3ShowController: " + e.getMessage());
		} finally{
			request.getRequestDispatcher("/booking/booking-3.jsp").forward(request, response);
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
