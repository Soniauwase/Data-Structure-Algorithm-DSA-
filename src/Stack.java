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

            public void push(T data) {
                Node<T> newNode = new Node<>(data);
                newNode.next = top;
                top = newNode;
                size++;
            }

            public T pop() {
                if (isEmpty()) {
                    throw new EmptyStackException();
                }
                T data = top.data;
                top = top.next;
                size--;
                return data;
            }

            public T peek() {
                if (isEmpty()) {
                    throw new EmptyStackException();
                }
                return top.data;
            }

            public boolean isEmpty() {
                return top == null;
            }

            public int size() {
                return size;
            }
        }


        public static class MyQueue<T> {
            private MyStack<T> stackIn;
            private MyStack<T> stackOut;

            public MyQueue() {
                stackIn = new MyStack<>();
                stackOut = new MyStack<>();
            }

            public void enqueue(T item) {
                stackIn.push(item);
            }

            public T dequeue() {
                shiftStacks();
                if (stackOut.isEmpty()) {
                    throw new NoSuchElementException("Queue is empty");
                }
                return stackOut.pop();
            }

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

            private void shiftStacks() {
                if (stackOut.isEmpty()) {
                    while (!stackIn.isEmpty()) {
                        stackOut.push(stackIn.pop());
                    }
                }
            }
        }


        public static boolean isBalanced(String expr) {
            MyStack<Character> stack = new MyStack<>();

            for (int i = 0; i < expr.length(); i++) {
                char ch = expr.charAt(i);

                if (ch == '(' || ch == '{' || ch == '[') {
                    stack.push(ch);
                }
                else if (ch == ')' || ch == '}' || ch == ']') {
                    if (stack.isEmpty()) {
                        return false;
                    }

                    char top = stack.pop();
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

