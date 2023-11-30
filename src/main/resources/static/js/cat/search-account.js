function searchId() {
    var name = $("#name").val();
    var tel = $("#tel").val();

    $.ajax({
        type: "POST",
        data: {"name": name, "tel": tel},
        url: "/find/id",
        success: function (data) {
            if (data == "null") {

                Swal.fire({
                    text: "아이디를 찾을 수 없습니다.",
                    icon: "error",
                    confirmButtonColor: "#db3545",
                    confirmButtonText: "확인"
                });

                $("#name").val('');
                $("#tel").val('');
            } else if (data != "null") {

                Swal.fire({
                    text: "회원님의 아이디는 " + data + "입니다.",
                    icon: "success",
                    confirmButtonColor: "#db3545",
                    confirmButtonText: "확인"
                });

                $("#name").val('');
                $("#tel").val('');
            }
        }
    });
}

function searchPwd() {
    var id = $("#id").val();
    var tel = $("#tel2").val();

    $.ajax({
        type: "POST",
        data: {"id": id, "tel": tel},
        url: "/find/pwd",
        success: function (data) {
            if (data == "null") {

                Swal.fire({
                    text: "비밀번호를 찾을 수 없습니다.",
                    icon: "error",
                    confirmButtonColor: "#db3545",
                    confirmButtonText: "확인"
                });

                $("#id").val('');
                $("#tel2").val('');
            } else if (data != "null") {

                Swal.fire({
                    text: "회원님의 비밀번호는 " + data + "입니다.",
                    icon: "success",
                    confirmButtonColor: "#db3545",
                    confirmButtonText: "확인"
                });

                $("#id").val('');
                $("#tel2").val('');
            }
        }
    });
}