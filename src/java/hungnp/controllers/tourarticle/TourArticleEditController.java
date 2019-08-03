/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.controllers.tourarticle;

import hungnp.dtos.TourArticleDTO;
import hungnp.models.TourArticleDAO;
import hungnp.models.TourDAO;
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
public class TourArticleEditController extends HttpServlet {

	private static final String ERROR = "error";
    private static final String SUCCESS = "admin/tour-article";
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String url = ERROR;
        try {
			
			int id = Integer.parseInt(request.getParameter("id"));
			String title = request.getParameter("title");
			String coverPhoto = request.getParameter("cover-photo");
			String duration = request.getParameter("duration");
            String transport = request.getParameter("transport");
            String departure = request.getParameter("departure");
            String destination = request.getParameter("destination");
			String article = request.getParameter("article");
			String lastUpdate = LocalDate.now().toString() + " " + LocalTime.now().toString();
			TourArticleDAO dao = new TourArticleDAO();
			boolean success = dao.update(new TourArticleDTO(id, title, coverPhoto, duration, transport, departure, destination, 0, article, null, lastUpdate));
			
			String[] locationList = request.getParameterValues("location");
			success = success && dao.updateLocations(id, locationList);
			
			TourDAO tourDao = new TourDAO();
			String[] startDateList = request.getParameterValues("startDate");
			int[] priceList = Parser.stringArrToIntArr(request.getParameterValues("price"));
			int[] priceForChildrenList = Parser.stringArrToIntArr(request.getParameterValues("priceForChildren"));
			int[] priceForBabyList = Parser.stringArrToIntArr(request.getParameterValues("priceForBaby"));
			int[] discountList = Parser.stringArrToIntArr(request.getParameterValues("discount"));
			int[] totalSeatsList = Parser.stringArrToIntArr(request.getParameterValues("totalSeats"));
			success = success && tourDao.addTours(id, startDateList, priceList, priceForChildrenList, priceForBabyList, discountList, totalSeatsList);

			if (!success) url += "?error=" + URLEncoder.encode("Không thể cập nhật bài viết!", "utf-8");
			else{
				String search = request.getParameter("search");
				if (search == null) url = SUCCESS;
				else url = SUCCESS + "?search=" + URLEncoder.encode(request.getParameter("search"), "utf-8");
			}
			
        } catch (Exception e) {
            log("Error at TourArticleEditController: " + e.getMessage());
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
