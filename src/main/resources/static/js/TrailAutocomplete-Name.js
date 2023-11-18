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
});
