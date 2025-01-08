package controller.item;

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
import model.Item;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableView<Item> tblItem;

    @FXML
    private JFXTextField txtCode;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXTextField txtQty;

    ItemService service = new ItemController();

    @FXML
    void btnAddOnAction(ActionEvent event) {

        Item item = new Item(
                txtCode.getText(),
                txtDescription.getText(),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtQty.getText())
        );
    if(service.addItem(item)){
        new Alert(Alert.AlertType.INFORMATION,"Item Add Success").show();
        loadTable();
    }else{
            new Alert(Alert.AlertType.ERROR,"Item Not Add").show();
        }

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (service.deleteItem(txtCode.getText())){
            new Alert(Alert.AlertType.INFORMATION).show();
            loadTable();
        }else {
            new Alert(Alert.AlertType.ERROR).show();

        }
    }
    private void loadTable(){
        ObservableList<Item> all = service.getAll();
        tblItem.setItems(all);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        loadTable();

        tblItem.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if(newValue!=null){
                setTextToValues(newValue);
            }

        }));
    }

    private void setTextToValues(Item newValue) {
        txtCode.setText(newValue.getCode());
        txtDescription.setText(newValue.getDescription());
        txtPrice.setText(newValue.getPrice().toString());
        txtQty.setText(newValue.getQty().toString());
    }


    @FXML
    void btnSearchOnAction(ActionEvent event) {
        Item item = service.searchItem(txtCode.getText());
        setTextToValues(item);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        Item item = new Item(
                txtCode.getText(),
                txtDescription.getText(),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtQty.getText())
        );

        if (service.updateItem(item)){
            new Alert(Alert.AlertType.INFORMATION,"Item Updated!!").show();
            loadTable();
        }else {
            new Alert(Alert.AlertType.ERROR,"Item Not Updated!!").show();

        }
    }


}
