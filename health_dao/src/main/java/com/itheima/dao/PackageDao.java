package com.itheima.dao;

import com.itheima.pojo.Package;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @user: Eric
 * @date: 2019/12/19
 * @description:
 */
public interface PackageDao {

    /**
     * 添加套餐
     * @param pkg
     */
    void add(Package pkg);

    /**
     * 添加套餐与检查组的关系
     * @param pkgId
     * @param checkgroupId
     */
    void addPackageCheckGroup(@Param("pkgId") Integer pkgId, @Param("checkgroupId") Integer checkgroupId);

    /**
     * 查询所有套餐
     * @return
     */
    List<Package> findAll();

    /**
     * 查询套餐详情
     * @param id
     * @return
     */
    Package findDetailById(int id);

    Package findDetailById2(int id);

    Package findDetailById3(int id);

    /**
     * 查询套餐信息
     * @param id
     * @return
     */
    Package findById(int id);

    /**
     * 获取套餐分组统计信息
     * @return
     */
    List<Map<String,Object>> getPackageReport();
}
