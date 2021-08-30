class Solution:
    def combinationSum(self, candidates: List[int], target: int) -> List[List[int]]:
        nums = set(candidates)
        to_check = [([], target)]
        sums = list()
        while to_check:
            for _ in range(len(to_check)):
                lst, t = to_check.pop(0)
                for num in nums:
                    if lst and lst[-1] > num:
                        continue
                    elif t - num == 0:
                        sums.append(lst + [num])
                    elif t - num < 0:
                        continue
                    else:
                        to_check.append((lst + [num], t - num))
        return sums

"""
Always read the prompt carefully! I tried to solve without replacement at first

def summed(lst, inds):
    return sum([lst[i] for i in inds])

def get_combos(candidates, inds, target):
    if summed(candidates, inds) > target:
        return [], True
    combos = list()
    while summed(candidates, inds) < target:
        if True:
            pass
    return combos, False

class Solution:
    def combinationSum(self, candidates: List[int], target: int) -> List[List[int]]:
        candidates = list(sorted(candidates))
        N = len(candidates)
        sums = list()
        for i in range(N):
            inds = list(range(0,i))
            combos, overflow = get_combos(candidates, inds, target)
            if overflow:
                break
            sums.extend(combos)
        return sums
"""
