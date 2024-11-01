package org.example.Coverage;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CoverageAnalyzer {
    private static final String FILE_PATH = "src/main/java/org/example/TestJava.java";
    private static Map<String, Set<Integer>> coveredLinesByClass = new HashMap<>();
    private static Map<String, Set<String>> totalMethodsByClass = new HashMap<>();
    private static Map<String, Set<Integer>> methodLines = new HashMap<>();


    public static void main(String[] args) throws Exception {
        JavaParser parser = new JavaParser();
        CompilationUnit cu = parser.parse(new File(FILE_PATH)).getResult()
                .orElseThrow(() -> new FileNotFoundException("Archivo no encontrado: " + FILE_PATH));

        instrumentMethods(cu);
        calculateCoverage();
    }

    private static void instrumentMethods(CompilationUnit cu) {
        cu.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(ClassOrInterfaceDeclaration classDecl, Void arg) {
                String className = classDecl.getNameAsString();
                totalMethodsByClass.put(className, new HashSet<>());
                coveredLinesByClass.put(className, new HashSet<>());

                super.visit(classDecl, arg);
            }

            @Override
            public void visit(MethodDeclaration md, Void arg) {
                super.visit(md, arg);
                String methodName = md.getNameAsString();
                String className = md.findAncestor(ClassOrInterfaceDeclaration.class)
                        .map(ClassOrInterfaceDeclaration::getNameAsString).orElse("UnknownClass");

                totalMethodsByClass.get(className).add(methodName);

                coveredLinesByClass.get(className).add(md.getBegin().map(pos -> pos.line).orElse(-1));
                MethodLines(md);
            }
        }, null);
    }


    private static void MethodLines(MethodDeclaration md) {
        Optional<com.github.javaparser.Position> methodStart = md.getBegin();
        Optional<com.github.javaparser.Position> methodEnd = md.getEnd();
        String methodName = md.getNameAsString();
        Set<Integer> lines = new HashSet<>();

        if (methodStart.isPresent() && methodEnd.isPresent()) {
            int cont = 0;
            for (int line = methodStart.get().line; line <= methodEnd.get().line; line++) {
                cont  = cont + 1;
            }
            lines.add(cont);
        }
        methodLines.put(methodName, lines);
    }


    private static void calculateCoverage() {
        for (String className : totalMethodsByClass.keySet()) {
            Set<Integer> coveredLines = coveredLinesByClass.getOrDefault(className, new HashSet<>());
            Set<String> totalMethods = totalMethodsByClass.getOrDefault(className, new HashSet<>());


            int totalCount = totalMethods.size();

            System.out.println("Class: " + className);
            System.out.println("Total Methods: " + totalCount);
            System.out.println("Line Covered: " + coveredLines);
            System.out.println("Methods Covered:");
            for (String methodName : totalMethods) {
                Set<Integer> lines = methodLines.getOrDefault(methodName, new HashSet<>());
                System.out.println("- " + methodName + " (Lines: " + lines + ")");
            }
            System.out.println();
        }
    }
}
