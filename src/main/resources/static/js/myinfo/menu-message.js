<!-- selectmenu.js -->
const messageList = () => {
    $.ajax({
        url: "/msg",
        type: "get",
        data: {"no": 1},
        async: true,
        datatype: "json",
        success: function (result2) {
            let tl = "";
            for (let item of result2.msglist) {
                let el = document.createElement("tr");
                el.setAttribute("num", item.no);
                el.setAttribute("data-bs-toggle", "modal");
                el.setAttribute("data-bs-toggle", "modal");
                el.setAttribute("data-bs-target", "#msgInfo");
                el.innerHTML += '<td style="width: 10%; text-align: center">' + item.smemberid + '</td>';
                if(item.div == 0)
                    el.innerHTML += '<td style="width: 7%; text-align: center">모임</td>';
                else
                    el.innerHTML += '<td style="width: 7%; text-align: center">소모임</td>';
                el.innerHTML += '<td style="width: 63%">' + item.title + '</td>';
                el.innerHTML += '<td style="width: 20%">' + item.mdate + '</td>';
                el.innerHTML += '<td style="display: none">' + item.content + '</td>';

                el.setAttribute("onclick", "msgInfo(this)");
                tl += el.outerHTML;
            }
            $("#msg-content").html(tl);
        }
    })
}

// TODO: msg-receipt.html
let msg = document.getElementById("msgInfo");
function msgInfo(value) {
    let no = value.getAttribute("num");
    console.log("no: " + no);

    let id = value.querySelector('td:nth-of-type(1)').innerText;
    let title = value.querySelector('td:nth-of-type(3)').innerText;
    let content = value.querySelector('td:nth-of-type(5)').innerText;

    msg.querySelector('img').setAttribute("src", "/member/img/" + id);

    $.ajax({
        url: "/msg/moim",
        type: "get",
        data: {"no" : no},
        datatype: "json",
        success: function(result) {
            msg.querySelector('h1').innerText = result.moim.title;
        }
    })

    msg.querySelector('.card-body').innerHTML = title + "<br/>" + content;
}

// TODO: message.html
let msgCheck = false;
const btnMenu = (value) => {
    let no = value.getAttribute('num');

    console.log("btn no: " + no);
    if(no == 2) {
        $('.table-msg:nth-of-type(1)').css("display", "none");
        $('.table-msg:nth-of-type(2)').css("display", "");
    } else if(no == 1) {
        $('.table-msg:nth-of-type(1)').css("display", "");
        $('.table-msg:nth-of-type(2)').css("display", "none");
    } else if (no == 3) {
        $('#msgSend').on('click', function() {

        })
    }

    if(!msgCheck) {
        $.ajax({
            url: "/msg/sendmsg",
            type: "get",
            data: {"id" : "metu"},
            datatype: "json",
            success: function(result) {
                let sendContent = document.getElementById("msg-send-content");
                let txt = "";
                for(let item of result.sendlist) {
                    let el = document.createElement("tr");
                    el.setAttribute("num", item.no);
                    el.setAttribute("div", item.div);
                    el.setAttribute("data-bs-toggle", "modal");
                    el.setAttribute("data-bs-target", "#myMsg");
                    if(item.div == 0)
                        el.innerHTML += '<td style="width: 7%; text-align: center">모임</td>';
                    else
                        el.innerHTML += '<td style="width: 7%; text-align: center">소모임</td>';
                    el.innerHTML += '<td style="width: 63%">' + item.title + '</td>';
                    el.innerHTML += '<td style="display: none;">' + item.content + '</td>';
                    el.innerHTML += '<td style="width: 20%">' + item.mdate + '</td>';

                    el.setAttribute("onclick", "myMsg(this)");
                    txt += el.outerHTML;
                }
                sendContent.innerHTML = txt;
                msgCheck = true;
            }
        })
    }
}
function myMsg(value) {
    let myMsg = document.getElementById("myMsg");
    let div = value.getAttribute("div");
    let no = value.getAttribute("num");
    if(div == 1) {
        $.ajax({
            url: "/msg/somoim",
            type: "get",
            data: {"id": "metu", "no": no},
            datatype: "json",
            success: function (result) {
                let somoim = result.somoim;

                myMsg.querySelector('.card-header').innerText = somoim.title;
                myMsg.querySelector('.card-title').innerText = value.querySelector('td:nth-of-type(2)').innerText;
                myMsg.querySelector('.card-text').innerText = value.querySelector('td:nth-of-type(3)').innerText;
            }
        })
    } else {
        $.ajax({
            url: "/msg/moim",
            type: "get",
            data: {"id": "metu", "no": no},
            datatype: "json",
            success: function (result) {
                let moim = result.moim;

                myMsg.querySelector('.card-header').innerText = moim.title;
                myMsg.querySelector('.card-title').innerText = value.querySelector('td:nth-of-type(2)').innerText;
                myMsg.querySelector('.card-text').innerText = value.querySelector('td:nth-of-type(3)').innerText;
            }
        })
    }
}