var femaleImg="female.png";
var maleImg="male.png";
var tableBtnValue=0;
var i=0;

window.onload = function () {
    getFile();
    
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
      console.log(myArray); 
      generateTable();
        
        
    })  
    }
  
  function generateTable(){
		var table = document.getElementById('myTable')
     var mast,attent,safe; 
		for (var animal of myArray){
           
            tableBtnValue=animal['id'];
            console.log("h timh tou btn einai:  "+ tableBtnValue)
           console.log("h timh toy i :"+ i);
           if(animal.sensor['normalRumination']==true) mast="Ναι";
           if(animal.sensor['normalRumination']==false) mast= "Όχι";
           if(animal.sensor['secure']==true) safe="Ναι";
           if(animal.sensor['secure']==false) safe="Όχι";
           if(animal.history['sick']==true) attent="Όχι";
           if(animal.history['sick']==false) attent="Ναι";
			var row = `<tr>
                    <td><image src="cow.png"></td>
							      <td>Αριθμός ζώου/Id:${animal['id']}</td>
							      <td>Κοπάδι:${animal['herd']}</td>
                    <td> Φύλο:${animal['sex']}</td>    
                    <td> Ασφαλές:${safe}</td>  
                    <td> Υγιές:${attent}</td>  
                    <td> Μηρυκασμός φυσιολογικός:${mast}</td>                          
                            
					  </tr>`
			table.innerHTML += row
      i++;

		}
	
}



function  openPage(i){
  console.log("--------openPage----------");
console.log("i:"+i);

}
