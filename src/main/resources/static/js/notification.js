$(document).ready(function() {
    connect();
})

const stompClient = new StompJs.Client({
    brokerURL: "ws://localhost/notification-websocket"
});

// 메세지 수신
stompClient.onConnect = () => {
    stompClient.subscribe('/sub/des/2', (notification) => {
        // alert("메세지 내용: " + JSON.parse(notification.body).content);
        alert("메세지 내용: " + JSON.parse(notification.body).content);
    })

    stompClient.subscribe('/sub/des/3', (notification) => {
        // alert("메세지 내용: " + JSON.parse(notification.body).content);
        alert("메세지 내용: " + JSON.parse(notification.body).content);
    })
}

function connect() {
    stompClient.activate();
    console.log("websocket 실행!");
}

// 메세지 송신
function sendMessage() {
    stompClient.publish({
        destination: "/app/moim",
        body: JSON.stringify({'no' : 1, 'div' : 2, 'content' : "메시지 전송!"})
    })
}