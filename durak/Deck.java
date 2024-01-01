package cards;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Deck {
    
    private List<Card> deck;
    private Card coz;

    public Deck() {
        deck = new LinkedList<>();
        for (int i = 1; i < 5; i++) {
            for (int j = 6; j < 15; j++) {
                deck.add(new Card(j, i));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(deck);
        coz = deck.get(size() - 1);
    }

    public int size() {
        return deck.size();
    }

    // deals cards from the deck as long as the deck is not empty
    public List<Card> deals(int numCards) {
        List<Card> playerHand = new LinkedList<>();
        for (int i = 0; i < numCards; i++) {
            playerHand.add(deck.remove(0));
        }
        return playerHand;
    }

    public String toString() {
        String s = "Deck: " + "\n";
        for (Card card: deck) { 
            s += card.toString() ;
        }
    
        return s;
    }

    public Card getCoz() {
        return coz;
    }

    public Card remove(int i) {
        return deck.remove(i);
    }

} 
