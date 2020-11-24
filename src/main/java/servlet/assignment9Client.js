/********************************************************/
// function getTable
//  parameter: table code
//  action: create the XMLHttpRequest object, register the
//          handler for onreadystatechange, prepare to send
//          the request (with open), and send the request,
//          along with the zip code, to the server
//  includes: the anonymous handler for onreadystatechange,
//            which is the receiver function, which gets the
//            response text.

function getTable ()
{
  if (window.XMLHttpRequest)
  {  // IE7+, Firefox, Chrome, Opera, Safari
     var xhr = new XMLHttpRequest();
  }
  else
  {  // IE5, IE6
     var xhr = new ActiveXObject ("Microsoft.XMLHTTP");
  }
//window.location.Href
  var url = "https://gterziyshello.herokuapp.com/assignment88";
  var operation = document.getElementById ("logicalOperation").value;  
  if (!operation.length > 0){
	return;
  }
  url = url + "?Expression=" + encodeURIComponent(operation);
  //Create a asynchronous GET request
  xhr.open ("POST", "https://gterziyshello.herokuapp.com/assignment88", true);
	
  // Register the embedded handler function
  // This function will be called when the server returns
  // (the "callback" function)
  xhr.onreadystatechange = function ()
  { // 4 means finished, and 200 means okay.
    if (xhr.readyState == 4 && xhr.status == 200)
    { // Data should look like "Fairfax, Virginia"
      var putOperation = xhr.responseText;
	  document.getElementById ("typed").innerHTML = putOperation;
    }
	
  }
  // Call the response software component
  xhr.send (null);
}