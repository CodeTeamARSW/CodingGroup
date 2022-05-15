var app = (function () {
    _local = "http://localhost:8080";
    _external = "https://livecoding-gpv.herokuapp.com";
    var _user;
    var _password;
    var stompClient = null;
    var _idsala;
    var blockedLines = [];

    var getURL = function(){
        return _local;
        //return _external;
    }

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
        if(_user.length == 0 || _password.length == 0){
            alert("All fields are required");
        }else{
            //Guardando usuario
            sessionStorage.setItem('user', _user);
            window.location.replace(_local + "/html/home.html");
            //window.location.replace(_external + "/html/home.html");
        }
    };

    var goToExistingRoom = function(){
        _idsala = prompt('Enter the room code: ');
        sessionStorage.setItem('idSala', _idsala);
        sessionStorage.setItem('newRoom','no');
        window.location.assign(_local +"/html/principal.html");
        $.ajax({
            url: _local+"/livecoding/registryLogs/"+_idsala,
            type: 'POST',
            data: JSON.stringify({user: sessionStorage.getItem('user'), activity: "Access to the room: " + _idsala, type: "INFO"}),
            contentType: "application/json"
        });
    };

    var goToNewRoom = function(){
        _idsala = Math.random().toString(16).substring(7, 15) + Math.random().toString(16).substring(7, 15);
        alert("The id of the room is: " + _idsala);
        sessionStorage.setItem('idSala', _idsala);
        sessionStorage.setItem('newRoom','yes');
        window.location.assign(_local +"/html/principal.html");
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
        console.info("Loading file...");
        var file;
        $.get("/livecoding/loadFile/"+_idsala, function(data) {
            console.log("DATA file  cargado..." + data);
            file = Object.values(data);
        }). then(function(data){
            console.log("File charged --------------------------------------------------------\n", file);
            console.log("Nombre       --------------------------------------------------------\n", file[0]);
            $(".file-name").text(file[0]);
            addContent(file);
        })
        .catch(function(err) {
            console.log("Something bad happens :c..", file);
        });
    };

    var loadChat = function(){
        var chats;
        $.get("/livecoding/loadChat/"+_idsala, function(data) {
            chats = Object.values(data);
        }). then(function(data){
            for (var i=0; i < chats.length; i++){
                $("#table-chat tbody").append("<tr><td>" +chats[i][0] + ": "+ chats[i][1] + "</td></tr>");
           }
        })
        .catch(function(err) {
            console.log("Something bad happens in chat :c..", chats);
        });
    }
    var addContent = function(file){
        //Clean content editable
        document.getElementById("content").innerHTML = "";
        for (var i = 1; i < file.length; i++){
            $("#content").append("<div>"+file[i]+"<br></div>");
        }
    };

    var saveFile = function() {
        $.ajax({
            url: _local+"/livecoding/saveFile/"+_idsala,
            type: 'GET',
            contentType: "application/json"
        });
        $.ajax({
            url: _local+"/livecoding/registryLogs/"+_idsala,
            type: 'POST',
            data: JSON.stringify({user: _user, activity: "The user " + _user + " has made changes to the file " + $("#file-name").text(), type: "INFO"}),
            contentType: "application/json"
        });
    };

    var validateAccess = function() {
        if (sessionStorage.getItem('user') == null){
            window.location.replace(_local + "/html/404.html");
            console.log("Redirigir a pagina de error 404");
        };
    };
   
    return {

        getURL:getURL,
        getStompClient: getStompClient,
        get_idSala: get_idSala,

        validateAccess:validateAccess,

        errorLoad: function(){
            $.get("http://api.ipify.org/?format=json", function(data){
                console.log(data);
                console.log("Attempt to unathorized access to application " + data.ip);
                return data;
            }).then(function(data){
                $.ajax({
                    url: _local+"/livecoding/registryLogs/eventWarning",
                    type: 'POST',
                    data: JSON.stringify({user: "warning_user", activity: "Attempt to unathorized access to application " + data.ip, type: "WARNING"}),
                    contentType: "application/json"
                });
            });
        },

        init: function() {
            validateAccess();
            setTimeout(function(){
                _user = sessionStorage.getItem('user');
                _idsala = sessionStorage.getItem('idSala');
                //Verifica si es nueva sala o no, Si es nueva guarda, sino Carga archivo
                //Nueva:
                if (sessionStorage.getItem('newRoom') == 'yes' ){
                    let txtArea = document.getElementById("content");
                    let codeLine = txtArea.children[0].outerText.substring(0, txtArea.children[0].outerText.length-1);
                    console.log(codeLine);
                    $.ajax({
                            url: _local+"/livecoding/saveRoom",
                            type: 'POST',
                            data: JSON.stringify({idSala: _idsala, admin: _user, intialLine: codeLine}),
                            contentType: "application/json"
                        }).then(function(data){
                            console.log("New file---------");
                            nameFile = prompt("Enter the name of the file", "HelloWorld.java");
                            $(".file-name").text(nameFile);
                            sessionStorage.setItem('nameFile', nameFile);
                        }).then(function(){
                            console.log('<--------Entando al PUT del archivo-------->');
                            console.log('<--------Valor de .file-name--------> ', $("kbd.file-name").text());
                            $.ajax({
                                    url: _local+"/livecoding/autoSave/"+_idsala,
                                    type: 'PUT',
                                    data: JSON.stringify([$("kbd.file-name").text(), codeLine]),
                                    contentType: "application/json"
                                });
                            console.log('<--------Despues del PUT del archivo-------->');
                        });
                }
                if (sessionStorage.getItem('newRoom') == 'no'){
                    loadFile();
                    loadChat();
                }
                connectAndSubscribe();
                appEventsTxtArea.addEventsToTextArea();
            },100)},

        validateLogin:validateLogin,
        goToExistingRoom:goToExistingRoom,
        goToNewRoom:goToNewRoom,
        getUser:getUser,
        isBlockedLine:isBlockedLine,
        saveFile:saveFile
    }

})();