
import java.util.*;

public class TreeNode {

	private String name;
	private TreeNode firstChild;
	private TreeNode nextSibling;

	TreeNode(String n, TreeNode d, TreeNode r) {

		if (n == null || n.isEmpty()) {
			throw new RuntimeException("Node name should not be empty or null");
		}

		if (n.contains("(") || n.contains(")") || n.contains(",") || n.contains(" ")) {
			throw new RuntimeException("Node name : " + n + " is not in the correct format");
		}

		this.name = n;
		this.firstChild = d;
		this.nextSibling = r;
	}


	public static TreeNode parsePrefix(String s) {
		if (s.contains(",") && !s.contains("(") || s.contains(",") && !s.contains(")")) {
			throw new RuntimeException("Argument : " + s + " is not in the correct format");
		}
		if (s.equals("\t")) {
			throw new RuntimeException("Argument : " + s + " is not in the correct format");
		}

		if (s == null || s.isEmpty()) {
			return null;
		}

		TreeNode node = parseTree(s);
		
		if(node.nextSibling!=null){
			throw new RuntimeException("Argument : " + s + " is not in the correct format");
		}

		return node;
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
					if (child.isEmpty()) {
						throw new RuntimeException("With argument: " + s + " there should be one child at least");
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
		if (firstChild != null) {
			b.append("(");
			b.append(firstChild.rightParentheticRepresentation());
			b.append(")");
		}
		b.append(name);
		if (nextSibling != null) {
			b.append(",");
			b.append(nextSibling.rightParentheticRepresentation());
		}
		return b.toString();
	}

	public static void main(String[] param) {
		String s = "A(B1,C,D)";
		TreeNode t = TreeNode.parsePrefix(s);
		String v = t.rightParentheticRepresentation();
		System.out.println(s + " ==> " + v); // A(B1,C,D) ==> (B1,C,D)A

		s = "D(A(B,C),U)";
		t = TreeNode.parsePrefix(s);
		v = t.rightParentheticRepresentation();
		System.out.println(s + " ==> " + v); // D(A(B,C),U) ==> ((B,C)A,U)D

		s = "A";
		t = TreeNode.parsePrefix(s);
		v = t.rightParentheticRepresentation();
		System.out.println(s + " ==> " + v); // A ==> A

		s = "A()";
		t = TreeNode.parsePrefix(s);
		v = t.rightParentheticRepresentation();
		System.out.println(s + " ==> " + v); // RuntimeException

	}
}
