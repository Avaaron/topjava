var ajaxUrl = "ajax/meals/";
var datatableApi;

function filter() {
    $.ajax({
        url: ajaxUrl + "filter",
        type: 'POST',
        data: $('#filter').serialize(),
        success: function (data) {
            updateTableByData(data);
            successNoty("filter");
        }
    });
}

function updateTableByData(data) {
    datatableApi.clear().rows.add(data).draw();
}

function cancel() {
    $("#filter")[0].reset();
    updateTable();
}

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [

            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    makeEditable();
});