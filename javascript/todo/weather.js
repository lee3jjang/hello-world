const API_KEY = 'a0f0936f343bf78fa6a220dcdb95f17d';
const COORDS = 'coords';
const weather = document.querySelector('.js-weather');

function getWeather(lat, lon){
    fetch(`https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lon}&appid=${API_KEY}&units=metric`
    ).then(function(Response){
        return Response.json();  
    }).then(function(json){
        const temp = json.main.temp;
        const place = json.name;
        weather.innerText = `${temp}@${place}`;
    });
}

function askForCoords(){
    navigator.geolocation.getCurrentPosition(handleGeoSucces, handleGeoError, {timeout: 10000});
}

function loadCoords(){
    const loadedCoords = localStorage.getItem(COORDS);
    if(loadedCoords===null){
        askForCoords();
    }else{
        const parsedCoords = JSON.parse(loadedCoords)
    }
}

function handleGeoSucces(position){
    console.log('ss');
    const latitude = position.coords.latitude;
    const longitude = position.coords.longitude;
    const coordsObj = {
        latitude,
        longitude
    };
    saveCoords(coordsObj);
    getWeather(latitude, longitude);
}

function saveCoords(coordsObj){
    localStorage.setItem(COORDS, JSON.stringify(coordsObj));
}

function handleGeoError(position){
    console.log('Can access geo location');
}

function init(){
    loadCoords();
}

init();