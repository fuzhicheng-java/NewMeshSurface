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
    <body>
        <div id="parameterInput">

            <div id="parameterContent" style="text-align: center;width:96%;padding:1%;height:400px;">
                <form action='HandleUpLoadFile' method='post' enctype='multipart/form-data'>
                    <input type="file" name="file" style="width:96%;border:solid;padding:1%" required>
                    <br/><br/>
                    <table style="width:100%; text-align: left; border:solid">
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
                        <tr><td style="text-align: center" colspan="2"><button>Advanced Parameters</button></td></tr>
                    </table>



                    <br/><br/>
                    <input type="submit" value="Run SDPBS">
                </form>


            </div>
        </div>
        <div id="logInformation" style="text-align: center">

            <div style="border:solid;height:400px">

            </div>
            <br/>
            <br/>
            <input type="submit" value="Save as Log File">

        </div>
    </body>
</html>
