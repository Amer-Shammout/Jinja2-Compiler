package AST;

import java.util.List;

public class ASTPrinter {

    public static void print(ASTNode node) {
        print(node, "", true);
    }

    private static void print(ASTNode node, String prefix, boolean isLast) {
        if (node == null) return; // ⭐ SAFETY

        System.out.println(prefix + (isLast ? "└── " : "├── ") + node.getNodeName());

        List<ASTNode> children = node.getChildren();
        for (int i = 0; i < children.size(); i++) {
            print(children.get(i),
                    prefix + (isLast ? "    " : "│   "),
                    i == children.size() - 1);
        }
    }

}
