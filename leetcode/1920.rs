impl Solution {
    pub fn build_array(nums: Vec<i32>) -> Vec<i32> {
        let mut copy: Vec<i32> = nums.clone();
        for (i, num) in nums.iter().enumerate() {
            copy[i] = nums[nums[i] as usize];
        }
        return copy;
    }
}
