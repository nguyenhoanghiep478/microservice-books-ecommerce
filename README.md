# microservice-book-ecommerce


## Hướng dẫn chạy chương trình

### Yêu cầu hệ thống
- Docker
- npm (14 hoặc cao hơn)
- Java (JDK 17 hoặc cao hơn)
- Spring Boot (3.x)
- Kafka và Zookeeper
- IDE Intellij
#### Các bước thực hiện
##### Bước 1: Chạy Docker Compose
Chạy lệnh dưới đây để khởi động database và Redis:
```bash
docker-compose up -d 
```

##### Bước 2: Chạy Zookeeper và Kafka
Thay đổi đường dẫn đến thư mục Kafka sau đó chạy 2 terminal
```bash
bin/zookeeper-server-start.sh config/zookeeper.properties
```

```bash
bin/kafka-server-start.sh config/server.properties
```

##### Bước 4: Chạy dự án Backend

Khởi chạy theo thứ tự
+ config-server
+ discovery
+ gateway

Và các service còn lại

##### Bước 5: Chạy dự án frontend
Di chuyển đến thư mục frontend và chạy lệnh:

```bash
npm install
npm run dev
```

Ứng dụng sẽ chạy trên url: http://localhost:3000 .