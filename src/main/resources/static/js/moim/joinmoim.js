
 const joinmoim = (no,memberid) => {
	 
	  $.ajax({
            url: "/moim/join",
            type: "post",
            data: {"moimno": no,"memberid":memberid},
            dataType: "json"
            
            
      });
 }