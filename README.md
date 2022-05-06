# IT Conference Application Instruction
## Running
Java 17 is required to build and run the application.  
Firstly download the project and move to its directory.

Then build JAR package:  
`mvnw clean package`

And finally run the application:  
`java -jar target/itconference-1.0.0.jar`

## Endpoints and example requests
* Displaying conference schedule  
  GET **/lectures**  
  Example request:
  > curl -v http://localhost:8080/lectures

* Signing up for a lecture  
  POST **/participations**  
  Body: {username: string, email: string, lectureId: long}  
  Example request:
  > curl -v -X POST http://localhost:8080/participations \  
  > -H "Content-Type: application/json" \  
  > -d '{"username": "simon3", "email": "simon@gmail.com", "lectureId": 4}'

* Displaying list of user's lectures  
  GET **/lectures**  
  Param: (username: string)  
  Example request:
  > curl -v http://localhost:8080/lectures?username=simon3

* Signing out of a lecture  
  DELETE **/participations**  
  Body: {username: string, lectureId: long}  
  Example request:
  > curl -v -X DELETE http://localhost:8080/participations \  
  > -H "Content-Type: application/json" \  
  > -d '{"username": "simon3", "lectureId": 4}'

* Changing user's email  
  PUT **/users/{username}**  
  Path variable: (username: string)  
  Body: {email: string}  
  Example request:
  > curl -v -X PUT http://localhost:8080/users/simon3 \  
  > -H "Content-Type: application/json" \  
  > -d '{"email": "simon333@gmail.com"}'

* Displaying users list  
  GET **/users**  
  Example request:
  > curl -v http://localhost:8080/users

* Displaying lectures summary  
  GET **/summary/lectures**  
  Example request:
  > curl -v http://localhost:8080/summary/lectures

* Displaying content tracks summary  
  GET **/summary/content-tracks**  
  Example request:
  > curl -v http://localhost:8080/summary/content-tracks

