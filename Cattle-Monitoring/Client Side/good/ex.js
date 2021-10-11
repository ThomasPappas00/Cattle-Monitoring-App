/*window.onload = () => {
    

        postSensor();


    };*/

    async function postSensor(){  
        var valueHerd=parseInt(document.getElementById("herd").value);
                 var valueSnrId=parseInt(document.getElementById("sensorId").value);
                console.log("sensor"+valueSnrId+"typos toy");
                console.log(typeof valueSnrId);
                console.log("herd"+valueHerd+"typos toy");
                console.log(typeof valueHerd);


       
    const response =await   fetch('http://localhost//CattleMonitoring/accessSensor', {
        mode:'no-cors',
        method: "post",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({"command":"postsensor","body":{"sensor_id":valueSnrId,"herd":valueHerd}}
        )}).then(resp => {
        
           
            console.log("Status: " + resp.body)
           
        }
    ).then(dataJson => {
        dataReceived = JSON.parse(dataJson)
        console.log("last then")
    }).catch((error) => { //3 new lines
        console.error("Error:"+ error);
        alert("Error:"+ error);
      })
}

async function postAnimal(){ 
    var ibr,bvd,pi3,brsv,sick,sex,birth;



    var valuecowId=parseInt(document.getElementById("cowId").value);
    var valueHerd2=parseInt(document.getElementById("herd2").value);
    var valueSnr=parseInt(document.getElementById("sensId").value);
    var valuebirth_date=document.getElementById("birth_date").value;
    var valuefather_id=parseInt(document.getElementById("father_id").value);
    var valuemother_id=parseInt(document.getElementById("mother_id").value);
    var valueIBR_vacc=document.getElementById("IBRList").value;
    ibr=(valueIBR_vacc==="true");
    var valueBVD_vacc=document.getElementById("BVDList").value;
    bvd=(valueBVD_vacc==="true");
    var valuePI3_vacc=document.getElementById("PI3List").value;
    pi3=(valuePI3_vacc==="true");
    var valueBRSV_vacc=document.getElementById("BRSVList").value;
    brsv=(valueBRSV_vacc==="true");
    var valuesick=document.getElementById("sickList").value;
    sick=(valuesick==="true");
    var valuegiven_birth=document.getElementById("birthList").value;
    birth=(valuegiven_birth==="true");

    var sex=document.getElementById("sexList").value;
    
    var valuemilkProduction=document.getElementById("milkProduction").value+" litres/day";



    console.log("cow"+valuecowId+"typos toy");
    console.log(typeof valuecowId);
    console.log("herd"+valueHerd2+"typos toy");
    console.log(typeof valueHerd2);
    console.log("sensor"+valueSnr+"typos toy");
    console.log(typeof valueSnr);
    console.log("birth_date"+valuebirth_date+"typos toy");
    console.log(typeof valuebirth_date);
    console.log("father"+valuefather_id+"typos toy");
    console.log(typeof valuefather_id);
    console.log("mother"+valuemother_id+"typos toy");
    console.log(typeof valuemother_id);
    console.log("IBR"+ibr+"typos toy");
    console.log(typeof ibr);
    console.log("BVD"+bvd+"typos toy");
    console.log(typeof bvd);
    console.log("PI3"+pi3+"typos toy");
    console.log(typeof pi3);
    console.log("BRSV"+brsv+"typos toy");
    console.log(typeof brsv);
    console.log("SICK"+sick+"typos toy");
    console.log(typeof sick);
    console.log("given birth  "+  birth+  "   typos toy");
    console.log(typeof birth);

    console.log("fylo"+sex+"typos toy");
    console.log(typeof sex);
    console.log("milk production"+valuemilkProduction+   "  typos toy");
    console.log(typeof valuemilkProduction);


    //check the strings - if "" is needed
    const response =await   fetch('http://localhost//CattleMonitoring/accessAnimal', {
         mode:'no-cors',
        method: "post",
         headers: { "Content-Type": "application/json" },
         body:  JSON.stringify({"command":"postanimal","body":{"id":valuecowId,"herd":valueHerd2,"sensor_id":valueSnr,
         "history":{"birth_date":valuebirth_date,"father_id":valuefather_id,
         "mother_id":valuemother_id,"IBR_vacc":ibr,
         "BVD_vacc":bvd,"PI3_vacc":pi3,
         "BRSV_vacc":brsv,
        "sick":sick,"given_birth":birth},
        "sex":sex,"milkProduction":valuemilkProduction}})
    }).then(resp => { console.log("Status: " + resp.body)
    }).then(dataJson => {
         dataReceived = JSON.parse(dataJson)
    }).catch((error) => { //3 new lines
        console.error('Error:', error);
        alert("Error:"+ error);
     })



}