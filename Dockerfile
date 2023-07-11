FROM centos:7

# Install Java
RUN yum update -y && \
    yum install -y java-1.8.0-openjdk-devel

# Download the jenkins.war file
RUN curl -L -o /opt/jenkins.war https://get.jenkins.io/war-stable/latest/jenkins.war

# Install OpenSSH server
RUN yum install -y openssh-server && \
    ssh-keygen -A && \
    echo 'root:password' | chpasswd

# Expose the default Jenkins port and the SSH port
EXPOSE 8080 22

# Start Jenkins and sshd
CMD /usr/sbin/sshd && java -jar /opt/jenkins.war
