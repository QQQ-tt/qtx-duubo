FROM openjdk:17

MAINTAINER wyler "1102214883@qq.com"

ADD target/dubbo-consumer-1.0-SNAPSHOT.jar dubbo-consumer.jar

#设置镜像对外暴露端口
EXPOSE 3011

# 执行启动命令
ENTRYPOINT ["java","-jar","/dubbo-consumer.jar",">/dubbo-consumer.log &"]


