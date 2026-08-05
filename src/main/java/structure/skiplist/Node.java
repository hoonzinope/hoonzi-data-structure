package structure.skiplist;

public final class Node<E> {
    E value;
    Node<E> [] next;
    int count;

    @SuppressWarnings("unchecked")
    Node(E value, int maxLevel) {
        this.value = value;
        this.next = (Node<E>[]) new Node<?>[maxLevel+1];
        this.count = 1;
    }

    int getLevel(){
        return this.next.length-1;
    }

    int getCount() {
        return this.count;
    }

    void addCount() {
        this.count++;
    }

    void subCount() {
        this.count--;
    }
}
