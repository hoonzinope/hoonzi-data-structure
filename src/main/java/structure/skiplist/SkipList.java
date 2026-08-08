package structure.skiplist;

import java.util.Comparator;
import java.util.Objects;
import java.util.random.RandomGenerator;

public class SkipList<E> {
    private boolean debug = true;
    private static final int MAX_LEVEL = 16;

    private final Comparator<? super E> comparator;
    private final Node<E> head;

    private int currentLevel;
    private int size;

    RandomGenerator generator = RandomGenerator.getDefault();

    public SkipList(Comparator<? super E> comparator) {
        this.comparator = Objects.requireNonNull(comparator);
        this.head = new Node<>(null, MAX_LEVEL);

        this.currentLevel = 0;
        this.size = 0;
    }

    public void insert(E insert_value) {
        var value = insert_value;
        var nodeLevel = buildNodeLevel();
        print("insert value="+insert_value+" level="+nodeLevel);

        var searchLevel = Math.max(nodeLevel, this.currentLevel);
        Node<E> [] prevNodes = (Node<E>[])  new Node<?>[searchLevel+1];
        Node<E> matched = null;

        Node<E> currentNode = this.head;
        for(var i = searchLevel; i > -1; i--) {
            Node<E> prev = currentNode;
            Node<E> next = currentNode.next[i];
            if (next == null) {
                if(nodeLevel >= i)
                    prevNodes[i] = currentNode;
                continue;
            }
            while(true) {
                Node<E> cmpNode = next;
                if(cmpNode == null) {
                    break;
                }
                E cmpNodeValue = cmpNode.value;
                int compared = compareTo(cmpNodeValue, value);
                if (compared < 0){
                    // cmpNodeValue < value
                    prev = next;
                    next = next.next[i];
                } else if (compared == 0) {
                    matched = next;
                    matched.addCount();

                    size++;
                    return;
                } else {
                    break;
                }
            }
            currentNode = prev;
            prevNodes[i] = prev;
        }

        Node<E> node = matched == null ? new Node<E>(insert_value, nodeLevel) : matched;
        for(var i = 0; i < nodeLevel+1; i++){
            node.next[i] = prevNodes[i].next[i];
            prevNodes[i].next[i] = node;
        }

        this.size++;
        this.currentLevel = Math.max(currentLevel, nodeLevel);
    }

    public void delete(E value) {
        print("delete value = "+value);
        boolean delete_flag = false;
        Node<E> currentNode = this.head;
        for(var i = currentLevel; i > -1; i--) {
            Node<E> prev = currentNode;
            Node<E> next = currentNode.next[i];
            if(next == null) continue;
            while(true) {
                if(next == null) break;
                int compared = compareTo(next.value, value);
                if(compared < 0){
                    prev = next;
                    next = next.next[i];
                }else if (compared == 0) {
                    if(next.count > 1) {
                        next.subCount();
                        size--;
                        return;
                    } else{
                        prev.next[i] = next.next[i];
                        delete_flag = true;
                        break;
                    }
                } else {
                    break;
                }
            }

            currentNode = prev;
            if(delete_flag && this.head.next[i] == null && currentLevel == i) {
                currentLevel--;
            }
        }
        if (delete_flag) {
            this.size--;
        }
    }

    public boolean contains(E value) {
        return this.search(value) != null;
    }

    public int count(E value) {
        Node<E> searchNode = this.search(value);
        var count = searchNode == null ? 0 : searchNode.count;
        return count;
    }

    private Node<E> search(E value) {
        var searchLevel = this.currentLevel;
        Node<E> currentNode = this.head;
        for(var i = searchLevel; i > -1; i--) {
            Node<E> prev = currentNode;
            Node<E> next = currentNode.next[i];
            if(next == null) continue;
            while(next != null) {
                int compared = compareTo(next.value, value);
                if(compared < 0){
                    prev = next;
                    next = next.next[i];
                }else if (compared == 0) {
                    return next;
                }else {
                    break;
                }
            }
            currentNode = prev;
        }
        return null;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void traverse() {
        for(var i = this.currentLevel; i > -1; i--) {
            var next = this.head.next[i];
            while(true) {
                if(next== null) {
                    break;
                }
                if(next.getCount() > 1){
                    System.out.print("("+next.value+","+next.getCount()+")");
                }else{
                    System.out.print(next.value);
                }
                System.out.print("-");
                next = next.next[i];
            }
            System.out.println(" ");
        }
    }

    private int buildNodeLevel() {
        var level = 0;
        while(true) {
            int randomNumber = generator.nextInt(2);
            if (randomNumber % 2 != 0) {
                level += 1;
            }else{
                break;
            }
        }
        return Math.min(level, MAX_LEVEL);
    }

    private int compareTo(E o1, E o2){
        return this.comparator.compare(o1,o2);
    }

    private void print(String log) {
        if(this.debug)
            System.out.println(log);
    }
}
