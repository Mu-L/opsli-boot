/**
 * Copyright 2020 OPSLI 快速开发平台 https://www.opsli.com
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.opsli.core.cache;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.opsli.core.autoconfigure.properties.CacheProperties;
import org.opsli.core.msg.CoreMsg;
import org.opsli.core.utils.ThrowExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static org.opsli.common.constants.OrderConstants.UTIL_ORDER;

/**
 * 缓存工具类
 *
 * @author Pace
 * @date 2020-09-16 16:20
 */
@Slf4j
@Order(UTIL_ORDER)
@Component
public class CacheUtil {


    /** 热点数据前缀 */
    private static String PREFIX_NAME;

    /** 增加初始状态开关 防止异常使用 */
    private static boolean IS_INIT;



    /**
     * 格式化Key
     * @param key 缓存Key
     * @return String
     */
    public static String formatKey(String key){
        // 判断 工具类是否初始化完成
        ThrowExceptionUtil.isThrowException(!IS_INIT,
                CoreMsg.OTHER_EXCEPTION_UTILS_INIT);

        return StrUtil.format(key, PREFIX_NAME);
    }


    /**
     * 初始化
     */
    @Autowired
    public void init(CacheProperties cacheProperties){
        CacheUtil.PREFIX_NAME = Convert.toStr(cacheProperties.getPrefix(), "opsli");
        IS_INIT = true;
    }

}
