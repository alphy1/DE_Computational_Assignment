import javafx.scene.chart.XYChart;

class Exact {
    private static double x0, y0, h, X;
    private static int N;
    private static double [] x_i;
    private static double [] y_i;
    private static XYChart.Series <Number, Number> exactSeries;
// Constructor
    Exact (double x0, double y0, double X, int N) {
        Exact.x0 = x0;
        Exact.y0 = y0;
        Exact.X = X;
        Exact.N = N;
    // Compute step
        h = (X - x0) / N;
    // Computation of x_i
        x_i = new double[N + 1];
        for (int i = 0; i <= N; ++i)
            x_i[i] = x0 + i * h;
    // Create array for y_i
        y_i = new double[N + 1];
    // Create Series
        exactSeries = new XYChart.Series <Number, Number>();
        exactSeries.getData().clear();
    // Give name to Series
        exactSeries.setName("Exact");
    }

    XYChart.Series <Number, Number> solveExact() {
    // First, compute constant
        double c = y0 * Math.exp(x0) - Math.exp(2 * x0);
    // Compute y_i
        for (int i = 0; i < x_i.length; ++i) {
            y_i[i] = Math.exp(x_i[i]) + c * Math.exp(-x_i[i]);
        // Add x_i and y_i to series
            exactSeries.getData().add(new XYChart.Data <Number, Number>(x_i[i], y_i[i]));
        }
    // Return series for further comparing
        return exactSeries;
    }

    public static double get_y_i(int i) { return y_i[i];}
}
