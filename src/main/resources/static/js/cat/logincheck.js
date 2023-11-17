let log = "";
window.onload = () => {
    let id = sessionStorage.getItem("loginId");

    // 비로그인 상태
    if(id == null || id == "") {
        log = false;
    } else if(id != null) {
        log = true;
    }

    let logBox = document.getElementById("logBox");

    if(log == false) {
        $.ajax({
            url: "/html/log/nav-logout.html",
            type:"get",//ajax에서 html은 get만
            datatype: "html",
            success: function(result) {
                logBox.innerHTML = result;
            }
        });
    } else {
        $.ajax({
            url: "/html/log/nav-login.html",
            type:"get",
            datatype: "html",
            success: function(result) {
                logBox.innerHTML = result;
            }
        });
    }
}

function memberLogin() {
    if(log == true) {
        location.href = "/";
    }

    let id = document.getElementById("log-id");
    let pwd = document.getElementById("log-pwd");

    $.ajax({
        url: "/logincheck",
        type: "post",
        data: {"id" : id.value, "pwd" : pwd.value},
        datatype: "json",
        success: function(result) {
            if(result.id == null)
                alert("존재하지 않는 계정입니다.");
            else {
                sessionStorage.setItem("loginId", result.id);
                location.href = "/";
            }
        }
    });
}