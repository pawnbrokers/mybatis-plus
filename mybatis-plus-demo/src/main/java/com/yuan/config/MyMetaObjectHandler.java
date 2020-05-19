package com.yuan.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component//别忘了加到容器中
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入时候的填充策略
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ............");
        // setFieldValByName(String fieldName, Object fieldVal, MetaObjectmetaObject
        this.setFieldValByName("create_time",new Date(),metaObject);
        this.setFieldValByName("update_time",new Date(),metaObject);

    }

    /**
     * 更新时候的填充策略
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ............");
        this.setFieldValByName("update_time",new Date(),metaObject);
    }
}
