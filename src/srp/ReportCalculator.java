package srp;

import java.util.List;

public class ReportCalculator {
    public ReportData calculate(List<Integer> data){
        int sum = calculateSum(data);
        double avg = calculateAverage(data);
        return new ReportData(sum, avg);
    }

    private int calculateSum(List<Integer> data){
        return data.stream().mapToInt(Integer::intValue).sum();
    }

    private double calculateAverage(List<Integer> data) {
        if (data.isEmpty()) return 0;
        return (double) calculateSum(data) / data.size();
    }
}
