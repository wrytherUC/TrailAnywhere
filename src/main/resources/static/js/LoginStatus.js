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
    let email = document.getElementById('email').value.trim();
    let password = document.getElementById('password').value.trim();
    const errorMessage = document.getElementById("loginError");
    const successMessage = document.getElementById("loginSuccess");

    $.post("loginUser", {
       email: email,
       password: password
    }, function(data) {
        console.log(data);
        if (data.userID === 0) {
            showErrorMessage(errorMessage);
        } else {
            // Save name and user ID to session storage
            sessionStorage.setItem("userID", data.userID.toString());
            sessionStorage.setItem("name", data.name.toString());

            // Show success message
            showSuccessMessage(successMessage, errorMessage);

            // Update 'log in' button to 'log out'
            isLoggedIn();
        }
    });

    // Prevent page refresh
    return false;
}

/**
 * Create a new user and log them in
 */
function createUser() {
    const errorMessage = document.getElementById("createError");
    const successMessage = document.getElementById("createSuccess");
    let name = document.getElementById("createName").value.trim();
    let email = document.getElementById("createEmail").value.trim();
    let password = document.getElementById("createPassword").value.trim();
    $.post("createUser", {
        name: name,
        email: email,
        password: password
    }, function(data) {
        if (data.userID === 0) {
            // Show error message
            errorMessage.innerText = "Email already exists.";
            showErrorMessage(errorMessage);
        } else {
            // Save name and user ID to session storage
            sessionStorage.setItem("userID", data.userID.toString());
            sessionStorage.setItem("name", data.name.toString());

            // Show success message
            successMessage.innerText = "Account successfully created.";
            showSuccessMessage(successMessage, errorMessage);

            // Update 'log in' button to 'log out'
            isLoggedIn();
        }
    });

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
