/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//window.addEventListener("load", function(e) {
//    //document.getElementById("LightingLight0").onmouseover = showDialog(createLightPropertyDialog, 0);
//    //document.getElementById("LightingLight1").onmouseover=showDialog(createLightPropertyDialog,1);
//    //document.getElementById("smooth").onmouseover=showDialog(createSmoothDialog);
//});
//function showDialog(constructor, arg, update) {
//    return function(event) {
//        this.dialog = constructor(arg);
//
//        if (update)
//            update(event);
//    };
//}
//function createLightPropertyDialog(index) {
//    if (document.getElementById("parentID1"))
//    {
//        document.getElementById("mainbody").removeChild(document.getElementById("parentID1"));
//    }
//    if (document.getElementById("parentID0"))
//    {
//        document.getElementById("mainbody").removeChild(document.getElementById("parentID0"));
//    } 
//    function picker_onclick() {
//        this.color.showPicker();
//    }
//    
//    var elemid = "LightingLight" + index;
//    var content = document.createElement("div");
//    content.id = "C_" + elemid;
//    var opt_div = document.createElement("div");
//    var opt_lighton = document.createElement("input");
//    opt_lighton.type = "checkbox";
//    opt_lighton.checked = getLight(index, "on");
//    opt_lighton.id = elemid + "_opt_enable";
//    opt_lighton.onchange = function() {
//        setLight(index, "on", this.checked);
//        redisplay();
//    };
//
//    var label = document.createElement("label");
//    label.textContent = "Enable";
//    label.htmlFor = elemid + "_opt_enable";
//    opt_div.appendChild(opt_lighton);
//    opt_div.appendChild(label);
//    content.appendChild(opt_div);
//
//    var lightprop = ["ambient", "diffuse", "specular"];
//    var pickers = [];
//    for (var i = 0; i < lightprop.length; i++) {
//        var property = document.createElement("input");
//        property.id = elemid + "_" + lightprop[i];
//        property.hidden = true;
//        property.onchange = (function(idx, p, id) {
//            return function() {
//                setLight(idx, p, document.getElementById(id).color.rgb);
//                redisplay();
//            };
//        })(index, lightprop[i], property.id + "_picker");
//
//        var picker = document.createElement("input");
//        picker.id = property.id + "_picker";
//        picker.type = "button";
//        picker.className = "color palette";
//        picker.onclick = picker_onclick;
//        pickers.push(picker);
//
//        var label = document.createElement("label");
//        label.textContent = lightprop[i];
//        label.style.marginRight = "15px";
//
//        content.appendChild(property);
//        content.appendChild(picker);
//        content.appendChild(label);
//    }
//    var index0=index+1;
//    var d = createDialog({content: content, title: "Light" + index0, xr: .85, yr: .5 + .2 * index}, index);
//
//    for (var i = 0; i < pickers.length; i++) {
//        var prop = pickers[i].id.match(/_[^_]*/)[0].substring(1);
//
//        pickers[i].color = new jscolor.color(pickers[i], {
//            valueElement: pickers[i].id.match(/[^_]*_[^_]*/)[0],
//            rgb: getLight(index, prop).slice(),
//            pickerClosable: true
//        });
//    }
//    d.style.cssText = "border: 1px solid transparent; border-color: white;color: white;";
//    return d;
//}
//function removeThis()
//{
//    if (document.getElementById("parentID1"))
//    {
//        document.getElementById("mainbody").removeChild(document.getElementById("parentID1"));
//    }
//    if (document.getElementById("parentID0"))
//    {
//        document.getElementById("mainbody").removeChild(document.getElementById("parentID0"));
//    } 
//}
//function createDialog(prop,index) {
//
//    var dialog = document.createElement("div");
//    //ialog.className = "dialog";
//    if(index===0)
//        {
//            dialog.id = "parentID0";
//        }
//    if(index===1)
//        {
//            dialog.id = "parentID1";
//        }
//    var titlebar = document.createElement("div");
//    titlebar.className = "dialog_title";
//    titlebar.textContent = prop.title;
//    //titlebar.onmousedown = startmoveDialog;
//
//    dialog.appendChild(titlebar);
//    dialog.appendChild(prop.content);
//    if (prop.buttons) {
//        for (var i = prop.buttons.length - 1; i >= 0; i--)
//            dialog.appendChild(prop.buttons[i]);
//    }
//    document.body.appendChild(dialog);
//    return dialog;
//}
//
//function createSmoothDialog() {
//    var content = document.createElement("div");
//    //content.onmousedown = startmoveDialog;
//    content.setAttribute("id", "C_Smooth");
//    content.innerHTML = "\
//<div>\
//        <form action='HandleSmooth' method='post'>\
//        <table>\
//        <tr><td><label>Lamda</label></td><td><input type='textbox' name='thet1' /></td></tr>\
//        <tr><td><label>Theta</label></td><td><input type='textbox' name='thet2' /></td></tr>\
//        <tr><td><label>Steps</label></td><td><input type='textbox' name='steps' /></td></tr></table>\
//	<input type='submit' value='Smooth'></form>\
//        </div>\
//";
//      var d = createDialog({content: content, title: "Smooth Mesh:"});
////    button.dialogWindow = d;
//    d.style.cssText = "position: absolute;";
//    return d;
//}

function saveImage() {
    //
    var canvas = document.getElementById("display");

    var w = canvas.width;
    var h = canvas.height;
    document.location.href = getCanvasImageURL(canvas, w, h).replace("image/png", "image/octet-stream");
    //alert("this is test");
}

function getCanvasImageURL(canvas, width, height) {
    var sample = document.createElement("canvas");
    sample.width = width;
    sample.height = height;
    var sample_ctx = sample.getContext("2d");
    sample_ctx.drawImage(canvas, 0, 0, canvas.width, canvas.height, 0, 0, sample.width, sample.height);
    return sample.toDataURL();
}

function reverseNormal()
{
    window.location="index.jsp?index=2";
}

function showCompute()
{
    var temp=document.getElementById("isComputed").checked;
    
    if(temp)
    {
        changeColorMethod(1);
    }
    else
    {
        changeColorMethod(0);
    }
}

function computeSurface(m)
{
    setModel(m,{ compute_normals: true, normalize_scale: true });
    showCompute();
}

function saveOFFFile()
{
//    document.write("test");
//    document.writeln("testsss");
//    document.writeln("test22");
//    var myFile = window.open("about:blank","");;
//     myFile.document.write("test11223");
//     myFile.document.writeln();
//     myFile.document.write("test11223");
    window.location="HandleDownLoadFile";
}