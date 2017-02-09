package hr.fer.zemris.java.tecaj_13.model;

/**
 * Created by filipsaina on 14/06/16.
 */
public class PollOptionsEntry {

    private long id;
    private String title;
    private String link;
    private long poolId;
    private long votesCount;
    //TODO another variable


    public long getId() {
        return id;
    }

    public PollOptionsEntry setId(long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PollOptionsEntry setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getLink() {
        return link;
    }

    public PollOptionsEntry setLink(String link) {
        this.link = link;
        return this;
    }

    public long getPoolId() {
        return poolId;
    }

    public PollOptionsEntry setPoolId(long poolId) {
        this.poolId = poolId;
        return this;
    }

    public long getVotesCount() {
        return votesCount;
    }

    public PollOptionsEntry setVotesCount(long votesCount) {
        this.votesCount = votesCount;
        return this;
    }
}
