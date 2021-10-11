let map; 
var markers = [];
var infoWindow;
var iconRed="circle.png";
var iconBlue="blueDot.png";
var iconBlack="squareBlack.png";
var iconOrange="squareOrange.png"
var iconWhite="squareWhite.png"
var smallCow="cowSmall.png";

//var data;


window.onload= () => {

  getFile();
  getGates();
 
  setInterval(myMethod, 10000);

  function myMethod( )
    {
        getFile();
        getGates();
    }


  setInterval(removeMethod,9980);
  function removeMethod( )
    {
      DeleteMarkers();
    }
 
  console.log("onload");
  
};

async function getFile(){  
  const response =await   fetch('http://localhost//CattleMonitoring/accessAnimals', {mode:'cors'},{headers : { 
    'Content-Type': 'json/aplication'}   }
    )
  .then(res => res.json()
  )
  .then((data)=>
  {
    for( var i in data){
      myArray=data[i];
     
      }
      
      getTable();
      
      
  })  
  }

function getTable(){
  console.log("get table function called");
  console.log(myArray);
  console.log("---------------");
  displayAnimals();
  console.log("+++++++++++++++++");
  showAnimalsMarkers();


}





function initMap() {

  var farm = {
    lat: 39.72924761521451, 
   lng: 21.686525892514542
 };


 map = new google.maps.Map(document.getElementById('map'), {
    center: farm,
    zoom: 19,
    mapTypeId: "satellite",
 });
  
 
 const oriaCoordinates = [
    {lat:39.737778822842465, lng:21.683457467236163},
    {lat:39.739610351931105, lng:21.681054208057304},
    {lat:39.736986828965335, lng:21.67504604376334},
    {lat:39.733439093236036, lng:21.676161842710858},
    {lat:39.73069978634819, lng:21.67753513364131},
    { lat: 39.72812539866613, lng: 21.67755659094211},
    {lat:39.726111964989464, lng:21.678629435378756},
    {lat:39.72535281017118, lng:21.68178371298463},
    {lat:39.72424706969791, lng:21.684036768655627},
    { lat: 39.725121824818224, lng:  21.693220690862415 },
    {lat:39.73048522485807, lng:21.689594314899534},
    {lat:39.733835051325855, lng:21.68573201111885},
    {lat:39.737778822842465, lng:21.683457467236163},
  
];

const oria = new google.maps.Polyline({
  path: oriaCoordinates,
  geodesic: true,
  strokeColor: "#0000FF",
  strokeOpacity: 1.0,
  strokeWeight: 2,
});
oria.setMap(map);
// end new code


 var marker= new google.maps.Marker({
  position:farm,
  icon: iconBlue,
  title:  "Farm"
  });
  
  marker.setMap(map);
  google.maps.event.addListener(marker,'click',function(){
    infoWindow.setContent(html);
    infoWindow.open(map,marker);
  });

  infoWindow = new google.maps.InfoWindow();
 

  }

  async function getGates(){  
    const response =await   fetch('http://localhost//CattleMonitoring/accessGates', {mode:'cors'},{headers : { 
   'Content-Type': 'json/aplication'}   }
    ).then(res => res.json()
      ).then((data)=>
       {
      for( var i in data){
  myGates=data[i];
   }})  
   console.log(myGates);
  
   displayGates();
   showGateMarkers();
}           




function displayAnimals(){
  
  var animalsHtml='';
  for (var animal of myArray){
    
    var X=animal.sensor.location['x'];
    var Y=animal.sensor.location['y'];
    console.log("display animals function x"+X);
    console.log("display animals function"+Y);
    console.log(X);
    console.log(Y);
    var id= animal['id'];
    
    
    if((animal.sensor["secure"]==false) || (animal.sensor["attention"] ==true)){
      if(animal.sensor["secure"]==false) 
        animal.reason="την ασφάλειά του.";
      if(animal.sensor["attention"] ==true){  
          if(animal.sensor["heartRate"]<40) 
            animal.reason="την υγεία του. Οι παλμοί του είναι χαμηλοί.";
          if(animal.sensor["heartRate"]>84) 
            animal.reason="την υγεία του. Οι παλμοί του είναι υψηλοί.";
          if(animal.sensor["temp"]<38) 
            animal.reason="την υγεία του. Η θερμοκρασία του είναι χαμηλή.";
          if(animal.sensor["temp"]>39.5) 
            animal.reason="την υγεία του. Η θερμοκρασία του είναι υψηλή.";
        //kane if gia low- high limits in pressure and tempf(temp<38.0temp>39.5hr<40||hr>84)

      }
      console.log("there is a problem with cow: "+animal.id);
      
      alert("Το ζώο με αριθμό(id) " + animal.id+ " χρειάζεται προσοχή για "+ animal.reason);
    }

    
    console.log(id);
    animalsHtml += `
    <div> animals </div>

    `
    
    
    var cow= {
      lat: Y, 
    lng: X};

  }

}

function displayGates(){
  
  var gatesHtml='';
  for (var gate of myGates){
    
    var gateX=gate.location['x'];
    var gateY=gate.location['y'];
    console.log("display gate x"+gateX);
    console.log("display gate function"+gateY);
    console.log(gateX);
    console.log(gateY);
    var gateId= gate['id'];

    console.log(gateId);
    gatesHtml += `
    <div> gates </div>

    `
    
    
    var gateL= {
      lat: gateY, 
    lng: gateX};

  }
  
} 

