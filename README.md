# bootcamp-assignment1

This is a Spring Boot Application leveraging Apache Kafka for publishing and consuming messages to and from appropriate topics.
There is an API exposed for publishing employee details

Steps to bring up the application on your local machine

1. Start up zookeeper and kafka applications
2. Import code in IntelliJ or execute the jar application
3. API Post URL Exposed
http://localhost:8080/pushToQueue

Body :
{
    "empName": "Nandita",
    "empId": 12345,
    "organization": "Infosys",
    "role": "BestOfAll"
}

4. When the API is hit, the initial body is publishes to a topic called Employee using a producer.
A consumer consumes this message and runs a validation. The logger to verify is Consumer Record:
If the posted information meets the criteria, it is published to a topic called correctEmployee. The logger to verify is #### -> Consumed message in correct topic ->
If validation fails, the information is posted to a DeadLetterQueue. The logger to verify is #### -> Consumed message in DLQ ->


Things to further work on
1. Make changes to accept a list of employee details rather than a single employee at a time
2. Give proper names to queues
3. Improve logging
