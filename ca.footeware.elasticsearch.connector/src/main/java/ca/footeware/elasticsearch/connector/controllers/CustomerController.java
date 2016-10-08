/**
 * 
 */
package ca.footeware.elasticsearch.connector.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ca.footeware.elasticsearch.connector.models.Customer;
import ca.footeware.elasticsearch.connector.repositories.CustomerRepository;

/**
 * @author footeware.ca
 *
 */
@Controller
public class CustomerController {

	@Autowired
	private CustomerRepository repository;

	/**
	 * Check if a customer already exists by first and last name of the provided
	 * customer.
	 * 
	 * @param customer
	 *            {@link Customer}
	 */
	private void checkCustomerNameExists(Customer customer) {
		List<Customer> existingCustomersByLastName = repository.findByLastName(customer.getLastName());
		for (Customer existingCustomerByLastName : existingCustomersByLastName) {
			if (existingCustomerByLastName.getFirstName().equals(customer.getFirstName())) {
				throw new RuntimeException("Customer by that name already exists.");
			}
		}
	}

	/**
	 * Add a customer.
	 * 
	 * @param model
	 *            {@link Model}
	 * @param customer
	 *            {@link Customer}
	 * @return {@link String}
	 */
	@RequestMapping(value = "/customers", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	String addCustomer(Model model, @RequestBody Customer customer) {
		checkCustomerNameExists(customer);
		repository.save(customer);
		model.addAttribute("customers", repository.findAll());
		return "customers";
	}

	/**
	 * Get all customers.
	 * 
	 * @param model
	 *            {@link Model}
	 * @return {@link String}
	 */
	@RequestMapping(value = "/customers", method = RequestMethod.GET)
	String getCustomers(Model model) {
		model.addAttribute("customers", repository.findAll());
		return "customers";
	}

	/**
	 * Delete a customer.
	 * 
	 * @param model
	 *            {@link Model}
	 * @param id
	 *            {@link String}
	 * @return {@link String}
	 */
	@RequestMapping(value = "/customers/{id}", method = RequestMethod.DELETE)
	String deleteCustomer(Model model, @PathVariable String id) {
		repository.delete(id);
		model.addAttribute("customers", repository.findAll());
		return "customers";
	}

	/**
	 * Get a customer.
	 * 
	 * @param model
	 *            {@link Model}
	 * @param id
	 *            {@link String}
	 * @return {@link String}
	 */
	@RequestMapping(value = "/customers/{id}", method = RequestMethod.GET)
	String getCustomer(Model model, @PathVariable String id) {
		if ("-1".equals(id)) {
			model.addAttribute("mode", "Add Customer");
		} else {
			model.addAttribute("mode", "Edit Customer");
			Customer customer = repository.findOne(id);
			model.addAttribute("customer", customer);
		}
		return "customer";
	}

	/**
	 * Update a customer.
	 * 
	 * @param model
	 *            {@link Model}
	 * @param id
	 *            {@link String}
	 * @param customer
	 *            {@link Customer}
	 * @return {@link String}
	 */
	@RequestMapping(value = "/customers/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	String updateCustomer(Model model, @PathVariable String id, @RequestBody Customer customer) {
		checkCustomerNameExists(customer);
		Customer existingCustomer = repository.findOne(id);
		existingCustomer.setFirstName(customer.getFirstName());
		existingCustomer.setLastName(customer.getLastName());
		repository.save(existingCustomer);
		return "customers";
	}

	@RequestMapping(value = "/customers/search", method = RequestMethod.GET)
	String doSearch(Model model, @RequestParam(required = false, name = "term") String term) {
		if (term == null) {
			return "search";
		}
		List<Customer> customers = new ArrayList<>();
		customers.addAll(repository.findAllByFirstName("*" + term + "*"));
		customers.addAll(repository.findAllByLastName("*" + term + "*"));
		model.addAttribute("customers", customers);
		return "customers";
	}

}
