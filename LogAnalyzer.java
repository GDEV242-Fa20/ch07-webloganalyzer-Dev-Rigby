/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version    2016.02.29
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    private int[] dayCounts;
    private int[] monthCounts;
    private int[] yearCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer()
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        dayCounts = new int[31];
        monthCounts = new int[12];
        // Create the reader to obtain the data.
        reader = new LogfileReader();
    }
    public LogAnalyzer(String fileName)
    {
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        dayCounts = new int[31];
        monthCounts = new int[12];
        // Create the reader to obtain the data.
        reader = new LogfileReader(fileName);
        
    }
    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        reader.reset();
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }
    public void analyzeDailyData()
    {
        reader.reset();
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int day = entry.getDay();
            dayCounts[day-1]++;
        }
    }
    public void analyzeMonthlyData()
    {
        reader.reset();
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int month = entry.getMonth();
            monthCounts[month-1]++;
        }
    }
    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        int hour = 0;
        while(hour != hourCounts.length) 
        {
            System.out.println(hour + ": " + hourCounts[hour]);
            hour++;
        }
    }
    public void printDailyCounts()
    {
        System.out.println("Day: Count");
        int day = 0;
        while(day != dayCounts.length) 
        {
            System.out.println((day+1) + ": " + dayCounts[day]);
            day++;
        }
    }
    
    public int numberOfAccesses()
    {
        int total = 0;
        for(int i : hourCounts)
        {
            total += i;
        }
        return total;
    }
    
    public int busiestHour()
    {
        int max = 0;
        int ret = 0;
        for(int i = 0; i < hourCounts.length; i++)
        {
            if(hourCounts[i] > max)
            {
                max = hourCounts[i];
                ret = i;
            }
        }
        return ret;
    }
    public int busiestDay()
    {
        int max = 0;
        int ret = 0;
        for(int i = 0; i < dayCounts.length; i++)
        {
            if(dayCounts[i] > max)
            {
                max = dayCounts[i];
                ret = i + 1;
            }
        }
        return ret;
    }
    public int busiestMonth()
    {
        int max = 0;
        int ret = 0;
        for(int i = 0; i < monthCounts.length; i++)
        {
            if(monthCounts[i] > max)
            {
                max = monthCounts[i];
                ret = i + 1;
            }
        }
        return ret;
    }
    public int busiestTwoHour()
    {
        int max = 0;
        int ret = 0;
        int tempTotal = 0;
        for(int i = 0; i < hourCounts.length; i++)
        {
            if(i < (hourCounts.length - 2))
            {
                tempTotal = hourCounts[i] + hourCounts[i+1] + hourCounts[i+2];
            }
            else if(i < (hourCounts.length - 2))
            {
                tempTotal = hourCounts[i] + hourCounts[i+1] + hourCounts[0];
            }
            else
                tempTotal = hourCounts[i] + hourCounts[0] + hourCounts[1];
            if(tempTotal > max)
            {
                max = tempTotal;
                ret = i;
            }
        }
        return ret;
    }
    public int quietestHour()
    {
        int min = hourCounts[0];
        int ret = 0;
        for(int i = 0; i < hourCounts.length; i++)
        {
            if(hourCounts[i] < min)
            {
                min = hourCounts[i];
                ret = i;
            }
        }
        return ret;
    }
    public int quietestDay()
    {
        int min = dayCounts[0];
        int ret = 0;
        for(int i = 0; i < dayCounts.length; i++)
        {
            if(dayCounts[i] < min)
            {
                min = dayCounts[i];
                ret = i + 1;
            }
        }
        return ret;
    }
    public int quietestMonth()
    {
        int min = monthCounts[0];
        int ret = 0;
        for(int i = 0; i < monthCounts.length; i++)
        {
            if(monthCounts[i] < min)
            {
                min = monthCounts[i];
                ret = i + 1;
            }
        }
        return ret;
    }
    public void totalAccessesPerMonth()
    {
        System.out.println("Month: Count");
        int month = 0;
        while(month != monthCounts.length) 
        {
            System.out.println((month+1) + ": " + monthCounts[month]);
            month++;
        }
    }
    public double averageAccessesPerMonth()
    {
        int total = 0;
        for(int i : monthCounts)
        {
            total += i;
        }
        return (total/12);
    }
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
    
}
