package com.uidser.ppmusic.rank.rabbitlistner;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.uidser.ppmusic.common.entity.DaySnapShot;
import com.uidser.ppmusic.common.entity.ListenQuantitySnapshot;
import com.uidser.ppmusic.common.exception.MusicException;
import com.uidser.ppmusic.rank.service.DaySnapShotService;
import com.uidser.ppmusic.rank.service.ListenQuantityRankSnapshotService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class playQuantityListener {

    @Resource
    private ListenQuantityRankSnapshotService listenQuantityRankSnapshotService;

    @Resource
    private DaySnapShotService daySnapShotService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "play.quantity.rank.queue"),
            exchange = @Exchange(value = "media-exchange", type = ExchangeTypes.TOPIC),
            key = "*.add.play.quantity"),
            containerFactory = "batchQueueRabbitListenerContainerFactory",
            ackMode = "MANUAL",
            concurrency = "1"
    )
    @Transactional
    public void addPlayQuantity(Channel channel,
                                List<Message> listenQuantitySnapshotList) {
        if (listenQuantitySnapshotList.size() > 0) {
            List<ListenQuantitySnapshot> listenQuantitySnapshotList1 = new ArrayList<>();
            List<DaySnapShot> daySnapShotList = new ArrayList<>();
            LocalDate today = LocalDate.now();
            for (int i = 0; i < listenQuantitySnapshotList.size(); i++) {
                Gson gson = new Gson();
                byte[] body = listenQuantitySnapshotList.get(i).getBody();
                String byteString = new String(body);
                ListenQuantitySnapshot listenQuantitySnapshot1 = gson.fromJson(byteString, ListenQuantitySnapshot.class);
                if(listenQuantitySnapshot1.getRankFrequency() != null && listenQuantitySnapshot1.getListenQuantity() != -1) {
                    listenQuantitySnapshotList1.add(listenQuantitySnapshot1);
                }
                DaySnapShot daySnapShot = gson.fromJson(byteString, DaySnapShot.class);
                daySnapShot.setSnapShotTime(today);
                daySnapShotList.add(daySnapShot);
            }
            try {
                if(listenQuantitySnapshotList1.size() > 0) {
                    listenQuantityRankSnapshotService.addPlayQuantity(listenQuantitySnapshotList1);
                }
                daySnapShotService.addDayListenQuantity(daySnapShotList);
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
