let idChecked = 1;
let pwdChecked = 1;
let nameChecked = 1;
let telChecked = 1;
let birthChecked = 1;
let checkboxChecked = 1;



//아이디 정규식 => 확인 후 중복 확인
async function checkId() {
    const id = form.id.value.trim();
    if (!(new RegExp("^.(?=.*[a-z])(?=.*[0-9]).{4,19}$").test(id))) {
        $("#idStatus").text("영어와 숫자를 포함하여 5~20자 이내로 입력해 주세요.");
        idChecked = 1;
        checkAll();
    } else {
        const result = await duplicateId(id);
        if (result == 1) {
            $("#idStatus").text("이미 존재하는 아이디입니다.");
            idChecked = 1;
            checkAll();
        } else if (result == 0) {
            $("#idStatus").text("사용 가능한 아이디입니다.");
            idChecked = 0;
            checkAll();
        }
    }
}

function duplicateId(id) {
    return new Promise(resolve => {
        $.ajax({
            type: "post",
            data: {"id": id},
            url: "/member/checkId",
            success: function (count) {
                resolve(count == 1 ? 1 : 0);
            }
        });
    });
}

//비밀번호 확인
function checkPwd() {
    const pwd = form.pwd.value.trim();
    const pwd2 = form.pwd2.value.trim();

    //비밀번호 정규식
    if (!(new RegExp("^.(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{7,15}$").test(pwd))) {
        $("#pwdStatus").text("영어, 숫자, 특수문자를 포함하여 8자~16자 이내로 입력해주세요.");
        pwdChecked = 1;
        checkAll();
    } else if (pwd != pwd2) {
        $("#pwdStatus").text("일치하지 않습니다.");
        pwdChecked = 1;
        checkAll();
    } else {
        $("#pwdStatus").text("");
        pwdChecked = 0;
        checkAll();
    }
}


//이름 정규식
function checkName() {
    const name = form.name.value.trim();

    if (!(new RegExp("^[가-힣a-zA-Z]+$").test(name))) {
        $("#nameStatus").text("한글 또는 영어만 입력해주세요");
        nameChecked = 1;
        checkAll();
    } else {
        $("#nameStatus").text("");
        nameChecked = 0;
        checkAll();
    }
}

//전화번호 정규식
function checkTel() {
    const tel = form.tel.value.trim();

    if (!(new RegExp("^(010)[0-9]{4}[0-9]{4}$").test(tel))) {
        $("#telStatus").text("01012345678 형식으로 입력해 주세요.");
        telChecked = 1;
        checkAll();
    } else {
        $("#telStatus").text("");
        telChecked = 0;
        checkAll();
    }
}

//이용약관 동의 체크했는지 확인
$("#check1").click(function () {
    const checked = $("#check1").is(":checked");

    if (checked) {
        checkboxChecked = 0;
        checkAll();
    } else {
        checkboxChecked = 1;
        checkAll();
    }
});

function checkBirth() {
    const birth = document.getElementById("birth");
    if (birth.value) {
        birthChecked = 0;
        checkAll();
    } else {
        birthChecked = 1;
        checkAll();
    }
}

function checkAll() {
    console.log(idChecked, pwdChecked, nameChecked, telChecked, birthChecked, checkboxChecked);
    if (idChecked == 0 && pwdChecked == 0 && nameChecked == 0 && telChecked == 0 && checkboxChecked == 0 && birthChecked == 0) {//모든 값이 0일 때
        $("#joinBtn").prop("disabled", false);
    } else {
        $("#joinBtn").prop("disabled", true);
    }
}