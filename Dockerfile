FROM centos:7

RUN yum update -y && \
    yum install -y wget

RUN yum install -y java-11-openjdk-devel

RUN curl -L -o /opt/jenkins.war https://get.jenkins.io/war-stable/latest/jenkins.war

RUN yum install -y openssh-server && \
    ssh-keygen -A && \
    echo 'root:password' | chpasswd

EXPOSE 8080 22

CMD /usr/sbin/sshd && java -jar /opt/jenkins.war
