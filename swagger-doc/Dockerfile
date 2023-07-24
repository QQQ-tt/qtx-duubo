FROM openjdk:8u342

MAINTAINER wyler "1102214883@qq.com"

ADD target/swagger-doc-2.7.4.jar swagger-doc.jar

#设置镜像对外暴露端口
EXPOSE 19081

# 执行启动命令
ENTRYPOINT ["java","-jar","/swagger-doc.jar",">/swagger-doc.log &"]


