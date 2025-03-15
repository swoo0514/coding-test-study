package org.week1.solve;

import java.io.*;
import java.util.*;
/*
* 방문 여부도 따질 필요 없는 간단한 문제였다. 주어진 길이만 만족하도록 체크하면 됨
* */

public class BOJ_15651 {
    static int max;
    static int size;
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        max = Integer.parseInt(st.nextToken()); // 들어갈 수 있는 값의 최대
        size = Integer.parseInt(st.nextToken()); // 길이

        dfs(0, new int[size]); // 처음 0개의 수열, 앞으로 수열을 담게 될 배열 생성
        System.out.println(sb);
    }

    private static void dfs(int cnt, int[] arr) {
        if (cnt == size) { // 길이 체크 -> 원하는 길이가 되면 배열을 다 빌더에 넣고 반환
            for (int i = 0 ; i < arr.length ; i++) {
                sb.append(arr[i]).append(" ");
            }
            sb.append("\n");
            return;
        }
        for (int i = 1 ; i <= max ; i++) {
            arr[cnt] = i; // 배열에 값을 넣고
            dfs(cnt + 1, arr); // cnt를 늘려서 재귀
        }
    }
}