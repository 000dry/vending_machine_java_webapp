document.addEventListener("DOMContentLoaded", function(){
  console.log("DOM loaded");


  //APP SETUP
  const protocol = location.hostname == 'vending-machine-app.herokuapp.com' ? "wss://" : "ws://";

  const ws = new WebSocket(protocol + location.hostname + ":" + location.port + "/vendor");
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
    setItemNamesAndPrice();
    setProductStockCount();
    setCoinCount();
  };

  //USER INTERFACE/SET UP AND VALIDATE TRANSACTION BEFORE SENDING

  const textDisplay = document.getElementById('text-display')
  const keyButtons = document.getElementsByClassName('key-button');
  const coinButtons = document.getElementsByClassName('coin-button');
  const returnButton = document.getElementById('return');
  const serviceButton = document.getElementById('service');
  const vendedItem = document.getElementById('item-vended');
  const newSessionButton = document.getElementById('new-session')

  // ADD EVENT LISTENERS TO KEY BUTTONS - SENDS MESSAGE TO SERVER TO UPDATE STATES APPROPRIATELY FOR VALID TRANSACTIIONS
  for(let i = 0; i < keyButtons.length; i++){

    keyButtons[i].addEventListener('click', function(){
      const itemPrice = getProductPrice(keyButtons[i].value);
      const itemName = getProductName(keyButtons[i].value);

      if(coinHandler.runningTotal >= itemPrice){
        ws.send(keyButtons[i].value);
        coinHandler.runningTotal -= itemPrice;

        const valueToDisplay = coinHandler.runningTotal;
        textDisplay.innerHTML = "Amount inserted: " + valueToDisplay.toFixed(2);

        if(itemName != "NO UPDATE"){
          vendedItem.innerHTML = itemName;
        }
      } else {
        textDisplay.innerHTML = "Please insert more coins"

        setTimeout(function(){
          const valueToDisplay = coinHandler.runningTotal;
          textDisplay.innerHTML = "Amount inserted: " + valueToDisplay.toFixed(2);
        }, 3000)
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

  //RETURN BUTTON CAN RETURN APPROPRIATE NUMBER AND VALUE OF COINS BEFORE OR AFTER TRANSACTION

  returnButton.addEventListener('click', function(){
    coinHandler.runningTotal = 0;
    textDisplay.innerHTML = "0.00";
    ws.send(returnButton.value);
    setTimeout(function(){
      textDisplay.innerHTML = "Insert Coins To Begin";
    }, 3000)
  })

  // SERVICE BUTTON EMPTIES CHANGE AND REFILLS TO INITIAL STATE AND RESTOCKS ITEMS
  serviceButton.addEventListener('click', function(){
    ws.send(serviceButton.value);
  })

  //NEW SESSION BUTTON TELLS MESSAGE HANDLER TO CREATE NEW APP

  newSessionButton.addEventListener('click', function(){
    ws.send(newSessionButton.value);
    coinHandler.runningTotal = 0;
    textDisplay.innerHTML = "Insert Coins To Begin";

  })

  //SET TEXT FOR ITEM DISPLAYS

  const getProductName = function(key){
    const stock = appJSON.vendingMachine.allItems.stock[key];

    if(stock.length != 0){
      return stock[0].productName;
    } else {
      return "NO UPDATE";
    }
  }

  const getProductPrice = function(key){
    const stock = appJSON.vendingMachine.allItems.stock[key];

    if(stock.length != 0){
      return stock[0].price;
    } else {
      return "NO UPDATE"
    }
  }

  const setItemNamesAndPrice = function(){
    const itemTexts = document.getElementsByClassName('item-name');
    const itemNames = [getProductName("A"), getProductName("B"), getProductName("C")];
    const itemPrices = [getProductPrice("A"), getProductPrice("B"), getProductPrice("C")];

    for(i = 0; i < itemTexts.length; i++){
      if(itemNames[i] != "NO UPDATE"){
        itemTexts[i].innerHTML = itemNames[i] + ": $" + itemPrices[i].toFixed(2);
      }
    }
  }

  const getProductStockCount = function(key){
    return appJSON.vendingMachine.allItems.stock[key].length;
  }

  const setProductStockCount = function(){
    const itemTexts = document.getElementsByClassName('item-stock');
    const itemStock = [getProductStockCount("A"), getProductStockCount("B"), getProductStockCount("C")];

    for(i = 0; i < itemTexts.length; i++){
      itemTexts[i].innerHTML = itemStock[i];
    }
  }

  const getCoinCount = function(coin){
    return appJSON.user[coin];
  }

  const setCoinCount = function(){
    const walletTexts = document.getElementsByClassName('wallet');
    const coinCounts = [getCoinCount("dollars"),getCoinCount("quarters"),getCoinCount("dimes"),getCoinCount("nickels")];

    for(i = 0; i < walletTexts.length; i++){
      walletTexts[i].innerHTML = walletTexts[i].title + "s: " + coinCounts[i];
    }
  }

})
