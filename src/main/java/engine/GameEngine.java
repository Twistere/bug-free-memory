package engine;

public class GameEngine implements Runnable{
    // Constante
    private static final int TARGET_FPS = 144;
    /**
     * TickRate du jeu
     * Le nombre de fois par seconde que le jeu se met à jour.
     */
    private static final int TARGET_UPS = 30;

    private final Window window;
    private final Timer timer;
    private final IGameLogic gameLogic;

    public GameEngine(String windowTitle, int width, int height, boolean vSync, IGameLogic gameLogic)  {
        window = new Window(windowTitle, width, height, vSync);
        this.gameLogic = gameLogic;
        timer = new Timer();
    }

    @Override
    public void run() {
        try{
            init();
            gameLoop();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cleanup();
        }
    }

    protected void cleanup(){
        gameLogic.cleanup();
    }
    protected void init() throws Exception{
        window.init();
        timer.init();
        gameLogic.init();
    }
    protected void gameLoop(){
        float elapsedTime;
        float accumalator = 0f;
        float interval = 1f / TARGET_UPS;
        boolean running = true;
        while  (running && !window.windowShouldClose()){
            elapsedTime = timer.getElapsedTime();
            accumalator += elapsedTime;

            input();

            while (accumalator >= interval){
                update(interval);
                accumalator -= interval;
            }

            render();

            if(!window.isvSync())
                sync();
        }
    }

    private void sync(){
        float loopSlot = 1f / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;
        while (timer.getTime() < endTime){
            try{
                Thread.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    protected void input(){
        gameLogic.input(window);
    }

    protected void update(float interval){
        gameLogic.update(interval);
    }

    protected void render(){
        gameLogic.render(window);
        window.update();
    }

}
