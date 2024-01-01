package cards;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Durak {
    private List<Player> players = new ArrayList<>();
    private boolean isFirstRound = true;
    public static List<Card> discardPile = new LinkedList<>();

    public static Deck deck = new Deck(); 
    public static int attacker = 0;
    public static Card coz;
    public static Player loser;

    public Durak(int numPlayers) {
        deck.shuffle();
        coz = deck.getCoz();

        // add players to the game and give them each 6 cards to start
        for (int i = 1; i <= numPlayers; i++) {
            Player player = new Player("Player" + i);
            players.add(player);
            player.takeAll(deck.deals(6));
        }
    }

    private boolean over() {
        return players.size() == 1;
    }

    private Round newRound() {
        // the attacker index is greater than the max index
        if (attacker >= players.size()) {
            attacker -= players.size();
        }

        if (isFirstRound) {
            attacker = getPlayerWithSmallestCoz();
        } 
       
        try {
            return new Round(players, attacker++, isFirstRound); 
        } finally {
            isFirstRound = false;
        }
    }

    private int getPlayerWithSmallestCoz() {
        int result = 0;

        // iterate through all players and find the player with the smallest coz
        for (int i=1; i<players.size(); i++) {
            Card smallestCoz = players.get(i).getSmallestCoz();
            if (smallestCoz != null && smallestCoz.compareTo(players.get(result).getSmallestCoz()) < 0) {
                result = i;
            }
        }

        return result;
    }

    public void play() { 
        while (!over()) {
                newRound().play();                  
        }

        loser = players.get(0);
    }

    public static Deck getDeck() {
        return deck;
    }
    
    public static void main(String[] args) {
        Durak durak = new Durak(3);
        System.out.println("Coz: " + coz);
        durak.play();
        System.out.println("Loser: " + loser);
    }
}
