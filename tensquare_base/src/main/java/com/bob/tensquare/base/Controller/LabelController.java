package com.bob.tensquare.base.Controller;

import com.bob.tensquare.base.pojo.Label;
import com.bob.tensquare.base.service.LabelService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/label")
public class LabelController {

    /**
     * 属性注入
     */
    @Autowired
    private LabelService labelService;

    /**
     * 查询所有，查询需要封装所查询到的数据
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result<List> findAll(){
        List<Label> lists= labelService.findAll();
        return new Result<>(true, StatusCode.OK,"查询成功",lists);
    }

    /**
     * 根据id查询
     * @param labelId
     * @return
     */
    @RequestMapping(value ="/{labelId}",method=RequestMethod.GET)
    public Result findById(@PathVariable String labelId){
        return new Result(true, StatusCode.OK,"查询成功",labelService.findById(labelId));
    }

    /**
     * 添加数据，返回添加后的状态等信息
     * @param label
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Label label){
        labelService.save(label);
        return new Result(true, StatusCode.OK,"添加成功");
    }

    /**
     * 修改数据
     * @param labelId
     * @param label
     * @return
     */
    @RequestMapping(value ="/{labelId}",method = RequestMethod.PUT)
    public Result update(@PathVariable String labelId,@RequestBody Label label){
        label.setId(labelId);
        labelService.update(label);
        return new Result(true, StatusCode.OK,"修改成功");
    }

    /**
     * 根据Id删除
     * @param labelId
     * @return
     */
    @RequestMapping(value ="/{labelId}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String labelId){
        labelService.deleteById(labelId);
        return new Result(true, StatusCode.OK,"删除成功");
    }
}
