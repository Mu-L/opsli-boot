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
package org.opsli.modulars.system.menu.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.opsli.core.base.entity.BaseEntity;

/**
 * 菜单表
 *
 * @author Pace
 * @date 2020-09-16 17:33
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysMenu extends BaseEntity {


    /** 父级主键 */
    private String parentId;

    /** 父级主键 集合 */
    private String parentIds;

    /** 权限编号 */
    private String permissions;

    /** 菜单名称 */
    private String menuName;

    /** 图标 */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String icon;

    /** 项目类型: 1-菜单 2-按钮3 -链接 */
    private String type;

    /** url地址 */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String url;

    /** 组件 - vue 对应组件 */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String component;

    /** 重定向 */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String redirect;

    /** 排序 */
    private Integer sortNo;

    /** 是否隐藏 0为否 1为是 */
    private String hidden;

    /** 是否总是显示 0为否 1为是 */
    private String alwaysShow;

    /** 标签 */
    private String label;

    // ========================================

    /** 逻辑删除字段 */
    @TableLogic
    private String deleted;

}
