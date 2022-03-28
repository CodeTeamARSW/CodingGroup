var app = (function () {
    _local = "http://localhost:8080";
    _external = "https://livecoding-gpv.herokuapp.com";

    var validateLogin = function(){
        window.location.replace(_local + "/home.html");
        //window.location.replace(_external + "/home.html");
    }

    var goToHome =  function(){
        window.location.replace(_local +"/principal.html");
        //window.location.replace(_external +"/principal.html");
    }
   
    return {
        validateLogin:validateLogin,
        goToHome:goToHome
    }

       
})();