/**
 * Fetch trails when the page loads
 */
document.addEventListener("DOMContentLoaded", function () {
    getFavoriteTrails();
    autocomplete();
});

/**
 * Retrieve favorite trails from backend
 */
function getFavoriteTrails() {
    try {
        let userID = sessionStorage.getItem("userID");
        $.post("getFavoriteTrails", {
            userID: userID
        }, function(data, success) {
            showFavoriteTrails(data);
            console.log(data);
        });
    } catch(e) {
        console.log(e);
    }
}

/**
 * Display a user's favorite trails onto the table
 */
function showFavoriteTrails(response) {
    const favoritesTable = document.getElementById("favoritesTable");
    if (response.length > 0) {
        for (let i = 0; i < response.length; i++) {
            let row = favoritesTable.insertRow();
            let name = row.insertCell(0);
            let type = row.insertCell(1);
            let difficulty = row.insertCell(2);
            let address = row.insertCell(3);
            let zipCode = row.insertCell(4);
            name.innerText = response[i].name;
            type.innerText = response[i].trailType;
            difficulty.innerText = response[i].difficulty;
            address.innerText = response[i].address;
            zipCode.innerText = response[i].zipCode;
        }
    } else {
        let row = favoritesTable.insertRow();
        let noTrails = row.insertCell(0);
        noTrails.colSpan = "5";
        noTrails.innerText = "You have no favorite trails.";
    }
}

/**
 * Add a favorite trail
 */
function addTrail() {
    let userID = sessionStorage.getItem("userID");
    let trailID = document.getElementById("addTrailID").value;
    try {
        $.post("addFavoriteTrail", {
            trailID: trailID,
            userID: userID
        }, function(data, success) {
            console.log("Trails: " + data);
        });
    } catch(e) {
        console.log(e);
    }
}

/**
 * Autocomplete trails for favoriting
 */
function autocomplete() {
    $("#addFavoriteTrail").autocomplete({
        source: "autocompleteTrailName",
        minLength: 1,
        select: function(event, ui) {
            // Display trail name
            this.value = ui.item.label;
            // Store PK in the hidden ID field, if that field exists
            $("#addTrailID").val(ui.item.value);
            return false;
        }
    });
    $("#deleteFavoriteTrail").autocomplete({
        source: function(request, response) {
            $.post("autocompleteFavoriteTrails", {
                userID: sessionStorage.getItem("userID"),
                term: request.term
            }, function(data) {
                response(data);
            });
        },
        minLength: 1,
        select: function(event, ui) {
            // Display trail name
            this.value = ui.item.label;
            // Store PK in the hidden ID field, if that field exists
            $("#deleteTrailID").val(ui.item.value);
            return false;
        }
    });
}

/**
 * Delete a favorite trail
 */
function deleteTrail() {
    let userID = sessionStorage.getItem("userID");
    let trailID = document.getElementById("deleteTrailID").value;
    try {
        $.post("deleteFavoriteTrail", {
            userID: userID,
            trailID: trailID
        });
        return false;
    } catch(e) {
        console.log(e);
    }
}