package Code;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HandleRedirection extends HttpServlet
{
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    HttpSession session = request.getSession();
    try
    {
      String offName = request.getParameter("name");
      String outName = request.getParameter("outName");
      Tools tools=new Tools();
      int flag = Integer.parseInt(request.getParameter("flag"));
      Feature feature = new Feature();
      ComputeResult CR =new ComputeResult();
      feature.offName = offName;
      tools.readAllOFF(offName, feature, flag);
      tools.readComputeResult(outName, CR, flag);
      if ((feature.faces != null) && (feature.vertices != null)) {
        if (session.getAttribute("files") == null) {
          session.setAttribute("files", feature);
        } else {
          session.removeAttribute("files");
          session.setAttribute("files", feature);
        }
        
        if (session.getAttribute("computedResult") == null) {
          session.setAttribute("computedResult", CR.Phi);
        } else {
          session.removeAttribute("computedResult");
          session.setAttribute("computedResult", CR.Phi);
        }
        
        //ReadFile.deleteFile(offName);
        //ReadFile.deleteFile(outName);
        response.sendRedirect("MeshShow.jsp?index=1");
      } else {
        //ReadFile.deleteFile(offName);
        //ReadFile.deleteFile(outName);
        response.sendRedirect("exception.jsp?info='can not read off file'");
      }

    }
    catch (Exception e)
    {
      response.sendRedirect("exception.jsp?info="+e.getMessage());
    }
    finally
    {
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