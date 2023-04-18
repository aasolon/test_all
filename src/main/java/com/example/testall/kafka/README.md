Запуск Kafka:
1) cd <kafka-folder> (cd /Users/17230726a/Desktop/WORK/kafka_2.13-3.0.0)
2) bin/zookeeper-server-start.sh config/zookeeper.properties
3) bin/kafka-server-start.sh config/server.properties

Создание топика:
bin/kafka-topics.sh --create --topic request-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

Удаление топика:
1) На сервере в config/server.properties выставить delete.topic.enable=true
2) bin/kafka-topics.sh --delete --topic request-topic --bootstrap-server localhost:9092

Список топиков:
bin/kafka-topics.sh --bootstrap-server localhost:9092 --list

Запустить консольный Producer:
bin/kafka-console-producer.sh --topic request-topic --bootstrap-server localhost:9092

Запустить консольный Consumer:
bin/kafka-console-consumer.sh --topic request-topic --from-beginning --bootstrap-server localhost:9092
