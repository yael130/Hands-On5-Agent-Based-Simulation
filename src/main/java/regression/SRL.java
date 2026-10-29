package regression;

/** Regresión lineal simple: y = beta0 + beta1*x. */
public final class SRL extends LeastSquaresRegression {
    public SRL(double[] x, double[] y) {
        super(y, simpleDesign(x, y));
    }

    private static double[][] simpleDesign(double[] x, double[] y) {
        validateLengths(x, y);
        double[][] matrix = new double[x.length][2];
        for (int i = 0; i < x.length; i++) {
            matrix[i][0] = 1.0;
            matrix[i][1] = x[i];
        }
        return matrix;
    }

    private static void validateLengths(double[] x, double[] y) {
        if (x == null || y == null || x.length != y.length) {
            throw new IllegalArgumentException("X e y deben tener el mismo número de observaciones.");
        }
    }

    @Override
    protected double[] features(double... predictors) {
        if (predictors.length != 1) throw new IllegalArgumentException("SLR requiere una variable x.");
        return new double[]{1.0, predictors[0]};
    }
}
