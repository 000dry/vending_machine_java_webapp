document.addEventListener("DOMContentLoaded", function(){
  console.log("DOM loaded");

  let ws = new WebSocket("ws://" + location.hostname + ":" + location.port + "/vendor");
  let appJSON = null;

  ws.onopen = function(){
    console.log("Websocket open!");
    ws.send("Hello server!");
  };

  ws.onmessage = function(event){
    appJSON = JSON.parse(event.data);
    console.log(appJSON);

    appJSON.vendingMachine.allItems.stock.A = [];

    console.log(appJSON);
  };

})
