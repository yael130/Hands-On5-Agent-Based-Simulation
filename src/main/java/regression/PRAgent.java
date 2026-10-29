package regression;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

/** Agente JADE que ejecuta una regresión polinomial de grado dos. */
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
                System.out.println("\n=== Regresión polinomial (PR, grado 2) ===");
                // Dataset hardcodeado generado por y = 2 + 3x + 0.5x².
                double[] x = {-2, -1, 0, 1, 2, 3};
                double[] y = { -2, -0.5, 2, 5.5, 10, 15.5 };
                PolynomialRegression model = new PolynomialRegression(x, y, 2);
                model.fit();
                double[] betas = model.coefficients();

                System.out.println("Ecuación: " + RegressionOutput.equation(
                        new String[]{"x", "x²"}, betas));
                double[] inputs = {-1, 1.5, 4};
                for (double input : inputs) {
                    System.out.println("Predicción para x = " + RegressionOutput.number(input)
                            + ": y = " + RegressionOutput.number(model.predict(input)));
                }
                System.out.println("R² = " + RegressionOutput.number(model.rSquared()));
            } finally {
                doDelete();
            }
        }
    }
}
