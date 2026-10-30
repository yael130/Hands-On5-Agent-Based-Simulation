package regression;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

/** Agente JADE que predice Machine Efficiency a partir de Batch Size. */
public final class PRAgent extends Agent {
    @Override
    protected void setup() {
        System.out.println("Agente " + getLocalName() + " listo.");
        addBehaviour(new RegressionBehaviour());
    }

    private class RegressionBehaviour extends OneShotBehaviour {
        @Override
        public void action() {
            try {
                System.out.println("\n=== Regresión polinomial (PR, grado 3) ===");
                // Batch Size (x) y Machine Efficiency (y)
                double[] x = {
                    108, 115, 106, 97, 95, 91, 97, 83, 83, 78, 54, 67, 56,
                    53, 61, 115, 81, 78, 30, 45, 99, 32, 25, 28, 90, 89
                };
                double[] y = {
                    95, 96, 95, 97, 93, 94, 95, 93, 92, 86, 73, 80, 65,
                    69, 77, 96, 87, 89, 60, 63, 95, 61, 55, 56, 94, 93
                };
                PolynomialRegression model = new PolynomialRegression(x, y, 3);
                model.fit();
                double[] betas = model.coefficients();

                System.out.println("Ecuación: " + RegressionOutput.equation(
                        new String[]{"x", "x²", "x³"}, betas));
                // Predicciones dentro del rango observado de Batch Size: 25 a 115.
                double[] inputs = {40, 65, 100};
                for (double input : inputs) {
                    System.out.println("Predicción para x = " + RegressionOutput.number(input)
                            + ": y = " + RegressionOutput.number(model.predict(input)));
                }
                System.out.println("R² PRA= " + RegressionOutput.number(model.rSquared()));
            } finally {
                doDelete();
            }
        }
    }
}
