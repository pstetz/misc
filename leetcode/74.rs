impl Solution {
    pub fn search_matrix(matrix: Vec<Vec<i32>>, target: i32) -> bool {
        let m: usize = matrix.len();
        let n: usize = matrix[0].len();
        let len: usize = m * n;
        let mut range: Vec<usize> = vec![0; 2];
        let mut guess: usize;
        let (mut x, mut y) = (0, 0);
        range[1] = len - 1;
        while range[0] <= range[1] && range[0] < len && range[1] < len {
            guess = range[0] + ((range[1] - range[0]) / 2);
            x = guess % n;
            y = guess / n;
            if matrix[y][x] == target {
                return true;
            } else if matrix[y][x] > target {
                range[1] = guess - 1;
            } else {
                range[0] = guess + 1;
            }
        }
        return false;
    }
}
