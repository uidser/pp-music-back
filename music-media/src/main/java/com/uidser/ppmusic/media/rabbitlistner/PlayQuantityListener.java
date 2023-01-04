package com.uidser.ppmusic.media.rabbitlistner;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.uidser.ppmusic.common.entity.ListenQuantitySnapshot;
import com.uidser.ppmusic.common.exception.MusicException;
import com.uidser.ppmusic.common.service.MediaService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlayQuantityListener {

    @Resource
    private MediaService mediaService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "play.quantity.media.queue"),
            exchange = @Exchange(value = "media-exchange", type = ExchangeTypes.TOPIC),
            key = "*.add.play.quantity"),
            containerFactory = "batchQueueRabbitListenerContainerFactory",
            ackMode = "MANUAL",
            concurrency = "1"
    )
    public void addPlayQuantity(Channel channel,
                                List<Message> listenQuantitySnapshotList) {
        if (listenQuantitySnapshotList.size() > 0) {
            List<ListenQuantitySnapshot> listenQuantitySnapshotList1 = new ArrayList<>();
            for (int i = 0; i < listenQuantitySnapshotList.size(); i++) {
                Gson gson = new Gson();
                String byteString = new String(listenQuantitySnapshotList.get(i).getBody());
                ListenQuantitySnapshot listenQuantitySnapshot1 = gson.fromJson(byteString, ListenQuantitySnapshot.class);
                listenQuantitySnapshotList1.add(listenQuantitySnapshot1);
            }
            try {
                mediaService.addPlayQuantitySelf(listenQuantitySnapshotList1);
                channel.basicAck(listenQuantitySnapshotList.get(listenQuantitySnapshotList.size() - 1).getMessageProperties().getDeliveryTag(), true);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    channel.basicNack(listenQuantitySnapshotList.get(listenQuantitySnapshotList.size() - 1).getMessageProperties().getDeliveryTag(), true, true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                throw new MusicException("写入数据库时出错", 201);
            }
        }
    }

}
