var appEventsTxtArea = (function() {

    var numLineSelected;

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
            console.log("div selected: ", divSelected);
            console.log(divSelected.textContent);
            let classNm = divSelected.className;
            numLineSelected = classNm[4];
            console.log("    ClassName:", classNm, "\n    NumLine:", numLineSelected);

            //stompClient.send('/topic/file.'+_idsala, {}, e.target.selectionStart)
        });
    };

    function KeypressEvent(txtArea) {
        txtArea.addEventListener("keypress", (e) => {
            let codeKeyPressed = e.keyCode;
            let characterPressed = String.fromCharCode(codeKeyPressed);
            console.log("\nKeypress Event ----------------------");
            console.log("codeKeyPressed:", codeKeyPressed);
            console.log("CharacterPressed:", characterPressed);
            //console.log("app.get_idSala()", app.get_idSala());
            //stompClient.send('/topic/file.'+app.get_idSala(), {}, e.target.selectionStart)
        });
    }
    
    function KeyupEvent(txtArea) {
        txtArea.addEventListener("keyup", (e) => {
            let codeKeyPressed = e.keyCode;
            let characterPressed = String.fromCharCode(codeKeyPressed);
            console.log("Keyup Event -------------------------");
            console.log("codeKeyPressed:", codeKeyPressed);
            console.log("CharacterPressed:", characterPressed);
        });
    }
    
    return {
        addEventsToTextArea: addEventsToTextArea
    }
})();