package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;

public class Controller {


    @FXML
    TabPane tabpanel;
    @FXML
    Tab tab;

    @FXML
    public void initialize() {
        System.out.println(tabpanel);
        System.out.println(tab);
    }

    @FXML
    public void abc() throws IOException {
        if (tabpanel.getTabs().get(tabpanel.getTabs().size() - 1).isSelected()) {
            System.out.println("last tab selected");
            int numTabs = tabpanel.getTabs().size();
            tabpanel.getTabs().add( numTabs -1,FXMLLoader.load(this.getClass().getResource("tab.fxml")));
            tabpanel.getSelectionModel().select(tabpanel.getTabs().get(numTabs-1));
        }
    }
}
