import javafx.scene.chart.XYChart;

class ImprovedEuler {
    private double x0, y0, h, X;
    private double [] x_i;
    private double [] y_i;
    private XYChart.Series <Number, Number> improvedSeries;
    private XYChart.Series <Number, Number> improvedErrors;
    private XYChart.Series <Number, Number> improvedApprox;
// Constructor
    ImprovedEuler (double x0, double y0, double X, int N) {
        this.x0 = x0;
        this.y0 = y0;
        this.X = X;
    // Compute step
        h = (X - x0) / N;
    // Computation of x_i
        x_i = new double[N + 1];
        for (int i = 1; i <= N; ++i)
            x_i[i] = x0 +  i * h;
    // Create array for y_i
        y_i = new double[N + 1];
        y_i[0] = y0;
    // Create Series
        improvedSeries = new XYChart.Series <Number, Number>();
        improvedSeries.getData().clear();
        improvedErrors = new XYChart.Series <Number, Number>();
        improvedErrors.getData().clear();
        improvedApprox = new XYChart.Series <Number, Number>();
        improvedApprox.getData().clear();
    // Give name to Series
        improvedSeries.setName("Improved Euler");
        improvedErrors.setName("Improved Euler");
        improvedApprox.setName("Improved Euler");
    }

    XYChart.Series <Number, Number> solveImprovedEuler() {
    //  Add first point
        improvedSeries.getData().add(new XYChart.Data<Number, Number>(x0,y0));
    // Compute y_i
        for (int i = 1; i < x_i.length; ++i) {
            double k1 = Main.F(x_i[i - 1], y_i[i - 1]);
            double k2 = Main.F(x_i[i], y_i[i - 1] + h * k1);
            y_i[i] = y_i[i - 1] + (h / 2) * (k1 + k2);
        // Add x_i and y_i to series
            improvedSeries.getData().add(new XYChart.Data <Number, Number> (x_i[i], y_i[i]));
        }
    // Return series for further comparing
        return improvedSeries;
    }

    XYChart.Series <Number, Number> get_Errors() {
    // Compute errors for ith point
        for (int i = 0; i < y_i.length; ++i) {
            improvedErrors.getData().add(new XYChart.Data<Number, Number>(x_i[i], Math.abs(Exact.get_y_i(i) - y_i[i])));
        }
    // Return series for further comparing
        return improvedErrors;
    }

    XYChart.Series <Number, Number> getApprox(int initial, int finalN) {
        for (int i = initial; i <= finalN; ++i) {
        // First solve exact
            Exact e = new Exact(x0, y0, X, i);
            e.solveExact();
        // Create an object to compute total approximation error
            ImprovedEuler temp = new ImprovedEuler(x0, y0, X, i);
            temp.solveImprovedEuler();
        // Find max error
            double max = -1e9;
            for (int j = 0; j < temp.y_i.length; ++j) {
                if (max < Math.abs(e.get_y_i(j) - temp.y_i[j]))
                    max = Math.abs(e.get_y_i(j) - temp.y_i[j]);
            }
        // Add error to the Series
            improvedApprox.getData().add(new XYChart.Data<Number, Number> (i, max));
        }
    // Return series for further comparing
        return improvedApprox;
    }
}
