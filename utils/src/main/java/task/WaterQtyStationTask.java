package task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pipeline.WaterQtyStationPipline;
import processo.WaterQtyStationProcessor;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.utils.HttpConstant;
import util.ThreadPoolUtil;

/**
 * @author qyl
 * @program WaterQtyMonHourTask.java
 * @Description 水质监测点基本信息
 * @createTime 2022-08-12 13:04
 */
@Component
public class WaterQtyStationTask {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String URL = "www.****.com";

    @Scheduled(cron = "0 10 0 * * ?")
    public void runDown() {
        //对接口地址进行字符拼接
        Request request = new Request(URL);
        request.setMethod(HttpConstant.Method.GET);
        //执行爬取任务
        Spider spider=Spider.create(new WaterQtyStationProcessor());
        //调用接口地址 在Pipline中进行数据处理
        spider.addRequest(request).addPipeline(new WaterQtyStationPipline())
                .thread(ThreadPoolUtil.getExecutorService(),1).run();
    }
}
