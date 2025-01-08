package controller.customer;

import javafx.collections.ObservableList;
import model.Customer;

public interface CustomerService {
    ObservableList<Customer> getAll();

    boolean addCustomer(Customer customer);

    boolean upadateCustomer(Customer customer);

    boolean deleteCustomer(String customerId);

    Customer searchCustomer(String customerId);

}
