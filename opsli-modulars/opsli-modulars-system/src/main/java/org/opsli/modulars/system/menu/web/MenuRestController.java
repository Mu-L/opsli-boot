package org.opsli.modulars.system.menu.web;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.opsli.api.base.result.ResultVo;
import org.opsli.api.web.system.menu.MenuApi;
import org.opsli.api.wrapper.system.menu.MenuModel;
import org.opsli.common.annotation.ApiRestController;
import org.opsli.core.base.concroller.BaseRestController;
import org.opsli.core.persistence.Page;
import org.opsli.core.persistence.querybuilder.QueryBuilder;
import org.opsli.core.persistence.querybuilder.WebQueryBuilder;
import org.opsli.modulars.system.menu.entity.SysMenu;
import org.opsli.modulars.system.menu.service.IMenuService;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @BelongsProject: opsli-boot
 * @BelongsPackage: org.opsli.modulars.test.web
 * @Author: Parker
 * @CreateTime: 2020-09-13 17:40
 * @Description: 菜单
 */
@Slf4j
@ApiRestController("/sys/menu")
public class MenuRestController extends BaseRestController<SysMenu, MenuModel, IMenuService>
        implements MenuApi {


    /**
     * 菜单 查一条
     * @param model 模型
     * @return ResultVo
     */
    @ApiOperation(value = "获得单条菜单", notes = "获得单条菜单 - ID")
    @Override
    public ResultVo<MenuModel> get(MenuModel model) {
        // 如果系统内部调用 则直接查数据库
        if(model != null && model.getIzApi() != null && model.getIzApi()){
            model = IService.get(model);
        }
        return ResultVo.success(model);
    }

    /**
     * 菜单 查询分页
     * @param pageNo 当前页
     * @param pageSize 每页条数
     * @param request request
     * @return ResultVo
     */
    @ApiOperation(value = "获得分页数据", notes = "获得分页数据 - 查询构造器")
    @Override
    public ResultVo<?> findPage(Integer pageNo, Integer pageSize, HttpServletRequest request) {

        QueryBuilder<SysMenu> queryBuilder = new WebQueryBuilder<>(SysMenu.class, request.getParameterMap());
        Page<SysMenu, MenuModel> page = new Page<>(pageNo, pageSize);
        page.setQueryWrapper(queryBuilder.build());
        page = IService.findPage(page);

        return ResultVo.success(page.getBootstrapData());
    }

    /**
     * 菜单 新增
     * @param model 模型
     * @return ResultVo
     */
    @ApiOperation(value = "新增菜单", notes = "新增菜单")
    @Override
    public ResultVo<?> insert(MenuModel model) {
        // 调用新增方法
        IService.insert(model);
        return ResultVo.success("新增菜单成功");
    }

    /**
     * 菜单 修改
     * @param model 模型
     * @return ResultVo
     */
    @ApiOperation(value = "修改菜单", notes = "修改菜单")
    @Override
    public ResultVo<?> update(MenuModel model) {
        // 调用修改方法
        IService.update(model);
        return ResultVo.success("修改菜单成功");
    }


    /**
     * 菜单 删除
     * @param id ID
     * @return ResultVo
     */
    @ApiOperation(value = "删除菜单数据", notes = "删除菜单数据")
    @Override
    public ResultVo<?> del(String id){
        IService.delete(id);
        return ResultVo.success("删除菜单成功");
    }


    /**
     * 菜单 批量删除
     * @param ids ID 数组
     * @return ResultVo
     */
    @ApiOperation(value = "批量删除菜单数据", notes = "批量删除菜单数据")
    @Override
    public ResultVo<?> delAll(String[] ids){
        IService.deleteAll(ids);
        return ResultVo.success("批量删除菜单成功");
    }


    /**
     * 菜单 Excel 导出
     * @param request request
     * @param response response
     * @return ResultVo
     */
    @ApiOperation(value = "导出Excel", notes = "导出Excel")
    @Override
    public ResultVo<?> exportExcel(HttpServletRequest request, HttpServletResponse response) {
        QueryBuilder<SysMenu> queryBuilder = new WebQueryBuilder<>(SysMenu.class, request.getParameterMap());
        return super.excelExport(MenuApi.TITLE, queryBuilder.build(), response);
    }

    /**
     * 菜单 Excel 导入
     * @param request 文件流 request
     * @return ResultVo
     */
    @ApiOperation(value = "导入Excel", notes = "导入Excel")
    @Override
    public ResultVo<?> excelImport(MultipartHttpServletRequest request) {
        return super.excelImport(request);
    }

    /**
     * 菜单 Excel 下载导入模版
     * @param response response
     * @return ResultVo
     */
    @ApiOperation(value = "导出Excel模版", notes = "导出Excel模版")
    @Override
    public ResultVo<?> importTemplate(HttpServletResponse response) {
        return super.importTemplate(MenuApi.TITLE, response);
    }

}