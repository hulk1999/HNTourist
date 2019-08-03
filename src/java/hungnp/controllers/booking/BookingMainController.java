/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.controllers.booking;

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
public class BookingMainController extends HttpServlet {

	private static final String ERROR = "error";
	private static final String SAVE_1 = "BookingSave1Controller";
	private static final String SAVE_2 = "BookingSave2Controller";
	private static final String SAVE_3 = "BookingSave3Controller";
	private static final String SEARCH = "admin/booking";
	private static final String CHANGE_STATUS = "BookingChangeStatusController";
	private static final String DELETE = "BookingDeleteController";
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String url = ERROR;
		try {
			String action = request.getParameter("action");
			if (action == null) url += "?error=" + URLEncoder.encode("Không tìm thấy action!", "utf-8");
            else if (action.equals("save1")) url = SAVE_1;
			else if (action.equals("save2")) url = SAVE_2;
			else if (action.equals("save3")) url = SAVE_3;
			else if (action.equals("search")) url = SEARCH + "?search=" + URLEncoder.encode(request.getParameter("search"), "utf-8");
			else if (action.equals("change-status")) url = CHANGE_STATUS;
			else if (action.equals("delete")) url = DELETE;
			else url += "?error=" + URLEncoder.encode("Action không được hỗ trợ!", "utf-8");
		} catch (Exception e) {
			log("Error at BookingMainController: " + e.getMessage());
		} finally{
			if (url.contains(ERROR) || url.contains(SEARCH)) response.sendRedirect(url);
			else request.getRequestDispatcher(url).forward(request, response);
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
