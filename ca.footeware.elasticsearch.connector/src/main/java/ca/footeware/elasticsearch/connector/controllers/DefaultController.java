/**
 * 
 */
package ca.footeware.elasticsearch.connector.controllers;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author footeware.ca
 *
 */
@Controller
public class DefaultController {

	@Autowired
	private ElasticsearchTemplate template;

	@RequestMapping(value = "/")
	String goHome(Model model) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchAllQuery())
				.withIndices("*").build();

		Map<String, Integer> indices = template.query(searchQuery, new ResultsExtractor<Map<String, Integer>>() {
			@Override
			public Map<String, Integer> extract(SearchResponse response) {
				Map<String, Integer> indices = new HashMap<>();
				SearchHits hits = response.getHits();
				for (SearchHit hit : hits) {
					String indexName = hit.getIndex();
					if (indices.keySet().contains(indexName)) {
						indices.put(indexName, (indices.get(indexName) + 1));
					} else {
						indices.put(indexName, 1);
					}
				}
				return indices;
			}
		});
		model.addAttribute("indices", indices);

		// long num = template.count(searchQuery);
		// model.addAttribute("numResults", num);
		return "index";
	}
}
