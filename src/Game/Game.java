package Game;

import Game.Board.BattleshipBoard;
import Game.Ship.*;
import Game.Ship.Exception.InvalidAmountOfPlayersException;

import java.util.Scanner;

/**
 * @author Matthieu Le Boucher
 */
public class Game {
    private final int boardSize = 10;
    public static final int PLAYERS_AMOUNT = 2;

    private boolean gameOver = false;
    private boolean gameStarted = false;

    private Player[] players;
    public int currentPlayerID = 0;
    private static final int SHIP_PER_PLAYER = 5;
    private static Ship[] expectedShips = {new PlaneCarrier(Ship.Orientation.HORIZONTAL),new Cruiser(Ship.Orientation.HORIZONTAL),new CounterTorpedoBoat(Ship.Orientation.HORIZONTAL),new Submarine(Ship.Orientation.HORIZONTAL),new TorpedoBoat(Ship.Orientation.HORIZONTAL),};



    public Game(String... playerNames) throws InvalidAmountOfPlayersException {
        if(playerNames.length != PLAYERS_AMOUNT)
            throw new InvalidAmountOfPlayersException();

        players = new Player[PLAYERS_AMOUNT];
        Scanner sc = new Scanner(System.in);
        // Initialize players list.
        for (int i = 0; i < PLAYERS_AMOUNT; i++) {
            players[i] = new Player(playerNames[i], new BattleshipBoard(boardSize));
        }

        // Game loop setup.

        // Setup turn to allow players to positionate their ships.

        /*for(int i = 0; i < players.length; i++) {                                                   //todo Change when UI available (currently console version)
            System.out.print("\nPlayer " + players[i].getName() + "is now positioning his ships.");
            int shipPlaced = 0;
            while(shipPlaced<SHIP_PER_PLAYER){
                char orientationC;
                int x;
                int y;
                System.out.print("\nYou are positioning a " + expectedShips[shipPlaced].getName() + " (length = "+ expectedShips[shipPlaced].getLength() + ").");
                do{
                    System.out.print("\nChoose the ship orientation ( V for VERTICAL / H for HORIZONTAL ) :");
                    orientationC = sc.next().charAt(0);
                }while((orientationC != 'H')&&(orientationC != 'V'));

                Ship.Orientation orientation = orientationC == 'H' ? Ship.Orientation.HORIZONTAL : Ship.Orientation.VERTICAL;

                if(orientation == Ship.Orientation.VERTICAL){
                    do {
                        System.out.print("Choose x (between 0 and " + boardSize + ") : ");
                        x = sc.nextInt();
                    }while (!((0 <= x)&&(x <= boardSize)));
                    do{
                        System.out.print("Choose y (between 0 and " + (boardSize-expectedShips[shipPlaced].getLength()) + ") : ");
                        y = sc.nextInt();
                    }while (!((0 <= y)&&(y <= (boardSize-expectedShips[shipPlaced].getLength()))));
                }else{
                    do {
                        System.out.print("Choose x (between 0 and " + (boardSize-expectedShips[shipPlaced].getLength()) + ") : ");
                        x = sc.nextInt();
                    }while (!((0 <= x)&&(x <= (boardSize-expectedShips[shipPlaced].getLength()))));
                    do{
                        System.out.print("Choose y (between 0 and " + boardSize + ") : ");
                        y = sc.nextInt();
                    }while (!((0 <= y)&&(y <= boardSize)));
                }
                try {
                    Ship ship;
                    switch (shipPlaced){
                        case 0:
                            ship = new PlaneCarrier(orientation);
                            players[i].getBoard().placeShipAt(ship,x,y);
                            players[i].addShip(ship);
                            ++shipPlaced;
                            break;
                        case 1:
                            ship = new Cruiser(orientation);
                            players[i].getBoard().placeShipAt(ship,x,y);
                            players[i].addShip(ship);
                            ++shipPlaced;
                            break;
                        case 2:
                            ship = new CounterTorpedoBoat(orientation);
                            players[i].getBoard().placeShipAt(ship,x,y);
                            players[i].addShip(ship);
                            ++shipPlaced;
                            break;
                        case 3:
                            ship = new Submarine(orientation);
                            players[i].getBoard().placeShipAt(ship,x,y);
                            players[i].addShip(ship);
                            ++shipPlaced;
                            break;
                        case 4:
                            ship = new TorpedoBoat(orientation);
                            players[i].getBoard().placeShipAt(ship,x,y);
                            players[i].addShip(ship);
                            ++shipPlaced;
                            break;
                    }
                } catch (InvalidMoveException e) {
                    System.out.println("Oops ! Wrong move !");
                }
            }
        }*/

        // Game loop.
        while(isGameStarted() && !gameOver) {
            // Ask for
            // Update current player ID to next player.
            //currentPlayerID = (currentPlayerID + 1) % PLAYERS_AMOUNT;
        }
    }

    public Player getCurrentPlayer() {
        return players[currentPlayerID];
    }

    public void changePlayer(){
        currentPlayerID = (currentPlayerID + 1) % PLAYERS_AMOUNT;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }
}
