import javafx.scene.chart.XYChart;

class RungeKutta {
    private double x0, y0, h, X;
    private double [] x_i;
    private double [] y_i;
    private XYChart.Series <Number, Number> RungeKuttaSeries;
    private XYChart.Series <Number, Number> RungeKuttaErrors;
    private XYChart.Series <Number, Number> RungeKuttaApprox;
// Constructor
    RungeKutta (double x0, double y0, double X, int N) {
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
        RungeKuttaSeries = new XYChart.Series <Number, Number>();
        RungeKuttaSeries.getData().clear();
        RungeKuttaErrors = new XYChart.Series <Number, Number>();
        RungeKuttaErrors.getData().clear();
        RungeKuttaApprox = new XYChart.Series <Number, Number>();
        RungeKuttaApprox.getData().clear();
    // Give name to Series
        RungeKuttaSeries.setName("Runge-Kutta");
        RungeKuttaErrors.setName("Runge-Kutta");
        RungeKuttaApprox.setName("Runge-Kutta");
    }

    XYChart.Series <Number, Number> solveRungeKutta() {
    //  Add first point
        RungeKuttaSeries.getData().add(new XYChart.Data <Number, Number>(x0,y0));
    // Compute y_i
        for (int i = 1; i < x_i.length; ++i) {
            double k1 = Main.F(x_i[i - 1], y_i[i - 1]);
            double k2 = Main.F(x_i[i - 1] + h / 2, y_i[i - 1] + k1 * (h / 2));
            double k3 = Main.F(x_i[i - 1] + h / 2, y_i[i - 1] + k2 * (h / 2));
            double k4 = Main.F(x_i[i - 1] + h, y_i[i - 1] + k3 * h);
            y_i[i] = y_i[i - 1] + (h / 6) * (k1 + 2 * k2 + 2 * k3 + k4);
        // Add x_i and y_i to series
            RungeKuttaSeries.getData().add(new XYChart.Data<Number, Number>(x_i[i], y_i[i]));
        }
    // Return series for further comparing
        return RungeKuttaSeries;

    }

    XYChart.Series <Number, Number> getRungeKuttaErrors() {
    // Compute errors for ith point
        for (int i = 0; i < y_i.length; ++i) {
            RungeKuttaErrors.getData().add(new XYChart.Data<Number, Number>(x_i[i], Math.abs(Exact.get_y_i(i) - y_i[i])));
        }
    // Return series for further comparing
        return RungeKuttaErrors;
    }

    XYChart.Series <Number, Number> getApprox(int initial, int finalN) {
        for (int i = initial; i <= finalN; ++i) {
        // First solve exact
            Exact e = new Exact(x0, y0, X, i);
            e.solveExact();
        // Create an object to compute total approximation error
            RungeKutta temp = new RungeKutta(x0, y0, X, i);
            temp.solveRungeKutta();
        // Find max error
            double max = -1e9;
            for (int j = 0; j < temp.y_i.length; ++j) {
                if (max < Math.abs(e.get_y_i(j) - temp.y_i[j]))
                    max = Math.abs(e.get_y_i(j) - temp.y_i[j]);
            }
        // Add error to the Series
            RungeKuttaApprox.getData().add(new XYChart.Data<Number, Number> (i, max));
        }
    // Return series for further comparing
        return RungeKuttaApprox;
    }
}
