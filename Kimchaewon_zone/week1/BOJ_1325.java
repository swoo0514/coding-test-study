import java.util.*;
import java.io.*;

/*
* 꽤나 오래 걸렸던 문제였습니다. 결국 지피티도 해결하지 못하여 백준 질문 게시판에 글을 남겼고, 유의미한 정보를 얻을 수 있었습니다.
* DFS로 무조건 풀려고 했는데, 끝까지 시간 초과나서 BFS로 풀었습니다.
* 그리고 자바 11버전에서는 시간 초과가 나는데, 자바 8버전에서는 통과된다. 이유를 아직 못찾았습니다.

* 문제 보고 처음 든 생각 : 한 방향으로만 이동하는 그래프를 DFS로 풀면 되겠다고 생각했습니다.

* 처음에는 DFS를 외부의 함수로 만들어 재귀로 호출하도록 하였습니다.
* 그러나 시간초과가 계속되었고, 재귀를 스택으로 바꾸어 풀면 해결될 것이라 생각하여 바꾸었습니다.

* DFS에서 더 탐색해야 할 노드가 있는지를 저장할 배열을 ArrayList로 선언하였습니다.
* 그러나 ArrayList에서 마지막 값을 pop할 때 O(N)의 시간이 걸리므로, Stack으로 바꾸어 O(1)의 시간복잡도로 개선하였습니다.

* 시간 초과로 인하여 DFS를 BFS로 바꾸기 위해 Stack을 Queue로 바꾸었습니다.
*/
public class Main {
    public static ArrayList<Integer>[] arr;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int max = 0;

        arr = new ArrayList[n + 1];  // 그래프를 저장할 이차원 동적 배열
        int[] arr1 = new int[n + 1]; // 해당 노드에서 출발할 때 해킹할 수 있는 컴퓨터의 수를 저장할 배열


        // 배열 생성
        for (int i = 1; i <= n; i++) {
            arr[i] = new ArrayList<>();
        }

        // 배열 입력
        for (int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            arr[b].add(a);
        }

        // BFS 실시, 최댓값을 구하기 위해 각각의 노드에서 출발하는 경우를 모두 셉니다.
        for (int i = 1; i <= n; i++){
            int cnt = 1;                                // 현재 노드도 해킹당한 것에 포함되므로 카운트해줍니다.
            boolean[]  visited = new boolean[n + 1];    // 이미 방문한 노드인지를 확인하여 그래프가 Cycle을 가질 때 무한 반복되지 않도록 합니다.
            visited[i] = true;                          // i번째 노드는 시작점이므로 방문하였다고 표시합니다.


            Queue<Integer> arr2= new LinkedList<>();    // 앞으로 더 방문해야할 노드를 저장할 Queue를 선언합니다. (FIFO)
            arr2.add(i);
//            Stack<Integer> arr2 = new Stack<>();
//            arr2.push(i);

            while (!arr2.isEmpty()){
//                int node = arr2.pop(); // 수정사항 1. Arraylist-> Stack으로 변경. O(N) -> O(1)
                int node = arr2.poll(); //  수정사항 2. Stack -> Queue로 변경.
                                        // Queue에 제일 먼저 저장된 노드부터 탐색합니다.

                /*
                * 수정사항1. Integer integer : arr[node]에서 int integer : arr[node]로 변경하였습니다.
                * Integer는 객체 타입으로써, 객체의 여러 멤버들을 가지고 있어 메모리 사용량이 많습니다. 또한, .equals()를 사용해야 합니다.
                * Integer를 int로 바꾸어, 자동 언박싱이 적용되게 하면, 숫자만을 이용하면 되므로 연산 속도를 올릴 수 있습니다.
                * 언박싱 하는 습관을 길러야겠다는 생각이 들었습니다.
                *
                * 동작원리 :
                * 현재 노드에 연결된 다른 노드를 방문하지 않았다면, Queue에 저장합니다.
                * 저장된 노드는 FIFO의 원칙에 따라서 같은 레벨의 노드들을 탐색할 때 Queue에서 꺼내집니다.
                */
                for (int integer : arr[node]){
                    if (!visited[integer]) {
                        visited[integer] = true;
                        cnt += 1;
//                        arr2.push(integer);
                        arr2.add(integer); //수정사항 Stack -> Queue로 변경.
                    }
                }


                /*
                * 기존의 코드는 arr1이라는 동적 배열에 최댓값을 가지는 노드를 차례대로 넣어주고,
                * 그 값보다 큰 값이 나오면, 동적 배열을 clear 하였습니다.
                * 그러나, arr.clear()는 O(N)의 시간복잡도를 가집니다. 그러므로 최댓값이 갱신될 때마다 O(N)이 호출됩니다.
                * 만약 n번 내내 최댓값이 갱신된다면(최악의 경우), N*N의 시간이 걸리므로 개선할 필요가 있었습니다.
                *
                * i번 노드의 값을 각각 배열에 넣어주고, 최댓값만 갱신하도록 합니다.-> 배열에 대입 연산이 n번 반복되므로 O(n) 고정
                * 나중에 최댓값을 출력할 때, i번째의 값이 최댓값과 일치한다면, i를 출력하도록 합니다.
                *
                * 이렇게 되면, 굳이 Arraylist로 arr1을 선언할 필요가 없어지므로 list로 arr1의 타입을 바꾸었습니다.
                * 기존의 코드는 O(N*N + N)의 시간복잡도였다면, 현재는 O(N + N)으로 개선되었습니다.
                */
                arr1[i] = cnt;
                if (cnt > max){
                    max = cnt; // 최댓값을 갱신합니다.
                }
            }
        }

