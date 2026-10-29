package regression;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

/** Agente JADE que ejecuta una regresión lineal múltiple. */
public final class MLRAgent extends Agent {
    @Override
    protected void setup() {
        System.out.println("Agente " + getLocalName() + " listo.");
        addBehaviour(new RegressionBehaviour());
    }

    private class RegressionBehaviour extends OneShotBehaviour {
        @Override
        public void action() {
            try {
                System.out.println("\n=== Regresión lineal múltiple (MLR) ===");
                // Dos predictores y respuesta hardcodeada (8 observaciones).
                double[][] x = {
                    {1, 2}, {2, 1}, {3, 4}, {4, 2},
                    {5, 3}, {6, 1}, {2, 5}, {3, 0}
                };
                double[] y = {3, 6.5, 4, 9, 9.5, 14.5, 0.5, 10};
                MLR model = new MLR(x, y);
                model.fit();
                double[] betas = model.coefficients();

                System.out.println("Ecuación: " + RegressionOutput.equation(
                        new String[]{"x1", "x2"}, betas));
                double[][] inputs = {{2, 3}, {4, 1}, {5, 4}};
                for (double[] input : inputs) {
                    System.out.println("Predicción para x1 = " + RegressionOutput.number(input[0])
                            + ", x2 = " + RegressionOutput.number(input[1])
                            + ": y = " + RegressionOutput.number(model.predict(input)));
                }
                System.out.println("R² = " + RegressionOutput.number(model.rSquared()));
            } finally {
                doDelete();
            }
        }
    }
}