function showAnimalsMarkers(){
  
  
  var bounds = new google.maps.LatLngBounds();//for zoom in red dots
  for (var animal of myArray){
    var coordinateX=animal.sensor.location['x'];
    var coordinateY=animal.sensor.location['y'];
    var id= animal['id'];
    var milkProduct= animal['milkProduction']; 
    var heartrate= animal.sensor['heartRate'];
    var temperature= animal.sensor['temp'];
    var latlng = new google.maps.LatLng(coordinateY,coordinateX);
    var herd= animal['herd'];
    
    console.log("x="+coordinateX);
    console.log("y="+coordinateY);
    console.log("latlng="+latlng);
    console.log(id);
    bounds.extend(latlng); // for zoom in red dots
    createMarker(id,latlng,milkProduct,herd,heartrate,temperature);
  }
  //map.fitBounds(bounds); // for zoom in red dots
  
 
}

function createMarker(id,latlng, milkProduct,herd,heartrate,temperature){
    
  var html=
  `
        <div class="animal-info-window">
            <div class="animal-info-name">
               Αριθμός ζώου / Id : ${id}
             </div>
            <div class="animal-info-herd">
                Κοπάδι:${herd}
            </div>
            <div class="animal-info-heartrate">
               <i class="fas fa-wave-square"></i> 
                Παλμοί: ${heartrate}
            </div>
            <div class="animal-info-temperature">
                <i class="fas fa-thermometer-three-quarters"></i>
                 Θερμοκρασία: ${temperature}
            </div>
            <div class="animal-info-milk">
               Παραγωγή γάλακτος: ${milkProduct}
            </div>
            
        </div>
    `;



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


function showGateMarkers(){
  
      for (var gate of myGates){
      var coordinateGateX=gate.location['x'];
      var coordinateGateY=gate.location['y'];
      var gateId = gate['id'];
      var gateState= gate['isOpen']; 
      var latlngGate = new google.maps.LatLng(coordinateGateY,coordinateGateX);
      
      console.log("x="+coordinateGateX);
      console.log("y="+coordinateGateY);
      console.log("latlng="+latlngGate);
      console.log(gateId);
      //bounds.extend(latlng); // for zoom in red dots
      createGateMarker(gateId,latlngGate,gateState);
    }
       
}

function createGateMarker(gateId,latlngGate,gateState){
    var opG;
    if(gateState==true) opG="Ναι" ;
    if(gateState==false) opG="Όχι";
    var html=
  `
        <div class="gate-info-window">
            <div class="gate-info-name">
                Πύλη / Id : ${gateId}
             </div>
            <div class="gate-info-state">
                Πύλη ανοιχτή : ${opG}
            </div>      
            <div class="gate-button">
            <button  id= "changeGate"  onclick="postGate(${gateId},${gateState});"  > Άλλαξε κατάσταση</button> 
            </div>                 
        </div>
    `;

      //gate is open
    if(gateState==true){
        console.log("%%%%%%%%%%%%%%%%%%%%%%%%%%");
        console.log("gate state ths pylhs:"+ gateId+" is"+gateState);
        var GateMarker = new google.maps.Marker({
        map:map,
        position:latlngGate,
        //label: id.toString(),
        icon: iconWhite
         });

      google.maps.event.addListener(GateMarker,'click',function(){
      infoWindow.setContent(html);
       infoWindow.open(map,GateMarker);
      });
      markers.push(GateMarker);
  }

  //gate is closed
  if(gateState==false) {
    console.log("%%%%%%%%%%%%%%%%%%%%%%%%%%");
        console.log("gate state ths pylhs:"+ gateId+" is"+gateState);
    var GateMarker = new google.maps.Marker({
    map:map,
    position:latlngGate,
    //label: id.toString(),
    icon: iconBlack
     });

     google.maps.event.addListener(GateMarker,'click',function(){
      infoWindow.setContent(html);
     infoWindow.open(map,GateMarker);
     });
     markers.push(GateMarker);
    }
}


function DeleteMarkers() {
    //Loop through all the markers and remove
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
};


async function postGate(gateId,gateState){ 
    console.log("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    console.log("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    console.log("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        var valueGate=gateId;
        var valueState;
        if (gateState==true) valueState=false;
        if (gateState==false) valueState=true;
  console.log("$$$$$$$$$$$$$$$$$$$$$$$$$$");
  console.log("valueGate ="+gateId);
  console.log("gate status is"+gateState);
  console.log(typeof valueState);
         //check the strings - if "" is needed
 const response =await   fetch('http://localhost//CattleMonitoring/accessGate/id?id='+valueGate, {
         mode:'no-cors',
          method: "post",
         headers: { "Content-Type": "application/json" },
         body:  JSON.stringify({"command":"postgate","body":{"gate_id":valueGate,"isOpen":valueState}})
         }).then(resp => { console.log("Status: " + resp.body)
         }).then(dataJson => {
              dataReceived = JSON.parse(dataJson)
              console.log(dataReceived);
         }).catch((error) => { //3 new lines
             console.error('Error:', error);
             //alert("Error:"+ error);
             alert("Τhe gate"+valueGate+" is now open")
          })



     };