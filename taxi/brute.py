from math import floor
from collections import Counter
from tqdm import tqdm

def solution(num, upper=1, lower=None):
    N = floor(upper ** (1 / 3))
    cubes = [pow(e, 3) for e in range(1, N + 1)]
    perm = list()
    for i, elem1 in tqdm(enumerate(cubes), total=len(cubes)):
        for j, elem2 in enumerate(cubes):
            if j < i: continue
            if lower and elem1 + elem2 < lower: break
            perm.append(elem1 + elem2)
    ans = Counter(perm)
    ans = {k for k, v in ans.items() if v >= num}
    assert ans, num
    return min(ans)
