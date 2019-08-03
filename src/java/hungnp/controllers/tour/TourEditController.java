/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.controllers.tour;

import hungnp.dtos.TourDTO;
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
public class TourEditController extends HttpServlet {

	private static final String ERROR = "error";
    private static final String SUCCESS = "admin/tour";
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String url = ERROR;
        try {
			String code = request.getParameter("code");
			int price = Integer.parseInt(request.getParameter("price"));
			int priceForChildren = Integer.parseInt(request.getParameter("priceForChildren"));
			int priceForBaby = Integer.parseInt(request.getParameter("priceForBaby"));
			int discount = request.getParameter("discount").length() == 0 ? 0 : Integer.parseInt(request.getParameter("discount"));
			int totalSeats = Integer.parseInt(request.getParameter("totalSeats"));
			TourDAO dao = new TourDAO();
			boolean success = dao.update(new TourDTO(code, null, price, priceForChildren, priceForBaby, discount, totalSeats, 0, 0));
			if (!success) url += "?error=" + URLEncoder.encode("Không thể cập nhật mã tour!", "utf-8");
			else{
				String search = request.getParameter("search");
				if (search == null) url = SUCCESS;
				else url = SUCCESS + "?search=" + URLEncoder.encode(request.getParameter("search"), "utf-8");
			}
        } catch (Exception e) {
            log("Error at TourEditController: " + e.getMessage());
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
