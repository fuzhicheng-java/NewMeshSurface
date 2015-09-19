package Code;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class HandleUpLoadFile extends HttpServlet
{
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    HttpSession session = request.getSession();
    try
    {
      String path = getClass().getResource("/").getPath();
      if (Paths.path == null)
      {
        File file = new File(path + "test.html");
        path = file.getParent();
        File file1 = new File(path + "test1.html");
        path = file1.getParent();
        File file2 = new File(path + "test1.html");
        path = file2.getParent();
        Paths.path = path;
      }
      else
      {
        path = Paths.path;
      }

      String fileName = "";
      String name = "";
      int flag = 0;
      String[] data=new String[38];
      int index=0;
      if (ServletFileUpload.isMultipartContent(request)) {
        List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
        
        for (FileItem item : multiparts) {
          if (!item.isFormField()) {
            String tet = item.getName().toLowerCase();
            name = new File(item.getName()).getName();
            name = ReadFile.CurrentTime() + "_" + name;
              if (tet.endsWith(".pdb")) {
                  String[] test1= name.split("\\.");
                  name=test1[0]+".pqr";
                  fileName = path + Paths.pqrPath + name;
                  flag = 3;
              } else if (tet.endsWith(".off")) {
                  fileName = path + Paths.offPath + name;
                  flag = 2;
              } else if (tet.endsWith(".pqr")) {
                  fileName = path + Paths.allPath + name;
                  flag = 3;
              }
            item.write(new File(fileName));
          }
          else
          {
              if(index==5&&!item.getFieldName().equals("isSaveMesh"))
              {
                  data[index++]="off";
              }
              if(index==6&&!item.getFieldName().equals("isSavePoten"))
              {
                  data[index++]="off";
              }
                     
              data[index++]=item.getString();
          }
        }
        if (flag!=2)
        {
          String pName=path+Paths.parametersPath+ReadFile.CurrentTime()+"-"+"Parameters.py";
          Parameters pas=new Parameters();
          Parameters.initParameters(pas, data);
          Parameters.writeFile(pName, pas);
          ProcessMeshOperations PMO=new ProcessMeshOperations();
          PMO.processMesh(name, path, flag, session);
          //response.sendRedirect("HandleProcessMesh?name=" + name + "&Path=" + path+"&option="+flag);
        }
        else {
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
          response.sendRedirect("HandleRedirection?flag=1&name=" + fileName);
        }
      }
    }
    catch (Exception e) {
      //e.printStackTrace();
      response.sendRedirect("exception.jsp?info="+e.getMessage());
      out.println("This is a test");
    }
    finally {
      //out.println("This is a test1");
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