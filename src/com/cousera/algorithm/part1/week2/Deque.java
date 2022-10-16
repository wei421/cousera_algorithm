
import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {


    private class Node<Item> {
        private Node<Item> prev;
        private Node<Item> next;
        private final Item data;

        Node(Item item) {
            data = item;
        }

        public void setPrev(Node<Item> node) {
            prev = node;
        }

        public void setNext(Node<Item> node) {
            next = node;
        }

        public Node<Item> getPrev() {
            return prev;
        }

        public Node<Item> getNext() {
            return next;
        }

        public Item getData() {
            return data;
        }
    }

    private Node<Item> first = null;
    private Node<Item> last = null;
    private int count = 0;

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node<Item> it = new Node<>(item);
        if (first == null) {
            last = it;
        } else {
            first.setPrev(it);
            it.setNext(first);
        }
        first = it;
        count++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node<Item> it = new Node<>(item);
        if (last == null) {
            first = it;
        } else {
            last.setNext(it);
            it.setPrev(last);
        }
        last = it;
        count++;

    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item it = first.getData();

        count--;
        if (size() == 0) {
            first = null;
            last = null;
            return it;
        }

        first = first.getNext();
        first.setPrev(null);
        return it;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item it = last.getData();

        count--;
        if (size() == 0) {
            first = null;
            last = null;
            return it;
        }

        last = last.getPrev();
        last.setNext(null);
        return it;

    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Node<Item> el = first;
            @Override
            public boolean hasNext() {
                return el != null;
            }
            @Override
            public Item next() {
                if (el == null) throw new NoSuchElementException();
                Node<Item> tmp = el;
                el = el.getNext();
                return tmp.getData();
            }
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> a = new Deque<>();
        a.addFirst("a");
        a.addFirst("b");
        a.addLast("c");
        for (String i : a) {
            System.out.println(i);
        }
        Iterator<String> b = a.iterator();
        System.out.println(b.next());
        System.out.println(b.next());
        System.out.println(b.next());
        System.out.println(a.removeFirst());
        System.out.println(a.removeFirst());
        System.out.println(a.removeFirst());
        System.out.println(a.removeFirst());
    }

}
