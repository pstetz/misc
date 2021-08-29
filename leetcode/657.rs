impl Solution {
    pub fn judge_circle(moves: String) -> bool {
        let mut x_count: i32 = 0;
        let mut y_count: i32 = 0;
        for c in moves.chars() {
            if c == 'R' {
                x_count += 1;
            } else if c == 'L' {
                x_count -= 1;
            } else if c == 'U' {
                y_count += 1;
            } else if c == 'D' {
                y_count -= 1;
            }
        }
        x_count == 0 && y_count == 0
    }
}
