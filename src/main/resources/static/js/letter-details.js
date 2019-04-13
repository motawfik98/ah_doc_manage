$(document).ready(function () {

    var myTable = $('#baseTable').DataTable({
        language: {
            url: '//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Arabic.json'
        },
        pageLength: 5,
        lengthMenu: [5],
        ajax: "dummy/url",
        serverSide: true,
        processing: true,
        deferLoading: 0,
        columns: [
            {
                data: "date",
                name: "noData",
                orderable: false,
                render: function (data, type, row, meta) {
                    return '<input type="radio"/ name="selected"/ value="' + row.id + '"/>'
                }
            },
            {data: "number", name: "number"},
            {data: "date", name: "date"},
            {data: "subject", name: "subject"},
            {data: "message", name: "message"}
        ],
        columnDefs: [
            {"searchable": false, "targets": [2, 3, 4]}
        ],

        searchHighlight: true
    });
    myTable.on("draw.dt", function () {
        var body = $(myTable.table().body());
        body.unhighlight();
        body.highlight(myTable.search());
    });

    $('#inputFile').on('change', function () {
        //get the file name
        var fileName = $(this).val().replace('C:\\fakepath\\', " ");

        //replace the "Choose a file" label
        $(this).next('.custom-file-label').html(fileName);
    });

    $("#linkSent").on("click", function (e) {
        var currentUrl = myTable.ajax.url();
        if (currentUrl !== "/data/select-sent-letters") {
            e.stopPropagation();
            $("#links").addClass("show");
            myTable.ajax.url("/data/select-sent-letters").load();
        }
    });
    $("#linkReceived").on("click", function (e) {
        var currentUrl = myTable.ajax.url();
        if (currentUrl !== "/data/select-received-letters") {
            e.stopPropagation();
            $("#links").addClass("show");
            myTable.ajax.url("/data/select-received-letters").load();
        }
    });
    $("#file-upload").on('change', function () {
        $("#file-form").submit();
    });
    $(".deleteImageForm").on('submit', function (e) {
        e.preventDefault();
        swal({
            title: "هل انت متأكد ؟",
            text: "لن تستطيع استرجاع الصوره",
            icon: "warning",
            buttons: true,
            dangerMode: true
        }).then((isConfirm) = > {
            if(isConfirm) {
                this.submit();
            } else
                swal("تم الألغاء", "تم الغاء العمليه", "error");
    })
    });

    $(".editArchive").on('click', function (e) {
        e.preventDefault();
        swal({
            title: "انتبه",
            text: "سوف يتم توجيهك الى احدث نسخه من الخطاب للتعديل",
            icon: "warning",
            buttons: true,
            dangerMode: true
        }).then((isConfirm) => {
            if (isConfirm) {
                window.location.href = $(this).attr('href');
            } else
                swal("تم الألغاء", "تم الغاء العمليه", "error");
    })

    })
});

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