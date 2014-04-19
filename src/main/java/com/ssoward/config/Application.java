package com.ssoward.config;

import com.ssoward.service.TestUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import net.sf.ehcache.Cache;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

@Configuration
@ComponentScan("com.ssoward")
@EnableAutoConfiguration
@EnableConfigurationProperties
@EnableCaching
public class Application extends DelegatingWebMvcConfiguration{

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Bean
//    public void test(@Value("${db.username}") String username){
//
//    }

    @Bean
    public CacheManager cacheManager() {

        EhCacheCacheManager cManager = new EhCacheCacheManager();

        net.sf.ehcache.CacheManager cacheManager = net.sf.ehcache.CacheManager.create();
        cacheManager.addCache(createCache("userDetailsCache"));
        cManager.setCacheManager(cacheManager);
        return cManager;
    }

    private Cache createCache(String cacheName) {
        return new Cache(
                new CacheConfiguration(cacheName,                                // cachename,
                        100)                                                     // the maximum number of elements in memory, before they are evicted (0 == no limit)
                        .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU)// LFU - least frequently used.
                        .timeToLiveSeconds(60 * 60)
                        .timeToIdleSeconds(60 * 10)
                        .persistence(new PersistenceConfiguration().strategy(PersistenceConfiguration.Strategy.LOCALTEMPSWAP)));
    }

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/spring");
        dataSource.setUsername("root");
        dataSource.setPassword("asdfg123");
        return dataSource;
    }

    @Bean
    public TestUtil testUtil(){
        return new TestUtil();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){

        return new JdbcTemplate(dataSource());
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("static/");
        viewResolver.setSuffix(".html");

        return viewResolver;
    }

    @Override
    protected void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/enter").setViewName("login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }


}
