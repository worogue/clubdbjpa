/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import business.Member;
import business.MemberDB;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author kmne6
 */
public class ClubLogonServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String msg = "";
        String userid = "";
        String URL = "/Logon.jsp";
        Long passatt;
        Member m;
        

        try {
            // should check validity of userid before using it
            userid = request.getParameter("userid").trim();
            m = MemberDB.getMemberByID(userid);
            if (m == null) {
                msg = "No membre record retrieved<br>";
            } else {
                // msg = "Member " + m.getLastnm() + " found.";
                passatt = Long.parseLong(request.getParameter("password").trim());
                m.setPassattempt(passatt);
                if(!m.isAuthenticated()) {
                    msg = "Unable to aunthenitcate<br>";
                } else {
                    URL = "/MemberScreen.jsp";
                    request.getSession().setAttribute("m", m);
                }
            }
        } catch (Exception e) {
            msg = "Exception: " + e.getMessage();
        }

        request.setAttribute("msg", msg);

        RequestDispatcher disp = getServletContext().getRequestDispatcher(URL);
        disp.forward(request, response);
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
