<%-- 
    Document   : inputPage
    Created on : Apr 6, 2015, 6:06:58 PM
    Author     : zhichengfu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="mainCss.css" rel="stylesheet">
    </head>
    <script>
        var myinterval;
        function showParas()
        {
            document.getElementById("parametersA").style.display = "block";
            document.getElementById("side-background").style.display = "block";
        }

        function closeBox()
        {
            document.getElementById("parametersA").style.display = "none";
            document.getElementById("side-background").style.display = "none";
        }

        function startLogProcess()
        {
            var xmlhttp = new XMLHttpRequest();
            xmlhttp.onreadystatechange = function ()
            {
                if (xmlhttp.readyState === 4 && xmlhttp.status === 200)
                {
                    if (xmlhttp.responseText.toString()!=="1"&&xmlhttp.responseText.toString()!== "")
                    {
                        var element=document.getElementById("logDetails");
                        element.innerHTML = xmlhttp.responseText;
                        element.scrollTop = element.scrollHeight;

                    }
                    else if(xmlhttp.responseText.toString()==="1")
                    {
                        clearInterval(myinterval);
                        document.getElementById("meshShowButton").disabled=false;
                    }
                }
            };
            xmlhttp.open("GET", "HandleGetLog", true);
            xmlhttp.send();
        }
        
        function loadLogProcess()
        {
            myinterval=setInterval(function(){ startLogProcess();}, 2000);
            var formElement = document.getElementById("formData");
            var formData = new FormData(formElement);
            var xmlhttp = new XMLHttpRequest();
            xmlhttp.open("POST", "HandleUpLoadFile", true);
            xmlhttp.send(formData);
            //return false;
        }
        
        function showResult()
        {
            window.location="HandleShowResult";
        }
    </script>
    <body>
        <div id="parameterInput">       
            <div id="parameterContent" style="text-align: center;width:96%;padding:1%;height:400px;">
                <form id="formData" action='' method='post' enctype='multipart/form-data'>
                    <input type="file" name="file" style="width:96%;border:solid;padding:1%">
                    <br/><br/>
                    <table style="width:100%; text-align: left; height:353px;border:solid">
                        <tr><th style="text-align: center" colspan="2">Parameters Settings:</th></tr>
                        <tr><td>Select a PBE Solver<br/></td>
                            <td><select name="PBESolver">
                                    <option value="PBESolver1">PBESolver1</option>
                                    <option value="PBESolver2">PBESolver2</option>
                                    <option value="PBESolver3">PBESolver3</option>
                                </select>
                            </td></tr>
                        <tr><td>Biomolecular dielectric constant<br/></td>
                            <td><input type="text" name="BDConstant"></td></tr>
                        <tr><td>Solvent dielectric constant<br/></td>
                            <td><input type="text" name="SDConstant"></td></tr>
                        <tr><td>Solvent ionic strength<br/></td>
                            <td><input type="text" name="SIStrength"></td></tr>
                        <tr><td>Temperature<br/></td>
                            <td><input type="text" name="Temperature"></td></tr>
                        <tr>
                            <td><input type="checkbox" name="isSaveMesh"><label>Save Mesh in File</label><br/></td>
                            <td></td></tr>
                        <tr>
                            <td><input type="checkbox" name="isSavePoten"><label>Save Potentials in File</label><br/></td>
                            <td></td></tr>
                        <tr><td style="text-align: center" colspan="2"><br/><label id="aparameters" onclick="showParas()">Advanced Parameters</label><br/><br/></td></tr>
                    </table> <br/><br/>
                    <div id="parametersA" style="display:none">
                        <div><br/><lable id="closeTag" style="color:white" onclick="closeBox()">Close This Page</lable><br/><br/></div>
                        <div id="parametersTemp">
                            <table id="meshParameterTable">
                                <caption style="color:white;font-size:20px;font-weight: bold;">Mesh Parameters:</caption>
                                <tr><td>Domain_Flag</td><td><input type="text" name="domain_flag" value="0"></td></tr>
                                <tr><td>SurfMethod</td><td><input type="text" name="surfmethod" value="0"></td></tr>
                                <tr><td>Probe_Radius</td><td><input type="text" name="probe_radius" value="1.4"></td></tr>
                                <tr><td>IsoValue</td><td><input type="text" name="isovalue" value="1.0"></td></tr>
                                <tr><td>BLOBBYNESS</td><td><input type="text" name="blobbyness" value="-0.2"></td></tr>
                                <tr><td>DIM_SCALE</td><td><input type="text" name="dim_scale" value="1.99"></td></tr>
                                <tr><td>CoarsenRate</td><td><input type="text" name="coarsenrate" value="0.3"></td></tr>
                                <tr><td>MIN_Volume</td><td><input type="text" name="min_volume" value="10000"></td></tr>
                                <tr><td>SphereRatio</td><td><input type="text" name="sphereratio" value="10"></td></tr>
                                <tr><td>OutSurfLayer</td><td><input type="text" name="outsurflayer" value="6"></td></tr>
                                <tr><td>MeshSizeUpperLimit</td><td><input type="text" name="meshsizeupperlimit" value="80001"></td></tr>
                                <tr><td>MeshSizeLowerLimit</td><td><input type="text" name="meshsizelowerlimit" value="10000"></td></tr>
                                <tr><td>ITER_NUM</td><td><input type="text" name="item_num" value="6"></td></tr>
                                <tr><td>MinRatio</td><td><input type="text" name="minratio" value="2.0"></td></tr>
                                <tr><td>H_Size</td><td><input type="text" name="h_size" value="5"></td></tr>
                                <tr><td>MaxVolumefactor</td><td><input type="text" name="maxvolumefactor" value="10.0"></td></tr>
                                <tr><td>TetgenStats</td><td><input type="text" name="tetgenstats" value="1"></td></tr>
                            </table>
                            <table id="controlParameterTable">
                                <caption style="color:white;font-size:20px;font-weight: bold;">Control Parameters:</caption>
                                <tr><td>Order</td><td><input type="text" name="order" value="1"></td></tr>
                                <tr><td>Hyper_Bound</td><td><input type="text" name="hyper_bound" value="85.0"></td></tr>
                                <tr><td>Hyper_Flag</td><td><input type="text" name="hyper_flag" value="0"></td></tr>
                                <tr><td>NX</td><td><input type="text" name="nx" value="128"></td></tr>
                                <tr><td>NY</td><td><input type="text" name="ny" value="128"></td></tr>
                                <tr><td>NZ</td><td><input type="text" name="nz" value="128"></td></tr>
                                <tr><td>Mesh_Scale</td><td><input type="text" name="mesh_scale" value="1.0"></td></tr>
                                <tr><td>Flag_G</td><td><input type="text" name="flag_g" value="0"></td></tr>
                                <tr><td>Flag_U0</td><td><input type="text" name="flag_u0" value="1"></td></tr>
                                <tr><td>WRITE_OPENDX_LOCAL</td><td><input type="text" name="open_local" value="False"></td></tr>
                                <tr><td>WRITE_POT_LOCAL</td><td><input type="text" name="pot_local" value="False"></td></tr>

                            </table>
                        </div>  
                    </div>
                    <div id="side-background" class="overlay-background-food" onclick="closeSideBoxFood(this)"></div>
                    <table style="width:100%">
                        <tr>
                            <td><input type="button" onclick="loadLogProcess()" value="Run SDPBS"></td>
                            <td><input id="meshShowButton" type="button" onclick="showResult()" value="View Result"  disabled></td>
                        </tr>
                    </table>
                    
                </form>


            </div>
        </div>
        <div id="logInformation" style="text-align: center">

            <div id="logDetails" style="border:solid;height:400px">

            </div>
            <br/>
            <br/>
            <input type="submit"  value="Save as Log File">

        </div>
    </body>
</html>
