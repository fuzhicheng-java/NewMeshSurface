/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.LinkedList;

public class Run_External_Code {

    public LinkedList<String> allLogs=new LinkedList<>();
    public int isFinished=0;
    public String outInfo="";
    public boolean runCommandCode(String exePath, String rootPath, String pName) {
        boolean flag = false;
        try {
            System.out.println("Executing notepad.exe");
            String path0 = exePath + " " + rootPath + " " + pName;
            Process process1 = Runtime.getRuntime().exec(path0);
            InputStream outInfo = process1.getInputStream();
            String filePath = rootPath + "/allPath/rest.log";
            BufferedReader reader = new BufferedReader(new InputStreamReader(outInfo));
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            String line = null;
            line = reader.readLine();
            while (line != null) {
                writer.write(line);
                allLogs.add(line);
                writer.newLine();
                line = reader.readLine();
            }
            writer.close();
            int value1 = process1.waitFor();
            if (value1 == 0) {
                process1.destroy();
                flag = true;
            }
            this.isFinished=1;
            return flag;
        } catch (Exception ex) {
            this.outInfo=ex.getMessage();
            flag = false;

            return flag;
        } finally {
            return flag;
        }

    }

    public static void main(String[] args)
            throws FileNotFoundException, IOException {
        String path = "/Users/zhichengfu/NetBeansProjects/SurfaceMesh/src/java/C_Codes/MeshSurface /Users/zhichengfu/NetBeansProjects/SurfaceMesh/pdbPath/1CID2013_09_22_06_33_49.pdb /Users/zhichengfu/NetBeansProjects/SurfaceMesh/offPath/1TIM2013_09_22_04_36_32.off";
    }
}
