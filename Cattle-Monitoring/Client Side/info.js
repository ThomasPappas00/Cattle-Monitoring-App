var femaleImg="female.png";
var maleImg="male.png";
var tableBtnValue=0;
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
        //console.log(typeof myArray); //object
        //console.log(typeof data); //object
      
        console.log(myArray); //οκ
        
        
        generateTable();
        
        
    })  
    }
  
  function generateTable(){
		var table = document.getElementById('myTable');
    var myr;
            var vac;
            var sic;
		for (var animal of myArray){
            var link=[];
            
            tableBtnValue=animal['id'];
           if(animal.sensor['normalRumination']==true) myr="ΝΑΙ";
           if(animal.sensor['normalRumination']==false) myr="ΟΧΙ";
           if(animal.history['IBR_vacc']==true) vac="ΝΑΙ";
           if(animal.history['IBR_vacc']==false) vac="ΟΧΙ";
           if(animal.history['sick']==true) sic="ΝΑΙ";
           if(animal.history['sick']==false) sic="ΟΧΙ";
			var row = `<tr>
                            
							<td>Ημ/νία Γέννησης:  ${animal.history['birth_date']}</td>
                            <td>Id:${animal['id']}</td>
							<td>Φύλο:${animal['sex']}</td>
                            <td>Μητέρα:${animal.history['mother_id']}</td> 
                            <td>Πατέρας:${animal.history['father_id']}</td>    
                            <td>Κανονικός Μυρηκασμός ${myr}</td>
                            <td>Id αισθητήρα:${animal.sensor['id']}</td>
                            <td>Παραγωγή γάλακτος:  ${animal['milkProduction']}</td>
                            <td>Άρρωστο:  ${sic}</td>
                            <td>Θερμ/σία:  ${animal.sensor['temp']}</td>   
                            <td>Παλμοί:${animal.sensor['heartRate']}</td>     
                            <td>Φύλο:${animal.sex}</td>  
                            <td>Εμβόλιο IBR  :${vac}</td>                    
                            <td> <button type="button" value="tableBtnValue" onclick="location.href='1.html';"    >></button>  </td>
					  </tr>`
			table.innerHTML += row


		
	
    }
  }

  //normalRumination