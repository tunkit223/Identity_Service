# Stage 1: Build application using Maven
FROM maven:3.9.8-amazoncorretto-21 AS build

WORKDIR /app

# Copy pom.xml và source code vào container build
COPY pom.xml .
COPY src ./src

# Build ứng dụng (bỏ qua test cho nhanh)
RUN mvn clean package -DskipTests

# Stage 2: Create runtime image
FROM amazoncorretto:21.0.4

# Set thư mục làm việc
WORKDIR /app

# Copy file jar từ stage build sang stage runtime
COPY --from=build /app/target/*.jar app.jar

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
