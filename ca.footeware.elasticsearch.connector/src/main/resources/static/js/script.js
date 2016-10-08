/**
 * Add a new customer.
 * 
 * @returns void
 */
function addCustomer() {
	var form = window.document.getElementsByTagName("form")[0];
	var firstName = form.firstName.value;
	var lastName = form.lastName.value;
	if (firstName.length == 0 || lastName == 0) {
		return;
	}
	var data = {};
	data["firstName"] = firstName;
	data["lastName"] = lastName;
	var body = JSON.stringify(data);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", "http://localhost:8080/customers/", true);
	xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	xhr.onload = function() {
		if (xhr.status != 200) {
			document.getElementById("errorDisplay").innerHTML = xhr.responseText;
		} else {
			window.location = "/customers";
		}
	}
	xhr.send(body);
}

/**
 * Delete a customer by ID.
 * 
 * @param id
 *            String
 * @returns void
 */
function deleteCustomer(id) {
	if (confirm("Are you sure you want to delete this customer?")) {
		var xhr = new XMLHttpRequest();
		var url = "http://localhost:8080/customers/" + id;
		xhr.open("DELETE", url, true);
		xhr.onload = function() {
			window.location.reload();
		}
		xhr.send();
	}
}

/**
 * Update a customer with provided id.
 * 
 * @param id
 *            String
 * @returns void
 */
function updateCustomer(id) {
	var form = window.document.getElementsByTagName("form")[0];
	var firstName = form.firstName.value;
	var lastName = form.lastName.value;
	if (firstName.length == 0 || lastName.length == 0) {
		return;
	}
	var data = {};
	data["firstName"] = firstName;
	data["lastName"] = lastName;
	var body = JSON.stringify(data);
	var xhr = new XMLHttpRequest();
	var url = "http://localhost:8080/customers/" + id;
	xhr.open("PUT", url, true);
	xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
	xhr.onload = function() {
		if (xhr.status != 200) {
			document.getElementById("errorDisplay").innerHTML = xhr.responseText;
		} else {
			window.location = "/customers";
		}
	}
	xhr.send(body);
}

