/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hungnp.controllers.account;

import hungnp.dtos.AccountDTO;
import hungnp.models.AccountDAO;
import hungnp.support.Parser;
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
public class AccountSignUpController extends HttpServlet {

    private static final String ERROR = "error";
    private static final String SUCCESS = "";
    private static final String INVALID = "sign-up";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String username = request.getParameter("username");
            AccountDAO dao = new AccountDAO();
            if (dao.checkExistence(username)){
                url = INVALID + "?duplicate=true";
            }
            else{
                String email = Parser.emptyToNull(request.getParameter("email"));
                String password = request.getParameter("password");
                String created = LocalDate.now().toString() + " " + LocalTime.now().toString();
                String lastUpdate = created;
                boolean success = dao.add(new AccountDTO(username, password, null, email, null, null, null, null, null, null, false, created, lastUpdate));
                if (!success) url += "?error=" + URLEncoder.encode("Không thể thêm tài khoản!", "utf-8");
                else{
                    url = SUCCESS;
                    HttpSession session = request.getSession();
                    session.setAttribute("username", username);
					session.setAttribute("admin", false);
                }
            }
        } catch (Exception e) {
            log("Error at AccountSignUpController: " + e.getMessage());
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
