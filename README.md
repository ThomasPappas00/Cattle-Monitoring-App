# Smart Agriculture - Monitoring Cattle in Real Time
A prototype IoT system that can be utilzied by cattlemen/ranchers/farmers for monitoring their animals' and farms' live data.

## Description
This system tackles some big concerns for ranchers/livestock breeders/farmers that maintain their business in huge areas by providing crucial information at the user’s web browser. Keeping track of the herds’ whereabouts, informing on the individual animal’s medical/vaccination history and security as long as the barn safety (environmental monitoring) and providing automation on gates and milking infrastructure with specific actuators. The Google Maps API is used for the map visualizations. 
Microsoft SQL Server is used for the database, Java for the back-end (Apache Tomcat v9.0) and the world simulator and HTML/CSS/Javascript for the front-end. 


### Monitoring Animals
•	Wearing collar sensor 
1.	GPS
2.	Temperature
3.	Heart rate
4.	Normal rumination
5.	Secure
6.	Attention
7.	Timestamp

![image](https://github.com/ThomasPappas00/Cattle-Monitoring-App/assets/75483971/54f45987-9c84-45cb-90e4-2c04215e5aaf)
![image](https://github.com/ThomasPappas00/Cattle-Monitoring-App/assets/75483971/e04f3016-314b-45a4-aa16-7a0fca64e7ad)

•	History is saved
1.	Birth date
2.	Parents
3.	Vaccinations 
4.	Is sick
5.	Sex
6.	Has given birth
7.	Milk production

![image](https://github.com/ThomasPappas00/Cattle-Monitoring-App/assets/75483971/30360242-6798-442d-98c8-57158d68dd54)

### Automation
•	Milk Pumps
1.	Available
2.	Cow using it
3.	Milk on tank
   
![image](https://github.com/ThomasPappas00/Cattle-Monitoring-App/assets/75483971/39852cf5-e082-4082-9663-b3ccb3bac542)

•	Gates (open-close)

![image](https://github.com/ThomasPappas00/Cattle-Monitoring-App/assets/75483971/4c4f8119-2bca-4684-9f1b-e0f929e1939a)

## Runtime 
A simulated farm starts when executing the _initFarm_ module. Three milk pumps are created and a number of animals (belonging in various herds) with random history, random sensor data and a random location inside the farm. The _Simulator_ module gives life to the farm. Animals move around, have their colar sensor data changed and go for milking at the three pumps. The _AccessSensor_ servlet handles requests for the _client_ to get a sensor with specific id, add a new sensor to the system and delete a sensor using the _http://{{ip}}/CattleMonitoring/accessSensor/_ endpoint. In general, the API of the app is:

| Paths                             | Methods       |
| -------------                     |:-------------:| 
| /CattleMonitoring/accessSensor/   | GET, POST, DELETE |
| /CattleMonitoring/accessSensors/  | GET   | 
| /CattleMonitoring/accessAnimal/   | GET, POST, DELETE |
| /CattleMonitoring/accessAnimals/  | GET   | 
| /CattleMonitoring/accessMilkPump/   | GET |
| /CattleMonitoring/accessMilkPump/  | GET   | 



## Demo
https://github.com/ThomasPappas00/Cattle-Monitoring-App/assets/75483971/34be87f9-de9c-4278-a7c1-4133d547be96

