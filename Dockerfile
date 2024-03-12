FROM openjdk:21
EXPOSE 9093
ADD target/kata-delivery-by-yassine-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]