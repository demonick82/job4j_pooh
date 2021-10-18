package req;

import java.util.Objects;

public class Req {
    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String parseParam = "";
        String[] req = content.split(System.lineSeparator());
        String[] reqType = req[0].split("/");
        String parseMethod = reqType[0].trim();
        String parseMode = reqType[1].trim();
        String parseQueue = reqType[2].split("\\s")[0].trim();
        if (parseMethod.equals("GET")) {
            if (parseMode.equals("topic")) {
                parseParam = reqType[3].split("\\s")[0].trim();
            }
        } else {
            parseParam = req[7];
        }

        return new Req(parseMethod, parseMode, parseQueue, parseParam);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Req req = (Req) o;
        return Objects.equals(httpRequestType, req.httpRequestType)
                && Objects.equals(poohMode, req.poohMode)
                && Objects.equals(sourceName, req.sourceName)
                && Objects.equals(param, req.param);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpRequestType, poohMode, sourceName, param);
    }
}
