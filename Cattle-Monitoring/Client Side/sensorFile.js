window.onload = function() {
}

let map; 
var markers = [];
var infoWindow;
var iconRed="circle.png";
var iconBlue="blueDot.png"

window.onload= () => {

  displayAnimals();
};

function initMap() {
    
  var farm = {
    lat: 39.72924761521451, 
   lng: 21.686525892514542
 };


 map = new google.maps.Map(document.getElementById('map'), {
    center: farm,
    zoom: 21,
    mapTypeId: "roadmap",
 });

 var marker= new google.maps.Marker({
  position:farm,
  icon: iconBlue,
  title:  "Farm"
  });
  //var html="<b>" +farm+"</b> + <br/>";
  marker.setMap(map);
  google.maps.event.addListener(marker,'click',function(){
    infoWindow.setContent(html);
    infoWindow.open(map,marker);
  });

  infoWindow = new google.maps.InfoWindow();
  //showAnimalsMarkers();

  }


console.log(animals);



function displayAnimals(){

  var animalsHtml='';
  for (var animal of animals){
    var X=animal.sensor.location['x'];
    var Y=animal.sensor.location['y'];
    console.log(X);
    console.log(Y);
    var id= animal['id'];
    console.log(id);
    animalsHtml += `
    <div> animals </div>

    `
    //document.querySelector('.animals-list').innerHTML=animalsHtml;
    
    var cow= {
      lat: Y, 
    lng: X
    
    };

  }

}

function showAnimalsMarkers(){
  var bounds = new google.maps.LatLngBounds();//for zoom in red dots
  for (var animal of animals){
    var coordinateX=animal.sensor.location['x'];
    var coordinateY=animal.sensor.location['y'];
    var id= animal.sensor['id'];
    var milkProduct= animal['milkProduction']; 
    var noMastitis= animal.sensor['normalRumination'];
    var latlng = new google.maps.LatLng(coordinateY,coordinateX);
    var herd= animal['herd'];
    console.log("x="+coordinateX);
    console.log("y="+coordinateY);
    console.log("latlng="+latlng);
    console.log(id);
    bounds.extend(latlng); // for zoom in red dots
    createMarker(id,latlng,milkProduct,herd,noMastitis);
  }
  map.fitBounds(bounds); // for zoom in red dots


}

function createMarker(id,latlng, milkProduct,herd,noMastitis){
  //var html="<b>" + id + "</b> <br/>"+ latlng;

  //new 
  var html=
  `
        <div class="animal-info-window">
            <div class="animal-info-name">
                ${id}
            </div>
           
            <div class="animal-info-rumination">
                
                ${noMastitis}
            </div>
            <div class="animal-info-milk">
              ${milkProduct}
            </div>
            <div class="animal-info-herd">
                
                ${herd}
            </div>
        </div>
    `;




  //end new


  var marker = new google.maps.Marker({
    map:map,
    position:latlng,
    //label: id.toString(),
    icon: iconRed
  });

  google.maps.event.addListener(marker,'click',function(){
    infoWindow.setContent(html);
    infoWindow.open(map,marker);
  });
  markers.push(marker);



}