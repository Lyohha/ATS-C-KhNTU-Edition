package ua.lyohha.tasks.management.loopwhileanddowhile;

import javafx.scene.control.Label;
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
        Variables variables = createVariables();
        createFirstExpression(variables);
        createSecondExpression(variables);
        createThirdExpression(variables);
        createLastLine();
    }

    private Variables createVariables() {
        int x = random.nextInt(20);
        int y = random.nextInt(20);
        int z = random.nextInt(20);
        lines.add(new HBox(
                CodeGenerator.createPart("\tint", CodeGenerator.CodeType.OPERATOR),
                CodeGenerator.createPart(" x="),
                CodeGenerator.createPart(x),
                CodeGenerator.createPart(" y="),
                CodeGenerator.createPart(y),
                CodeGenerator.createPart(";")
        ));

        return new Variables(x, y, z);
    }

    private void createFirstExpression(Variables variables) {
        int
                n1 = (variables.y > 10 ? 0 : 10) + random.nextInt(10);
        CodeGenerator.CompareOperator
                c1 = variables.y > 10 ? CodeGenerator.CompareOperator.MORE : CodeGenerator.CompareOperator.LESS;
        CodeGenerator.IDOperator
                ido1 = variables.y > 10 ? CodeGenerator.IDOperator.PREFIXDECREMENT : CodeGenerator.IDOperator.PREFIXINCREMENT;
        CodeGenerator.Operator
                o1 = CodeGenerator.Operator.values()[random.nextInt(CodeGenerator.Operator.values().length)];

        variables.y = n1;
        variables.x = CodeGenerator.exeOperator(variables.x, variables.y, o1);

        lines.add(new HBox(
                CodeGenerator.createPart("\twhile", CodeGenerator.CodeType.OPERATOR),
                CodeGenerator.createPart("(y"),
                CodeGenerator.createPart(CodeGenerator.getOperator(c1)),
                CodeGenerator.createPart(n1),
                CodeGenerator.createPart(") "),
                addIDO(ido1, "y"),
                CodeGenerator.createPart("; x"),
                CodeGenerator.createPart(CodeGenerator.getOperator(o1)),
                CodeGenerator.createPart("=y;"),
                CodeGenerator.createPart(" PRINT(", CodeGenerator.CodeType.DEFINE1),
                CodeGenerator.createPart("x"),
                CodeGenerator.createPart(")", CodeGenerator.CodeType.DEFINE1),
                CodeGenerator.createPart(";")
        ));

        answers[0] = Integer.toString(variables.x);
    }

    private void createSecondExpression(Variables variables) {
        variables.x = random.nextInt(10);
        variables.y = random.nextInt(10);
        CodeGenerator.Operator
                o1 = random.nextInt(2) == 1 ? CodeGenerator.Operator.ADDITION : CodeGenerator.Operator.DIFFERENCE;
        int
                n1 = variables.y > 5 ? 0 : 5 + random.nextInt(5);
        CodeGenerator.CompareOperator
                c1 = variables.y > 5 ? CodeGenerator.CompareOperator.MORE : CodeGenerator.CompareOperator.LESS;
        CodeGenerator.IDOperator
                ido1 = variables.y > 5 ? CodeGenerator.IDOperator.PREFIXDECREMENT : CodeGenerator.IDOperator.PREFIXINCREMENT;

        lines.add(new HBox(
                CodeGenerator.createPart("\tx="),
                CodeGenerator.createPart(variables.x),
                CodeGenerator.createPart("; y="),
                CodeGenerator.createPart(variables.y),
                CodeGenerator.createPart(";")
        ));

        while (CodeGenerator.compare(variables.y, n1, c1) == 1) {
            variables.y = CodeGenerator.exeOperator(variables.y, ido1);
            variables.x = CodeGenerator.exeOperator(variables.x, variables.y, o1);
        }
        lines.add(new HBox(
                CodeGenerator.createPart("\twhile", CodeGenerator.CodeType.OPERATOR),
                CodeGenerator.createPart("(y"),
                CodeGenerator.createPart(CodeGenerator.getOperator(c1)),
                CodeGenerator.createPart(n1),
                CodeGenerator.createPart(") x"),
                CodeGenerator.createPart(CodeGenerator.getOperator(o1)),
                CodeGenerator.createPart("="),
                addIDO(ido1, "y"),
                CodeGenerator.createPart(";")
        ));

        lines.add(new HBox(
                CodeGenerator.createPart("\tPRINT(", CodeGenerator.CodeType.DEFINE1),
                CodeGenerator.createPart("x"),
                CodeGenerator.createPart(")", CodeGenerator.CodeType.DEFINE1),
                CodeGenerator.createPart("; "),
                CodeGenerator.createPart("PRINT(", CodeGenerator.CodeType.DEFINE1),
                CodeGenerator.createPart("y"),
                CodeGenerator.createPart(")", CodeGenerator.CodeType.DEFINE1),
                CodeGenerator.createPart(";")
        ));

        answers[1] = Integer.toString(variables.x);
        answers[2] = Integer.toString(variables.y);
    }

    private void createThirdExpression(Variables variables) {
        variables.y = random.nextInt(9) + 1;
        variables.z = random.nextInt(10);
        int n1 = random.nextInt(variables.y);
        CodeGenerator.Operator
                o1 = random.nextInt(2) == 1 ? CodeGenerator.Operator.ADDITION : CodeGenerator.Operator.DIFFERENCE;

        lines.add(new HBox(
                CodeGenerator.createPart("\ty="),
                CodeGenerator.createPart(variables.y),
                CodeGenerator.createPart("; z="),
                CodeGenerator.createPart(variables.z),
                CodeGenerator.createPart(";")
        ));

        do {
            variables.z = CodeGenerator.exeOperator(variables.z, variables.y, o1);
        }
        while (--variables.y > n1);

        lines.add(new HBox(
                CodeGenerator.createPart("\tdo", CodeGenerator.CodeType.OPERATOR),
                CodeGenerator.createPart(" z"),
                CodeGenerator.createPart(CodeGenerator.getOperator(o1)),
                CodeGenerator.createPart("=y;")
        ));

        lines.add(new HBox(
                CodeGenerator.createPart("\twhile", CodeGenerator.CodeType.OPERATOR),
                CodeGenerator.createPart("(--y>"),
                CodeGenerator.createPart(n1),
                CodeGenerator.createPart("); "),
                CodeGenerator.createPart("PRINT(", CodeGenerator.CodeType.DEFINE1),
                CodeGenerator.createPart("z"),
                CodeGenerator.createPart(")", CodeGenerator.CodeType.DEFINE1),
                CodeGenerator.createPart(";")
        ));

        answers[3] = Integer.toString(variables.z);
    }


    private Label addIDO(CodeGenerator.IDOperator operator, String variable) {
        switch (operator) {
            case SUFIXDECREMENT:
                return CodeGenerator.createPart(variable + "--");
            case PREFIXDECREMENT:
                return CodeGenerator.createPart("--" + variable);
            case SUFIXINCREMENT:
                return CodeGenerator.createPart(variable + "++");
            case PREFIXINCREMENT:
                return CodeGenerator.createPart("++" + variable);
            default:
                return null;
        }
    }


    private void createFirstsLine() {
        lines.add(new HBox(
                CodeGenerator.createPart("#include", CodeGenerator.CodeType.DIRECTIVE),
                CodeGenerator.createPart(" <stdio.h>", CodeGenerator.CodeType.LIBRARY)
        ));

        lines.add(new HBox(
                CodeGenerator.createPart("#define", CodeGenerator.CodeType.DIRECTIVE),
                CodeGenerator.createPart(" PRINT(", CodeGenerator.CodeType.DEFINE1),
                CodeGenerator.createPart("int", CodeGenerator.CodeType.OPERATOR),
                CodeGenerator.createPart(")", CodeGenerator.CodeType.DEFINE1),
                CodeGenerator.createPart(" printf("),
                CodeGenerator.createPart("\"%d\\n\"", CodeGenerator.CodeType.STRING),
                CodeGenerator.createPart(", "),
                CodeGenerator.createPart("int", CodeGenerator.CodeType.OPERATOR),
                CodeGenerator.createPart(")")
        ));

        lines.add(new HBox(
                CodeGenerator.createPart(" ")
        ));

        lines.add(new HBox(
                CodeGenerator.createPart("void", CodeGenerator.CodeType.OPERATOR),
                CodeGenerator.createPart(" main()")
        ));

        lines.add(new HBox(
                CodeGenerator.createPart("{")
        ));
    }

    private void createLastLine() {
        lines.add(new HBox(
                CodeGenerator.createPart("}")
        ));
    }

    private class Variables {
        public int x;
        public int y;
        public int z;

        public Variables(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
