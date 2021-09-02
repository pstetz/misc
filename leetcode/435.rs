impl Solution {
    pub fn erase_overlap_intervals(mut intervals: Vec<Vec<i32>>) -> i32 {
        intervals.sort_by(|a, b| a[1].cmp(&b[1]));
        let mut prev_end: i32 = intervals[0][1];
        let mut count: i32 = 0;
        let (mut start, mut end);
        for i in 1..intervals.len() {
            start = intervals[i][0];
            end = intervals[i][1];
            if start < prev_end {
                count += 1;
            } else {
                prev_end = end;
            }
        }
        count
    }
}
