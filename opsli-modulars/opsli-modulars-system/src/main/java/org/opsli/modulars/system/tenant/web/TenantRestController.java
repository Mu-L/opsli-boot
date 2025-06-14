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
package org.opsli.modulars.system.tenant.web;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.opsli.api.base.result.ResultWrapper;
import org.opsli.api.web.system.tenant.TenantApi;
import org.opsli.api.wrapper.system.tenant.TenantModel;
import org.opsli.common.annotation.ApiRestController;
import org.opsli.common.enums.DictType;
import org.opsli.common.utils.WrapperUtil;
import org.opsli.core.base.controller.BaseRestController;
import org.opsli.core.log.annotation.OperateLogger;
import org.opsli.core.log.enums.ModuleEnum;
import org.opsli.core.log.enums.OperationTypeEnum;
import org.opsli.core.persistence.Page;
import org.opsli.core.persistence.querybuilder.GenQueryBuilder;
import org.opsli.core.persistence.querybuilder.QueryBuilder;
import org.opsli.core.persistence.querybuilder.WebQueryBuilder;
import org.opsli.modulars.system.tenant.entity.SysTenant;
import org.opsli.modulars.system.tenant.service.ITenantService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Optional;


/**
 * 租户管理 Controller
 *
 * @author Pace
 * @date 2020-09-16 17:33
 */
