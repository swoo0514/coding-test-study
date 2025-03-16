import java.io.*;
import java.util.*;

/*
* 문제를 보고 처음한 생각 : DFS로 풀면 되겠다고 생각하였습니다. 판다의 위치를 찍고, 그 점에서부터 DFS를 수행하여 최댓값을 구하면 되겠다고 생각하였습니다.

* 한 방향으로 쭉 탐색하는 문제이므로, 스택보다는 재귀 방식의 DFS가 더 직관적이라고 판단하였습니다.

* 설계 :
* 1. 특정 위치에서 DFS를 시작합니다.
* 2. 이동할 수 있는 칸(현재 칸보다 대나무가 많은 칸)이 있다면, 해당 칸에 대해 DFS를 수행합니다.
* 3. DFS를 수행할 때, 'dp[x][y] = max(dp[x][y], dfs(nx, ny) + 1);'을 통해 최댓값을 갱신합니다.
* 4. 이동할 수 있는 더 큰 대나무 칸이 없다면 DFS를 종료합니다.
*
* 고민 : 모든 위치에서 DFS를 수행하면, 모든 분기점에서 이동할 수 있는 최댓값을 계속 새로 계산하고, 이전 값을 저장해야 하기 때문에,
* 중복 연산이 발생하여 성능이 저하될 것이라고 생각하였습니다.
* 따라서, 특정 위치에서의 최댓값을 저장할 배열이 필요하다고 판단하였습니다.
*
* DP를 사용하면 한 번 계산한 값은 다시 계산할 필요가 없기 때문에 성능이 개선될 것이고,
* 한 지점에서 움직일 최댓값(경로)은 정해져 있기 때문에 dp를 사용하기로 했습니다.
*
* 생각할 거리:
* 왜 BFS는 이 문제에서 추천되지 않을까?
*
* 1. 이 문제는 "이동 경로의 최댓값"을 구하는 문제다.
* 2. BFS는 여러 방향을 동시에 탐색하지만, 이 문제에서는 한 방향으로 계속 이동해야 한다.
* 3. BFS는 너비 우선 탐색이므로, 모든 노드를 한 번씩 방문하면서 분기별로 탐색을 진행한다.
*    - 즉, 한 경로를 따라 최댓값을 구하기보다는 "모든 경우의 수"를 탐색하므로, 연산량이 많아진다.
* 4. 반면 DFS는 한 경로를 끝까지 탐색한 뒤, 최댓값을 저장하면서 되돌아온다.
*    - 따라서 한 번 계산한 위치는 다시 계산하지 않아도 되므로, 불필요한 연산이 줄어든다.
* 5. DP를 활용하면, DFS가 한 번 탐색한 결과를 저장할 수 있어, 중복 연산을 더 줄일 수 있다.
*
* 결론:
* - BFS는 최단 경로를 찾는 데 적합하지만, 이 문제처럼 "최장 경로"를 찾아야 하는 경우에는 DFS + DP가 훨씬 유리하다.
*/

public class Main{
    public static int[][] bamboos; // 대나무의 개수를 저장할 배열
    public static int[][] dp;
    public static int n;
    public static int[][] directions = {{0,1}, {0,-1},{1,0},{-1,0}}; // 상하좌우의 위치를 계산할 때 이용할 배열

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 숲의 크기
        n = Integer.parseInt(st.nextToken());

        // 대나무 숲 크기 설정
        bamboos = new int[n][n];

        // 대나무 숲 입력받기
        for (int i = 0; i < n; i++){
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++)
                bamboos[i][j] = Integer.parseInt(st.nextToken());

        }

        // 해당 위치에서의 최댓값을 저장할 배열 선언
        dp = new int[n][n];

        // dp 의 모든 값을 기본값(-1)로 선언. (아직 방문하지 않았다는 의미로)
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                dp[i][j] = -1;

        // 모든 지점에서 dfs를 실시하여 최댓값을 저장합니다.
        int maxCnt = 0;
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                maxCnt = Math.max(maxCnt, dfs(i, j)); // 최댓값을 비교하면서, dfs를 실시합니다.
            }
        }

        System.out.println(maxCnt);

    }

    private static int dfs(int x, int y){
        if (dp[x][y] != -1)
            return dp[x][y]; // 이미 최댓값을 한 번 탐색한 경우에는, 저장된 최댓값을 반환합니다.

        // 그렇지 않다면
        dp[x][y] = 1; // 현재 위치에서 대나무를 먹습니다.

        for (int[] dir : directions){ // 상하좌우 중 한 방향을 선택합니다. 이차원 배열이므로 int[] dir로 선언합니다.
            int dx = dir[0];
            int dy = dir[1];

            int nx = x + dx, ny = y + dy; // 앞으로 나아갈 위치를 선정합니다.
            if ((0 <= nx && nx < n) && (0 <= ny && ny < n) && bamboos[nx][ny] > bamboos[x][y]){ // 만일 현재 위치보다 대나무가 많고, 범위 안에 존재한다면,
                dp[x][y] = Math.max(dp[x][y], dfs(nx, ny) + 1);             // 현재 저장된 값과 상하좌우의 한 방향으로 이동했을 경우(+1)를 비교하여, 큰 값을 저장합니다.

                /*
                 * 여기서 들었던 궁금증 : 어차피 현재 위치보다 앞으로 갈 위치(nx, ny)의 대나무가 더 많다면,
                 * 항상 nx,ny의 대나무의 수가 많을텐데, x,y와 비교할 필요가 있을까?
                 *
                 * 해결 :
                 * 1. nx, ny는 여러 방향으로 이동할 수 있기 때문에, 각 경로마다 이동할 수 있는 값이 다르다.
                 * 2. 따라서, 단순히 한 방향으로 이동하는 것이 최적의 값이 아닐 수 있다.
                 * 3. 한 번 이동한 값은 dp[x][y]에 저장되므로, 다른 방향에서도 경로를 비교하며 갱신할 수 있다.
                 *
                 * 결론 :
                 * 단순히 "현재 위치보다 nx, ny가 항상 크다"는 것은 다른 모든 경로를 고려하지 않은 잘못된 가정이다.
                 */
            }
        }
        return dp[x][y]; // 최댓값이 저장된 현재의 위치의 값을 반환합니다.
    }
}
