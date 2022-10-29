package lab1;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class DoublyLinkedList<V> {
    private final Node fake = new Node();

    /**
     * Constructs an empty list.
     */
    public DoublyLinkedList() {
        fake.prev = fake.next = fake;
    }

    /**
     * Inserts value into the end of list.
     * @param value stored value
     * @return node, where the given value is stored
     */
    public Node add(@NotNull V value) {
        Node node = new Node(value);
        add(node);
        return node;
    }

    /**
     * Removes the first element
     * @return removed value if list is not empty
     */
    public Optional<V> removeFirst() {
        if (fake.next != fake) {
            Node node = fake.next;
            remove(node);
            return Optional.of(node.getValue());
        }
        return Optional.empty();
    }

    /**
     * Moves current node to the end of the list.
     * @param node some node in the list
     */
    public void moveToFront(@NotNull Node node) {
        remove(node);
        add(node);
    }

    /**
     * Removes node from the list.
     * @param node some node in the list
     */
    public void remove(@NotNull Node node) {
        assert nodeInList(node);
        node.unlink();
    }

    private void add(@NotNull Node node) {
        node.link(fake.prev);
    }

    private boolean nodeInList(@NotNull Node node) {
        for (Node n = fake.next; n != fake; n = n.next) {
            if (n == node) {
                return true;
            }
        }
        return false;
    }

    /**
     * Contains value stored in the list. Used for moving.
     */
    public class Node {
        private final V value;
        private Node prev;
        private Node next;

        private Node(V value) {
            this.value = value;
        }

        private Node() {
            this(null);
        }

        public V getValue() {
            return value;
        }

        private void link(@NotNull Node after) {
            assert after.next != null;
            assert after.next.prev == after;

            prev = after;
            next = after.next;
            next.prev = prev.next = this;
        }

        private void unlink() {
            assert prev != null;
            assert next != null;
            assert prev.next == this;
            assert next.prev == this;

            prev.next = next;
            next.prev = prev;
            prev = next = null;
        }
    }
}
