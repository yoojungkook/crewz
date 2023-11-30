let log = "";
/**
 * 로그인 상태일 때와 비로그인 상태일 때 출력되는 html이 다름
 */
window.onload = () => {
    let id = sessionStorage.getItem("loginId");

    if (id == null || id == "") {
        log = false;
    } else if (id != null) {
        log = true;
    }

    let logBox = document.getElementById("logBox");

    if (log == false) {
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
}

/**
 * login.html에서 로그인 버튼 클릭할 경우 실행
 */
function memberLogin() {
    if(log == true) {
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
            if (result.id == null) {

                Swal.fire({
                    text: "로그인에 실패했습니다.",
                    icon: "error",
                    confirmButtonColor: "#db3545",
                    confirmButtonText: "확인"
                });

            } else {
                sessionStorage.setItem("loginId", result.id);
                location.href = "/";
            }
        }
    });
}