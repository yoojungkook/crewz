let writemsg_check = true;
$('#writemsg').on('shown.bs.modal', function (e) {
    if(writemsg_check){
        msglist();
        writemsg_check = false;
    }

    return false;
})

function msglist() {
    $.ajax({
        url: "/msg/list",
        type: "post",
        data: {"id" : "netu"},
        datatype: "json",
        success: function(result) {
            console.log(result.moimList);
            console.log(result.somoimList);
            let moim_msg_list = document.getElementById("moim-msg-list");

            let moimList = result.moimList;
            let txt = "";
            if(moimList.length != 0) {
                txt += "<li><button class=\"dropdown-item\" type=\"button\"><h4 style='margin-bottom: 0'>모임</h4></button></li><hr style='margin: 1px'/>";
                for (let item of moimList) {
                    txt += "<li><button class=\"dropdown-item\" type=\"button\" data-bs-target=\"#msg-writer\" data-bs-toggle=\"modal\" onclick='chooseReceiver(" + item.no + ', 0, \"' + item.title +"\")'>" + item.title + "</button></li>";
                }
                txt += "<hr/>";
            }

            let somoimList = result.somoimList;
            if(somoimList.length != 0) {
                txt += "<li><button class=\"dropdown-item\" type=\"button\"><h4 style='margin-bottom: 0'>소모임</h4></button></li><hr style='margin: 1px'/>"
                for(let item of somoimList) {
                    txt += "<li><button class=\"dropdown-item\" type=\"button\" data-bs-target=\"#msg-writer\" data-bs-toggle=\"modal\" onclick='chooseReceiver(" + item.no + ', 1, \"' + item.title +"\")'>" + item.title + "</button></li>";
                }
            }

            moim_msg_list.innerHTML = txt;
        }
    })
}