impl Solution {
    pub fn min_add_to_make_valid(s: String) -> i32 {
        let mut surplus: i32 = 0;
        let mut needed: i32 = 0;
        for c in s.chars() {
            if c == '(' {
                surplus += 1;
            } else {
                if surplus <= 0 {
                    needed += 1;
                } else {
                    surplus -= 1;
                }
            }
        }
        surplus + needed
    }
}
