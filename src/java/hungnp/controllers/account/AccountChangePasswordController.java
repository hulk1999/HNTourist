/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.controllers.account;

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
public class AccountChangePasswordController extends HttpServlet {

    private static final String ERROR = "error";
    private static final String WRONG = "account/change-password";
    private static final String SUCCESS = "account/profile";
	
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("username");
			String password = request.getParameter("current-password");
			String newPassword = request.getParameter("new-password");
			AccountDAO dao = new AccountDAO();
			if(!dao.checkLogin(username, password)) url = WRONG + "?error=wrong";
			else{
				if (password.equals(newPassword)) url = WRONG + "?error=duplicate";
				else{
					String lastUpdate = LocalDate.now().toString() + " " + LocalTime.now().toString();
					if (dao.setPassword(username, newPassword, lastUpdate)) url = SUCCESS;
					else url += "?error=" + URLEncoder.encode("Không thể đổi mật khẩu!", "utf-8");
				}
			}
        } catch (Exception e) {
            log("Error at AccountChangePasswordController: " + e.getMessage());
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
