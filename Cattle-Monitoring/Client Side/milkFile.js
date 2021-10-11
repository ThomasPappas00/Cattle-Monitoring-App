let map; 
var markers = [];
var infoWindow;
var iconRed="circle.png";
var iconBlue="blueDot.png";
var iconGreen="greenDot.png";
var iconBlack="blackDot.png";

const antliaBounds={
north: 39.729494601169804,
south: 39.72913670302929,
west:  21.686456830884925,
east:  21.687022776934757,
};


window.onload= () => {

  getFile();
  getFileAntlies();
 setInterval(myMethod, 10000);

function myMethod( )
{
    
  getFile();
  getFileAntlies();
}
  
  //getFile();

  console.log("onload");
  //console.log();
  //getSensorFile();
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
  async function getFileAntlies(){  
    const response =await   fetch('http://localhost//CattleMonitoring/accessMilkPumps', {mode:'cors'},{headers : { 
      'Content-Type': 'json/aplication'}   }
      )
    .then(res => res.json()
    )
    .then((data)=>
    {
      for( var i in data){
        myAntlies=data[i];
       
        }
        
        getTableAntlies();
       
        
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

function getTableAntlies(){
  
  displayAntlies();
  showAntliesMarkers();

}





function initMap() {

  var farm = {
    lat: 39.72924761521451, 
   lng: 21.686525892514542
 };


 map = new google.maps.Map(document.getElementById('map'), {
  center: farm,
  restriction: {
    latLngBounds: antliaBounds,
    strictBounds: false,
  },
  zoom: 20,
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
 

 function displayAnimals(){
  
  var animalsHtml='';
  for (var animal of myArray){
    var reason;
    var X=animal.sensor.location['x'];
    var Y=animal.sensor.location['y'];
    console.log("display animals function x"+X);
    console.log("display animals function"+Y);
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
    lng: X};

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
                
                Παλμοί ${heartrate}
            </div>
            <div class="animal-info-temperature">
                
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



function displayAntlies(){
  var antliesHtml='';
  for (var antlia of myAntlies){
    
    //var XA=antlia.loc['x'];
   // var YA=antlia.loc['y'];
    
    //console.log(XA);
    //console.log(YA);
    var idAntlias= antlia['id'];
    console.log(idAntlias);
    antliesHtml += `
    <div> antlies </div>

    `
    
  }


  }


  function showAntliesMarkers(){
     var coordinateXA1=21.686729074888387;
      var coordinateYA1=39.72942704417047;
      var latlngAntlias1 = new google.maps.LatLng(coordinateYA1,coordinateXA1);
      var coordinateXA2=21.68677601354578;
      var coordinateYA2=39.72937289538259;
      var latlngAntlias2 = new google.maps.LatLng(coordinateYA2,coordinateXA2);
      var coordinateXA3=21.686808200053708;
      var coordinateYA3=39.7293316391347;
      var latlngAntlias3 = new google.maps.LatLng(coordinateYA3,coordinateXA3);

    for (var antlia of myAntlies){
      //var coordinateXA=antlia.loc['x'];
      //var coordinateYA=antlia.loc['y'];
      var idAntlias= antlia['id'];
      var capacity= antlia['capacity']; 
      var cowInAntlia;//= antlia['animalId'];
      var available;//= antlia['avail'];
      //var latlngAntlias = new google.maps.LatLng(coordinateYA,coordinateXA);
      if(antlia['avail']==true) available="Ναι";
      if(antlia['avail']==false) available="Οχι";
      if(antlia['animalId']==0) cowInAntlia="-";
      else cowInAntlia=antlia['animalId']; //check an einai ok
     
      
      //bounds.extend(latlngAntlias); // xreiazetai?
      if(idAntlias==1) createMarkerAntlias1(idAntlias,latlngAntlias1,capacity,cowInAntlia,available);
      if(idAntlias==2) createMarkerAntlias2(idAntlias,latlngAntlias2,capacity,cowInAntlia,available);
      if(idAntlias==3) createMarkerAntlias3(idAntlias,latlngAntlias3,capacity,cowInAntlia,available);

    }
    


  }

  function createMarkerAntlias1(idAntlias,latlngAntlias1,capacity,cowInAntlia,available){

  
    var html=
    `
          <div class="antlia-info-window">
          
              <div class="antlia-info-name">
                  Αριθμός αντλίας  : ${idAntlias}
              </div>
             
              <div class="antlia-capacity">
                  
                  Πληρότητα : ${capacity} %
              </div>
              
              <div class="antlia-in-use">
                 Διαθεσιμότητα αντλίας: ${available}
              </div>
              <div class="animal-in-antlia">
                  
                  Αγελάδα που αρμέγεται: ${cowInAntlia}
              </div>
          </div>
      `;
  
    var marker = new google.maps.Marker({
      map:map,
      position:latlngAntlias1,
      icon: iconBlack
    });

    
  
    google.maps.event.addListener(marker,'click',function(){
      infoWindow.setContent(html);
      infoWindow.open(map,marker);
     
    });
    markers.push(marker);

    }
    function createMarkerAntlias2(idAntlias,latlngAntlias2,capacity,cowInAntlia,available){

  
      var html=
      `
          <div class="antlia-info-window">
          
            <div class="antlia-info-name">
                Αριθμός αντλίας  : ${idAntlias}
            </div>
     
            <div class="antlia-capacity">
          
              Πληρότητα : ${capacity} %
            </div>
      
            <div class="antlia-in-use">
                Διαθεσιμότητα αντλίας: ${available}
            </div>
            <div class="animal-in-antlia">
          
              Αγελάδα που αρμέγεται: ${cowInAntlia}
            </div>
          </div>
        `;
    
      var marker = new google.maps.Marker({
        map:map,
        position:latlngAntlias2,
        icon: iconBlack
      });
  
      
    
      google.maps.event.addListener(marker,'click',function(){
        infoWindow.setContent(html);
        infoWindow.open(map,marker);
       
      });
      markers.push(marker);
  
      }

      function createMarkerAntlias3(idAntlias,latlngAntlias3,capacity,cowInAntlia,available){

  
        var html=
        `
        <div class="antlia-info-window">
          
        <div class="antlia-info-name">
            Αριθμός αντλίας  : ${idAntlias}
        </div>
 
        <div class="antlia-capacity">
      
          Πληρότητα : ${capacity} %
        </div>
  
        <div class="antlia-in-use">
            Διαθεσιμότητα αντλίας: ${available}
        </div>
        <div class="animal-in-antlia">
      
          Αγελάδα που αρμέγεται: ${cowInAntlia}
        </div>
      </div>
          `;
      
        var marker = new google.maps.Marker({
          map:map,
          position:latlngAntlias3,
          icon: iconBlack
        });
    
        
      
        google.maps.event.addListener(marker,'click',function(){
          infoWindow.setContent(html);
          infoWindow.open(map,marker);
         
        });
        markers.push(marker);
    
        }