// let log = null;
let loginId = localStorage.getItem("loginId");
console.log("로그인 id: " + loginId);

function loginStatus() {
    if (loginId == null || loginId == "") {
        return false;
    } else if (loginId != null) {
        return true;
    }
}

let logBox = document.getElementById("logBox");

if (loginStatus() == false) {
    $.ajax({
        url: "/html/log/nav-logout.html",
        type: "get",//ajax에서 html은 get만
        datatype: "html",
        success: function (result) {
            logBox.innerHTML = result;
        }
    });
} else {
    $.ajax({
        url: "/html/log/nav-login.html",
        type: "get",
        datatype: "html",
        success: function (result) {
            logBox.innerHTML = result;
        }
    });
}

function memberLogin() {
    if (loginStatus() == true) {
        location.href = "/";
    }

    let log_id = document.getElementById("log-id");
    let log_pwd = document.getElementById("log-pwd");

    $.ajax({
        url: "/logincheck",
        type: "post",
        data: {"id": log_id.value, "pwd": log_pwd.value},
        datatype: "json",
        success: function (result) {
            if (result.id == null)
                alert("존재하지 않는 계정입니다.");
            else {
                localStorage.setItem("loginId", result.id);
                location.href = "/";
            }
        }
    });
}