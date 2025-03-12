import java.util.ArrayList;

// DFS에 대해서 1) 재귀 2) 스택 으로 구현하였습니다.
/* 예시의 형태는 다음과 같습니다.
* 1 - 3
* |
* 2 - 5
* |
* 4
* */
public class Main {
    public static int[][] arr = {{}, {2, 3}, {1, 4, 5}, {1}, {2}, {2}};

    public static boolean [] visited_for_DFS = new boolean[arr.length];

    public static void DFS_recursive(int index){ // 재귀 형식
        visited_for_DFS[index] = true; // 처음에 방문한 node는 방문한 것으로 처리합니다.
        System.out.print(index + " "); // 방문 순서대로 출력합니다.

        for (int i = 0; i < arr[index].length; i++) { // 해당 노드의 인접 노드들을 순서대로 탐색합니다.
            if (!visited_for_DFS[arr[index][i]]) {    // 만일 인접 노드를 방문한 적이 없다면,
                DFS_recursive(arr[index][i]);         // 인접 노드부터 방문하도록 합니다.
            }
        }
    }

    public static void DFS_stack(int index){ // 스택 형식
        ArrayList<Integer> must_visit = new ArrayList<Integer>(); // 방문해야할 스택은 길이가 변하므로, 동적배열로 선언하였습니다.
        boolean[] visited = new boolean[arr.length];        // 방문한 노드들을 표시할 visited 배열입니다.
        must_visit.add(index);                              // 탐색의 시작이 될 노드를 방문해야할 스택에 넣습니다.
        visited[index] = true;                              // 그리고 첫 노드를 방문하였음을 표시합니다.

        while (must_visit.size() != 0){ // 방문해야할 노드가 남아있다면,
            int num = must_visit.remove(must_visit.size() - 1); // 스택에서 맨 마지막에 넣은 노드를 꺼냅니다.
            System.out.print(num+ " ");
            visited[num] = true;                                      // 해당 노드를 꺼냈으므로 방문했다고 표시합니다.

            for (int i = arr[num].length - 1; i >= 0; i--){           // 인접 노드를 왼쪽에서부터 탐색합니다.
                if (!visited[arr[num][i]])                            // 만일 인접 노드를 방문한 적이 없다면,
                    must_visit.add(arr[num][i]);                      // 방문해야할 배열에 해당 노드를 넣습니다.
            }
        }
    }
    public static void main(String[] args) {
        DFS_stack(1);
        // 스택의 경우
        // [1] -> [3 2] -> [3 5 4] -> [3 5] -> [3] -> [] 순으로 스택이 비워집니다.
        System.out.println("");

        DFS_recursive(1);


    }
}
