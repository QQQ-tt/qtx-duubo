FROM openjdk:17

MAINTAINER wyler "1102214883@qq.com"

ADD target/dubbo-provider-1.0-SNAPSHOT.jar dubbo-provider.jar

#设置镜像对外暴露端口
EXPOSE 3010

# 执行启动命令
ENTRYPOINT ["java","-jar","/dubbo-provider.jar",">/dubbo-provider.log &"]


