$(document).ready(function () {
    $('form').on('submit', function (e) {
        e.preventDefault();
        $('tr.success').removeClass('success');
        $('tr.danger').removeClass('danger');
        var $form = $(this);
        $.get($form.attr('action'), $form.serialize(), function (data) {
            if (data == 'ok') {
                $form.parents('tr').addClass('success');
            } else {
                $form.parents('tr').addClass('danger');
            }
        });
    });

    var russian = {
        "processing": "Подождите...",
        "search": "Поиск:",
        "lengthMenu": "Показать _MENU_ записей",
        "info": "Записи с _START_ до _END_ из _TOTAL_ записей",
        "infoEmpty": "Записи с 0 до 0 из 0 записей",
        "infoFiltered": "(отфильтровано из _MAX_ записей)",
        "infoPostFix": "",
        "loadingRecords": "Загрузка записей...",
        "zeroRecords": "Записи отсутствуют.",
        "emptyTable:": "В таблице отсутствуют данные",
        "paginate": {
            "first": "Первая",
            "previous": "<span><i class='glyphicon glyphicon-arrow-left'></i></span>",
            "next": "<span><i class='glyphicon glyphicon-arrow-right'></i></span>",
            "last": "Последняя"
        },
        "aria": {
            "sortAscending": ": активировать для сортировки столбца по возрастанию",
            "sortDescending": ": активировать для сортировки столбца по убыванию"
        }
    };
    $('.table').DataTable({
        "language": russian,
        "pagingType": "simple_numbers",
        "lengthMenu": [[25, 50, -1], [25, 50, "All"]],
        "order": [[ 1, 'asc' ], [ 0, 'asc' ]]
    });
});