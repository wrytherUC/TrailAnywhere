document.addEventListener("DOMContentLoaded", function () {
    // Populate dropdown menu with a user's alerts for the current trail
    alertDropdown();
});

const addFavoritesButton = document.getElementById("addFavorites");
addFavoritesButton.addEventListener("click", (e) => {
    e.preventDefault();
    addFavoriteTrail();
});

/**
 * Add a new alert
 */
function addTrailAlert() {
    let result = false;
    try {
        // Get trail ID from page link
        const segments = new URL(window.location.href).pathname.split('/');
        const trailID = segments.pop() || segments.pop(); // Handle potential trailing slash

        let userID = 0;
        // Get user ID from session storage
        if (sessionStorage.getItem("userID") !== null) {
            userID = sessionStorage.getItem("userID");
        } else {
            // Redirect to login page
            window.location.href = "/Login";
            return result;
        }

        // Get form data
        const alertText = document.getElementById("alertText").value.trim();
        const alertMessage = document.getElementById("addAlertMessage");

        // Post data to controller
        $.post("/addTrailAlert", {
            trailID: trailID,
            userID: userID,
            alertText: alertText
        }, function(data) {
            console.log(data);
            if (data.alertID !== 0 ) {
                result = true;
                location.reload();
            } else {
                displayMessage(alertMessage, "Failed to add alert.", false);
            }
        });
    } catch(e) {
        console.log(e);
    }
    return result;
}

/**
 * Delete an alert
 */
function deleteTrailAlert() {
    const dropdown = document.getElementById("deleteAlert").value;
    try {
        if (dropdown !== 0) {
            $.post("/deleteAlert", {
                alertID: dropdown
            }, function() {
                location.reload();
            });
        }
    } catch(e) {
        console.log(e);
    }
}

/**
 * Autocomplete a user's alerts
 */
function alertDropdown() {
    const dropdown = document.getElementById("deleteAlert");
    let userID = 0;
    if (sessionStorage.getItem("userID") !== null) {
        userID = sessionStorage.getItem("userID");
    } else {
        // Empty dropdown menu "Login to view your alerts"
        dropdown.options[0] = new Option("Log in to view your alerts.", "0");
        return; // Exit function
    }
    // Get trail ID from page link
    const segments = new URL(window.location.href).pathname.split('/');
    const trailID = segments.pop() || segments.pop(); // Handle potential trailing slash

    try {
        $.post("/getUserAlerts", {
            trailID: trailID,
            userID: userID
        }, function(data) {
            if (data.length !== 0 ) {
                // Populate dropdown menu
                for (let i = 0; i < data.length; i++) {
                    dropdown.options[i] = new Option(data[i].label, data[i].value);
                }
            } else {
                dropdown.options[0] = new Option("You have no alerts for this trail.", "0");
            }
        });
    } catch(e) {
        console.log(e);
    }
}

/**
 * Display errors to the user
 * @param message - message element
 * @param messageText - message text
 * @param result - error or success
 */
function displayMessage(message, messageText, result) {
    if (result) {
        if (message.classList.contains("d-none") || message.classList.contains("alert-danger")) {
            message.classList.remove("d-none");
            message.classList.remove("alert-danger");
            message.classList.add("d-block");
            message.classList.add("alert-success");
            message.innerText = messageText;
        }
    } else {
        if (message.classList.contains("d-none") || message.classList.contains("alert-success")) {
            message.classList.remove("d-none");
            message.classList.remove("alert-success");
            message.classList.add("d-block");
            message.classList.add("alert-danger");
            message.innerText = messageText;
        }
    }

    // Hide message after 5 seconds
    setTimeout(() => {
        if (message.classList.contains("d-block")) {
            message.classList.remove("d-block");
            message.classList.add("d-none");
        }
    }, 5000);
}

/**
 * Add trail to favorites
 */
function addFavoriteTrail() {
    // Get trail ID from page link
    const segments = new URL(window.location.href).pathname.split('/');
    const trailID = segments.pop() || segments.pop(); // Handle potential trailing slash

    // Get user ID
    let userID = 0;
    if (sessionStorage.getItem("userID") !== null) {
        userID = sessionStorage.getItem("userID");
    } else {
        window.location.href = "/Login";
        return;
    }

    const message = document.getElementById("addFavoriteMessage");
    // Post data to controller
    try {
        $.post("/addFavoriteTrail", {
            trailID: trailID,
            userID: userID
        }, function(data) {
            if (data.trailID !== 0) {
                displayMessage(message, "Successfully added this trail to favorites.", true);
            } else {
                displayMessage(message, "Failed to add this trail to favorites.", false);
            }
        });
    } catch(e) {
        console.log(e);
    }
}