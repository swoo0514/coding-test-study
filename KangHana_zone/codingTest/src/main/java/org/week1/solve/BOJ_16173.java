package KangHana_zone.codingTest.src.main.java.org.week1.solve;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_16173 {
    static int[][] map;
    static int N;
    static int[] dy = new int[]{0, 1}; // 오른, 아래
    static int[] dx = new int[]{1, 0};
    final static int RIGHT = 0;
    final static int DOWN = 1;
    static boolean ans;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        for (int i = 0 ; i < N ; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0 ; j < N ; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        ans = false;
        dfs(0, 0, map[0][0], -1);
        System.out.println(ans ? "HaruHaru" : "Hing");
    }

    private static void dfs(int y, int x, int rest, int dir) {
        if (rest < 0) return;
        // 도착여부 확인
        if (y == N - 1 && x == N - 1 && rest == 0) {
            ans = true;
            return;
        } else if (y == N - 1 && x == N - 1) return;

        for (int i = 0 ; i < 2 ; i++) {
            if (canGo(y + dy[i], x + dx[i]) ) {
                if (rest == 0) { // 모든 칸을 갔으니 새로 읽어줌
                    int value = map[y][x];
                    dfs(y + dy[i], x + dx[i], value - 1, i);
                } else {
                    if (i == dir || dir == -1) {
                        dfs(y + dy[i], x + dx[i], rest - 1, i);
                    }
                }
            }
        }
    }

    private static boolean canGo(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }
}