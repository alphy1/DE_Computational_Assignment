import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;


public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private NumberAxis Chart;

    @FXML
    private ToggleButton Compute;

    @FXML
    private AnchorPane Field;

    @FXML
    private TextField Tf;

    @FXML
    private TextField Ti;

    @FXML
    private LineChart<Number, Number> errorsChart;

    @FXML
    private LineChart<Number, Number> approxChart;

    @FXML
    private CheckBox isEuler;

    @FXML
    private CheckBox isExact;

    @FXML
    private CheckBox isImprovedEuler;

    @FXML
    private CheckBox isRungeKutta;

    @FXML
    private TextField maxX;

    @FXML
    private LineChart<Number, Number> methodsChart;

    @FXML
    private TextField n;

    @FXML
    private TextField x0;

    @FXML
    private TextField y0;

    @FXML
    void initialize() {
        assert Chart != null : "fx:id=\"Chart\" was not injected: check your FXML file 'DE.fxml'.";
        assert Compute != null : "fx:id=\"Compute\" was not injected: check your FXML file 'DE.fxml'.";
        assert Field != null : "fx:id=\"Field\" was not injected: check your FXML file 'DE.fxml'.";
        assert Tf != null : "fx:id=\"Tf\" was not injected: check your FXML file 'DE.fxml'.";
        assert Ti != null : "fx:id=\"Ti\" was not injected: check your FXML file 'DE.fxml'.";
        assert approxChart != null : "fx:id=\"approxChart\" was not injected: check your FXML file 'DE.fxml'.";
        assert errorsChart != null : "fx:id=\"errorsChart\" was not injected: check your FXML file 'DE.fxml'.";
        assert isEuler != null : "fx:id=\"isEuler\" was not injected: check your FXML file 'DE.fxml'.";
        assert isExact != null : "fx:id=\"isExact\" was not injected: check your FXML file 'DE.fxml'.";
        assert isImprovedEuler != null : "fx:id=\"isImprovedEuler\" was not injected: check your FXML file 'DE.fxml'.";
        assert isRungeKutta != null : "fx:id=\"isRungeKutta\" was not injected: check your FXML file 'DE.fxml'.";
        assert maxX != null : "fx:id=\"maxX\" was not injected: check your FXML file 'DE.fxml'.";
        assert methodsChart != null : "fx:id=\"methodsChart\" was not injected: check your FXML file 'DE.fxml'.";
        assert n != null : "fx:id=\"n\" was not injected: check your FXML file 'DE.fxml'.";
        assert x0 != null : "fx:id=\"x0\" was not injected: check your FXML file 'DE.fxml'.";
        assert y0 != null : "fx:id=\"y0\" was not injected: check your FXML file 'DE.fxml'.";
    }

    @FXML
    void computation() {
    // Clear charts before start
        methodsChart.getData().clear();
        errorsChart.getData().clear();
        approxChart.getData().clear();
    // Take values from a text fields
        double x = Double.parseDouble(x0.getCharacters().toString());
        double y = Double.parseDouble(y0.getCharacters().toString());
        double X = Double.parseDouble(maxX.getCharacters().toString());
        int N = Integer.parseInt(n.getCharacters().toString());
        int initial = Integer.parseInt(Ti.getCharacters().toString());
        int finalN = Integer.parseInt(Tf.getCharacters().toString());
    // Plot corresponding graph
        if (isExact.isSelected()) {
        // Create object of class Exact
            Exact ex = new Exact(x, y, X, N);
        // Call function to compute and get Series with solution and add it to the method's chart
            methodsChart.getData().add(ex.solveExact());
        }
        if (isEuler.isSelected()) {
        // Create object of class Euler
            Euler eu = new Euler(x, y, X, N);
        // Call function to compute and get Series with solution and add it to the method's chart
            methodsChart.getData().add(eu.solveEuler());
        // To calculate errors exact have to been computed
            if (isExact.isSelected()) {
            // Call function to get Series with errors and add it to the error's chart
                errorsChart.getData().add(eu.getErrors());
            // Call function to get Series with total approximation errors and add it to the approximation chart
                approxChart.getData().add(eu.getApprox(initial, finalN));
            }
        }
        if (isImprovedEuler.isSelected()) {
        // Create object of class ImprovedEuler
            ImprovedEuler imp = new ImprovedEuler(x, y, X, N);
        // Call function to compute and get Series with solution and add it to the method's chart
            methodsChart.getData().add(imp.solveImprovedEuler());
        // To calculate errors exact have to been computed
            if (isExact.isSelected()) {
            // Call function to get Series with errors and add it to the error's chart
                errorsChart.getData().add(imp.get_Errors());
            // Call function to get Series with total approximation errors and add it to the approximation chart
                approxChart.getData().add(imp.getApprox(initial, finalN));
            }
        }
        if (isRungeKutta.isSelected()) {
        // Create object of class RungeKutta
            RungeKutta rk = new RungeKutta(x, y, X, N);
        // Call function to compute and get Series with solution and add it to the method's chart
            methodsChart.getData().add(rk.solveRungeKutta());
        // To calculate errors exact have to been computed
            if (isExact.isSelected()) {
            // Call function to get Series with errors and add it to the error's chart
                errorsChart.getData().add(rk.getRungeKuttaErrors());
            // Call function to get Series with total approximation errors and add it to the approximation chart
                approxChart.getData().add(rk.getApprox(initial, finalN));
            }
        }
    }
}