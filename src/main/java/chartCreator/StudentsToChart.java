package chartCreator;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeriesCollection;

public class StudentsToChart {

    private JFreeChart createPerformanceChart(String studentName, XYSeriesCollection dataset) {
        return ChartFactory.createXYLineChart(
                "Успеваемость: " + studentName,
                "Темы",
                "Баллы",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
    }

    private JFreeChart createAttendanceChart(String studentName, DefaultCategoryDataset dataset) {
        return ChartFactory.createBarChart(
                "Посещаемость: " + studentName,
                "",
                "Количество",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
    }

    public JFreeChart getPerformanceChart(String studentName, XYSeriesCollection dataset) {
        return createPerformanceChart(studentName, dataset);
    }

    public JFreeChart getAttendanceChart(String studentName, DefaultCategoryDataset dataset) {
        return createAttendanceChart(studentName, dataset);
    }
}
