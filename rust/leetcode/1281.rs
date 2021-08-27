impl Solution {
    pub fn subtract_product_and_sum(n: i32) -> i32 {
        let mut product: i32 = 1;
        let mut sum: i32 = 0;
        for c in n.to_string().chars() {
            let num = c as i32 - '0' as i32;
            product = product * num;
            sum = sum + num;
        }
        product - sum
    }
}
