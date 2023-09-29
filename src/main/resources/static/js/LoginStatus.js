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

/* Still need to make a logout button - only showing when the user is currently logged in - when clicked will switch boolean or kill cookie/session */
/* Also need to make favorites page only accessible if isLoggedIn is true or other logic proves the user is logged in via SQL database connection string */
