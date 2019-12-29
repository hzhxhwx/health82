package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.zookeeper.server.SessionTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @user: Eric
 * @date: 2019/12/17
 * @description:
 */
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 添加检查组
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    @Transactional
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        // 添加检查组
        checkGroupDao.add(checkGroup);
        // 获取检查组的id
        Integer checkGroupId = checkGroup.getId();
        // 遍历checkitemIds，
        if(null != checkitemIds){
            // 有选择检查项
            for (Integer checkitemId : checkitemIds) {
                //添加检查组与检查项的关系
                checkGroupDao.addCheckGroupCheckItem(checkGroupId, checkitemId);
            }
        }
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        // 判断是否有条件
        if(!StringUtils.isEmpty(queryPageBean.getQueryString())){
            // 有查询条件，补%,模糊查询
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        // 使用分页插件
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 查询会被分页
        Page<CheckGroup> page = checkGroupDao.findByCondition(queryPageBean.getQueryString());
        // 封装分页结果
        PageResult<CheckGroup> pageResult = new PageResult<CheckGroup>(page.getTotal(), page.getResult());
        return pageResult;
    }

    /**
     * 通过id查询检查组
     * @param id
     * @return
     */
    @Override
    public CheckGroup findById(int id) {
        return checkGroupDao.findById(id);
    }

    /**
     * 查询选中的检查项id集合
     * @param checkGroupId
     * @return
     */
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(int checkGroupId) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(checkGroupId);
    }

    /**
     * 修改检查组
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    @Transactional
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        // 更新检查组
        checkGroupDao.update(checkGroup);
        // 删除旧的关系
        checkGroupDao.deleteAssociation(checkGroup.getId());
        // 遍历添加新的关系
        if(null != checkitemIds){
            // 创建另一个会话，ExecutorType：执行器的类型 为Batch 批次, autoCommit: 是否自动提交
            SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
            // 默认由spring创建的那个dao, 执行类型是simple, 这会使用的是Batch，重新获取。
            // mybatis每个dao只能支持一种类型的执行器
            CheckGroupDao checkGroupDaoMapper = sqlSession.getMapper(CheckGroupDao.class);
            for (Integer checkItemId : checkitemIds) {
                // 此时就是使用了批量添加
                checkGroupDaoMapper.addCheckGroupCheckItem(checkGroup.getId(), checkItemId);
                //checkGroupDao.addCheckGroupCheckItem(checkGroup.getId(), checkItemId);
            }
            // 提交session
            sqlSession.commit();
            sqlSession.flushStatements();
            sqlSession.close();
        }
    }

    /**
     *  查询所有检查组
     * @return
     */
    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}
