// TODO: msg-send.html
$(document).on('click', '.msg-item', function() {
    let select = document.getElementById("select");
    select.innerText = this.innerText;
    select.setAttribute("div", this.getAttribute("div"));
    select.setAttribute("no", this.getAttribute("no"));
})

let Msg = {
    id : "",
    div : "",
    smemberid : "",
    no : "",
    title : "",
    content : "",
    mdate : new Date()
}

$('#msgSend').on('show.bs.modal', function() {
    let txt = "";
    let item = this;
    $.ajax({
        url: "/msg/list",
        type: "post",
        data: {"id" : "netu"},
        datatype: "json",
        success: function(result) {
            let moimlist = result.moimList;
            let somoimlist = result.somoimList;

            let moimTxt = "<li><a class=\"dropdown-item\" href=\"#\"><h5>모임</h5></a></li>";
            moimTxt += "<li><hr class=\"dropdown-divider\"></li>";
            if(moimlist.length != 0) {
                // <li><button className="dropdown-item" type="button"></button></li>
                for(let item of moimlist) {
                    moimTxt += "<li><button div=\"0\" no='" + item.no + "' class=\"dropdown-item msg-item\">" + item.title + "</button></li>";
                }
                moimTxt += "<li><hr class=\"dropdown-divider\"></li>";
            }
            txt += moimTxt;

            let somoimTxt = "<li><a class=\"dropdown-item\"><h5>소모임</h5></a></li>";
            somoimTxt += "<li><hr class=\"dropdown-divider\"></li>";
            if(somoimlist.length != 0) {
                for(let item of somoimlist) {
                    somoimTxt += "<li><button div=\"1\" no='" + item.no + "' class=\"dropdown-item msg-item\">" + item.title + "</button></li>";
                }
            }
            txt += somoimTxt;
            item.querySelector('.dropdown-menu').innerHTML = txt;
        }
    })

    this.querySelector('#send').addEventListener('click', function() {
        let select = document.getElementById("select");

        Msg = {
            no : select.getAttribute("no"),
            div : select.getAttribute("div"),
            smemberid : localStorage.getItem("id"),
            title : $("#recipient-name").val(),
            content : $("#message-text").val(),
            mdate : new Date()
        }

        console.log("Msg: " + Msg);

        $.ajax({
            url: "/msg/send",
            type: "post",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(Msg),
            datatype: "json",
            success: function(result) {
                console.log("메세지 보내기 성공!")
                $("#msgSend").modal("hide");
                sendMessage(Msg);
            },
            error: function() {
                console.log("메세지 보내기 실패!")
            }
        })
    })
})