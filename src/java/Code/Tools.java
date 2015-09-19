/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 *
 * @author zhichengfu
 */
public class Tools {
    
  int value=0;
  public boolean readOFF(BufferedReader reader, Feature feature, PrintWriter out) throws FileNotFoundException, IOException
  {
    String[] lines = getContentFromFile(reader, out);

    int j = 0;
    String[] fields = null;
    while ((fields == null) || (fields.length == 0)) {
      while (lines[(++j)].charAt(0) == '#');
      fields = lines[j].split(" ");
    }
    int nv = Integer.parseInt(fields[0]);
    int nf = Integer.parseInt(fields[1]);
    feature.vertices = new String[nv][3];
    feature.faces = new String[nf][3];
    j++;
    for (int i = 0; i < nv; i++) {
      fields = lines[(2 + i)].split(" ");
      feature.vertices[i][0] = fields[2];
      feature.vertices[i][1] = fields[1];
      feature.vertices[i][2] = fields[0];
    }
    for (int i = 0; i < nf; i++) {
      fields = lines[(2 + nv + i)].split(" ");
      feature.faces[i][0] = fields[1];
      feature.faces[i][1] = fields[2];
      feature.faces[i][2] = fields[3];
    }
    return true;
  }

  public String[] getContentFromFile(BufferedReader reader, PrintWriter out)
    throws FileNotFoundException, IOException
  {
    LinkedList<String> temp = new LinkedList();
    int i = 0;
    String line = reader.readLine();
    ReadFile.test(out, line);
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

    return lines;
  }

  public String getTrim(int index, String[] temp)
  {
    int ind = 0;
    for (int i = 0; i < temp.length; i++)
    {
      if (!temp[i].equals(""))
      {
        ind++;
        if (ind == index)
        {
          return temp[i];
        }
      }
    }
    return null;
  }

  public void readAllOFF(String offPath,Feature feature, int flag) throws FileNotFoundException, IOException {
    BufferedReader reader = new BufferedReader(new FileReader(offPath));

    String head = reader.readLine();
    if (head.equals("OFF"))
    {
      String[] temp = reader.readLine().split(" ");
      int nv = Integer.parseInt(getTrim(1, temp));
      ComputeResult.totalNumber=nv;
      
      int nf = Integer.parseInt(getTrim(2, temp));
      feature.fLength = nf;
      feature.vLength = nv;
      feature.vertices = new String[nv][3];
      feature.faces = new String[nf][3];

      for (int n = 0; n < nv; n++) {
        temp = reader.readLine().split(" ");
        if (flag == 0) {
          feature.vertices[n][2] = String.valueOf(getTrim(1, temp));
          feature.vertices[n][1] = String.valueOf(getTrim(2, temp));
          feature.vertices[n][0] = String.valueOf(getTrim(3, temp));
        } else {
          feature.vertices[n][0] = String.valueOf(getTrim(1, temp));
          feature.vertices[n][1] = String.valueOf(getTrim(2, temp));
          feature.vertices[n][2] = String.valueOf(getTrim(3, temp));
        }
      }

      for (int n = 0; n < nf; n++) {
        temp = reader.readLine().split(" ");

        feature.faces[n][0] = String.valueOf(getTrim(2, temp));
        feature.faces[n][1] = String.valueOf(getTrim(3, temp));
        feature.faces[n][2] = String.valueOf(getTrim(4, temp));
      }
      reader.close();
    }
  }
  
