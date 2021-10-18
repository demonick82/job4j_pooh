package servises;

import org.junit.Test;
import req.Req;
import req.Resp;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class QueueServiceTest {
    @Test
    public void whenPostThenGetQueue() {
        QueueService queueService = new QueueService();
        String paramForPostMethod = "temperature=18";
        /* Добавляем данные в очередь weather. Режим queue */
        queueService.process(
                new Req("POST", "queue", "weather", paramForPostMethod)
        );
        /* Забираем данные из очереди weather. Режим queue */
        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result.text(), is("temperature=18"));
    }

    @Test
    public void whenPostThenGet2Queue() {
        QueueService queueService = new QueueService();
        String paramForPostMethod = "temperature=18";
        String paramForPostMethod2 = "Saint Petersburg";

        /* Добавляем данные в очередь weather. Режим queue */
        queueService.process(
                new Req("POST", "queue", "weather", paramForPostMethod)
        );
        /* Забираем данные из очереди weather. Режим queue */
        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null)
        );

        queueService.process(
                new Req("POST", "queue", "city", paramForPostMethod2)
        );

        Resp result2 = queueService.process(
                new Req("GET", "queue", "city", null)
        );


        assertThat(result.text(), is("temperature=18"));
        assertThat(result2.text(), is("Saint Petersburg"));

    }
}