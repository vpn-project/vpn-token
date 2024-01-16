FROM maven
WORKDIR /vpn
COPY . .
RUN mvn clean install
CMD mvn spring-boot:run
