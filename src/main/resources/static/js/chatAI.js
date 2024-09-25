// Function to toggle the chat box visibility
function toggleChat() {
    var chatBox = document.getElementById("chat-box");

    // Check if the chat box is visible or not
    if (chatBox.style.display === "none" || chatBox.style.display === "") {
        chatBox.style.display = "flex";  // Show the chat box
    } else {
        chatBox.style.display = "none";  // Hide the chat box
    }
}

// Function to send the user message to the backend
function sendMessage() {
    var userMessage = document.getElementById("userMessage").value;
    if (userMessage.trim() === "") return; // Prevent sending empty messages

    // Display user's message in the chat window
    displayMessage("You", userMessage);
    document.getElementById("userMessage").value = ""; // Clear input field

    // Send request to the backend
    fetch('/chat', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ message: userMessage }),
    })
        .then(response => response.json())
        .then(data => {
            displayMessage("AI", data.reply); // Display AI response
        })
        .catch(error => {
            displayMessage("Error", "There was a problem with the request.");
            console.error("Error:", error);
        });
}

// Function to display messages in the chat box
function displayMessage(sender, message) {
    var chatMessages = document.getElementById("chat-messages");
    var messageDiv = document.createElement("div");

    // Format message display
    messageDiv.innerHTML = "<strong>" + sender + ":</strong> " + message;
    chatMessages.appendChild(messageDiv);

    // Auto-scroll to the bottom of the chat messages
    chatMessages.scrollTop = chatMessages.scrollHeight;
}

// Add event listener for 'keypress' on the input field
document.getElementById("userMessage").addEventListener("keypress", function(event) {
    // Check if the pressed key is 'Enter'
    if (event.key === "Enter") {
        event.preventDefault(); // Prevent the default action (form submission, if any)
        sendMessage(); // Call the sendMessage function
    }
});
