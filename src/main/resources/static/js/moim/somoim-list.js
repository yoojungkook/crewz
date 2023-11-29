// 플래그 변수 -> 소모임을 계속 눌러도 후기가 더 추가 되는 것을 막음
let somoim_flag = -1;
const somoim = (value) => {
	var no = value;
    console.log("value: " + value);
    console.log("somoim_flag: " + somoim_flag);
    if (somoim_flag == -1) {
        let area = document.getElementById("item-area");
        $.ajax({
            url: "/html/somoim-item.html",
            type: "get",
            datatype: "html",
            success: function (result) {
                $.ajax({
                    url: "/moim/somoimlist",
                    type: "get",
                    data: {"no": no},
                    dataType: "json",
                    success: function (list) {
                        for (let i of list.so_list) {
							let so_num = i.no;
							console.log(so_num);
							$.ajax({
							url:"/somoim/cnt",
							type : "get",	
							data : {"somoimno" : so_num},
							dataType: "json",
							success : function(data){
								console.log("cnt : " + data);
								let item = document.createElement("div");
                            	item.setAttribute("class", "text-start");
                           	 	item.innerHTML = result;
								/*item.querySelector(".text-start #somoimname").value = i.title;*/
								item.querySelector(".text-start .somoim_update").setAttribute('num', so_num);
	                            item.querySelector(".text-start .somoim-content h4").innerText = i.title;
	                            item.querySelector(".text-start .somoim-content p").innerText = i.content;
	                            item.querySelector(".text-start .somoim-content span jdate").innerText = i.jdate;                           
	                            item.querySelector(".text-start .somoim-content p somoim-loc").innerText = i.loc;
	                            item.querySelector(".text-start .somoim-content p somoim-loc-trip").innerText = i.loc_trip;
	                            item.querySelector(".text-start .somoim-content p somoim-total").innerText = i.total;
	                            item.querySelector(".text-start .somoim-content p cnt").innerText = data.cnt;
	                            item.querySelector(".text-start .somoim_img img").src = "/somoim/read-img?fname="+i.photo;
	                            item.querySelector(".text-start .somoim-content div button").onclick = function(){
									let result = window.confirm("가입 하시겠습니까?");
									console.log(result);
									if(result == true){
										somoim_join(i.no, "test");
										let temp = item.querySelector(".text-start .somoim-content div button").innerHTML;
										if(temp === "가입하기" ){
											item.querySelector(".text-start .somoim-content div button").innerHTML = "취소하기";
										}else{
											item.querySelector(".text-start .somoim-content div button").innerHTML = "가입하기";
										}
									}else{
										console.log("false 들어옴");
									}
								}
								item.querySelector(".text-start .somoim-content div .del").onclick = function(){
									let result = window.confirm("삭제 하시겠습니까?");
									if(result == true){
										$.ajax({
						                    url: "/somoim/del",
						                    type: "get",
						                    data: {"somoimno": so_num,"moimno":i.moimno},
						                    dataType: "json",
										});
									}
									 
								}	
                            	area.append(item);
							}
							})
                        }
                    }
                })
            }
        })
        somoim_flag = -2;
    }
}

const somoim_join = (num, user) => {
	$.ajax({
            url: "/somoim/join",
            type: "get",
            data: {"no":num , "user":user },
            dataType: "json",
            success: function (data) {}
				
			});
}