var female="female.png";
var male="male.png";


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
        console.log("typos data)");
        console.log(typeof data);
        console.log("typos myarray");
        console.log(typeof myArray);
       
        }
      console.log(myArray); 
      generateList();
        
        
    })  
    }
    function generateList(){
        var ddlAnimals = document.getElementById("ddlAnimals");
           
      //Add the Options to the DropDownList.
        for (var animal of myArray){           //i = 0; i < customers.length; i++) {
        var option = document.createElement("OPTION");

        //Set Customer Name in Text part.
        option.innerHTML ="id:"+ animal["id"];
       

        //Set CustomerId in Value part.
        option.value = animal["id"];

        //Add the Option element to DropDownList.
        ddlAnimals.options.add(option);
        }
        //document.getElementById("ddlAnimals").innerHTML = ddlAnimals.value;
          //  console.log("h epilogh toy drop down einai:"+ddlAnimals);
    }
    
    
 function animalChoice() {
    var selectedValue = document.getElementById("ddlAnimals").value;
    console.log("ddlAnimals:"+selectedValue);
    
    
    for (var animal of myArray){
        if (animal.id==selectedValue){
            
            document.getElementById("myTable").innerHTML = "";
            var table = document.getElementById('myTable');
            var bvd;
            var pi3;
            var brsv;
            var myr;
            var att;
            var fylo;
            var labor;
            var sec;
            console.log("mesa sto for:"+selectedValue);
            console.log("heartrate"+animal.sensor['heartRate']);
            //console.log("typos toy animal:");
            //console.log(typeof animal); object
           
            if(animal.sensor['normalRumination']==true) myr="??????";
           if(animal.sensor['normalRumination']==false) myr="??????";
           if(animal.history['IBR_vacc']==true) bvd="??????";
           if(animal.history['IBR_vacc']==false) bvd="??????";
           if(animal.history['BVD_vacc']==true) vac="??????";
           if(animal.history['BVD_vacc']==false) vac="??????";
           if(animal.history['PI3_vacc']==true) pi3="??????";
           if(animal.history['PI3_vacc']==false) pi3="??????";
           if(animal.history['BRSV_vacc']==true) brsv="??????";
           if(animal.history['BRSV_vacc']==false) brsv="??????";
           if(animal.history['sick']==true) sic="??????";
           if(animal.history['sick']==false) sic="??????";
           if(animal.history['given_birth']==true) labor="??????";
           if(animal.history['given_birth']==false) labor="??????";
           if(animal.sensor['secure']==true) sec="??????";
           if(animal.sensor['secure']==false) sec="??????";
           if(animal.sensor['attention']==true) att="??????";
           if(animal.sensor['attention']==false) att="??????";
           if(animal['sex']=="F")  fylo="????????????";//fylo=true;//"fas fa-venus";//
           if(animal['sex']=="M") fylo="????????????????";//fylo=false;"fas fa-mars";//
           console.log("fylo=");
           console.log(typeof fylo);
            //var x=document.getElementById('table table-striped');
            //var y=document.getElementById('table table-striped');
           // x.innerHTML="heartrate"+animal.sensor['heartRate'];
           // y.innerHTML="sick"+sic;

           var row = `<tr>           
                         <td ><strong>?????????????? ?????????????????????? </strong></td>
                        <td> </td>
                         </tr>`
                table.innerHTML += row

        

           var row = `<tr>           
                    <td>?????????????? ????????: </td>
                    <td> ${animal['id']}</td>
            </tr>`
            table.innerHTML += row
           

           var row = `<tr>
                        <td>????/?????? ????????????????:</td>
                        <td>  ${animal.history['birth_date']}</td>
                            
		  </tr>`
			table.innerHTML += row

            var row = `<tr>           
                    <td>?????????????? ???????? / Id  ??????????????: </td>
                    <td> ${animal.history['mother_id']}</td>
            </tr>`
            table.innerHTML += row
           

            var row = `<tr>           
                    <td>?????????????? ???????? / Id ????????????: </td>
                    <td> ${animal.history['father_id']}</td>
            </tr>`
            table.innerHTML += row
           

            var row = `<tr>           
                    <td>????????: </td>
                    <td>${fylo} </td>
            </tr>`
            table.innerHTML += row
           

            var row = `<tr>           
                    <td>???????? ????????????????: </td>
                    <td> ${labor}</td>
            </tr>`
            table.innerHTML += row
           
            var row = `<tr>           
                         <td><strong>?????????????? ?????????????????? </strong></td>
                         <td> </td>
                        </tr>`
                table.innerHTML += row

            var row = `<tr>           
                    <td>??????????????????????: </td>
                    <td> <i class="fas fa-thermometer-three-quarters"></i>  ${animal.sensor['temp']}</td>
            </tr>`
            table.innerHTML += row
           
            var row = `<tr>           
                    <td>????????????: </td>
                    <td> <i class="fas fa-wave-square"></i>  ${animal.sensor['heartRate']}</td>
            </tr>`
    table.innerHTML += row


            var row = `<tr>
                    <td>?????????????????? ???????????????????? </td>
                    <td>${myr}</td>
             </tr>`

            table.innerHTML += row


            var row = `<tr>
                         <td colspan="2"><strong>??????????????</strong></td>
                          <td></td>
                        </tr>`

                table.innerHTML += row

            var row = `<tr>          
                    <td>?????????????? IBR: </td>
                     <td> ${bvd}</td>
            </tr>`
            table.innerHTML += row

            var row = `<tr>           
                    <td> ?????????????? BVD : </td>
                    <td> ${bvd}</td>
            </tr>`
            table.innerHTML += row
           
            var row = `<tr>           
                    <td>?????????????? PI3: </td>
                    <td> ${pi3}</td>
            </tr>`
            table.innerHTML += row
           
            var row = `<tr>           
                    <td>?????????????? BRSV: </td>
                    <td> ${brsv}</td>
            </tr>`
            table.innerHTML += row

            var row = `<tr>           
                                 <td colspan="2"><strong>???????????????????? - ??????????????????</strong> </td>
                                 <td> </td>
                        </tr>`
            table.innerHTML += row

           

            var row = `<tr>          
                    <td>?????????? ??????????????: </td>
                    <td> ${sic}</td>
            </tr>`
            table.innerHTML += row

            var row = `<tr>           
                    <td>???????????????????? ?????????? ??????????????: </td>
                    <td> ${att}</td>
            </tr>`
            table.innerHTML += row
           
            var row = `<tr>           
                    <td>?????????? ??????????????: </td>
                    <td> ${sec}</td>
            </tr>`
            table.innerHTML += row
           
            var row = `<tr>           
                    <td>?????????????? ??????????????????: </td>
                    <td> ${animal.sensor['id']}</td>
            </tr>`
            table.innerHTML += row

           

        }

    }
    
}

   



















