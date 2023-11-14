// 플래그 변수 -> 버튼을 계속 눌러도 후기가 더 추가 되는 것을 막음
let review_flag = 0;
const rv = (value) => {
    console.log("체크!");
    if(value == review_flag) {
        let review_list = document.getElementById("review-list");
        $.ajax({
            url: "/html/review-item.html",
            type: "get",
            datatype: "html",
            success: function(result) {
                $.ajax({
                    url: "/msg",
                    type: "get",
                    data: {"no": 0},
                    datatype: "json",
                    success: function(review) {
                        let j = 0;
                        let last = review.msglist.length;
                        for(let item of review.msglist) {
                            let vi = document.createElement("div");
                            vi.setAttribute("class", "row");
                            if( j != last-1)
                                vi.setAttribute("style", "margin: 10px; border-bottom: 5px solid gainsboro");
                            else
                                vi.setAttribute("style", "margin: 10px;");
                            vi.innerHTML = result;

                            vi.querySelector("r-title").innerHTML = item.title;

                            review_list.append(vi);
                            j++;
                        }
                    }
                })
            }
        })
        review_flag = 1;
    }
}

const check = () => {
}