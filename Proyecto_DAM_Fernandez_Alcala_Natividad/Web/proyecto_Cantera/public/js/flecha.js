// Scroll up

document.getElementById('button-up').addEventListener('click', scrollUp );

function scrollUp(){

    var currentScroll = document.documentElement.scrollTop /* || document.body.scrollTop */;

    if(currentScroll > 0){
        /* window.requestAnimationFrame(scrollUp); */
        window.scrollTo(0, 0);
        /* window.scrollTo(0, currentScroll - (currentScroll / 1000)); */
        // Desaparece de golpe al subir
        /* buttonUp.style.transform = "scale(0)"; */
    }

}

buttonUp = document.getElementById('button-up');

window.onscroll = function() {
    
    var scroll = document.documentElement.scrollTop;

    if(scroll > 300){
        buttonUp.style.transform = "scale(1)";
    } else if (scroll < 300) {
        buttonUp.style.transform = "scale(0)";
    }

}

