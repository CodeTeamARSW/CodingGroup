var app = (function () {
    _local = "http://localhost:8080";
    _external = "https://livecoding-gpv.herokuapp.com";
    var stompClient = null;
    var _idsala;
    var _id;


    var validateLogin = function(){
        window.location.replace(_local + "/home.html");
        //window.location.replace(_external + "/home.html");
    };

    var goToHome =  function(){
        _idsala = prompt('Digite el código de la sala: ');
        localStorage.setItem(_idsala, _idsala);
        if(_idsala.length !== 0){
            console.log("Esta monda no sirve (antes del replace) => "+_idsala);
            window.location.assign(_local +"/principal.html");
            console.log("Esta monda no sirve (despues del replace) => "+_idsala);
            _id = localStorage.getItem(_idsala);
            console.log("Esta monda no sirve (despues del replace y con el local) => "+_id);
            //window.location.replace(_external +"/principal.html");
        }else{
            alert("Ponga un hp numero!!!");
        }
    };

    var connectAndSubscribe = function () {
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);

        //_id = localStorage.getItem(_idsala);
        console.log("Esta monda no sirve (en el connect con el local) => "+_id);
        console.log("Esta monda no sirve (en el connect) => "+_idsala);

        stompClient.connect({}, function (){
            stompClient.subscribe('/topic/file.'+_id, function (eventbody){
                alert(eventbody);
            });
        });
    };
   
    return {

        init: function() {
            connectAndSubscribe();
        },

        validateLogin:validateLogin,
        goToHome:goToHome
    }

       
})();