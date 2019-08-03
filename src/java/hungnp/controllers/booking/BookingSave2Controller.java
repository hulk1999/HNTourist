/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.controllers.booking;

import hungnp.dtos.BookingDTO;
import hungnp.dtos.TouristDTO;
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
public class BookingSave2Controller extends HttpServlet {

	private static final String ERROR = "error";
	private static final String BACK = "booking-1";
	private static final String FORWARD = "booking-3";
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String url = ERROR;
		try {
			
			// save booking info
			
			String contactName = request.getParameter("contactName");
			String contactPhone = request.getParameter("contactPhone");
			String contactEmail = request.getParameter("contactEmail");
			String contactAddress = request.getParameter("contactAddress");
			
			String[] nameList = request.getParameterValues("name");
			String[] phoneList = request.getParameterValues("phone");
			String[] addressList = request.getParameterValues("address");
			String[] genderList = request.getParameterValues("gender");
			String[] countryList = request.getParameterValues("country");
			String[] passportList = request.getParameterValues("passport");
			
			// get bookingDto
			String code = request.getParameter("code");
			List<BookingDTO> bookingList = (List) request.getSession().getAttribute("bookingList");
			BookingDTO bookingDto = null;
			for (BookingDTO dto : bookingList)
				if (dto.getTourCode().equals(code)){
					bookingDto = dto;
					break;
				}
			
			bookingDto.setContactName(contactName);
			bookingDto.setContactPhone(contactPhone);
			bookingDto.setContactEmail(contactEmail);
			bookingDto.setContactAddress(contactAddress);
			
			HttpSession session = request.getSession();
			List<TouristDTO> touristList = (List) session.getAttribute("touristList");
			if (touristList == null){
				touristList = new ArrayList();
				session.setAttribute("touristList", touristList);
			}
			List<TouristDTO> deleteList = new ArrayList();
			for (TouristDTO dto : touristList) // delete tourists of this tour code
				if (dto.getTourCode().equals(code))	deleteList.add(dto);
			for (TouristDTO dto : deleteList) touristList.remove(dto);
			for (int i = 0; i <= nameList.length - 1; i++) // add tourists of this tour code
				if (nameList[i].length() > 0 || phoneList[i].length() > 0 || addressList[i].length() > 0 || genderList[i].length() > 0 || countryList[i].length() > 0 || passportList[i].length() > 0)
					touristList.add(new TouristDTO(-1, -1, code, nameList[i], phoneList[i], addressList[i], genderList[i].length() == 0 ? null : genderList[i], countryList[i], passportList[i]));
			
			String destination = request.getParameter("destination");
			if (destination.equals("forward")) url = FORWARD + "?code=" + code;
			else url = BACK + "?code=" + code;
			
		} catch (Exception e) {
			log("Error at BookingSave2Controller: " + e.getMessage());
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
