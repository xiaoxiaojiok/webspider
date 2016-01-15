
FROM ubuntu:14.04

MAINTAINER coderxiao <xiaoxiaojiok@gmail.com>

RUN sudo mv /etc/apt/sources.list /etc/apt/sources.list.backup
ADD sources.list /etc/apt/sources.list
RUN apt-get update

RUN apt-get install openssh-server -y
RUN mkdir -p /var/run/sshd

RUN sed -i 's/PermitRootLogin without-password/PermitRootLogin yes/' /etc/ssh/sshd_config

RUN echo "root:xiaoxiaojiok" | chpasswd 

RUN mkdir /java
ADD jdk-8u60-linux-x64.tar.gz /java/
ADD webspider-core-1.0.0-release.tar.gz /
RUN echo "export JAVA_HOME=/java/jdk1.8.0_60" >> /etc/profile
RUN echo "export PATH=$PATH:/java/jdk1.8.0_60/bin" >> /etc/profile

EXPOSE 22 8888 9090 9999

ENV LANG en_US.UTF-8
ENV LC_ALL en_US.UTF-8
ENV JAVA_HOME /java/jdk1.8.0_60
ENV PATH=$PATH:$JAVA_HOME/bin

RUN java -version

# VOLUME ["/data"]

CMD /webspider-core-1.0.0-release/bin/start.sh && /usr/sbin/sshd -D