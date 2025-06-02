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
package org.opsli.modulars.system.role.web;

import com.google.common.collect.Lists;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.opsli.api.base.result.ResultWrapper;
import org.opsli.api.web.system.role.RoleMenuRefApi;
import org.opsli.api.wrapper.system.role.RoleMenuRefModel;
import org.opsli.api.wrapper.system.user.UserModel;
import org.opsli.common.annotation.ApiRestController;
import org.opsli.common.annotation.ApiVersion;
import org.opsli.common.exception.ServiceException;
import org.opsli.core.autoconfigure.properties.GlobalProperties;
import org.opsli.core.log.annotation.OperateLogger;
import org.opsli.core.log.enums.ModuleEnum;
import org.opsli.core.log.enums.OperationTypeEnum;
import org.opsli.core.msg.CoreMsg;
import org.opsli.core.utils.UserUtil;
import org.opsli.modulars.system.SystemMsg;
import org.opsli.modulars.system.menu.entity.SysMenu;
import org.opsli.modulars.system.role.service.IRoleMenuRefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 角色权限 Controller
 *
 * @author Pace
 * @date 2020-09-16 17:33
 */
@Tag(name = RoleMenuRefApi.TITLE)
@Slf4j
@ApiRestController("/{ver}/system/role/perms")
public class RoleMenuRefRestController implements RoleMenuRefApi {

    /** 配置类 */
    @Autowired
    protected GlobalProperties globalProperties;

    @Autowired
    private IRoleMenuRefService iRoleMenuRefService;

    /**
     * 获得当前已有权限
     * @param model roleId 角色Id
     * @return ResultWrapper
     */
    @Operation(summary = "获得当前已有权限")
    @PreAuthorize("hasAuthority('system_role_setMenuPerms')")
    @Override
    public ResultWrapper<?> getPerms(RoleMenuRefModel model) {
        if(model == null){
            return ResultWrapper.getCustomResultWrapper(SystemMsg.EXCEPTION_ROLE_ID_NOT_NULL);
        }

        List<SysMenu> perms = iRoleMenuRefService.getPerms(model.getRoleId());
        List<String> permsIds = Lists.newArrayListWithCapacity(perms.size());
        if(!perms.isEmpty()){
            for (SysMenu perm : perms) {
                permsIds.add(perm.getId());
            }

            // 按照parentId分组
            Map<String, List<SysMenu>> groupMap = perms.stream()
                    .collect(Collectors.groupingBy(SysMenu::getParentId));

            // 获得分组key 根据分组key 删除分组父ID
            for (String key : groupMap.keySet()) {
                permsIds.remove(key);
            }
        }

        return ResultWrapper.getSuccessResultWrapper(permsIds);
    }

    /**
     * 获得当前已有权限 (针对Vue3 兼容)
     * @param model roleId 角色Id
     * @return ResultWrapper
     */
    @Operation(summary = "获得当前已有权限 (针对Vue3 兼容)")
    @PreAuthorize("hasAuthority('system_role_setMenuPerms')")
    @Override
    @ApiVersion(2)
    public ResultWrapper<?> getPermsV2(RoleMenuRefModel model) {
        if(model == null){
            return ResultWrapper.getCustomResultWrapper(SystemMsg.EXCEPTION_ROLE_ID_NOT_NULL);
        }

        List<SysMenu> perms = iRoleMenuRefService.getPerms(model.getRoleId());
        List<String> permsIds = Lists.newArrayListWithCapacity(perms.size());
        if(!perms.isEmpty()){
            for (SysMenu perm : perms) {
                permsIds.add(perm.getId());
            }
        }

        return ResultWrapper.getSuccessResultWrapper(permsIds);
    }


    /**
     * 设置菜单权限
     * @param model 模型
     * @return ResultWrapper
     */
    @Operation(summary = "设置菜单权限")
    @PreAuthorize("hasAuthority('system_role_setMenuPerms')")
    @OperateLogger(description = "设置菜单权限",
            module = ModuleEnum.MODULE_ROLE, operationType = OperationTypeEnum.UPDATE, db = true)
    @Override
    public ResultWrapper<?> setPerms(RoleMenuRefModel model) {
        // 演示模式 不允许操作
        this.demoError();

        if(model == null){
            return ResultWrapper.getErrorResultWrapper().setMsg("设置权限失败");
        }

        boolean ret = iRoleMenuRefService.setPerms(model.getRoleId(),
                model.getPermsIds());
        if(ret){
            return ResultWrapper.getSuccessResultWrapper();
        }
        // 权限设置失败
        return ResultWrapper.getCustomResultWrapper(SystemMsg.EXCEPTION_ROLE_PERMS_ERROR);
    }


    /**
     * 设置菜单权限
     * @param model 模型
     * @return ResultWrapper
     */
    @Operation(summary = "设置菜单权限")
    @PreAuthorize("hasAuthority('system_role_setMenuPerms')")
    @OperateLogger(description = "设置菜单权限",
            module = ModuleEnum.MODULE_ROLE, operationType = OperationTypeEnum.UPDATE, db = true)
    @Override
    public ResultWrapper<?> setPermsV2(RoleMenuRefModel model) {
        // 演示模式 不允许操作
        this.demoError();

        if(model == null){
            return ResultWrapper.getErrorResultWrapper().setMsg("设置权限失败");
        }

        boolean ret = iRoleMenuRefService.setPermsV2(model.getRoleId(),
                model.getPermsIds());
        if(ret){
            return ResultWrapper.getSuccessResultWrapper();
        }
        // 权限设置失败
        return ResultWrapper.getCustomResultWrapper(SystemMsg.EXCEPTION_ROLE_PERMS_ERROR);
    }


    /**
     * 演示模式
     */
    private void demoError(){
        UserModel user = UserUtil.getUser();
        // 演示模式 不允许操作 （超级管理员可以操作）
        if(globalProperties.isEnableDemo() &&
                !StringUtils.equals(UserUtil.SUPER_ADMIN, user.getUsername())){
            throw new ServiceException(CoreMsg.EXCEPTION_ENABLE_DEMO);
        }
    }

}
