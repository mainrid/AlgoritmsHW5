
import java.util.*;

public class TreeNode {

	private String name;
	private TreeNode firstChild;
	private TreeNode nextSibling;

	TreeNode(String n, TreeNode d, TreeNode r) {

		if (n == null || n.isEmpty()) {
			throw new RuntimeException("Node name should not be empty or null");
		}
		
		if(n.contains("(") || n.contains(")") || n.contains(",") || n.contains(" ")){
			throw new RuntimeException("Node name : " + n + " is not in the correct format");
		}
		
		this.name = n;
		this.firstChild = d;
		this.nextSibling = r;
	}

	public static TreeNode parsePrefix(String s) {
		if (s == null || s.isEmpty()) {
			return null;
		}

		TreeNode node = parseTree(s);

		return node;
	}

	public String getName() {
		return this.name;
	}

	public TreeNode getChild() {
		return this.firstChild;
	}

	public TreeNode getSibling() {
		return this.nextSibling;
	}

	private static TreeNode parseTree(String s) {
		try {
			if (s == null || s.isEmpty()) {
				return null;
			}
			StringTokenizer splitted = new StringTokenizer(s, ",()", true);
			String token = "";
			String child = "";
			String sibling = "";
			int bracketCounter = 0;

			String name = splitted.nextToken();

			while (splitted.hasMoreTokens()) {
				token = splitted.nextToken();
				if (token.equals("(")) {
					bracketCounter++;
					while (splitted.hasMoreTokens()) {
						token = splitted.nextToken();
						if (token.equals(")")) {
							bracketCounter--;
							if (bracketCounter == 0) {
								break;
							}
						}
						if (token.equals("(")) {
							bracketCounter++;
						}
						child += token;
					}
				} else if (token.equals(",")) {
					while (splitted.hasMoreTokens()) {
						sibling += splitted.nextToken();
					}
				}
			}

			return new TreeNode(name, parseTree(child), parseTree(sibling));
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage() + ". Incorrect argument: " + s);
		}
	}

	public String rightParentheticRepresentation() {
		StringBuffer b = new StringBuffer();
		// TODO!!! create the result in buffer b
		return b.toString();
	}

	public static void main(String[] param) {
		String s = "A(B1,C,D)";
		TreeNode t = TreeNode.parsePrefix(s);

		if (t.getName().equals("A") && t.getChild().getName().equals("B1") && t.getChild().getSibling().getName().equals("C") && t.getChild().getSibling().getSibling().getName().equals("D")) {
			System.out.println("Test OK");
		} else {
			System.out.println("Test fails");
		}
		// String v = t.rightParentheticRepresentation();
		// System.out.println(s + " ==> " + v); // A(B1,C,D) ==> (B1,C,D)A
	}
}
