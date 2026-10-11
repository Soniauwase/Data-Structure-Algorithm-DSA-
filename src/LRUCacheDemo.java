import java.util.HashMap;
import java.util.Map;
public  class LRUCacheDemo {
    public static class LRUCache<K, V> {

        private static class Node<K, V> {
            K key;
            V value;
            Node<K, V> prev;
            Node<K, V> next;

            Node(K key, V value) {
                this.key = key;
                this.value = value;
            }
        }

        private final int capacity;
        private final Map<K, Node<K, V>> map;
        private final Node<K, V> head;
        private final Node<K, V> tail;


        public LRUCache(int capacity) {
            this.capacity = capacity;
            this.map = new HashMap<>();

            this.head = new Node<>(null, null);
            this.tail = new Node<>(null, null);
            head.next = tail;
            tail.prev = head;
        }

        public V get(K key) {
            if (!map.containsKey(key)) {
                return null;
            }

            Node<K, V> node = map.get(key);
            removeNode(node);
            addToHead(node);
            return node.value;
        }


        public void put(K key, V value) {
            if (map.containsKey(key)) {
                Node<K, V> node = map.get(key);
                node.value = value;
                removeNode(node);
                addToHead(node);
            } else {
                if (map.size() >= capacity) {
                    Node<K, V> lru = tail.prev;
                    removeNode(lru);
                    map.remove(lru.key);
                }

                Node<K, V> newNode = new Node<>(key, value);
                map.put(key, newNode);
                addToHead(newNode);
            }
        }
        private void removeNode(Node<K, V> node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        private void addToHead(Node<K, V> node) {
            node.prev = head;
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
        }
    }


    public static void main(String[] args) {
        System.out.println("=== Testing LRU Cache (Capacity = 2) ===");

        LRUCache<Integer, String> cache = new LRUCache<>(2);

        cache.put(1, "One");
        cache.put(2, "Two");

        System.out.println("Get key 1 (Expected 'One'): " + cache.get(1)); // Access 1, making 2 the LRU item

        cache.put(3, "Three");

        System.out.println("Get key 2 after eviction (Expected null): " + cache.get(2));
        System.out.println("Get key 3 (Expected 'Three'): " + cache.get(3));
        System.out.println("Get key 1 (Expected 'One'): " + cache.get(1));
    }
}


