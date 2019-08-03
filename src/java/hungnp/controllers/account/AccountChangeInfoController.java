/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.controllers.account;

import hungnp.dtos.AccountDTO;
import hungnp.models.AccountDAO;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DELL
 */
public class AccountChangeInfoController extends HttpServlet {

    private static final String ERROR = "error";
    private static final String SUCCESS = "account/profile";
            
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("username");
			String fullName = request.getParameter("full-name");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			String gender = request.getParameter("gender");
			String birthdate = request.getParameter("birthdate");
			String address = request.getParameter("address");
			String title = request.getParameter("title");
			String agency = request.getParameter("agency");
			String lastUpdate = LocalDate.now().toString() + " " + LocalTime.now().toString();
			AccountDAO dao = new AccountDAO();
			boolean success = dao.update(new AccountDTO(username, null, fullName, email, phone, gender, birthdate, address, title, agency, false, null, lastUpdate));
			if (!success) url += "?error=" + URLEncoder.encode("Không thể thay đổi thông tin!", "utf-8");
			else url = SUCCESS;
        } catch (Exception e) {
            log("Error at AccountChangeInfoController: " + e.getMessage());
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
