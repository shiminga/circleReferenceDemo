package com.springtest.demo.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.provider.AbstractDataSourceProvider;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import org.apache.shardingsphere.infra.config.RuleConfiguration;
import org.apache.shardingsphere.shardingjdbc.spring.boot.encrypt.SpringBootEncryptRuleConfigurationProperties;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.*;

@Configuration
@AutoConfigureBefore({DynamicDataSourceAutoConfiguration.class})
public class DataSourceConfig {

    @Bean
    public ServletRegistrationBean druidServlet() {// 主要实现web监控的配置处理
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
                new StatViewServlet(), "/druid/*");//表示进行druid监控的配置处理操作
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1,129.168.1.11");//白名单
        servletRegistrationBean.addInitParameter("deny", "129.168.1.12");//黑名单
        servletRegistrationBean.addInitParameter("loginUsername", "root");//用户名
        servletRegistrationBean.addInitParameter("loginPassword", "root");//密码
        servletRegistrationBean.addInitParameter("resetEnable", "false");//是否可以重置数据源
        return servletRegistrationBean;

    }
    @Bean    //监控
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");//所有请求进行监控处理
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.css,/druid/*");//排除
        return filterRegistrationBean;
    }

//    @Resource
//    private DynamicDataSourceProperties properties;
//
//    @Lazy
//    @Resource(name = "shardingDataSource")
//    private DataSource shardingDataSource;
//
//    @Bean
//    public DynamicDataSourceProvider dynamicDataSourceProvider() {
//        Gove
//        Map<String, DataSourceProperty> datasourceMap = properties.getDatasource();
//        return new AbstractDataSourceProvider() {
//            @Override
//            public Map<String, DataSource> loadDataSources() {
//                Map<String, DataSource> dataSourceMap = createDataSourceMap(datasourceMap);
//                dataSourceMap.put("sharding", shardingDataSource);
//                return dataSourceMap;
//            }
//        };
//    }
//
//    @Primary
//    @Bean
//    public DataSource dataSource(DynamicDataSourceProvider dynamicDataSourceProvider) {
//        DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource();
//        dataSource.setPrimary(properties.getPrimary());
//        dataSource.setStrict(properties.getStrict());
//        dataSource.setStrategy(properties.getStrategy());
//        dataSource.setProvider(dynamicDataSourceProvider);
//        dataSource.setP6spy(properties.getP6spy());
//        dataSource.setSeata(properties.getSeata());
//        return dataSource;
//    }
}
