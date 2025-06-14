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
package org.opsli.api.wrapper.system.menu;

import com.alibaba.excel.annotation.ExcelIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.opsli.common.annotation.validator.Validator;
import org.opsli.common.annotation.validator.ValidatorLenMax;
import org.opsli.common.enums.ValidatorType;

import java.io.Serial;
import java.io.Serializable;

/**
 * 创建完整菜单
 *
 * @author Pace
 * @date 2020-09-16 17:33
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuFullModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 上级菜单ID */
    @Schema(description = "上级菜单ID")
    @ExcelIgnore
    @Validator({ValidatorType.IS_NOT_NULL})
    @ValidatorLenMax(19)
    private String parentId;

    /** 菜单名称 */
    @Schema(description = "菜单名称")
    @ExcelIgnore
    @Validator({ValidatorType.IS_NOT_NULL})
    @ValidatorLenMax(100)
    private String title;

    /** 模块名 */
    @Schema(description = "模块名")
    @ExcelIgnore
    @Validator({ValidatorType.IS_NOT_NULL})
    @ValidatorLenMax(40)
    private String moduleName;

    /** 子模块名 */
    @Schema(description = "子模块名")
    @ExcelIgnore
    @ValidatorLenMax(40)
    private String subModuleName;

}
