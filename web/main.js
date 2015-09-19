/* global gl */

var mousestate = 0, mouseX = 0, mouseY = 0;

var matId = mat4.identity(mat4.create());
var model_mat;
var view_mat;
var proj_mat;

var value_Color = 0;

var view_clip_neardist = .1;

var myshader;
var myscene = {
    camera: {
        position: vec3.create(),
        direction: vec3.create([0, 0, -1]),
        upDirection: vec3.create([0, 1, 0])
    },
    models: [],
    lights: [],
    draw_edges: false,
};
var mycamera = myscene.camera;
var mymodel = myscene.models[0];

function startWebGL(value, m)
{
    var temp;
        var canvas = document.getElementById("display");
        canvas.width = canvas.clientWidth;
        canvas.height = canvas.clientHeight;

        if (!initWebgl(canvas))
            return;

        window.addEventListener("resize", function () {
            canvas.width = canvas.clientWidth;
            canvas.height = canvas.clientHeight;
            reshape(canvas.width, canvas.height);
            console.log("display size " + canvas.width + " x " + canvas.height);
            requestAnimationFrame(display);
        });

        window.addEventListener("contextmenu", function (e) {
            e.preventDefault()
        });

        document.getElementById("display").addEventListener("mousedown", function (e) {
            mouseX = e.pageX;
            mouseY = e.pageY;

            if (mousestate == 0)
                mousestate = e.which;
        });
        window.addEventListener("mouseup", function () {
            mousestate = 0;
            requestAnimationFrame(display);
        });
        window.addEventListener("mousemove", function (e) {
            if (mousestate) {
                var x = e.pageX;
                var y = e.pageY;
                var dx = x - mouseX;
                var dy = mouseY - y;

                switch (mousestate) {
                    case 1:
                        var axis = vec3.create();
                        var mv = vec3.create([dx, dy, 0]);
                        vec3.cross(mv, mycamera.direction, axis);

                        var angle = vec3.length(mv) * .005 * Math.PI;
                        vec3.normalize(axis);
                        vec3.scale(axis, Math.sin(angle * .5));

                        var quat = quat4.create([axis[0], axis[1], axis[2], Math.cos(angle * .5)]);
                        quat4.multiply(quat, mymodel.rotation, mymodel.rotation);
                        break;
                    case 2:
                        var d = vec3.length([dx * .01, dy * -.01, 0.0]);
                        if (dx < 0)
                            d *= -1;
                        if ((mymodel.scale[0] + d) > 0)
                            vec3.add(mymodel.scale, [d, d, d]);
                        break;
                    case 3:
                        dx *= 2 / document.body.clientWidth;
                        dy *= 2 / document.body.clientHeight;
                        vec3.add(mymodel.position, [dx, dy, 0.0]);
                        break;
                }
                mouseX = x;
                mouseY = y;
                requestAnimationFrame(display);
            }
        });
        
         window.addEventListener("keypress", function(e) {
            mousestate = e.which;
            if (mousestate === 45)
            {
                var dx = -10;
                var dy = 0;
                var d = vec3.length([dx * .01, dy * -.01, 0.0]);
                //alert(d);
                if (dx < 0)
                    d *= -1;
                vec3.add(mymodel.scale, [d, d, d]);
            }
            else if (mousestate === 61)
            {
                var dx = 10;
                var dy = 0;
                var d = vec3.length([dx * .01, dy * -.01, 0.0]);
                vec3.add(mymodel.scale, [d, d, d]);
            }
            else
            {
                //alert(mousestate);
            }
            requestAnimationFrame(display);
        });

        if (value === 0)
        {
            temp = geometryExport.newSphere();
            init(canvas, temp);
        }
        else
        {
            init(canvas, m);
        }

        requestAnimationFrame(display);
    
}

function rescaling(geometry) {
    var vertices = geometry.vertices
    var min = Number.MAX_VALUE;
    var max = Number.MIN_VALUE;

    for (var i = 0; i < vertices.length; i++) {
        for (var j = 0; j < 3; j++) {
            min = Math.min(min, vertices[i][j]);
            max = Math.max(max, vertices[i][j]);
        }
    }

    var n = 1.0 / (max - min);

    for (var i = 0; i < vertices.length; i++)
        vec3.scale(vertices[i], n);
}

