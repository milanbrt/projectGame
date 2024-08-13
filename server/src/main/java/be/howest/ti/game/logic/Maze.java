package be.howest.ti.game.logic;

import java.security.SecureRandom;
import java.util.*;

public class Maze {
    private Tile[][] tiles;
    private Location lastInsertedTileLocation;
    private Tile spareTile;
    private final List<boolean[]> possibleTiles = Arrays.asList(
            new boolean[]{true, true, false, false},
            new boolean[]{true, false, true, false},
            new boolean[]{true, false, false, false}
    );

    private final List<String> possibleTreasures = List.of("Bag of Gold Coins",
            "Bat",
            "Book with Clasp",
            "Dragon",
            "Ghost (in bottle)",
            "Ghost (waving)",
            "Gold Crown",
            "Gold Menorah",
            "Gold Ring",
            "Helmet (armor)",
            "Jewel",
            "Lady Pig",
            "Lizard",
            "Moth",
            "Owl",
            "Rat",
            "Scarab",
            "Set of Keys",
            "Skull",
            "Sorceress",
            "Spider on Web",
            "Sword",
            "Treasure Chest",
            "Treasure Map");

    Deque<String> treasures = new ArrayDeque<>();


    public Maze() {
        this.tiles = new Tile[7][7];
        initializeFixedTiles();
        generateRandomTiles();
        lastInsertedTileLocation = new Location(0, 0);
        SecureRandom random = new SecureRandom();
        int randomIndex = random.nextInt(possibleTiles.size());
        boolean[] walls = possibleTiles.get(randomIndex);
        int randomTimes = random.nextInt(4);
        boolean[] randomWalls = executeRightRandomTimes(walls, randomTimes);
        this.spareTile = new Tile("", randomWalls, null, new Location(0, 0));
        spareTile.setTreasure("null");
        generateRandomTreasuresOnBoard();

        setUpRandomTreasures();
    }

    private void setUpRandomTreasures(){
        List<String> shuffleTreasures = new ArrayList<>(possibleTreasures);
        Collections.shuffle(shuffleTreasures);
        treasures.addAll(shuffleTreasures);
    }

    public String getRandomTreasure(){
        return treasures.pollFirst();
    }


   private void generateRandomTreasuresOnBoard(){
       List<Location> locations = new ArrayList<>();
       for (int row = 0; row < 7; row++) {
           for (int col = 0; col < 7; col++) {
               if (!(row == 0 && col == 0 || row == 0 && col == tiles.length - 1 || row == tiles.length - 1 && col == 0 || row == tiles.length - 1 && col == tiles.length - 1)) {
                   locations.add(new Location(row, col));
               }
           }
       }
       Collections.shuffle(locations);
       for (int i = 0; i < possibleTreasures.size(); i++){
           Location location = locations.get(i);
           tiles[location.getRow()][location.getCol()].setTreasure(possibleTreasures.get(i));
       }
   }


