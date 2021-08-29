impl Solution {
    pub fn check_if_pangram(sentence: String) -> bool {
        use std::collections::HashSet;
        let mut chars: HashSet<char> = HashSet::new();
        for c in sentence.chars() {
            chars.insert(c);
        }
        chars.len() == 26
    }
}
