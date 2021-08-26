import unittest
from brute import *

class TestHelpers(unittest.TestCase):
    def setUp(self):
        pass

    def test_taxi_1(self):
        self.assertEqual(solution(1, upper=10), 2)

    def test_taxi_2(self):
        self.assertEqual(solution(2, upper=10000), 1729)

    def test_taxi_3(self):
        self.assertEqual(solution(3, upper=107539319), 87539319)

    def test_taxi_4(self):
        # self.assertEqual(solution(4, upper=1e14), 6963472309248)

if __name__ == "__main__":
    unittest.main()
