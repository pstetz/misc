impl Solution {
    pub fn search_range(nums: Vec<i32>, target: i32) -> Vec<i32> {
        let mut range: Vec<i32> = vec![-1; 2];
        if nums.len() == 0 {
            return range;
        }
        let mut pointers: Vec<i32> = vec![0; 2];
        let mut guess: i32;
        pointers[1] = (nums.len() as i32) - 1;
        while pointers[0] <= pointers[1] {
            guess = pointers[0] + ((pointers[1] - pointers[0]) / 2);
            if nums[guess as usize] == target {
                range[0] = guess;
                pointers[1] = guess - 1;
            } else if nums[guess as usize] > target {
                pointers[1] = guess - 1;
            } else {
                pointers[0] = guess + 1;
            }
        }
        pointers[0] = 0;
        pointers[1] = (nums.len() as i32) - 1;
        while pointers[0] <= pointers[1] {
            guess = pointers[0] + ((pointers[1] - pointers[0]) / 2);
            if nums[guess as usize] == target {
                range[1] = (guess as i32);
                pointers[0] = guess + 1;
            } else if nums[guess as usize] > target {
                pointers[1] = guess - 1;
            } else {
                pointers[0] = guess + 1;
            }
        }
        range
    }
}
