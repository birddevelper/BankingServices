FROM bitnami/rabbitmq:latest
EXPOSE 15672
EXPOSE 5672
ADD rabbitmq.config /etc/rabbitmq/
ADD definitions.json /etc/rabbitmq/
RUN rabbitmq-plugins enable rabbitmq_management
#RUN chown rabbitmq:rabbitmq /etc/rabbitmq/rabbitmq.config /etc/rabbitmq/definitions.json
CMD ["rabbitmq-server"]