package qtx.dubbo.batch.config.datasource;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.ExecutorType;
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
@MapperScan(basePackages = "qtx.dubbo.batch.mapper.write", sqlSessionFactoryRef = "writeSqlSessionFactory")
public class DataSourceWrite {

    @Bean(name = "writeDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.write")
    public HikariDataSource writeDataSource(){
        HikariDataSource build = DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
        build.setPoolName("write");
        return build;
    }

    @Bean(name = "writeSqlSessionFactory")
    public SqlSessionFactory writeSqlSessionFactory(@Qualifier("writeDataSource") HikariDataSource dataSource) throws Exception {
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
        configuration.setDefaultExecutorType(ExecutorType.BATCH);
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfiguration(configuration);
        // 这个是指定加载的xml 文件路径。
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mappers/write/*Mapper.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "writeSqlSessionFactoryTemplate")
    public SqlSessionTemplate writeSqlSessionFactoryTemplate(@Qualifier("writeSqlSessionFactory") SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }

    @Bean("writeTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("writeDataSource") HikariDataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
}