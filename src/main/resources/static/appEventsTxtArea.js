var appEventsTxtArea = (function() {

    var numLineSelected;
    var _idsala;
    var _stompClient;

    function addEventsToTextArea() {
        var txt_area = $('#content');
        console.log(txt_area[0]);

        clickEvent(txt_area[0]);
        KeypressEvent(txt_area[0]);
        KeyupEvent(txt_area[0]);
    };

    function clickEvent(txtArea){
        txtArea.addEventListener('click', (e) => {
            let divSelected = e.target;

            var i=0;
            while (divSelected != txtArea.children[i] && i<txtArea.children.length){
                i++;
            }
            numLineSelected = i;

            _stompClient = app.getStompClient();
            _idsala = app.get_idSala();
            //console.log("Como se esta enviando "+ "{\"event\": \"click\", \"numLine\": \""+numLineSelected+"\"}");
            _stompClient.send('/topic/file.'+_idsala, {}, "{\"event\": \"click\", \"numLine\": \""+numLineSelected+"\"}");
        });
    };

    function KeypressEvent(txtArea) {
        txtArea.addEventListener("keypress", (e) => {
            console.log("<------------------------ Keypress ------------------------")
            console.log("El div a ver si sirve => ");

            console.log(txtArea.children[numLineSelected].outerHTML);

            let codeKeyPressed = e.keyCode;
            let body = "{\"event\": \"keypress\", \"html\": \""+txtArea.children[numLineSelected].outerHTML+"\", \"numLine\": \""+numLineSelected+ "\"}";
            console.log("Como se esta enviando "+ body);
            if (codeKeyPressed != 13){
                _stompClient.send('/topic/file.'+_idsala, {},body);
            }

        });
    }
    
    function KeyupEvent(txtArea) {
        txtArea.addEventListener("keyup", (e) => {
            let codeKeyPressed = e.keyCode;
            let characterPressed = String.fromCharCode(codeKeyPressed);
            console.log("Keyup Event -------------------------");
            console.log("codeKeyPressed:", codeKeyPressed);
            console.log("CharacterPressed:", characterPressed);
            if (codeKeyPressed == 13 || codeKeyPressed == 40 ){
                numLineSelected++;
            }else if (codeKeyPressed == 38){
                numLineSelected--;
            }
            let body = "{\"event\": \"keyup\", \"code\": \""+codeKeyPressed+"\", \"numLine\": \""+numLineSelected+ "\"}";
            console.log("Como se esta enviando "+ body);
            _stompClient.send('/topic/file.'+_idsala, {},body);
        });
    };

    function getNumLine(){
        return numLineSelected;
    }

    function setNumLine(newLine){
       numLineSelected = newLine;     
    }
    
    return {
        addEventsToTextArea: addEventsToTextArea,
        setNumLine:setNumLine,
        getNumLine:getNumLine
    }
})();