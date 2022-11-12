var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#updates").html("");
}

function connect() {
    var socket = new SockJS('/pizzeria-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic', function (customer) {
            showCustomer(JSON.parse(customer.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function showCustomer(update) {
    const otherCols = 4;
    let pizza = update.pizzas.length > 0 
        ? "<td>" + update.pizzas[0].pizzaId + 
            "</td><td>" + update.pizzas[0].state + 
            "</td><td>" + update.pizzas[0].cookId + "</td>" 
        : "<td></td><td></td><td></td>" ;
    $("#updates").append(
        "<tr><td>" + update.orderId + 
        "</td><td>" + update.customerId + 
        "</td><td>" + update.queueId +  
        "</td><td>" + update.state +  
        "</td>" + pizza + "</tr>"
    );
    let row;
    const tdO = "<td>";
    const tdC = "</td>";
    for (let i = 1; i < update.pizzas.length; i++) {
        row = "<tr" + (i == update.pizzas.length - 1 
            ? ' style="border-bottom: 2px solid #ddd;">' 
            : ">");
        for (let j = 0; j < otherCols; j++)
            row += tdO + tdC;
        row += tdO + update.pizzas[i].pizzaId + tdC;
        row += tdO + update.pizzas[i].state + tdC;
        row += tdO + update.pizzas[i].cookId + tdC;
        row += "</tr>"
        $("#updates").append(row);
    }
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
});