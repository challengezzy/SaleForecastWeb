package dmdd.dmddjava.dataaccess.dataobject;

import java.io.Serializable;


/** @author Hibernate CodeGenerator */
public class SysPeriod implements Serializable {
	
	public final static long serialVersionUID = -1190000003;

    /** identifier field */
    private Long id;

    /** persistent field */
    private int compilePeriod;

    /** persistent field */
    private int historyValidPeriod;

    /** persistent field */
    private int forecastRunPeriod;

    /** persistent field */
    private int historyOpenPeriod;

    /** persistent field */
    private int forecastDispPeriod;

    /** persistent field */
    private int periodNumPerYear;

    /** nullable persistent field */
    private String comments;

    /** nullable persistent field */
    private Long version;

    /** full constructor */
    public SysPeriod(int compilePeriod, int historyValidPeriod, int forecastRunPeriod, int historyOpenPeriod, int forecastDispPeriod, int periodNumPerYear, String comments, Long version) {
        this.compilePeriod = compilePeriod;
        this.historyValidPeriod = historyValidPeriod;
        this.forecastRunPeriod = forecastRunPeriod;
        this.historyOpenPeriod = historyOpenPeriod;
        this.forecastDispPeriod = forecastDispPeriod;
        this.periodNumPerYear = periodNumPerYear;
        this.comments = comments;
        this.version = version;
    }

    /** default constructor */
    public SysPeriod() {
    }

    /** minimal constructor */
    public SysPeriod(int compilePeriod, int historyValidPeriod, int forecastRunPeriod, int historyOpenPeriod, int forecastDispPeriod, int periodNumPerYear) {
        this.compilePeriod = compilePeriod;
        this.historyValidPeriod = historyValidPeriod;
        this.forecastRunPeriod = forecastRunPeriod;
        this.historyOpenPeriod = historyOpenPeriod;
        this.forecastDispPeriod = forecastDispPeriod;
        this.periodNumPerYear = periodNumPerYear;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCompilePeriod() {
        return this.compilePeriod;
    }

    public void setCompilePeriod(int compilePeriod) {
        this.compilePeriod = compilePeriod;
    }

    public int getHistoryValidPeriod() {
        return this.historyValidPeriod;
    }

    public void setHistoryValidPeriod(int historyValidPeriod) {
        this.historyValidPeriod = historyValidPeriod;
    }

    public int getForecastRunPeriod() {
        return this.forecastRunPeriod;
    }

    public void setForecastRunPeriod(int forecastRunPeriod) {
        this.forecastRunPeriod = forecastRunPeriod;
    }

    public int getHistoryOpenPeriod() {
        return this.historyOpenPeriod;
    }

    public void setHistoryOpenPeriod(int historyOpenPeriod) {
        this.historyOpenPeriod = historyOpenPeriod;
    }

    public int getForecastDispPeriod() {
        return this.forecastDispPeriod;
    }

    public void setForecastDispPeriod(int forecastDispPeriod) {
        this.forecastDispPeriod = forecastDispPeriod;
    }

    public int getPeriodNumPerYear() {
        return this.periodNumPerYear;
    }

    public void setPeriodNumPerYear(int periodNumPerYear) {
        this.periodNumPerYear = periodNumPerYear;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getVersion() {
        return this.version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String toString() {
        return "" + this.id;
    }

}
