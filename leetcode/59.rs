impl Solution {
    pub fn generate_matrix(n: i32) -> Vec<Vec<i32>> {
        let mut dir_x: i32 = 1;
        let mut dir_y: i32 = 0;
        let (mut x, mut y) = (0, 0);
        let mut matrix = vec![vec![0; n as usize]; n as usize];
        for i in 1..(n * n + 1) {
            matrix[y as usize][x as usize] = i;
            if dir_x.abs() == 1 {
                if x + dir_x < 0 || x + dir_x >= n || matrix[y as usize][(x + dir_x) as usize] != 0 {
                    dir_y = dir_x;
                    dir_x = 0;
                }
            } else if dir_y.abs() == 1 {
                if y + dir_y < 0 || y + dir_y >= n || matrix[(y + dir_y) as usize][x as usize] != 0 {
                    dir_x = -1 * dir_y;
                    dir_y = 0;
                }
            }
            x = x + dir_x;
            y = y + dir_y;
        }
        matrix
    }
}
