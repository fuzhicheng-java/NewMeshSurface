<%-- 
    Document   : MeshShow
    Created on : Apr 21, 2015, 6:34:03 PM
    Author     : zhichengfu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Code.Feature"%>
<%@page import="Code.Paths"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <script src="js1.js" type="text/javascript"></script>
        <link href="mainCss.css" rel="stylesheet" type="text/css">
            <script id="shader-vert" type="text/plain">
                attribute vec3 position;
                attribute vec3 normal;
                attribute vec3 pesudoValue;

                uniform mat4 model;
                uniform mat4 view;
                uniform mat4 projection;

                uniform vec3 camera_position;

                varying vec3 fragvertex;
                varying vec3 vnormal;
                varying vec3 pesudoV;

                void main(void) {
                vec4 vertex = model * vec4(position, 1.0);
                gl_Position = projection * view * vertex;

                fragvertex = vertex.xyz;
                vnormal = normal;
                pesudoV=pesudoValue;
                }
            </script>
            <script id="shader-frag" type="text/plain-text">
                precision highp float;

                struct Light {
                bool on;
                vec4 position;
                vec3 ambient;
                vec3 diffuse;
                vec3 specular;
                };

                uniform mat4 model;
                uniform mat4 view;
                uniform mat4 projection;

                uniform vec3 camera_position;

                uniform int mode;

                uniform int isPesudoColor;

                uniform vec3 pesudoColor;

                uniform Light light[2];

                varying vec3 fragvertex;
                varying vec3 vnormal;
                varying vec3 pesudoV;

                void main(void) {
                vec3 color = vec3(0);

                if(mode != 0) {
                gl_FragColor = vec4(0.0,0.0,0.0,1.0);
                return;
                }

                if(isPesudoColor==0)
                {

                vec3 fragnormal = normalize((model * vec4(vnormal, 0.0)).xyz);

                vec3 lightdir, refldir;
                float diffuse, spec;

                vec3 eyedir = normalize(camera_position - fragvertex);

                float td = dot(eyedir, fragnormal);
                if(td < 0.0) {
                gl_FragColor = vec4(vec3(-td), 1.0);
                return;
                }

                for(int i = 0; i < 2; i++) {
                if(light[i].on) {
                if(light[i].position.w == 0.0)
                lightdir = normalize(light[i].position.xyz);
                else
                lightdir = normalize(light[i].position.xyz - fragvertex);

                refldir = reflect(-lightdir, fragnormal);

                diffuse = max(dot(lightdir, fragnormal), 0.0);
                spec = pow(max(dot(refldir, eyedir), 0.0), 28.0);
                color += light[i].ambient + light[i].diffuse * diffuse + light[i].specular * spec;
                }
                }
                }
                else if(isPesudoColor==1)
                {    
                color=pesudoV;
                }

                gl_FragColor = vec4(color, 1.0);
                }
            </script>
            <script src="ShowingParts.js" type="text/javascript" ></script>
            <script type="text/javascript" src="gl-matrix-min.js"></script>
            <script type="text/javascript" src="main.js" ></script>
            <script type="text/javascript" src="jscolor/jscolor.js"></script>
            <script type="text/javascript" src="webgl-debug.js"></script>
            <script type="text/javascript" src="readfile.js"></script>
            <script type="text/javascript" src="geometryExport.js"></script>
            <script type="text/javascript" src="gl.js"></script>
            <script type="text/javascript" src="ShowingParts.js"></script>

            <%!
                String namePath = null;
                String p1 = null;
                String p2 = null;
                String p3 = null;
                Paths temp = null;
                Feature feature = null;
                String[][] vertices = null;
                String[][] faces = null;
                int vLength = 0;
                int fLength = 0;
                int index = 0;
                int optionCompute = 0;
                String[][] colorValues = null;
                String tempS = "";
                String offPath = "test.off";
            %>

            <%
                int index = 0;
                //index = Integer.parseInt(request.getParameter("index"));
                if (request.getParameter("index") != null) {
                    index = Integer.parseInt(request.getParameter("index"));
                    temp = (Paths) session.getAttribute("Paths");
                    index = Integer.parseInt(request.getParameter("index"));
                    //optionCompute=Integer.parseInt(request.getParameter("optionCompute"));
                    if (temp != null) {
                        p1 = temp.p1;
                        p2 = temp.p2;
                        p3 = temp.p3;
                    }
                    feature = (Feature) session.getAttribute("files");
                    colorValues = (String[][]) session.getAttribute("computedResult");
                    vertices = feature.vertices;
                    faces = feature.faces;
                    vLength = vertices.length;
                    fLength = faces.length;
                    offPath = feature.offName;
                    if (index == 2) {
                        for (int i = 0; i < vLength; i++) {
                            tempS = feature.vertices[i][2];
                            feature.vertices[i][2] = feature.vertices[i][0];
                            feature.vertices[i][0] = tempS;
                        }
                    }
                }

            %>

            <script type="text/javascript">

                function processOFF()
                {
                    var vs = [];
                        var fs = [];
                        var color = [];
                <% for (int i = 0; i < vLength; i++) {
                %>

                        vs[<%= i%>] = [<%=vertices[i][0]%>,<%=vertices[i][1]%>,<%=vertices[i][2]%>];
                        color[<%= i%>] = [<%=colorValues[i][0]%>,<%=colorValues[i][1]%>,<%=colorValues[i][2]%>];

                <%
                    }
                %>
                <% for (int j = 0; j < fLength; j++) {
                %>
                        fs[<%= j%>] = [<%=faces[j][0]%>,<%=faces[j][1]%>,<%=faces[j][2]%>];
                <%
                    }
                %>
                        var m = {};
                        m.vertices = vs;
                        m.indices = fs;
                        m.colors = color;
                        startWebGL(1, m);
                    
                }
            </script>
    </head>
    <body onload="processOFF();">
        <div id="MeshShow">
        <canvas id="display">
                Your browser doesn't appear to support the HTML5 canvas element.
        </canvas>
        </div>
        <div id="meshParameter">
            <div style="border:solid;height:100%;">
                <div style="margin-left: 20px;margin-top:20px;">
                    <input type="checkbox" id="isComputed" onchange="showCompute();">&nbsp;&nbsp;<a>Use Pseudo Color</a><br/>
                    <input type="checkbox" name="isShowMesh" onchange='myscene.draw_edges = this.checked;
                                    redisplay()'>&nbsp;&nbsp;<a>Show Mesh</a><br/><br/>
                    <form action="" method="">
                    <select>
                        <option value="Biomolecule">Select Biomolecule</option>
                    </select>
                    <br/><br/>
                    <select>
                        <option value="Plot Method">Plot Method</option>
                    </select>
                    <br/><br/>
                    <a>Background Color:</a><select>
                        <option value="white">White</option>
                        <option value="black">Black</option>
                    </select>
                    <br/><br/>
                    <a>Color Scale Range</a><br/>
                    <input type="text" name="scaleFrom" style="width:40px;">&nbsp;&nbsp;&nbsp;
                    <input type="text" name="scaleTo" style="width:40px;">&nbsp;&nbsp;
                    <input type="checkbox" name="isAutoScale">&nbsp;&nbsp;<a>Auto Scale</a><br/><br/>
                    <input type="checkbox" name="isShowBar">&nbsp;&nbsp;<a>Show Color Bar</a>
                        <br/><a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Number of Items: </a><input type="text"style="width:60px;" name="numberItem"><br/><br/>
                    
                        <div style="text-align: center">
                            <br/>
                    <table class="innerTable" style="margin-left: 5px;">
                        <tr><td>Line Width</td><td><input type="test" name="linewidth"></td></tr>
                        <tr><td>Line Color</td><td><input type="test" name="linecolor"></td></tr>
                        <tr><td>Point Width</td><td><input type="test" name="pointwidth"></td></tr>
                        <tr><td>Point Color</td><td><input type="test" name="pointcolor"></td></tr>
                    </table>
                            <br/><br/><br/> 
                            <input type="submit" value="Apply" style="width:200px;">
                        </div>
                    </form>
                </div>
            </div> 
        </div>
    </body>
</html>

