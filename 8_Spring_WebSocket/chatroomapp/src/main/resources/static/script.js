var stompClient=null;

// Function to connect to the server
function connect(){

    // Create a socket
    let socket=new SockJS('/ws')

    // Create a stomp client over the socket
    stompClient=Stomp.over(socket)

    // Connect the stomp client
    stompClient.connect({},function(frame){
        console.log('Connected: '+frame)

        // Hide the name form and show the chat room
        $("#name-from").addClass('d-none')
        $("#chat-room").removeClass('d-none')

        // Subscribe to the public topic
        stompClient.subscribe('/topic/messages',function(response){

            // Parse the response and show the message
            let message=JSON.parse(response.body)
            showMessage(message)
        })
    })
}

// Function to show the message
function showMessage(message){
    $("#message-container-table").prepend(`
        <tr><td><b>${message.name} :</b> ${message.content}</td></tr>`)
}

// Function to send the message
function sendMessage(){
    // Get the name from local storage and the content from the input field
    let name=localStorage.getItem("name")
    let content=$("#message-value").val()

    // Check if content is not empty and stompClient is connected
    if(content && stompClient){

        // Create a message object and send it to the server
        let messageObj={
            name:name,
            content:content
        }
        // Send the message to the server
        stompClient.send("/app/chat",{},JSON.stringify(messageObj))

        // Clear the input field
        $("#message-value").val('')
    }
}

$(document).ready((e)=>{

    // Login button click event
    $("#login-btn").click(()=>{

        // Store the name in local storage
        let name=$("#name-value").val()
        localStorage.setItem("name",name)

        // Set the name title
        $("#name-title").html(`Welcome , <b>${name}</b>`)

        // Connect to the server
        connect();

    })

    // Send button click event
    $("#send-btn").click(()=>{

        // Send the message
        sendMessage();

    })

    // Logout button click event
    $("#logout").click(()=>{
        // Clear the local storage and reload the page
        localStorage.clear();

        if(stompClient!==null){
            stompClient.disconnect();

            // Hide the chat room and show the name form
            $("#name-from").removeClass('d-none')
            $("#chat-room").addClass('d-none')
            // Clear the name input field
            $("#name-value").val('')
            console.log("Disconnected");
        }
//        location.reload();
    })
})