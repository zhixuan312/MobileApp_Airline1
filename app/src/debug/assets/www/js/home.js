var storeObject = {
    category: null
}

var host = "172.25.103.127:8080";
var urlPrefix = "/MerlionAirlinesSystem-war/webresources/";
var url = "http://" + host + urlPrefix;
var referenceN;
var passportN;

window.onload = function () {

    var url = document.location.href,
        params = url.split('?')[1].split('&'),
       
       tmp = params[0].split('=');
       referenceN= tmp[1];
       passportN = params[1].split('=')[1];
    console.log("kaishile");
    document.getElementById('here').innerHTML = referenceN+passportN;
    var request = "referenceN="+referenceN+"&passport="+passportN;
    
  
   
}

function loadProductCategories()
{
    var request = "referenceN="+referenceN+"&passport="+passportN;
    
    
    
    $.ajax({ 
      type: "GET",


      contentType: "application/json",
      dataType: "jsonp",
      url: url + "generic/webCheckIn",
      data: request,

      complete: function(result){

       var object = jQuery.parseJSON(result.responseText);
                    var output = "";

                          for(i = 0; i < object.ticketCheckInEntityList.ticketCheckInEntity.length; i++)
                          {
                              output += "<li><a href=\"#items\" onclick=\"storeObject.category='" + object.ticketCheckInEntityList.ticketCheckInEntity[i] + "';\">" + object.ticketCheckInEntityList.ticketCheckInEntity[i] + "</a></li>"
                                console.log("enter");
                          }
console.log("this is the output"+output);
                           $('#productCategories').html(output).listview("refresh");
      } 
    });
}



function loadProductItems()
{
    var request = "shopId=1&category=" + storeObject.category;
    
    
    
    $.ajax({ 
      type: "GET",
      url: url + "ProductItemResources/getProductItemsByShopIdAndCategory",
      dataType: "text/plain",
      contentType: "application/json",
      data: request, 
      complete: function(result){
          var object = jQuery.parseJSON(result.responseText);
                    var output = "";

                    for(i = 0; i < object.ticketCheckInEntityList.ticketCheckInEntity.length; i++)
                    {
                        output += "<li><a href=\"#items\" onclick=\"storeObject.category='" + object.ticketCheckInEntityList.ticketCheckInEntity[i] + "';\">" + object.ticketCheckInEntityList.ticketCheckInEntity[i] + "</a></li>"
                    }

                     $('#productCategories').html(output).listview("refresh");
      } 
    });
}

$(document).on('pagebeforeshow', '#categories', loadProductCategories);
$(document).on('pagebeforeshow', '#items', loadProductItems);

