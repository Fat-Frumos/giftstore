FROM centos:7

RUN yum update -y && \
    yum install -y wget

RUN wget https://download.oracle.com/java/17/latest/jdk-17_linux-x64_bin.rpm && \
    yum -y install ./jdk-17_linux-x64_bin.rpm

RUN yum install -y epel-release && \
    yum install -y nginx

COPY nginx.conf /etc/nginx/nginx.conf

COPY index.html /usr/share/nginx/html

RUN wget https://raw.githubusercontent.com/gdraheim/docker-systemctl-replacement/master/files/docker/systemctl.py -O /usr/bin/systemctl && \
    chmod a+x /usr/bin/systemctl


EXPOSE 22 80 8080

RUN yum install -y openssh-server && \
    ssh-keygen -A && \
    echo 'root:password' | chpasswd

CMD service sshd start && nginx && /usr/sbin/sshd -D


# # Build stage
# FROM maven:3.8.2-jdk-11 AS build
# COPY . .
# RUN mvn clean package -X -DskipTests -Dmaven

# # Run stage
# FROM openjdk:8-jdk-alpine

# COPY --from=build ./web-app/target/web-app-1.0.0.jar certificate.jar
# EXPOSE 8080
# ENTRYPOINT ["java","-jar","certificate.jar"]