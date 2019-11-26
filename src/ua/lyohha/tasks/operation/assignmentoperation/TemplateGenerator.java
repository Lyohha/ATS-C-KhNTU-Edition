package ua.lyohha.tasks.operation.assignmentoperation;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import ua.lyohha.tasks.CodeGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TemplateGenerator {

    private List<HBox> lines = new ArrayList<>();
    private String[] answers = new String[4];
    private Random random;

    public TemplateGenerator() {
        createTemplate();
    }

    public void setTemplate(Pane pane) {
        for (int i = 0; i < lines.size(); i++) pane.getChildren().add(lines.get(i));
    }

    public String[] getAnswers() {
        return answers;
    }

    private void createTemplate() {
        random = new Random(System.currentTimeMillis());
        createFirstsLine();
        int x = createVariables();
        x = createFirstExpression(x);
        createSecondExpression(x);
        x = createThirdExpression(x);
        createFourthExpression(x);
        createLastLine();
    }

    private int createVariables() {
        int x = random.nextInt(10);
        lines.add(new HBox(
                CodeGenerator.createPart("\t", CodeGenerator.CodeType.TEXT),
                CodeGenerator.createPart("int", CodeGenerator.CodeType.OPERATOR),
                CodeGenerator.createPart(" x=", CodeGenerator.CodeType.TEXT),
                CodeGenerator.createPart(Integer.toString(x), CodeGenerator.CodeType.NUMBER),
                CodeGenerator.createPart(", y, z;", CodeGenerator.CodeType.TEXT)
        ));

        return x;
    }

    private int createFirstExpression(int x) {
        int n1, n2, n3;
        CodeGenerator.Operator o1, o2, o3;

        int a, b;

        do {
            n1 = random.nextInt(9) + 1;
            n2 = random.nextInt(9) + 1;
            n3 = random.nextInt(9) + 1;

            o1 = CodeGenerator.Operator.values()[random.nextInt(CodeGenerator.Operator.values().length)];
            o2 = CodeGenerator.Operator.values()[random.nextInt(CodeGenerator.Operator.values().length)];
            o3 = CodeGenerator.Operator.values()[random.nextInt(CodeGenerator.Operator.values().length)];

            if ((o3 == CodeGenerator.Operator.MULTIPLICATION || o3 == CodeGenerator.Operator.DIVISION) &&
                    !(o2 == CodeGenerator.Operator.MULTIPLICATION || o3 == CodeGenerator.Operator.DIVISION)) {
                a = CodeGenerator.exeOperator(n2, n3, o3);
                if (a == 0 && o2 == CodeGenerator.Operator.DIVISION)
                    continue;
                b = CodeGenerator.exeOperator(n1, a, o2);
            } else {
                a = CodeGenerator.exeOperator(n1, n2, o2);
                b = CodeGenerator.exeOperator(a, n3, o3);
            }
            if (b == 0 && o1 == CodeGenerator.Operator.DIVISION)
                continue;
            break;
        }
        while (true);

        lines.add(new HBox(
                CodeGenerator.createPart("\tx" + CodeGenerator.getOperator(o1) + "=", CodeGenerator.CodeType.TEXT),
                CodeGenerator.createPart(Integer.toString(n1), CodeGenerator.CodeType.NUMBER),
                CodeGenerator.createPart(CodeGenerator.getOperator(o2), CodeGenerator.CodeType.TEXT),
                CodeGenerator.createPart(Integer.toString(n2), CodeGenerator.CodeType.NUMBER),
                CodeGenerator.createPart(CodeGenerator.getOperator(o3), CodeGenerator.CodeType.TEXT),
                CodeGenerator.createPart(Integer.toString(n3), CodeGenerator.CodeType.NUMBER),
                CodeGenerator.createPart("; ", CodeGenerator.CodeType.TEXT),
                CodeGenerator.createPart("PRINTX", CodeGenerator.CodeType.DEFINE1),
                CodeGenerator.createPart(";", CodeGenerator.CodeType.TEXT)
        ));


        x = CodeGenerator.exeOperator(x, b, o1);
        answers[0] = Integer.toString(x);
        return x;
    }

    private void createSecondExpression(int x) {
        int n1 = random.nextInt(9) + 1;
        CodeGenerator.Operator
                o1 = CodeGenerator.Operator.values()[random.nextInt(CodeGenerator.Operator.values().length)];

        lines.add(new HBox(
                CodeGenerator.createPart("\tx", CodeGenerator.CodeType.TEXT),
                CodeGenerator.createPart(CodeGenerator.getOperator(o1), CodeGenerator.CodeType.TEXT),
                CodeGenerator.createPart("=y=z=", CodeGenerator.CodeType.TEXT),
                CodeGenerator.createPart(Integer.toString(n1), CodeGenerator.CodeType.NUMBER),
                CodeGenerator.createPart("; ", CodeGenerator.CodeType.TEXT),
                CodeGenerator.createPart("PRINTX", CodeGenerator.CodeType.DEFINE1),
                CodeGenerator.createPart(";", CodeGenerator.CodeType.TEXT)
        ));

        x = CodeGenerator.exeOperator(x, n1, o1);

        answers[1] = Integer.toString(x);
    }

    private int createThirdExpression(int x) {
        int n1 = random.nextInt(100),
                n2 = random.nextInt(100);

        CodeGenerator.CompareOperator
                o1 = CodeGenerator.CompareOperator.values()[random.nextInt(CodeGenerator.CompareOperator.values().length)];

        lines.add(new HBox(
                CodeGenerator.createPart("\tx=", CodeGenerator.CodeType.TEXT),
                CodeGenerator.createPart(Integer.toString(n1), CodeGenerator.CodeType.NUMBER),
                CodeGenerator.createPart(CodeGenerator.getOperator(o1), CodeGenerator.CodeType.TEXT),
                CodeGenerator.createPart(Integer.toString(n2), CodeGenerator.CodeType.NUMBER),
                CodeGenerator.createPart("; ", CodeGenerator.CodeType.TEXT),
                CodeGenerator.createPart("PRINTX", CodeGenerator.CodeType.DEFINE1),
                CodeGenerator.createPart(";", CodeGenerator.CodeType.TEXT)
        ));

        x = CodeGenerator.compare(n1, n2, o1);
        answers[2] = Integer.toString(x);
        return x;
    }

    private void createFourthExpression(int x) {
        int
                n1 = random.nextInt(100);
        CodeGenerator.CompareOperator
                o1 = CodeGenerator.CompareOperator.values()[random.nextInt(CodeGenerator.CompareOperator.values().length)];

        lines.add(new HBox(
                CodeGenerator.createPart("\tx", CodeGenerator.CodeType.TEXT),
                CodeGenerator.createPart(CodeGenerator.getOperator(o1), CodeGenerator.CodeType.TEXT),
                CodeGenerator.createPart("(y=", CodeGenerator.CodeType.TEXT),
                CodeGenerator.createPart(Integer.toString(n1), CodeGenerator.CodeType.NUMBER),
                CodeGenerator.createPart("); ", CodeGenerator.CodeType.TEXT),
                CodeGenerator.createPart("PRINTX", CodeGenerator.CodeType.DEFINE1),
                CodeGenerator.createPart(";", CodeGenerator.CodeType.TEXT)
        ));

        answers[3] = Integer.toString(x);
    }

    private void createFirstsLine() {
        lines.add(new HBox(
                CodeGenerator.createPart("#include", CodeGenerator.CodeType.DIRECTIVE),
                CodeGenerator.createPart(" ", CodeGenerator.CodeType.TEXT),
                CodeGenerator.createPart("<stdio.h>", CodeGenerator.CodeType.LIBRARY)
        ));

        lines.add(new HBox(
                CodeGenerator.createPart("#define", CodeGenerator.CodeType.DIRECTIVE),
                CodeGenerator.createPart(" ", CodeGenerator.CodeType.TEXT),
                CodeGenerator.createPart("PRINTX", CodeGenerator.CodeType.DEFINE1),
                CodeGenerator.createPart(" ", CodeGenerator.CodeType.TEXT),
                CodeGenerator.createPart("printf(", CodeGenerator.CodeType.TEXT),
                CodeGenerator.createPart("\"%d\\n\"", CodeGenerator.CodeType.STRING),
                CodeGenerator.createPart(", x)", CodeGenerator.CodeType.TEXT)
        ));

        lines.add(new HBox(
                CodeGenerator.createPart(" ", CodeGenerator.CodeType.TEXT)
        ));

        lines.add(new HBox(
                CodeGenerator.createPart("void", CodeGenerator.CodeType.OPERATOR),
                CodeGenerator.createPart(" ", CodeGenerator.CodeType.TEXT),
                CodeGenerator.createPart("main()", CodeGenerator.CodeType.TEXT)
        ));

        lines.add(new HBox(
                CodeGenerator.createPart("{", CodeGenerator.CodeType.TEXT)
        ));
    }

    private void createLastLine() {
        lines.add(new HBox(
                CodeGenerator.createPart("}", CodeGenerator.CodeType.TEXT)
        ));
    }


}