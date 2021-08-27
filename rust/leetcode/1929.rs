impl Solution {
    pub fn get_concatenation(nums: Vec<i32>) -> Vec<i32> {
        let n: usize = nums.len();
        let mut res = vec![0; n * 2];
        for (i, &x) in nums.iter().enumerate() {
            res[i] = x;
            res[i + n] = x;
        };
        return res
    }
}


impl Solution {
    pub fn get_concatenation(nums: Vec<i32>) -> Vec<i32> {
        let mut copy: Vec<i32> = nums.clone();
        for num in nums {
            copy.push(num)
        }
        return copy;
    }
}
