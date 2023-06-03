# Pull tomcat latest image from dockerhub
FROM tomcat:8.0.51-jre8-alpine
MAINTAINER sakskul27@gmail.com
# copy war file on to container
COPY ./target/UserManager*.jar /usr/local/tomcat/webapps
EXPOSE  8080
USER UserManager
WORKDIR /usr/local/tomcat/webapps
CMD ["catalina.sh","run"]