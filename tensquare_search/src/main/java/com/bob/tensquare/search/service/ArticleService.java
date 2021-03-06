package com.bob.tensquare.search.service;


import com.bob.tensquare.search.dao.ArticleDao;
import com.bob.tensquare.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.management.Query;

@Service
public class ArticleService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private ArticleDao articleDao;

    public void save(Article article){
        article.setId(idWorker.nextId()+"");
        articleDao.save(article);
    }

    /**
     * 分页+搜索
     * @param key
     * @param page
     * @param size
     * @return
     */
    public Page<Article> findByKey(String key, int page, int size) {
        Pageable pageable= PageRequest.of(page-1,size);
        return articleDao.findByTitleOrContentLike(key,key,pageable);
    }
}
