# AgentBasedSimulation

Implementación de tres técnicas de regresión con agentes JADE:

- **SLR:** regresión lineal simple.
- **PR:** regresión polinomial de grado 2.
- **MLR:** regresión lineal múltiple con dos predictores.

Los modelos ajustan los coeficientes por mínimos cuadrados mediante las ecuaciones normales `beta = (X'X)^-1 X'y`, resueltas con eliminación gaussiana y pivoteo parcial. Cada agente tiene un único `OneShotBehaviour`; dentro de su `action()` define un dataset hardcodeado, ajusta el modelo, imprime ecuación, tres predicciones y R², y luego solicita su eliminación con `doDelete()`. El lanzador corre JADE sin consola gráfica para que funcione  desde una terminal sin X11; JADE guarda sus archivos auxiliares dentro de `out/`. La plataforma mantiene el proceso abierto tras terminar los agentes.
 
Las siglas de las clases de agente corresponden a **SLR** (Simple Linear Regression), **PR** (Polynomial Regression) y **MLR** (Multiple Linear Regression). 
`SRL` es el nombre del modelo simple solicitado en la actividad; 
`MLR` es su modelo múltiple.

## Estructura

- `src/main/java/regression/SRL.java`: modelo de regresión lineal simple.
- `src/main/java/regression/PolynomialRegression.java`: modelo de regresión polinomial configurable.
- `src/main/java/regression/MLR.java`: modelo de regresión lineal múltiple.
- `src/main/java/regression/SLRAgent.java`, `PRAgent.java`, `MLRAgent.java`: agentes JADE de las tres técnicas.
- `src/main/java/regression/LeastSquaresRegression.java`: implementación compartida de mínimos cuadrados y R².
- `src/main/java/regression/RegressionLauncher.java`: inicia los tres agentes de JADE en modo consola.

## Compilar y ejecutar en Linux

Desde la raíz `AgentBasedSimulation`:

```bash
JADE_JAR="../JADE-all-4.6.0/JADE-bin-4.6.0/jade/lib/jade.jar"
mkdir -p out
javac --release 25 -encoding UTF-8 -cp "$JADE_JAR" -d out $(find src/main/java -name '*.java')
java -cp "out:$JADE_JAR" regression.RegressionLauncher
```

En VS Code también puedes abrir la paleta de comandos (`Ctrl+Shift+P`), elegir **Tasks: Run Task** y ejecutar **Compilar y ejecutar regresiones JADE**. La tarea compila de nuevo y abre la salida en una terminal integrada dedicada.

`--release 25` asegura que las clases compiladas por `javac` 27 sean compatibles con la JVM 25. Los datos y los valores para las tres predicciones están definidos en el `action()` de cada agente.
