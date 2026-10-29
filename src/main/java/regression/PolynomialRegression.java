package regression;

/** Regresión polinomial y = beta0 + beta1*x + ... + betak*x^k. */
public final class PolynomialRegression extends LeastSquaresRegression {
    private final int degree;

    public PolynomialRegression(double[] x, double[] y, int degree) {
        super(y, polynomialDesign(x, y, degree));
        this.degree = degree;
    }

    private static double[][] polynomialDesign(double[] x, double[] y, int degree) {
        if (x == null || y == null || x.length != y.length) {
            throw new IllegalArgumentException("X e y deben tener el mismo número de observaciones.");
        }
        if (degree < 1 || x.length < degree + 1) {
            throw new IllegalArgumentException("El grado debe ser positivo y requiere al menos grado + 1 observaciones.");
        }
        double[][] matrix = new double[x.length][degree + 1];
        for (int i = 0; i < x.length; i++) {
            matrix[i][0] = 1.0;
            for (int power = 1; power <= degree; power++) matrix[i][power] = Math.pow(x[i], power);
        }
        return matrix;
    }

    @Override
    protected double[] features(double... predictors) {
        if (predictors.length != 1) throw new IllegalArgumentException("La regresión polinomial requiere una variable x.");
        double[] row = new double[degree + 1];
        row[0] = 1.0;
        for (int power = 1; power <= degree; power++) row[power] = Math.pow(predictors[0], power);
        return row;
    }
}
