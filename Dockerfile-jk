FROM centos:7

# Install Java
RUN yum update -y && \
    yum install -y java-1.8.0-openjdk-devel

# Add the Jenkins repository and key
RUN curl --silent --location http://pkg.jenkins-ci.org/redhat-stable/jenkins.repo | tee /etc/yum.repos.d/jenkins.repo && \
    rpm --import https://pkg.jenkins.io/redhat/jenkins.io.key

# Import the Jenkins public key
RUN rpm --import https://pkg.jenkins.io/redhat/jenkins.io.key

# Install Jenkins
RUN yum install -y jenkins

# Install OpenSSH server
RUN yum install -y openssh-server && \
    ssh-keygen -A && \
    echo 'root:password' | chpasswd

# Expose the default Jenkins port and the SSH port
EXPOSE 8080 22

# Start Jenkins and sshd
CMD /usr/sbin/sshd && java -jar /usr/lib/jenkins/jenkins.war
