/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ag
 */
@WebServlet(urlPatterns = {"/SetReview"})
public class SetReview extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            try{
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/hotelsystem";
                String user = "root";
                String password = "root";
                Connection Con = null;
                Statement Stmt = null;
                ResultSet RS = null;
                int Rss ;
                Con = DriverManager.getConnection(url, user, password);
                Stmt = Con.createStatement();
                String HotelID = request.getParameter("hotelid");  
                String StarsNum = request.getParameter("rate");
                String UserComm = request.getParameter("comment");
                int StarsNumVal = Integer.parseInt(StarsNum); 
                String StarsNumValue = Integer.toString(StarsNumVal*2);
              
                String Line1 = "SELECT * FROM hotel where hotelID = '"+ HotelID + "'"; 
                RS = Stmt.executeQuery(Line1);    
                    if(RS.next()){  
                        String Line2 = "UPDATE hotel SET userRating='"+ StarsNumValue +"' ,userComments='"+UserComm+"'  WHERE hotelID='"+ HotelID+"'"; 
                        Rss = Stmt.executeUpdate(Line2);  
                        out.println("<script type=\"text/javascript\"> alert('Your Review has been recorded');  </script>");
                        //response.sendRedirect("HotelInfo.jsp"); //comment to show alert
                    }
                    else{
                    out.print("Can't be done");
                     }
                Stmt.close();
                Con.close();  
                
           } catch (Exception ex) {
            ex.printStackTrace();
        }

            
            
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SetReview.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SetReview.class.getName()).log(Level.SEVERE, null, ex);
        }
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
