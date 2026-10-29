package regression;

import java.util.Locale;

final class RegressionOutput {
    private RegressionOutput() { }

    static String equation(String[] terms, double[] betas) {
        StringBuilder equation = new StringBuilder("y = ").append(number(betas[0]));
        for (int i = 1; i < betas.length; i++) {
            equation.append(betas[i] < 0 ? " - " : " + ")
                    .append(number(Math.abs(betas[i]))).append(terms[i - 1]);
        }
        return equation.toString();
    }

    static String number(double value) {
        return String.format(Locale.US, "%.4f", value);
    }
}
