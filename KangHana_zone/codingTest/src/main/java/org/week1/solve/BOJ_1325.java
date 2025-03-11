package KangHana_zone.codingTest.src.main.java.org.week1.solve;
import java.io.*;
import java.util.*;

/*
* dfs보단 bfs가 성능이 잘 나올 거 같지만... dfs를 공부하는 거기 때문에 최대한 맞췄습니다
* dfs에 dp를 도입 (한개한개 재귀로 들어가는 것보다 dp배열에 신뢰하는 노드 개수를 저장해서 그걸 재사용)
* 하는 걸 생각했으나, 중복으로 노드가 세어지는 경우를 어떻게 피할 수 있을지에 관한 고민이 사라지지 않아
* 그냥 dfs만 사용했습니다.
*
* */
public class BOJ_1325 {

    // 신뢰관계인 노드 넣음 (ex| idx 1인 노드가 3,4를 신뢰하는 경우 lst.get(1)에 add(3), add(4) 적용
    static List<List<Integer>> lst = new ArrayList<>();
    static boolean[] isVisit; // 재방문 방지 (시간초과 방지)
    static int max = 0; // 가장 많이 해결할 수 있는 경우 저장
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        for (int i = 0 ; i <= N ; i++) {
            lst.add(new ArrayList<>()); // lst 생성
        }

        for (int i = 0 ; i < M ; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            lst.get(b).add(a); // b가 해킹당할 경우 a도 해킹 가능
        }
        int[] count = new int[N + 1]; // 한번에 해킹 가능한 컴퓨터 수 저장
        for (int i = 1 ; i <= N ; i++) {
            if (lst.get(i).isEmpty()) continue; // 신뢰하는 pc가 없는 경우 dfs 수행할 필요 없음
            isVisit = new boolean[N + 1]; // 방문배열 초기화
            dfs(i);
            int cnt = 0;
            for (boolean visit : isVisit) {
                if (visit) cnt++; // 한번에 해킹 가능한 컴퓨터 수 뽑기 -> 방문한 컴퓨터 개수 세면 됨
            }
            max = Math.max(cnt, max); // 최대인 거 갱신
            count[i] = cnt;
        }

        StringBuilder sb = new StringBuilder(); // 출력 효율을 위한 빌더
        for (int i = 1 ; i <= N ; i++) {
            if (count[i] == max) sb.append(i).append(" ");
        }
        System.out.println(sb);
    }

    private static void dfs(int now) {
        isVisit[now] = true; // 방문했으면 true
        List<Integer> canHacks = lst.get(now);
        if (canHacks.isEmpty()) {
            return;
        }
        for (int ob : canHacks) {
            if (!isVisit[ob]) { // 방문한 적 없으면 방문
                dfs(ob);
            }
        }
    }
}