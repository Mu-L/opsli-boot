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
package org.opsli.api.wrapper.system.user;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.opsli.api.wrapper.system.org.SysOrgModel;
import org.opsli.common.annotation.validator.Validator;
import org.opsli.common.annotation.validator.ValidatorLenMax;
import org.opsli.common.enums.ValidatorType;

import java.io.Serializable;
import java.util.List;

/**
 * 用户组织关系表
 *
 * @author Pace
 * @date 2020-09-16 17:33
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ExcelIgnoreUnannotated
public class UserOrgRefModel implements Serializable {

    /** ID */
    @Schema(description = "ID")
    private String id;

    /** 用户ID */
    @Schema(description = "用户ID")
    @Validator({ValidatorType.IS_NOT_NULL})
    @ValidatorLenMax(50)
    private String userId;

    /** 当前组织 */
    @Schema(description = "当前组织")
    private String orgId;

    /** 组织ID集合 xxx,xxx */
    @Schema(description = "组织ID集合")
    private String orgIds;

    /** 是否默认 */
    @Schema(description = "是否默认")
    private String izDef;

}
