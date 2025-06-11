FROM openjdk:21-jdk
LABEL authors="Matvej","Arnas"
WORKDIR /app
COPY build/libs/PaymentDerviceAplicationII-0.0.1-SNAPSHOT.jar /app/payment-service-application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/payment-service-application.jar"]