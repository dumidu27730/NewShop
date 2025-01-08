package controller.customer;

import com.jfoenix.controls.JFXTextField;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;



import java.net.URL;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableView<Customer> tblCustomers;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtSalary;

   CustomerService service =new CustomerController();
    @FXML
    void btnAddOnAction(ActionEvent event) {
       Customer customer= new Customer(
                colId.getText(),
                txtName.getText(),
                txtAddress.getText(),
                Double.parseDouble(txtSalary.getText())
        );

        if(service.addCustomer(customer)){
            new Alert(Alert.AlertType.INFORMATION,"Customer Add Success").show();
            loadTable();
        }else{
            new Alert(Alert.AlertType.ERROR,"Customer Not Add").show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (service.deleteCustomer(txtId.getText())){
            new Alert(Alert.AlertType.INFORMATION).show();
            loadTable();
        }else {
            new Alert(Alert.AlertType.ERROR).show();

        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
       Customer customer = service.searchCustomer(txtId.getText());
        setTextToValues(customer);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        Customer customer = new Customer(
                txtId.getText(),
                txtName.getText(),
                txtAddress.getText(),
                Double.parseDouble(txtSalary.getText())
        );

        if (service.upadateCustomer(customer)){
            new Alert(Alert.AlertType.INFORMATION,"Customer Updated!!").show();
            loadTable();
        }else {
            new Alert(Alert.AlertType.ERROR,"Customer Not Updated!!").show();

        }
    }

    private void loadTable(){
        ObservableList<Customer> all = service.getAll();
        tblCustomers.setItems(all);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary .setCellValueFactory(new PropertyValueFactory<>("salary"));
        loadTable();

        tblCustomers.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if(newValue!=null){
                setTextToValues(newValue);
            }

        }));
    }
    private void setTextToValues(Customer newValue) {
        txtId.setText(newValue.getId());
        txtName.setText(newValue.getName());
        txtAddress.setText(newValue.getAddress());
        txtSalary.setText(newValue.getSalary().toString());
    }

}
