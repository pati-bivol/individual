package cards;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Player {
    private String name;
    private List<Card> hand;

    public Player(String name) {
        this.name = name;
        hand = new LinkedList<>();
    }

    // player takes all cards in play
    public void takeAll(List<Card> cards) {
        hand.addAll(cards);
        cards.clear();
    }

    public int countCards() {
        return hand.size();
    }

    public void sort() {
        Collections.sort(hand);
    }

    public Card get(int i) {
        return hand.get(i);
    }

    @Override
    public String toString() {
        return name;
    }

    public void add(List<Card> list) {
        hand.addAll(list);
    }

    public void set(int i, Card card) {
        hand.remove(card);
        hand.add(i, card);
    }

    public boolean attack(List<Card> cardsInPlay) {
        // if there are no cards in play the player may choose any card in their hand to put down
        if (cardsInPlay.isEmpty()) {
            cardsInPlay.add(hand.remove(0));
            return true;
        }

        // if there are cards in play the player may put down a card holding the same value as one of the cards in play
        for (Card cardInPlay : cardsInPlay) {
            Card attackerCard = getCardWithValue(cardInPlay.getValue());
            if (attackerCard != null) {
                cardsInPlay.add(attackerCard);
                return true;
            }
        }

        return false;
    }

    public boolean defend(List<Card> cardsInPlay) {
        // the last card that was put down is the card that needs to be defended
        Card attack = cardsInPlay.get(cardsInPlay.size() - 1);

        Card defend = getCardGreaterThan(attack);
        if (defend != null) {
            cardsInPlay.add(defend);
            return true;
        }

        return false;
    }

    public Card remove(int i) {
        return hand.remove(i);
    }

    public void add(int i, Card card) {
        hand.add(i, card);
    }

    // method returning the smallest value coz in the player's hand
    public Card getSmallestCoz() {
        Card result = null;

        for (Card card : hand) {
            if (card.isCoz() && card.compareTo(result) < 0) {
                result = card;
            }
        }

        return result;
    }

    private Card getCardWithValue(int value) {
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getValue() == value) {
                return hand.remove(i);
            }
        }
        return null;
    }

    private Card getCardGreaterThan(Card card) {
        for (int i = 0; i < hand.size(); i++) {
            if (card.getSuit() == hand.get(i).getSuit()) {
                if (card.getValue() < hand.get(i).getValue()) {
                    return hand.remove(i);
                }
            }
        }

        if (!card.isCoz()) {
            try {
                return getSmallestCoz();
            } finally {
                hand.remove(getSmallestCoz());
            }
        }

        return null;
    }
}
