# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.5/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.5/gradle-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.0.5/reference/htmlsingle/#web)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.0.5/reference/htmlsingle/#using.devtools)
* [Spring Data Redis (Access+Driver)](https://docs.spring.io/spring-boot/docs/3.0.5/reference/htmlsingle/#data.nosql.redis)

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Messaging with Redis](https://spring.io/guides/gs/messaging-redis/)

### Additional Links

These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)



# วิธี start app 

## 1. สร้าง  topic ของ redis

```bash
redis-cli
````

```bash
`XADD USER * userFirstData 1`
`XADD PRODUCT * productFirstData 1`
```

## 2. สร้าง group ของแต่ละ topic 

```bash
XGROUP CREATE USER USER-1 $
XGROUP CREATE PRODUCT PRODUCT-1 $
```

## 3. ลบข้อมูลเริ่มต้นด้วย ID 

```bash
XRANGE USER - +  `จะได้ id -> ${ID}`
XDEL USER ${ID}
```

```bash
XRANGE PRODUCT - +  `จะได้ id -> ${ID}`
XDEL PRODUCT ${ID}
```

