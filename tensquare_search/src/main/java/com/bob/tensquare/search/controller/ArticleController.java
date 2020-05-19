package com.bob.tensquare.search.controller;


import com.bob.tensquare.search.pojo.Article;
import com.bob.tensquare.search.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 向索引库中添加文档
     * @param article
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Article article){
        articleService.save(article);
        return new Result(true, StatusCode.OK,"添加成功");
    }

    /**
     * 从索引库中查询+分页
     * @param key
     * @return
     */
    @RequestMapping(value="/{key}/{page}/{size}", method = RequestMethod.POST)
    public Result findByKey(@PathVariable String key,@PathVariable int page,@PathVariable int size){

        Page<Article> pageData=articleService.findByKey(key,page,size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Article>(pageData.getTotalElements(),pageData.getContent()));
    }
}
