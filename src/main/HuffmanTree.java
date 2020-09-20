package main;

/**
 * @author Yhtyyar created on 20.09.2020
 */
// binary tree
// 0- on the left, 1 - on the right
public class HuffmanTree {


    private Node root;

    HuffmanTree() {
        root = new Node('r');
    }

    public void addWord(String word, Token token) {
        Node currNode = root;
        for (int i = 0; i < word.length(); i++) {
            switch (word.charAt(i)) {
                case '0' :
                    if (currNode.getLeft() == null)
                        currNode.setLeft(new Node('0'));
                    currNode = currNode.getLeft();
                    break;
                case '1':
                    if (currNode.getRight() == null)
                        currNode.setRight(new Node('1'));
                    currNode = currNode.getRight();
                    break;
                default:
                    throw new IllegalArgumentException("Not binary word added");
            }
        }
        currNode.setToken(token);
    }

    public Node getRoot() {
        return root;
    }

    public static class Node {
        private char value; //Po suti eto ne nuzhno, no ne meshayet
        private Node left, right;
        private Token token;
        Node(char value) {
            this.value = value;
            left = right = null;
        }
        public void setToken(Token token) {
            this.token = token;
        }
        public Token getToken() {
            return token;
        }
        public boolean isLeaf() {
            return left == null && right == null;
        }
        public char getValue() {
            return value;
        }
        public Node getLeft() {
            return left;
        }
        public void setLeft(Node left) {
            this.left = left;
        }
        public Node getRight() {
            return right;
        }
        public void setRight(Node right) {
            this.right = right;
        }
    }
}
