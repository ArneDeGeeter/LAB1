package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;
import java.util.stream.Collectors;

import static sample.newTabController.fileToArraylist;

public class Main extends Application  {
    public static int firstProcess = 0;
    public static List<double[]> percentilePoints;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.setMinHeight(425);
        primaryStage.setMinWidth(400);
        primaryStage.show();
    }


    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, ScriptException {
        doWork();
        launch(args);
        File file = new File("processen5.xml");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        org.w3c.dom.Document document = documentBuilder.parse(file);
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
        firstProcess = processes.get(0).getArrivaltime();
        //   spn(processes);
        /*FCFSScheduler fcfs=new FCFSScheduler(copyArrayList(processes));
        fcfs.startScheduling();*/
       /* SPNScheduler spnScheduler=new SPNScheduler(copyArrayList(processes));
        spnScheduler.startScheduling();*/
        /*RRScheduler rrScheduler=new RRScheduler(copyArrayList(processes),4);
        rrScheduler.startScheduling();
        STRScheduler strScheduler=new STRScheduler(copyArrayList(processes));
        strScheduler.startScheduling();*/
        /*HRRNScheduler hrrnScheduler= new HRRNScheduler(copyArrayList(processes));
        hrrnScheduler.startScheduling();*/
        MLFBScheduler mlfbScheduler = new MLFBScheduler(copyArrayList(processes), "2^x", 5);
        mlfbScheduler.startScheduling();

      /*  ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");*/

    }

    public static ArrayList<Process> copyArrayList(List<Process> processes) {
        ArrayList<Process> copy = new ArrayList<>();
        for (Process p : processes) {
            copy.add(new Process(p));
        }
        return copy;
    }

    //Stackoverflow https://stackoverflow.com/a/26227947/10238891
    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

    private static void doWork(){
        File file = new File("processen10000.xml");
        /*
        RRScheduler rrScheduler = new RRScheduler(fileToArraylist(file), 4);
        rrScheduler.startScheduling();
        GraphData.saveData(rrScheduler, "RR-");

        FCFSScheduler fcfsScheduler = new FCFSScheduler(fileToArraylist(file));
        fcfsScheduler.startScheduling();
        GraphData.saveData(fcfsScheduler, "FCFS-");

        HRRNScheduler hrrnScheduler = new HRRNScheduler(fileToArraylist(file));
        hrrnScheduler.startScheduling();
        GraphData.saveData(hrrnScheduler, "HRRN-");

        MLFBScheduler mlfbScheduler = new MLFBScheduler(fileToArraylist(file), "2^x", 5);
        mlfbScheduler.startScheduling();
        GraphData.saveData(mlfbScheduler, "MLFB-");

        SPNScheduler spnScheduler = new SPNScheduler(fileToArraylist(file));
        spnScheduler.startScheduling();
        GraphData.saveData(mlfbScheduler, "SPN-");

        STRScheduler strScheduler = new STRScheduler(fileToArraylist(file));
        strScheduler.startScheduling();
        GraphData.saveData(strScheduler, "STR-");
        */


        ArrayList<LinkedList<double[]>> data = new ArrayList<>();

        data.add(DataTransferUtils.load("graphs/FCFS-percentile-time-graph.csv"));
        data.add(DataTransferUtils.load("graphs/HRRN-percentile-time-graph.csv"));
        data.add(DataTransferUtils.load("graphs/MLFB-percentile-time-graph.csv"));
        data.add(DataTransferUtils.load("graphs/RR-percentile-time-graph.csv"));
        data.add(DataTransferUtils.load("graphs/SPN-percentile-time-graph.csv"));
        data.add(DataTransferUtils.load("graphs/STR-percentile-time-graph.csv"));

        Chart chart = new Chart("title", data);
        chart.setAlwaysOnTop(true);
        chart.pack();
        chart.setSize(600, 400);
        chart.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        chart.setVisible(true);

    }
}