    public void readComputeResult(String computePath,ComputeResult cr, int flag) throws FileNotFoundException, IOException
    {
       
      try (BufferedReader reader = new BufferedReader(new FileReader(computePath))) {
          
          String[] temp;
          String readLine=reader.readLine();
          cr.Green=new String[ComputeResult.totalNumber][3];
          cr.Psi=new String[ComputeResult.totalNumber][3];
          cr.Phit=new String[ComputeResult.totalNumber][3];
          cr.Phi=new String[ComputeResult.totalNumber][3];
          cr.SubDomain=new int[ComputeResult.totalNumber];
          for(int n=0;n<ComputeResult.totalNumber;n++)
          {
              temp=reader.readLine().split(" ");
              
              cr.Green[n][0]=String.valueOf(this.getTrim(5, temp));
              cr.Psi[n][0]=String.valueOf(this.getTrim(6, temp));
              cr.Phit[n][0]=String.valueOf(this.getTrim(7, temp));
              cr.Phi[n][0]=String.valueOf(this.getTrim(8, temp));
              cr.SubDomain[n]=Integer.parseInt(this.getTrim(9, temp));
              
              if(n==0)
              {
                  cr.GreenMax=Double.parseDouble(cr.Green[n][0]);
                  cr.GreenMin=Double.parseDouble(cr.Green[n][0]);
                  cr.PsiMax=Double.parseDouble(cr.Psi[n][0]);
                  cr.PsiMin=Double.parseDouble(cr.Psi[n][0]);
                  cr.PhiMax=Double.parseDouble(cr.Phi[n][0]);
                  cr.PhiMin=Double.parseDouble(cr.Phi[n][0]);
                  cr.PhitMax=Double.parseDouble(cr.Phit[n][0]);
                  cr.PhitMin=Double.parseDouble(cr.Phit[n][0]);
              }
              else
              {
                  if(cr.GreenMax<Double.parseDouble(cr.Green[n][0]))
                  {
                      cr.GreenMax=Double.parseDouble(cr.Green[n][0]);
                  }
                  if(cr.GreenMin>Double.parseDouble(cr.Green[n][0]))
                  {
                      cr.GreenMin=Double.parseDouble(cr.Green[n][0]);
                  }
                  
                  if(cr.PhiMax<Double.parseDouble(cr.Phi[n][0]))
                  {
                      cr.PhiMax=Double.parseDouble(cr.Phi[n][0]);
                  }
                  if(cr.PhiMin>Double.parseDouble(cr.Phi[n][0]))
                  {
                      cr.PhiMin=Double.parseDouble(cr.Phi[n][0]);
                  }
                  
                  if(cr.PsiMax<Double.parseDouble(cr.Psi[n][0]))
                  {
                      cr.PsiMax=Double.parseDouble(cr.Psi[n][0]);
                  }
                  if(cr.PsiMin>Double.parseDouble(cr.Psi[n][0]))
                  {
                      cr.PsiMin=Double.parseDouble(cr.Psi[n][0]);
                  }
                  
                  if(cr.PhitMax<Double.parseDouble(cr.Phit[n][0]))
                  {
                      cr.PhitMax=Double.parseDouble(cr.Phit[n][0]);
                  }
                  if(cr.PhitMin>Double.parseDouble(cr.Phit[n][0]))
                  {
                      cr.PhitMin=Double.parseDouble(cr.Phit[n][0]);
                  }
              }
          }
          
        for(int n=0;n<ComputeResult.totalNumber;n++)
        {
            value=this.getColorValue(Double.parseDouble(cr.Green[n][0]), cr.GreenMin, cr.GreenMax);
            if(value<50)
            {
                double ff=(value)/50.0;
                cr.Green[n][0]=String.valueOf(1.0);
                cr.Green[n][1]=String.valueOf(ff);
                cr.Green[n][2]=String.valueOf(ff);
            }
            else if(value>50)
            {
                double ff=1.0-(value-50.0)/50.0;
                cr.Green[n][0]=String.valueOf(ff);
                cr.Green[n][1]=String.valueOf(ff);
                cr.Green[n][2]=String.valueOf(1.0);
            }
            else
            {
                cr.Green[n][0]=String.valueOf(1.0);
                cr.Green[n][1]=String.valueOf(1.0);
                cr.Green[n][2]=String.valueOf(1.0);
            }
            
            value=this.getColorValue(Double.parseDouble(cr.Psi[n][0]), cr.PsiMin, cr.PsiMax);
            if(value<50)
            {
                double ff=(value)/50.0;
                cr.Psi[n][0]=String.valueOf(1.0);
                cr.Psi[n][1]=String.valueOf(ff);
                cr.Psi[n][2]=String.valueOf(ff);
            }
            else if(value>50)
            {
                double ff=1.0-(value-50.0)/50.0;
                cr.Psi[n][0]=String.valueOf(ff);
                cr.Psi[n][1]=String.valueOf(ff);
                cr.Psi[n][2]=String.valueOf(1.0);
            }
            else
            {
                cr.Psi[n][0]=String.valueOf(1.0);
                cr.Psi[n][1]=String.valueOf(1.0);
                cr.Psi[n][2]=String.valueOf(1.0);
            }
            
            value=this.getColorValue(Double.parseDouble(cr.Phi[n][0]), cr.PhiMin, cr.PhiMax);
            if(value<50)
            {
                double ff=(value)/50.0;
                cr.Phi[n][0]=String.valueOf(1.0);
                cr.Phi[n][1]=String.valueOf(ff);
                cr.Phi[n][2]=String.valueOf(ff);
            }
            else if(value>50)
            {
                double ff=1.0-(value-50.0)/50.0;
                cr.Phi[n][0]=String.valueOf(ff);
                cr.Phi[n][1]=String.valueOf(ff);
                cr.Phi[n][2]=String.valueOf(1.0);
            }
            else
            {
                cr.Phi[n][0]=String.valueOf(1.0);
                cr.Phi[n][1]=String.valueOf(1.0);
                cr.Phi[n][2]=String.valueOf(1.0);
            }
            
            value=this.getColorValue(Double.parseDouble(cr.Phit[n][0]), cr.PhitMin, cr.PhitMax);
            if(value<50)
            {
                double ff=(value)/50.0;
                cr.Phit[n][0]=String.valueOf(1.0);
                cr.Phit[n][1]=String.valueOf(ff);
                cr.Phit[n][2]=String.valueOf(ff);
            }
            else if(value>50)
            {
                double ff=1-(value-50.0)/50.0;
                cr.Phit[n][0]=String.valueOf(ff);
                cr.Phit[n][1]=String.valueOf(ff);
                cr.Phit[n][2]=String.valueOf(1.0);
            }
            else
            {
                cr.Phit[n][0]=String.valueOf(1.0);
                cr.Phit[n][1]=String.valueOf(1.0);
                cr.Phit[n][2]=String.valueOf(1.0);
            }
        }
      }
    }
    
    public int getColorValue(double value, double min ,double max)
    {
        double temp=(value-min)/(max-min);
        return (int) Math.floor(100*temp);
    }
    
    
    
}
