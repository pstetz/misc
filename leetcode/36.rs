impl Solution {
    pub fn is_valid_sudoku(board: Vec<Vec<char>>) -> bool {
        use std::collections::HashSet;
        let mut seen: HashSet<char> = HashSet::new();
        let mut c: char;

        // Horizontal check
        for i in 0..9 {
            seen.clear();
            for j in 0..9 {
                c = board[i][j];
                if seen.contains(&c) {
                    return false;
                }
                if c != '.' {
                    seen.insert(c);
                }
            }
        }

        // Horizontal check
        for i in 0..9 {
            seen.clear();
            for j in 0..9 {
                c = board[j][i];
                if seen.contains(&c) {
                    return false;
                }
                if c != '.' {
                    seen.insert(c);
                }
            }
        }

        // Cell check
        for i in 0..9 {
            seen.clear();
            for j in 0..9 {
                c = board[3 * (i / 3) + (j / 3)][3 * (i % 3) + (j % 3)];
                if seen.contains(&c) {
                    return false;
                }
                if c != '.' {
                    seen.insert(c);
                }
            }
        }
        true
    }
}
