## templateService

Шаблон проекта для начала разработки

## Описание

Проект реализован как шаблон для быстрого старта разработки прикладного ПО
В реализации сервиса применена архитектура цитадель с вынесением внутренних форпостов
Описание архитектуры - https://habr.com/ru/company/e-legion/blog/579848/

Имеет структуру:

templateService :: common   - Модуль утилит, общие модели, dto, dao<br/>
templateService :: db       - Модуль взаимодействия с бд
templateService :: facade   - Модуль запуска приложения, описание маршрутов, логика сервисного слоя
templateService :: logging  - Модуль Модуль логирования

При необходимости возможно создавать модули, например:

mediator        :: модуль запуска внутренних и не http маршрутов - cron, quartz, mailInbound и т.д.
resource        :: модуль для хранения ресурсов
inbound         :: модуль для работы с очередями

## Запуск приложения без docker

1. Собираем проект мавеном: mvn clean install
2. Копируем jar из модуля template-facade в /opt/templateService
3. Создаем каталог conf
4. Копируем application.properties в созданную папку
5. Устанавливаем необходимые параметры настроек - порт
6. Запускам приложение

* windows java -jar -Xms128m -Xmx128m templateService.jar --spring.config.name=.\conf\application
* linux java -jar -Xms128m -Xmx128m /opt/templateService/templateService.jar --spring.config.location=file:/opt/templateService/conf/application.properties
  Размер хипа изменяется администраторами с учетом опыта эксплуатации приложения

## Сборка образа

Сборка возможна двумя способами:

при наличии docker desktop можно воспользоваться Deckerfile

без docker desktop образ можно собрать и отправить в репозиторий плагином jib
для этого необходимо выполнить mvn compile jib:build
Параметры подключения и авторизации должны располагаться в settings.xml раздела server в maven
https://cloud.google.com/java/getting-started/jib

http://localhost:8100/templateService/swagger

Последовательность действий
docker build -t templateservice:v0.0.1 .												-- сборка
docker tag templateservice:v0.0.1 docker.host.srv/spring2ws/templateservice:v0.0.1 		-- тег
docker push docker.host.srv/spring2ws/templateservice:v0.0.1							-- отправка в репозиторий

docker pull docker.host.srv/spring2ws/templateservice:v0.0.1							-- получение образа из репозитория
docker run --rm --name templateservice -i -t -d -p 8100:8100 -e JAVA_OPTS='-Xms128m -Xmx128m' templateservice:v0.0.1              -- запуск контейнера

Запуск docker-compose
docker-compose up -d															    -- сборка на основа compose

Просмотр лога контейнера
ps -ef|grep haproxy
docker logs -f ID                               -- лог это что бы бежал
docker ps                                       -- смотрит id или имя контейнера
docker exec -it ID bash                         -- зайти внутрь контейнера
netstat -antp                                   -- соответствие внутренних и внешних портов