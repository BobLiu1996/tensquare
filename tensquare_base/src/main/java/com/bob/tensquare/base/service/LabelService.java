package com.bob.tensquare.base.service;

import com.bob.tensquare.base.dao.LabelDao;
import com.bob.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LabelService  {

    @Autowired
    private LabelDao labelDao;
    @Autowired
    private IdWorker idWorker;

    /**
     * 查询所有
     * @return
     */
    public List<Label> findAll(){
        return labelDao.findAll();
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Label findById(String id){
        return labelDao.findById(id).get();
    }

    /**
     * 保存
     * @param label
     */
    public void save(Label label){
        label.setId(idWorker.nextId()+"");//通过+""强制转化为字符串是，小技巧
        labelDao.save(label);
    }

    /**
     * 修改
     * @param label
     */
    public void update(Label label){
        labelDao.save(label);
    }

    /**
     * 根据id删除
     * @param id
     */
    public void deleteById(String id){
        labelDao.deleteById(id);
    }

    /**
     * 根据条件查询
     * @param label
     * @return
     */
    public List<Label> findSearch(Label label) {
        //匿名内部类实现Specification接口
        return labelDao.findAll(new Specification<Label>() {
            /**
             * @param root 根对象，也就是把条件封装到哪个对象
             * @param query 封装的是查询的关键字：group by  order by
             * @param cb 用来封装条件对象where：列名=列值
             * @return
             */
            @Nullable
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //创建集合存放所有的查询条件
                List<Predicate> list=new ArrayList<>();
                //labelname
                if(label.getLabelname()!=null && !"".equals(label.getLabelname())){
                    Predicate predicate= cb.like(root.get("labelname").as(String.class),"%"+label.getLabelname()+"%");//where labelname like %小明%
                    list.add(predicate);
                }
                //state
                if(label.getState()!=null && !"".equals(label.getState())){
                    Predicate predicate= cb.like(root.get("state").as(String.class),"%"+label.getState()+"%");//where state like %..%
                    list.add(predicate);
                }
                //new一个数组作为最终返回条件值
                Predicate[] predicates= new Predicate[list.size()];
                //将list转化为数组
                predicates=list.toArray(predicates);
                return cb.and(predicates);//拼接where条件:where labelname like %小明% and state="1"
            }
        });
    }

    /**
     * 分页查询
     * @param label
     * @param page
     * @param size
     * @return
     */
    public Page<Label> pageQuery(Label label, int page, int size) {
        Pageable pageable= PageRequest.of(page-1,size);
        return labelDao.findAll(new Specification<Label>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //创建集合存放所有的查询条件
                List<Predicate> list=new ArrayList<>();
                //labelname
                if(label.getLabelname()!=null && !"".equals(label.getLabelname())){
                    Predicate predicate= cb.like(root.get("labelname").as(String.class),"%"+label.getLabelname()+"%");//where labelname like %小明%
                    list.add(predicate);
                }
                //state
                if(label.getState()!=null && !"".equals(label.getState())){
                    Predicate predicate= cb.like(root.get("state").as(String.class),"%"+label.getState()+"%");//where state like %..%
                    list.add(predicate);
                }
                //new一个数组作为最终返回条件值
                Predicate[] predicates= new Predicate[list.size()];
                //将list转化为数组
                predicates=list.toArray(predicates);
                return cb.and(predicates);//拼接where条件:where labelname like %小明% and state="1"
            }
        },pageable);
    }
}
