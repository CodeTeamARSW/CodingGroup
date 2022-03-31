var app = (function () {
    _local = "http://localhost:8080";
    _external = "https://livecoding-gpv.herokuapp.com";
    var stompClient = null;
    var _idsala;


    var validateLogin = function(){
        window.location.replace(_local + "/home.html");
        //window.location.replace(_external + "/home.html");
    };

    var goToHome =  function(){
        window.location.assign(_local +"/principal.html");
    };

    var updateLines = function() {
        console.log("UpdateLines -----")
        var txt_zone = document.getElementById('content');
        txt_zone.addEventListener('keypress', updateLine);
    }
    var updateLine = function(evt){
        if (evt.target.nodeName === "div") {
            console.log(evt.target);
            console.log(evt.target.textContent);
        }
    }

    var connectAndSubscribe = function () {
        var socket = new SockJS('/stompendpoint');
        var _id;
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function (){
            stompClient.subscribe('/topic/file.'+_idsala, function (eventbody){

                //addLetter();
                console.log('La letra es (charachachanchan) => ' + eventbody.body);

            });
        });
    };
   
    return {

        init: function() {

            _idsala = prompt('Digite el código de la sala: ');
            $.ajax({
                url: "http://localhost:8080/livecoding/saveRoomId",
                type: 'POST',
                data: JSON.stringify(_idsala),
                contentType: "application/json"
            });

            updateLines()

            connectAndSubscribe();

            var txt_area = document.getElementById('content');

            /*txt_area.addEventListener( 'keypress', (e) => {

                stompClient.send('/topic/file.'+_idsala, {}, String.fromCharCode(e.which));
            })*/
        },

        validateLogin:validateLogin,
        goToHome:goToHome
    }

       
})();