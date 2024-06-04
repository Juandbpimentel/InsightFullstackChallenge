FROM openjdk:17-jdk-bullseye
EXPOSE 8080
ENV JAR_FILE=${JAR_FILE:-./target/SupplierApplicationInsight-1.0.0.jar}
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]