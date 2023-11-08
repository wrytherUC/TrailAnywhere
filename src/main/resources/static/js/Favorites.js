if (window.location.href.includes("/Favorites")) {
    getFavoriteTrails();
}

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
        if (response.length > 0) {
            console.log("Trail: " + response[0].name);
            showFavoriteTrails(response);
        }
    }
    xhr.send(data);
}

/**
 * Display a user's favorite trails
 */
function showFavoriteTrails(response) {

}