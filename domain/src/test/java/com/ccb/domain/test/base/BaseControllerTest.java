package com.ccb.domain.test.base;


import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.test.context.ActiveProfiles;
import com.ccb.domain.StartApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;

/**
 * user: yangfei
 * Date: 13-7-11
 * Time: 上午10:16
 * 创建项目所需基类，封装了abstract减少代码冗余，提高开发效率
 */
@ActiveProfiles("dev")
@EnableApolloConfig(value = {"common", "domain-main-config-dev.yml"})
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartApplication.class)
public abstract class BaseControllerTest {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    protected String baseFront;
    protected String baseOpen;

    protected TestRestTemplate template;

    /**
     * 初始化
     *
     * @throws Exception
     */
    @Before
    public void init() throws MalformedURLException {

        this.baseFront = "http://localhost:8033/domain/front/api/v1/AppVersion/";
        this.baseOpen = "http://localhost:8033/domain/open/api/v1/AppVersion/";
        this.template = new TestRestTemplate();
    }


    @After
    public void end() {
        log.info("closed ********************************** 执行");
    }
}
