var app = (function () {
    _local = "http://localhost:8080";
    _external = "https://livecoding-gpv.herokuapp.com";
    var _user;
    var _password;
    var stompClient = null;
    var _idsala;

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

    var connectAndSubscribe = function () {
        var socket = new SockJS('/stompendpoint');
        var _id;
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function (){
            stompClient.subscribe('/topic/file.'+_idsala, function (eventbody){
                let txtArea = document.getElementById("content");
                let json = JSON.parse(eventbody.body);
                let event = json.event;

                if(event == "click" && (_user != json.user )){
                    console.log("Num line" + json.numLine);
                    console.log(txtArea.children[json.numLine]);
                    console.log("Línea seleccionada "+ txtArea.children[json.numLine].outerHTML);
                } else if (event == "keypress" &&  (_user != json.user ) ){
                    txtArea.children[json.numLine].outerHTML = json.html;
                    console.log(txtArea.children[json.numLine].outerHTML);    
                }else if (event == "keyup"){

                }     
            });
        });
    };
   
    return {

        getStompClient: getStompClient,

        get_idSala: get_idSala,

        init: function() {
            _idsala = prompt('Digite el código de la sala: ');
            $.ajax({
                url: "http://localhost:8080/livecoding/saveRoomId",
                type: 'POST',
                data: JSON.stringify(_idsala),
                contentType: "application/json"
            });
            _user = sessionStorage.getItem('user');
            updateLines();
            connectAndSubscribe();
            appEventsTxtArea.addEventsToTextArea();
        },

        validateLogin:validateLogin,
        goToHome:goToHome,
        getUser:getUser
    }

})();