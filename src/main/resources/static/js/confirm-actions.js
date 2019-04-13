$(document).ready(function () {
    $("#deleteLetter").on('submit', function (e) {
        e.preventDefault();
        swal({
            title: "هل انت متأكد ؟",
            text: "لن تستطيع استرجاع السجل",
            icon: "warning",
            buttons: true,
            dangerMode: true
        }).then((isConfirm) => {
            if (isConfirm) {
                this.submit();
            } else
                swal("تم الألغاء", "تم الغاء العمليه", "error");
    })
    });

    $("#redirectLetter").on('click', function (e) {
        e.preventDefault();
        swal({
            title: "حفظ البيانات",
            text: "سوف يتم تجاهل اي تغييرات - هل انت متأك من الحفظ ؟",
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