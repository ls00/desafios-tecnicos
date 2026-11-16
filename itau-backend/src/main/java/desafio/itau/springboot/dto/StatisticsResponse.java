package desafio.itau.springboot.dto;

import java.util.DoubleSummaryStatistics;

public class StatisticsResponse {
    private long count;
    private double sum;
    private double avg;
    private double max;
    private double min;

    public StatisticsResponse(DoubleSummaryStatistics statistics) {
        this.count = statistics.getCount();
        this.sum = statistics.getSum();
        this.avg = statistics.getAverage();
        this.max = statistics.getCount() == 0 ? 0 : statistics.getMax();
        this.min = statistics.getCount() == 0 ? 0 : statistics.getMin();
    }

    public long getCount() {
        return count;
    }
    public double getSum() {
        return sum;
    }
    public double getAvg() {
        return avg;
    }
    public double getMax() {
        return max;
    }
    public double getMin() {
        return min;
    }
}