    private void removeNull(){
        for (int row = 0; row < 7; row++){
            for (int col = 0; col < 7; col++){
                Tile tile = tiles[row][col];
                String treasure = tile.getTreasure();
                if (treasure != null && treasure.equals("null")){
                    tile.setTreasure(null);
                }
            }
        }
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public Maze getMaze() {
        return this;
    }

    private void removeAllLocations(Player player){
        for (int row = 0; row < 7; row++){
            for (int col = 0; col < 7; col++){
                Tile tile = tiles[row][col];
                List<String> players = tile.getPlayers();
                if (players != null){
                    tile.removePlayer(player);
                }
            }
        }
    }

    public Player movePlayer(Player player, Location newLocation) {
        Location currentLocation = player.getLocation();
        Tile currentTile = tiles[currentLocation.getRow()][currentLocation.getCol()];
        Tile newTile = tiles[newLocation.getRow()][newLocation.getCol()];
        if (getReachableTiles(currentTile).contains(newTile)) {
            currentTile.removePlayer(player);
            newTile.addPlayer(player);
            removeAllLocations(player);
            player.setLocation(newLocation, getMaze());
        } else {
            throw new IllegalArgumentException("Player cannot move to this location");
        }
        return player;
    }

    public Player shoveTile(Location location, Tile newTile, Player player, Game game){
        int row = location.getRow();
        int col = location.getCol();
        
        if(checkExceptionsShoveTile(location, player, game)){
            if (row == 0){
                spareTile = shiftColDown(col, newTile);
                shiftPlayerDown(game, col);
            }
            if (col == tiles.length - 1){
                spareTile = shiftRowLeft(row, newTile);
                shiftPlayerLeft(game, row);
            }
            if (col == 0){
                shiftPlayerRight(game, row);
                spareTile = shiftRowRight(row, newTile);

            }
            if (row == tiles.length - 1){
                spareTile = shiftColUp(col, newTile);
                shiftPlayerUp(game, col);
            }
        }
        lastInsertedTileLocation = location;
        return player;
    }

    private Boolean checkExceptionsShoveTile(Location location, Player player, Game game){
        int row = location.getRow();
        int col = location.getCol();



        if (!game.getCurrentShovePlayer().equals(player.getPlayerName().toString())){
            throw new IllegalArgumentException("Player cannot shove when it's not their turn");
        }

        if (lastInsertedTileLocation.getCol() == col && lastInsertedTileLocation.getRow() == row){
            throw new IllegalArgumentException("Cannot shove tile in the same location");
        }

        if (row % 2 == 0 && col % 2 == 0){
            throw new IllegalArgumentException("Cannot shove tile in fixed location");
        }

        if (row != 0 && row != tiles.length - 1 && col != 0 && col != tiles.length - 1){
            throw new IllegalArgumentException("Cannot shove tile in this location");
        }

        return true;
    }

    private void shiftPlayerDown(Game game, int col){
        List<Player> alreadyMoved = new ArrayList<>();
        for (Player player : game.getPlayers()){
            if (player.getLocation().getCol() == col && !alreadyMoved.contains(player)){
                    if (player.getLocation().getRow() == 6){
                        tiles[6][col].removePlayer(player);
                        tiles[0][col].addPlayer(player);
                        player.setLocation(new Location(0, col), getMaze());

                    } else{
                        tiles[player.getLocation().getRow()][col].removePlayer(player);
                        tiles[player.getLocation().getRow() + 1][col].addPlayer(player);
                        player.setLocation(new Location(player.getLocation().getRow() + 1, col), getMaze());
                    }
                    alreadyMoved.add(player);
                }

        }
    }

    private Tile shiftColDown(int col, Tile spareTile){
        Tile temp = tiles[tiles.length - 1][col];
        for (int row = tiles.length - 1; row > 0; row--){
            tiles[row][col] = tiles[row - 1][col];
            tiles[row][col].setLocation(new Location(row, col));
        }
        if (tiles[0][col].getTreasure() == null){
            tiles[0][col].setTreasure("");
        }
        tiles[0][col] = spareTile;

        return temp;
    }

    private void shiftPlayerUp(Game game, int col){
        List<Player> alreadyMoved = new ArrayList<>();
        for (Player player : game.getPlayers()){
            if (player.getLocation().getCol() == col && !alreadyMoved.contains(player)){
                    if (player.getLocation().getRow() == 0){
                        tiles[0][col].removePlayer(player);
                        tiles[tiles.length - 1][col].addPlayer(player);
                        player.setLocation(new Location(tiles.length - 1, col), getMaze());

                    } else{
                        tiles[player.getLocation().getRow()][col].removePlayer(player);
                        tiles[player.getLocation().getRow() - 1][col].addPlayer(player);
                        player.setLocation(new Location(player.getLocation().getRow() - 1, col), getMaze());
                    }
                    alreadyMoved.add(player);
                }

        }
    }

    private Tile shiftColUp(int col, Tile spareTile){
        Tile temp = tiles[0][col];
        for (int row = 0; row < tiles.length - 1; row++){
            tiles[row][col] = tiles[row + 1][col];
            tiles[row][col].setLocation(new Location(row, col));
        }
        if (tiles[tiles.length - 1][col].getTreasure() == null){
            tiles[tiles.length - 1][col].setTreasure("");
        }
        tiles[tiles.length - 1][col] = spareTile;
        return temp;
    }

    private void shiftPlayerRight(Game game, int row){
        List<Player> alreadyMoved = new ArrayList<>();
        for (Player player : game.getPlayers()){
            if (player.getLocation().getRow() == row && !alreadyMoved.contains(player)){
                    if (player.getLocation().getCol() == tiles.length - 1){
                        tiles[row][tiles.length - 1].removePlayer(player);
                        tiles[row][0].addPlayer(player);
                        player.setLocation(new Location(row, 0), getMaze());

                    } else{
                        tiles[row][player.getLocation().getCol()].removePlayer(player);
                        tiles[row][player.getLocation().getCol() + 1].addPlayer(player);
                        player.setLocation(new Location(row, player.getLocation().getCol() + 1), getMaze());
                    }
                    alreadyMoved.add(player);
                }

        }
    }

    private Tile shiftRowRight(int row, Tile spareTile){
        Tile temp = tiles[row][tiles.length - 1];
        for (int col = tiles.length - 1; col > 0; col--){
            tiles[row][col] = tiles[row][col - 1];
            tiles[row][col].setLocation(new Location(row, col));
        }
        if (tiles[row][0].getTreasure() == null){
            tiles[row][0].setTreasure("");
        }
        tiles[row][0] = spareTile;
        return temp;
    }

    private void shiftPlayerLeft(Game game, int row){
        List<Player> alreadyMoved = new ArrayList<>();
        for (Player player : game.getPlayers()){
            if (player.getLocation().getRow() == row && !alreadyMoved.contains(player)){
                    if (player.getLocation().getCol() == 0){
                        tiles[row][0].removePlayer(player);
                        tiles[row][tiles.length - 1].addPlayer(player);
                        player.setLocation(new Location(row, tiles.length - 1), getMaze());

                    } else{
                        tiles[row][player.getLocation().getCol()].removePlayer(player);
                        tiles[row][player.getLocation().getCol() - 1].addPlayer(player);
                        player.setLocation(new Location(row, player.getLocation().getCol() - 1), getMaze());

                    }
                    alreadyMoved.add(player);
                }

        }
    }

    private Tile shiftRowLeft(int row, Tile spareTile){
        Tile temp = tiles[row][0];
        for (int col = 0; col < tiles.length - 1; col++){
            tiles[row][col] = tiles[row][col + 1];
            tiles[row][col].setLocation(new Location(row, col));
        }
        if (tiles[row][tiles.length - 1].getTreasure() == null){
            tiles[row][tiles.length - 1].setTreasure("");
        }
        tiles[row][tiles.length - 1] = spareTile;
        return temp;
    }


    private boolean[] moveTileToRight(boolean[] walls) {
        boolean[] newWalls = new boolean[4];
        newWalls[0] = walls[3];
        newWalls[1] = walls[0];
        newWalls[2] = walls[1];
        newWalls[3] = walls[2];
        return newWalls;
    }

    private boolean[] executeRightRandomTimes(boolean[] walls, int randomTimes) {
        for (int i = 0; i < randomTimes; i++) {
            walls = moveTileToRight(walls);
        }
        return walls;
    }

    public List<List<Map<String, Object>>> getMazeBridge() {
        List<List<Map<String, Object>>> mazeStructure = new ArrayList<>();
        removeNull();
        for (Tile[] row : tiles) {
            List<Map<String, Object>> rowStructure = new ArrayList<>();
            for (Tile tile : row) {
                Map<String, Object> tileStructure = new HashMap<>();
                tileStructure.put("walls", tile.getWalls());
                tileStructure.put("treasure", tile.getTreasure());
                tileStructure.put("players", tile.getPlayers());
                rowStructure.add(tileStructure);
            }
            mazeStructure.add(rowStructure);
        }
        return mazeStructure;
    }

    public Tile getSpareTile() {
        return spareTile;
    }

    public Tile getTile(Location location) {
        return tiles[location.getRow()][location.getCol()];
    }

    private void generateRandomTiles(){
        SecureRandom random = new SecureRandom();
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles.length; col++) {
                // If the tile is not a fixed tile, generate a new tile
                if (!(row % 2 == 0 && col % 2 == 0)) {
                    int randomIndex = random.nextInt(possibleTiles.size());
                    boolean[] walls = possibleTiles.get(randomIndex);
                    int randomTimes = random.nextInt(4);
                    boolean[] randomWalls = executeRightRandomTimes(walls, randomTimes);
                    tiles[row][col] = new Tile("null", randomWalls, null, new Location(row, col));
                }
            }
        }
    }

    private void initializeFixedTiles(){
        tiles[0][0] = new Tile("null", new boolean[]{true, false, false, true}, null, new Location(0, 0));
        tiles[0][2] = new Tile("null", new boolean[]{true, false, false, false}, null,new Location(0, 2));
        tiles[0][4] = new Tile("null", new boolean[]{true, false, false, false}, null, new Location(0, 4));
        tiles[0][6] = new Tile("null", new boolean[]{true, true, false, false}, null, new Location(0, 6));

        tiles[2][0] = new Tile("null", new boolean[]{false, false, false, true}, null, new Location(2, 0));
        tiles[2][2] = new Tile("null", new boolean[]{false, false, false, true}, null, new Location(2, 2));
        tiles[2][4] = new Tile("null", new boolean[]{true, false, false, false}, null, new Location(2, 4));
        tiles[2][6] = new Tile("null", new boolean[]{false, true, false, false}, null, new Location(2, 6));

        tiles[4][0] = new Tile("null", new boolean[]{false, false, false, true}, null, new Location(4, 0));
        tiles[4][2] = new Tile("null", new boolean[]{false, false, true, false}, null, new Location(4, 2));
        tiles[4][4] = new Tile("null", new boolean[]{false, true, false, false}, null, new Location(4, 4));
        tiles[4][6] = new Tile("null", new boolean[]{false, true, false, false}, null, new Location(4, 6));

        tiles[6][0] = new Tile("null", new boolean[]{false, false, true, true}, null, new Location(6, 0));
        tiles[6][2] = new Tile("null", new boolean[]{false, false, true, false}, null, new Location(6, 2));
        tiles[6][4] = new Tile("null", new boolean[]{false, false, true, false}, null, new Location(6, 4));
        tiles[6][6] = new Tile("null", new boolean[]{false, true, true, false}, null, new Location(6, 6));
    }



    public Set<Tile> getReachableTiles(Tile startTile) {
        Set<Tile> reachable = new HashSet<>();
        Queue<Tile> unprocessed = new LinkedList<>();

        reachable.add(startTile);
        unprocessed.add(startTile);
        while (!unprocessed.isEmpty()) {
            Tile currentTile = unprocessed.poll();
            for (Tile neighbour: getReachableNeighbours(currentTile)) {
                if (!reachable.contains(neighbour)) {
                    reachable.add(neighbour);
                    unprocessed.add(neighbour);
                }
            }
        }
        return reachable;
    }

    private List<Tile> getReachableNeighbours(Tile currentTile) {
        List<Tile> neighbours = new ArrayList<>();
        int currentRow = currentTile.getLocation().getRow();
        int currentCol = currentTile.getLocation().getCol();

        checkNorth(currentRow, currentCol, currentTile, neighbours);
        checkEast(currentRow, currentCol, currentTile, neighbours);
        checkSouth(currentRow, currentCol, currentTile, neighbours);
        checkWest(currentRow, currentCol, currentTile, neighbours);

        return neighbours;
    }

    private void checkNorth(int currentRow, int currentCol, Tile currentTile, List<Tile> neighbours){
        if (currentRow > 0) {
            Tile neighbour = tiles[currentRow - 1][currentCol];
            if (!currentTile.getWalls()[0] && !neighbour.getWalls()[2]) {
                neighbours.add(neighbour);
            }
        }
    }

    private void checkEast(int currentRow, int currentCol, Tile currentTile, List<Tile> neighbours){
        if (currentCol < tiles.length - 1) {
            Tile neighbour = tiles[currentRow][currentCol + 1];
            if (!currentTile.getWalls()[1] && !neighbour.getWalls()[3]) {
                neighbours.add(neighbour);
            }
        }
    }

    private void checkSouth(int currentRow, int currentCol, Tile currentTile, List<Tile> neighbours){
        if (currentRow < tiles.length - 1) {
            Tile neighbour = tiles[currentRow + 1][currentCol];
            if (!currentTile.getWalls()[2] && !neighbour.getWalls()[0]) {
                neighbours.add(neighbour);
            }
        }
    }

    private void checkWest(int currentRow, int currentCol, Tile currentTile, List<Tile> neighbours){
        if (currentCol > 0) {
            Tile neighbour = tiles[currentRow][currentCol - 1];
            if (!currentTile.getWalls()[3] && !neighbour.getWalls()[1]) {
                neighbours.add(neighbour);
            }
        }
    }

}
