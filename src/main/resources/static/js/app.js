var app = (function () {
    _local = "http://localhost:8080";
    _external = "20.213.1.54:8080";
    _url = _local;
    var _user;
    var _password;
    var stompClient = null;
    var _idsala;
    var blockedLines = [];

    var getURL = function(){
        return _url;
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
            window.location.replace(_url + "/html/home.html");
        }
    };

    var goToExistingRoom = function(){
        _idsala = prompt('Enter the room code: ');
        sessionStorage.setItem('idSala', _idsala);
        sessionStorage.setItem('newRoom','no');
        window.location.assign(_url +"/html/principal.html");
        $.ajax({
            url: _url+"/livecoding/registryLogs/"+_idsala,
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
        window.location.assign(_url +"/html/principal.html");
    };

    var findLineBlockedByUser = function(user) {
        let indexTemp;
        blockedLines.forEach(function(element, index) {
            if (element.user == user) {
                indexTemp = index;
            }
        });
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
                    $("#table-chat tbody").append("<tr><td>" +json.user + ": "+ json.content + "</td></tr>");
                    $(".chat").animate({ scrollTop: $('.chat')[0].scrollHeight}, 1000);
                } else if (event == 'comment') {
                    $("#table-comment tbody").append("<tr><td>" + json.user + ": (Linea " + json.numLineCode +") " + json.content + "</td></tr>")
                    $(".comments").animate({ scrollTop: $('.comments')[0].scrollHeight}, 1000);
                    // Se resalta lalinea seleccionada del comentario
                    txtArea.children[json.numLineCode-1].setAttribute("style", "background-color:#fc303054;");
                }
            });

            stompClient.subscribe('/topic/file.'+_idsala, function (eventbody){
                let txtArea = document.getElementById("content");
                let json = JSON.parse(eventbody.body);
                let event = json.event;

                if(event == "click" && (_user != json.user)){
                    let pos = findLineBlockedByUser(json.user);
                    if (pos != undefined) {
                        blockedLines.splice(pos, 1);
                    }
                    blockedLines.push(new blockedLine(json.numLine, json.user));
                } else if (event == "keypress" && (_user != json.user)){
                    txtArea.children[json.numLine].outerHTML = json.html;
                }else if (event == "keyup" && (_user != json.user)){
                    // Tecla Enter
                    if (json.code == 13) {
                        let newdiv = document.createElement("div");
                        newdiv.innerHTML = "<br>";
                        txtArea.children[json.numLine-1].insertAdjacentElement("afterend", newdiv);
                    }
                    // Flecha Up
                    if (json.code == 38) {
                        let pos = findLineBlockedByUser(json.user);
                        if (pos != undefined) {
                            blockedLines.splice(pos, 1);
                        }
                        blockedLines.push(new blockedLine(json.numLine, json.user));
                    }

                    // Flecha Down
                    if (json.code == 40) {
                        let pos = findLineBlockedByUser(json.user);
                        if (pos != undefined) {
                            blockedLines.splice(pos, 1);
                        }
                        blockedLines.push(new blockedLine(json.numLine, json.user));
                    }

                    // Tecla Del
                    if (json.code == 8) {
                        txtArea.children[json.numLine].outerHTML = json.html;
                    }
                }
            });
        });
    };

    var loadFile = function() {
        var file;
        $.get("/livecoding/loadFile/"+_idsala, function(data) {
            file = Object.values(data);
        }). then(function(data){
            $(".file-name").text(file[0]);
            addContent(file);
        })
        .catch(function(err) {
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
            url: _url+"/livecoding/saveFile/"+_idsala,
            type: 'GET',
            contentType: "application/json"
        });
        $.ajax({
            url: _url+"/livecoding/registryLogs/"+_idsala,
            type: 'POST',
            data: JSON.stringify({user: _user, activity: "The user " + _user + " has made changes to the file " + $("#file-name").text(), type: "INFO"}),
            contentType: "application/json"
        });
    };

    var validateAccess = function() {
        if (sessionStorage.getItem('user') == null){
            window.location.replace(_url + "/html/404.html");
        }
    };
   
    return {

        getURL:getURL,
        getStompClient: getStompClient,
        get_idSala: get_idSala,

        validateAccess:validateAccess,

        errorLoad: function(){
            $.get("http://api.ipify.org/?format=json", function(data){
                return data;
            }).then(function(data){
                $.ajax({
                    url: _url+"/livecoding/registryLogs/eventWarning",
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
                    $.ajax({
                            url: _url+"/livecoding/saveRoom",
                            type: 'POST',
                            data: JSON.stringify({idSala: _idsala, admin: _user, intialLine: codeLine}),
                            contentType: "application/json"
                        }).then(function(data){
                            nameFile = prompt("Enter the name of the file", "HelloWorld.java");
                            $(".file-name").text(nameFile);
                            sessionStorage.setItem('nameFile', nameFile);
                        }).then(function(){
                            $.ajax({
                                    url: _url+"/livecoding/autoSave/"+_idsala,
                                    type: 'PUT',
                                    data: JSON.stringify([$("kbd.file-name").text(), codeLine]),
                                    contentType: "application/json"
                                });
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