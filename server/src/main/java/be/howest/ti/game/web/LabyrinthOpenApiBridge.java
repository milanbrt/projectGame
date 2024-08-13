package be.howest.ti.game.web;

import be.howest.ti.game.logic.*;
import be.howest.ti.game.web.tokens.FancyTokens;
import be.howest.ti.game.web.tokens.PlainTextTokens;
import be.howest.ti.game.web.tokens.TokenManager;
import be.howest.ti.game.web.views.request.*;
import be.howest.ti.game.web.views.response.*;
import io.vertx.ext.web.handler.BearerAuthHandler;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LabyrinthOpenApiBridge extends OpenApiBridge { // NOSONAR this is not a monster class, it is a bridge :-)

    private static final Logger LOGGER = Logger.getLogger(LabyrinthOpenApiBridge.class.getName());

    private final TokenManager tokenManager;
    private final LabyrinthService service;

    public LabyrinthOpenApiBridge() {
        this(new LabyrinthServiceImpl(), new FancyTokens(UUID.randomUUID().toString()));
    }

    LabyrinthOpenApiBridge(LabyrinthService service, TokenManager tokenManager) {
        setSecurityHandlers(Map.ofEntries(
                Map.entry("playerToken", BearerAuthHandler.create(tokenManager)))
        );

        this.service = service;
        this.tokenManager = tokenManager;

        if (this.tokenManager == null || this.service == null) {
            LOGGER.log(Level.SEVERE, "TokenManager and LabyrinthService are not provided ...");
        }
    }


    @Operation("get-info")
    public ResponseWithHiddenStatus getInfo(GetInfoRequest ctx) {
        LOGGER.log(Level.INFO, "In request handler of: get-info");
        return new MessageResponse(501, "NYI: get-info");
    }

    @Operation("get-treasures")
    public ResponseWithHiddenStatus getTreasures(GetTreasuresRequest request) {
        LOGGER.log(Level.INFO, "In request handler of: get-treasures");
        return new GetTreasuresResponse(List.of("Bag of Gold Coins",
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
                "Treasure Map"));
    }


    @Operation("get-games")
    public ResponseWithHiddenStatus getGames(GetGamesRequest request) {
        LOGGER.log(Level.INFO, "In request handler of: get-games");

        List<Map<String, Object>> games = new ArrayList<>();
        List<Game> allGames = service.getGamesByPrefix(request.getPrefix());

        for (Game game : allGames) {
            Map<String, Object> gameInfo = game.getGamesRunning();
            games.add(gameInfo);
        }
        return new GetGameResponse(games);
    }


    @Operation("create-game")
    public ResponseWithHiddenStatus createGame(CreateGameRequest request) {
        LOGGER.log(Level.INFO, "In request handler of: create-game");
        String prefix = request.getPrefix();
        String gameName = request.getGameName();
        String playerName = request.getPlayerName();
        String gameMode = request.getGameMode();
        int minPlayers = request.getMinPlayers();
        int maxPlayers = request.getMaxPlayers();
        int maxTreasures = request.getMaxTreasures();

        Game game = service.configureGame(gameName, prefix, playerName, gameMode, minPlayers, maxPlayers, maxTreasures);

        Player user = new Player(game.getGameId().toString(), playerName);
        String playerToken = tokenManager.createToken(user);

        return new CreateGameResponse(game.getGameId().toString(), playerName, playerToken);
    }

    @Operation("delete-games")
    public ResponseWithHiddenStatus deleteGames(DeleteGamesRequest request) {
        LOGGER.log(Level.INFO, "In request handler of: delete-games");
        return new MessageResponse(501, "NYI: delete-games");
    }

    @Operation("get-game-details")
    public ResponseWithHiddenStatus sendGetGameDetailsResponse(GetGameDetailsRequest request) {
        LOGGER.log(Level.INFO, "In request handler of: get-game-details");
        String gameId = request.getGameId();

        Game game = service.getGameById(gameId);

        Map<String, Object> gameInfo = null;
        if (request.getDescription() != null) {
            gameInfo = game.getGamesRunning();
        }

        Map<String, Object> allPlayersInfo = null;
        if (request.getPlayers() != null) {
            allPlayersInfo = game.getAllPlayerDetails();
        }

        List<List<Map<String, Object>>> mazeStructure = null;
        if (request.getMaze() != null) {
            mazeStructure = game.getMaze().getMazeBridge();
        }

        Map<String, Object> spareTile = null;
        if (request.getSpareTile() != null) {
            spareTile = game.getMaze().getSpareTile().getSpareTileInfo();
        }
        return new GetGameDetailsResponse(gameInfo, allPlayersInfo, mazeStructure, spareTile);
    }


    @Operation("shove-tile")
    public ResponseWithHiddenStatus shoveTile(ShoveTileRequest request) {
        LOGGER.log(Level.INFO, "In request handler of: shove-tile");

        Game game = service.getGameById(request.getGameId());

        LabyrinthUser user = request.getUser();
        Player player = new Player(user);
        Location destination = request.getDestination();
        Tile tile = request.getTile();

        Player shoveplayer = game.getMaze().shoveTile(destination, tile, player, game);
        game.giveTurnToPlayer(shoveplayer);


        Maze maze = game.getMaze();

        tile = maze.getSpareTile();

        return new ShoveTileResponse(maze, tile);
    }

    @Operation("get-reachable-locations")
    public ResponseWithHiddenStatus getReachableLocations(GetReachableLocationsRequest request) {
        LOGGER.log(Level.INFO, "In request handler of: get-reachable-locations");

        Game game = service.getGameById(request.getGameId());

        Maze maze = game.getMaze();

        int row = request.getRow();
        int col = request.getCol();

        Location location = new Location(row, col);
        Tile tile = maze.getTile(location);

        Set<Tile> reachableLocations = null;
        if (tile != null) {
            reachableLocations = maze.getReachableTiles(tile);
        }
        Set<Location> reachableLocationsOnlyLocation = new HashSet<>();
        for (Tile t : reachableLocations) {
            reachableLocationsOnlyLocation.add(t.getLocation());
        }

        return new GetReachableLocationsResponse(reachableLocationsOnlyLocation);
    }

    @Operation("get-player-details")
    public ResponseWithHiddenStatus getPlayerDetails(GetPlayerDetailsRequest request) {
        LOGGER.log(Level.INFO, "In request handler of: get-player-details");
        String playerName = request.getPlayerName();
        Game game = service.getGameById(request.getGameId());
        Player player = game.getPlayerByName(playerName);
        Map<String, Object> playerInfo = player.playerDetails();
        return new GetPlayerDetailsResponse(playerInfo);
    }

    @Operation("join-game")
    public ResponseWithHiddenStatus joinGame(JoinGameRequest request) {
        LOGGER.log(Level.INFO, "In request handler of: join-game");

        String gameId = request.getGameId();
        String playerName = request.getPlayerName();

        Game game = service.getGameById(gameId);
        Player player = new Player(gameId, playerName);
        game.addPlayer(player);

        String playerToken = tokenManager.createToken(player);
        return new JoinGameResponse(gameId, playerName, playerToken);
    }

    @Operation("leave-game")
    public ResponseWithHiddenStatus leaveGame(LeaveGameRequest request) {
        LOGGER.log(Level.INFO, "In request handler of: leave-game");
        String gameId = request.getGameId();
        String playerName = request.getPlayerName();

        Game game = service.getGameById(gameId);
        Player user = new Player(gameId, playerName);

        game.removePlayer(user);

        return new LeaveGameResponse(gameId, playerName);
    }

    @Operation("move-player")
    public ResponseWithHiddenStatus movePlayer(MovePlayerRequest request) {
        LOGGER.log(Level.INFO, "In request handler of: move-player");
        String gameId = request.getGameId();
        Game game = service.getGameById(gameId);

        String playerName = request.getPlayerName();
        Player player = game.getPlayerByName(playerName);

        Location destination = request.getDestination();
        game.getMaze().movePlayer(player, destination);
        Location currentLocation = player.getLocation();

        game.giveTurnToPlayer(game.getNextPlayer());

        return new MovePlayerResponse(playerName, currentLocation, player.getTreasures(), player.getState(), player.getObjective());
    }


}
