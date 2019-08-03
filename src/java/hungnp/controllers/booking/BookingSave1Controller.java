/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.controllers.booking;

import hungnp.dtos.BookingDTO;
import hungnp.models.TourDAO;
import java.io.IOException;
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
public class BookingSave1Controller extends HttpServlet {

	private static final String ERROR = "error";
	private static final String BACK = "tour-article";
	private static final String FORWARD = "booking-2";
	private static final String OUT_OF_SEATS = "booking-1";
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String url = ERROR;
		try {
			
			// save booking info
			
			int adultNumber = Integer.parseInt(request.getParameter("adultNumber"));
			int childNumber = Integer.parseInt(request.getParameter("childNumber"));
			int babyNumber = Integer.parseInt(request.getParameter("babyNumber"));
			String paymentMethod = request.getParameter("paymentMethod"); // none, transfer, cash
			
			String code = request.getParameter("code");
			HttpSession session = request.getSession();
			List<BookingDTO> bookingList = (List) session.getAttribute("bookingList");
			if (bookingList == null){
				bookingList = new ArrayList();
				session.setAttribute("bookingList", bookingList);
			}
			BookingDTO bookingDto = null;
			for (BookingDTO dto : bookingList)
				if (dto.getTourCode().equals(code)){
					bookingDto = dto;
					break;
				}
			if (bookingDto == null){
				bookingDto = new BookingDTO();
				bookingList.add(bookingDto);
				bookingDto.setTourCode(code);
			}
			
			bookingDto.setAdultNumber(adultNumber);
			bookingDto.setChildNumber(childNumber);
			bookingDto.setBabyNumber(babyNumber);
			bookingDto.setPaymentMethod(paymentMethod);		
			
			int tourArticleID = new TourDAO().getTourArticleID(code);
			String destination = request.getParameter("destination");
			if (destination.equals("forward")){
				int seatsAvailable = new TourDAO().getSeatsAvailable(code);
				if (adultNumber + childNumber + babyNumber > seatsAvailable){
					url = OUT_OF_SEATS + "?code=" + code + "&out-of-seats=" + Integer.toString(seatsAvailable);
				} else{
					request.setAttribute("bookingDto", bookingDto);
					url = FORWARD + "?code=" + code;
				}
			}
			else url = BACK + "?id=" + tourArticleID;
			
		} catch (Exception e) {
			log("Error at BookingSave1Controller: " + e.getMessage());
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
