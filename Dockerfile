FROM centos:7

# Install OpenJDK 17
RUN yum update -y && \
    yum install -y java-17-openjdk

# Install Nginx
RUN yum install -y epel-release && \
    yum install -y nginx

# Copy custom index.html file
COPY html/index.html /usr/share/nginx/html

# Open ports
EXPOSE 22 80 8080

# Start SSH daemon (optional if you need SSH access)
RUN yum install -y openssh-server && \
    ssh-keygen -A && \
    echo 'root:password' | chpasswd

# Start Nginx and SSH service
CMD service nginx start && /usr/sbin/sshd -D


# # Build stage
# FROM maven:3.8.2-jdk-11 AS build
# COPY . .
# RUN mvn clean package -X -DskipTests -Dmaven

# # Run stage
# FROM openjdk:8-jdk-alpine

# COPY --from=build ./web-app/target/web-app-1.0.0.jar certificate.jar
# EXPOSE 8080
# ENTRYPOINT ["java","-jar","certificate.jar"]