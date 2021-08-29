use std::collections::HashMap;

impl Solution {
    pub fn num_identical_pairs(nums: Vec<i32>) -> i32 {
        let mut pairs: i32 = 0;
        let mut cache: HashMap<i32, i32> = HashMap::new();
        for num in nums.iter() {
            let count = cache.get(&num).unwrap_or(&0);
            pairs = pairs + count;
            cache.insert(*num, *count + 1);
        }
        pairs
    }
}
