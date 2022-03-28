var app = (function () {
    _local = "http://localhost:8080";
    _external = "https://livecoding-gpv.herokuapp.com";
    var stompClient = null;
    //var _idsala;
    //var _id;


    var validateLogin = function(){
        window.location.replace(_local + "/home.html");
        //window.location.replace(_external + "/home.html");
    };

    var goToHome =  function(){
        _idsala = prompt('Digite el código de la sala: ');
        alert("Id sala: " + _idsala);
        //sessionStorage.setItem(_idsala, _idsala);

        $.ajax({
            url: "http://localhost:8080/livecoding/saveRoomId",
            type: 'POST',
            data: JSON.stringify(_idsala),
            contentType: "application/json"
        });
        console.log("Guardando id: " + _idsala);

        if(_idsala.length !== 0){
            console.log("Prueba (antes del replace) => "+_idsala);
            window.location.assign(_local +"/principal.html");
            console.log("Prueba (despues del replace) => "+_idsala);
            _id = sessionStorage.getItem(_idsala);
            console.log("Prueba (despues del replace y con el local) => "+_id);
            //window.location.replace(_external +"/principal.html");
        }else{
            alert("Ponga un numero!!!");
        }
    };

    var connectAndSubscribe = function () {
        var socket = new SockJS('/stompendpoint');
        var _id;
        stompClient = Stomp.over(socket);

        $.get("/livecoding/getIdRoom", function(data) {
            _id = data;
            console.log("Obteniendo id: " + _id);
		});
        //_id = localStorage.getItem(_idsala);
        console.log("--Prueba (en el connect con el local) => "+_id);
        console.log("--Prueba (en el connect) => "+_idsala);

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