/**
 * 
 */
package ca.footeware.elasticsearch.connector;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @author footeware.ca
 *
 */
@EnableElasticsearchRepositories
public class ElasticsearchConfig {

	@Bean
	public ElasticsearchOperations elasticsearchTemplate(){
		return new ElasticsearchTemplate(getClient());
	}

	private Client getClient() {
		return TransportClient.builder().settings(getSettings()).build();
	}

	private Settings getSettings() {
		// authentication settings go here
		return Settings.builder().build();
	}
}
