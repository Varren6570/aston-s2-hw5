# === ЭТАП 1: Сборка приложения и подготовка JRE через jlink ===
FROM maven:3-eclipse-temurin-17 AS build

# Создание рабочей директории внутри контейнера
RUN mkdir /usr/src/project

# Копирование всех файлов проекта (включая pom.xml, src и т.д.) внутрь контейнера
COPY . /usr/src/project

# Установка рабочей директории
WORKDIR /usr/src/project

# Сборка проекта с пропуском тестов
RUN mvn clean package -DskipTests

# Распаковка итогового jar-файла
RUN jar xf target/TestConsumerApplication.jar

# Генерация списка Java-модулей, необходимых для работы приложения
RUN jdeps --ignore-missing-deps \
    --recursive \
    --multi-release 17 \
    --print-module-deps \
    --class-path 'BOOT-INF/lib/*' \
    target/TestConsumerApplication.jar > deps.info

# Создание минимального JRE на основе найденных зависимостей
RUN jlink \
    --add-modules $(cat deps.info) \
    --strip-debug \
    --compress 2 \
    --no-header-files \
    --no-man-pages \
    --output /customjre

# === ЭТАП 2: Финальный образ для запуска приложения ===
FROM debian:bookworm-slim

# Определение переменной окружения для JAVA_HOME
ENV JAVA_HOME=/opt/java/openjdk

# Добавление JRE в системный PATH
ENV PATH="$JAVA_HOME/bin:$PATH"

# Копирование подготовленного минимального JRE из предыдущего этапа
COPY --from=build /customjre $JAVA_HOME

# Создание рабочей директории для приложения
RUN mkdir /app

# Копирование jar-файла приложения из предыдущего этапа
COPY --from=build /usr/src/project/target/TestConsumerApplication.jar /app/

# Установка рабочей директории
WORKDIR /app

# Открытие порта
EXPOSE 8082

# Точка входа — запуск Java-приложения
ENTRYPOINT ["java", "-jar", "TestConsumerApplication.jar"]
