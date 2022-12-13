package com.chyern.mysql;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chyern.mysql.domain.PageResponse;
import com.chyern.mysql.util.PageUtil;
import com.chyern.mysql.util.WrapperUtil;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/12/13 11:55
 */
public abstract class BaseDaoImpl<T> implements BaseDao<T>, InitializingBean {

    private BaseMapper<T> mapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        mapper = initMapper();
    }

    public abstract BaseMapper<T> initMapper();

    @Override
    public int insert(T t) {
        return mapper.insert(t);
    }

    @Override
    public int insertBatch(List<T> list) {
        return mapper.insertBatch(list);
    }

    @Override
    public int delete(T t) {
        return mapper.delete(WrapperUtil.buildQueryWrapper(t));
    }

    @Override
    public List<T> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public T selectOne(T t) {
        QueryWrapper<T> tQueryWrapper = WrapperUtil.buildQueryWrapper(t);
        return mapper.selectOne(tQueryWrapper);
    }

    @Override
    public List<T> selectList(T t) {
        QueryWrapper<T> tQueryWrapper = WrapperUtil.buildQueryWrapper(t);
        return mapper.selectList(tQueryWrapper);
    }

    @Override
    public PageResponse<T> selectPage(Long pageNo, Long pageSize, T t) {
        IPage<T> iPage = new Page<>(pageNo, pageSize);
        QueryWrapper<T> tQueryWrapper = WrapperUtil.buildQueryWrapper(t);
        IPage<T> page = mapper.selectPage(iPage, tQueryWrapper);
        return PageUtil.buildPageResponse(page, item -> item);
    }

    @Override
    public Integer selectCount(T t) {
        return mapper.selectCount(WrapperUtil.buildQueryWrapper(t));
    }
}
