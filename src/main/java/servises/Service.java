package servises;

import req.Req;
import req.Resp;

public interface Service {
    Resp process(Req req);
}
