/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.controllers.booking;

import hungnp.models.BookingDAO;
import hungnp.models.TourDAO;
import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DELL
 */
public class BookingChangeStatusController extends HttpServlet {

	private static final String ERROR = "error";
    private static final String INPUT = "admin/booking";
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String url = ERROR;
        try {
			int id = Integer.parseInt(request.getParameter("id"));
			String tourCode = request.getParameter("tourCode");
			int totalSeats = Integer.parseInt(request.getParameter("totalSeats"));
			String status = request.getParameter("status");
			String currentStatus = request.getParameter("currentStatus");
			String search = request.getParameter("search");
			if (currentStatus.equals("Hết hạn") || currentStatus.equals("Hủy")){
				if (search == null) url = INPUT + "?invalid=true";
				else url = INPUT + "?invalid=true&search=" + URLEncoder.encode(request.getParameter("search"), "utf-8");
			} else if (new BookingDAO().changeStatus(id, status)){
				if (status.equals("Hết hạn") || status.equals("Hủy")) new TourDAO().increaseSeatsBooked(tourCode, -totalSeats);
				if (search == null) url = INPUT;
				else url = INPUT + "?search=" + URLEncoder.encode(request.getParameter("search"), "utf-8");
			}
			else url += "?error=" + URLEncoder.encode("Không thể thay đổi trạng thái!", "utf-8");
        } catch (Exception e) {
            log("Error at BookingChangeStatusController: " + e.getMessage());
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
