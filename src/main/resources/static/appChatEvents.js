var appChatEvents = (function () {
    var _stompClient, _idsala, _user, _content;


    var addMessage = function(evt){

            _stompClient = app.getStompClient();
            _idsala = app.get_idSala();
            _user = sessionStorage.getItem('user');
            _content = document.getElementById('entry-chat').value;
            _stompClient.send('/app/message.'+_idsala, {}, "{\"event\": \"message\", \"content\": \""+_content+"\", \"user\": \"" + _user + "\"}");
            $('#entry-chat').val("");
        };


     return {

            addMessage: addMessage
        }
    })();