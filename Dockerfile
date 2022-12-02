FROM gradle:6.8-jdk11-openj9
COPY . .
RUN javac Application.java
ENTRYPOINT ["java","Application"]