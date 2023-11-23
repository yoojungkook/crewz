$("#joinmodal").on('shown.bs.modal', function () {

})

$(".dropdown-item category").click(function (e) {
    $("#category-box").text($(this).text());
})

$(".msg-info").on('shown.bs.modal', function (e) {

})

const msg = (e, index) => {
    $('#msg-people').text($('#send-people' + index).text());
    $('#msg-content').text($('#send-content' + index).text());
    $('#msg-date').text($('#send-date' + index).text());
}

let count = 0;
let scrollCheck = true;

const ch = () => {
    if (scrollCheck) {
        $.ajax({
            url: "/html/notification-msg.html",
            type: "get",
            datatype: "html",
            success: function (result) {
                $.ajax({
                    url: "/msg",
                    type: "get",
                    data: {"no": count},
                    datatype: "json",
                    success: function (list) {
                        for (let item of list.msglist) {
                            let vi = document.createElement("span");
                            vi.setAttribute("data-bs-toggle", "modal");
                            vi.setAttribute("data-bs-target", ".msg-info");
                            vi.innerHTML = result;

                            vi.querySelector(".col-10 m-header:first-of-type").innerHTML = item.title;
                            vi.querySelector(".col-10 m-content:first-of-type").innerHTML = item.content;
                            vi.querySelector(".col-10 m-date:first-of-type").innerHTML = item.date;

                            document.getElementById("msg-body").append(vi);
                        }
                        count += 1;
                    }, error: function () {

                    }
                })

            }, error: function () {
                console.log("실패1!");
            }
        })
    }
}

$('#msg-body').scroll(function () {
    if (scrollCheck) {
        console.log("이동!");
        let scrollTop = $(this).scrollTop();
        let innerHeight = $(this).innerHeight();
        let scrollHeight = $(this).prop('scrollHeight');

        if (scrollTop + innerHeight >= scrollHeight - 3) {
            if (count < 3) ch();
            else {
                let vi = document.createElement("div");
                vi.setAttribute("class", "d-flex justify-content-center");
                vi.innerHTML = "<button type=\"button\" class=\"btn\">쪽지함으로</button>";
                document.getElementById("msg-body").append(vi);
                scrollCheck = false;
            }
        }
    }
});

const info = (value) => {
    location.href = "/category/" + value + "/view?num=1";
}

const id = sessionStorage.getItem("loginId");
const mypage = () => {
    location.href = "/myinfo/mypage?id=" + id;
}

function home() {
    location.href = "/";
}

function logout() {
    // sessionStorage.clear();
    sessionStorage.removeItem("loginId");
    location.href = "/";
}