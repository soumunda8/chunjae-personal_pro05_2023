(function ($) {
    "use strict";

    // Spinner
    var spinner = function () {
        setTimeout(function () {
            if ($('#spinner').length > 0) {
                $('#spinner').removeClass('show');
            }
        }, 1);
    };
    spinner();

    // Sticky Navbar
    $(window).scroll(function () {
        if ($(this).scrollTop() > 300) {
            $('.sticky-top').addClass('bg-primary shadow-sm').css('top', '0px');
        } else {
            $('.sticky-top').removeClass('bg-primary shadow-sm').css('top', '-150px');
        }
    });


    // Facts counter
    $('[data-toggle="counter-up"]').counterUp({
        delay: 10,
        time: 2000
    });


    // Back to top button
    $(window).scroll(function () {
        if ($(this).scrollTop() > 100) {
            $('.back-to-top').fadeIn('slow');
        } else {
            $('.back-to-top').fadeOut('slow');
        }
    });
    $('.back-to-top').click(function () {
        $('html, body').animate({scrollTop: 0}, 1500, 'easeInOutExpo');
        return false;
    });

})(jQuery);

function findAddr(){
    new daum.Postcode({
        oncomplete:function(data){
            var roadAddr = data.roadAddress;
            var jibunAddr = data.jibunAddress;
            document.getElementById("postcode").value = data.zonecode;
            if(roadAddr !== ''){
                document.getElementById("address1").value = roadAddr;
            } else if(jibunAddr !== ''){
                document.getElementById("address1").value = jibunAddr;
            }
            document.getElementById("address2").focus();
        }
    }).open();
}