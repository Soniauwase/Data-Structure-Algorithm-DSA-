import java.util.EmptyStackException;
import java.util.NoSuchElementException;

public  class Stack {

public static class MyStack<T> {
    private static class Node<T> {
                T data;
                Node<T> next;
                Node(T data) {
                    this.data = data;
                    this.next = null;
                }
            }

            private Node<T> top;
            private int size;

            public MyStack() {
                this.top = null;
                this.size = 0;
            }

            // Push: Add item to the top of the stack - O(1)
            public void push(T data) {
                Node<T> newNode = new Node<>(data);
                newNode.next = top;
                top = newNode;
                size++;
            }

            // Pop: Remove and return the top item - O(1)
            public T pop() {
                if (isEmpty()) {
                    throw new EmptyStackException();
                }
                T data = top.data;
                top = top.next;
                size--;
                return data;
            }

            // Peek: Look at the top item without removing it - O(1)
            public T peek() {
                if (isEmpty()) {
                    throw new EmptyStackException();
                }
                return top.data;
            }

            // isEmpty: Check if stack has no elements - O(1)
            public boolean isEmpty() {
                return top == null;
            }

            public int size() {
                return size;
            }
        }

        // ==========================================
        // 2. MyQueue<T> using Two Stacks
        // ==========================================
        public static class MyQueue<T> {
            private MyStack<T> stackIn;   // Handles incoming elements (enqueue)
            private MyStack<T> stackOut;  // Handles outgoing elements (dequeue/peek)

            public MyQueue() {
                stackIn = new MyStack<>();
                stackOut = new MyStack<>();
            }

            // Enqueue: Add item to the back of the queue - O(1)
            public void enqueue(T item) {
                stackIn.push(item);
            }

            // Dequeue: Remove item from the front of the queue - Amortized O(1)
            public T dequeue() {
                shiftStacks();
                if (stackOut.isEmpty()) {
                    throw new NoSuchElementException("Queue is empty");
                }
                return stackOut.pop();
            }

            // Peek: View front item - Amortized O(1)
            public T peek() {
                shiftStacks();
                if (stackOut.isEmpty()) {
                    throw new NoSuchElementException("Queue is empty");
                }
                return stackOut.peek();
            }

            public boolean isEmpty() {
                return stackIn.isEmpty() && stackOut.isEmpty();
            }

            // Helper: Moves items from stackIn to stackOut to reverse their order
            private void shiftStacks() {
                if (stackOut.isEmpty()) {
                    while (!stackIn.isEmpty()) {
                        stackOut.push(stackIn.pop());
                    }
                }
            }
        }

        // ==========================================
        // 3. isBalanced(String expr) Bracket Validator
        // ==========================================
        public static boolean isBalanced(String expr) {
            MyStack<Character> stack = new MyStack<>();

            for (int i = 0; i < expr.length(); i++) {
                char ch = expr.charAt(i);

                // If it's an opening bracket, push it onto the stack
                if (ch == '(' || ch == '{' || ch == '[') {
                    stack.push(ch);
                }
                // If it's a closing bracket
                else if (ch == ')' || ch == '}' || ch == ']') {
                    // If the stack is empty, we have a closing bracket with no opening match
                    if (stack.isEmpty()) {
                        return false;
                    }

                    char top = stack.pop();

                    // Check if the popped opening bracket matches the current closing bracket
                    if ((ch == ')' && top != '(') ||
                            (ch == '}' && top != '{') ||
                            (ch == ']' && top != '[')) {
                        return false;
                    }
                }
            }
            return stack.isEmpty();
        }

    }

