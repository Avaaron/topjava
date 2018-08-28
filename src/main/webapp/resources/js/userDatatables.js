var ajaxUrl = "ajax/admin/users/";
var datatableApi;

function updateUserEnabled(chkbx, id) {
    //var id = $(".user_str").attr('id');
    var enabled = chkbx.is(':checked');
    $.ajax({
        url: ajaxUrl + 'enabled/' + id,
        type: 'POST',
        data: 'enabled=' + enabled,
        success: function () {
            chkbx.closest('tr').toggleClass('disabled');
            successNoty("Статус изменён");
        }
    });
}

function deleteUser() {
    $(".str").click(function () {
        deleteRow($(this).attr("id"));
        updateTable();
    });
    $.ajaxSetup({cache: false});
}

function saveUser() {
    var form = $("#detailsForm");
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $("#editRow").modal("hide");
            updateTable();
            successNoty("Saved");
        }
    });
}


// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
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
   $(':checkbox').each(function () {
        if (!$(this).is(":checked")) {
            $(this).closest('tr').css("text-decoration", "line-through");
        }
    });
});