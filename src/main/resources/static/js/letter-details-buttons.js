$(document).ready(function () {

    var button = $("#showHideButton");
    button.on("click", function () {
        $(".deleted").toggleClass("d-none");
        var text = button.text();
        button.text(
            text === "اظهار" ? "اخفاء" : "اظهار"
        );
    });

    $('#inputFile').on('change',function(){
        //get the file name
        var fileName = $(this).val().replace('C:\\fakepath\\', " ");

        //replace the "Choose a file" label
        $(this).next('.custom-file-label').html(fileName);
    })
});
