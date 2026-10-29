package regression;

/** Ajusta modelos lineales en sus parámetros mediante las ecuaciones normales. */
abstract class LeastSquaresRegression {
    private final double[] y;
    private final double[][] designMatrix;
    private double[] betas;

    LeastSquaresRegression(double[] y, double[][] designMatrix) {
        if (y == null || designMatrix == null || y.length != designMatrix.length || y.length == 0) {
            throw new IllegalArgumentException("X e y deben tener el mismo número de observaciones.");
        }
        int columns = designMatrix[0].length;
        if (columns == 0 || y.length < columns) {
            throw new IllegalArgumentException("Se necesitan al menos tantas observaciones como parámetros.");
        }
        for (double[] row : designMatrix) {
            if (row.length != columns) {
                throw new IllegalArgumentException("La matriz de diseño debe ser rectangular.");
            }
        }
        this.y = y.clone();
        this.designMatrix = copy(designMatrix);
    }

    /** Calcula beta = (X'X)^-1 X'y resolviendo el sistema con eliminación gaussiana. */
    public final void fit() {
        int parameters = designMatrix[0].length;
        double[][] normal = new double[parameters][parameters + 1];

        for (int row = 0; row < parameters; row++) {
            for (int column = 0; column < parameters; column++) {
                for (double[] observation : designMatrix) {
                    normal[row][column] += observation[row] * observation[column];
                }
            }
            for (int observation = 0; observation < y.length; observation++) {
                normal[row][parameters] += designMatrix[observation][row] * y[observation];
            }
        }
        betas = solve(normal);
    }

    protected abstract double[] features(double... predictors);

    public final double predict(double... predictors) {
        ensureFitted();
        double[] row = features(predictors);
        if (row.length != betas.length) {
            throw new IllegalArgumentException("Número de variables predictoras incorrecto.");
        }
        double prediction = 0.0;
        for (int i = 0; i < betas.length; i++) {
            prediction += betas[i] * row[i];
        }
        return prediction;
    }

    public final double rSquared() {
        ensureFitted();
        double mean = 0.0;
        for (double value : y) mean += value;
        mean /= y.length;

        double residualSum = 0.0;
        double totalSum = 0.0;
        for (int i = 0; i < y.length; i++) {
            double residual = y[i] - predictFromDesignRow(designMatrix[i]);
            residualSum += residual * residual;
            double centered = y[i] - mean;
            totalSum += centered * centered;
        }
        if (totalSum == 0.0) return residualSum == 0.0 ? 1.0 : Double.NaN;
        return 1.0 - residualSum / totalSum;
    }

    public final double[] coefficients() {
        ensureFitted();
        return betas.clone();
    }

    private double predictFromDesignRow(double[] row) {
        double value = 0.0;
        for (int i = 0; i < betas.length; i++) value += betas[i] * row[i];
        return value;
    }

    private void ensureFitted() {
        if (betas == null) throw new IllegalStateException("Primero se debe llamar a fit().");
    }

    private static double[] solve(double[][] augmented) {
        int n = augmented.length;
        for (int pivot = 0; pivot < n; pivot++) {
            int best = pivot;
            for (int row = pivot + 1; row < n; row++) {
                if (Math.abs(augmented[row][pivot]) > Math.abs(augmented[best][pivot])) best = row;
            }
            if (Math.abs(augmented[best][pivot]) < 1e-12) {
                throw new IllegalArgumentException("El modelo no es identificable: X'X es singular.");
            }
            double[] temp = augmented[pivot];
            augmented[pivot] = augmented[best];
            augmented[best] = temp;

            double divisor = augmented[pivot][pivot];
            for (int column = pivot; column <= n; column++) augmented[pivot][column] /= divisor;
            for (int row = 0; row < n; row++) {
                if (row == pivot) continue;
                double factor = augmented[row][pivot];
                for (int column = pivot; column <= n; column++) {
                    augmented[row][column] -= factor * augmented[pivot][column];
                }
            }
        }
        double[] solution = new double[n];
        for (int i = 0; i < n; i++) solution[i] = augmented[i][n];
        return solution;
    }

    private static double[][] copy(double[][] matrix) {
        double[][] result = new double[matrix.length][];
        for (int i = 0; i < matrix.length; i++) result[i] = matrix[i].clone();
        return result;
    }
}
