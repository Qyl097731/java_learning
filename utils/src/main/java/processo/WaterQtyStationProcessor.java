package processo;


import com.alibaba.fastjson.JSONObject;
import entity.WaterQtyStationEntity;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qyl
 * @program WaterQtyStationProcessor.java
 * @createTime 2022-08-25 17:13
 */
public class WaterQtyStationProcessor implements PageProcessor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Site site;

    public WaterQtyStationProcessor(){
        setSite();
    }

    private void setSite() {
        // 设置站点信息，模拟浏览器
        site = Site.me().setRetryTimes(30).setSleepTime(1000).setTimeOut(20000)
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.66 Safari/537.36")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                .addHeader("Cache-Control", "max-age=0")
                .addHeader("Upgrade-Insecure-Requests:", "1")
                .addHeader("Host", "172.19.2.142:86");
    }

    @Override
    public void process(Page page) {
        String content = page.getHtml().xpath("body/text()").get();
        //两个bean的大小写不一致，统一转换成小写
        content = content.toLowerCase();
        //logger.debug(content);
        List<String> data = null;
        try {
            data = new JsonPathSelector("$.records").selectList(content);
        } catch (Exception e) {
            logger.info("无法识别爬取的内容:{}", content);
        }
        List<WaterQtyStationEntity> entities = null;
        if (!CollectionUtils.isEmpty(data)) {
            entities = new ArrayList<>();
            for (String lines : data) {
                WaterQtyStationEntity singleBean = JSONObject.parseObject(lines, WaterQtyStationEntity.class);
                entities.add(singleBean);
            }
        }
        page.putField("entities", entities);
    }

    @Override
    public Site getSite() {
        return site;
    }
}
