# 1. OpenJDK 17の軽量版（slim）を使う
FROM openjdk:17-jdk-slim

# 2. コンテナの中に「/app」という作業ディレクトリを作る
WORKDIR /app

# 3. Spring BootのJARファイルを「app.jar」としてコンテナにコピー
COPY build/libs/calendar02-0.0.1-SNAPSHOT.jar /app.jar

# 4. アプリが使うポート8080を開く
EXPOSE 8080

# 5. Spring Bootアプリを起動する
CMD ["java", "-jar", "app.jar"]