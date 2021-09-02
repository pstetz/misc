impl Solution {
    pub fn is_robot_bounded(instructions: String) -> bool {
        let mut loc: Vec<i32> = vec![0; 2];
        let mut dir: Vec<i32> = vec![0; 2];
        let mut tmp: i32;
        dir[0] = 1;
        for _ in 0..4 {
            for c in instructions.chars() {
                if c == 'G' {
                    loc[0] += dir[0];
                    loc[1] += dir[1];
                } else if c == 'R' {
                    tmp = dir[0];
                    dir[0] = dir[1];
                    dir[1] = -tmp;
                } else if c == 'L' {
                    tmp = dir[0];
                    dir[0] = -dir[1];
                    dir[1] = tmp;
                }
            }
        }
        loc[0] == 0 && loc[1] == 0
    }
}
