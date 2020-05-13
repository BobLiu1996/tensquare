package com.bob.tensquare.base.service;

import com.bob.tensquare.base.dao.LabelDao;
import com.bob.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

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

}
