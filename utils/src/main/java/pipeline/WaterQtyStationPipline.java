package pipeline;

import entity.WaterQtyStationEntity;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * 数据管道处理
 * @author qyl
 */
public class WaterQtyStationPipline implements Pipeline {
    //日志
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void process(ResultItems resultItems, Task task) {

        List<WaterQtyStationEntity> entities = resultItems.get("entities");
        if (!CollectionUtils.isEmpty(entities)) {
            logger.debug(String.valueOf(entities.size()));
            //遍历接口数据
        }
    }
}
