package com.sung.housingfinance.kafka;

import com.sung.housingfinance.dto.response.ResponseData;
import lombok.extern.slf4j.Slf4j;
import oracle.jvm.hotspot.jfr.Producer;
import org.apache.kafka.clients.Metadata;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import javax.management.monitor.StringMonitor;
import java.util.Objects;
import java.util.Properties;

/*
 *
 * @author 123msn
 * @since 2019-07-18
 */
@Slf4j
public class ExampleKafkaProducer {

    private static final String TOPIC = "dream";
    public static ResponseData producer(String message){
        ResponseData responseData = new ResponseData();
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, message);
        producer.send(record,(metadata, e) -> {
            if(Objects.nonNull(message)){ log.info(metadata.toString());
            }else{
                e.printStackTrace();
            }
        });

        producer.flush();
        producer.close();
        responseData.setMsg("성공");

        return responseData;
    }
}
