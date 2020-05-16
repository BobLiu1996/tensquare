package com.bob.tensquare.spit.service;


import com.bob.tensquare.spit.dao.SpitDao;
import com.bob.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sun.misc.Request;
import util.IdWorker;

import java.util.Date;
import java.util.List;

@Service
@Transactional//关于增删改，必须要使用这个注解，事务管理
public class SpitService {

    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 查询所有
     * @return
     */
    public List<Spit> findAll(){
        return spitDao.findAll();
    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    public Spit findById(String id){
        return spitDao.findById(id).get();
    }

    /**
     * 保存
     * @param spit
     */
    public void save(Spit spit){
        spit.set_id(idWorker.nextId()+"");
        spit.setPublishtime(new Date());//发布日期
        spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数
        spit.setState("1");//状态

        //如果当前添加的有父节点，那么父节点的thumbup需+1
        if(spit.getParentid()!=null && !"".equals(spit.getParentid())){
            //封装查询条件，更新哪条数据
            Query query=new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            //对某个字段进行更新
            Update update=new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,"spit");
        }
        spitDao.save(spit);
    }

    /**
     * 更新
     * @param spit
     */
    public void update(Spit spit){
        spitDao.save(spit);
    }

    /**
     * 根据id删除id
     * @param id
     */
    public void deleteById(String id){
        spitDao.deleteById(id);
    }


    public Page<Spit> findByParentId(String parentid,int page,int size){
        PageRequest pageData=PageRequest.of(page-1,size);
        return spitDao.findByParentid(parentid,pageData);
    }

    public void thumbup(String spitId) {
        //方式一，效率低
//        Spit spit=spitDao.findById(spitId).get();
//        spit.setThumbup((spit.getThumbup()==null?0:spit.getThumbup())+1);
//        spitDao.save(spit);
        //方式2：使用原生Mongo原生命令来实现自增db.spit.update("_id","1",{$inc:{thumbup:NumberInt(1)}})
        Query query=new Query();//查询条件
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update=new Update();
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query,update,"spit");
    }
}