function centerObject(geometry) {
    var i, j;
    var vertices = geometry.vertices;
    var min = [Number.MAX_VALUE, Number.MAX_VALUE, Number.MAX_VALUE];
    var max = [Number.MIN_VALUE, Number.MIN_VALUE, Number.MIN_VALUE];

    for (i = 0; i < vertices.length; i++) {
        for (j = 0; j < 3; j++) {
            min[j] = Math.min(min[j], vertices[i][j]);
            max[j] = Math.max(max[j], vertices[i][j]);
        }
    }

    var offset = vec3.add(max, min);
    vec3.scale(offset, .5);

    for (i = 0; i < vertices.length; i++)
        vec3.subtract(vertices[i], offset);
}

function calcNormals2(geometry) {
    var i, j, k, faces, nfaces, fnormals, vnormals;

    fnormals = [];
    nfaces = geometry.indices.length;
    for (i = 0; i < nfaces; i++) {
        var idx, a, b, c, n;
        idx = geometry.indices[i];
        a = vec3.create(geometry.vertices[idx[0]]);
        b = vec3.create(geometry.vertices[idx[1]]);
        c = geometry.vertices[idx[2]];
        n = vec3.create();
        vec3.subtract(a, c);
        vec3.subtract(b, c);
        vec3.cross(b, a, n);
        fnormals.push(n);
    }

    faces = geometry.indeces;
    vfaces = []
    for (i = 0; i < nfaces; i++) {
        for (j = 0; j < 3; j++) {
            v = faces[j];
            if (vfaces[v] == null)
                vfaces[v] = [i];
            else
                vfaces[v].push(i);
        }
    }

    vnormals = []
    vnormals_count = []
    for (i = 0; i < nverts; i++) {
        fverts = vfaces[i];
        if (fverts) {
            if (fverts[0] == i) {
                a = fverts[1];
                b = fverts[2];
            }
            else if (fverts[1] == i) {
                a = fverts[2];
                b = fverts[0];
            }
            else if (fverts[2] == i) {
                a = fverts[0];
                b = fverts[1];
            }

        }
    }


    for (i = 0; i < vnormals.length; i++)
        vec3.normalize(vnormals[i]);

    return vnormals;
}

function calcNormals(geometry) {
    function avg_normal(avg, counter, normal) {
        counter += 1;
        vec3.scale(avg, (counter - 1) / counter);
        vec3.scale(normal, 1.0 / counter);
        vec3.add(avg, normal);
    }

    var vn = [];
    var vncount = [];
    for (var i = 0; i < geometry.indices.length; i++) {
        var id = vec3.create(geometry.indices[i]);
        var a = vec3.create(geometry.vertices[id[0]]);
        var b = vec3.create(geometry.vertices[id[1]]);
        var c = vec3.create(geometry.vertices[id[2]]);
        var n = vec3.create();
        vec3.subtract(b, a);
        vec3.subtract(c, a);
        vec3.cross(b, c, n);

        var vn_flip = {}
        var flip = [];
        for (var j = 0; j < 3; j++) {
            if (vn[id[j]])
                if (vec3.dot(vn[id[j]], n) < 0)
                    flip.push(j);
        }
        if (flip.length == 3)
            vec3.negate(n);
        else if (flip.length == 1)
            vn_flip[id[flip[0]]];
        else if (flip.length == 2) {
            vec3.negate(n);
            var j = 3 - flip[0] - flip[1];
            vn_flip[id[j]];
        }

        var vn_flipped = {}
        for (flip in vn_flip) {
            delete vn_flip[flip];
            if ((vn[flip] == null) || (flip in vn_flipped))
                continue;
            vn_flipped[flip] = true;
            vec3.negate(vn[flip]);
            for (var k = 0; k < i; k++) {
                for (var j = 0; j < 3; j++) {
                    if (geometry.indices[k][j] == flip) {
                        var g = geometry.indices[k][(j + 1) % 3];
                        var h = geometry.indices[k][(j + 2) % 3];
                        vn_flip[g] = true;
                        vn_flip[h] = true;
                        break;
                    }
                }
            }
        }

        for (var j = 0; j < 3; j++) {
            if (vncount[id[j]])
                avg_normal(vn[id[j]], vncount[id[j]], n);
            else {
                vn[id[j]] = [n[0], n[1], n[2]];
                vncount[id[j]] = 1;
            }
        }
    }
    for (var i = 0; i < vn.length; i++) {
        if (vn[i] == null)
            vn[i] = vec3.create(geometry.vertices[i]);
        vec3.normalize(vn[i]);
    }
    geometry.normals = vn;
}

