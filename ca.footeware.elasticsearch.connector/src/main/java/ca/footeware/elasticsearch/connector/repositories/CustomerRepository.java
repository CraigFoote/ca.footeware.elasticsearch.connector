package ca.footeware.elasticsearch.connector.repositories;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import ca.footeware.elasticsearch.connector.models.Customer;

public interface CustomerRepository extends ElasticsearchRepository<Customer, String> {

	public Customer findByFirstName(String firstName);

	public List<Customer> findByLastName(String lastName);
	
	public List<Customer> findAllByFirstName(String firstName);

	public List<Customer> findAllByLastName(String lastName);
}