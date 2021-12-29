"use strict";
use(function () {

    var cookieset =false;
    var cookie ="";

    if(request != null){

    cookie = request.getCookie("test")?request.getCookie("test").value:"";

    if(cookie === "")
    {
			return {cookieset : false};
    }else{
			return {cookieset : true};
    }
}

});