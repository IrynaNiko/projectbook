$(function() {

    $( "#dialog-form" ).dialog({
        autoOpen: false,
        width: 'auto',
        modal: true,
        resizable: false,
        fluid: true,
        dialogClass: 'dialog',
        position: 'center',

        close: function() {
            $( ".inputDLP").val('');
            $( "label.error").hide();
            $( "#serverError").hide();
        }
    });

    $('.closeButton').click(function () {
        $("#dialog-form").dialog('close');
    });

    $( "#sighInButton" ).click(function() {
        $( "#dialog-form" ).dialog( "open" );
    });

    $( "#loginButton" ).click(function(e) {
        e.preventDefault();
        e.stopPropagation();

        if($("#loginForm").valid()){
            var userToSend = getUserObj();
            if (sendLoginData(userToSend)) {
                $( "#dialog-form" ).dialog( "close" );
            }
        }
    });

    $("#loginForm").validate({
        rules: {
            userLogin: {
                required: true,
                email: true
            },
            userPassword: {
                required: true
            }
        },
        messages: {
            userLogin: {
                required: "User login is required.",
                email: "Email address must be in the format of name@domain.com"
            },
            userPassword: {
                required: "User password is required."
            }
        }
    });

    function getUserObj() {
        var user = {};
        user.email = $('#uLogin').val();
        user.password = $('#uPassword').val();
        return user;
    };

    function sendLoginData(user) {

        var success = true;
        var userInJson = JSON.stringify(user);
        $.ajax({
            url: '/pb/ajax/user/login',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            async: false,
            type: 'POST',
            data: userInJson,
            success:function (data) {
                window.location.href = "/project?firstlogin=true";
            },
            error: function (data) {
                success = false;
                $("#serverError").empty().append('Invalid login or/and password.');
            }
        });
        return success;
    };
});