document.addEventListener("DOMContentLoaded", function(){
  console.log("DOM loaded");

  let ws = new WebSocket("ws://" + location.hostname + ":" + location.port + "/vendor");

  ws.onopen = function(){
    console.log("Websocket open!")
    ws.send("Hello server!");
  };

  ws.onmessage = function(event){
    console.log(event.data);
  };

})