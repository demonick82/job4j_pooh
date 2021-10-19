package servises;

import req.Req;
import req.Resp;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> queue =
            new ConcurrentHashMap();

    public Resp process(Req req) {
        String text = "";
        String status = "200";
        if ("POST".equals(req.httpRequestType())) {
            for (ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> m : queue.values()) {
                for (ConcurrentLinkedQueue<String> q : m.values()) {
                    q.offer(req.getParam());
                }
            }
        } else if ("GET".equals(req.httpRequestType())) {
            if (queue.get(req.getSourceName()) == null) {
                ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> map = new ConcurrentHashMap<>();
                map.putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
                queue.putIfAbsent(req.getSourceName(), map);
            } else {
                queue.get(req.getSourceName())
                        .putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
                text = queue.get(req.getSourceName()).get(req.getParam()).poll();
                if (text == null) {
                    return new Resp("", "204");
                }
            }
        }
        return new Resp(text, status);
    }
}
