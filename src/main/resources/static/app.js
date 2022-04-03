var app = (function () {
    _local = "http://localhost:8080";
    _external = "https://livecoding-gpv.herokuapp.com";
    var stompClient = null;
    var _idsala;

    var getStompClient = function(){
        return stompClient;
    };

    var get_idSala = function() {
        return _idsala;
    };

    var validateLogin = function() {
        window.location.replace(_local + "/home.html");
        //window.location.replace(_external + "/home.html");
    };

    var goToHome =  function(){
        window.location.assign(_local +"/principal.html");
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
                console.log('La letra es (charachachanchan) => ' + eventbody.body);
            });
        });
    };

    /*function caretPosition(input) {
        console.log("CaretPosition-----------------")
        var start = input[0].selectionStart,
            end = input[0].selectionEnd,
            diff = end - start;
    
        if (start >= 0 && start == end) {
            // do cursor position actions, example:
            console.log('Cursor Position: ' + start);
        } else if (start >= 0) {
            // do ranged select actions, example:
            console.log('Cursor Position: ' + start + ' to ' + end + ' (' + diff + ' selected chars)');
        }
    };*/
   
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

            updateLines();
            connectAndSubscribe();
            appEventsTxtArea.addEventsToTextArea();
        },

        validateLogin:validateLogin,
        goToHome:goToHome
    }

})();