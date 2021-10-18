package servises;

import req.Req;
import req.Resp;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String text = "";
        String status = req.getSourceName();
        if ("POST".equals(req.httpRequestType())) {
            queue.putIfAbsent(status, new ConcurrentLinkedQueue<>());
            queue.get(status).add(req.getParam());
        } else if ("GET".equals(req.httpRequestType())) {
            text = queue.get(status).poll();
        }
        return new Resp(text, status);
    }
}
