/**
 * 화면 시작 시 해당 텍스트 숨김 처리
 */
$(document).ready(function () {
    $("#pwdStatusSuccess").hide();
    $("#pwdStatusFail").hide();
    $("#pwdStatus").hide();
    $("#telStatus").hide();
});

/**
 * 비밀번호 일치 여부 확인
 */
function changePwd() {
    let pwdNew1 = $("#pwdNew1").val();
    let pwdNew2 = $("#pwdNew2").val();

    if (!(new RegExp("^.(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{7,15}$").test(pwdNew1))) {
        $("#submitBtn").prop("disabled", true);
        $("#pwdStatus").show();
    } else if (pwdNew1 != pwdNew2) {
        $("#submitBtn").prop("disabled", true);
        $("#pwdStatusSuccess").hide();
        $("#pwdStatusFail").show();
        $("#pwdStatus").hide();
    } else if (pwdNew1 == pwdNew2) {
        $("#pwd").val(pwdNew2);
        $("#submitBtn").prop("disabled", false);
        $("#pwdStatusSuccess").show();
        $("#pwdStatusFail").hide();
        $("#pwdStatus").hide();
    }
}

function changeTel() {
    let tel = $("#tel").val();

    if (!(new RegExp("^(010)[0-9]{4}[0-9]{4}$").test(tel))) {
        $("#telStatus").show();
        $("#submitBtn").prop("disabled", true);
    } else {
        $("#telStatus").hide();
        $("#submitBtn").prop("disabled", false);
    }
}

/**
 * 회원탈퇴
 */
function delMember() {
    let id = $("#id").val();

    Swal.fire({
        title: "정말로 탈퇴하시겠습니까?",
        icon: "warning",
        confirmButtonColor: "#db3545",
        confirmButtonText: "네",
        showCancelButton: true,
        cancelButtonText:"아니오"
    }).then((result) => {
        if (result.isConfirmed) {
            sessionStorage.removeItem("loginId");
            localStorage.clear();
            location.href = "/member/delete?id=" + id;
        }
    });
}

/**
 * 세션 통해서 아이디 가져오기
 */
$("#profile-id").val(sessionStorage.getItem("loginId"));

/**
 * 프로필 사진 변경
 */
const profileChange = document.getElementById('profile-upload');
const profileForm = document.getElementById('profile-form');
const id = sessionStorage.getItem("loginId");
profileChange.addEventListener('change', function () {
    if (profileChange.files.length > 0) {
        profileForm.submit();
    }
});

/**
 * 라디오 버튼에 체크했는지 확인 후 버튼 활성화 여부 결정
 */
$(".reason").click(function () {
    if ($("input[type=radio]:checked").is(":checked")) {
        $("#deleteBtn").prop("disabled", false);
    } else {
        $("#deleteBtn").prop("disabled", true);
    }
});