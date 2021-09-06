package by.minsk.resume.configuration;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.NodeBuilder;
import org.elasticsearch.node.Node;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories("by.minsk.resume.repository.search")
public class ElasticSearchConfig {
    @Value("${elasticsearch.home}")
    private String elasticSearchHome;

    @Bean
    public Node node(){
        return  new NodeBuilder().local(true).settings(Settings.builder().put("path.home", elasticSearchHome)).node();
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchTemplate(node().client());
    }
}
