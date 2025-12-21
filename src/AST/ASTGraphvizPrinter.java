package AST;

import AST.ASTNode;

import java.io.FileWriter;
import java.io.IOException;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class ASTGraphvizPrinter {

    private static int nodeCounter = 0;
    private static final Map<ASTNode, String> ids = new IdentityHashMap<>();

    private static String getId(ASTNode node) {
        return ids.computeIfAbsent(node, n -> "node" + (++nodeCounter));
    }

    public static void print(ASTNode root, String fileName) throws IOException {

        try (FileWriter writer = new FileWriter(fileName)) {

            writer.write("digraph AST {\n");
            writer.write("  node [shape=circle, fontname=\"Arial\"];\n");

            traverse(root, writer);

            writer.write("}\n");
        }
    }

    private static void traverse(ASTNode node, FileWriter writer) throws IOException {

        if (node == null) return;

        String id = getId(node);
        writer.write(String.format(
                "  %s [label=\"%s\"];\n",
                id,
                node.getClass().getSimpleName()
        ));

        List<ASTNode> children = node.getChildren();

        for (ASTNode child : children) {
            if (child == null) continue;

            String childId = getId(child);
            writer.write(String.format("  %s -> %s;\n", id, childId));
            traverse(child, writer);
        }
    }
}
