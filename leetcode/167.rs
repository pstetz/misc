impl Solution {
    pub fn two_sum(numbers: Vec<i32>, target: i32) -> Vec<i32> {
        let mut left: usize = 0;
        let mut right: usize = numbers.len() - 1;
        let mut result: Vec<i32> = vec![0; 2];
        while numbers[left] + numbers[right] != target {
            if numbers[left] + numbers[right] > target {
                right -= 1;
            } else if numbers[left] + numbers[right] < target {
                left += 1;
            }
        }
        result[0] = (left as i32) + 1;
        result[1] = (right as i32) + 1;
        result
    }
}

impl Solution {
    pub fn two_sum(numbers: Vec<i32>, target: i32) -> Vec<i32> {
        let mut left: usize = 0;
        let mut right: usize = numbers.len() - 1;
        while numbers[left] + numbers[right] != target {
            if numbers[left] + numbers[right] > target {
                right -= 1;
            } else if numbers[left] + numbers[right] < target {
                left += 1;
            }
        }
        vec![left as i32 + 1, right as i32 + 1]
    }
}


