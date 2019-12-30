package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.PackageDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Package;
import com.itheima.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @user: Eric
 * @date: 2019/12/19
 * @description:
 */
@Service(interfaceClass = PackageService.class)
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackageDao packageDao;


    @Override
    @Transactional
    public void add(Package pkg, Integer[] checkgroupIds) {
        // 添加套餐
        packageDao.add(pkg);
        // 获取套餐的id
        Integer pkgId = pkg.getId();
        // 循环添加套餐与检查组的关系
        if (null != checkgroupIds) {
            for (Integer checkgroupId : checkgroupIds) {
                packageDao.addPackageCheckGroup(pkgId, checkgroupId);
            }
        }
    }

    /**
     * 查询所有套餐
     *
     * @return
     */
    @Override
    public List<Package>  findAll() {
        return packageDao.findAll();
    }

    /**
     * 查询套餐详情
     *
     * @param id
     * @return
     */
    @Override
    public Package findDetailById(int id) {
        //通过套餐id查询套餐详情, 这个套餐对象里包含检查组数据与检查项的数据
        Package pkg = packageDao.findDetailById(id);
        return pkg;
    }

    /**
     * 查询套餐详情
     *
     * @param id
     * @return
     */
    @Override
    public Package findDetailById2(int id) {
        //通过套餐id查询套餐详情, 这个套餐对象里包含检查组数据与检查项的数据
        Package pkg = packageDao.findDetailById2(id);
        return pkg;
    }

    /**
     * 查询套餐信息
     *
     * @param id
     * @return
     */
    @Override
    public Package findById(int id) {
        return packageDao.findById(id);
    }



    /**
     * 获取套餐分组统计信息
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getPackageReport() {
        return packageDao.getPackageReport();
    }

    /**
     * 分页查询，模糊查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }

        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Package> pages = packageDao.selectByCondition(queryPageBean.getQueryString());

        PageResult pageResult = new PageResult(pages.getTotal(), pages.getResult());
        return pageResult;
    }
}
