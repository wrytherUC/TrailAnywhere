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
            } else {
                displayMessage(alertMessage, "Failed to add alert.");
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
    // Get trail ID

    // Post data to controller
}

/**
 * Autocomplete a user's alerts
 */
function alertAutocomplete() {
    // Get a user's alerts and populate it into textboxes
}

/**
 * Display errors to the user
 * @param message - message element
 * @param messageText - message text
 */
function displayMessage(message, messageText) {
    // Unhide message element

    // Set properties to display

    // Hide after 5 seconds
}

/**
 * Add trail to favorites
 */
function addFavoriteTrail() {
    // Get trail ID

    // Get user ID

    // Post data to controller

    // Display success message
}