function initializeFontForPDF() {
    pdfMake.fonts = {
        Arabic: {
            normal: 'Cairo-Regular.ttf',
            bold: 'Cairo-Bold.ttf',
            italics: 'Cairo-Regular.ttf.ttf',
            bolditalics: 'Cairo-Regular.ttf.ttf'
        }
    }
}
// initializeFontForPDF();

function prepareOutput(tableName, cols, sentOrReceived) {
    return $(tableName).DataTable({
        language: {
            buttons: {
                pageLength: {
                    '10': "10 سطور",
                    _: "%d سطر",
                    '-1': "كل السطور"
                }
            },
            url: '//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Arabic.json'
        },
        ajax: url,
        serverSide: true,
        processing: true,
        columns: cols,
        order: [[2, 'desc']],
        searchHighlight: true,
        dom: 'Bfrtip',
        lengthMenu: [
            [10, 25, 50, 100, -1],
            ['عرض 10 سطور', 'عرض 25 سطر', 'عرض 50 سطر', 'عرض 100 سطر', 'عرض الكل']
        ],
        buttons: [
            'pageLength',
            {
                extend: "colvis",
                columns: ":gt(1)",
                text: "اظهار/اخفاء"
            },
            {
                text: 'PDF',
                action: function(e, dt, node, config) {
                    var url = '/' + sentOrReceived + '/print';
                    window.open(url, '_blank');
                }
            }
            // {
            //     extend: 'pdfHtml5',
            //     text: "Datatables PDF",
            //     orientation: 'landscape',
            //     pageSize: 'A4',
            //     download: 'open',
            //     exportOptions: {
            //         columns: Array.from(Array(cols.length).keys()).reverse()
            //     },
            //     customize: function (document) {
            //         document.defaultStyle.font = 'Arabic';
            //         var fontSize = document.defaultStyle.fontSize + "px";
            //         console.log(document);
                    // var actualTable = document.content[1].table;
                    // console.log(actualTable);
                    // console.log(actualTable.widths);
                    // actualTable.body.forEach(function (row, index) {
                    //     console.log(actualTable.widths[index]._calcWidth);
                    // });

                    // document.content[1].table.body.forEach(function (item) {
                        // item.forEach(function (stringDocument) {

                            // stringDocument.text = reverseString(stringDocument.text);
                            // stringDocument.text = stringDocument.text.split(" ").reverse().join(" ").split("\n").reverse().join("\n");
                            //
                            // stringDocument.text = stringDocument.text.split(' ').join(String.fromCharCode(160));
                            // stringDocument.alignment = 'right';
                            // console.log(stringDocument.text);
                        // });
                    // });
            //     }
            // }, {
            //     extend: "print",
            //     text: "طباعه",
            //     customize: function(win)
            //     {
            //
            //         var last = null;
            //         var current = null;
            //         var bod = [];
            //
            //         var css = '@page { size: landscape; }',
            //             head = win.document.head || win.document.getElementsByTagName('head')[0],
            //             style = win.document.createElement('style');
            //
            //         style.type = 'text/css';
            //         style.media = 'print';
            //
            //         if (style.styleSheet)
            //         {
            //             style.styleSheet.cssText = css;
            //         }
            //         else
            //         {
            //             style.appendChild(win.document.createTextNode(css));
            //         }
            //
            //         head.appendChild(style);
            //     }
            // }

        ]

    });
}



function showHideDetails(dt, tr) {
    var row = dt.row(tr);
    if (row.child.isShown()) {
        row.child.hide();
        tr.removeClass('shown');
    } else {
        row.child(format(row.data())).show();
        tr.addClass('shown');
    }
}

function searchHighlight(table) {
    table.on("draw.dt", function () {
        var body = $(table.table().body());
        body.unhighlight();
        body.highlight(table.search());
    })
}

function getBasicColumns(sentOrReceived, remainingColumns) {
    var cols = [
        {
            class: "details-control",
            orderable: false,
            data: "year",
            name: "noData",
            render: function (data, type, row, meta) {
                return '';
            }
        },
        {
            data: "number",
            name: "number",
            render: function (data, type, row, meta) {
                return '<a href="/generic/' + row.id + '">' + data + '</a>';
            }
        },
        {
            data: "date",
            name: "date",
            render: function (data) {
                return data[0] + "/" + data[1] + "/" + data[2];
            }

        },
        {data: "year", name: "year"},
        {data: "subject", name: "subject"}

    ];
    cols = cols.concat(remainingColumns);

    return cols;
}

function search(table) {
    $("#min").datepicker();
    $("#max").datepicker();

    $(".search").on('keyup change', function () {
        searchConcept(table, $(this));
    });

    $('.search').each(function() {
        searchConcept(table, $(this));
    });

}

function searchConcept(table, $field) {
    var columnName = $field.attr('id');
    if (columnName === 'min' || columnName === 'max') {
        var column = table.column('date:name');
        var minValue = 'min' + $('#min').val();
        var maxValue = 'max' + $('#max').val();
        var finalValue = minValue + ' ' + maxValue;
        if (column.search() !== finalValue)
            column.search(finalValue).draw();
    } else {
        var column = table.column(columnName + ":name");
        if (column.search() !== $field.val()) {
            column.search($field.val()).draw();
        }
    }
}


!function (e) {
    function n(e, t, r, o) {
        var u;
        if (jQuery.isArray(t)) jQuery.each(t, function (t, u) {
            r || i.test(e) ? o(e, u) : n(e + "[" + ("object" == typeof u ? t : "") + "]", u, r, o)
        }); else if (r || "object" !== jQuery.type(t)) o(e, t); else for (u in t) n(e + "." + u, t[u], r, o)
    }

    var t = /%20/g, i = /\[\]$/;
    e.param = function (e, i) {
        var r, o = [], u = function (e, n) {
            n = jQuery.isFunction(n) ? n() : null == n ? "" : n, o[o.length] = encodeURIComponent(e) + "=" + encodeURIComponent(n)
        };
        if (void 0 === i && (i = jQuery.ajaxSettings && jQuery.ajaxSettings.traditional), jQuery.isArray(e) || e.jquery && !jQuery.isPlainObject(e)) jQuery.each(e, function () {
            u(this.name, this.value)
        }); else for (r in e) n(r, e[r], i, u);
        return o.join("&").replace(t, "+")
    }
}(jQuery);

