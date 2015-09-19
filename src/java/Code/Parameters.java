/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zhichengfu
 */
public class Parameters {

    public String Domain_flag;
    public String Probe_radius;
    public String SurfMethod;
    public String IsoValue;
    public String BLOBBYNESS;
    public String DIM_SCALE;
    public String CoarsenRate;
    public String MIN_VOLUME;
    public String SphereRatio;
    public String OutSurfLayer;
    public String MeshSizeUpperLimit;
    public String MeshSizeLowerLimit;
    public String ITER_NUM;
    public String MinRatio;
    public String H_size;
    public String MaxVolumefactor;
    public String MaxVolume;
    public String TetgenStats;

    public String eps_p;
    public String eps_s;
    public String T;
    public String Is;

    public String Hyper_bound;
    public String Hyper_flag;
    public String NX;
    public String NY;
    public String NZ;
    public String PLOT_PERFORMANCE = "False";
    public String mesh_scale;
    public String order;
    public String flag_g;
    public String flag_u0;
    public String WRITE_OPENDX_LOCAL;
    public String WRITE_POT_LOCAL;

    public String PBESolver;

    public String saveMesh;

    public String savePoFile;
    
    public String saveMesh_Name="saveMesh";
    public String savePoFile_Name="savePoFile";

    public String Domain_flag_Name = "Domain_flag";
    public String Probe_radius_Name = "Probe_radius";
    public String SurfMethod_Name = "SurfMethod";
    public String IsoValue_Name = "IsoValue";
    public String BLOBBYNESS_Name = "BLOBBYNESS";
    public String DIM_SCALE_Name = "DIM_SCALE";
    public String CoarsenRate_Name = "CoarsenRate";
    public String MIN_VOLUME_Name = "MIN_VOLUME";
    public String SphereRatio_Name = "SphereRatio";
    public String OutSurfLayer_Name = "OutSurfLayer";
    public String MeshSizeUpperLimit_Name = "MeshSizeUpperLimit";
    public String MeshSizeLowerLimit_Name = "MeshSizeLowerLimit";
    public String ITER_NUM_Name = "ITER_NUM";
    public String MinRatio_Name = "MinRatio";
    public String H_size_Name = "H_size";
    public String MaxVolumefactor_Name = "MaxVolumefactor";
    public String MaxVolume_Name = "MaxVolume";
    public String TetgenStats_Name = "TetgenStats";

    public String eps_p_Name = "eps_p";
    public String eps_s_Name = "eps_s";
    public String T_Name = "T";
    public String Is_Name = "Is";

    public String Hyper_bound_Name = "Hyper_bound";
    public String Hyper_flag_Name = "Hyper_flag";
    public String NX_Name = "NX";
    public String NY_Name = "NY";
    public String NZ_Name = "NZ";
    public String PLOT_PERFORMANCE_Name = "PLOT_PERFORMANCE";
    public String mesh_scale_Name = "mesh_scale";
    public String order_Name = "order_Name";
    public String flag_g_Name = "flag_g";
    public String flag_u0_Name = "flag_u0";
    public String WRITE_OPENDX_LOCAL_Name = "WRITE_OPENDX_LOCAL";
    public String WRITE_POT_LOCAL_Name = "WRITE_POT_LOCAL";
    public String PBESolver_Name="PBESolver";

    public static void initParameters(Parameters pas, String[] temps) {
        int index = 0;
        pas.PBESolver = temps[index++];
        pas.eps_p = temps[index++];
        pas.eps_s = temps[index++];
        pas.Is = temps[index++];
        pas.T = temps[index++];
        pas.saveMesh = temps[index++];
        pas.savePoFile = temps[index++];
        pas.Domain_flag = temps[index++];
        pas.SurfMethod = temps[index++];
        pas.Probe_radius = temps[index++];
        pas.IsoValue = temps[index++];
        pas.BLOBBYNESS = temps[index++];
        pas.DIM_SCALE = temps[index++];
        pas.CoarsenRate = temps[index++];
        pas.MIN_VOLUME = temps[index++];
        pas.SphereRatio = temps[index++];
        pas.OutSurfLayer = temps[index++];
        pas.MeshSizeUpperLimit = temps[index++];
        pas.MeshSizeLowerLimit = temps[index++];
        pas.ITER_NUM = temps[index++];
        pas.MinRatio = temps[index++];
        pas.H_size = temps[index++];
        pas.MaxVolumefactor = temps[index++];
        pas.TetgenStats = temps[index++];

        pas.order = temps[index++];
        pas.Hyper_bound = temps[index++];
        pas.Hyper_flag = temps[index++];
        pas.NX = temps[index++];
        pas.NY = temps[index++];
        pas.NZ = temps[index++];
        pas.mesh_scale = temps[index++];
        pas.flag_g = temps[index++];
        pas.flag_u0 = temps[index++];
        pas.WRITE_OPENDX_LOCAL = temps[index++];
        pas.WRITE_POT_LOCAL = temps[index++];

    }
    
