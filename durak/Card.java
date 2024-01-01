package cards;

public class Card implements Comparable {   
 
    private int value;
    private int suit;

    public static final int JACK = 11;
    public static final int QUEEN = 12;
    public static final int KING = 13;
    public static final int ACE = 14;

    public static final int SPADES = 1;
    public static final int DIAMONDS = 2;
    public static final int HEARTS = 3;
    public static final int CLUBS = 4;

    public Card(int value, int suit) {
        this.value = value;
        this.suit = suit;
    }
  
    @Override
    public String toString() {
        String s = getValueAsString() + " of " + getSuitAsString() + "\n";
        return s;
    }

    // method for printing the value of the card in the toString
    public String getValueAsString() {
        switch(value) {
            case JACK:
                return "Jack";
            case QUEEN:
                return "Queen";
            case KING:
                return "King";
            case ACE:
                return "Ace";
            default:
                return String.valueOf(value);
        }
    }

    // method for printing the suit of the card in the toString
    public String getSuitAsString() {
        switch(suit) {
            case SPADES:
                return "Spades";
            case DIAMONDS:
                return "Diamonds";
            case HEARTS:
                return "Hearts";
            case CLUBS:
                return "Clubs";
            default:
                return "Invalid Suit";
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Card other = (Card) obj;
        if (suit != other.suit)
            return false;
        if (value != other.value)
            return false;
        return true;
    }

    public int getValue() {
        return value;
    }

    public int getSuit() {
        return suit;
    }

    @Override
    public int compareTo(Object object) {
        if (object == null) return -1;

        Card card = (Card) object;
        // a card that has the same suit as the coz is always more valuable than the card that does not
        if (this.getSuit() == Durak.coz.getSuit() && card.getSuit() != Durak.coz.getSuit()) {
            return 1;
        } else if (card.getSuit() == Durak.coz.getSuit() && this.getSuit() != Durak.coz.getSuit()) {
            return -1; 
        } else {
            if (this.getValue() < card.getValue()) {
                return -1;
            } else if (this.getValue() == card.getValue()) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    public void setValue(int value) {
        this.value = value;
    }


    public boolean isCoz() {
        return getSuit() == Durak.getDeck().getCoz().getSuit();
    }
}