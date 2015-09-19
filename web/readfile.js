function readOFF(fileStr, vertices, faces) {
    function tokenize(str) {
        var toks = [];
        for(var s = 0; s < str.length; s++) {
            if(str[s] != ' ') {
                for(var e = s; e < str.length; e++) {
                    if(str[e] == ' ')
                        break;
                }
                toks[toks.length] = str.slice(s,e);
                s = e;
            }
        }
        return toks;
    }

    var lines = fileStr.split('\n');
    if(lines[0].substr(0,3) != "OFF")
        return;
    var j = 0;
    var fields;
    while(!fields || fields.length == 0) {
        while(lines[++j].charAt(0) == '#');
        fields = tokenize(lines[j]);
    }
    var nv = parseInt(fields[0]);
    var nf = parseInt(fields[1]);
    j++;
    for(var i = 0; i < nv; i++) {
        fields = tokenize(lines[2 + i]);
        vertices[i] = [fields[0], fields[1], fields[2]];
    }
    for(var i = 0; i < nf; i++) {
        fields = tokenize(lines[2 + nv + i]);
        faces[i] = [fields[1], fields[2], fields[3]];
    }
}


function readRawiv(fileArrayBuffer, filesize) {
	var fptr = 0;
	var fdata = new Uint8Array(fileArrayBuffer);

	function readInt() {
		var int = fdata[fptr] << 24
				| fdata[fptr+1] << 16
				| fdata[fptr+2] << 8
				| fdata[fptr+3];
		fptr += 4;
		return int;
	}

	var nverts, ncells, xdim, ydim, zdim, format;

	fptr = 6 * 4;
	nverts = readInt();
	ncells = readInt();

	size = 12 * 4 + 2 * 4 + 3 * 4;
	if(size + nverts == filesize)
		format = 1;
	else if(size + nverts * 2 == filesize)
		format = 2;
	else if(size + nverts * 4 == filesize)
		format = 4;

	if(format !== 1)
		throw "File format not supported";

	xdim = readInt();
	ydim = readInt();
	zdim = readInt();
	if(xdim * ydim * zdim !== nverts)
		throw "Error reading file";

	return {
		data: fdata.subarray(17 * 4),
		width: xdim,
		height: ydim,
		depth: zdim,
		format: format,
	};
}
