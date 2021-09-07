
interface CompactTreeBuilder<T> {
    /**
     * Given a root of a tree. The tree may be of any depth and width,
     * i.e. each node may have any number of child nodes.
     * This method should transform a tree in such a way
     * that each node (except probably one) would either have N or 0 children
     * (and one node may have a number of children between 0 and N).
     *
     * Algorithm may transform given tree in any way with only condition:
     * if node A was an ascendant of node B in a source tree
     * node B may not be an ascendant of a node A in a result tree (they may become siblings though).
     *
     * E.g.
     *
     * source:        compact(A, 2)     compact(A, 1)             compact(A, 100)
     *
     * A               A                 A                         A
     *  |               |                 |_B                       |_B
     *  |_B             |_B                  |_C                    |
     *     |            |  |                    |_D                 |_C
     *     |            |  |_D                     |_E              |
     *     |            |  |                          |_F           |_D
     *     |_C          |  |_E                           |_G        |
     *     | |_D        |    |_H                            |_H     |_E
     *     |    |_F     |                                           |
     *     |            |_C                                         |_F
     *     |_E            |                                         |
     *       |_G          |_F                                       |_G
     *       |            |                                         |
     *       |_H          |_G                                       |_H
     *
     *
     *  in an example for compact(A,2) above node E is an exception node:
     *  it has 1 child while any other node has either 2 or 0 children
     */
    Node<T> compact(Node<T> root, int N);
    }
 
    class Node<T> {
    private final T data;
 
    private final List<Node<T>> children;
 
    public Node(T data, List<Node<T>> children) {
        this.data = data;
        this.children = children;
    }
 
    public T getData() {
        return data;
    }
 
    public List<Node<T>> getChildren() {
        return children;
    }
}

需要两个队列，一个队列用来存等待设置N节点的节点，
另一个队列保存bfs的结果。每次从bfs的队列里面poll出peek，
需要做三件事情，
一个是将它设置为第一个队列peek的儿子，
另外一个是将它的儿子放在bfs的队列中，
第三个是将它放在第一个队列中等待设置Nchild。
需要给第一个对列的peek设置一个count， 
记录它已经设置了多少儿子。每次到了N以后出队，count归0。


Node compact(Node root, int N) {
    Node newRoot = new Node(root.data);
    Queue<Node> queue = new LinkedList<>();
  
    queue.offer(root);
  
    Queue<Node> newQueue = new LinkedList<>();
  
    newQueue.offer(newRoot);

    while (queue.size() > 0) {
        Node newNode = newQueue.poll();
         int index = 0;
         while (index < N) {
            if(queue.size() > 0) {
              Node next = bfs(queue);
              Node newNext = new Node(next.data);
              newNode.children.add(newNext);
              newNode.offer(newNext);
              index++;
            } else {
              break;
            }
        }
      }
    retunr newRoot;
  }
  
public Node bfs(Queue<Node> queue) {
    Node node = queue.poll();
    for(Node next: node.children) {
      queue.offer(next);
    }
    return node;
}
