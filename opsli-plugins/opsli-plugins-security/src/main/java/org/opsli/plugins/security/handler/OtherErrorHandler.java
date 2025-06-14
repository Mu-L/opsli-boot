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
package org.opsli.plugins.security.handler;

import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opsli.plugins.security.utils.WebUtils;
import org.opsli.plugins.security.vo.AuthResultWrapper;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 其他异常 处理器
 * @author Pace
 * @date 2022-07-17 12:57 PM
 **/
@Slf4j
@AllArgsConstructor
@Component
public class OtherErrorHandler implements LoginAccessDeniedListener {

    @Override
    public boolean handle(Object loginModel, HttpServletRequest request, HttpServletResponse response, Exception e) {
        // 记录异常日志
        log.error("认证其他未知异常 => 异常：{}",
                e.getMessage(),
                e);

        AuthResultWrapper<?> customAuthResultWrapper = AuthResultWrapper.getErrorResultWrapper();
        WebUtils.renderString(request, response, JSONUtil.toJsonStr(customAuthResultWrapper));

        return false;
    }

}
