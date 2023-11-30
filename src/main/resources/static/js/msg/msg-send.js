const msgSend = (e, index) => {
    $('#msgSend-people').text($('#send-people' + index).text());
    $('#msgSend-content').text($('#send-content' + index).text());
    $('#msgSend-date').text($('#send-date' + index).text());
}

let count = 1;
let scrollCheck = true;

const msgCheck = () => {
    if(scrollCheck && count <= 3) {
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
                        if(list.msglist.length != 0) {
                            for (let item of list.msglist) {
                                let vi = document.createElement("span");
                                vi.setAttribute("data-bs-toggle", "modal");
                                vi.setAttribute("data-bs-target", ".msgSend-info");
                                vi.innerHTML = result;

                                vi.querySelector(".col-10 m-header:first-of-type").innerHTML = item.title;
                                vi.querySelector(".col-10 m-content:first-of-type").innerHTML = item.content;
                                vi.querySelector(".col-10 m-date:first-of-type").innerHTML = item.mdate;

                                document.getElementById("msg-body").append(vi);
                            }

                            if(list.msglist.length != 5) {
                                let vi = document.createElement("div");
                                vi.setAttribute("class", "d-flex justify-content-center");
                                vi.innerHTML = "<button type=\"button\" class=\"btn\">쪽지함으로</button>";
                                document.getElementById("msg-body").append(vi);
                                count = 3;
                            }
                            else
                                count += 1;
                            console.log("count: " + count);
                        }
                    }, error: function () {
                        alert("리스트 조회 실패!");
                    }
                })
            }, error: function () {
                console.log("메세지 조회 실패!");
            }
        })
    }
}

$('#msg-body').scroll(function() {
    if(scrollCheck) {
        let scrollTop = $(this).scrollTop();
        let innerHeight = $(this).innerHeight();
        let scrollHeight = $(this).prop('scrollHeight');

        if (scrollTop + innerHeight >= scrollHeight - 3) {
            if (count <= 3)  {
                msgCheck();
            }
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