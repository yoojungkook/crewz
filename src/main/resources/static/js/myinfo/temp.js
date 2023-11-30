$(document).ready(function() {
    console.log("진입 성공!")

    localStorage.setItem("id", "netu");

    $.ajax({
        url: "/member/temp",
        type: "post",
        data: {"id" : localStorage.getItem("id")},
        success: function() {
            console.log("로그인 성공!");
        }
    })
})