$(document).ready(function () {
    $("#imageButton").on("click", function () {
        $(".collapse").removeClass('show');
        var url = "/" + type + "/" + letterId + "/images";
        var $imageContainer = $("#imagesContainer");
        $imageContainer.html("");
        $.get(url, function (data) {
            var classes = "col-sm-6 p-3";
            if (data.deleted) classes += " d-none deleted";

            data.forEach(function (item, index) {
                var type = (item.letter.sentLetter) ? "sent" : "received";

                var firstDiv = $('<div>', {
                    class : classes
                }).appendTo($imageContainer);
                var cardDiv = $('<div>', {
                    class : 'card'
                }).appendTo(firstDiv);
                var cardBody = $('<div>', {
                    class : 'card-body'
                }).appendTo(cardDiv);

                $('<h5>', {
                    text: item.path
                }).appendTo(cardBody);

                $('<a>', {
                    class: "btn btn-primary m-1",
                    text: "اظهار",
                    target: "_blank",
                    href: "/" + type + "/" + item.letter.id + "/images/" + item.id
                }).appendTo(cardBody);
                $('<a>', {
                    class: "btn btn-primary m-3",
                    text: "مسح",
                    href: "#"
                }).appendTo(cardBody);

                console.log(item);
            });
        });
        $(this).addClass('show');
    });

    var button = $("#showHideButton");
    button.on("click", function () {
        $(".deleted").toggleClass("d-none");
        var text = button.text();
        button.text(
            text === "اظهار" ? "اخفاء" : "اظهار"
        );
    });
});
