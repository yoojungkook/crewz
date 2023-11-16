let heart = false;
function home() {
    location.href= "/";
}

function heart_check() {
    // this.stopPropagation();
    let like = document.getElementById("like");
    let dislike = document.getElementById("dislike");
    if(heart) {
        like.style.display = "";
        dislike.style.display = "none";
        heart = false;
    } else {
        like.style.display = "none";
        dislike.style.display = "";
        heart = true;
    }
}