        for (int i = 0; i <= n; i++){
            if (arr1[i] == max) // i번째 배열의 값이 최댓값이라면 출력합니다.
                sb.append(i).append(" ");
        }
        System.out.print(sb.toString());
    }
}

/*
아래의 두 코드는 DFS(재귀)와 DFS(스택)의 차이를 비교하고자 함입니다.
재귀는 시간초과가 안나는데, 스택은 시간초과가 납니다.
그리고 List<List<Integer>> 로 선언하면 시간초과가 안나는데, ArrayList<Integer>로만 생성하면 시간초과가 납니다.
이유가 무엇일까요?

import java.util.*;
import java.io.*;

public class Main {
    public static ArrayList<Integer>[] arr;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int max = 0;

        arr = new ArrayList[n + 1];  // 그래프를 저장할 이차원 동적 배열
        int[] arr1 = new int[n + 1]; // 해당 노드에서 출발할 때 해킹할 수 있는 컴퓨터의 수를 저장할 배열


        // 배열 생성
        for (int i = 1; i <= n; i++) {
            arr[i] = new ArrayList<>();
        }

        // 배열 입력
        for (int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            arr[b].add(a);
        }


        for (int i = 1; i <= n; i++){
            int cnt = 1;                                // 현재 노드도 해킹당한 것에 포함되므로 카운트해줍니다.
            boolean[]  visited = new boolean[n + 1];    // 이미 방문한 노드인지를 확인하여 그래프가 Cycle을 가질 때 무한 반복되지 않도록 합니다.
            visited[i] = true;                          // i번째 노드는 시작점이므로 방문하였다고 표시합니다.
            
            Stack<Integer> arr2 = new Stack<>();
            arr2.push(i);

            while (!arr2.isEmpty()){
                int node = arr2.pop();


                for (int integer : arr[node]){
                    if (!visited[integer]) {
                        visited[integer] = true;
                        cnt += 1;
                        System.out.print(integer);
                        arr2.push(integer);          
                    }
                }
                
                arr1[i] = cnt;
                if (cnt > max){
                    max = cnt; // 최댓값을 갱신합니다.
                }
            }
        }

        for (int i = 0; i <= n; i++){
            if (arr1[i] == max) // i번째 배열의 값이 최댓값이라면 출력합니다.
                sb.append(i).append(" ");
        }
        System.out.print(sb.toString());
    }
}

public class Main3 {
    public static List<List<Integer>> arr;
    public static int[] arr1;
    public static boolean[] visited;
    public static int cnt;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int max = 0;

        arr = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            arr.add(new ArrayList<>()); 
        }

        arr1 = new int[n + 1]; // 각 노드에서 출발할 때 해킹할 수 있는 컴퓨터 수 저장

        // 그래프 입력
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            arr.get(b).add(a);
        }

        // 모든 노드에서 DFS 수행
        for (int i = 1; i <= n; i++) {
            if (arr.get(i).isEmpty()) continue;

            visited = new boolean[n + 1];  // 방문 배열 초기화
            cnt = 1; // 현재 노드도 해킹된 것으로 카운트
            visited[i] = true;

            dfs(i); // DFS 수행

            arr1[i] = cnt;
            if (cnt > max) {
                max = cnt; // 최댓값 갱신
            }
        }

        // 최댓값을 가진 노드 출력
        for (int i = 1; i <= n; i++) {
            if (arr1[i] == max) {
                sb.append(i).append(" ");
            }
        }
        System.out.print(sb.toString());
    }

    private static void dfs(int node) {
        for (int next : arr.get(node)) { 

            if (!visited[next]) {
                visited[next] = true;
                System.out.print(next);
                cnt++;  // 해킹된 컴퓨터 개수 증가
                dfs(next);
            }
        }
    }
}

*/
