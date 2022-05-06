var app = (function () {
    _local = "http://localhost:8080";
    _external = "https://livecoding-gpv.herokuapp.com";
    var _user;
    var _password;
    var stompClient = null;
    var _idsala;
    var blockedLines = [];

    var getStompClient = function(){
        return stompClient;
    };

    var get_idSala = function() {
        return _idsala;
    };

    var getUser= function(){
        return _user;
    };

    var validateLogin = function() {
        _user = document.getElementById('userName').value;
        _password = document.getElementById('password').value;
        //Guardando usuario
        sessionStorage.setItem('user', _user);

        window.location.replace(_local + "/home.html");
        //window.location.replace(_external + "/home.html");
    };

    var goToHome =  function(){
        window.location.assign(_local +"/principal.html");
        //window.location.assign(_external +"/principal.html");
    };

    var updateLines = function() {
        console.log("UpdateLines -----")
        var txt_zone = document.getElementById('content');
    };

    var updateLine = function(evt){
        if (evt.target.nodeName === "div") {
            console.log(evt.target);
            console.log(evt.target.textContent);
        }
    };

    var findLineBlockedByUser = function(user) {
        let indexTemp;
        blockedLines.forEach(function(element, index) {
            if (element.user == user) {
                indexTemp = index;
            }
        });
        console.log("Dato en la posición " + indexTemp + "/////////////////////////////////////////////////////////")
        return indexTemp;
    }

    var isBlockedLine = function (numLine) {
        blockedBy = "";
        blockedLines.forEach(function(element) {
            if (element.numLine == numLine) {
                blockedBy = element.user;
            }
        });
        return blockedBy;
    }
    
    function blockedLine (numLine, user) {
        this.numLine = numLine;
        this.user = user
    }

    var connectAndSubscribe = function () {
        var socket = new SockJS('/stompendpoint');
        var _id;
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function (){
            stompClient.subscribe('/topic/message.'+_idsala, function (eventbody){
                let txtArea = document.getElementById("content");
                let json = JSON.parse(eventbody.body);
                let event = json.event;
                if (event == 'message' ){
                    console.log("Recibiendo el mensaje.......");
                    console.log(json.content);
                    $("#table-chat tbody").append("<tr><td>" +json.user + ": "+ json.content + "</td></tr>");
                    $(".chat").animate({ scrollTop: $('.chat')[0].scrollHeight}, 1000);
                } else if (event == 'comment') {
                    $("#table-comment tbody").append("<tr><td>" + json.user + ": (Linea " + json.numLineCode +") " + json.content + "</td></tr>")
                    $(".comments").animate({ scrollTop: $('.comments')[0].scrollHeight}, 1000);
                    // Se resalta lalinea seleccionada del comentario
                    console.log("TXT área ", txtArea.children[json.numLineCode-1]);
                    txtArea.children[json.numLineCode-1].setAttribute("style", "background-color:#fc303054;");
                }
            });

            stompClient.subscribe('/topic/file.'+_idsala, function (eventbody){
                let txtArea = document.getElementById("content");
                let json = JSON.parse(eventbody.body);
                let event = json.event;

                console.log("Mensaje Recibido:\n", json);
                if(event == "click" && (_user != json.user)){
                    let pos = findLineBlockedByUser(json.user);
                    if (pos != undefined) {
                        blockedLines.splice(pos, 1);
                    }
                    blockedLines.push(new blockedLine(json.numLine, json.user));
                    console.log("Num line: " + json.numLine);
                    console.log(txtArea.children[json.numLine]);
                    console.log("Línea seleccionada "+ txtArea.children[json.numLine].outerHTML);
                } else if (event == "keypress" && (_user != json.user)){
                    txtArea.children[json.numLine].outerHTML = json.html;
                    // console.log(txtArea.children[json.numLine].outerHTML);    
                }else if (event == "keyup" && (_user != json.user)){
                    // Tecla Enter
                    if (json.code == 13) {
                        console.log("Tecla Enter oprimida en la línea " + json.numLine);
                        let newdiv = document.createElement("div");
                        newdiv.innerHTML = "<br>";
                        txtArea.children[json.numLine-1].insertAdjacentElement("afterend", newdiv);
                        console.log(txtArea.children[json.numLine]);
                        console.log(newdiv);
                    }
                    // Flecha Up
                    if (json.code == 38) {
                        console.log("Flecha Up oprimida");
                        console.log("Usuario " + json.user + " saltó de la línea " + (json.numLine + 1) + " a la linea " + json.numLine);
                        console.log("blockedLines (Antes) -> " + blockedLines);
                        let pos = findLineBlockedByUser(json.user);
                        if (pos != undefined) {
                            blockedLines.splice(pos, 1);
                        }
                        blockedLines.push(new blockedLine(json.numLine, json.user));
                        console.log("blockedLines (Después) -> " + blockedLines);
                    }

                    // Flecha Down
                    if (json.code == 40) {
                        console.log("Flecha Down oprimida");
                        console.log("Usuario " + json.user + " saltó de la línea " + (json.numLine - 1) + " a la linea " + json.numLine);
                        console.log("blockedLines (Antes) -> " + blockedLines);
                        let pos = findLineBlockedByUser(json.user);
                        if (pos != undefined) {
                            blockedLines.splice(pos, 1);
                        }
                        blockedLines.push(new blockedLine(json.numLine, json.user));
                        console.log("blockedLines (Después) -> " + blockedLines);
                    }

                    // Tecla Del
                    if (json.code == 8) {
                        console.log("DEL: json.numLine: " + json.numLine);
                        console.log("DEL: OBJ: " + txtArea.children[json.numLine]);
                        console.log("DEL: outerHTML: " + txtArea.children[json.numLine].outerHTML);
                        console.log("json.html: " + json.html);
                        txtArea.children[json.numLine].outerHTML = json.html;
                    }
                }
            });
        });
    };

    var loadFile = function() {
        console.info("Loading file...")
        $.get("/livecoding/loadFile/"+_idsala, function(data) {
            let file = Object.values(data);
            console.log("file charged --------------------------------------------------------\n", file);
        })
        .catch(function(err) {
            nameFile = prompt("Enter the name of the file", "HelloWorld.java");
            $(".file-name").text(nameFile);
        });
    }
   
    return {

        getStompClient: getStompClient,

        get_idSala: get_idSala,

        init: function() {
            _idsala = prompt('Enter the room code: ');
            $.ajax({
                url: "http://localhost:8080/livecoding/saveRoom",
                type: 'POST',
                data: JSON.stringify(_idsala),
                contentType: "application/json"
            });
            _user = sessionStorage.getItem('user');
            updateLines();
            connectAndSubscribe();
            appEventsTxtArea.addEventsToTextArea();
            loadFile();
        },

        validateLogin:validateLogin,
        goToHome:goToHome,
        getUser:getUser,
        isBlockedLine:isBlockedLine,
    }

})();