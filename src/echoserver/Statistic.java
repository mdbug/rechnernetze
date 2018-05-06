package echoserver;

public class Statistic {
    private int shortest;
    private int longest;
    private int count = 0;
    private long aggregatedSize = 0;

    public Statistic() {

    }

    public Statistic(Statistic statistic) {
        this.shortest = statistic.shortest;
        this.longest = statistic.longest;
        this.count = statistic.count;
        this.aggregatedSize = statistic.aggregatedSize;
    }

    public void add(int size) {
        if (count == 0) {
            shortest = longest = size;
        } else {
            shortest = Math.min(shortest, size);
            longest = Math.max(longest, size);
        }
        aggregatedSize += size;
        ++count;
    }

    public void merge(Statistic other) {
        if (this.count == 0) {
            this.shortest = other.shortest;
            this.longest = other.longest;
        } else {
            this.shortest = Math.min(this.shortest, other.shortest);
            this.longest = Math.min(this.longest, other.longest);
        }
        this.aggregatedSize += other.aggregatedSize;
        this.count += other.count;
    }

    public String toString() {
        return "Shortest: " + shortest + "\n"
                + "Longest: " + longest + "\n"
                + "Count: " + count + "\n"
                + "Aggregated Size: " + aggregatedSize;
    }
}