function generateEdges(geometry) {
    var triangles = geometry.indices;
    var edgeIndices = [];
    for (var i = 0; i < triangles.length; i++) {
        edgeIndices.push([triangles[i][0], triangles[i][1]]);
        edgeIndices.push([triangles[i][1], triangles[i][2]]);
        edgeIndices.push([triangles[i][2], triangles[i][0]]);
    }
    geometry.edgeIndices = edgeIndices;
}

function initBuffers(obj, vertices, normals, faceIndices, edgeIndices, colors) {
    function set(array, va) {
        var i, j, k, data, len, slen;
        len = array.length;
        slen = array[0].length;

        data = new Float32Array(len * slen);
        for (k = 0, i = 0; i < len; i++) {
            for (j = 0; j < slen; j++, k++)
            {
                if (va === 1)
                {
                    data[k] = Math.abs(array[i][j]);
                }
                else
                {
                    data[k] = array[i][j];
                }
            }
        }
        return data;
    }

    var vbo = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, vbo);
    var data = set(vertices, 0);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(data), gl.STATIC_DRAW);
    gl.vertexAttribPointer(myshader.layout.position, 3, gl.FLOAT, false, 0, 0);
    obj.vbo = vbo;

    var vnbo = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, vnbo);
    data = set(normals, 0);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(data), gl.STATIC_DRAW);
    gl.vertexAttribPointer(myshader.layout.normal, 3, gl.FLOAT, false, 0, 0);
    obj.vnbo = vnbo;

    var cobo = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, cobo);
    data = set(colors, 1);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(data), gl.STATIC_DRAW);
    gl.vertexAttribPointer(myshader.layout.pesudoValue, 3, gl.FLOAT, false, 0, 0);
    obj.cobo = cobo;

    var ibo = gl.createBuffer();
    gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, ibo);
    data = set(faceIndices);
    gl.bufferData(gl.ELEMENT_ARRAY_BUFFER, new Uint16Array(data), gl.STATIC_DRAW);
    obj.ibo = ibo;

    obj.icount = faceIndices.length * 3;

    var eibo = gl.createBuffer();
    gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, eibo);
    data = set(edgeIndices, 0);
    gl.bufferData(gl.ELEMENT_ARRAY_BUFFER, new Uint16Array(data), gl.STATIC_DRAW);
    obj.eibo = eibo;

    obj.eicount = edgeIndices.length * 2;
}


function setModel(geometry, prop) {
    var pos;
    if (prop)
        pos = prop.position;

    myscene.models[0] = {};
    mymodel = myscene.models[0];
    mymodel.position = vec3.create(pos);
    mymodel.rotation = quat4.create([0, 0, 0, 1]);
    mymodel.scale = vec3.create([1, 1, 1]);

    if (prop) {
        if (prop.compute_normals)
            calcNormals(geometry);
        if (prop.center_object)
            centerObject(geometry);
        if (prop.normalize_scale)
            rescaling(geometry);
    }

    generateEdges(geometry);
    initBuffers(mymodel, geometry.vertices, geometry.normals, geometry.indices, geometry.edgeIndices,
            geometry.colors);
}

function initShader() {
    var vs_src = document.getElementById("shader-vert").textContent;
    var fs_src = document.getElementById("shader-frag").textContent;
    var sp = getShader(vs_src, fs_src);

    sp.layout = {};

    var attribs = ["position", "normal", "pesudoValue"];
    for (var i = 0; i < attribs.length; i++)
        sp.layout[attribs[i]] = gl.getAttribLocation(sp, attribs[i]);

    var uniforms = ["model", "view", "projection", "camera_position",
        "isPesudoColor", "pesudoColor",
        "mode", "light[0].on", "light[1].on",
        "light[0].position", "light[0].ambient", "light[0].diffuse", "light[0].specular",
        "light[1].position", "light[1].ambient", "light[1].diffuse", "light[1].specular"];
    for (var i = 0; i < uniforms.length; i++)
        sp.layout[uniforms[i]] = gl.getUniformLocation(sp, uniforms[i]);

    gl.useProgram(sp);
    gl.enableVertexAttribArray(sp.layout.normal);
    gl.enableVertexAttribArray(sp.layout.position);
    gl.enableVertexAttribArray(sp.layout.pesudoValue);
    myshader = sp;
}

function setClipDistance(zvalue) {
    view_clip_neardist = zvalue;
    var canvas = document.getElementById("display");
    reshape(canvas.width, canvas.height);
}

