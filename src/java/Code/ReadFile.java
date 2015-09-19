/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.LinkedList;

public class ReadFile
{
  public String[][] vertices;
  public String[][] faces;

  public boolean readOFF(String fileStr)
    throws FileNotFoundException, IOException
  {
    String[] lines = getContentFromFile(fileStr);
    int j = 0;
    String[] fields = null;
    while ((fields == null) || (fields.length == 0)) {
      while (lines[(++j)].charAt(0) == '#');
      fields = lines[j].split(" ");
    }
    int nv = Integer.parseInt(fields[0]);
    int nf = Integer.parseInt(fields[1]);
    this.vertices = new String[nv][3];
    this.faces = new String[nf][3];
    j++;
    for (int i = 0; i < nv; i++) {
      fields = lines[(2 + i)].split(" ");
      this.vertices[i][0] = fields[2];
      this.vertices[i][1] = fields[1];
      this.vertices[i][2] = fields[0];
    }
    for (int i = 0; i < nf; i++) {
      fields = lines[(2 + nv + i)].split(" ");
      this.faces[i][0] = fields[1];
      this.faces[i][1] = fields[2];
      this.faces[i][2] = fields[3];
    }
    return true;
  }

  public boolean readOFF(String fileStr, PrintWriter out)
    throws FileNotFoundException, IOException
  {
    String[] lines = getContentFromFile(fileStr, out);

    int j = 0;
    String[] fields = null;
    while ((fields == null) || (fields.length == 0)) {
      while (lines[(++j)].charAt(0) == '#');
      fields = lines[j].split(" ");
    }
    int nv = Integer.parseInt(fields[0]);
    int nf = Integer.parseInt(fields[1]);
    this.vertices = new String[nv][3];
    this.faces = new String[nf][3];
    j++;
    for (int i = 0; i < nv; i++) {
      fields = lines[(2 + i)].split(" ");
      this.vertices[i][0] = fields[2];
      this.vertices[i][1] = fields[1];
      this.vertices[i][2] = fields[0];
    }
    for (int i = 0; i < nf; i++) {
      fields = lines[(2 + nv + i)].split(" ");
      this.faces[i][0] = fields[1];
      this.faces[i][1] = fields[2];
      this.faces[i][2] = fields[3];
    }
    return true;
  }

  public static String[] getContentFromFile(String filePath)
    throws FileNotFoundException, IOException
  {
    BufferedReader reader = new BufferedReader(new FileReader(filePath));
    LinkedList<String> temp = new LinkedList();
    int i = 0;
    String line = reader.readLine();
    while (line != null) {
      temp.add(line);
      line = reader.readLine();
    }
    reader.close();
    String[] lines = new String[temp.size()];
    for (String s : temp) {
      lines[i] = s;
      i++;
    }
    deleteFile(filePath);
    return lines;
  }

  public String[] getContentFromFile(String filePath, PrintWriter out) throws FileNotFoundException, IOException {
    LinkedList<String> temp = null;
    int i = 0;
    try
    {
      test(out, filePath);

      BufferedReader reader = new BufferedReader(new FileReader(filePath));

      temp = new LinkedList();

      String line = reader.readLine();
      while (line != null) {
        temp.add(line);
        line = reader.readLine();
      }
      reader.close();
    } catch (Exception e) {
      test(out, "this is a exception");
    }
    int max = temp.size();

    String[] lines = new String[max];
    for (String s : temp) {
      lines[i] = s;
      i++;
    }

    return lines;
  }

  public static void deleteFile(String path) {
    File file = new File(path);
    if (file.exists())
      file.delete();
  }

  public static String getThePath(String path)
  {
    String path1 = path + "t1.txt";
    File file1 = new File(path1);
    String path2 = file1.getParentFile().getAbsolutePath() + "t2.txt";
    File file2 = new File(path2);
    String result = file2.getParentFile().getAbsolutePath();
    deleteFile(path1);
    deleteFile(path2);
    return result;
  }

  public static void test(PrintWriter out, String info) {
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<title>Servlet NewServlet</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>" + info + "</h1>");
    out.println("</body>");
    out.println("</html>");
  }

  public static String CurrentTime()
  {
    Date now = new Date();

    int year = now.getYear();
    int month = now.getMonth() + 1;
    int day = now.getDate();

    int hh = now.getHours();
    int mm = now.getMinutes();
    int ss = now.getSeconds();

    String clock = year + "-";

    if (month < 10) {
      clock = clock + "0";
    }

    clock = clock + month + "-";

    if (day < 10) {
      clock = clock + "0";
    }

    clock = clock + day + "_";

    if (hh < 10) {
      clock = clock + "0";
    }

    clock = clock + hh + "_";
    if (mm < 10) {
      clock = clock + '0';
    }
    clock = clock + mm + "_";
    if (ss < 10) {
      clock = clock + '0';
    }
    clock = clock + ss;
    return clock;
  }
}