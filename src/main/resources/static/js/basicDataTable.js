$(document).ready(function () {

    var myTable = $('#baseTable').DataTable( {
        language: {
            url: '//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Arabic.json'
        },
        processing: true,

        searchHighlight: true
    } );
    myTable.on("draw.dt", function () {
        var body = $( myTable.table().body() );
         body.unhighlight();
         body.highlight( myTable.search() );
    })
});

