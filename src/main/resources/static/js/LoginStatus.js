document.addEventListener("DOMContentLoaded", function () {
    // Display 'log in' button if needed
    isLoggedIn();
});

/**
 * Check if a user is logged in
 */
function isLoggedIn() {
    const statusIndicator = document.getElementById("status-indicator");
    const statusText = document.getElementById("status-text");
    let usernameText = document.getElementById("username-text");
    const favorites = document.getElementById("favoritesLink");

    if (sessionStorage.getItem("userID") !== null) {
        // Show logout button
        statusIndicator.style.backgroundColor = "#00FF00"; // Green color for status
        statusText.textContent = "Log Out";
        statusIndicator.addEventListener("click", () => {
            sessionStorage.clear();
        });
        return true;
    } else {
        statusIndicator.style.backgroundColor = "#FF0000"; // Red color for status
        statusText.textContent = "Log In";
        usernameText.textContent = ""; // Clear the username when not logged in

        // Redirect to login page if trying to access favorites page
        favorites.addEventListener("click", () => {
            window.location.href = "/Login";
        });
        return false;
    }
}

/**
 * Send form data to /loginUser endpoint
 */
function loginUser() {
    // Bind form data
    let data = new FormData();
    data.append("email", document.getElementById('email').value.trim());
    data.append("password", document.getElementById('password').value.trim());
    const errorMessage = document.getElementById("loginError");
    const successMessage = document.getElementById("loginSuccess");

    // Submit form data to the /loginUser endpoint
    let xhr = new XMLHttpRequest();
    xhr.open("POST", "/loginUser");
    xhr.onload = function() {
        // Check if endpoint returned valid user
        let response = JSON.parse(xhr.responseText);
        if (response.userID === 0) {
            showErrorMessage(errorMessage);
        } else {
            // Save name and user ID to session storage
            sessionStorage.setItem("userID", response.userID.toString());
            sessionStorage.setItem("name", response.name.toString());

            // Show success message
            showSuccessMessage(successMessage, errorMessage);

            // Update 'log in' button to 'log out'
            isLoggedIn();
        }
    };
    xhr.send(data);

    // Prevent page refresh
    return false;
}

/**
 * Create a new user and log them in
 */
function createUser() {
    const errorMessage = document.getElementById("createError");
    const successMessage = document.getElementById("createSuccess");
    let data = new FormData();
    data.append("name", document.getElementById("createName").value.trim());
    data.append("email", document.getElementById("createEmail").value.trim());
    data.append("password", document.getElementById("createPassword").value.trim());
    let xhr = new XMLHttpRequest();
    xhr.open("POST", "/createUser");
    xhr.onload = function(){
        let response = JSON.parse(xhr.responseText);
        console.log(response);
        if (response.userID === 0) {
            // Show error message
            showErrorMessage(errorMessage);
        } else {
            // Save name and user ID to session storage
            sessionStorage.setItem("userID", response.userID.toString());
            sessionStorage.setItem("name", response.name.toString());

            // Show success message
            showSuccessMessage(successMessage, errorMessage);

            // Update 'log in' button to 'log out'
            isLoggedIn();
        }
    };
    xhr.send(data);

    // Prevent page refresh
    return false;
}

/**
 * Display an error message
 * @param errorMessage
 */
function showErrorMessage(errorMessage) {
    // Show error message
    if (errorMessage.classList.contains("d-none")) {
        errorMessage.classList.remove("d-none");
        errorMessage.classList.add("d-block");
    }

    // Remove message after 5 seconds
    setTimeout(() => {
        errorMessage.classList.remove("d-block");
        errorMessage.classList.add("d-none");
    }, 5000);
}

/**
 * Show success message
 * @param successMessage - success message
 * @param errorMessage - error message
 */
function showSuccessMessage(successMessage, errorMessage) {
    // Show success message
    if (successMessage.classList.contains("d-none")) {
        successMessage.classList.remove("d-none");
        successMessage.classList.add("d-block");
    }

    // Remove message after 5 seconds
    setTimeout(() => {
        successMessage.classList.remove("d-block");
        successMessage.classList.add("d-none");
    }, 5000);

    // Remove error message if needed
    if (errorMessage.classList.contains("d-block")) {
        errorMessage.classList.remove("d-block");
        errorMessage.classList.add("d-none");
    }
}
