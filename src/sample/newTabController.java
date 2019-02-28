package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class newTabController {

    @FXML
    private ChoiceBox choiceBox;

    @FXML
    private AnchorPane anchor;
    Label lbl2 = new Label();
    @FXML
    Label fileLabel;
    File file;

    boolean number = false;
    boolean fileb = false;
    Button button = new Button();

    @FXML
    public void initialize() {
        choiceBox.setItems(FXCollections.observableArrayList("First come, first served", "Round robin", "Shortest process next", "Shortest time remaining", "Highest response rate next", "Multi level feedback"));
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (choiceBox.getItems().get((Integer) newValue).equals("First come, first served")) {

                } else if (choiceBox.getItems().get((Integer) newValue).equals("Round robin")) {
                    button.setLayoutX(7);
                    button.setLayoutY(104 + 2 * 26);
                    button.setDisable(true);
                    button.setText("Run algorithm");
                    TextField txtfield = new TextField();
                    txtfield.setLayoutX(7);
                    txtfield.setLayoutY(104);
                    txtfield.textProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observable, String oldValue,
                                            String newValue) {
                            if (!newValue.matches("\\d*")) {
                                txtfield.setText(newValue.replaceAll("[^\\d]", ""));
                            }
                            if (!newValue.trim().isEmpty()) {
                                number = true;
                                button.setDisable(!number || !fileb);

                            } else {
                                number = false;
                                button.setDisable(!number || !fileb);
                            }
                            System.out.println(newValue);
                        }
                    });
                    Label lbl = new Label();
                    lbl.setLayoutX(7);
                    lbl.setLayoutY(78);
                    lbl.setText("Choose max time");

                    button.setOnAction(e -> {
                        RRScheduler rrScheduler = new RRScheduler(fileToArraylist(file),Integer.parseInt(txtfield.getText()));
                        rrScheduler.startScheduling();
                    });

                    anchor.getChildren().add(txtfield);
                    anchor.getChildren().add(lbl);
                    anchor.getChildren().add(button);
                } else if (choiceBox.getItems().get((Integer) newValue).equals("Shortest process next")) {

                } else if (choiceBox.getItems().get((Integer) newValue).equals("Shortest time remaining")) {

                } else if (choiceBox.getItems().get((Integer) newValue).equals("Highest response rate next")) {

                } else if (choiceBox.getItems().get((Integer) newValue).equals("Multi level feedback")) {

                }
            }

        });
    }

    @FXML
    public void showFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML Files", "*.xml")
        );
        fileChooser.setInitialDirectory(new File(new File("processen5.xml").toURI()).getParentFile());
        System.out.println(new File(new File("processen5.xml").toURI()).getParentFile());

        file = fileChooser.showOpenDialog(null);
        fileLabel.setText(file.getName());
        fileb = file != null;
        button.setDisable(!number || !fileb);

    }
    public ArrayList<Process> fileToArraylist(File f){
        File file = new File("processen5.xml");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        org.w3c.dom.Document document = null;
        try {
            document = documentBuilder.parse(file);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        NodeList list = document.getElementsByTagName("process");
        System.out.println(list.getLength());
        List<Process> processes = new ArrayList<>();

        for (int i = 0; i < list.getLength(); i++) {

            Node node = list.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element element = (Element) node;
                Process process = new Process(
                        Integer.parseInt(element.getElementsByTagName("pid").item(0).getTextContent()),
                        Integer.parseInt(element.getElementsByTagName("arrivaltime").item(0).getTextContent()),
                        Integer.parseInt(element.getElementsByTagName("servicetime").item(0).getTextContent())
                );
                processes.add(process);
            }
        }
        Comparator<Process> cmp = Comparator.<Process>comparingInt(p -> p.getArrivaltime())
                .thenComparingInt(p -> p.getArrivaltime());
        processes.sort(cmp);
        return (ArrayList<Process>) processes;
    }
}
