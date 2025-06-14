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
package org.opsli.api.base.encrypt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.opsli.common.annotation.validator.Validator;
import org.opsli.common.annotation.validator.ValidatorLenMax;
import org.opsli.common.enums.ValidatorType;

import java.io.Serial;
import java.io.Serializable;

/**
 * 登陆 加解密
 *
 * @author Pace
 * @date 2021-01-24 12:48 下午
 **/
@Data
public class EncryptModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Validator({ValidatorType.IS_NOT_NULL})
    @ValidatorLenMax(2000)
    @Schema(description = "加密数据")
    private String encryptData;

}
