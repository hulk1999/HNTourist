/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.controllers.contact;

import hungnp.dtos.ContactDTO;
import hungnp.models.ContactDAO;
import hungnp.support.Parser;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DELL
 */
public class ContactAddController extends HttpServlet {

	private static final String ERROR = "error";
    private static final String SUCCESS = "contact";
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String url = ERROR;
        try {
			ContactDAO dao = new ContactDAO();
			int id = dao.getLastID() + 1;
			String name = Parser.emptyToNull(request.getParameter("name"));
			String email = request.getParameter("email");
			String phone = Parser.emptyToNull(request.getParameter("phone"));
			String address = Parser.emptyToNull(request.getParameter("address"));
			String message = request.getParameter("message");
			String sentTime = LocalDate.now().toString() + " " + LocalTime.now().toString();
			boolean success = dao.add(new ContactDTO(id, name, email, phone, address, message, sentTime, false));
			if (!success) url += "?error=" + URLEncoder.encode("Không thể thêm địa điểm!", "utf-8");
			else url = SUCCESS;
        } catch (Exception e) {
            log("Error at ContactAddController: " + e.getMessage());
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
