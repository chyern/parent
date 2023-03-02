package com.chyern.mysql;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chyern.mysql.domain.PageRequest;
import com.chyern.mysql.domain.PageResponse;
import com.chyern.mysql.util.PageUtil;
import com.chyern.mysql.util.WrapperUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;

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
    public void afterPropertiesSet() {
        mapper = initMapper();
    }

    public abstract <R extends BaseMapper<T>> R initMapper();

    @Override
    public int insert(T t) {
        return mapper.insert(t);
    }

    @Override
    public int insertBatch(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }
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
    public PageResponse<T> selectPage(PageRequest<T> pageRequest) {
        IPage<T> iPage = new Page<>(pageRequest.getPageNo(), pageRequest.getPageSize());
        QueryWrapper<T> tQueryWrapper = WrapperUtil.buildQueryWrapper(pageRequest.getData());
        mapper.selectPage(iPage, tQueryWrapper);
        return PageUtil.buildPageResponse(iPage);
    }

    @Override
    public Long selectCount(T t) {
        return mapper.selectCount(WrapperUtil.buildQueryWrapper(t));
    }

    @Override
    public boolean exists(T t) {
        return mapper.exists(WrapperUtil.buildQueryWrapper(t));
    }
}
