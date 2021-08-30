impl Solution {
    pub fn longest_valid_parentheses(s: String) -> i32 {
        let mut stack: Vec<i32> = vec![0];
        let mut longest: i32 = 0;
        for c in s.chars() {
            if c == '(' {
                stack.push(0)
            } else if stack.len() > 1 {
                let val: i32 = stack.pop().unwrap();
                let i: usize = stack.len()-1;
                stack[i] += val + 2;
                if stack[i] > longest {
                    longest = stack[i];
                }
            } else {
                stack = vec![0];
            }
        }
        longest
    }
}
