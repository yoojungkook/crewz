let cat = document.getElementsByClassName("cat");
let list = [];
for (let i = 0; i < cat.length; i++) {
    list[i] = cat[i].innerText;
}

const go = (no) => {
    location.href = "/search?no=" + no;
}

let category = document.getElementById("category-box");
let input = document.getElementById("search-box");
input.addEventListener("keypress", function (event) {
    if (event.key === "Enter") {
        event.preventDefault();

        // if(category == "카테고리") location.href = "/category?name=" + input.value;
        // else location.href = "/search/" + category.innerText + "/keyword?msgSend=" + input.value;
        // for (let i = 0; i < list.length; i++) {
        //     if (list[i] == category.innerText)
        //         location.href = "/search/" + category.innerText;
        // }
        location.href = "/search/" + category.getAttribute("num") + "/msgSend?word=" + input.value;
    }
});

const catchange = (value, item) => {
    category.innerText = value;
    category.setAttribute("num", item.getAttribute("num"));
}
