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

    var addComment = function(evt){

            _stompClient = app.getStompClient();
            _idsala = app.get_idSala();
            _user = sessionStorage.getItem('user');
            _content = document.getElementById('entry-comment').value;
            _numlineCode = document.getElementById('nline-comment').value;
            if (_numlineCode.length == 0){
                alert('Por favor ingresar el número de la línea a la que desea realizar un comentario');
            } else {
                _stompClient.send('/app/message.'+_idsala, {}, "{\"event\": \"comment\", \"content\": \""+_content+"\", \"user\": \"" + _user + "\", \"numLineCode\": \"" + _numlineCode + "\"}");
                $('#entry-comment').val("");
                $('#nline-comment').val("");
            }
    };
     return {

            addMessage: addMessage,
            addComment: addComment
        }
    })();