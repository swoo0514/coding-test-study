package org.week1.solve;

import java.io.*;
import java.util.*;
import static java.lang.Math.*;
/*
* 그냥 dfs로 풀면 250000번의 재귀가 발생 -> 시간초과 우려
* 따라서, 이차원 배열을 생성하여 해당 위치에 두었을 때 판다가 먹을 수 있는 최대 칸 개수를 저장
* -> 현재 위치에서 상하좌우를 따졌을 때 배열의 값이 0이면 (방문한 적 없으면) 재귀
* -> 0이 아니면 (방문한 적 있으면) 배열에 들어있는 값 + 1을 현재 위치에 넣음
*
* 이렇게 하면 이미 방문한 곳을 재귀할 필요 없어짐
* */
public class BOJ_1937 {
    static int[][] maps; // 입력받은 값 저장
    static int[][] dp; // 이동가능한 최대 칸 수 저장
    static int[] dy = new int[]{0, 1, 0, -1}; // 오른 아래 왼 위 이동
    static int[] dx = new int[]{1, 0, -1, 0};
    static int ans = 0; // 각각의 최대 칸 수 중 최대값 -> 정답

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        maps = new int[N][N]; // input배열 생성
        dp = new int[N][N]; // dp배열 생성

        for (int i = 0 ; i < N ; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0 ; j < N ; j++) {
                maps[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0 ; i < N ; i++) {
            for (int j = 0 ; j < N ; j++) {
                if (dp[i][j] == 0) { // 방문한 적 없는 경우 dfs 적용
                    dfs(i, j);
                }
            }
        }

        System.out.println(ans);
    }

    private static void dfs(int y, int x) {
        for (int i = 0 ; i < 4 ; i++) { // 현 위치 기준 상하좌우 방문
            int nowY = y + dy[i]; // 앞으로 방문할 위치의 y좌표
            int nowX = x + dx[i]; // 앞으로 방문할 위치의 x좌표
            if (canGo(nowY, nowX, y, x) && dp[nowY][nowX] == 0) { // 방문 가능하고 처음 방문할 때
                dfs(nowY, nowX); // 첫 방문시 재귀
                dp[y][x] = max(dp[y][x], dp[nowY][nowX] + 1);
            } else if (canGo(nowY, nowX, y, x)) {
                // 방문을 해본 경우
                dp[y][x] = max(dp[y][x], dp[nowY][nowX] + 1);
            }
        }
        if (dp[y][x] == 0) dp[y][x] = 1; // 이동 할 수 없는 경우 -> 현재 위치만 방문 가능하므로 1 넣음
        ans = max(ans, dp[y][x]); // ans 갱신
    }

    private static boolean canGo(int y, int x, int preY, int preX) {
        // OutOfIndex 방지, 더 큰 값일 때만 이동 가능
        return y >= 0 && y < maps.length && x >= 0 && x < maps.length && maps[preY][preX] < maps[y][x];
    }
}