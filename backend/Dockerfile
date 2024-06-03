FROM openjdk:22-jdk-bullseye
RUN mkdir /backend
COPY * ./backend
RUN apt-get update
RUN apt-get install -y maven
WORKDIR /backend
RUN mvn clean install
RUN mvn package
CMD ["java", "-jar", "SupplierApplicationInsight-1.0.0.jar"]