@Tag(name = TenantApi.TITLE)
@Slf4j
@ApiRestController("/{ver}/system/tenant")
public class TenantRestController extends BaseRestController<SysTenant, TenantModel, ITenantService>
        implements TenantApi {


    /**
     * 变更租户状态账户
     * @return ResultWrapper
     */
    @Operation(summary = "变更租户状态账户")
    @PreAuthorize("hasAuthority('system_tenant_enable')")
    @OperateLogger(description = "变更租户状态账户",
            module = ModuleEnum.MODULE_TENANT, operationType = OperationTypeEnum.UPDATE, db = true)
    @Override
    public ResultWrapper<?> enableTenant(String tenantId, String enable) {
        // 演示模式 不允许操作
        super.demoError();

        // 变更租户状态账户
        boolean enableStatus = IService.enableTenant(tenantId, enable);
        if(!enableStatus){
            return ResultWrapper.getErrorResultWrapper().setMsg("变更租户状态账户失败");
        }
        return ResultWrapper.getSuccessResultWrapper();
    }

    /**
     * 租户 查一条
     * @param model 模型
     * @return ResultWrapper
     */
    @Operation(summary = "获得单条租户 - ID")
    @PreAuthorize("hasAuthority('system_tenant_select')")
    @Override
    public ResultWrapper<TenantModel> get(TenantModel model) {
        model = IService.get(model);
        return ResultWrapper.getSuccessResultWrapper(model);
    }

    /**
     * 租户 查询分页
     * @param pageNo 当前页
     * @param pageSize 每页条数
     * @param request request
     * @return ResultWrapper
     */
    @Operation(summary = "获得分页数据 - 查询构造器")
    //@PreAuthorize("hasAuthority('system_tenant_select')")
    @Override
    public ResultWrapper<?> findPage(Integer pageNo, Integer pageSize, HttpServletRequest request) {

        QueryBuilder<SysTenant> queryBuilder = new WebQueryBuilder<>(IService.getEntityClass(), request.getParameterMap());
        Page<SysTenant, TenantModel> page = new Page<>(pageNo, pageSize);
        page.setQueryWrapper(queryBuilder.build());
        page = IService.findPage(page);

        return ResultWrapper.getSuccessResultWrapper(page.getPageData());
    }

    /**
     * 租户 新增
     * @param model 模型
     * @return ResultWrapper
     */
    @Operation(summary = "新增租户")
    @PreAuthorize("hasAuthority('system_tenant_insert')")
    @OperateLogger(description = "新增租户",
            module = ModuleEnum.MODULE_TENANT, operationType = OperationTypeEnum.INSERT, db = true)
    @Override
    public ResultWrapper<?> insert(TenantModel model) {
        // 调用新增方法
        IService.insert(model);
        return ResultWrapper.getSuccessResultWrapperByMsg("新增租户成功");
    }

    /**
     * 租户 修改
     * @param model 模型
     * @return ResultWrapper
     */
    @Operation(summary = "修改租户")
    @PreAuthorize("hasAuthority('system_tenant_update')")
    @OperateLogger(description = "修改租户",
            module = ModuleEnum.MODULE_TENANT, operationType = OperationTypeEnum.UPDATE, db = true)
    @Override
    public ResultWrapper<?> update(TenantModel model) {
        // 演示模式 不允许操作
        super.demoError();

        // 调用修改方法
        IService.update(model);
        return ResultWrapper.getSuccessResultWrapperByMsg("修改租户成功");
    }


    /**
     * 租户 删除
     * @param id ID
     * @return ResultWrapper
     */
    @Operation(summary = "删除租户数据")
    @PreAuthorize("hasAuthority('system_tenant_delete')")
    @OperateLogger(description = "删除租户数据",
            module = ModuleEnum.MODULE_TENANT, operationType = OperationTypeEnum.DELETE, db = true)
    @Override
    public ResultWrapper<?> del(String id){
        // 演示模式 不允许操作
        super.demoError();

        IService.delete(id);
        return ResultWrapper.getSuccessResultWrapperByMsg("删除租户成功");
    }


    /**
     * 租户 批量删除
     * @param ids ID 数组
     * @return ResultWrapper
     */
    @Operation(summary = "批量删除租户数据")
    @PreAuthorize("hasAuthority('system_tenant_delete')")
    @OperateLogger(description = "批量删除租户数据",
            module = ModuleEnum.MODULE_TENANT, operationType = OperationTypeEnum.DELETE, db = true)
    @Override
    public ResultWrapper<?> delAll(String ids){
        // 演示模式 不允许操作
        super.demoError();

        String[] idArray = Convert.toStrArray(ids);
        IService.deleteAll(idArray);
        return ResultWrapper.getSuccessResultWrapperByMsg("批量删除租户成功");
    }


    /**
     * 租户 Excel 导出认证
     *
     * @param type 类型
     * @param request request
     */
    @Operation(summary = "Excel 导出认证")
    @PreAuthorize("hasAnyAuthority('system_tenant_export', 'system_tenant_import')")
    @Override
    public ResultWrapper<String> exportExcelAuth(String type, HttpServletRequest request) {
        Optional<String> certificateOptional =
                super.excelExportAuth(type, TenantApi.SUB_TITLE, request);
        if(certificateOptional.isEmpty()){
            return ResultWrapper.getErrorResultWrapper();
        }
        return ResultWrapper.getSuccessResultWrapper(certificateOptional.get());
    }


    /**
     * 租户 Excel 导出
     * @param response response
     */
    @Operation(summary = "导出Excel")
    @PreAuthorize("hasAuthority('system_tenant_export')")
    @OperateLogger(description = "导出Excel",
            module = ModuleEnum.MODULE_TENANT, operationType = OperationTypeEnum.SELECT, db = true)
    @Override
    public void exportExcel(String certificate, HttpServletResponse response) {
        // 导出Excel
        super.excelExport(certificate, response);
    }

    /**
     * 租户 Excel 导入
     * @param request 文件流 request
     * @return ResultWrapper
     */
    @Operation(summary = "导入Excel")
    @PreAuthorize("hasAuthority('system_tenant_import')")
    @OperateLogger(description = "租户 Excel 导入",
            module = ModuleEnum.MODULE_TENANT, operationType = OperationTypeEnum.INSERT, db = true)
    @Override
    public ResultWrapper<?> importExcel(MultipartHttpServletRequest request) {
        return super.importExcel(request);
    }


    // =========================

    /**
     * 获得已启用租户 查一条
     * @param tenantId 模型
     * @return ResultWrapper
     */
    @Operation(summary = "获得已启用租户 - ID")
    @Override
    public ResultWrapper<TenantModel> getTenantByUsable(String tenantId) {
        QueryBuilder<SysTenant> queryBuilder = new GenQueryBuilder<>();
        QueryWrapper<SysTenant> queryWrapper = queryBuilder.build();
        queryWrapper.eq("id", tenantId)
                .eq("enable", DictType.NO_YES_YES.getValue());
        SysTenant entity = IService.getOne(queryWrapper);

        return ResultWrapper.getSuccessResultWrapper(
                WrapperUtil.transformInstance(entity, IService.getModelClass())
        );
    }

}
