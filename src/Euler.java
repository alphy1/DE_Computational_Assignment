import javafx.scene.chart.XYChart;

class Euler {
    private double x0, y0, h, X;
    private double[] x_i;
    private double[] y_i;
    private XYChart.Series <Number, Number> eulerSeries;
    private XYChart.Series <Number, Number> eulerErrors;
    private XYChart.Series <Number, Number> eulerApprox;
// Constructor
    Euler(double x0, double y0, double X, int  N) {
        this.x0 = x0;
        this.y0 = y0;
        this.X = X;
    // Compute step
        h = (X - x0) / N;
    // Computation of x_i
        x_i = new double[N + 1];
        for (int i = 1; i <= N; ++i)
            x_i[i] = x0 + i * h;
    // Create array for y_i
        y_i = new double[N + 1];
        y_i[0] = y0;
    // Create Series
        eulerSeries = new XYChart.Series <Number, Number>();
        eulerSeries.getData().clear();
        eulerErrors = new XYChart.Series <Number, Number>();
        eulerErrors.getData().clear();
        eulerApprox = new XYChart.Series <Number, Number>();
        eulerApprox.getData().clear();
    // Give name to Series
        eulerSeries.setName("Euler");
        eulerErrors.setName("Euler");
        eulerApprox.setName("Euler");
    }

    XYChart.Series <Number, Number> solveEuler() {
    //  Add first point
        eulerSeries.getData().add(new XYChart.Data <Number, Number>(x0, y0));
    // Compute y_i
        for (int i = 1; i < x_i.length; ++i) {
            y_i[i] = y_i[i - 1] + h * Main.F(x_i[i - 1], y_i[i - 1]);
        // Add x_i and y_i to series
            eulerSeries.getData().add(new XYChart.Data <Number, Number>(x_i[i], y_i[i]));
        }
    // Return series for further comparing
        return eulerSeries;
    }

    XYChart.Series <Number, Number> getErrors() {
    // Compute error for ith point and add it to the series
        for (int i = 0; i < y_i.length; ++i) {
            eulerErrors.getData().add(new XYChart.Data <Number, Number>(x_i[i], Math.abs(Exact.get_y_i(i) - y_i[i])));
        }
    // Return series for further comparing
        return eulerErrors;
    }

    XYChart.Series <Number, Number> getApprox(int initial, int finalN) {
        for (int i = initial; i <= finalN; ++i) {
        // First solve exact
            Exact e = new Exact(x0, y0, X, i);
            e.solveExact();
        // Create an object to compute total approximation error
            Euler temp = new Euler(x0, y0, X, i);
            temp.solveEuler();
        // Find max error
            double max = 0;
            for (int j = 0; j < temp.y_i.length; ++j) {
                if (max <= Math.abs(e.get_y_i(j) - temp.y_i[j]))
                    max = Math.abs(e.get_y_i(j) - temp.y_i[j]);
            }
        // Add error to the Series
            eulerApprox.getData().add(new XYChart.Data<Number, Number> (i, max));
        }
    // Return series for further comparing
        return eulerApprox;
    }
}
