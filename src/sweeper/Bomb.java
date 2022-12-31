package sweeper;

class Bomb {
    private Matrix bombMap;
    private int totalBombs;

    public Bomb(int totalBombs) {
        this.totalBombs = totalBombs;
    }

    void start() {
        bombMap = new Matrix(Box.CLOSED);
        for (int j = 0; j < totalBombs; j++) {
            placeBomb();
        }

    }

    Box get(Coord coord) {
        return bombMap.get((coord));
    }

    private void placeBomb() {
        Coord coord = Ranges.getRandomCoord();
        bombMap.set(coord, Box.BOMB);
        invNumberAroundBomb(coord);

    }

    private void invNumberAroundBomb(Coord coord) {
        for (Coord around : Ranges.getCoordsAround(coord)) {
            if (Box.BOMB != bombMap.get(around))
                bombMap.set(around, Box.NUM1);
        }
    }

}
