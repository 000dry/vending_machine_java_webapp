document.addEventListener("DOMContentLoaded", function(){
  console.log("DOM loaded");

  const ws = new WebSocket("ws://" + location.hostname + ":" + location.port + "/vendor");
  let appJSON = null;

  ws.onopen = function(){
    console.log("Websocket open!");
    ws.send("Hello server!");
    setTimeout(function(){ws.send("A");}, 1000);
  };

  ws.onmessage = function(event){
    appJSON = JSON.parse(event.data);
    console.log(appJSON);
  };

})
