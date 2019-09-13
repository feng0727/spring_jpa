package com.ccb.domain.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: yangfei
 * @desc: 工具类
 * @date: created in 2019/3/16 11:09
 * @modifed by:
 */
@Slf4j
public class Utils {

    /**
     * @param fromList
     * @param tClass
     * @param <F>
     * @param <T>
     * @return
     * @desc 将entityList转换成modelList
     */
    public static <F, T> List<T> entityListToModelList(List<F> fromList, Class<T> tClass) {
        if (fromList.isEmpty() || fromList == null) {
            return null;
        }
        List<T> tList = new ArrayList<>();
        for (F f : fromList) {
            T t = entityToModel(f, tClass);
            tList.add(t);
        }
        return tList;
    }

    /**
     * @param entity
     * @param modelClass
     * @param <F>
     * @param <T>
     * @return
     * @desc Entity属性的值赋值到Model
     */
    public static <F, T> T entityToModel(F entity, Class<T> modelClass) {
        log.debug("entityToModel : Entity属性的值赋值到Model");
        Object model = null;
        if (entity == null || modelClass == null) {
            return null;
        }

        try {
            model = modelClass.newInstance();
        } catch (InstantiationException e) {
            log.error("entityToModel : 实例化异常", e);
        } catch (IllegalAccessException e) {
            log.error("entityToModel : 安全权限异常", e);
        }
        BeanUtils.copyProperties(entity, model);
        return (T) model;
    }
}
