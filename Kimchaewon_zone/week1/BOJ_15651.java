import java.io.*;
import java.util.Scanner;
/*
* 문제를 보고 처음한 생각 :
* 백트래킹을 잠시 공부하면서 본 거라 비슷하다고 생각하였으나, 구현 방법이 기억나지 않았다.
* 수기로 그래프를 그려보면서, 모든 경우의 수에 대해서 level을 내려가면서 각각 N가지의 경우의 수를 세면 된다고 생각하였다.
* 그런 의미에서 DFS를 이용해서 풀려고 했었고, 각각 dfs가 반복될 때마다 해당하는 경우를 출력 해주면 된다고 생각하였다.
*
* 1 : 현재 레벨의 값 이전의 값을 출력할 수 없었다. print를 해주지만,
* 그 이전의 값을 저장하지 않고 있었기 때문에 이를 저장할 공간이 필요했고,
* visited 배열을 만들었고, 코드의 초안을 완성했다.
* 결과 -> 시간 초과
*
* 2 : visited 배열을 사용하여 모든 레벨에서 탐색한 값을 저장한 후,
* 출력할 때 for 루프를 사용하여 O(M)의 연산을 수행했다.
* 이로 인해 O(N^M * M)의 시간복잡도가 발생하여 시간 초과가 발생하였다.
* 이를 해결하기 위해 StringBuilder를 사용하여 문자열을 한 번에 관리하고 append()와 delete()로 O(1) 연산을 수행하도록 변경했다.
* 결과 -> 시간초과

* 3 : 근본적인 알고리즘 문제라고 생각하여, 지피티와 상의하였으나, 모든 경우의 수를 출력해야 하므로,
* N^M보다 더 줄어들 수는 없는 부분이라고 판단하였다.

* 4 : 그렇다면, 자바에서 입출력 시에 시간이 너무 오래걸리는 것이라 생각하였고,
* 질문 게시판을 참고하여, BufferedWriter를 사용하기로 하였다.
* 모든 입력을 저장해놓고 있다가 한번에 flush하여, 출력을 한번만 실시하도록 하였다.
* 결과 -> 성공

* 자바에서 System.out.println()을 반복하여 출력하면, I/O병목현상이 발생하여 속도가 급격히 저하될 수 있다고 한다.
* 최종 공간복잡도 O(M(-> StringBuilder)) , 시간복잡도 O(N^M)
* 입력받을 때 쓰는 공간은 O(1)으로 친다고 한다.
*/
public class Main {
    public static int M; // 조합의 길이(자릿수라고 생각하면 된다. ex) _ _ _ = 3자리)
    public static int N; // 각 레벨마다 탐색해야 할 노드의 숫자
    public static StringBuilder sb = new StringBuilder(); // 현재 탐색 중인 조합을 저장
    public static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    /**
     * DFS로 모든 조합을 생성하고, BufferedWriter에 출력할 것을 저장하는 함수
     * @param i 현재 선택한 숫자
     * @param level 현재 레벨(깊이)
     * @param sb 현재까지 탐색한 숫자들을 저장하는 StringBuilder
     * @throws IOException
     */
    public static void dfs(int i, int level, StringBuilder sb) throws IOException {
        // 탐색 종료 조건
        if (level == M){                        // M자리까지 원하는 숫자를 모두 선택하였다면,
            bw.write(sb.toString() + "\n"); // 현재 조합을 버퍼에 저장한 후에
            return;                             // 종료한다.
        }

        level += 1; // 한 단계 밑으로 내려간다.
      
        // 현재 레벨에서 탐색 가능한 모든 숫자를 선택한다.
        for (int j = 1; j <= N; j++){
            sb.append(j).append(" "); // 선택한 숫자를 문자열에 추가한다.
            dfs(j, level, sb);        // 그 다음 레벨로 내려가서 탐색을 시작한다.
            sb.delete(sb.length() - 2, sb.length()); // 백 : 탐색이 끝나면, 추가한 숫자를 삭제한다.
                                                     // (ex. 111를 탐색하고 나서, 마지막 1을 제거하여 11로 만든 후에 2를 append하여 112를 저장한다.)
        }
    }


    public static void main(String[] args) throws IOException { // BufferedWriter의 경우 IOException을 호출해 주어야 한다.
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();

        dfs(1, 0, sb); // 제일 위의 단계에서부터 시작한다.
        bw.flush();           // 버퍼에 저장된 숫자들의 조합을 한번에 출력한다.
        bw.close();           // 메모리 해제.
    }
}
