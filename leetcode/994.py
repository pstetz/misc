def get_orange_info(grid):
    fresh, rotten = set(), set()
    for i, row in enumerate(grid):
        for j, val in enumerate(row):
            if val == 2:
                rotten.add(f"{i},{j}")
            elif val == 1:
                fresh.add(f"{i},{j}")
    return fresh, rotten

def next_iter(fresh, rotten):
    rotting = set()
    for orange in fresh:
        x, y = map(int, orange.split(","))
        for dx, dy in [(1, 0), (-1, 0), (0, 1), (0, -1)]:
            _id = f"{x+dx},{y+dy}"
            if _id in rotten:
                rotting.add(orange)
    return rotting

class Solution:
    def orangesRotting(self, grid: List[List[int]]) -> int:
        time = 0
        fresh, rotten = get_orange_info(grid)
        while True:
            rotting = next_iter(fresh, rotten)
            if len(rotting) == 0:
                if len(fresh) > 0:
                    return -1
                else:
                    return time
            time += 1
            fresh = fresh - rotting
            rotten = rotten.union(rotting)

class Solution:
    def orangesRotting(self, grid: List[List[int]]) -> int:
        FRESH, ROTTEN = 1, 2
        rotten = list()
        m, n = len(grid), len(grid[0])
        num_fresh = 0
        for i, row in enumerate(grid):
            for j, val in enumerate(row):
                if val == ROTTEN:
                    rotten.append((i, j))
                elif val == FRESH:
                    num_fresh += 1

        time = 0
        while rotten and num_fresh != 0:
            time += 1
            for _ in range(len(rotten)):
                x, y = rotten.pop(0)
                for dx, dy in [(1, 0), (-1, 0), (0, 1), (0, -1)]:
                    i, j = x + dx, y + dy
                    if i < 0 or j < 0:
                        continue
                    if i >= m or j >= n:
                        continue
                    if grid[i][j] == FRESH:
                        grid[i][j] = ROTTEN
                        num_fresh -= 1
                        rotten.append((i, j))
        return time if num_fresh == 0 else -1
