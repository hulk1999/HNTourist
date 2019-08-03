/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.controllers.location;

import hungnp.dtos.LocationDTO;
import hungnp.models.LocationDAO;
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
public class LocationAddController extends HttpServlet {

	private static final String ERROR = "error";
    private static final String SUCCESS = "admin/location";
    private static final String INVALID = "admin/location-add";
		
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String url = ERROR;
        try {
			String location = request.getParameter("location");
            LocationDAO dao = new LocationDAO();
            if (dao.checkExistence(location)){
                url = INVALID + "?duplicate=true";
            }
            else{
				boolean foreign = request.getParameter("foreign").equals("Nước ngoài");
				String region = request.getParameter("region");
                boolean success = dao.add(new LocationDTO(location, foreign, region));
                if (!success) url += "?error=" + URLEncoder.encode("Không thể thêm địa điểm!", "utf-8");
				else url = SUCCESS;
            }
        } catch (Exception e) {
            log("Error at LocationAddController: " + e.getMessage());
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
