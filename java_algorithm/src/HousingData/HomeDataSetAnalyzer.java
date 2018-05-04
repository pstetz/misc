package HousingData;

import java.util.ArrayDeque;
import java.util.HashMap;

/**
 * This can be abstracted somewhat and then attacked to increase efficiency.
 * The tradeoff is it uses a bit more memory...
 *
 * Instead of working with housing prices, I work with the relationships between them.  To save time, I go through the
 * housing price data only once and create a new array ('priceIncreasingTracker') based on if the next point is
 * bigger (+1), smaller (-1) or the same (0).
 *      - This is counting relationships so we should convert to values at some point
 *      - ( e.g. [VALUES] = [RELATIONSHIPS] + 1 )
 *
 * Once the array is created, I go through the first window of size K and create a 'Trends' object.
 * Trends keeps track of how long prices are increasing, decreasing, or staying the same.
 *
 * The reason behind creating a trends object is an interval of size X has a predictable number of subintervals inside.
 * So I setup a hash that keeps track how many subintervals are in an interval of length X (max interval size is K)
 * They are given by the iterative formula... Subintervals( X ) = Subintervals( X - 1 ) + X - 1
 *      - Drawing it out helped me find the pattern.
 *      - I included the somewhat blurry drawing (name: 'subinterval pattern.jpg') in the folder I sent
 *
 * The trends object is modified after each shift of the window.  The window is the same when it shifts over one except
 * for the very start and the very end.  I discovered there's a surprising amount of code that's needed when shifting
 * the window
 */

class HomeDataSetAnalyzer {
    long[] output;
    private int K, N;
    private int[] homePrices, priceIncreasingTracker;
    private Trend trend = new Trend(); // custom class
    private HashMap<Integer, Long> lengthToNumSubrangeHash = new HashMap<>();

    HomeDataSetAnalyzer(String[][] homeData) {
        N = Integer.valueOf(homeData[0][0]);
        K = Integer.valueOf(homeData[0][1]);
        homePrices = stringLstToIntLst(homeData[1]);
        setupSubRanges();
        setupIncreasingArray();
        output = new long[N - K + 1];

        // It's trivial if either N or K equals 1
        if( !(K == 1 || N == 1) ) {
            setupTrends();
            startCountingSubranges();
        }
    }

    /**
     * This only needs to be initialized once and then it's modified with each window change.
     *
     * start and end are the first and last trends (How much it's increasing/decreasing at the beginning/end).
     * endOfStart and startOfEnd keep track of where start and end are placed on the priceIncreasingTracker array.
     *
     * sum is the cached values of all the subranges within the interval.
     */
    private class Trend {
        long start, end;
        long sum;
        int endOfStart, startOfEnd;

        private Trend() {
            sum = 0;
        }
    }

    private void startCountingSubranges() {
        // Loops through each possible window of K in the dataset
        for(int i = 1; i < N - K + 1; i++) {
            // The start and end values will change so I subtract them now so the new values can be added later
            trend.sum -= getTrendValue(trend.start);
            trend.sum -= getTrendValue(trend.end);

            trend.endOfStart = i;
            trend.startOfEnd = i + K - 2;

            // Adjusts the start and end values
            adjustFirstTrend(i);
            adjustLastTrend(i);

            output[i] = trend.sum;
        }
    }

    private void adjustFirstTrend(int index) {
        int first = priceIncreasingTracker[trend.endOfStart];
        int firstPolarity = getPolarity(first);

        // Case 1: the starting trend continues.  The window is moved to the right 1, so the start is 1 shorter
        if(Math.abs(trend.start) > 1 && firstPolarity == getPolarity(trend.start)) {
            trend.start -= firstPolarity;
            trend.sum += getTrendValue(trend.start);
            return;
        }

        // Case 2: the starting trend ends.  The new starting trend is found and set
        trend.start = first;
        while(trend.endOfStart + 1 < index + K - 1) {
            if(priceIncreasingTracker[trend.endOfStart + 1] != first) {
                break;
            }
            trend.start += firstPolarity;
            trend.endOfStart++;
        }
    }

