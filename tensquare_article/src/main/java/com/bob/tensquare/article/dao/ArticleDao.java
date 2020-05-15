package com.bob.tensquare.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bob.tensquare.article.pojo.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{


    /**
     * 文章审核
     * @param id
     */
    @Modifying//增删改必须加这个注解，防止线程安全问题
    @Query(value="UPDATE tb_article set state=1 where id=?",nativeQuery = true)
    public void updateState(String id);

    /**
     * 文章点赞功能实现
     * @param id
     */
    @Modifying
    @Query(value="UPDATE tb_article set thumbup=thumbup+1 where id=?",nativeQuery = true)
    public void addThumbup(String id);

}
