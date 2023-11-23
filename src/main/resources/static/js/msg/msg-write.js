let receiver = document.getElementById("msg-receiver");
let title = document.querySelector("#f input[name='title']");
let content = document.querySelector("#f textarea");
let id = 'netu';

let data = {
    no : "",
    div : "",
    smemberid : id,
    title : "",
    content : "",
    mdate : new Date(),
    read : 0
}

const chooseReceiver = (no, div, title) => {
    data.no = no;
    data.div = div;

    receiver.innerText += title;

    return false;
}

$('#msg-writer').on('hidden.bs.modal', function (e) {
    receiver.innerText = "받는 사람: ";
    title.value = "";
    content.value = "";

    return false;
})

const sendMsg = () => {
    data.title = title.value;
    data.content = content.value;

    console.log(data);
    $.ajax({
        url: "/msg/send",
        type: "post",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data),
        datatype: "json",
        success: function(result) {
            alert("성공!");
            sendMessage();
        }, error: function(result) {
            alert("성공!");
        }
    })
}