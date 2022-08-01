package engine;

public class Timer {
    private double lastLoopTime;

    public void init(){
        lastLoopTime = getTime();
    }

    public double getTime(){
        return System.nanoTime() / 1_000_000_000.0;
    }

    public float getElapsedTime(){
        double time = getTime();
        // On calcul l'écart entre deux temps
        float elapsedTime = (float) (time - lastLoopTime);
        // On assigne time à lastLoopTime pour les prochains appel de la fonction.
        lastLoopTime = time;
        return elapsedTime;
    }

    public double getLastLoopTime(){
        return lastLoopTime;
    }
}
