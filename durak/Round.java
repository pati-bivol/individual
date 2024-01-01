package cards;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Round {
    private boolean isFirstRound;
    private List<Player> players;
    private int attackerIndex;
    private int defenderIndex;
    private List<Card> cardsInPlay = new ArrayList<>(12);


    public Round(List<Player> players, int attackerIndex, boolean isFirstRound) {
        this.players = players;
        this.attackerIndex = attackerIndex;
        this.isFirstRound = isFirstRound;

        defenderIndex = attackerIndex + 1;
        if (defenderIndex >= players.size()) {
            defenderIndex -= players.size();
        }
    }

    // sorts each player's hand by value (least to greatest)
    private void sortHands() {
        for (Player player : players) {
            player.sort();
        }
    }


    private void endRound() {
        for (Player player : players) {
            // each player get cards added to their hand until they have 6
            while (player.countCards() < 6 && Durak.getDeck().size() != 0) {
                player.add(Durak.getDeck().deals(1));
            }
        }

        // if the deck is empty and the players have no cards in their hand they have won
        removeWinners();

        // move all cards in play into the discard pile to prepare for the new round
        if (!cardsInPlay.isEmpty()) {
            Durak.discardPile.addAll(cardsInPlay);
            cardsInPlay.clear();
        }
    }

    private boolean isOver(boolean attacked) {
        if (!attacked) {
            return true;
        }

            // if the defender has been attacked 5 times in the first round a new round must begin
        if (isFirstRound) {
            if (cardsInPlay.size() == 10) {
                return true;
            }
            // if the defender has been attacked 6 times in the first round a new round must begin
        } else {
            if (cardsInPlay.size() == 12) {
                return true;
            }
        }

        // there is only one player left in the game
        if (players.size() == 1) {
            return true;
        }

        return false;
    }

    private void removeWinners() {
        if (Durak.deck.size() != 0) {
            return;
        }

        Iterator<Player> it = players.iterator();
        while (it.hasNext()) {
            Player player = it.next();
            if (player.countCards() == 0) {
                it.remove();
                // for each player removed, take one away from the attacker idx
                // the size has changed
                Durak.attacker--;
            }
        }
    }

    private List<Player> getAttackers() {
        List<Player> result = new LinkedList<>();
        result.add(players.get(attackerIndex));

        // any player that is not the defender is an attacker
        for (int i = 0; i < players.size(); i++) {
            if (i != attackerIndex && i != defenderIndex) {
                result.add(players.get(i));
            }
        }

        return result;
    }

    private Player getDefender() {
        return players.get(defenderIndex);
    }

    public void play() {
        Player defender = getDefender();
        boolean attacked = true;
        sortHands();

        while (!isOver(attacked)) {
            int startingPlaySize = cardsInPlay.size();
            for (Player attacker : getAttackers()) {
                if (attacker.attack(cardsInPlay)) {
                    // the defender cannot defend the attacking card
                    if (!defender.defend(cardsInPlay)) {
                        defender.takeAll(cardsInPlay);
                        endRound();
                        Durak.attacker++;
                        return;
                    }
                }
            }

            // a round has passed and no one has attacked the defender
            if (cardsInPlay.size() == startingPlaySize) {
                attacked = false;
            }
        }
        endRound();
    }
}