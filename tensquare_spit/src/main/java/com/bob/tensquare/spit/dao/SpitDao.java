package com.bob.tensquare.spit.dao;

import com.bob.tensquare.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * SpitDao数据持久化层，使用MongoDB非关系型数据库
 */
public interface SpitDao extends MongoRepository<Spit,String> {

    /**
     * 必须要注重书写规范，否则报错
     *
     * @return
     */
    public Page<Spit> findByParentid(String parentId, Pageable pageable);
}
