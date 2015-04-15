# spring-data-rest-sample

`POST http://localhost:8080/spring-data-rest-sample/testRelations
{ "name":"bla", "description":"bla description" }`

`POST http://localhost:8080/spring-data-rest-sample/tests
{ "name":"bla", "description":"bla description", "testRelation" : "http://localhost:8080/java-sample-webapp/testRelations/552e334374a4b5de43242428" }`

`GET http://localhost:8080/spring-data-rest-sample/tests?projection=testTestRelationProjection`
