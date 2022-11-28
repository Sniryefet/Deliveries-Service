# Delivery API

### Overview
This is a Kotlin project that expose various endpoints relates to simple deliveries actions.
The project highlights mostly clean code, readability, architecture and common patterns [Dto, Dao, Singleton].
add possible business constraints. it also includes integration with 3rd parties to retrieve different kind of data


### Prerequisite
 Since there are 3rd parties involve we shall create API keys to use them. Please create a file under the project called ".mykeys" and add "HOLIDAYS=<YOUR_KEY>", and "GEOCODING=<YOUR_KEY>" separatet by new line. You can create your API keys from the following sources: [https://holidayapi.com/docs](), [https://holidayapi.com/docs]()

### Code Layers / Project Structure
1. **api** - interface layer that expose the functionality to the world (TODO: exclude it to a different service)
2. **core** - contains the business logic in the system
3. **dao** - the interfaces for interaction with the DB [MySql in our case]
4. **models** - internal objects (mostly kotlin data class) that our being use only within the service
5. **handlers** - This is the integration layer, responsible for fetching data from the various sources and pass it on to the core layer for processing
6. **Resource** - This is the entry point of the service (usually contains basic input validation & serialization/deserialization logic)


### Business / code Requirements

* courier API - just a staQc json file with the available Timeslots for the upcoming week which would be loaded on start.
*  Exclude courier timeslots that fall on holidays via -> hTps://holidayapi.com/docs (or any other)
* The two sources above should be fetched in parallel in order to calculated the resulted timeslots
* Business capacity - assume that the system supports up to 10 deliveries per day.
* Each timeslots can be used for 2 deliveries
* Find a way how to handle / validate concurrent requests
(two consumers trying to reserve the same Qmeslot)

### API Endpoints
* **POST** /resolve-address - resolves a single line address into a structured one <br/>
{<br/>
“searchTerm”: {SINGLE LINE ADDRESS} <br/>
}

* **POST** /timeslots - retrieve all available timeslots for a formatted address<br/>
{<br/>
“address”: {FORMATTED ADDRESS} <br/>
}<br/>

* **POST** /deliveries - book a delivery <br/>{
“user”: {USER},<br/>
“QmeslotId”: {TIMESLOT_ID}<br/> 
}<br/>
* **POST** /deliveries/{DELIVERY_ID}/complete - mark a delivery as completed 
* **DELETE** /deliveries/{DELIVERY_ID} - cancel a delivery
* **GET** /deliveries/daily - retrieve all today’s deliveries
* **GET** /deliveries/weekly - retrieve the deliveries for current week
