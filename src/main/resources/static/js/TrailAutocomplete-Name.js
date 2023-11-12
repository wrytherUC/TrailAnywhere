$(function () {
    $("#floatingInput").autocomplete({
        source: "autocompleteTrailName",
        minLength: 1,
        select: function(event, ui) {
            // Display trail name
            this.value = ui.item.label;
            // Store PK in the hidden ID field, if that field exists
            if ($("#trailID").length !== 0) {
                $("#trailID").val(ui.item.value);
            }
            return false;
        }
    });

    $("#search-form").submit(function (event) {
        event.preventDefault(); // Prevent the form from submitting normally

        // Get the user's input
        var userInput = $("#floatingInput").val();

        // Show the table
        $("#trail-table").show();

        // Hide rows that don't match the user's input
        $("#trail-table tbody tr").each(function () {
            var trailRow = $(this);
            var shouldShow = false;

            // Show the header row
            $("#header-row").show();

            // Check each cell in the row for a match with user input
            trailRow.find("td").each(function () {
                if ($(this).text().toLowerCase().includes(userInput.toLowerCase())) {
                    shouldShow = true;
                    return false; // Break the loop
                }
            });

            if (shouldShow) {
                trailRow.show();
            } else {
                trailRow.hide();
            }
        });
    });
});
