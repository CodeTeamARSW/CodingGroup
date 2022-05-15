var appEventsTxtArea = (function() {

    var numLineSelected;
    var _idsala;
    var _stompClient;

    function addEventsToTextArea() {
        var txt_area = $('#content');

        clickEvent(txt_area[0]);
        KeypressEvent(txt_area[0]);
        KeyupEvent(txt_area[0]);
    };

    function updateAndSaveFile(){
        var txt_area = document.getElementById("content");
        let codice = txt_area.children;
        var temp = Object.values(codice);
        let codice_map = [$("kbd.file-name").text()];
        temp.forEach(value => codice_map.push(value.outerText.substring(0, value.outerText.length-1)));
        $.ajax({
                url: app.getURL()+"/livecoding/autoSave/"+_idsala,
                type: 'PUT',
                data: JSON.stringify(codice_map),
                contentType: "application/json"
            });
    }

    function clickEvent(txtArea){
        txtArea.addEventListener('click', (e) => {
            let divSelected = e.target;
            // Se determina la el número de la linea en la que se desea editar
            var i=0;
            while (divSelected != txtArea.children[i] && i<txtArea.children.length){
                i++;
            }
            numLineSelected = i;
            // Verifica que la linea en la que se quiere editar no esté editando otro usuario.
            let blockedBy = app.isBlockedLine(numLineSelected);
            if (blockedBy != "") {
                alert("La línea está siendo editada por el usuario '" + blockedBy + "'");
            } else {
                _stompClient = app.getStompClient();
                _idsala = app.get_idSala();
                var user = sessionStorage.getItem('user');
                _stompClient.send('/app/file.'+_idsala, {}, "{\"event\": \"click\", \"numLine\": \""+numLineSelected+"\", \"user\": \"" + user + "\"}");
            }
        });
    };

    function KeypressEvent(txtArea) {
        txtArea.addEventListener("keypress", (e) => {
            let codeKeyPressed = e.keyCode;

            if (codeKeyPressed != 13 ){
                setTimeout(function(){
                    var user = sessionStorage.getItem('user');
                    let body = "{\"event\": \"keypress\", \"html\": \""+txtArea.children[numLineSelected].outerHTML+"\", \"numLine\": \""+numLineSelected+"\", \"user\": \"" + user + "\"}";
                    _stompClient.send('/app/file.'+_idsala, {}, body);
                    updateAndSaveFile();
                },100);
            }

        });
    }
    
    function KeyupEvent(txtArea) {
        txtArea.addEventListener("keyup", (e) => {
            let codeKeyPressed = e.keyCode;
            let characterPressed = String.fromCharCode(codeKeyPressed);
            if (codeKeyPressed == 13){
                numLineSelected++;
            } else if (codeKeyPressed == 38) {
                if (numLineSelected != 0) {
                    numLineSelected--;
                }
            } else if (codeKeyPressed == 40) {
                if (numLineSelected != txtArea.children.length) {
                    numLineSelected++;
                }
            }

            var user = sessionStorage.getItem('user');
            var body;
            if (codeKeyPressed == 8) {
                body = "{\"event\": \"keyup\", \"code\": \""+codeKeyPressed+"\", \"html\": \""+txtArea.children[numLineSelected].outerHTML+"\", \"numLine\": \""+numLineSelected+"\", \"user\": \"" + user + "\"}";
                _stompClient.send('/app/file.'+_idsala, {}, body);
                updateAndSaveFile();
            } else if (codeKeyPressed == 13 || codeKeyPressed == 38 || codeKeyPressed == 40) {
                body = "{\"event\": \"keyup\", \"code\": \""+codeKeyPressed+"\", \"numLine\": \""+numLineSelected+"\", \"user\": \"" + user + "\"}";
                _stompClient.send('/app/file.'+_idsala, {}, body);
            }
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