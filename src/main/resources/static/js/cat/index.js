$(".dropdown-item category").click(function (e) {
    $("#category-box").text($(this).text());
})


const msg = (e, index) => {
    $('#msg-people').text($('#send-people' + index).text());
    $('#msg-content').text($('#send-content' + index).text());
    $('#msg-date').text($('#send-date' + index).text());
}

const mypage = () => {
    location.href = "/myinfo/mypage?id=" + localStorage.getItem("loginId");
}

function logout() {
    $.ajax({
        url: "/member/logout",
        type: "post",
        data: {"id" : loginId},
        datatype: "json",
        success: function(result) {
            if(result.status) {
                localStorage.removeItem("loginId");
                location.href="/";
            } else {
                alert("로그아웃 실패!");
            }
        }
    })
}

function home() {
    location.href= "/";
}