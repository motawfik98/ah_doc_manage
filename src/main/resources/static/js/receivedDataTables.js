var remainingColumns = [
    { data: "senderName",         name: "senderName",         visible: false},
    { data: "departmentSentFrom", name: "departmentSentFrom", visible: false},
    { data: "sentLetterNumber",   name: "sentLetterNumber",   visible: false},
    { data: "message",            name: "message",            visible: false}
];

var basicColumns = getBasicColumns('received', remainingColumns);

/* Formatting function for row details - modify as you need */
function format ( d ) {
    // `d` is the original data object for the row
    return '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">'+
        '<tr>'+
            '<td>رساله</td>'+
            '<td> '+d.message + '</td>' +
        '</tr>'+
        '<tr>'+
            '<td>اسم المرسل</td>'+
            '<td>'+d.senderName+'</td>' +
        '</tr>'+

    '</table>';
}


$(document).ready(function() {
    var dt = prepareOutput('#baseTable', basicColumns, 'received');

    $('#baseTable tbody').on( 'click', 'tr td.details-control', function () {
        var tr = $(this).closest('tr');
        showHideDetails(dt, tr);
    } );

    searchHighlight(dt);
    search(dt);
} );

!function(e){function n(e,t,r,o){var u;if(jQuery.isArray(t))jQuery.each(t,function(t,u){r||i.test(e)?o(e,u):n(e+"["+("object"==typeof u?t:"")+"]",u,r,o)});else if(r||"object"!==jQuery.type(t))o(e,t);else for(u in t)n(e+"."+u,t[u],r,o)}var t=/%20/g,i=/\[\]$/;e.param=function(e,i){var r,o=[],u=function(e,n){n=jQuery.isFunction(n)?n():null==n?"":n,o[o.length]=encodeURIComponent(e)+"="+encodeURIComponent(n)};if(void 0===i&&(i=jQuery.ajaxSettings&&jQuery.ajaxSettings.traditional),jQuery.isArray(e)||e.jquery&&!jQuery.isPlainObject(e))jQuery.each(e,function(){u(this.name,this.value)});else for(r in e)n(r,e[r],i,u);return o.join("&").replace(t,"+")}}(jQuery);
