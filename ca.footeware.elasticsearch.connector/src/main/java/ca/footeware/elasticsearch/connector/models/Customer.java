package ca.footeware.elasticsearch.connector.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "customers")
public class Customer {

	@Id
	private String id;

	private String firstName;

	private String lastName;

	public Customer() {
	}

	/**
	 * Constructor.
	 * 
	 * @param firstName
	 *            {@link String}
	 * @param lastName
	 *            {@link String}
	 */
	public Customer(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return String.format("Customer[id=%s, firstName='%s', lastName='%s']", this.id, this.firstName, this.lastName);
	}

}