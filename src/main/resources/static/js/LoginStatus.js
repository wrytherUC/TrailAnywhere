document.addEventListener("DOMContentLoaded", function () {
    const statusIndicator = document.getElementById("status-indicator");
    const statusText = document.getElementById("status-text");
    const usernameText = document.getElementById("username-text"); // Added for username

    // Replace this condition with your logic to determine if the user is logged in
    const isLoggedIn = true; // Change this condition as needed

    if (isLoggedIn) {
        const username = "JohnDoe"; // Replace with the actual username
        statusIndicator.style.backgroundColor = "#00FF00"; // Green color for status
        statusText.textContent = "Logged In";
        usernameText.textContent = `As: ${username}`; // Display the username
    } else {
        statusIndicator.style.backgroundColor = "#FF0000"; // Red color for status
        statusText.textContent = "Logged Out";
        usernameText.textContent = ""; // Clear the username when not logged in
    }
});
