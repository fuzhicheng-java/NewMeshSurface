/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

import java.io.PrintWriter;
import javax.servlet.http.HttpSession;

/**
 *
 * @author zhichengfu
 */
public class ProcessMeshOperations {

    public void processMesh(String fileName, String path, int option, HttpSession session) {

        String pdbPath, pqrPath, exePath, offPath, path0, outPath, rootPath, PIDNAME;
        boolean flag = false;
        try {
            Paths temp = new Paths();
            temp.p1 = "true";
            temp.p2 = "true";
            temp.p3 = "true";

            Run_External_Code runTemp = new Run_External_Code();
            session.setAttribute("Driver", runTemp);
            if (fileName == null) {
                //response.sendRedirect("exception?info='filename is null'");
            } else {
                String[] names = fileName.split("\\.");
                pdbPath = path + Paths.pdbPath + fileName;
                offPath = path + Paths.allPath + names[0] + ".off";
                outPath = path + Paths.allPath + names[0] + ".dat";
                PIDNAME = names[0];
                exePath = path + Paths.exePath;
                //pythonPath=path+Paths.pythonPath;
                pqrPath = path + Paths.pqrPath + fileName;
                if (option == 1) {
                    path0 = exePath + " " + pdbPath + " " + offPath;
                    //flag= runTemp.runCommandCode(exePath, pdbPath, offPath,outPath);
                } else {
                    path0 = Paths.pythonPath + " " + pqrPath + " " + offPath;
                    flag = runTemp.runCommandCode(Paths.pythonPath, path, PIDNAME);
                    flag = true;
                }
                if (flag) {
                    if (option == 1) {
                        ReadFile.deleteFile(pdbPath);
                    }
          //offPath=path+Paths.offPath+"115-03-22_22_11_55_4pti.off";
                    //outPath=path+Paths.outPath+"115-03-22_22_11_55_4pti.dat";
                    //response.sendRedirect("exception.jsp?info=" + runTemp.outInfo);
                    if (session.getAttribute("name") != null) {
                        session.removeAttribute("name");
                    }
                    if (session.getAttribute("outName") != null) {
                        session.removeAttribute("outName");
                    }
                    session.setAttribute("name", offPath);
                    session.setAttribute("outName", outPath);
                    //response.sendRedirect("HandleRedirection?flag=0&name=" + offPath+"&outName="+outPath);
                }

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
