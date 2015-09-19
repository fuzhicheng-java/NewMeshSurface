package Code;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HandleDownLoadFile extends HttpServlet
{
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    HttpSession session = request.getSession();
    try
    {
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<title>OFF_File</title>");
      out.println("</head>");
      out.println("<body>");
      Feature feature = (Feature)session.getAttribute("files");

      out.println("OFF<br>");
      out.println(feature.vLength + " " + feature.fLength + "<br>");
      for (int i = 0; i < feature.vLength; i++)
      {
        out.print(feature.vertices[i][0] + " " + feature.vertices[i][1] + " " + feature.vertices[i][2] + "<br>");
      }
      for (int i = 0; i < feature.fLength; i++)
      {
        out.print("3 " + feature.faces[i][0] + " " + feature.faces[i][1] + " " + feature.faces[i][2] + "<br>");
      }
      out.println("</body>");
      out.println("</html>");
    }
    finally {
      out.close();
    }
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    processRequest(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    processRequest(request, response);
  }

  public String getServletInfo()
  {
    return "Short description";
  }
}
