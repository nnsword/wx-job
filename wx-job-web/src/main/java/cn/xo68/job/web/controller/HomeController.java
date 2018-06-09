/**
 * Copyright (c) 2018, 中国电信甘肃万维公司 All rights reserved.
 * 中国电信甘肃万维公司 专有/保密源代码,未经许可禁止任何人通过任何渠道使用、修改源代码.
 * 项目名称 : wxjobweb
 *
 * @version V1.0
 */
package cn.xo68.job.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  TODO (这里用一句话描述这个类的作用)
 * @author admin
 * @date 2018/6/9 12:10
 *
 */
@RestController
public class HomeController {

    private static final Logger logger= LoggerFactory.getLogger(HomeController.class);

    @GetMapping({"/",""})
    public String index(){
        logger.info("访问了首页....");
        return "success";
    }
}