function getLight(index, param) {
    if (param == null)
        return myscene.lights[index];
    else
        return myscene.lights[index][param];
}

function setLight(index, param, value) {
    var lightsrcs = myscene.lights;

    if (param == "on") {
        lightsrcs[index].on = value;
        gl.uniform1i(myshader.layout["light[" + index + "].on"], lightsrcs[index].on);
    }
    else if (param == "position") {
        vec4.set(value, lightsrcs[index].position);
        gl.uniform4fv(myshader.layout["light[" + index + "].position"], value);
    }
    else {
        vec3.set(value, lightsrcs[index][param]);
        gl.uniform3fv(myshader.layout["light[" + index + "]." + param], value);
    }
}

function reshape(w, h) {
    var r = w / h;
    proj_mat = mat4.perspective(45 * h / w, w / h, view_clip_neardist, 10, proj_mat);
    gl.uniformMatrix4fv(myshader.layout.projection, false, proj_mat);
    gl.viewport(0, 0, w, h);
}

function init(canvas, m) {
    initShader();

    gl.clearColor(0.0, 0.0, 0.0, 1.0);
    gl.enable(gl.DEPTH_TEST);
    gl.enable(gl.POLYGON_OFFSET_FILL);

    myscene.camera.position = vec3.create([0.0, 0.0, 5.0]);

    model_mat = mat4.create();
    view_mat = mat4.create();
    proj_mat = mat4.create();

    reshape(canvas.width, canvas.height);

    myscene.lights = [{
            on: true,
            position: [.5, 0, 1, 1],
            ambient: [.1, 0, 0],
            diffuse: [1, 0, 0],
            specular: [1, 0, 0]
        }, {
            on: true,
            position: [-5, 5, 5, 0],
            ambient: [0, 0, .1],
            diffuse: [0, 0, 1],
            specular: [0, 0, 1]
        }];

    var lightsrcs = myscene.lights;
    //gl.uniform1i(myshader.layout["isPesudoColor"],0);
    //
    //gl.uniform3fv(myshader.layout["pesudoColor"],[0.4,0.8,0.5]);
    for (var i = 0; i < lightsrcs.length; i++) {
        gl.uniform1i(myshader.layout["light[" + i + "].on"], lightsrcs[i].on);
        gl.uniform4fv(myshader.layout["light[" + i + "].position"], lightsrcs[i].position);
        gl.uniform3fv(myshader.layout["light[" + i + "].ambient"], lightsrcs[i].ambient);
        gl.uniform3fv(myshader.layout["light[" + i + "].diffuse"], lightsrcs[i].diffuse);
        gl.uniform3fv(myshader.layout["light[" + i + "].specular"], lightsrcs[i].specular);
    }


    setModel(m, {compute_normals: true, center_object:true, normalize_scale: true});
}


function display() {
    var tmp_vec = vec3.create();
    var lightsrcs = myscene.lights;

    gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT);

    vec3.add(mycamera.position, mycamera.direction, tmp_vec);
    mat4.lookAt(mycamera.position, tmp_vec, mycamera.upDirection, view_mat);
    gl.uniformMatrix4fv(myshader.layout.view, false, view_mat);
    gl.uniform3fv(myshader.layout.camera_position, mycamera.position);

    mat4.fromRotationTranslation(mymodel.rotation, mymodel.position, model_mat);
    mat4.scale(model_mat, mymodel.scale);

    gl.uniformMatrix4fv(myshader.layout.model, false, model_mat);
    gl.uniform1i(myshader.layout.mode, 0);

    gl.uniform1i(myshader.layout.isPesudoColor, value_Color);

    gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, mymodel.ibo);
    gl.drawElements(gl.TRIANGLES, mymodel.icount, gl.UNSIGNED_SHORT, 0);

    if (myscene.draw_edges) {
        gl.polygonOffset(1, -1);
        gl.uniform1i(myshader.layout.mode, 1);

        gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, mymodel.eibo);
        gl.drawElements(gl.LINES, mymodel.eicount, gl.UNSIGNED_SHORT, 0);
    }
    else
        gl.polygonOffset(0, 0);
}
;


function mainLoop() {
    display();
    requestAnimationFrame(mainLoop);
}

function redisplay() {
    requestAnimationFrame(display);
}

function changeColorMethod(value)
{
    value_Color = value;
    redisplay();
}


