var app = (function () {

    var validateLogin = function(){

        $.get("/livecoding/users", function(data) {
            console.log(data)});
        
        window.location.replace("http://localhost:8080/home.html");

    }
   
    return {
        validateLogin:validateLogin
    }

       
})();