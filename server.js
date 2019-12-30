http = require('http');
fs = require('fs');

port = 3000;
host = '127.0.0.1';
eventId = 0;

server = http.createServer( function(req, res) {

    if (req.method == 'POST') {
        res.writeHead(200, {'Content-Type': 'text/html'});

        var body = '';
        req.on('data', function (data) {
            body += data;
        });
        req.on('end', function () {
            console.log("POST payload for Event id " + eventId + ": \n" + body);

            fs.writeFile("/tmp/test", "Hey there!", function(err) {
                if(err) {
                    return console.log(err);
                }
                console.log("The file was saved!");
            });

            eventId = eventId + 1;
        	res.end( '' );
        });
    }
    else
    {
        console.log("Not expecting other request types...");
        res.writeHead(200, {'Content-Type': 'text/html'});
		var html = '<html><body>HTTP Server at http://' + host + ':' + port + '</body></html>';
        res.end(html);
    }

});

server.listen(port, host);
console.log('Listening at http://' + host + ':' + port);