    public static void writeFile(String path,Parameters pas)
    {
        try {
            BufferedWriter writer=new BufferedWriter(new FileWriter(path));
            writer.write(pas.BLOBBYNESS_Name+"="+pas.BLOBBYNESS);
            writer.newLine();
            writer.write(pas.CoarsenRate_Name+"="+pas.CoarsenRate);
            writer.newLine();
            writer.write(pas.DIM_SCALE_Name+"="+pas.DIM_SCALE);
            writer.newLine();
            writer.write(pas.Domain_flag_Name+"="+pas.Domain_flag);
            writer.newLine();
            writer.write(pas.H_size_Name+"="+pas.H_size);
            writer.newLine();
            writer.write(pas.Hyper_bound_Name+"="+pas.Hyper_bound);
            writer.newLine();
            writer.write(pas.Hyper_flag_Name+"="+pas.Hyper_flag);
            writer.newLine();
            writer.write(pas.ITER_NUM_Name+"="+pas.ITER_NUM);
            writer.newLine();
            writer.write(pas.Is_Name+"="+pas.Is);
            writer.newLine();
            writer.write(pas.IsoValue_Name+"="+pas.IsoValue);
            writer.newLine();
            writer.write(pas.MIN_VOLUME_Name+"="+pas.MIN_VOLUME);
            writer.newLine();
            writer.write(pas.MaxVolumefactor_Name+"="+pas.MaxVolumefactor);
            writer.newLine();
            writer.write(pas.MeshSizeLowerLimit_Name+"="+pas.MeshSizeLowerLimit);
            writer.newLine();
            writer.write(pas.MeshSizeUpperLimit_Name+"="+pas.MeshSizeUpperLimit);
            writer.newLine();
            writer.write(pas.MinRatio_Name+"="+pas.MinRatio);
            writer.newLine();
            writer.write(pas.NX_Name+"="+pas.NX);
            writer.newLine();
            writer.write(pas.NY_Name+"="+pas.NY);
            writer.newLine();
            writer.write(pas.NZ_Name+"="+pas.NZ);
            writer.newLine();
            writer.write(pas.OutSurfLayer_Name+"="+pas.OutSurfLayer);
            writer.newLine();
            writer.write(pas.PBESolver_Name+"="+pas.PBESolver);
            writer.newLine();
            writer.write(pas.PLOT_PERFORMANCE_Name+"="+pas.PLOT_PERFORMANCE);
            writer.newLine();
            writer.write(pas.Probe_radius_Name+"="+pas.Probe_radius);
            writer.newLine();
            writer.write(pas.SphereRatio_Name+"="+pas.SphereRatio);
            writer.newLine();
            writer.write(pas.SurfMethod_Name+"="+pas.SurfMethod);
            writer.newLine();           
            writer.write(pas.T_Name+"="+pas.T);
            writer.newLine();
            writer.write(pas.TetgenStats_Name+"="+pas.TetgenStats);
            writer.newLine();
            writer.write(pas.WRITE_OPENDX_LOCAL_Name+"="+pas.WRITE_OPENDX_LOCAL);
            writer.newLine();
            writer.write(pas.WRITE_POT_LOCAL_Name+"="+pas.WRITE_POT_LOCAL);
            writer.newLine();
            writer.write(pas.eps_p_Name+"="+pas.eps_p);
            writer.newLine();
            writer.write(pas.eps_s_Name+"="+pas.eps_s);
            writer.newLine();
            writer.write(pas.flag_g_Name+"="+pas.flag_g);
            writer.newLine();           
            writer.write(pas.flag_u0_Name+"="+pas.flag_u0);
            writer.newLine();
            writer.write(pas.mesh_scale_Name+"="+pas.mesh_scale);
            writer.newLine();
            writer.write(pas.order_Name+"="+pas.order);
            writer.newLine();
            writer.write(pas.saveMesh_Name+"="+pas.saveMesh);
            writer.newLine();
            writer.write(pas.savePoFile_Name+"="+pas.savePoFile);
            writer.newLine();
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Parameters.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
