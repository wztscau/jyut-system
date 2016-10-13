package com.jyut.system.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.ListView;

/**
 * 消息接受的服务，尚未实现
 */
public class MessageService extends Service {
    public MessageService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

//    class MessageImpl extends stub
}
