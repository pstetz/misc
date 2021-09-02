// Number may change
// https://leetcode.com/explore/challenge/card/august-leetcoding-challenge-2021/617/week-5-august-29th-august-31st/3957/
// Range Addition II
impl Solution {
    pub fn max_count(m: i32, n: i32, ops: Vec<Vec<i32>>) -> i32 {
        let mut i_min: i32 = m;
        let mut j_min: i32 = n;
        for op in ops.iter() {
            if op[0] < i_min {
                i_min = op[0];
            }
            if op[1] < j_min {
                j_min = op[1];
            }
        }
        i_min * j_min
    }
}
