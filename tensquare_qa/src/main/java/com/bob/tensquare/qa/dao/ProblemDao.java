package com.bob.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bob.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    /**
     * 最新回答  nativeQuery表示使用Sql语句（使用到多表查询最好使用原生的SQl语句）
     * Pageable pageable 用于分页
     * @return
     */
    @Query(value = "SELECT * FROM tb_problem,tb_pl WHERE id=problemid and labelid=? ORDER BY replytime DESC",nativeQuery = true)
    public Page<Problem> newList(String labelId, Pageable pageable);

    /**
     * 获取人们回答
     * @return
     */
    @Query(value = "SELECT * FROM tb_problem,tb_pl WHERE id=problemid and labelid=? ORDER BY reply DESC",nativeQuery = true)
    public Page<Problem> hotList(String labelId, Pageable pageable);

    /**
     * 等待回答
     * @return
     */
    @Query(value = "SELECT * FROM tb_problem,tb_pl WHERE id=problemid and labelid=? and reply=0 ORDER BY createtime DESC",nativeQuery = true)
    public Page<Problem> waitList(String labelId, Pageable pageable);
	
}
