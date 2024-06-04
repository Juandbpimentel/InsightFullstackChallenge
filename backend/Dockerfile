FROM openjdk:17-jdk-bullseye AS gcloud-build
EXPOSE 8080
RUN apt-get update
RUN apt-get install -y maven git
RUN git clone https://github.com/Juandbpimentel/InsightFullstackChallenge.git
WORKDIR /InsightFullstackChallenge/backend
RUN mvn clean install
ENV JAR_FILE=${JAR_FILE:-./target/SupplierApplicationInsight-1.0.0.jar}
RUN mv ./target/SupplierApplicationInsight-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]