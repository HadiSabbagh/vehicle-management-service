FROM openjdk:19
COPY target/vehicle-management-service-1.0.jar vehicle-management-service-1.0.jar
ENTRYPOINT ["java","-jar","vehicle-management-service-1.0.jar"]
EXPOSE 8080
