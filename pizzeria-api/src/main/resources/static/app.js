var stompClient = null;

function setConnected(connected) {
    $("#connect-nc").prop("disabled", connected);
    $("#connect-uc").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#customers").html("");
}

function connectNC() {
    var socket = new SockJS('/pizzeria-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/customer/new', function (customer) {
            showCustomer(JSON.parse(customer.body));
        });
    });
}

function connectUC() {
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

function sendRequest() {
    $.ajax({
        type: "POST",
        url: "/pizzeria/order",
        data: "{customerId:" + $("#cid").val() + "}"
    });
    //stompClient.send("/pizzeria/orderFor", {}, JSON.stringify({'cid': $("#cid").val()}));
}

function showCustomer(customer) {
    $("#customers").append("<tr><td>" + customer.pizzas + "</td><td>" + customer.customerId + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect-nc" ).click(function() { connectNC(); });
    $( "#connect-uc" ).click(function() { connectUC(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendRequest(); });
});