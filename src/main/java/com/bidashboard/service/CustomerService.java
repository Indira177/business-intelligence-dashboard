package com.bidashboard.service;

import com.bidashboard.exception.DuplicateResourceException;
import com.bidashboard.exception.ResourceNotFoundException;
import com.bidashboard.model.Customer;
import com.bidashboard.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Register a new customer
    public Customer createCustomer(Customer customer) {

        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new DuplicateResourceException(
                    "Customer email already exists"
            );
        }

        customer.setRegisteredAt(LocalDateTime.now());

        return customerRepository.save(customer);
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Get customer by ID
    public Customer getCustomerById(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with ID: " + id
                        ));
    }

    // Search customer by name
    public List<Customer> searchCustomersByName(String name) {
        return customerRepository.findByCustomerNameContainingIgnoreCase(name);
    }

    // Get customers by city
    public List<Customer> getCustomersByCity(String city) {
        return customerRepository.findByCityIgnoreCase(city);
    }

    // Get customers by region
    public List<Customer> getCustomersByRegion(String region) {
        return customerRepository.findByRegionIgnoreCase(region);
    }

    // Update customer
    public Customer updateCustomer(String id, Customer updatedCustomer) {

        Customer existingCustomer = getCustomerById(id);

        if (updatedCustomer.getEmail() != null &&
                !updatedCustomer.getEmail().equalsIgnoreCase(existingCustomer.getEmail()) &&
                customerRepository.existsByEmail(updatedCustomer.getEmail())) {

            throw new DuplicateResourceException(
                    "Customer email already exists"
            );
        }

        existingCustomer.setCustomerName(updatedCustomer.getCustomerName());
        existingCustomer.setEmail(updatedCustomer.getEmail());
        existingCustomer.setPhone(updatedCustomer.getPhone());
        existingCustomer.setAddress(updatedCustomer.getAddress());
        existingCustomer.setCity(updatedCustomer.getCity());
        existingCustomer.setRegion(updatedCustomer.getRegion());

        return customerRepository.save(existingCustomer);
    }

    // Delete customer
    public void deleteCustomer(String id) {

        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Customer not found with ID: " + id
            );
        }

        customerRepository.deleteById(id);
    }
}
