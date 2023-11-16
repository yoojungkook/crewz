// 플래그 변수 -> 소모임을 계속 눌러도 후기가 더 추가 되는 것을 막음
let somoim_flag = 0;
const somoim = (value) => {
    console.log("value: " + value);
    console.log("somoim_flag: " + somoim_flag);
    if (value == somoim_flag) {
        let area = document.getElementById("item-area");
        $.ajax({
            url: "/html/somoim-item.html",
            type: "get",
            datatype: "html",
            success: function (result) {
                $.ajax({
                    url: "/msg",
                    type: "get",
                    data: {"no": 0},
                    datatype: "json",
                    success: function (list) {
                        for (let i of list.msglist) {
                            let item = document.createElement("div")
                            item.setAttribute("class", "text-start")
                            item.innerHTML = result;

                            item.querySelector(".text-start .somoim-content h4").innerText = i.title;
                            item.querySelector(".text-start .somoim-content p").innerText = i.content;

                            area.append(item);
                        }
                    }
                })
            }
        })
        somoim_flag = 1;
    }
}