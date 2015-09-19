package Code;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HandleProcessMesh extends HttpServlet
{
    
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    HttpSession session = request.getSession(true);
    String pdbPath, pqrPath, exePath, offPath,path0,outPath, rootPath,PIDNAME;
    boolean flag=false;
    try
    {
      Paths temp = new Paths();
      temp.p1 = "true";
      temp.p2 = "true";
      temp.p3 = "true";
      if (session.getAttribute("Paths") == null) {
        session.setAttribute("Paths", temp);
      } else {
        session.removeAttribute("Paths");
        session.setAttribute("Paths", temp);
      }
      String fileName = request.getParameter("name");
      String path = request.getParameter("Path");
      int option=Integer.parseInt(request.getParameter("option"));
      Run_External_Code runTemp = new Run_External_Code();
      session.setAttribute("Driver", runTemp);
      if (fileName == null) {
        response.sendRedirect("exception?info='filename is null'");
      } else {
        String[] names = fileName.split("\\.");
        pdbPath = path + Paths.pdbPath + fileName;
        offPath = path + Paths.allPath + names[0] + ".off";
        outPath=path + Paths.allPath + names[0] + ".dat";
        PIDNAME=names[0];
        exePath = path + Paths.exePath;
        //pythonPath=path+Paths.pythonPath;
        pqrPath=path+Paths.pqrPath+fileName;
//        if(Paths.pqrFilePath!=null)
//        {
//            ReadFile.deleteFile(Paths.pqrFilePath);
//        }
        if(option==1)
        {
           path0 = exePath + " " + pdbPath + " " + offPath;
           //flag= runTemp.runCommandCode(exePath, pdbPath, offPath,outPath);
        }
        else
        {
           path0 = Paths.pythonPath + " " + pqrPath + " " + offPath;
           flag= runTemp.runCommandCode(Paths.pythonPath, path, PIDNAME);
           flag=true;
        }
        if (flag) {
          if(option==1)
          {
             ReadFile.deleteFile(pdbPath);
          }
          //offPath=path+Paths.offPath+"115-03-22_22_11_55_4pti.off";
          //outPath=path+Paths.outPath+"115-03-22_22_11_55_4pti.dat";
          //response.sendRedirect("exception.jsp?info=" + runTemp.outInfo);
          if(session.getAttribute("name")!=null)
          {
              session.removeAttribute("name");
          }
          if(session.getAttribute("outName")!=null)
          {
              session.removeAttribute("outName");
          }
          session.setAttribute("name", offPath);
          session.setAttribute("outName",outPath);
          //response.sendRedirect("HandleRedirection?flag=0&name=" + offPath+"&outName="+outPath);
        }
        else
        {
          response.sendRedirect("exception.jsp?info=" + path0);
        }

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
