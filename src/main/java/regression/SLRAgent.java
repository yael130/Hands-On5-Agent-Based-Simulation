package regression;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

/** Agente JADE que ejecuta una regresión lineal simple una sola vez. */
public final class SLRAgent extends Agent {
    @Override
    protected void setup() {
        System.out.println("Agente " + getLocalName() + " listo.");
        addBehaviour(new RegressionBehaviour());
    }

    private class RegressionBehaviour extends OneShotBehaviour {
        @Override
        public void action() {
            try {
                System.out.println("\n=== Regresión lineal simple (SLR) ===");
                // Dataset de ejemplo hardcodeado: y = 2 + 1.5x aproximadamente.
                double[] x = {1, 2, 3, 4, 5, 6};
                double[] y = {3.2, 4.8, 6.4, 7.7, 9.3, 11.1};
                SRL model = new SRL(x, y);
                model.fit();
                double[] betas = model.coefficients();

                System.out.println("Ecuación: " + RegressionOutput.equation(new String[]{"x"}, betas));
                double[] inputs = {2, 4, 7};
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
