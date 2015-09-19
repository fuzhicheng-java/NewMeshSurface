/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Paths
{
  public static String path = null;
  public static String pdbPath = "/pdbPath/";
  public static String exePath = "/C_Code_Exe/MeshSurfaceUnix";
  public static String pythonPath="python /Users/yijiang/Codes/Web-SDPBS/Run/Examples/PBE/PBE_driver2.py";
  public static String offPath = "/offPath/";
  public static String pqrPath="/pqrPath/";
  public static String outPath="/outPath/";
  public static String allPath="/allPath/";
  public static String parametersPath="/parameters/";
  public static String source="source /Applications/FEniCS1.3.app/Contents/Resources/share/fenics/fenics.conf";
  public String fileName;
  public String pdbName;
  public String offName;
  public String p1;
  public String p2;
  public String p3;
  
  public static String pqrFilePath=null;

  public static String getNowDate()
  {
    String format = "yyyy_MM_dd_hh_mm_ss";
    Date date = new Date();
    String str = null;
    SimpleDateFormat df = new SimpleDateFormat(format);
    str = df.format(date);
    return str;
  }
}
