package sweeper;

public class Game {

    private Bomb bomb;
    private Flag flag;

    private GameState gameState;

    public GameState getGameState() {
        return gameState;
    }

    public Game(int cols, int rows, int bombs) {
        Ranges.setSize(new Coord(cols, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    public void start() {
        bomb.start();
        flag.start();
        gameState = GameState.PLAYED;
    }

    public Box getBox(Coord coord) {
        if (flag.get(coord) == Box.OPENED) {
            return bomb.get(coord);
        } else {
            return flag.get(coord);
        }
    }

    public void pressLeftButton(Coord coord) {
        if (gameOver()) {return;}
        openBox(coord);
        checkWinner();
    }

    private boolean gameOver() {
        if (gameState == GameState.PLAYED) {
            return false;
        }
        start();
        return true;
    }

    private void checkWinner() {
        if (gameState == GameState.PLAYED) {
            if (flag.getCoountOfClosedBoxes() == bomb.getTotalBombs()) {
                gameState = GameState.WINNER;
            }
        }
    }

    private void openBox(Coord coord) {
        switch (flag.get(coord)) {
            case OPENED:
                setOpenedToClosedBoxesAroundNumber(coord); return;
            case FLAGED:
                return;
            case CLOSED:
                switch (bomb.get(coord)) {
                    case ZERO:
                        openBoxesAround(coord); return;
                    case BOMB:
                        openBombs(coord); return;
                    default:
                        flag.setOpenedToBox(coord);
                }

        }
    }

    void setOpenedToClosedBoxesAroundNumber(Coord coord){
        if (bomb.get(coord) != Box.BOMB){
            if (flag.getCountOfFlagetBoxesAround(coord) == bomb.get(coord).getNumber()){
                for (Coord around : Ranges.getCoordsAround(coord)){
                    if (flag.get(around) == Box.CLOSED){
                        openBox(around);
                    }
                }
        }
        }

    }

    private void openBombs(Coord bombed) {
        gameState = GameState.BOMBED;
        flag.setBombedToBox(bombed);
        for (Coord coord : Ranges.getAllCoords()) {
            if (bomb.get(coord) == Box.BOMB) {
                flag.setOpenedToClosedBombBox(coord);
            } else
                flag.setNoBombToFlageSafeBox(coord);
        }
    }

    private void openBoxesAround(Coord coord) {
        flag.setOpenedToBox(coord);
        for (Coord around : Ranges.getCoordsAround(coord)) {
            openBox(around);
        }
    }

    public void pressRigthButton(Coord coord) {
        if (gameOver()) {return;}
        flag.toggleFlagetToBox(coord);
    }
}
