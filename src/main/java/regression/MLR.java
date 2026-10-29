package regression;

/** Regresión lineal múltiple y = beta0 + beta1*x1 + ... + betap*xp. */
public final class MLR extends LeastSquaresRegression {
    private final int predictors;

    public MLR(double[][] x, double[] y) {
        super(y, multipleDesign(x, y));
        predictors = x[0].length;
    }

    private static double[][] multipleDesign(double[][] x, double[] y) {
        if (x == null || y == null || x.length != y.length || x.length == 0) {
            throw new IllegalArgumentException("X e y deben tener el mismo número de observaciones.");
        }
        int columns = x[0].length;
        if (columns == 0) throw new IllegalArgumentException("Se requiere al menos una variable predictora.");
        double[][] design = new double[x.length][columns + 1];
        for (int i = 0; i < x.length; i++) {
            if (x[i].length != columns) throw new IllegalArgumentException("X debe ser rectangular.");
            design[i][0] = 1.0;
            System.arraycopy(x[i], 0, design[i], 1, columns);
        }
        return design;
    }

    @Override
    protected double[] features(double... values) {
        if (values.length != predictors) {
            throw new IllegalArgumentException("MLR requiere " + predictors + " variables predictoras.");
        }
        double[] row = new double[predictors + 1];
        row[0] = 1.0;
        System.arraycopy(values, 0, row, 1, predictors);
        return row;
    }
}
