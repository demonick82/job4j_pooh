package servises;

import req.Req;
import req.Resp;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> queue =
            new ConcurrentHashMap();
    private static String tmpUser;

    @Override
    public Resp process(Req req) {
        String text = "";
        String status = req.getSourceName();

        if ("POST".equals(req.httpRequestType())) {
            if (req.getParam().contains("client")) {
                tmpUser = req.getParam();
            } else {
                ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> tmpMap = new ConcurrentHashMap();
                tmpMap.putIfAbsent(status, new ConcurrentLinkedQueue<>());
                tmpMap.get(status).add(req.getParam());
                queue.putIfAbsent(tmpUser, tmpMap);
            }
        } else if ("GET".equals(req.httpRequestType())) {
            ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> tmp = queue.get(req.getParam());
            if (tmp != null) {
                text = tmp.get(status).poll();
            }
        }
        return new Resp(text, status);
    }
}
