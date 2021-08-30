impl Solution {
    pub fn my_pow(x: f64, n: i32) -> f64 {
		// This dictionary will be the starting point for all other values
        use std::collections::HashMap;
        let mut powers: HashMap<i32, f64> = HashMap::new();
        powers.insert(1, x);
        powers.insert(-1, 1.0 / x);

        let mut x_to_the_n: f64 = 1.0;
        let mut current_n = 0;
        while n - current_n != 0 {
			// Find the biggest power saved in the powers hashmap
			// (that's less than n - current_n)
            let mut next_power: i32 = 0;
            for (power, num) in powers.iter() {
                if n > 0 && power > &next_power && power <= &(n - current_n) {
                    next_power = *power;
                } else if n < 0 && power < &next_power && power >= &(n - current_n){
                    next_power = *power;
                }
            }

			// Multiple x_to_the_n by this value and increase its power
            current_n += next_power;
            x_to_the_n *= powers[&next_power];
            powers.insert(current_n, x_to_the_n);
        }

        x_to_the_n // return x_to_the_n
    }
}
