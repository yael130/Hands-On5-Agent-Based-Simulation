package regression;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

/** Inicia los tres agentes dentro de un contenedor JADE principal. */
public final class RegressionLauncher {
    private RegressionLauncher() { }

    public static void main(String[] args) throws Exception {
        Runtime runtime = Runtime.instance();
        Profile profile = new ProfileImpl();
        // La consola solicitada es la terminal; sin GUI, JADE no requiere X11.
        profile.setParameter(Profile.GUI, "false");
        // JADE escribe aquí los descriptores de plataforma y MTP, fuera de la raíz.
        profile.setParameter(Profile.FILE_DIR, "out/");
        ContainerController container = runtime.createMainContainer(profile);

        start(container, "SLR", SLRAgent.class);
        start(container, "PR", PRAgent.class);
        start(container, "MLR", MLRAgent.class);
    }

    private static void start(ContainerController container, String name,
                              Class<? extends jade.core.Agent> agentClass) throws Exception {
        System.out.println("Creando agente " + name + " (" + agentClass.getSimpleName() + ")...");
        AgentController agent = container.createNewAgent(name, agentClass.getName(), new Object[0]);
        agent.start();
        System.out.println("Agente " + name + " activado.");
    }
}
