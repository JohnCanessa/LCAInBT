import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * Auxiliary class to build the binary tree.
 */
class TreeNode {

    // **** members ****
    int         val;
    TreeNode    left;
    TreeNode    right;

    // **** constructor ****
    TreeNode(int x) {
        val = x;
    }
}


/**
 * 
 */
class Solution {


    /**
     * Insert nodes into a binary tree in level order.
     * This function is recursive.
     */
    static TreeNode insertLevelNode(String[] arr, TreeNode root, int i) {

        // **** recursion base case ****
        if (i < arr.length) {
            
            // **** allocate element (if needed) ****
            if (!arr[i].equals("null")) {
                TreeNode temp = new TreeNode(Integer.parseInt(arr[i]));
                root = temp;
            } else {
                return null;
            }

            // **** insert left child ****
            root.left = insertLevelNode(arr, root.left, 2 * i + 1);

            // **** insert right child ****
            root.right = insertLevelNode(arr, root.right, 2 * i + 2);
        }

        // **** ****
        return root;
    }


    /**
     * Perform a level order traversal of the binary tree.
     */
    static void levelOrderTraversal (TreeNode root) {

        // **** ****
        List<TreeNode> currentQ = new LinkedList<TreeNode>();
        List<TreeNode> nextQ    = new LinkedList<TreeNode>();

        // **** ****
        currentQ.add(root);

        // **** traverse all levels ****
        while (!currentQ.isEmpty()) {

            // **** start with the leftmost node in this level ****
            TreeNode node = currentQ.remove(0);

            // **** ****
            if (node != null) {

                // **** print current node ****
                System.out.print(node.val + " ");

                // **** add left child to the next queue ****
                if (node.left != null)
                    nextQ.add(node.left);
                else
                    nextQ.add(null);
            
                // **** add the right child to the next queue ****
                if (node.right != null)
                    nextQ.add(node.right);
                else
                    nextQ.add(null);
            } else {

                // **** print null object ****
                System.out.print("  ");
            }

            // **** check for the then end of this level ****
            if (currentQ.isEmpty()) {

                // **** print line separator (end of this level) ****
                System.out.println();

                // **** swap queues ****
                currentQ = nextQ;
                nextQ = new LinkedList<TreeNode>();
            }
        }
    }


    /**
     * Find the node with the specified value.
     * Initialization for recursive call.
     */
    static TreeNode find(TreeNode root, int val) {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        find(root, val, stack);
        if (!stack.empty())
            return stack.pop();
        else
            return null;
    }


    /**
     * Find the node with the specified value.
     * This is a recursive function.
     */
    static void find(TreeNode root, int val, Stack<TreeNode> stack) {

        // **** ****
        if (root == null)
            return;

        // **** find on left child  ****
        find(root.left, val, stack);

        // **** ****
        if ((root != null) && (root.val == val)) {
            stack.push(root);
        }
  
        // **** find on right child ****
        find(root.right, val, stack);
    }


    /**
     * Find the LCA.
     * Recursive method.
     */
    static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

        // **** check if we are done ****
        if ((root == null) || (root == p) || (root == q))
            return root;

        // **** traverse the left tree ****
        TreeNode left = lowestCommonAncestor(root.left, p, q);

        // **** traverse the right tree ****
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        // **** return the root node as the LCA (p and q in this tree) ****
        if ((left != null) && (right != null))
            return root;

        // **** return the left OR the right node as the LCA ****
        return left != null ? left : right;
    }


    /**
     * Post order of binary tree traversal.
     * Recursive method.
     */
    static TreeNode lcaPostOrder(TreeNode root, TreeNode p, TreeNode q) {

        // **** end case ****
        if ((root == null) || (root == p) || (root == q))
            return root;

        // **** visit the left child ****
        TreeNode left = lcaPostOrder(root.left, p, q);

        // **** visit the right child ****
        TreeNode right = lcaPostOrder(root.right, p, q);

        // **** p and q in the current root of the binary tree ****
        if ((left != null) && (right!= null))
            return root;

        // **** p or q found but not both ****
        if (left != null)
            return left;
        else 
            return right;
    }


    /**
     * Test scaffolding.
     */
    public static void main(String[] args) {

        // **** open scanner ****
        Scanner sc = new Scanner(System.in);

        // **** read the node values and place them in an array of strings ****
        String strVal[] = sc.nextLine().split(" ");

        // **** root for the binary tree ****
        TreeNode root = null;

        // **** populate the binary tree ****
        root = insertLevelNode(strVal, root, 0);

        // **** print the binary tree in breath-first order ****
        levelOrderTraversal(root);

        // **** get the number of queries ****
        int m = Integer.parseInt(sc.nextLine());

        // ***** loop once per query ****
        for (int i = 0; i < m; i++) {

            // **** read p and q ****
            String[] pAndQ = sc.nextLine().split(" ");

            // **** convert to integer ****
            int pVal = Integer.parseInt(pAndQ[0]);
            
            // **** convert to integer ****
            int qVal = Integer.parseInt(pAndQ[1]);

            // **** find node p ****
            TreeNode p = find(root, pVal); 

            // **** find node q ****
            TreeNode q = find(root, qVal);

            // **** find the LCA of p and q ****
            TreeNode lca = lowestCommonAncestor(root, p, q);

            // **** display the LCA ****
            System.out.println("main <<< (" + pVal + "," + qVal + ") lca: " + lca.val);

            // // **** find the LCA of p and q (second attempt; ended being the same as previous) ****
            // lca = lcaPostOrder(root, p, q);

            // // **** display the LCA ****
            // System.out.println("main <<< (" + pVal + "," + qVal + ") lca: " + lca.val);
        }

        // **** close scanner ****
        sc.close();
    }

}