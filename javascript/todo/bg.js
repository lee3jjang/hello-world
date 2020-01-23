const body = document.querySelector('body');

const IMG_NUMBER=4;

function paintImage(imgNumber){
    const image = new Image();
    image.src = `images/${imgNumber+1}.jpg`;
    image.classList.add('bgImage');
    body.appendChild(image);
}

function init(){
    const randomNumber = Math.floor(Math.random()*IMG_NUMBER);
    paintImage(randomNumber);
}

init();