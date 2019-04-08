$(document).ready(function () {
    $('#submit').click(function () {
        var form = $("#url").serialize();
        $.ajax({
            type: "POST",
            contentType: 'application/json; charset=utf-8',
            url: "/createURL",
            data: JSON.stringify(form),
            dataType: 'json',
            mimeType: 'application/json',
            success:function (response) {
               /* $("#resultURLText").removeAttribute("hidden");
                $("#resultURL").removeAttribute("hidden");
                $("#resultURL").innerText = response;*/
                console.log("Success");
                console.log(response);
                alert("Success "+response);
            },
            error:function (response) {
                /*$("#resultURL").removeAttribute("hidden");
                $("#resultURL").innerText = response;*/
                console.log("Error");
                console.log(response);
                alert("Error"+response);
            }
        });
    });
});