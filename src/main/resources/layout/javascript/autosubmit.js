$(document).ready(function(){
    var input = $("#auto_submit_item");
    input.keyup(function () {
        $(this).parents("#form").submit();
    });
    var len = input.val().length;
    input[0].focus();
    input[0].setSelectionRange(len, len);
});
