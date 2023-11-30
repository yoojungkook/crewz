let url = window.location.href;
if (!url.includes('#')) {
    $.ajax({
        url: "/html/mypage/myinfo.html",
        type: "get",
        datatype: "html",
        success: function (result) {
            $('#mypage-content').html(result);
        }
    })
} else {
    let loc = url.split("#");
    $.ajax({
        url: "/html/mypage/" + loc[1] + ".html",
        type: "get",
        datatype: "html",
        success: function (result) {
            if (loc[1] == "message") {
                $('#mypage-content').html(result);
                messageList();
            } else {
                $('#mypage-content').html(result);
            }
        }
    })
}

$('.menu-tab').on('click', function() {
    let url2 = window.location.href;
    let loc2 = url2.split("#");
    if(!url2.includes('#')) {
        $.ajax({
            url: "/html/mypage/myinfo.html",
            type: "get",
            datatype: "html",
            success: function(result) {
                $('#mypage-content').html(result);
            }
        })
    } else {
        $.ajax({
            url: "/html/mypage/" + loc2[1] + ".html",
            type: "get",
            async: true,
            datatype: "html",
            success: function (result) {
                if (loc2[1] == "message") {
                    $('#mypage-content').html(result);
                    messageList();
                } else {
                    $('#mypage-content').html(result);
                }
            }
        })
    }
})