    private void adjustLastTrend(int index) {
        int last = priceIncreasingTracker[trend.startOfEnd];
        int lastPolarity = getPolarity(last);

        // Case 1: The ending trend already exists (it's the same as the starting trend.  Relevant for small K).
        // This edge case is the same as the edge case below, except this only happens when adjustFirstTrend has case 2
        if (trend.startOfEnd == trend.endOfStart) {
            trend.sum += getTrendValue(trend.start);
            trend.end = 0;
            return;
        }

        // Case 2: The ending trend continues.  The window is moved to the right 1, so the start is 1 longer
        if(lastPolarity == getPolarity(trend.end)) {
            trend.end += lastPolarity;
            trend.sum += getTrendValue(trend.end);
            return;
        }

        // Case 3: The ending trend ends.  Adds the previous trend to the sum.  Finds the new trend and also adds that
        // to the sum
        trend.sum += getTrendValue(trend.end);
        trend.end = last;
        while(trend.startOfEnd >= index) {
            if(priceIncreasingTracker[trend.startOfEnd - 1] != last) {
                break;
            }
            trend.end += lastPolarity;
            trend.startOfEnd--;

            // Edge Case: This accounts for the case when the starting trend and the ending trend are the same.
            // This edge case is the same as case 1, except this only happens when adjustFirstTrend has case 1
            if (trend.startOfEnd == trend.endOfStart) {
                adjustIfStartTouchesEnd(lastPolarity);
                return;
            }
        }
        trend.sum += getTrendValue(trend.end);
    }

    // The old starting trend is removed from the sum.  The trend is then adjusted to the right one and added back in.
    private void adjustIfStartTouchesEnd(int value) {
        trend.sum -= getTrendValue(trend.start);
        trend.start += value;
        trend.sum += getTrendValue(trend.start);
        trend.end = 0;
    }


    /**
     * *******************************************
     * ********      Setup functions      ********
     * *******************************************
     */


    // Sets up the Trend class defined at the very top
    private void setupTrends() {
        ArrayDeque<Integer> trendSetter = prepTrend();

        trend.start = trendSetter.getFirst();
        for(Integer val: trendSetter) {
            trend.sum += getTrendValue(val);
        }
        trend.end = (trendSetter.size() == 1 ? 0 : trendSetter.getLast() );
        output[0] = trend.sum;
    }

    /**
     * Clumps the window into increasing/decreasing/stagnant windows
     *      -> [1, 1, 1, 1, 1]        -->   [5]
     *      -> [-1, -1, -1, -1, -1]   -->   [-5]
     *      -> [0, 0, 0, 0, 0]        -->   [0]
     *      -> [1, 1, -1, 0, 0]      -->   [2, -1, 0]
     */
    private ArrayDeque<Integer> prepTrend() {
        ArrayDeque<Integer> ret =  new ArrayDeque<>();
        int prev = priceIncreasingTracker[0];
        ret.addLast(prev);
        for(int i = 1; i < K - 1; i++) {
            int curr = priceIncreasingTracker[i];
            if(prev == curr) {
                ret.addLast(ret.removeLast() + curr );
            } else {
                ret.addLast( curr );
                prev = curr;
            }
        }
        return ret;
    }

    /**
     * Stores how many subintervals are inside an interval
     */
    private void setupSubRanges() {
        lengthToNumSubrangeHash.put(0, (long) 0);
        for(int i = 1; i < K + 2; i++) {
            long subranges = lengthToNumSubrangeHash.get(i - 1) + i - 1;
            lengthToNumSubrangeHash.put(i, subranges);
        }
    }

    /**
     * Tracks whether the prices are increasing from value to value
     * I like to think of it as ['INCREASING', 'INCREASING', 'SAME', 'INCREASING', 'DECREASING', ...], but
     * it's actually represented as [1, 1, 0, -1, ...]
     */
    private void setupIncreasingArray() {
        int[] ret = new int[N - 1];
        for(int i = 0; i < N - 1; i++) {
            ret[i] = getPolarity( homePrices[i + 1] - homePrices[i] );
        }
        priceIncreasingTracker = ret;
    }


    /**
     * Helper functions
     */


    // Determines how many subintervals are in an interval.  Applies the appropriate polarity as well.
    private long getTrendValue(int val) {
        int polarity = getPolarity(val);
        long numberOfSubranges = lengthToNumSubrangeHash.get(Math.abs(val) + 1);
        return polarity * numberOfSubranges;
    }

    private long getTrendValue(long val) {
        return getTrendValue( (int) val);
    }

    // Returns 1, -1, or 0 depending on the input being positive, negative, or neither
    private int getPolarity(int value) {
        if(value == 0) {
            return 0;
        } else {
            return (value > 0) ? 1 : -1;
        }
    }

    private int getPolarity(long value) { return getPolarity( (int) value ); }

    // Converts a list of strings into a list of numbers.  Steps like this aren't needed in Python or Javascript.
    private int[] stringLstToIntLst(String[] pricesString) {
        int[] ret = new int[N];
        for(int i = 0; i < N; i++) {
            ret[i] = Integer.valueOf(pricesString[i]);
        }
        return ret;
    }
}