/**
 * Fetch trails when the page loads
 */
document.addEventListener("DOMContentLoaded", function () {
    getFavoriteTrails();
});

/**
 * Retrieve favorite trails from backend
 */
function getFavoriteTrails() {
    let userID = 0;
    let data = new FormData();
    if (sessionStorage.getItem("userID") !== null) {
        userID = sessionStorage.getItem("userID");
        data.append("userID", userID);
    }
    let xhr = new XMLHttpRequest();
    xhr.open("POST", "/getFavoriteTrails");
    xhr.onload = function() {
        let response = JSON.parse(xhr.responseText);
        showFavoriteTrails(response);
    }
    xhr.send(data);
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