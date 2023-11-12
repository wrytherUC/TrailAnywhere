/**
 * Validate the create trail form
 */
function validateForm() {
    const trailInputs = document.getElementsByClassName("form-control");
    let validated = true;
    try {
        // Check for integer input only for zip codes and coordinates
        for (let i = 0; i < trailInputs.length; i++) {
            if (trailInputs[i].id === "trailZipCode" || trailInputs[i].id === "trailLongitude" || trailInputs[i].id === "trailLatitude") {
                if (!Number.isInteger(parseInt(trailInputs[i].value.trim()))) {
                    console.log(trailInputs[i].id + ": " + trailInputs[i].value);
                    validated = false;
                    displayMessage(false, "Please enter numbers only for zip code and coordinates.");
                }
            }
        }

        // If form is validated, save the trail
        if (validated === true) {
            if (createTrail() === true) {
                // Prevent page refresh
                validated = false;
            }
        }
    } catch(e) {
        console.log(e);
    }
    return validated;
}

/**
 * Create a new trail
 */
function createTrail() {
    // Get form data
    const name = document.getElementById("trailName").value.trim();
    const type = document.getElementById("trailType").value.trim();
    const terrain = document.getElementById("trailTerrain").value.trim();
    const difficulty = document.getElementById("trailDifficulty").value.trim();
    const address = document.getElementById("trailAddress").value.trim();
    const zipCode = document.getElementById("trailZipCode").value.trim();
    const latitude = document.getElementById("trailLatitude").value.trim();
    const longitude = document.getElementById("trailLongitude").value.trim();
    let success = false;

    // API post to create a trail and display message
    try {
        $.post("trail", {
            name: name,
            trailType: type,
            terrain: terrain,
            difficulty: difficulty,
            address: address,
            zipCode: zipCode,
            latitude: latitude,
            longitude: longitude
        }, function(data) {
            console.log(data);
            if (data.trailID === 0) {
                displayMessage(false, "Error creating trail.");
            } else {
                displayMessage(true, "Successfully created trail.");
                success = true;
            }
        });
    } catch(e) {
        console.log(e);
    }

    return success;
}

/**
 * Display an error or success message
 * @param result - boolean to trigger success or error message
 * @param messageText - message text
 */
function displayMessage(result, messageText) {
    const message = document.getElementById("trailMessage");
    if (result) {
        if (message.classList.contains("d-none")) {
            message.classList.remove("d-none");
            message.classList.add("d-block");
            message.classList.add("alert-success");
            message.innerText = messageText;
        }
    } else {
        if (message.classList.contains("d-none")) {
            message.classList.remove("d-none");
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