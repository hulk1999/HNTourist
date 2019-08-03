/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.controllers.booking;

import hungnp.dtos.BookingDTO;
import hungnp.dtos.TouristDTO;
import hungnp.models.BookingDAO;
import hungnp.models.TourDAO;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
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
public class BookingSave3Controller extends HttpServlet {

	private static final String ERROR = "error";
	private static final String DESTINATION = "booking-4";
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String url = ERROR;
		try {
			
			// save booking info
			
			String code = request.getParameter("code");
			
			// get bookingDto
			HttpSession session = request.getSession();
			List<BookingDTO> bookingList = (List) session.getAttribute("bookingList");
			BookingDTO bookingDto = null;
			for (BookingDTO dto : bookingList){
				if (dto.getTourCode().equals(code)){
					bookingDto = dto;
					break;
				}
			}
			BookingDAO dao = new BookingDAO();
			bookingDto.setId(dao.getLastID() + 1);
			bookingDto.setUsername((String) session.getAttribute("username"));
			
			// get tourist list
			List<TouristDTO> touristList = (List) session.getAttribute("touristList");
			if (touristList == null){
				touristList = new ArrayList();
				session.setAttribute("touristList", touristList);
			}
			List<TouristDTO> saveList = new ArrayList(); // save to database then remove from session
			for (TouristDTO dto : touristList)
				if (dto.getTourCode().equals(code))	saveList.add(dto);
			for (TouristDTO dto : saveList) touristList.remove(dto);
			
			// increase seats booked in tour
			int seatsBooked = bookingDto.getAdultNumber() + bookingDto.getChildNumber() + bookingDto.getBabyNumber();
			
			if (new TourDAO().increaseSeatsBooked(code, seatsBooked)){
				if (dao.add(bookingDto, saveList)) url = DESTINATION + "?code=" + code + "&id=" + bookingDto.getId();
				else url += "?error=" + URLEncoder.encode("Không thể đặt tour!", "utf-8");
			} else url += "?error=" + URLEncoder.encode("Không thể thêm chỗ!", "utf-8");
			
		} catch (Exception e) {
			log("Error at BookingSave3Controller: " + e.getMessage());
		} finally{
			response.sendRedirect(url);
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
