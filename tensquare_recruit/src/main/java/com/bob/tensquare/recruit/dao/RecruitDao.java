package com.bob.tensquare.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bob.tensquare.recruit.pojo.Recruit;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{
	public List<Recruit> findTop6ByStateOrderByCreatetime(String state);//where state=? order by createtime,一个参数

    public List<Recruit> findTop6ByStateNotOrderByCreatetimeDesc(String state);//
}
