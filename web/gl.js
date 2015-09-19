var gl;

function initWebgl(canvas) {
    gl = canvas.getContext("experimental-webgl", {preserveDrawingBuffer: true});
    if(!gl) {
        console.log("Failed to get webgl context");
        return false;
    }

    if(typeof WebGLDebugUtils !== "undefined")
    	gl = WebGLDebugUtils.makeDebugContext(gl);

    return true;
}

window.requestAnimationFrame = (function() {
	return window.requestAnimationFrame ||
           window.webkitRequestAnimationFrame ||
           window.mozRequestAnimationFrame ||
           window.oRequestAnimationFrame ||
           function(callback) { window.setTimeout(callback, .16); };
})();


function getShaderObj(src, type) {
	var shader = gl.createShader(type);
	gl.shaderSource(shader, src);
	gl.compileShader(shader);
	
	if(!gl.getShaderParameter(shader, gl.COMPILE_STATUS))
		console.log(gl.getShaderInfoLog(shader));
	return shader;
}

function getShader(vs_src, fs_src) {
	var vertshader = getShaderObj(vs_src, gl.VERTEX_SHADER);
	var fragshader = getShaderObj(fs_src, gl.FRAGMENT_SHADER);
	
	var shaderprog = gl.createProgram();
	gl.attachShader(shaderprog, vertshader);
	gl.attachShader(shaderprog, fragshader);
	gl.linkProgram(shaderprog);
	
	if(!gl.getProgramParameter(shaderprog, gl.LINK_STATUS))
		console.log(gl.getProgramInfoLog(shaderprog));
	return shaderprog;
}
