const ajaxUrl = "ajax/profile/meals/";
let datatableApi;

function updateTable() {
    $.ajax({
        type: "GET",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

$(function () {
    datatableApi = $("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": "",
            saccess: function (data) {
                $.forEach()
            }
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime",
                "render": function (dateTime, type, row) {
                    if (type == 'display') {
                        return '<span>' + dateTime.substring(0, 16).replace("T"," ") + '</span>';
                    }
                    return dateTime;
                }
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {
            if (!data.exceed) {
                $(row).addClass('green');
            } else {
                $(row).addClass('red');
            }
        },
        "initComplete": makeEditable
    });

    $('#startDate').datetimepicker({
        timepicker: false,
        format:'Y-m-d',
        lang: 'ru',
        onShow: function (ct) {
            this.setOptions({
                maxDate: $('#endDate').val() ? $('#endDate').val() : false
            })
        }
    });

    $('#endDate').datetimepicker({
        timepicker: false,
        format:'Y-m-d',
        lang: 'ru',
        onShow: function (ct) {
            this.setOptions({
                minDate: $('#startDate').val() ? $('#startDate').val() : false
            })
        }
    });

    $('#startTime').datetimepicker({
        datepicker: false,
        format:'H:i'
    });

    $('#endTime').datetimepicker({
        datepicker: false,
        format:'H:i'
    });

    $('#dateTime').datetimepicker({
        format:'Y-m-d H:i',
        lang:'ru'
    });
});