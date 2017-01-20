package com.zowdow.direct_api.network.models.unified.suggestions;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Suggestion implements SuggestionData {
    private int    mId;
    private String mRid;
    private String mSuggestion;
    private String mQueryFragment;
    private int    mSuggRank;
    private List<Card> mCards = new ArrayList<>();
    private boolean mTracked;
    private String  mCarouselType;
    private String  mCardFormat;

    /**
     * Get suggestions id
     *
     * @return suggestions id
     */
    public int getId() {
        return mId;
    }

    /**
     * Set suggestions id
     *
     * @param id suggestions id
     */
    public void setId(int id) {
        mId = id;
    }

    /**
     * Get request id
     *
     * @return request id
     */
    public String getRid() {
        return mRid;
    }

    /**
     * Set request id
     *
     * @param rid request id
     */
    public void setRid(String rid) {
        mRid = rid;
    }

    /**
     * Get suggestion text
     *
     * @return suggestion text
     */
    public String getSuggestion() {
        return mSuggestion;
    }

    /**
     * Set suggestion text
     *
     * @param suggestion suggestion text
     */
    public void setSuggestion(String suggestion) {
        mSuggestion = suggestion;
    }

    /**
     * Get query fragment
     *
     * @return query fragment
     */
    public String getQueryFragment() {
        return mQueryFragment;
    }

    /**
     * Set query fragment
     *
     * @param queryFragment query fragment
     */
    public void setQueryFragment(String queryFragment) {
        mQueryFragment = queryFragment;
    }

    /**
     * Get suggestion rank
     *
     * @return suggestion rank
     */
    public int getSuggRank() {
        return mSuggRank;
    }

    /**
     * Set suggestions rank
     *
     * @param suggRank suggestion rank
     */
    public void setSuggRank(int suggRank) {
        mSuggRank = suggRank;
    }

    /**
     * Get a list of cards
     *
     * @return a list of cards
     */
    public List<Card> getCards() {
        return mCards;
    }

    /**
     * Set a list of cards
     *
     * @param cards a list of cards
     */
    public void setCards(List<Card> cards) {
        mCards = cards;
    }

    /**
     * Get carousel type
     *
     * @return carousel type
     */
    public String getCarouselType() {
        return mCarouselType;
    }

    /**
     * Set carousel type
     *
     * @param carouselType carousel type
     */
    public void setCarouselType(String carouselType) {
        this.mCarouselType = carouselType;
    }

    public void setCardFormat(String cardFormat) {
        this.mCardFormat = cardFormat;
    }

    public String getCardFormat() {
        return mCardFormat;
    }

    /**
     * Is suggestions text equals to other's suggestion text
     *
     * @param other Suggestion to compare with
     * @return true if suggestion is equals to other's suggestion
     */
    public boolean equalsTo(Suggestion other) {
        return other != null && mSuggestion.equals(other.getSuggestion());
    }

    @Override
    public String toString() {
        return mSuggestion;
    }

    /**
     * Returns has the suggestion been tracked. If tracked was false, it'll become true
     *
     * @return true is the suggestions has been tracked
     */
    public boolean hasBeenTracked() {
        boolean result = mTracked;
        mTracked = true;
        return result;
    }

    /**
     * Set values from another suggestion
     *
     * @param other     Suggestion to copy values from
     * @param cardLimit Max card count
     */
    public void update(final Suggestion other, final int cardLimit) {
        setId(other.getId());
        setRid(other.getRid());
        setSuggRank(other.getSuggRank());
        setQueryFragment(other.getQueryFragment());
        setSuggestion(other.getSuggestion());

        for (int i = 0; i < getCards().size(); i++) {
            Iterator<Card> it = other.getCards().iterator();
            while (it.hasNext()) {
                Card newCard = it.next();
                if (getCards().get(i).getId().equals(newCard.getId())) {
                    if (!getCards().get(i).getRid().equals(newCard.getRid())) {
                        getCards().get(i).update(newCard);
                    }
                    it.remove();
                    break;
                }
            }
        }
        if (cardLimit == 0) {
            getCards().clear();
            return;
        }
        if (getCards().size() + other.getCards().size() <= cardLimit) {
            getCards().addAll(other.getCards());
        } else {
            for (int i = 0; i < cardLimit - getCards().size(); i++) {
                getCards().add(other.getCards().get(i));
            }
        }
    }
}