package org.example;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class JavaParserExample {
    public static void main() {
        try {

            FileInputStream in = new FileInputStream(new File("src/main/java/org/example/TestJava.java"));

            JavaParser javaParser = new JavaParser();

            CompilationUnit cu = javaParser.parse(in).getResult().get();

            System.out.println(cu.toString());

            System.out.println("AST");

            VoidVisitor<?> visitor = new ASTPrinter();
            visitor.visit(cu, null);

            System.out.println("Only methods");
            extractMethods(cu);

            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class ASTPrinter extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(CompilationUnit cu, Void arg) {
            super.visit(cu, arg);
            System.out.println(cu.toString());
        }
    }

    public static void extractMethods(CompilationUnit cu) {
        try {

            for (TypeDeclaration<?> type : cu.getTypes()) {
                List<BodyDeclaration<?>> members = type.getMembers();
                for (BodyDeclaration<?> member : members) {
                    if (member instanceof MethodDeclaration) {
                        MethodDeclaration method = (MethodDeclaration) member;
                        System.out.println(method.getName());
                        System.out.println(method.getParameters());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

