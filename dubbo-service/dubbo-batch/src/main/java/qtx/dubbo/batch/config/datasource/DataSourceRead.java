package qtx.dubbo.batch.config.datasource;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * @author qtx
 * @since 2023/10/7 20:53
 */
@Configuration
@MapperScan(basePackages = "qtx.dubbo.batch.mapper.read", sqlSessionFactoryRef = "readSqlSessionFactory")
public class DataSourceRead {

    @Bean(name = "readDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.read")
    public HikariDataSource readDataSource() {
        HikariDataSource build = DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
        build.setPoolName("read");
        return build;
    }

    @Bean(name = "readSqlSessionFactory")
    public SqlSessionFactory readSqlSessionFactory(@Qualifier("readDataSource") HikariDataSource dataSource) throws Exception {
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfiguration(configuration);
        // 这个是指定加载的xml 文件路径。
        sqlSessionFactoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mappers/read/*Mapper.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "readSqlSessionFactoryTemplate")
    public SqlSessionTemplate readSqlSessionFactoryTemplate(@Qualifier("readSqlSessionFactory") SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }

    @Bean("readTransactionManager")
    public DataSourceTransactionManager readTransactionManager(@Qualifier("readDataSource") HikariDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
