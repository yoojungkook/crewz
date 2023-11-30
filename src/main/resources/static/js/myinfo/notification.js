$(document).ready(function() {
    connect();
})

const stompClient = new StompJs.Client({
    brokerURL: "ws://localhost/notification-websocket"
});

// 메세지 수신
stompClient.onConnect = () => {
    stompClient.subscribe('/sub/message/' + localStorage.getItem("id"), (msg) => {
        // alert("메세지 내용: " + JSON.parse(notification.body).content);
        // alert("메세지 내용: " + JSON.parse(notification.body).content);
        Msg = JSON.parse(msg.body);

        console.log("수신받은 메세지")
        console.log(Msg);

        $.ajax({
            url: "/html/mypage/toast.html",
            type: "get",
            datatype: "html",
            success: function(result) {
                let toastContainer = document.getElementById("toastContainer");
                toastContainer.innerHTML += result;

                document.querySelector('#toast-content').setAttribute("id", "toast-" + Msg.no);

                let toastBtn = document.getElementById("toastBtn");
                let liveStrap = document.getElementById("toast-" + Msg.no);

                if(toastBtn) {
                    const strap = bootstrap.Toast.getOrCreateInstance(liveStrap);
                    toastBtn.addEventListener('click', () => {
                        strap.show();
                    })
                }

                // 토스트 실행
                $("#toastBtn").trigger('click');

                // 토스트 삭제(4초뒤)
                $("#toast-" + Msg.no).on("hide.bs.toast", () => {
                    $("#toast-" + Msg.no).remove();
                })
            }
        })
    })
}

function connect() {
    stompClient.activate();
    console.log("websocket 실행!");
}

// 메세지 송신
function sendMessage(msg) {
    console.log("전해받은 Msg");
    console.log(msg);
    stompClient.publish({
        destination: "/app/moim",
        body: JSON.stringify(msg)
    })
}