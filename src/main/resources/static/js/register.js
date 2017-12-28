$("#register-btn").click(function(event) {
    var data = {};
    $("#register-form").serializeArray().map(function(x){data[x.name] = x.value;});
    if(data.password === data.passConf) {
        delete data.passConf;
        data.bornDate = new Date(data.bornDate).getTime();
        $.ajax({
            method: "POST",
            url: '/api/register',
            data: JSON.stringify(data),
            contentType: 'application/json'
        }).done(function() {
            window.location = "/login";
        });
    }
    event.preventDefault();
});