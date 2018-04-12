document.addEventListener("DOMContentLoaded", function(){
  console.log("DOM loaded");


  //APP SETUP
  const ws = new WebSocket("ws://" + location.hostname + ":" + location.port + "/vendor");
  let appJSON = null;

  let coinHandler = {
    DOLLAR: 1.00,
    QUARTER: 0.25,
    DIME: 0.10,
    NICKEL: 0.05,
    runningTotal: 0
  }

  ws.onopen = function(){
    console.log("Websocket open!");
    ws.send("Hello server!");
  };
  //EVERY TIME APP.JAVA'S STATE IS UPDATED IT IS RECEIVED HERE AS JSON AND STORED IN APPJSON VARIABLE AT TOP
  ws.onmessage = function(event){
    appJSON = JSON.parse(event.data);
    console.log(appJSON.vendingMachine);
    console.log(appJSON.user);
  };

  //USER INTERFACE/SET UP AND VALIDATE TRANSACTION BEFORE SENDING

  const textDisplay = document.getElementById('text-display')
  const keyButtons = document.getElementsByClassName('key-button');
  const coinButtons = document.getElementsByClassName('coin-button');
  const serviceButton = document.getElementById('service');


  // ADD EVENT LISTENERS TO KEY BUTTONS - SENDS MESSAGE TO SERVER TO UPDATE STATES APPROPRIATELY FOR VALID TRANSACTIIONS
  for(let i = 0; i < keyButtons.length; i++){

    keyButtons[i].addEventListener('click', function(){
      const itemPrice = appJSON.vendingMachine.allItems.stock[keyButtons[i].value][0].price;
      console.log(itemPrice);

      if(coinHandler.runningTotal >= itemPrice){
        ws.send(keyButtons[i].value);
        coinHandler.runningTotal -= itemPrice;

        const valueToDisplay = coinHandler.runningTotal;
        textDisplay.innerHTML = "Amount inserted: " + valueToDisplay.toFixed(2);

      } else {
        textDisplay.innerHTML = "Please insert more coins"

        setTimeout(function(){
          const valueToDisplay = coinHandler.runningTotal;
          textDisplay.innerHTML = "Amount inserted: " + valueToDisplay.toFixed(2);
        }, 1000)
      }
    })
  }

  //ADD EVENT LISTER TO COIN BUTTONS - SENDS COINS THROUGH TO THE SERVER AND UPDATES RUNNING TOTAL ON CLIENT

  for(let i = 0; i < coinButtons.length; i++){

    coinButtons[i].addEventListener('click', function(){
      const coinValue = coinButtons[i].value;
      const convertedValue = coinHandler[coinValue];

      if(appJSON.user.wallet.includes(coinValue)){
        coinHandler.runningTotal += convertedValue;
        ws.send(coinValue);

        const valueToDisplay = coinHandler.runningTotal;
        textDisplay.innerHTML = "Amount inserted: " + valueToDisplay.toFixed(2);
      } else{
        console.log("USER DOES NOT HAVE THIS COIN");
      }
    })
  }

  // SERVICE BUTTON EMPTIES CHANGE AND REFILLS TO INITIAL STATE AND RESTOCKS ITEMS
  serviceButton.addEventListener('click', function(){
    ws.send("SERVICE");
  })
})
